CREATE TABLE IF NOT EXISTS patient (
     id INT AUTO_INCREMENT PRIMARY KEY,
     full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    address VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    register_date DATE NOT NULL
    );

INSERT INTO patient (full_name, email, address, date_of_birth, register_date)
VALUES
('John Doe', 'john.doe@example.com', '123 Elm Street', '1985-04-12', '2025-08-05'),
('Jane Smith', 'jane.smith@example.com', '456 Oak Avenue', '1990-11-23', '2025-08-05'),
('Alice Johnson', 'alice.johnson@example.com', '789 Pine Road', '1978-07-30', '2025-08-05');
