-- Crear tabla Categorias si no existe
IF OBJECT_ID('dbo.Categorias', 'U') IS NULL
BEGIN
    CREATE TABLE Categorias (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Clasificacion NVARCHAR(30) NOT NULL
    );
END
GO


IF OBJECT_ID('dbo.Clientes', 'U') IS NULL
BEGIN
    CREATE TABLE Clientes (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Usuario NVARCHAR(30) NOT NULL,
        NombreCompleto NVARCHAR(100) NOT NULL,
        CorreoElectronico NVARCHAR(100) NOT NULL,
        Activo INT NOT NULL,
        FechaCreacion DATETIME2 NOT NULL,
        FechaBaja DATETIME2 NOT NULL
    );
END
GO


IF OBJECT_ID('dbo.ClienteCategoria', 'U') IS NULL
BEGIN
    CREATE TABLE ClienteCategoria (
        CategoriasId INT NOT NULL,
        ClientesId INT NOT NULL,
        CONSTRAINT PK_ClienteCategoria PRIMARY KEY (CategoriasId, ClientesId),
        CONSTRAINT FK_ClienteCategoria_Categorias FOREIGN KEY (CategoriasId) REFERENCES Categorias(Id) ON DELETE CASCADE,
        CONSTRAINT FK_ClienteCategoria_Clientes FOREIGN KEY (ClientesId) REFERENCES Clientes(Id) ON DELETE CASCADE
    );
END
GO


IF OBJECT_ID('dbo.Peliculas', 'U') IS NULL
BEGIN
    CREATE TABLE Peliculas (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Titulo NVARCHAR(30) NOT NULL,
        Director NVARCHAR(30) NOT NULL,
        Year INT NOT NULL,
        ClienteId INT NULL,
        CategoriaId INT NOT NULL,
        CONSTRAINT FK_Peliculas_Categorias_CategoriaId FOREIGN KEY (CategoriaId) REFERENCES Categorias(Id) ON DELETE CASCADE,
        CONSTRAINT FK_Peliculas_Clientes_ClienteId FOREIGN KEY (ClienteId) REFERENCES Clientes(Id)
    );
END
GO

------------------------------------------------------------
-- Inserciones de ejemplo
------------------------------------------------------------

INSERT INTO Clientes (Usuario, NombreCompleto, CorreoElectronico, Activo, FechaCreacion, FechaBaja) VALUES
('Disponible', 'Disponible', 'disponible@videoclub.local', 1, '2025-01-01T00:00:00', '0001-01-01T00:00:00'),
('laura.gomez', 'Laura Gómez', 'laura.gomez@example.com', 1, '2024-06-12T10:15:00', '0001-01-01T00:00:00'),
('carlos.ruiz', 'Carlos Ruiz', 'carlos.ruiz@example.com', 1, '2023-11-01T09:00:00', '0001-01-01T00:00:00'),
('maria.fernandez', 'María Fernández', 'maria.fernandez@example.com', 1, '2022-02-20T14:30:00', '0001-01-01T00:00:00'),
('javier.santos', 'Javier Santos', 'javier.santos@example.com', 1, '2024-01-05T12:00:00', '0001-01-01T00:00:00'),
('lucia.martin', 'Lucía Martín', 'lucia.martin@example.com', 1, '2021-08-18T08:45:00', '0001-01-01T00:00:00');
GO

------------------------------------------------------------
-- Categorías (6)
------------------------------------------------------------
INSERT INTO Categorias (Clasificacion) VALUES
('Acción'),
('Comedia'),
('Drama'),
('Ciencia Ficción'),
('Terror'),
('Aventura');
GO

------------------------------------------------------------
-- Películas (30 registros)
-- Nota: ClienteId = 1 => "Disponible" (libre)
--       ClienteId > 1 => alquilada por ese Cliente
--       CategoriaId entre 1 y 6 según las inserciones anteriores
------------------------------------------------------------
INSERT INTO Peliculas (Titulo, Director, Year, ClienteId, CategoriaId) VALUES
('El Último Héroe', 'John McTiernan', 1999, 2, 1),
('Risas a Medianoche', 'Todd Phillips', 2018, 1, 2),
('Corazones Rotos', 'Greta Gerwig', 2020, 3, 3),
('Misión Galáctica', 'James Cameron', 2015, 1, 4),
('El Grito Final', 'Jordan Peele', 2019, 4, 5),
('Aventuras en la Isla', 'Steven Spielberg', 2001, 1, 6),
('El Último Aliento', 'Ridley Scott', 2017, 5, 4),
('Humor sin Fronteras', 'Taika Waititi', 2021, 1, 2),
('La Sombra del Pasado', 'Christopher Nolan', 2006, 6, 3),
('Horizonte de Acero', 'Neill Blomkamp', 2010, 1, 4),
('La Noche del Lobo', 'Ari Aster', 2022, 2, 5),
('Exploradores del Futuro', 'Denis Villeneuve', 2019, 1, 4),
('Golpe Perfecto', 'Guy Ritchie', 2023, 3, 1),
('Cita a Ciegas', 'Nancy Meyers', 2003, 1, 2),
('Sueños Rotos', 'Damien Chazelle', 2016, 1, 3),
('El Abismo', 'James Cameron', 1989, 4, 4),
('Risas Mortales', 'Jordan Peele', 2021, 1, 2),
('Día de Furia', 'Tony Scott', 1998, 5, 1),
('Caminos Cruzados', 'Richard Linklater', 2014, 1, 3),
('Más Allá del Miedo', 'David Fincher', 2020, 6, 5),
('El Reino Perdido', 'Peter Jackson', 2002, 1, 6),
('La Máquina del Tiempo', 'Robert Zemeckis', 1990, 1, 4),
('Comedia Urbana', 'Edgar Wright', 2017, 2, 2),
('Tragedia Familiar', 'Sofia Coppola', 2005, 3, 3),
('Códigos del Crimen', 'Martin Scorsese', 2019, 4, 1),
('La Oscuridad', 'James Wan', 2018, 1, 5),
('Aventuras del Norte', 'Ron Howard', 2011, 1, 6),
('Robo en el Espacio', 'Christopher Nolan', 2014, 1, 4),
('La Risa Prohibida', 'Adam McKay', 2022, 1, 2),
('Sombras del Amor', 'Greta Gerwig', 2023, 1, 3);
GO

------------------------------------------------------------
-- Relaciones Cliente - Categoria (ejemplo de preferencias)
-- Nota: columnas en la tabla intermedia son (CategoriasId, ClientesId)
------------------------------------------------------------
INSERT INTO ClienteCategoria (CategoriasId, ClientesId) VALUES
(1, 2), (2, 2),    -- Laura: Acción, Comedia
(3, 3), (4, 3),    -- Carlos: Drama, Ciencia Ficción
(5, 4), (6, 4),    -- María: Terror, Aventura
(1, 5), (4, 5),    -- Javier: Acción, Ciencia Ficción
(2, 6), (3, 6);    -- Lucía: Comedia, Drama
GO

