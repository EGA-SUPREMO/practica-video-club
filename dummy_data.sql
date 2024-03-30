INSERT INTO actor (id, nombre) 
VALUES 
(1, 'Brad Pitt'),
(2, 'Angelina Jolie'),
(3, 'Leonardo DiCaprio'),
(4, 'Jennifer Lawrence'),
(5, 'Tom Hanks'),
(6, 'Meryl Streep'),
(7, 'Robert Downey Jr.'),
(8, 'Emma Stone'),
(9, 'Denzel Washington'),
(10, 'Scarlett Johansson');

INSERT INTO genero (id, nombre) 
VALUES 
(1, 'Accion'),
(2, 'Comedia'),
(3, 'Drama'),
(4, 'Thriller'),
(5, 'Documental'),
(6, 'Romance'),
(7, 'Horror'),
(8, 'Adventura'),
(9, 'Fantasia'),
(10, 'Anime');

INSERT INTO director (id, nombre) 
VALUES 
(1, 'Christopher Nolan'),
(2, 'Quentin Tarantino'),
(3, 'Steven Spielberg'),
(4, 'Martin Scorsese'),
(5, 'James Cameron'),
(6, 'David Fincher'),
(7, 'Alfred Hitchcock'),
(8, 'Tim Burton'),
(9, 'Francis Ford Coppola'),
(10, 'Stanley Kubrick');

INSERT INTO socio (id, nombre, direccion, telefono) 
VALUES 
(1, 'Carlos Herrera', 'Calle 123', '4124337480'),
(2, 'María Sanchez', 'Calle 123', '4124337480'),
(3, 'Ana Lopez', 'Calle 123', '4124337480'),
(4, 'Laura Gomez', 'Calle 123', '4124337480'),
(5, 'Pedro Diaz', 'Calle 123', '4124337480'),
(6, 'Isabel Ruiz', 'Calle 123', '4124337480'),
(7, 'Daniel Garcia', 'Calle 123', '4124337480'),
(8, 'Sofia Martinez', '4124337480', '4124337480'),
(9, 'Miguel Gonzales', 'Calle 123', '4124337480'),
(10, 'Luis Rodrigez', 'Calle 123', '4124337480');

INSERT INTO pelicula (id, titulo, id_director, id_genero) 
VALUES 
(1, 'Inception', 1, 5),
(2, 'Pulp Fiction', 2, 4),
(3, 'Silent words', 3, 3),
(4, 'The Godfather', 4, 3),
(5, 'Avatar', 5, 8),
(6, 'Fight Club', 6, 4),
(7, 'Psycho', 7, 7),
(8, 'Edward Scissorhands', 8, 6),
(9, 'The Shawshank Redemption', 3, 3),
(10, 'The Dark Knight', 1, 1);


-- --

INSERT INTO actores_en_peliculas (id, id_actor, id_pelicula) 
VALUES 
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 8),
(9, 9, 9),
(10, 10, 10),
(11, 1, 2),
(12, 2, 3),
(13, 3, 4),
(14, 4, 5),
(15, 5, 6),
(16, 6, 7),
(17, 7, 8),
(18, 8, 9),
(19, 9, 10),
(20, 10, 1);

INSERT INTO cinta (id, id_pelicula) 
VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10),
(11, 1),
(12, 2),
(13, 3),
(14, 4),
(15, 5),
(16, 6),
(17, 7),
(18, 8),
(19, 9),
(20, 10);

INSERT INTO lista_espera (id, id_pelicula, id_socio, fecha) 
VALUES 
(1, 1, 1, '2024-02-01'),
(2, 2, 2, '2024-02-02'),
(3, 3, 3, '2024-02-03'),
(4, 4, 4, '2024-02-04'),
(5, 5, 5, '2024-02-05'),
(6, 6, 6, '2024-02-06'),
(7, 7, 7, '2024-02-07'),
(8, 8, 8, '2024-02-08'),
(9, 9, 9, '2024-02-09'),
(10, 10, 10, '2024-02-10'),
(11, 1, 2, '2024-02-11'),
(12, 2, 3, '2024-02-12'),
(13, 3, 4, '2024-02-13'),
(14, 4, 5, '2024-02-14'),
(15, 5, 6, '2024-02-15'),
(16, 6, 7, '2024-02-16'),
(17, 7, 8, '2024-02-17'),
(18, 8, 9, '2024-02-18'),
(19, 9, 10, '2024-02-19'),
(20, 10, 1, '2024-02-20');

INSERT INTO prestamo (id, id_socio, fecha_prestada, fecha_devuelta) 
VALUES 
(1, 1, '2024-01-01', '2024-01-10'),
(2, 2, '2024-01-02', '2024-01-11'),
(3, 3, '2024-01-03', '2024-01-12'),
(4, 4, '2024-01-04', '2024-01-13'),
(5, 5, '2024-01-05', '2024-01-14'),
(6, 6, '2024-01-06', '2024-01-15'),
(7, 7, '2024-01-07', '2024-01-16'),
(8, 8, '2024-01-08', '2024-01-17'),
(9, 9, '2024-01-09', '2024-01-18'),
(10, 10, '2024-01-10', '2024-01-19'),
(11, 1, '2024-01-11', '2024-01-20'),
(12, 2, '2024-01-12', '2024-01-21'),
(13, 3, '2024-01-13', '2024-01-22'),
(14, 4, '2024-01-14', '2024-01-23'),
(15, 5, '2024-01-15', '2024-01-24'),
(16, 6, '2024-01-16', '2024-01-25'),
(17, 7, '2024-01-17', '2024-01-26'),
(18, 8, '2024-01-18', '2024-01-27'),
(19, 9, '2024-01-19', '2024-01-28'),
(20, 10, '2024-01-20', '2024-01-29'),
(21, 1, '2024-02-14', '2024-02-23');

INSERT INTO prestamo (id, id_socio, fecha_prestada) 
VALUES 
(22, 1, '2024-01-11'),
(23, 2, '2024-01-12'),
(24, 3, '2024-01-13'),
(25, 4, '2024-01-15'),
(26, 5, '2024-01-15');

INSERT INTO prestamo_cinta (id, id_cinta, id_prestamo) 
VALUES 
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 8),
(9, 9, 9),
(10, 10, 10),
(11, 1, 11),
(12, 2, 12),
(13, 3, 13),
(14, 4, 14),
(15, 5, 15),
(16, 6, 16),
(17, 7, 17),
(18, 8, 18),
(19, 9, 19),
(20, 10, 20),
(21, 1, 21),
(22, 14, 21),
(23, 2, 23),
(24, 3, 24),
(25, 4, 25),
(26, 5, 26);

INSERT INTO socio_actor_favorito (id, id_actor, id_socio) 
VALUES 
(1, 9, 1),
(2, 8, 2),
(3, 7, 3),
(4, 5, 4),
(5, 6, 5),
(6, 4, 6),
(7, 3, 7),
(8, 2, 8),
(9, 10, 9),
(10, 9, 10),
(11, 5, 1),
(12, 4, 2),
(13, 8, 3),
(14, 3, 4),
(15, 2, 5),
(16, 1, 6),
(17, 8, 7),
(18, 9, 8),
(19, 6, 9),
(20, 1, 10);

INSERT INTO socio_director_favorito (id, id_director, id_socio) 
VALUES 
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 8),
(9, 9, 9),
(10, 10, 10),
(11, 8, 1),
(12, 4, 2),
(13, 6, 3),
(14, 2, 4),
(15, 1, 5),
(16, 9, 6),
(17, 5, 7),
(18, 7, 8),
(19, 3, 9),
(20, 2, 10);

INSERT INTO socio_genero_favorito (id, id_genero, id_socio) 
VALUES 
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5),
(6, 6, 6),
(7, 7, 7),
(8, 8, 8),
(9, 9, 9),
(10, 10, 10),
(11, 3, 1),
(12, 4, 2),
(13, 5, 3),
(14, 6, 4),
(15, 7, 5),
(16, 8, 6),
(17, 9, 7),
(18, 10, 8),
(19, 1, 9),
(20, 2, 10);