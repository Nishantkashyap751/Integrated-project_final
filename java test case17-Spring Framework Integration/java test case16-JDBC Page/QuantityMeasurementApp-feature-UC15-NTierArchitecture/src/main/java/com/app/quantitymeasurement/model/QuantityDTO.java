package com.app.quantitymeasurement.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuantityDTO {

    @NotNull(message = "Value cannot be null")
    private Double value;

    @NotEmpty(message = "Unit name cannot be empty")
    private String unitName;

    @NotEmpty(message = "Measurement type cannot be empty")
    private String measurementType;

    private boolean hasError;
    private String errorMessage;
    private Double scalarResult;

    public QuantityDTO(double value, String unitName, String measurementType) {
        this.value = value;
        this.unitName = unitName;
        this.measurementType = measurementType;
        this.hasError = false;
    }

    public QuantityDTO(double scalarResult) {
        this.scalarResult = scalarResult;
        this.hasError = false;
    }

    public static QuantityDTO error(String errorMessage) {
        QuantityDTO dto = new QuantityDTO();
        dto.hasError = true;
        dto.errorMessage = errorMessage;
        return dto;
    }

    public boolean hasError() {
        return hasError;
    }

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
