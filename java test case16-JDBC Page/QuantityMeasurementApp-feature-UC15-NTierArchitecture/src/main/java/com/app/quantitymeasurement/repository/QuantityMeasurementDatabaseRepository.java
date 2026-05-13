package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityMeasurementDatabaseRepository.class);
    private final ConnectionPool connectionPool;

    public QuantityMeasurementDatabaseRepository() {
        this.connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        String sql = "INSERT INTO quantity_measurement_entity (operation_type, measurement_type, input1_value, input2_value, target_unit, result_value, has_error, error_message) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, entity.getOperationType());
                pstmt.setString(2, entity.getMeasurementType());
                pstmt.setString(3, entity.getInput1Value());
                pstmt.setString(4, entity.getInput2Value());
                pstmt.setString(5, entity.getTargetUnit());
                pstmt.setString(6, entity.getResultValue());
                pstmt.setBoolean(7, entity.hasError());
                pstmt.setString(8, entity.getErrorMessage());
                
                pstmt.executeUpdate();
                
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        entity.setId(id);
                        LOGGER.info("Saved entity id={}", id);
                    }
                }
                
                conn.commit(); // Commit transaction
                LOGGER.debug("Entity saved to database: {} - {}", entity.getOperationType(), entity.getMeasurementType());
                LOGGER.info("Connection Stats: {}", connectionPool.getStatistics());
            } catch (SQLException e) {
                conn.rollback(); // Rollback on error
                throw e;
            }
        } catch (SQLException e) {
            LOGGER.error("Error saving entity to database", e);
            throw new DatabaseException("Could not save measurement", e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException e) {
                    LOGGER.error("Error resetting auto-commit", e);
                }
                connectionPool.releaseConnection(conn);
            }
        }
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {
        String sql = "SELECT * FROM quantity_measurement_entity";
        return executeQuery(sql);
    }

    @Override
    public List<QuantityMeasurementEntity> findByOperationType(String operationType) {
        String sql = "SELECT * FROM quantity_measurement_entity WHERE operation_type = ?";
        return executeQuery(sql, operationType);
    }

    @Override
    public List<QuantityMeasurementEntity> findByMeasurementType(String measurementType) {
        String sql = "SELECT * FROM quantity_measurement_entity WHERE measurement_type = ?";
        return executeQuery(sql, measurementType);
    }

    @Override
    public void clear() {
        String sql = "DELETE FROM quantity_measurement_entity";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(sql);
                LOGGER.info("All records deleted from database");
            }
        } catch (SQLException e) {
            LOGGER.error("Error deleting all records", e);
            throw new DatabaseException("Could not delete records", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
    }

    @Override
    public long getTotalCount() {
        String sql = "SELECT COUNT(*) FROM quantity_measurement_entity";
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error getting total count", e);
            throw new DatabaseException("Could not get count", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return 0;
    }

    private List<QuantityMeasurementEntity> executeQuery(String sql, Object... params) {
        List<QuantityMeasurementEntity> results = new ArrayList<>();
        Connection conn = null;
        try {
            conn = connectionPool.getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        results.add(mapResultSetToEntity(rs));
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Error executing query: {}", sql, e);
            throw new DatabaseException("Query execution failed", e);
        } finally {
            connectionPool.releaseConnection(conn);
        }
        return results;
    }

    private QuantityMeasurementEntity mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new QuantityMeasurementEntity(
                rs.getLong("id"),
                rs.getString("operation_type"),
                rs.getString("measurement_type"),
                rs.getString("input1_value"),
                rs.getString("input2_value"),
                rs.getString("target_unit"),
                rs.getString("result_value"),
                rs.getBoolean("has_error"),
                rs.getString("error_message")
        );
    }
}
