package com.app.quantitymeasurement;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.util.ApplicationConfig;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verification tests based on UC16 verification requirements.
 */
public class QuantityMeasurementVerificationTests {

    private IQuantityMeasurementRepository repository;
    private ConnectionPool pool;

    @BeforeEach
    void setUp() {
        repository = new QuantityMeasurementDatabaseRepository();
        repository.clear();
        pool = ConnectionPool.getInstance();
    }

    @Test
    void testPackageStructure_AllLayersPresent() {
        assertNotNull(com.app.quantitymeasurement.repository.IQuantityMeasurementRepository.class);
        assertNotNull(com.app.quantitymeasurement.service.IQuantityMeasurementService.class);
        assertNotNull(com.app.quantitymeasurement.controller.QuantityMeasurementController.class);
        assertNotNull(com.app.quantitymeasurement.entity.QuantityDTO.class);
    }

    @Test
    void testDatabaseConfiguration_LoadedFromProperties() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        assertNotNull(config.getProperty("db.url"));
        assertEquals("sa", config.getProperty("db.user"));
    }

    @Test
    void testConnectionPool_Initialization() {
        int idle = pool.getIdleConnections();
        assertTrue(idle >= 0, "Pool should be initialized");
    }

    @Test
    void testConnectionPool_Acquire_Release() throws SQLException {
        int initialIdle = pool.getIdleConnections();
        Connection conn = pool.getConnection();
        assertEquals(initialIdle - 1, pool.getIdleConnections());
        
        pool.releaseConnection(conn);
        assertEquals(initialIdle, pool.getIdleConnections());
        assertTrue(pool.getStatistics().contains("Idle:"));
    }

    @Test
    void testConnectionPool_AllConnectionsExhausted() throws SQLException {
        ApplicationConfig config = ApplicationConfig.getInstance();
        int maxSize = config.getIntProperty("db.pool.maxSize", 10);
        List<Connection> connections = new ArrayList<>();
        
        try {
            // Exhaust pool
            for (int i = 0; i < maxSize; i++) {
                connections.add(pool.getConnection());
            }
            
            // Try one more - should throw exception based on pool configuration
            assertThrows(SQLException.class, () -> {
                pool.getConnection();
            }, "Should throw exception when pool is exhausted");
            
        } finally {
            for (Connection c : connections) {
                pool.releaseConnection(c);
            }
        }
    }

    @Test
    void testDatabaseRepository_Save_Retrieve_Filter_Count() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                "COMPARE", "LENGTH", "1 FEET", "12 INCH", null, "true");
        
        repository.save(entity);
        assertEquals(1, repository.getTotalCount());
        
        List<QuantityMeasurementEntity> all = repository.findAll();
        assertEquals(1, all.size());
        assertEquals("COMPARE", all.get(0).getOperationType());
        
        List<QuantityMeasurementEntity> filtered = repository.findByMeasurementType("LENGTH");
        assertEquals(1, filtered.size());
        
        List<QuantityMeasurementEntity> byOp = repository.findByOperationType("COMPARE");
        assertEquals(1, byOp.size());
    }
}
