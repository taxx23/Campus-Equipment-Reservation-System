CREATE DATABASE IF NOT EXISTS campus_facility_db;
USE campus_facility_db;

CREATE TABLE reservations (
    reservation_id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(100),
    user_role VARCHAR(20),
    item_category VARCHAR(20),
    quantity INT,
    booking_days INT,
    daily_rate DECIMAL(10,2),
    subtotal DECIMAL(10,2),
    net_total DECIMAL(10,2)
);