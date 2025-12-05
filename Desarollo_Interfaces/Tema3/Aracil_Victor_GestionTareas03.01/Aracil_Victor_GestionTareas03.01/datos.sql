-- ============================================
-- CREACIÓN DE USUARIOS
-- ============================================
INSERT INTO Users (Usuario, PasswordHash, NombreCompleto, CorreoElectronico, Activo, FechaCreacion)
VALUES 
('victor', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'Victor Aracil', 'victor@example.com', 1, '2025-12-05'),
('ana', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'Ana Arenas', 'ana@example.com', 1, '2025-12-05'),
('miguel', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'Miguel Torregrosa', 'miguel@example.com', 1, '2025-12-05'),
('izan', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'Izan Marcos', 'izan@example.com', 1, '2025-12-05'),
('alberto', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'Alberto González', 'alberto@example.com', 1, '2025-12-05'),
('laura', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'Laura Pérez', 'laura@example.com', 1, '2025-12-05'),
('javier', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'Javier López', 'javier@example.com', 1, '2025-12-05'),
('sofia', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'Sofía Martínez', 'sofia@example.com', 1, '2025-12-05'),
('carlos', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'Carlos Fernández', 'carlos@example.com', 1, '2025-12-05'),
('maria', '03AC674216F3E15C761EE1A5E255F067953623C8B388B4459E13F978D7C846F4', 'María Gómez', 'maria@example.com', 1, '2025-12-05');

-- ============================================
-- CREACIÓN DE ETIQUETAS
-- ============================================
INSERT INTO Etiquetas (Nombre)
VALUES
('Urgente'),
('Importante'),
('Opcional'),
('Personal'),
('Trabajo'),
('Estudio'),
('Hogar'),
('Proyecto'),
('Revisión'),
('Extra');

-- ============================================
-- CREACIÓN DE TAREAS
-- ============================================
INSERT INTO Tareas (Titulo, Descripcion, Color, Vencimiento, Completado, PorcentajeCompletado, Estado, FechaCreacion, UserId, EtiquetaId)
VALUES
('Comprar materiales', 'Comprar impresora 3D y filamento', 2, '2025-12-10', 0, 0, 0, '2025-12-05', 1, 1),
('Configurar servidor', 'Instalar y configurar servidor local', 3, '2025-12-15', 0, 20, 1, '2025-12-05', 1, 2),
('Diseñar logo', 'Crear logo para proyecto', 4, '2025-12-12', 0, 0, 0, '2025-12-05', 2, 3),
('Escribir documentación', 'Documentar la práctica de C#', 1, '2025-12-14', 0, 50, 1, '2025-12-05', 2, 4),
('Revisión final', 'Revisar todas las tareas antes de entregar', 2, '2025-12-20', 0, 0, 0, '2025-12-05', 3, 5),
('Actualizar base de datos', 'Aplicar migraciones y pruebas', 3, '2025-12-18', 0, 10, 1, '2025-12-05', 3, 6),
('Preparar presentación', 'Diapositivas para la reunión', 4, '2025-12-19', 0, 0, 0, '2025-12-05', 4, 7),
('Enviar correos', 'Enviar correos a clientes', 1, '2025-12-13', 0, 100, 2, '2025-12-05', 4, 8),
('Comprar alimentos', 'Lista de supermercado semanal', 2, '2025-12-11', 0, 30, 1, '2025-12-05', 5, 9),
('Limpieza general', 'Limpiar toda la oficina', 3, '2025-12-17', 0, 0, 0, '2025-12-05', 5, 10);

