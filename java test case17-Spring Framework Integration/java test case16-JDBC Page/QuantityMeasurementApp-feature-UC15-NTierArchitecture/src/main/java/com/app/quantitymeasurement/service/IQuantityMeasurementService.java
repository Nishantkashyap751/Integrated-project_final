package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.model.QuantityDTO;
import com.app.quantitymeasurement.model.QuantityMeasurementDTO;
import java.util.List;

public interface IQuantityMeasurementService {

    QuantityDTO compare(QuantityDTO dto1, QuantityDTO dto2);

    QuantityDTO convert(QuantityDTO source, QuantityDTO targetDTO);

    QuantityDTO add(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO);

    QuantityDTO subtract(QuantityDTO dto1, QuantityDTO dto2, QuantityDTO targetDTO);

    QuantityDTO divide(QuantityDTO dto1, QuantityDTO dto2);

    List<QuantityMeasurementDTO> getHistoryByOperation(String operation);

    List<QuantityMeasurementDTO> getHistoryByType(String type);

    List<QuantityMeasurementDTO> getErroredHistory();

    long getCountByOperation(String operation);
}
