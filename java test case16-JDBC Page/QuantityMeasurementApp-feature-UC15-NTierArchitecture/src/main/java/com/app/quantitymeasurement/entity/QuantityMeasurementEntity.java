package com.app.quantitymeasurement.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private final String operationType;
    private final String measurementType;
    private final String input1Value;
    private final String input2Value;
    private final String targetUnit;
    private final String resultValue;
    private final boolean hasError;
    private final String errorMessage;

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

    // Full constructor for repository
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

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getOperationType()  { return operationType; }
    public String getMeasurementType() { return measurementType; }
    public String getInput1Value()    { return input1Value; }
    public String getInput2Value()    { return input2Value; }
    public String getTargetUnit()     { return targetUnit; }
    public String getResultValue()    { return resultValue; }
    public boolean hasError()         { return hasError; }
    public String getErrorMessage()   { return errorMessage; }

    @Override
    public String toString() {
        if (hasError) {
            return "Entity[op=" + operationType + ", category=" + measurementType + ", error=" + errorMessage + "]";
        }
        return "Entity[op=" + operationType
                + ", category=" + measurementType
                + ", in1=" + input1Value
                + (input2Value != null ? ", in2=" + input2Value : "")
                + (targetUnit  != null ? ", target=" + targetUnit  : "")
                + ", result=" + resultValue + "]";
    }
}
