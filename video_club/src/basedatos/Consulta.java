package basedatos;

import video_club.Pelicula;

import java.sql.*;

/**
 * -- Inicio de sesion
 * usa obtenerSocio()
 * quitar 90 al socio por id se usa el metodo restarSaldo()
 * TODO metodo para rentar no existe, haria que si la cintas disponibles son 0, se agrega a la lista de espera, si no, se agrega a la lista de prestamo con fecha devuelta null
 * 
 * -- ver lista de espera, se pide id de socio y se muestra, titulo y estado
 * 
 * 
 * -- Para consultas del buscador
 * usa buscarPor + lo que haya selecionado el usuario, ej buscarPorGenero(String) para obtener todas las peliculas que tengan ese genero
 * 
 * @author alejandro
 */
public class Consulta {
    private Connection connection;
    
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
            //System.out.println(consulta.obtenerSocio(2)[0]);
            /*System.out.println(consulta.obtenerSocio(2)[1]);
            System.out.println(consulta.restarSaldo(2));
            consulta.borrarCuenta(2);
            System.out.println(consulta.obtenerSocio(2));
            System.out.println(peli.genero);
            System.out.println(peli.director);
            System.out.println(peli.actores[0]);
            System.out.println(peli.actores[1]);
            System.out.println(peli.cintas_disponibles);
            */
            Pelicula[] pelis = consulta.obtenerListaEsperaDeSocio(2);
            for (Pelicula pelicula : pelis) {
                System.out.println("Titulo: " + pelicula.titulo);
                System.out.println("Estado: " + pelicula.estado);
                System.out.println(); // Add an empty line for separation
            }
            if (consulta.connection != null) {
                consulta.connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean restarSaldo(int id) {
        String sql = "SELECT saldo FROM socio WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int saldoActual = resultSet.getInt("saldo");
                    if (saldoActual - 90 < 0) {
                        return false;
                    } else {
                        String updateSql = "UPDATE socio SET saldo = saldo - 90 WHERE id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                            updateStatement.setInt(1, id);
                            int rowsAffected = updateStatement.executeUpdate();
                            
                            return rowsAffected > 0;
                        }
                    }
                } else {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // hacerlo por orden, en espera, rentadas, devueltas
    public Pelicula[] obtenerListaEsperaDeSocio(int idSocio) {
        Pelicula[] peliculas, peliculasDevueltas = new Pelicula[100], peliculasPorDevolver = new Pelicula[100], peliculasEnEspera = new Pelicula[100];
        String sql_devueltas = "SELECT p.titulo FROM prestamo pr JOIN prestamo_cinta pc ON pr.id = pc.id_prestamo JOIN cinta c ON pc.id_cinta = c.id JOIN pelicula p ON c.id_pelicula = p.id WHERE pr.id_socio = ? AND pr.fecha_devuelta IS NOT NULL;";
        String sql_por_devolver = "SELECT p.titulo FROM prestamo pr JOIN prestamo_cinta pc ON pr.id = pc.id_prestamo JOIN cinta c ON pc.id_cinta = c.id JOIN pelicula p ON c.id_pelicula = p.id WHERE pr.id_socio = ? AND pr.fecha_devuelta IS NULL;";
        String sql_en_espera = "SELECT DISTINCT p.titulo FROM lista_espera le JOIN pelicula p ON le.id_pelicula = p.id WHERE le.id_socio = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql_devueltas)) {
            statement.setInt(1, idSocio);
            ResultSet resultSet = statement.executeQuery();
            
            peliculasDevueltas = mappearResultSetParaPeliculas(resultSet, "Devuelta");
        } catch (SQLException e) { e.printStackTrace(); }

        try (PreparedStatement statement = connection.prepareStatement(sql_por_devolver)) {
            statement.setInt(1, idSocio);
            ResultSet resultSet = statement.executeQuery();
            
            peliculasPorDevolver = mappearResultSetParaPeliculas(resultSet, "Por Devolver");
        } catch (SQLException e) { e.printStackTrace(); }
        try (PreparedStatement statement = connection.prepareStatement(sql_en_espera)) {
            statement.setInt(1, idSocio);
            ResultSet resultSet = statement.executeQuery();
            
            peliculasEnEspera = mappearResultSetParaPeliculas(resultSet, "En Espera");
        } catch (SQLException e) { e.printStackTrace(); }

        peliculas = new Pelicula[peliculasDevueltas.length + peliculasEnEspera.length + peliculasPorDevolver.length];

        int i = 0;
        System.arraycopy(peliculasEnEspera, 0, peliculas, i, peliculasEnEspera.length);
        i += peliculasEnEspera.length;

        System.arraycopy(peliculasPorDevolver, 0, peliculas, i, peliculasPorDevolver.length);
        i += peliculasPorDevolver.length;

        System.arraycopy(peliculasDevueltas, 0, peliculas, i, peliculasDevueltas.length);

        return peliculas;
    }

    public String[] obtenerSocio(int id) {
        String[] socio = new String[2];
        String sql = "SELECT nombre, saldo FROM socio WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String saldo = resultSet.getInt("saldo") + "";
                socio[0] = saldo;
                socio[1] = nombre;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return socio;
    }


    public void borrarCuenta(int id) throws SQLException{
        String sql = "DELETE FROM socio WHERE id = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id+"");
            ResultSet resultSet = statement.executeQuery();
        }
    }

    public Pelicula[] buscarPorTitulo(String titulo) throws SQLException {
        String sql = "SELECT * FROM pelicula WHERE titulo = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, titulo);
            ResultSet resultSet = statement.executeQuery();
            
            return mappearResultSetParaPeliculas(resultSet);
        }
    }
    
    public Pelicula[] buscarPorGenero(String genero) throws SQLException {
        String sql = "SELECT p.* FROM pelicula p JOIN genero g ON p.id_genero = g.id WHERE g.nombre = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, genero);
            ResultSet resultSet = statement.executeQuery();
            
            return mappearResultSetParaPeliculas(resultSet);
        }
    }
    
    public Pelicula[] buscarPorActor(String nombreActor) throws SQLException {
        String sql = "SELECT p.* FROM pelicula p JOIN actores_en_peliculas ap ON p.id = ap.id_pelicula " +
                     "JOIN actor a ON ap.id_actor = a.id WHERE a.nombre = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombreActor);
            ResultSet resultSet = statement.executeQuery();
            
            return mappearResultSetParaPeliculas(resultSet);
        }
    }
    
    public Pelicula[] buscarPorDirector(String nombreDirector) throws SQLException {
        String sql = "SELECT p.* FROM pelicula p JOIN director d ON p.id_director = d.id WHERE d.nombre = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nombreDirector);
            ResultSet resultSet = statement.executeQuery();
            
            return mappearResultSetParaPeliculas(resultSet);
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
        String sql = "SELECT COUNT(*) AS num_cintas_disponibles FROM cinta WHERE id_pelicula = ? AND id NOT IN (SELECT cinta.id FROM cinta JOIN prestamo_cinta ON cinta.id = prestamo_cinta.id_cinta JOIN prestamo ON prestamo_cinta.id_prestamo = prestamo.id JOIN pelicula ON cinta.id_pelicula = pelicula.id WHERE pelicula.id = ? AND prestamo.fecha_devuelta IS NULL );";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id+"");
            statement.setString(2, id+"");
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
    
    private Pelicula[] mappearResultSetParaPeliculas(ResultSet resultSet) throws SQLException {
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
    private Pelicula[] mappearResultSetParaPeliculas(ResultSet resultSet, String estado) throws SQLException {
        Pelicula[] peliculas = new Pelicula[100];
        int index = 0;
        
        while (resultSet.next()) {
            String titulo = resultSet.getString("titulo");
            peliculas[index] = new Pelicula("", titulo, null, "");
            peliculas[index].estado = estado;
            index++;
        }
        
        Pelicula[] resultadoFinal = new Pelicula[index];
        System.arraycopy(peliculas, 0, resultadoFinal, 0, index);
        
        return resultadoFinal;
    }
}
