package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import java.util.List;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> findAll();

    List<QuantityMeasurementEntity> findByOperationType(String operationType);
    
    List<QuantityMeasurementEntity> findByMeasurementType(String measurementType);

    void clear();
    
    long getTotalCount();
}
