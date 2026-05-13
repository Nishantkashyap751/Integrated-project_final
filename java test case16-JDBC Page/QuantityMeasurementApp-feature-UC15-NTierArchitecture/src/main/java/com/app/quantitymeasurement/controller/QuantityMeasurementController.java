package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityMeasurementController.class);
    private final IQuantityMeasurementService service;

    public QuantityMeasurementController(IQuantityMeasurementService service) {
        if (service == null) throw new IllegalArgumentException("Service must not be null");
        this.service = service;
    }

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

    public void displayResult(QuantityDTO result) {
        if (result == null) {
            LOGGER.error("Result is null");
            return;
        }
        if (result.hasError()) {
            LOGGER.error("Operation failed: {}", result.getErrorMessage());
        } else {
            LOGGER.info("Operation successful: {}", result);
        }
    }

    private void validateInput(QuantityDTO dto1, QuantityDTO dto2) {
        if (dto1 == null || dto2 == null)
            throw new IllegalArgumentException("Input DTOs must not be null");
    }
}
