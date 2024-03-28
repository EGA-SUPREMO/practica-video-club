package basedatos;

import video_club.Pelicula;

import java.sql.*;

/**
 *
 * -- Inicio de sesion
SELECT *
FROM socio
WHERE id = 'cedula_del_socio';

borrar cuenta

quitar 90 al socio actual

-- Para consultas del buscador
*hecho

-- ver lista de espera

 * @author alejandro
 */
public class Consulta {
    Connection connection;
    
    public Consulta(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
    }

    public static void main(String[] args) {
        
        String url = "jdbc:mariadb://localhost:3306/video_club";
        String username = "trabajo";
        String password = "1234";

        try {
            Consulta consulta = new Consulta(url, username, password);
            System.out.println("Connected to the database!");

            Pelicula peli = consulta.buscarPorTitulo("The Godfather")[0];
            //Pelicula peli = consulta.buscarPorGenero("Drama")[0];
            //System.out.println(consulta.buscarPorGenero("Drama").length);

            System.out.println(peli.genero);
            System.out.println(peli.director);
            System.out.println(peli.actores[0]);
            System.out.println(peli.actores[1]);
            System.out.println(peli.cintas_disponibles);

            if (consulta.connection != null) {
                consulta.connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Pelicula[] buscarPorTitulo(String titulo) throws SQLException {
        String sql = "SELECT * FROM pelicula WHERE titulo = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, titulo);
            ResultSet resultSet = statement.executeQuery();
            
            return mapResultSetToPeliculas(resultSet);
        }
    }
    
    private Pelicula[] buscarPorGenero(String genero) throws SQLException {
        String sql = "SELECT p.* FROM pelicula p JOIN genero g ON p.id_genero = g.id WHERE g.nombre = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, genero);
            ResultSet resultSet = statement.executeQuery();
            
            return mapResultSetToPeliculas(resultSet);
        }
    }
    
    private Pelicula[] buscarPorActor(String nombreActor) throws SQLException {
        String sql = "SELECT p.* FROM pelicula p JOIN actores_en_peliculas ap ON p.id = ap.id_pelicula " +
                     "JOIN actor a ON ap.id_actor = a.id WHERE a.nombre = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombreActor);
            ResultSet resultSet = statement.executeQuery();
            
            return mapResultSetToPeliculas(resultSet);
        }
    }
    
    private Pelicula[] buscarPorDirector(String nombreDirector) throws SQLException {
        String sql = "SELECT p.* FROM pelicula p JOIN director d ON p.id_director = d.id WHERE d.nombre = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombreDirector);
            ResultSet resultSet = statement.executeQuery();
            
            return mapResultSetToPeliculas(resultSet);
        }
    }
    private String buscarGeneroPorId(int id) throws SQLException {
        String sql = "SELECT genero.nombre FROM genero WHERE genero.id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id+"");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            } else {
                return "No se encontro";
            }
        }
    }
    private String buscarDirectorPorId(int id) throws SQLException {
        String sql = "SELECT director.nombre FROM director WHERE director.id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id+"");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("nombre");
            } else {
                return "No se encontro";
            }
        }
    }

    private String cintas_disponiblesPorPeliculaId(int id) throws SQLException {
        String sql = "SELECT COUNT(*) AS num_cintas_disponibles FROM cinta WHERE id_pelicula = ? AND id NOT IN (SELECT cinta.id FROM cinta JOIN prestamo_cinta ON cinta.id = prestamo_cinta.id_cinta JOIN prestamo ON prestamo_cinta.id_prestamo = prestamo.id JOIN pelicula ON cinta.id_pelicula = pelicula.id WHERE pelicula.id = 4 AND prestamo.fecha_devuelta IS NULL );";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id+"");
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("num_cintas_disponibles");
            } else {
                return "No se encontro";
            }
        }
    }

    private String[] buscarActoresPorPeliculaId(int id) throws SQLException {
        String sql = "SELECT a.nombre FROM actores_en_peliculas ap JOIN actor a ON ap.id_actor = a.id WHERE ap.id_pelicula = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id+"");
            ResultSet rs = statement.executeQuery();
            String[] nombresActores = new String[99];
            int i = 0;
            while (rs.next()) {
                nombresActores[i] = rs.getString("nombre");
                i++;
            }

            return nombresActores;
        }
    }
    
    private Pelicula[] mapResultSetToPeliculas(ResultSet resultSet) throws SQLException {
        Pelicula[] peliculas = new Pelicula[100];
        int index = 0;
        
        while (resultSet.next()) {
            String titulo = resultSet.getString("titulo");
            String id_pelicula = resultSet.getString("id");
            String genero_id = resultSet.getString("id_genero");
            String id_director = resultSet.getString("id_director");
            String genero = buscarGeneroPorId(Integer.parseInt(genero_id));
            String director = buscarDirectorPorId(Integer.parseInt(id_director));
            String[] actores = buscarActoresPorPeliculaId(Integer.parseInt(id_pelicula));

            peliculas[index] = new Pelicula(genero, titulo, actores, director);
            peliculas[index].cintas_disponibles = cintas_disponiblesPorPeliculaId(Integer.parseInt(id_pelicula));
            index++;
        }
        
        Pelicula[] resultadoFinal = new Pelicula[index];
        System.arraycopy(peliculas, 0, resultadoFinal, 0, index);
        
        return resultadoFinal;
    }
}
