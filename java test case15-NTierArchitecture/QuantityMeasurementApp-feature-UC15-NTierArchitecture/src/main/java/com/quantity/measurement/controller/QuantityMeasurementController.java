package com.quantity.measurement.controller;

import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    // Dependency Injection via constructor
    public QuantityMeasurementController(IQuantityMeasurementService service) {
        if (service == null) throw new IllegalArgumentException("Service must not be null");
        this.service = service;
    }

    // ==================== Facade Methods (REST-ready) ====================

    public QuantityDTO performCompare(QuantityDTO dto1, QuantityDTO dto2) {
        validateInput(dto1, dto2);
        QuantityDTO result = service.compare(dto1, dto2);
        displayResult(result);
        return result;
    }

    public QuantityDTO performConvert(QuantityDTO source, QuantityDTO targetDTO) {
        validateInput(source, targetDTO);
        QuantityDTO result = service.convert(source, targetDTO);
        displayResult(result);
        return result;
    }

    public QuantityDTO performAdd(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO) {
        validateInput(dto1, dto2);
        QuantityDTO result = service.add(dto1, dto2, targetDTO);
        displayResult(result);
        return result;
    }

    public QuantityDTO performSubtract(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO) {
        validateInput(dto1, dto2);
        QuantityDTO result = service.subtract(dto1, dto2, targetDTO);
        displayResult(result);
        return result;
    }

    public QuantityDTO performDivide(QuantityDTO dto1, QuantityDTO dto2) {
        validateInput(dto1, dto2);
        QuantityDTO result = service.divide(dto1, dto2);
        displayResult(result);
        return result;
    }

    // ==================== Display ====================

    public void displayResult(QuantityDTO result) {
        if (result == null) { System.out.println("Error: null result"); return; }
        if (result.hasError()) {
            System.out.println("Error: " + result.getErrorMessage());
        } else {
            System.out.println(result.toString());
        }
    }

    // ==================== Validation ====================

    private void validateInput(QuantityDTO dto1, QuantityDTO dto2) {
        if (dto1 == null || dto2 == null)
            throw new IllegalArgumentException("Input DTOs must not be null");
    }
}