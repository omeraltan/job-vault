CREATE TABLE IF NOT EXISTS candidate (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) NOT NULL,
    position_type VARCHAR(255) NOT NULL,
    military_status VARCHAR(255) NOT NULL,
    notice_period VARCHAR(255) NOT NULL,
    cv_path VARCHAR(255)
    );
