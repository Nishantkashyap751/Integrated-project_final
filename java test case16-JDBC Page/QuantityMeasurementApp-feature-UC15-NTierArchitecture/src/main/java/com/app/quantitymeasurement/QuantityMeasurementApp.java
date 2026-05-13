package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.entity.QuantityDTO;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;
import com.app.quantitymeasurement.service.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityMeasurementApp.class);
    private static QuantityMeasurementApp instance;
    private final QuantityMeasurementController controller;

    private QuantityMeasurementApp() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        String repoType = config.getProperty("app.repository.type", "CACHE");
        
        IQuantityMeasurementRepository repository;
        if ("DATABASE".equalsIgnoreCase(repoType)) {
            LOGGER.info("Initializing DatabaseRepository...");
            repository = new QuantityMeasurementDatabaseRepository();
        } else {
            LOGGER.info("Initializing CacheRepository...");
            repository = QuantityMeasurementCacheRepository.getInstance();
        }

        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        this.controller = new QuantityMeasurementController(service);
        
        LOGGER.info("Application initialized with {} repository", repoType);
    }

    public static synchronized QuantityMeasurementApp getInstance() {
        if (instance == null) instance = new QuantityMeasurementApp();
        return instance;
    }

    public QuantityMeasurementController getController() {
        return controller;
    }

    public static void main(String[] args) {
        LOGGER.info("=== UC16: Quantity Measurement with JDBC Persistence ===");
        QuantityMeasurementApp app = getInstance();
        QuantityMeasurementController ctrl = app.getController();

        // Sample Operations
        QuantityDTO d1 = new QuantityDTO(1.0, "FEET", "LENGTH");
        QuantityDTO d2 = new QuantityDTO(12.0, "INCH", "LENGTH");
        QuantityDTO target = new QuantityDTO(0, "FEET", "LENGTH");
        ctrl.performAdd(d1, d2, target);

        QuantityDTO celsius = new QuantityDTO(0.0, "CELSIUS", "TEMPERATURE");
        QuantityDTO fahrenheitTarget = new QuantityDTO(0, "FAHRENHEIT", "TEMPERATURE");
        ctrl.performConvert(celsius, fahrenheitTarget);
        
        LOGGER.info("Demo operations completed.");
    }
}
