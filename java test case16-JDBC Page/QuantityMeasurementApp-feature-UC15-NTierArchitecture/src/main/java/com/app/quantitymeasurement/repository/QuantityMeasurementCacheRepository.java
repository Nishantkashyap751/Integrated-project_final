package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    private static QuantityMeasurementCacheRepository instance;

    private QuantityMeasurementCacheRepository() {}

    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    private final List<QuantityMeasurementEntity> cache = new ArrayList<>();

    @Override
    public void save(QuantityMeasurementEntity entity) {
        if (entity == null) throw new IllegalArgumentException("Entity must not be null");
        cache.add(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {
        return Collections.unmodifiableList(cache);
    }

    @Override
    public List<QuantityMeasurementEntity> findByOperationType(String operationType) {
        return cache.stream()
                .filter(e -> operationType.equalsIgnoreCase(e.getOperationType()))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuantityMeasurementEntity> findByMeasurementType(String measurementType) {
        return cache.stream()
                .filter(e -> measurementType.equalsIgnoreCase(e.getMeasurementType()))
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public long getTotalCount() {
        return cache.size();
    }
}
