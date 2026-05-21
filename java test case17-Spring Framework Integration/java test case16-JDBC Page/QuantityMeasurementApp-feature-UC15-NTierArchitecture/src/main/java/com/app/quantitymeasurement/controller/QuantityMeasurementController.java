package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityInputDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityMeasurementController.class);
    private final IQuantityMeasurementService service;

    @Autowired
    public QuantityMeasurementController(IQuantityMeasurementService service) {
        if (service == null) throw new IllegalArgumentException("Service must not be null");
        this.service = service;
    }

    @PostMapping("/compare")
    public ResponseEntity<QuantityDTO> performCompare(@Valid @RequestBody QuantityInputDTO input) {
        LOGGER.info("REST: performCompare with payload: {}", input);
        QuantityDTO result = service.compare(input.getFirstQuantity(), input.getSecondQuantity());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/convert")
    public ResponseEntity<QuantityDTO> performConvert(@Valid @RequestBody QuantityInputDTO input) {
        LOGGER.info("REST: performConvert with payload: {}", input);
        QuantityDTO result = service.convert(input.getFirstQuantity(), input.getSecondQuantity());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    public ResponseEntity<QuantityDTO> performAdd(@Valid @RequestBody QuantityInputDTO input) {
        LOGGER.info("REST: performAdd with payload: {}", input);
        QuantityDTO result = service.add(input.getFirstQuantity(), input.getSecondQuantity(), input.getTargetQuantity());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    public ResponseEntity<QuantityDTO> performSubtract(@Valid @RequestBody QuantityInputDTO input) {
        LOGGER.info("REST: performSubtract with payload: {}", input);
        QuantityDTO result = service.subtract(input.getFirstQuantity(), input.getSecondQuantity(), input.getTargetQuantity());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    public ResponseEntity<QuantityDTO> performDivide(@Valid @RequestBody QuantityInputDTO input) {
        LOGGER.info("REST: performDivide with payload: {}", input);
        QuantityDTO result = service.divide(input.getFirstQuantity(), input.getSecondQuantity());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/history")
    public ResponseEntity<List<QuantityMeasurementDTO>> getHistory(
            @RequestParam(required = false) String operation,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Boolean errored) {
        LOGGER.info("REST: getHistory (operation={}, type={}, errored={})", operation, type, errored);
        if (errored != null && errored) {
            return ResponseEntity.ok(service.getErroredHistory());
        }
        if (operation != null) {
            return ResponseEntity.ok(service.getHistoryByOperation(operation));
        }
        if (type != null) {
            return ResponseEntity.ok(service.getHistoryByType(type));
        }
        // Retrieve all history by default if no filters are supplied
        return ResponseEntity.ok(service.getHistoryByOperation("ALL"));
    }

    @GetMapping("/metrics")
    public ResponseEntity<Long> getMetrics(@RequestParam String operation) {
        LOGGER.info("REST: getMetrics (operation={})", operation);
        long count = service.getCountByOperation(operation);
        return ResponseEntity.ok(count);
    }
}
