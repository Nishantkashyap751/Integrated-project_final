package com.quantity.measurement.dto;

public class QuantityDTO {

    private double value;
    private String unitName;
    private String measurementType;
    private boolean hasError;
    private String errorMessage;
    private Double scalarResult; // for divide operations

    // ==================== Constructors ====================

    public QuantityDTO() {}

    public QuantityDTO(double value, String unitName, String measurementType) {
        this.value = value;
        this.unitName = unitName;
        this.measurementType = measurementType;
        this.hasError = false;
    }

    // Constructor for scalar (divide) result
    public QuantityDTO(double scalarResult) {
        this.scalarResult = scalarResult;
        this.hasError = false;
    }

    // Error constructor
    public static QuantityDTO error(String errorMessage) {
        QuantityDTO dto = new QuantityDTO();
        dto.hasError = true;
        dto.errorMessage = errorMessage;
        return dto;
    }

    // ==================== Getters / Setters ====================

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getUnitName() { return unitName; }
    public void setUnitName(String unitName) { this.unitName = unitName; }

    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) { this.measurementType = measurementType; }

    public boolean hasError() { return hasError; }
    public void setHasError(boolean hasError) { this.hasError = hasError; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public Double getScalarResult() { return scalarResult; }
    public void setScalarResult(Double scalarResult) { this.scalarResult = scalarResult; }

    // ==================== Inner enum ====================

    public enum MeasurementType {
        LENGTH, WEIGHT, VOLUME, TEMPERATURE
    }

    @Override
    public String toString() {
        if (hasError) return "Error: " + errorMessage;
        if (scalarResult != null) return "Scalar(" + scalarResult + ")";
        return "Quantity(" + value + ", " + unitName + ")";
    }
}