package com.quantity.measurement.repository;

import com.quantity.measurement.entity.QuantityMeasurementEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

    // ==================== Singleton ====================

    private static QuantityMeasurementCacheRepository instance;

    private QuantityMeasurementCacheRepository() {}

    public static synchronized QuantityMeasurementCacheRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementCacheRepository();
        }
        return instance;
    }

    // ==================== In-memory store ====================

    private final List<QuantityMeasurementEntity> cache = new ArrayList<>();

    // ==================== Operations ====================

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
    public void clear() {
        cache.clear();
    }
}