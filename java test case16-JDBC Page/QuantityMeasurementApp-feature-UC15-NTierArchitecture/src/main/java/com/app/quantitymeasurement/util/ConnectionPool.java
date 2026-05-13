package com.app.quantitymeasurement.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;

    private final String url;
    private final String user;
    private final String password;
    private final int maxSize;
    private final long timeoutMs;
    
    private final BlockingQueue<Connection> pool;
    private final AtomicInteger currentPoolSize = new AtomicInteger(0);

    private ConnectionPool() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");
        this.maxSize = config.getIntProperty("db.pool.maxSize", 10);
        this.timeoutMs = config.getIntProperty("db.pool.timeoutMs", 5000);
        this.pool = new LinkedBlockingQueue<>(maxSize);

        try {
            Class.forName(config.getProperty("db.driver", "org.h2.Driver"));
            initializePool(config.getIntProperty("db.pool.minIdle", 2));
        } catch (ClassNotFoundException e) {
            LOGGER.error("Database driver not found", e);
            throw new RuntimeException("DB Driver not found", e);
        }
    }

    private void initializePool(int minIdle) {
        for (int i = 0; i < minIdle; i++) {
            try {
                pool.add(createNewConnection());
            } catch (SQLException e) {
                LOGGER.error("Error creating initial connection", e);
            }
        }
    }

    private Connection createNewConnection() throws SQLException {
        currentPoolSize.incrementAndGet();
        return DriverManager.getConnection(url, user, password);
    }

    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        try {
            Connection conn = pool.poll(timeoutMs, TimeUnit.MILLISECONDS);
            if (conn == null) {
                if (currentPoolSize.get() < maxSize) {
                    return createNewConnection();
                }
                throw new SQLException("Connection pool timeout: No available connections");
            }
            if (conn.isClosed()) {
                currentPoolSize.decrementAndGet();
                return getConnection();
            }
            return conn;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SQLException("Connection request interrupted", e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    if (!pool.offer(connection)) {
                        connection.close();
                        currentPoolSize.decrementAndGet();
                    }
                } else {
                    currentPoolSize.decrementAndGet();
                }
            } catch (SQLException e) {
                LOGGER.error("Error releasing connection", e);
            }
        }
    }

    public String getStatistics() {
        return String.format("Pool size: %d | Active: %d | Idle: %d", 
                maxSize, getActiveConnections(), getIdleConnections());
    }

    public void shutdown() {
        Connection conn;
        while ((conn = pool.poll()) != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("Error closing connection during shutdown", e);
            }
        }
    }

    public int getActiveConnections() {
        return currentPoolSize.get() - pool.size();
    }

    public int getIdleConnections() {
        return pool.size();
    }
}
