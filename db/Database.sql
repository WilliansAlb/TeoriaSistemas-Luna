CREATE DATABASE IF NOT EXISTS agenda_lunar;
USE agenda_lunar;
/*Drop tables*/
DROP TABLE IF EXISTS etiqueta_publicacion;
DROP TABLE IF EXISTS etiqueta;
DROP TABLE IF EXISTS comentario;
DROP TABLE IF EXISTS publicacion;
DROP TABLE IF EXISTS eventos;
DROP TABLE IF EXISTS siembra;
DROP TABLE IF EXISTS cultivo;
DROP TABLE IF EXISTS lugar;
DROP TABLE IF EXISTS usuario;

/*usuario*/
DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario (
    nombreusuario varchar(255) NOT NULL,
    nombre varchar(255) NOT NULL,
    contrasenia varchar(255) NOT NULL,
    tipo tinyint NOT NULL,
    PRIMARY KEY (nombreusuario)
);

/*cultivo*/
DROP TABLE IF EXISTS cultivo;
CREATE TABLE cultivo(
    id int NOT NULL AUTO_INCREMENT,
    tipo varchar(255),
    PRIMARY KEY (id)   
);

/*lugar*/
DROP TABLE IF EXISTS lugar;
CREATE TABLE lugar(
    id int NOT NULL AUTO_INCREMENT,
    nombre varchar(255) NOT NULL,
    ubicacion varchar(255) NOT NULL,
    clima varchar(255) NOT NULL,
    id_usuario varchar(255) NOT NULL,    
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(nombreusuario)
);

/*siembra*/
DROP TABLE IF EXISTS siembra;
CREATE TABLE siembra(
    id int NOT NULL AUTO_INCREMENT,
    id_lugar int NOT NULL,    
    id_cultivo int NOT NULL,
    fechaSiembra DATE NOT NULL,
    cosechado BOOLEAN NOT NULL,
    nombre varchar(255) NOT NULL,
    id_usuario varchar(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_lugar) REFERENCES lugar(id),
    FOREIGN KEY (id_cultivo) REFERENCES cultivo(id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(nombreusuario)
);

/*eventos*/
DROP TABLE IF EXISTS eventos;
CREATE TABLE eventos(
    id int NOT NULL AUTO_INCREMENT,
    id_usuario varchar(255) NOT NULL,
    id_siembra int ,
    nombre varchar(255) NOT NULL,
    fechaEvento DATE NOT NULL,
    descripcion varchar(5000),
    tipo varchar(255) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(nombreusuario),
    FOREIGN KEY (id_siembra) REFERENCES siembra(id)
);

/*etiqueta*/
DROP TABLE IF EXISTS etiqueta;
CREATE TABLE etiqueta(
    id int NOT NULL AUTO_INCREMENT,
    nombre varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

/*publicacion*/
DROP TABLE IF EXISTS publicacion;
CREATE TABLE publicacion(
    id int NOT NULL AUTO_INCREMENT,
    id_usuario varchar(255) NOT NULL,
    contenido varchar(5000),
    fecha_publicacion DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(nombreusuario)
);

/*comentario*/
DROP TABLE IF EXISTS comentario;
CREATE TABLE comentario(
    id int NOT NULL AUTO_INCREMENT,
    id_usuario varchar(255) NOT NULL,
    id_publicacion int NOT NULL,
    comentario varchar(2500) NOT NULL,
    fecha DATE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_usuario) REFERENCES usuario(nombreusuario),
    FOREIGN KEY (id_publicacion) REFERENCES publicacion(id)
);

/*etiqueta_publicacion*/
DROP TABLE IF EXISTS etiqueta_publicacion;
CREATE TABLE etiqueta_publicacion(
    id int NOT NULL AUTO_INCREMENT,
    id_etiqueta int NOT NULL,
    id_publicacion int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_etiqueta) REFERENCES etiqueta(id),
    FOREIGN KEY (id_publicacion) REFERENCES publicacion(id)
);

/*plugin

DROP TABLE IF EXISTS plugin;
CREATE TABLE plugin(
    id int NOT NULL AUTO_INCREMENT,
    id_tipo_proyecto int NOT NULL,
    id_siembra int NOT NULL,
    nombre varchar(255) NOT NULL,
    fechaEvento DATE NOT NULL,
    descripcion varchar(5000),
    tipo varchar(255),
    PRIMARY KEY (id),
    FOREIGN KEY (id_tipo_proyecto) REFERENCES proyecto(id),
    FOREIGN KEY (id_siembra) REFERENCES siembra(id)
);

/*tipoEvento
DROP TABLE IF EXISTS tipoEvento;
CREATE TABLE tipoEvento(
    id int NOT NULL AUTO_INCREMENT,
    id_tipo_proyecto int NOT NULL,
    id_siembra int NOT NULL,
    nombre varchar(255) NOT NULL,
    fechaEvento DATE NOT NULL,
    descripcion varchar(5000),
    tipo varchar(255),
    PRIMARY KEY (id),
    FOREIGN KEY (id_tipo_proyecto) REFERENCES proyecto(id),
    FOREIGN KEY (id_siembra) REFERENCES siembra(id)
);
*/
