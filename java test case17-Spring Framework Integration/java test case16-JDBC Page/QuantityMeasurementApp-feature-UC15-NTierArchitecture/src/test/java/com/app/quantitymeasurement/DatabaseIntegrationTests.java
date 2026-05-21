package com.app.quantitymeasurement;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class DatabaseIntegrationTests {

    @Autowired
    private QuantityMeasurementRepository repository;

    @Autowired
    private IQuantityMeasurementService service;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void testDatabase_SaveAndRetrieve_AddOperation() {
        QuantityDTO d1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO d2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO tgt = new QuantityDTO(0.0, "FEET", "LENGTH");

        QuantityDTO result = service.add(d1, d2, tgt);

        assertFalse(result.hasError());
        assertEquals(2.0, result.getValue(), 1e-6);

        long count = repository.count();
        assertEquals(1, count);

        List<QuantityMeasurementEntity> saved = repository.findByOperationType("ADD");
        assertEquals(1, saved.size());
        assertEquals("LENGTH", saved.get(0).getMeasurementType());
    }

    @Test
    void testDatabase_FilterByMeasurementType() {
        QuantityDTO d1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        service.convert(d1, d1); // 1 record

        QuantityDTO w1 = new QuantityDTO(1.0, "KILOGRAM", "WEIGHT");
        service.convert(w1, w1); // 1 record

        assertEquals(2, repository.count());

        List<QuantityMeasurementEntity> lengthRecords = repository.findByMeasurementType("LENGTH");
        assertEquals(1, lengthRecords.size());

        List<QuantityMeasurementEntity> weightRecords = repository.findByMeasurementType("WEIGHT");
        assertEquals(1, weightRecords.size());
    }
}
