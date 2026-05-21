CREATE TABLE IF NOT EXISTS quantity_measurement_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation_type VARCHAR(50) NOT NULL,
    measurement_type VARCHAR(50),
    input1_value VARCHAR(255),
    input2_value VARCHAR(255),
    target_unit VARCHAR(50),
    result_value VARCHAR(255),
    has_error BOOLEAN DEFAULT FALSE,
    error_message VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX IF NOT EXISTS idx_operation_type ON quantity_measurement_entity(operation_type);
CREATE INDEX IF NOT EXISTS idx_measurement_type ON quantity_measurement_entity(measurement_type);
