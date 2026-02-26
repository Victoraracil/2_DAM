-- 1.--
SELECT titulo, anio
FROM CONTENIDO
ORDER BY anio ASC;

-- 2.--
SELECT nick
FROM USUARIO
WHERE nick LIKE 'S%';

-- 3.--
SELECT titulo, anio
FROM CONTENIDO
WHERE tipo = 'Pelicula'
  AND genero LIKE '%Ficcion%'
  AND anio < 2020
ORDER BY anio DESC;

-- 4.--
SELECT nick, pais
FROM USUARIO
WHERE EXTRACT(YEAR FROM fecha_alta) = 2023;

-- 5.--
SELECT c.titulo, v.valoracion
FROM VISUALIZACION v
JOIN USUARIO u ON v.id_usuario = u.id_usuario
JOIN CONTENIDO c ON v.id_contenido = c.id_contenido
WHERE u.nick = 'Cinefilo88';

-- 6.--
SELECT c.titulo, AVG(v.valoracion) AS nota_media
FROM CONTENIDO c
JOIN VISUALIZACION v ON c.id_contenido = v.id_contenido
GROUP BY c.titulo
ORDER BY nota_media DESC;

-- 7.--
SELECT titulo
FROM CONTENIDO
WHERE tipo = 'Pelicula'
  AND precio_alquiler = (
        SELECT MAX(precio_alquiler)
        FROM CONTENIDO
        WHERE tipo = 'Pelicula'
  );

-- 8.--
SELECT u.nick
FROM USUARIO u
WHERE NOT EXISTS (
    SELECT c.id_contenido
    FROM CONTENIDO c
    MINUS
    SELECT v.id_contenido
    FROM VISUALIZACION v
    WHERE v.id_usuario = u.id_usuario
);

-- 9.--
SELECT DISTINCT u.nick
FROM USUARIO u
JOIN VISUALIZACION v ON u.id_usuario = v.id_usuario
JOIN CONTENIDO c ON v.id_contenido = c.id_contenido
WHERE c.genero = 'Terror'
MINUS
SELECT DISTINCT u.nick
FROM USUARIO u
JOIN VISUALIZACION v ON u.id_usuario = v.id_usuario
JOIN CONTENIDO c ON v.id_contenido = c.id_contenido
WHERE c.genero = 'Comedia';

-- 10.--
SELECT c.titulo, c.tipo
FROM CONTENIDO c
JOIN VISUALIZACION v ON c.id_contenido = v.id_contenido
GROUP BY c.titulo, c.tipo
HAVING COUNT(DISTINCT v.id_usuario) > 3;
