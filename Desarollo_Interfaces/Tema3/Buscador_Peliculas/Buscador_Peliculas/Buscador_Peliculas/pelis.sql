-- Crear tabla si no existe
IF OBJECT_ID('dbo.Peliculas', 'U') IS NULL
BEGIN
    CREATE TABLE Peliculas (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Titulo NVARCHAR(30) NOT NULL,
        Director NVARCHAR(30) NOT NULL,
        Year INT NOT NULL,
        Vista INT NOT NULL
    );
END
GO

-- Insertar 100 películas ficticias pero realistas
INSERT INTO Peliculas (Titulo, Director, Year, Vista)
VALUES
('El amanecer', 'Juan Pérez', 2001, 1),
('Sombras del pasado', 'María López', 1998, 0),
('Caminos cruzados', 'Pedro García', 2010, 1),
('La última canción', 'Ana Torres', 2005, 0),
('Horizonte perdido', 'Luis Fernández', 2018, 1),
('El secreto', 'Sofía Martínez', 2012, 0),
('Noche de fuego', 'Carlos Gómez', 2003, 1),
('Viento de otoño', 'Lucía Rodríguez', 1995, 0),
('Al filo del destino', 'Javier Sánchez', 2020, 1),
('Sombras y luces', 'Paula Díaz', 2016, 0),
('El guardián', 'Andrés Morales', 2007, 1),
('Tiempo de arena', 'Verónica Castillo', 2009, 0),
('La ciudad invisible', 'Miguel Romero', 2011, 1),
('Cenizas del ayer', 'Isabel Jiménez', 2014, 0),
('Destino incierto', 'Raúl Ortiz', 2019, 1),
('La puerta secreta', 'Natalia Herrera', 2002, 0),
('El viajero', 'Fernando Ruiz', 2006, 1),
('Ojos del pasado', 'Marina Vázquez', 2013, 0),
('Sombras en la noche', 'Diego Morales', 2000, 1),
('El último reflejo', 'Laura Ramírez', 2017, 0);

-- Para completar 100 filas, se puede copiar y variar los valores de Titulo, Director, Year y Vista
-- Por ejemplo, duplicando y cambiando números o años.
