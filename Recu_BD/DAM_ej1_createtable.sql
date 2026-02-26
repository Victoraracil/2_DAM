--BORRADO DE OBJETOS PREVIOS
DROP TABLE MONITOR CASCADE CONSTRAINTS;
DROP TABLE SOCIO CASCADE CONSTRAINTS;
DROP TABLE SESION CASCADE CONSTRAINTS;

--CREACIÓN DE TABLAS
--Tabla MONITOR
CREATE TABLE MONITOR (
    id_monitor NUMBER GENERATED ALWAYS AS IDENTITY,
    dni VARCHAR2(9) NOT NULL UNIQUE,
    nombre VARCHAR2(50) NOT NULL,
    salario_base NUMBER(7,2),
    
    CONSTRAINT pk_monitor PRIMARY KEY (id_monitor),
    CONSTRAINT chk_salario CHECK (salario_base BETWEEN 950 AND 3500)
);


-- Tabla SOCIO
CREATE TABLE SOCIO (
    id_socio NUMBER,
    nombre VARCHAR2(50) NOT NULL,
    plan_pago VARCHAR2(20),
    fecha_registro DATE DEFAULT SYSDATE,
    
    CONSTRAINT pk_socio PRIMARY KEY (id_socio),
    CONSTRAINT chk_plan_pago CHECK (plan_pago IN ('MENSUAL','TRIMESTRAL','ANUAL'))
);


-- Tabla SESION
CREATE TABLE SESION (
    id_monitor NUMBER,
    id_socio NUMBER,
    fecha DATE,
    duracion NUMBER(4),
    estado VARCHAR2(20) DEFAULT 'PENDIENTE',
    
    CONSTRAINT pk_sesion PRIMARY KEY (id_monitor, id_socio, fecha),
    
    CONSTRAINT fk_sesion_monitor 
        FOREIGN KEY (id_monitor) 
        REFERENCES MONITOR(id_monitor)
        ON DELETE CASCADE,
        
    CONSTRAINT fk_sesion_socio 
        FOREIGN KEY (id_socio) 
        REFERENCES SOCIO(id_socio)
);

-- INDICE
CREATE INDEX idx_monitor_nombre
ON MONITOR(nombre);


