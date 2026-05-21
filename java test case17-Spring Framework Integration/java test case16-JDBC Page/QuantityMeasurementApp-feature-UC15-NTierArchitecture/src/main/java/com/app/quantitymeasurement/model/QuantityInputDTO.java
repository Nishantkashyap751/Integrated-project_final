package com.app.quantitymeasurement.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityInputDTO {

    @NotNull(message = "First quantity cannot be null")
    @Valid
    private QuantityDTO firstQuantity;

    @NotNull(message = "Second quantity cannot be null")
    @Valid
    private QuantityDTO secondQuantity;

    @Valid
    private QuantityDTO targetQuantity;
}
