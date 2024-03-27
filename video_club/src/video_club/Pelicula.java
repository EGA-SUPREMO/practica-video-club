/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package video_club;

/**
 *
 * @author trabajo
 */
public class Pelicula {
    
    public String genero;
    public String titulo;
    public String[] actores;
    public String director;
    public int cintas_disponibles;
    public String estado;
    
    public Pelicula(String genero, String titulo, String[] actores,
                String director, int cintas_disponibles, String estado) {
        this.genero = genero;
        this.titulo = titulo;
        this.actores = actores;
        this.director = director;
        this.cintas_disponibles = cintas_disponibles;
        this.estado = estado;
    }

}
