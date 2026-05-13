package com.quantity.measurement.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    // Fields
    private final String operationType;        // COMPARE, CONVERT, ADD, SUBTRACT, DIVIDE
    private final String input1Value;
    private final String input2Value;          // null for single-operand
    private final String targetUnit;
    private final String resultValue;
    private final boolean hasError;
    private final String errorMessage;

    // ==================== Constructors ====================

    // Single-operand (e.g., CONVERT)
    public QuantityMeasurementEntity(String operationType,
                                     String input1Value,
                                     String targetUnit,
                                     String resultValue) {
        this.operationType = operationType;
        this.input1Value = input1Value;
        this.input2Value = null;
        this.targetUnit = targetUnit;
        this.resultValue = resultValue;
        this.hasError = false;
        this.errorMessage = null;
    }

    // Binary-operand (e.g., ADD, SUBTRACT, COMPARE, DIVIDE)
    public QuantityMeasurementEntity(String operationType,
                                     String input1Value,
                                     String input2Value,
                                     String targetUnit,
                                     String resultValue) {
        this.operationType = operationType;
        this.input1Value = input1Value;
        this.input2Value = input2Value;
        this.targetUnit = targetUnit;
        this.resultValue = resultValue;
        this.hasError = false;
        this.errorMessage = null;
    }

    // Error constructor
    public QuantityMeasurementEntity(String operationType,
                                     String input1Value,
                                     String input2Value,
                                     String errorMessage,
                                     boolean hasError) {
        this.operationType = operationType;
        this.input1Value = input1Value;
        this.input2Value = input2Value;
        this.targetUnit = null;
        this.resultValue = null;
        this.hasError = hasError;
        this.errorMessage = errorMessage;
    }

    // ==================== Getters ====================

    public String getOperationType()  { return operationType; }
    public String getInput1Value()    { return input1Value; }
    public String getInput2Value()    { return input2Value; }
    public String getTargetUnit()     { return targetUnit; }
    public String getResultValue()    { return resultValue; }
    public boolean hasError()         { return hasError; }
    public String getErrorMessage()   { return errorMessage; }

    // ==================== toString ====================

    @Override
    public String toString() {
        if (hasError) {
            return "Entity[op=" + operationType + ", error=" + errorMessage + "]";
        }
        return "Entity[op=" + operationType
                + ", in1=" + input1Value
                + (input2Value != null ? ", in2=" + input2Value : "")
                + (targetUnit  != null ? ", target=" + targetUnit  : "")
                + ", result=" + resultValue + "]";
    }
}