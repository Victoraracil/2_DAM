-------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------
-- t5_consultas_concesionario.sql

-- Tema 5 - Tarea evaluable concesionario - RESUELTO

-------------------------------------------------------------------------------------------------------------------------------
-- 1.- Obtener todos los códigos de los coches cuyo nombre empiece por "C"

SELECT CODIGO 
FROM COCHE 
WHERE NOMBRE LIKE 'C%';

--SELECT * FROM coche WHERE UPPER(nombre) LIKE 'C%';


-------------------------------------------------------------------------------------------------------------------------------
-- 2.- Obtener el CIF de todos los concesionarios cuyo atributo cantidad en la tabla de distribución está
-- comprendido entre 10 y 18, ambos inclusive

SELECT DISTINCT CIF_CONCESIONARIO 
FROM DISTRIBUCION 
WHERE CANTIDAD BETWEEN 10 AND 18;

-------------------------------------------------------------------------------------------------------------------------------
-- 3.- Obtener los códigos de los coches suministrados por algún concesionario de 'BARCELONA'
SELECT D.CODIGO_COCHE 
FROM DISTRIBUCION D, CONCESIONARIO C
WHERE D.CIF_CONCESIONARIO = C.CIF
AND C.CIUDAD = 'Barcelona' 
ORDER BY D.CODIGO_COCHE;

-- CON INNER JOIN
SELECT D.CODIGO_COCHE 
FROM DISTRIBUCION D
INNER JOIN CONCESIONARIO C ON D.CIF_CONCESIONARIO = C.CIF
WHERE C.CIUDAD = 'Barcelona'
ORDER BY D.CODIGO_COCHE;

-------------------------------------------------------------------------------------------------------------------------------
-- 4.- Obtener los códigos de los coches vendidos a clientes de 'MADRID'

-- Los códigos de coche pueden estar repetidos porque un cliente de madrid comprara el mismo modelo en 
-- diferentes concesionarios
SELECT DISTINCT V.CODIGO_COCHE
FROM VENTAS V , CLIENTE C
WHERE C.DNI=V.DNI_CLIENTE
AND C.CIUDAD='Madrid'; 


-- CON INNER JOIN
SELECT DISTINCT v.codigo_coche FROM ventas v
INNER JOIN cliente c ON c.dni = v.dni_cliente
WHERE c.ciudad = 'Madrid' -- SE PUEDE USAR AND EN VEZ DE WHERE
ORDER BY v.codigo_coche;

-------------------------------------------------------------------------------------------------------------------------------
-- 5.- Obtener los códigos de los coches comprados en un concesionario de la misma ciudad que el cliente que lo compra
SELECT CODIGO_COCHE 
FROM VENTAS V,CLIENTE CL,CONCESIONARIO CO
WHERE CO.CIUDAD=CL.CIUDAD
AND CO.CIF=V.CIF_CONCESIONARIO
AND CL.DNI=V.DNI_CLIENTE
ORDER BY V.CODIGO_COCHE;

SELECT v.codigo_coche FROM ventas v
INNER JOIN concesionario co ON co.CIF = v.CIF_concesionario
INNER JOIN cliente cl ON cl.dni = v.DNI_cliente
WHERE cl.ciudad = co.ciudad ORDER BY v.codigo_coche;

SELECT DISTINCT v.codigo_coche FROM ventas v
INNER JOIN concesionario co ON co.CIF = v.CIF_concesionario
INNER JOIN cliente cl ON cl.dni = v.DNI_cliente AND cl.ciudad = co.ciudad
ORDER BY v.codigo_coche;


-------------------------------------------------------------------------------------------------------------------------------
-- 6.- Obtener el nombre y el apellido de los clientes que han adquirido un automóvil a un concesionario
-- que posea actualmente coches en stock del modelo 'NISSAN'

SELECT DISTINCT cl.nombre || ' ' || cl.apellidos AS nombre_cliente 
FROM ventas v INNER JOIN cliente cl ON cl.DNI = v.DNI_cliente
              INNER JOIN distribucion d ON d.CIF_concesionario = v.CIF_concesionario
              INNER JOIN coche co ON co.codigo = d.codigo_coche AND co.modelo = 'NISSAN'
              AND D.CANTIDAD>0
ORDER BY nombre_cliente;


-------------------------------------------------------------------------------------------------------------------------------
-- 7.- Obtener el nombre y el apellido de los clientes cuyo número de DNI es menor que el
--del cliente 'Manuel Ivorra'

-- si solo hay un cliente con ese nombre-apellido
SELECT nombre, apellidos FROM cliente 
WHERE DNI < (SELECT DNI FROM cliente WHERE nombre = 'Manuel' AND apellidos = 'Ivorra')
ORDER BY nombre, apellidos;

-- si hay mas de un cliente que tenga ese nombre-apellido
SELECT nombre, apellidos FROM cliente 
WHERE dni < ALL (SELECT DNI FROM cliente WHERE nombre = 'Manuel' AND apellidos = 'Ivorra')
ORDER BY nombre, apellidos;

-- si hay mas de un cliente que tenga ese nombre-apellido, otra opción
SELECT nombre, apellidos FROM cliente 
WHERE dni < (SELECT MIN(DNI) FROM cliente WHERE nombre = 'Manuel' AND apellidos = 'Ivorra')
ORDER BY nombre, apellidos;


-- utilizando funciones de caracteres
--SELECT nombre || ' ' || apellidos AS nombre_cliente FROM cliente 
--WHERE DNI < (SELECT DNI FROM cliente WHERE 'Manuel Ivorra' = (nombre || ' ' || SUBSTR(apellidos, 1, INSTR(apellidos, ' ') - 1)))
--ORDER BY nombre_cliente;


-------------------------------------------------------------------------------------------------------------------------------
-- 8.- Obtener el nombre y el apellido de los clientes cuyo nombre empieza por "A" y cuyo número de DNI
--es mayor que el de todos los clientes que son de "MADRID" o menor que el de todos los de "VALENCIA"

SELECT nombre,apellidos FROM cliente
WHERE nombre LIKE 'A%' AND
( DNI > ALL (SELECT DNI 
             FROM cliente
             WHERE ciudad = 'Madrid') OR DNI < ALL (SELECT DNI 
                                                    FROM cliente
                                                    WHERE ciudad = 'Valencia'));


-------------------------------------------------------------------------------------------------------------------------------
-- 9.- Utilizando EXISTS, obtener el DNI de los clientes que hayan adquirido por lo menos 
-- alguno de los coches que haya sido vendido por el concesionario cuyo CIF es "85543123"

SELECT DNI FROM cliente c
WHERE EXISTS(SELECT * 
             FROM ventas v
             WHERE v.CIF_concesionario = '85543123' AND v.DNI_cliente = c.DNI)
ORDER BY DNI;

SELECT DISTINCT v1.DNI_cliente AS DNI 
FROM ventas v1
WHERE EXISTS(SELECT * 
             FROM ventas v2
             WHERE v2.CIF_concesionario = '85543123' 
             AND v1.DNI_cliente = v2.DNI_cliente)
ORDER BY DNI;


-------------------------------------------------------------------------------------------------------------------------------
-- 10.- Obtener DNI de los clientes cuya ciudad sea la última de la lista alfabética de
--las ciudades donde hay concesionarios.

SELECT DNI 
FROM cliente
WHERE ciudad = (SELECT MAX(ciudad) 
                FROM concesionario);


-------------------------------------------------------------------------------------------------------------------------------
-- 11.- Obtener el nombre y el apellido de los clientes que han comprado como mínimo un coche blanco
--y un coche rojo

SELECT DISTINCT c.nombre, c.apellidos FROM cliente c
INNER JOIN ventas v1 ON c.DNI = v1.DNI_cliente AND v1.color = 'blanco'
INNER JOIN ventas v2 ON v2.DNI_cliente = v1.DNI_cliente AND v2.color = 'rojo';

SELECT DISTINCT c.nombre, c.apellidos FROM cliente c
WHERE 
    c.DNI IN (SELECT DNI_cliente FROM ventas WHERE color = 'blanco') AND
    c.DNI IN (SELECT DNI_cliente FROM ventas WHERE color = 'rojo');

SELECT DISTINCT c.nombre, c.apellidos FROM cliente c
WHERE 
    EXISTS (SELECT * FROM ventas WHERE color = 'blanco' AND dni_cliente = c.DNI) AND
    EXISTS (SELECT * FROM ventas WHERE color = 'rojo' AND dni_cliente = c.DNI);


-------------------------------------------------------------------------------------------------------------------------------
-- 12.- Obtener la media de los automóviles que cada concesionario tiene actualmente en stock

SELECT CIF_concesionario, ROUND(AVG(cantidad),2) AS media_vehiculos
FROM distribucion 
GROUP BY cif_concesionario
ORDER BY CIF_concesionario;


-------------------------------------------------------------------------------------------------------------------------------
-- 13.- Obtener el cif del concesionario que no sea de "MADRID" cuya media de vehículos en stock sea 
-- la mas alta de todas las medias.


            SELECT c.cif,c.nombre
            FROM distribucion d,concesionario c
            WHERE c.cif=d.cif_concesionario
            and c.ciudad !='Madrid'
            GROUP BY c.cif,c.nombre
            HAVING AVG(cantidad) >= (SELECT MAX(AVG(cantidad))
                                    FROM distribucion d,concesionario c
                                    WHERE c.cif=d.cif_concesionario
                                    and c.ciudad !='Madrid'
                                    GROUP BY CIF_concesionario);


-------------------------------------------------------------------------------------------------------------------------------
-- 14.- Número de coches vendidos por cada concesionario, mostrando el CIF y la cantidad de unidades 
-- vendidas en orden decreciente.

SELECT CIF_concesionario, COUNT(*) AS unidades_vendidas 
FROM ventas
GROUP BY CIF_concesionario
ORDER BY unidades_vendidas DESC;


-------------------------------------------------------------------------------------------------------------------------------
-- 15.- DNI de los clientes que hayan comprado más de 1 coche

SELECT DNI_cliente AS DNI, COUNT(*) unidades_compradas 
FROM VENTAS
GROUP BY dni_cliente
HAVING COUNT(*) > 1
ORDER BY unidades_compradas DESC, DNI;
-- 15.2- DNI de los clientes que hayan comprado más de 1 coche al mismo concesionario
SELECT DNI_cliente AS DNI,cif_concesionario as CONCESIONARIO, COUNT(*) unidades_compradas 
FROM VENTAS
GROUP BY dni_cliente,cif_concesionario
HAVING COUNT(*) > 1
ORDER BY DNI;



-------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------