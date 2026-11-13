USE campus_facility_db;
INSERT INTO reservations
(user_name, user_role, item_category, quantity,
 booking_days, daily_rate, subtotal, net_total)
VALUES
('FATIMA', 'Student', 'Laptop', 2, 3, 50, 300, 300),
('SARAH', 'Faculty', 'Projector', 1, 2, 80, 160, 136);