package com.app.quantitymeasurement.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "quantity_measurements")
@Data
@NoArgsConstructor
public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_type", nullable = false)
    private String operationType;

    @Column(name = "measurement_type", nullable = false)
    private String measurementType;

    @Column(name = "input_1_value")
    private String input1Value;

    @Column(name = "input_2_value")
    private String input2Value;

    @Column(name = "target_unit")
    private String targetUnit;

    @Column(name = "result_value")
    private String resultValue;

    @Column(name = "has_error", nullable = false)
    private boolean hasError;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public QuantityMeasurementEntity(String operationType,
                                     String measurementType,
                                     String input1Value,
                                     String targetUnit,
                                     String resultValue) {
        this.operationType = operationType;
        this.measurementType = measurementType;
        this.input1Value = input1Value;
        this.input2Value = null;
        this.targetUnit = targetUnit;
        this.resultValue = resultValue;
        this.hasError = false;
        this.errorMessage = null;
    }

    public QuantityMeasurementEntity(String operationType,
                                     String measurementType,
                                     String input1Value,
                                     String input2Value,
                                     String targetUnit,
                                     String resultValue) {
        this.operationType = operationType;
        this.measurementType = measurementType;
        this.input1Value = input1Value;
        this.input2Value = input2Value;
        this.targetUnit = targetUnit;
        this.resultValue = resultValue;
        this.hasError = false;
        this.errorMessage = null;
    }

    public QuantityMeasurementEntity(String operationType,
                                     String measurementType,
                                     String input1Value,
                                     String input2Value,
                                     String errorMessage,
                                     boolean hasError) {
        this.operationType = operationType;
        this.measurementType = measurementType;
        this.input1Value = input1Value;
        this.input2Value = input2Value;
        this.targetUnit = null;
        this.resultValue = null;
        this.hasError = hasError;
        this.errorMessage = errorMessage;
    }

    // Full constructor for repository mapping / tests
    public QuantityMeasurementEntity(Long id, String operationType, String measurementType, String input1Value, String input2Value, 
                                     String targetUnit, String resultValue, boolean hasError, String errorMessage) {
        this.id = id;
        this.operationType = operationType;
        this.measurementType = measurementType;
        this.input1Value = input1Value;
        this.input2Value = input2Value;
        this.targetUnit = targetUnit;
        this.resultValue = resultValue;
        this.hasError = hasError;
        this.errorMessage = errorMessage;
    }
}
