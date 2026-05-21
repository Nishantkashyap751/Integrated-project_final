package com.app.quantitymeasurement.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementDTO {

    private Long id;
    private String operationType;
    private String measurementType;
    private String input1Value;
    private String input2Value;
    private String targetUnit;
    private String resultValue;
    private boolean hasError;
    private String errorMessage;
    private LocalDateTime createdAt;

    public static QuantityMeasurementDTO fromEntity(QuantityMeasurementEntity entity) {
        if (entity == null) return null;
        return new QuantityMeasurementDTO(
                entity.getId(),
                entity.getOperationType(),
                entity.getMeasurementType(),
                entity.getInput1Value(),
                entity.getInput2Value(),
                entity.getTargetUnit(),
                entity.getResultValue(),
                entity.isHasError(),
                entity.getErrorMessage(),
                entity.getCreatedAt()
        );
    }

    public QuantityMeasurementEntity toEntity() {
        return new QuantityMeasurementEntity(
                this.id,
                this.operationType,
                this.measurementType,
                this.input1Value,
                this.input2Value,
                this.targetUnit,
                this.resultValue,
                this.hasError,
                this.errorMessage
        );
    }

    public static List<QuantityMeasurementDTO> fromEntityList(List<QuantityMeasurementEntity> list) {
        if (list == null) return null;
        return list.stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public static List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> list) {
        if (list == null) return null;
        return list.stream()
                .map(QuantityMeasurementDTO::toEntity)
                .collect(Collectors.toList());
    }
}
