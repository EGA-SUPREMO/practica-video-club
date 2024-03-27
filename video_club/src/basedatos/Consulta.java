package basedatos;

import video_club.Pelicula;

import java.sql.*;

/**
 *
 * -- Inicio de sesion
SELECT *
FROM socio
WHERE id = 'cedula_del_socio';


-- Para consultas del buscador
SELECT *
FROM pelicula
WHERE titulo = 'título_de_la_pelicula';

SELECT p.*
FROM pelicula p
JOIN genero g ON p.id_genero = g.id
WHERE g.nombre = 'terror';

SELECT p.*
FROM pelicula p
JOIN actores_en_peliculas ap ON p.id = ap.id_pelicula
JOIN actor a ON ap.id_actor = a.id
WHERE a.nombre = 'nombre_del_actor';

SELECT p.*
FROM pelicula p
JOIN director d ON p.id_director = d.id
WHERE d.nombre = 'nombre_del_director';

-- para cuando se muestra en las busquedas, tiene dos vars de input(el id de la pelicula), en la columna de cintas disponibles
SELECT COUNT(*) AS num_cintas_disponibles
FROM cinta
WHERE id_pelicula = 4 AND id NOT IN 
(
    SELECT cinta.id
    FROM cinta
    JOIN prestamo_cinta ON cinta.id = prestamo_cinta.id_cinta
    JOIN prestamo ON prestamo_cinta.id_prestamo = prestamo.id
    JOIN pelicula ON cinta.id_pelicula = pelicula.id
    WHERE pelicula.id = 4 AND prestamo.fecha_devuelta IS NULL
);


 * @author alejandro
 */
public class Consulta {

    public static void main(String[] args) {
        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            String url = "jdbc:mysql://localhost:3306/your_database_name";
            String username = "trabajo";
            String password = "1234";
            connection = DriverManager.getConnection(url, username, password);

            // Execute a query
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM your_table_name");

            // Process the results
            while (resultSet.next()) {
                // Retrieve data from the result set
                String column1 = resultSet.getString("column1");
                int column2 = resultSet.getInt("column2");

                // Process the data
                System.out.println("Column 1: " + column1 + ", Column 2: " + column2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // Datos temporales, sirve como prueba mientras no esta listo la conexion a
    // la base de datos
    public Pelicula[] buscarPorTitulo() {
        Pelicula[] resultados = new Pelicula[3];
        
        String[] actores1 = {"Actor1", "Actor2", "Actor3"};
        String[] actores2 = {"Actor4", "Actor5", "Actor6"};
        String[] actores3 = {"Actor7", "Actor8", "Actor9"};

        resultados[0] = new Pelicula("Comedia", "Pelicula1", actores1, "Director1", 5, "Disponible");
        resultados[1] = new Pelicula("Drama", "Pelicula2", actores2, "Director2", 3, "En préstamo");
        resultados[2] = new Pelicula("Acción", "Pelicula3", actores3, "Director3", 7, "Disponible");

        return resultados;
    }


}
