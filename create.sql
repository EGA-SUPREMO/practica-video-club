CREATE DATABASE video_club;
USE video_club;

CREATE TABLE actor (
    id INT PRIMARY KEY,
    nombre VARCHAR(255)
);
CREATE TABLE director (
    id INT PRIMARY KEY,
    nombre VARCHAR(255)
);
CREATE TABLE genero (
    id INT PRIMARY KEY,
    nombre VARCHAR(255)
);
CREATE TABLE pelicula (
    id INT PRIMARY KEY,
    titulo VARCHAR(255),
    id_director INT,
    id_genero INT,
    FOREIGN KEY (id_genero) REFERENCES genero(id),
    FOREIGN KEY (id_director) REFERENCES director(id)
);
CREATE TABLE socio (
    id INT PRIMARY KEY,
    saldo INT DEFAULT 900,
    nombre VARCHAR(255),
    direccion VARCHAR(255),
    telefono CHAR(11)
);

CREATE TABLE socio_actor_favorito (
    id INT PRIMARY KEY,
    id_actor INT,
    id_socio INT,
    FOREIGN KEY (id_actor) REFERENCES actor(id),
    FOREIGN KEY (id_socio) REFERENCES socio(id) ON DELETE CASCADE
);

CREATE TABLE socio_director_favorito (
    id INT PRIMARY KEY,
    id_director INT,
    id_socio INT,
    FOREIGN KEY (id_director) REFERENCES director(id),
    FOREIGN KEY (id_socio) REFERENCES socio(id) ON DELETE CASCADE
);

CREATE TABLE socio_genero_favorito (
    id INT PRIMARY KEY,
    id_genero INT,
    id_socio INT,
    FOREIGN KEY (id_genero) REFERENCES genero(id),
    FOREIGN KEY (id_socio) REFERENCES socio(id) ON DELETE CASCADE
);

CREATE TABLE actores_en_peliculas (
    id INT PRIMARY KEY,
    id_actor INT,
    id_pelicula INT,
    FOREIGN KEY(id_pelicula) REFERENCES pelicula(id),
    FOREIGN KEY(id_actor) REFERENCES actor(id)
);

CREATE TABLE cinta (
    id INT PRIMARY KEY,
    id_pelicula INT,
    FOREIGN KEY (id_pelicula) REFERENCES pelicula(id) ON DELETE CASCADE
);

CREATE TABLE lista_espera (
    id INT PRIMARY KEY,
    id_pelicula INT,
    id_socio INT,
    fecha DATETIME,
    FOREIGN KEY (id_pelicula) REFERENCES pelicula(id) ON DELETE CASCADE,
    FOREIGN KEY (id_socio) REFERENCES socio(id) ON DELETE CASCADE
);

CREATE TABLE prestamo (
    id INT PRIMARY KEY,
    id_socio INT,
    fecha_prestada DATETIME,
    fecha_devuelta DATETIME,
    FOREIGN KEY (id_socio) REFERENCES socio(id) ON DELETE CASCADE
);

CREATE TABLE prestamo_cinta (
    id INT PRIMARY KEY,
    id_cinta INT,
    id_prestamo INT,
    FOREIGN KEY (id_cinta) REFERENCES cinta(id) ON DELETE CASCADE,
    FOREIGN KEY (id_prestamo) REFERENCES prestamo(id) ON DELETE CASCADE
);

CREATE USER 'trabajo'@'localhost' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON video_club.* TO 'trabajo'@'localhost';
