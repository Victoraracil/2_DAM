-------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------
-- Creacion BD ALUMNOS

-- t5_alumnos_BD.sql

-------------------------------------------------------------------------------------------------------------------------------
-- TABLAS

DROP TABLE matriculado;
DROP TABLE alumnos;
DROP TABLE asignaturas;
DROP TABLE profesores;


CREATE TABLE alumnos (
    DNI VARCHAR2(20),
    nombre VARCHAR2(20),
    apellidos VARCHAR2(20),
    fecha_na DATE
);

CREATE TABLE asignaturas (
    nombre     VARCHAR2(10),
    profesor  VARCHAR2(20)
);

CREATE TABLE matriculado (
    alumno VARCHAR2(20),
    asignatura VARCHAR2(20),
    curso_academico NUMBER(4, 0) DEFAULT 2019,
    nota NUMBER(2, 0) DEFAULT 1
);

CREATE TABLE profesores (
    DNI VARCHAR2(20),
    nombre VARCHAR2(20),
    apellidos VARCHAR2(20)
);

-------------------------------------------------------------------------------------------------------------------------------
-- DATOS

INSERT INTO alumnos (DNI,nombre,apellidos,fecha_na) 
VALUES ('22345678L','JORDI','SANCHEZ',TO_TIMESTAMP('10/10/80 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'));
INSERT INTO alumnos (DNI,nombre,apellidos,fecha_na) 
VALUES ('44345675J','PACO','PEREZ',TO_TIMESTAMP('10/11/80 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'));
INSERT INTO alumnos (DNI,nombre,apellidos,fecha_na) 
VALUES ('22345676N','RAFA','ROMERO',TO_TIMESTAMP('01/12/81 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'));
INSERT INTO alumnos (DNI,nombre,apellidos,fecha_na) 
VALUES ('22345677M','JAVI','PONCE',TO_TIMESTAMP('12/01/82 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'));
INSERT INTO alumnos (DNI,nombre,apellidos,fecha_na) 
VALUES ('22345678B','MANOLI','ALVAREZ',TO_TIMESTAMP('10/02/81 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'));
INSERT INTO alumnos (DNI,nombre,apellidos,fecha_na) 
VALUES ('22356679F','MANUEL','PEREZ',TO_TIMESTAMP('13/03/80 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'));
INSERT INTO alumnos (DNI,nombre,apellidos,fecha_na) 
VALUES ('22378670S','ANA','SANCHEZ',TO_TIMESTAMP('10/10/83 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'));
INSERT INTO alumnos (DNI,nombre,apellidos,fecha_na) 
VALUES ('22312678W','ISABEL','PEREZ',TO_TIMESTAMP('15/04/86 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'));
INSERT INTO alumnos (DNI,nombre,apellidos,fecha_na) 
VALUES ('23245671Q','ESTEFANIA','SANCHEZ',TO_TIMESTAMP('10/10/85 00:00:00,000000000','DD/MM/RR HH24:MI:SS,FF'));

INSERT INTO asignaturas (nombre,profesor) VALUES ('DAC','44345678K');
INSERT INTO asignaturas (nombre,profesor) VALUES ('DEG','43456778H');
INSERT INTO asignaturas (nombre,profesor) VALUES ('FOL','42347498W');
INSERT INTO asignaturas (nombre,profesor) VALUES ('ADA','44312348J');
INSERT INTO asignaturas (nombre,profesor) VALUES ('PLE','44345678K');
INSERT INTO asignaturas (nombre,profesor) VALUES ('RAL',NULL);

INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('44345675J','DAC',2010,7);
INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('44345675J','DEG',2010,5);
INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('44345675J','ADA',2010,7);
INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('22345676N','DAC',2009,8);
INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('22345677M','FOL',2010,4);
INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('22345677M','DEG',2010,3);
INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('22345678B','FOL',2010,9);
INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('22345678B','DEG',2007,6);
INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('23245671Q','DAC',2010,9);
INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('23245671Q','DEG',2000,3);
INSERT INTO matriculado (alumno,asignatura,curso_academico,nota) values ('22312678W','FOL',2010,4);

INSERT INTO profesores (DNI,nombre,apellidos) values ('44345678K','ALVARO','PEREZ');
INSERT INTO profesores (DNI,nombre,apellidos) values ('43456778H','PEDR0','SANCHEZ');
INSERT INTO profesores (DNI,nombre,apellidos) values ('44312348J','MAITE','PEREZ');
INSERT INTO profesores (DNI,nombre,apellidos) values ('41345668L','PACO','ALMAGRO');
INSERT INTO profesores (DNI,nombre,apellidos) values ('42347498W','FERMINA','AGUILAR');

-------------------------------------------------------------------------------------------------------------------------------
-- CONSTRAINTS

ALTER TABLE profesores ADD CONSTRAINT pk_profesores PRIMARY KEY (DNI) ENABLE;
-- ALTER TABLE profesores MODIFY ("DNI" NOT NULL ENABLE);

ALTER TABLE alumnos ADD CONSTRAINT pk_alumnos PRIMARY KEY (DNI) ENABLE;
-- ALTER TABLE alumnos MODIFY (DNI NOT NULL ENABLE);

ALTER TABLE matriculado ADD CONSTRAINT pk_matriculado PRIMARY KEY (alumno, asignatura) ENABLE;
-- ALTER TABLE matriculado MODIFY (alumno NOT NULL ENABLE);
-- ALTER TABLE matriculado MODIFY (asignatura NOT NULL ENABLE);

ALTER TABLE asignaturas ADD CONSTRAINT pk_asignaturas PRIMARY KEY (nombre) ENABLE;
-- ALTER TABLE asignaturas MODIFY (nombre NOT NULL ENABLE);

ALTER TABLE asignaturas ADD CONSTRAINT fk_asignaturas_profesores FOREIGN KEY (profesor)
  REFERENCES profesores (DNI) ON DELETE CASCADE ENABLE;

ALTER TABLE matriculado ADD CONSTRAINT fk_matriculado_alumnos FOREIGN KEY (alumno)
  REFERENCES alumnos (DNI) ON DELETE CASCADE ENABLE;

ALTER TABLE matriculado ADD CONSTRAINT fk_matriculado_asignaturas FOREIGN KEY (asignatura)
  REFERENCES asignaturas (nombre) ON DELETE CASCADE ENABLE;
      
-------------------------------------------------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------------------------------------------------


