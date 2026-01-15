/* Victor Aracil Gozalvez*/

/* A tener en cuenta:
	Al estar probando el programa, cuando le das a las estaciones, tarda un poco en cargar y que se muestren*/

/*ESTACIONES*/
INSERT INTO Stations (Name, Address, Latitude, Longitude, IsActive) VALUES
('Station Centro', 'Calle Mayor 1', 40.4168, -3.7038, 1),
('Station Norte', 'Av. Norte 12', 40.4800, -3.6880, 1),
('Station Sur', 'Calle Sur 22', 40.3500, -3.7000, 1),
('Station Este', 'Av. Este 9', 40.4200, -3.6000, 1),
('Station Oeste', 'Calle Oeste 88', 40.4300, -3.7800, 1),
('Station Plaza', 'Plaza España 3', 40.4230, -3.7120, 1),
('Station Airport', 'Aeropuerto T4', 40.4983, -3.5676, 1),
('Station Mall', 'Centro Comercial', 40.4010, -3.6890, 1),
('Station Tech', 'Parque Tecnológico', 40.4500, -3.6500, 1),
('Station Industrial', 'Polígono Norte', 40.5100, -3.7200, 0);


/*CARGADORES*/
INSERT INTO Chargers (StationId, Type, MaxPower, IsOccupied) VALUES
(1, 0, 22, 0),
(1, 2, 150, 1),
(2, 1, 50, 0),
(2, 2, 120, 1),
(3, 3, 3, 0),
(4, 2, 350, 1),
(5, 0, 22, 0),
(6, 1, 50, 0),
(7, 2, 250, 1),
(8, 3, 7, 0);


/*USUARIOS*/
INSERT INTO Users (FullName, Email, RFIDTag, Balance) VALUES
('Juan Pérez', 'juan.perez@mail.com', 'RFID001', 50.0000),
('María López', 'maria.lopez@mail.com', 'RFID002', 30.5000),
('Carlos Gómez', 'carlos.gomez@mail.com', 'RFID003', 75.2500),
('Laura Sánchez', 'laura.sanchez@mail.com', 'RFID004', 10.0000),
('David Ruiz', 'david.ruiz@mail.com', 'RFID005', 120.0000),
('Ana Martín', 'ana.martin@mail.com', 'RFID006', 60.0000),
('Pedro León', 'pedro.leon@mail.com', 'RFID007', 45.5000),
('Lucía Torres', 'lucia.torres@mail.com', 'RFID008', 90.0000),
('Jorge Vidal', 'jorge.vidal@mail.com', 'RFID009', 15.0000),
('Sara Molina', 'sara.molina@mail.com', 'RFID010', 200.0000);



/*TARIFAS*/
INSERT INTO Tariffs (Name, PricePerKWh, StartHour, EndHour) VALUES
('Nocturna', 0.1200, '00:00:00', '06:00:00'),
('Mañana', 0.2000, '06:00:00', '09:00:00'),
('Media Mañana', 0.2300, '09:00:00', '12:00:00'),
('Mediodía', 0.2500, '12:00:00', '15:00:00'),
('Tarde', 0.2800, '15:00:00', '18:00:00'),
('Pico', 0.3200, '18:00:00', '21:00:00'),
('Valle', 0.1500, '21:00:00', '23:00:00'),
('Super Valle', 0.1000, '23:00:00', '23:59:59'),
('Fin de Semana', 0.1800, '00:00:00', '23:59:59'),
('Especial', 0.2200, '10:00:00', '14:00:00');



/*SESION DE CARGA*/
INSERT INTO ChargingSessions
(ChargerId, UserId, StartTime, EndTime, KWhConsumed, TotalCost) VALUES
(1, 1, '08:00:00', '09:30:00', 12.50, 2.5000),
(2, 2, '10:00:00', '11:00:00', 20.00, 5.0000),
(3, 3, '14:15:00', '15:45:00', 18.75, 4.6875),
(4, 4, '19:00:00', NULL, 5.00, 1.5000),
(5, 5, '22:30:00', '23:30:00', 10.00, 1.5000),
(6, 6, '07:00:00', '08:00:00', 15.00, 3.0000),
(7, 7, '12:00:00', '13:15:00', 22.50, 5.6250),
(8, 8, '16:30:00', '18:00:00', 30.00, 9.6000),
(9, 9, '20:00:00', '21:30:00', 25.00, 8.0000),
(10, 10, '23:00:00', '23:59:00', 8.00, 0.8000);




