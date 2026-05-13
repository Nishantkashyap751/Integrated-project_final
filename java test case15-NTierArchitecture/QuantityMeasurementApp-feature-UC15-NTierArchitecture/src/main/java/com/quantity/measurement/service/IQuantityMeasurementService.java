package com.quantity.measurement.service;

import com.quantity.measurement.dto.QuantityDTO;

public interface IQuantityMeasurementService {

    QuantityDTO compare(QuantityDTO dto1, QuantityDTO dto2);

    QuantityDTO convert(QuantityDTO source, QuantityDTO targetDTO);

    QuantityDTO add(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO);

    QuantityDTO subtract(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO);

    QuantityDTO divide(QuantityDTO dto1, QuantityDTO dto2);
}