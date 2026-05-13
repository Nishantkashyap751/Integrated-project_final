package QuantityMeasurementApp;

import com.quantity.measurement.controller.QuantityMeasurementController;
import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.repository.IQuantityMeasurementRepository;
import com.quantity.measurement.repository.QuantityMeasurementCacheRepository;
import com.quantity.measurement.service.IQuantityMeasurementService;
import com.quantity.measurement.serviceimpl.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    // ==================== Singleton ====================

    private static QuantityMeasurementApp instance;

    private final QuantityMeasurementController controller;

    private QuantityMeasurementApp() {
        // Factory: creates all dependencies and wires them via DI
        IQuantityMeasurementRepository repository = QuantityMeasurementCacheRepository.getInstance();
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        this.controller = new QuantityMeasurementController(service);
    }

    public static synchronized QuantityMeasurementApp getInstance() {
        if (instance == null) instance = new QuantityMeasurementApp();
        return instance;
    }

    public QuantityMeasurementController getController() {
        return controller;
    }

    // ==================== Entry Point ====================

    public static void main(String[] args) {
        QuantityMeasurementApp app = getInstance();
        QuantityMeasurementController ctrl = app.getController();

        System.out.println("=== UC15: Quantity Measurement N-Tier Demo ===");

        // Addition demo: 1 FEET + 12 INCHES → FEET
        QuantityDTO d1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO d2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO target = new QuantityDTO(0, "FEET", "LENGTH");
        ctrl.performAdd(d1, d2, target);

        // Conversion demo: 0 CELSIUS → FAHRENHEIT
        QuantityDTO celsius = new QuantityDTO(0.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO fahrenheitTarget = new QuantityDTO(0, "FAHRENHEIT", "TEMPERATURE");
        ctrl.performConvert(celsius, fahrenheitTarget);

        // Temperature add (error demo)
        QuantityDTO c1 = new QuantityDTO(100.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO c2 = new QuantityDTO(50.0, "CELSIUS", "TEMPERATURE");
        ctrl.performAdd(c1, c2, c1);
    }
}