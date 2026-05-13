package com.app.quantitymeasurement;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseIntegrationTests {

    private IQuantityMeasurementRepository repository;
    private IQuantityMeasurementService service;
    private QuantityMeasurementController controller;

    @BeforeEach
    void setUp() {
        repository = new QuantityMeasurementDatabaseRepository();
        repository.clear();
        service = new QuantityMeasurementServiceImpl(repository);
        controller = new QuantityMeasurementController(service);
    }

    @Test
    void testDatabase_SaveAndRetrieve_AddOperation() {
        QuantityDTO d1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO d2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO tgt = new QuantityDTO(0.0, "FEET", "LENGTH");

        QuantityDTO result = controller.performAdd(d1, d2, tgt);

        assertFalse(result.hasError());
        assertEquals(2.0, result.getValue(), 1e-6);

        long count = repository.getTotalCount();
        assertEquals(1, count);

        List<QuantityMeasurementEntity> saved = repository.findByOperationType("ADD");
        assertEquals(1, saved.size());
        assertEquals("LENGTH", saved.get(0).getMeasurementType());
    }

    @Test
    void testDatabase_FilterByMeasurementType() {
        QuantityDTO d1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        controller.performConvert(d1, d1); // 1 record

        QuantityDTO w1 = new QuantityDTO(1.0, "KILOGRAM", "WEIGHT");
        controller.performConvert(w1, w1); // 1 record

        assertEquals(2, repository.getTotalCount());

        List<QuantityMeasurementEntity> lengthRecords = repository.findByMeasurementType("LENGTH");
        assertEquals(1, lengthRecords.size());

        List<QuantityMeasurementEntity> weightRecords = repository.findByMeasurementType("WEIGHT");
        assertEquals(1, weightRecords.size());
    }
}
