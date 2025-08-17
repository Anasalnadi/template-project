-- CREATE TABLE IF NOT EXISTS patient (
--      id INT AUTO_INCREMENT PRIMARY KEY,
--      full_name VARCHAR(255) NOT NULL,
--     email VARCHAR(255) NOT NULL UNIQUE,
--     address VARCHAR(255) NOT NULL,
--     date_of_birth DATE NOT NULL,
--     register_date DATE NOT NULL
--     );

CREATE TABLE IF NOT EXISTS patient (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
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
('Alice Johnson', 'alice.johnson@example.com', '789 Pine Road', '1978-07-30', '2025-08-05'),
('Michael Brown', 'michael.brown@example.com', '321 Maple Drive, Miami', '1982-01-15', '2025-08-05'),
('Emily Davis', 'emily.davis@example.com', '654 Cedar Street, Dallas', '1995-06-22', '2025-08-05'),
('David Wilson', 'david.wilson@example.com', '987 Birch Lane, Seattle', '1970-03-08', '2025-08-05'),
('Sarah Miller', 'sarah.miller@example.com', '159 Spruce Court, Boston', '1988-12-05', '2025-08-05'),
('Robert Taylor', 'robert.taylor@example.com', '753 Willow Way, Denver', '1992-09-14', '2025-08-05'),
('Linda Anderson', 'linda.anderson@example.com', '258 Aspen Street, Phoenix', '1983-05-19', '2025-08-05'),
('James Thomas', 'james.thomas@example.com', '147 Poplar Road, Atlanta', '1999-07-11', '2025-08-05'),
('Patricia Jackson', 'patricia.jackson@example.com', '369 Dogwood Blvd, Houston', '1986-02-24', '2025-08-05'),
('Christopher White', 'chris.white@example.com', '951 Redwood Lane, San Francisco', '1994-08-29', '2025-08-05'),
('Barbara Harris', 'barbara.harris@example.com', '753 Palm Street, Orlando', '1975-10-17', '2025-08-05'),
('Daniel Martin', 'daniel.martin@example.com', '852 Magnolia Ave, Austin', '1989-04-09', '2025-08-05'),
('Elizabeth Thompson', 'elizabeth.thompson@example.com', '147 Cypress Road, Portland', '1993-11-20', '2025-08-05');


