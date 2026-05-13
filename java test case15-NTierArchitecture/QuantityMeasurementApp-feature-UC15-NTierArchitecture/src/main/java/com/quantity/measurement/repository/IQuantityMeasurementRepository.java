package com.quantity.measurement.repository;

import com.quantity.measurement.entity.QuantityMeasurementEntity;
import java.util.List;

public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> findAll();

    List<QuantityMeasurementEntity> findByOperationType(String operationType);

    void clear();
}