
/*ESTACIONES*/
INSERT INTO Stations (Name, Address, Latitude, Longitude, IsActive) VALUES
('Estación Centro', 'Calle Mayor 1', 40.4168, -3.7038, 1),
('Estación Norte', 'Avenida Norte 45', 40.4800, -3.6880, 1),
('Estación Sur', 'Calle Sur 12', 40.3500, -3.7000, 1),
('Estación Este', 'Avenida del Este 8', 40.4200, -3.6000, 1),
('Estación Oeste', 'Calle Oeste 99', 40.4300, -3.7800, 1);

/*CARGADORES*/
INSERT INTO Chargers (StationId, Type, MaxPower, IsOccupied) VALUES
(1, 0, 22, 0),
(1, 2, 150, 1),
(2, 1, 50, 0),
(3, 3, 3, 0),
(4, 2, 350, 1);

/*USUARIOS*/
INSERT INTO Users (FullName, Email, RFIDTag, Balance) VALUES
('Juan Pérez', 'juan.perez@email.com', 'RFID0001', 50.0000),
('María López', 'maria.lopez@email.com', 'RFID0002', 30.5000),
('Carlos Gómez', 'carlos.gomez@email.com', 'RFID0003', 75.2500),
('Laura Sánchez', 'laura.sanchez@email.com', 'RFID0004', 10.0000),
('David Ruiz', 'david.ruiz@email.com', 'RFID0005', 120.0000);


/*TARIFAS*/
INSERT INTO Tariffs (Name, PricePerKWh, StartHour, EndHour) VALUES
('Nocturna', 0.1200, '00:00:00', '06:00:00'),
('Mañana', 0.2000, '06:00:00', '12:00:00'),
('Mediodía', 0.2500, '12:00:00', '18:00:00'),
('Tarde', 0.3000, '18:00:00', '22:00:00'),
('Valle', 0.1500, '22:00:00', '23:59:59');


/*SESION DE CARGA*/
INSERT INTO ChargingSessions
(ChargerId, UserId, StartTime, EndTime, KWhConsumed, TotalCost) VALUES
(1, 1, '08:00:00', '09:30:00', 12.50, 2.5000),
(2, 2, '10:00:00', '11:00:00', 20.00, 5.0000),
(3, 3, '14:15:00', '15:45:00', 18.75, 4.6875),
(4, 4, '19:00:00', NULL, 5.00, 1.5000),
(5, 5, '22:30:00', '23:30:00', 10.00, 1.5000);



