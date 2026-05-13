package com.quantity.measurement.serviceimpl;

import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.entity.QuantityMeasurementEntity;
import com.quantity.measurement.enumslmpl.LengthUnit;
import com.quantity.measurement.enumslmpl.TemperatureUnit;
import com.quantity.measurement.enumslmpl.VolumeUnit;
import com.quantity.measurement.enumslmpl.WeightUnit;
import com.quantity.measurement.enums.IMeasurable;
import com.quantity.measurement.exception.QuantityMeasurementException;
import com.quantity.measurement.model.Quantity;
import com.quantity.measurement.repository.IQuantityMeasurementRepository;
import com.quantity.measurement.service.IQuantityMeasurementService;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    private final IQuantityMeasurementRepository repository;

    // Dependency Injection via constructor
    public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
        if (repository == null) throw new IllegalArgumentException("Repository must not be null");
        this.repository = repository;
    }

    // ==================== Public API ====================

    @Override
    public QuantityDTO compare(QuantityDTO dto1, QuantityDTO dto2) {
        validateNotNull(dto1, dto2);
        try {
            validateSameType(dto1, dto2);
            boolean result = buildQuantity(dto1).equals(buildQuantity(dto2));
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    "COMPARE", dto1.toString(), dto2.toString(), null, String.valueOf(result));
            repository.save(entity);
            QuantityDTO out = new QuantityDTO(result ? 1.0 : 0.0, "BOOLEAN", "COMPARE");
            out.setScalarResult(result ? 1.0 : 0.0);
            return out;
        } catch (Exception e) {
            return handleError("COMPARE", dto1, dto2, e);
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO source, QuantityDTO targetDTO) {
        validateNotNull(source, targetDTO);
        try {
            IMeasurable targetUnit = resolveUnit(targetDTO.getUnitName(), targetDTO.getMeasurementType());
            IMeasurable sourceUnit = resolveUnit(source.getUnitName(), source.getMeasurementType());
            double baseValue = sourceUnit.convertToBaseUnit(source.getValue());
            double converted = targetUnit.convertFromBaseUnit(baseValue);

            QuantityDTO result = new QuantityDTO(converted, targetDTO.getUnitName(), targetDTO.getMeasurementType());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    "CONVERT", source.toString(), targetDTO.getUnitName(), result.toString());
            repository.save(entity);
            return result;
        } catch (Exception e) {
            return handleError("CONVERT", source, targetDTO, e);
        }
    }

    @Override
    public QuantityDTO add(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO) {
        validateNotNull(dto1, dto2);
        if (targetDTO == null) targetDTO = dto1;
        try {
            validateSameType(dto1, dto2);
            @SuppressWarnings("unchecked")
            Quantity<IMeasurable> q1 = (Quantity<IMeasurable>) buildQuantity(dto1);
            @SuppressWarnings("unchecked")
            Quantity<IMeasurable> q2 = (Quantity<IMeasurable>) buildQuantity(dto2);
            IMeasurable targetUnit = resolveUnit(targetDTO.getUnitName(), dto1.getMeasurementType());

            @SuppressWarnings("unchecked")
            Quantity<IMeasurable> res = q1.add(q2, (IMeasurable) targetUnit);

            QuantityDTO out = new QuantityDTO(res.getValue(), targetUnit.toString(), dto1.getMeasurementType());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    "ADD", dto1.toString(), dto2.toString(), targetUnit.toString(), out.toString());
            repository.save(entity);
            return out;
        } catch (Exception e) {
            return handleError("ADD", dto1, dto2, e);
        }
    }

    @Override
    public QuantityDTO subtract(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO) {
        validateNotNull(dto1, dto2);
        if (targetDTO == null) targetDTO = dto1;
        try {
            validateSameType(dto1, dto2);
            @SuppressWarnings("unchecked")
            Quantity<IMeasurable> q1 = (Quantity<IMeasurable>) buildQuantity(dto1);
            @SuppressWarnings("unchecked")
            Quantity<IMeasurable> q2 = (Quantity<IMeasurable>) buildQuantity(dto2);
            IMeasurable targetUnit = resolveUnit(targetDTO.getUnitName(), dto1.getMeasurementType());

            @SuppressWarnings("unchecked")
            Quantity<IMeasurable> res = q1.subtract(q2, (IMeasurable) targetUnit);

            QuantityDTO out = new QuantityDTO(res.getValue(), targetUnit.toString(), dto1.getMeasurementType());
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    "SUBTRACT", dto1.toString(), dto2.toString(), targetUnit.toString(), out.toString());
            repository.save(entity);
            return out;
        } catch (Exception e) {
            return handleError("SUBTRACT", dto1, dto2, e);
        }
    }

    @Override
    public QuantityDTO divide(QuantityDTO dto1, QuantityDTO dto2) {
        validateNotNull(dto1, dto2);
        try {
            validateSameType(dto1, dto2);
            @SuppressWarnings("unchecked")
            Quantity<IMeasurable> q1 = (Quantity<IMeasurable>) buildQuantity(dto1);
            @SuppressWarnings("unchecked")
            Quantity<IMeasurable> q2 = (Quantity<IMeasurable>) buildQuantity(dto2);

            double scalar = q1.divide(q2);
            QuantityDTO out = new QuantityDTO(scalar);
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    "DIVIDE", dto1.toString(), dto2.toString(), null, String.valueOf(scalar));
            repository.save(entity);
            return out;
        } catch (Exception e) {
            return handleError("DIVIDE", dto1, dto2, e);
        }
    }

    // ==================== Helpers ====================

    private void validateNotNull(QuantityDTO dto1, QuantityDTO dto2) {
        if (dto1 == null || dto2 == null)
            throw new IllegalArgumentException("QuantityDTO inputs must not be null");
    }

    private void validateSameType(QuantityDTO dto1, QuantityDTO dto2) {
        if (!dto1.getMeasurementType().equalsIgnoreCase(dto2.getMeasurementType())) {
            throw new QuantityMeasurementException(
                    "Different measurement types: " + dto1.getMeasurementType() + " vs " + dto2.getMeasurementType());
        }
    }

    @SuppressWarnings("rawtypes")
    private Quantity<?> buildQuantity(QuantityDTO dto) {
        IMeasurable unit = resolveUnit(dto.getUnitName(), dto.getMeasurementType());
        return new Quantity<>(dto.getValue(), unit);
    }

    IMeasurable resolveUnit(String unitName, String measurementType) {
        if (unitName == null || measurementType == null)
            throw new QuantityMeasurementException("unitName and measurementType must not be null");
        switch (measurementType.toUpperCase()) {
            case "LENGTH":     return LengthUnit.valueOf(unitName.toUpperCase());
            case "WEIGHT":     return WeightUnit.valueOf(unitName.toUpperCase());
            case "VOLUME":     return VolumeUnit.valueOf(unitName.toUpperCase());
            case "TEMPERATURE":return TemperatureUnit.valueOf(unitName.toUpperCase());
            default: throw new QuantityMeasurementException("Unknown measurement type: " + measurementType);
        }
    }

    private QuantityDTO handleError(String operation, QuantityDTO dto1, QuantityDTO dto2, Exception e) {
        String msg = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        QuantityMeasurementEntity errorEntity = new QuantityMeasurementEntity(
                operation,
                dto1 != null ? dto1.toString() : "null",
                dto2 != null ? dto2.toString() : "null",
                msg,
                true);
        repository.save(errorEntity);
        return QuantityDTO.error(msg);
    }
}