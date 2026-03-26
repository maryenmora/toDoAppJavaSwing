package model;

// Modelo que representa una los datos de tarea
public class Tarea {

    private String titulo;
    private String descripcion;
    private String estado;

    // Constructor para crear una nueva tarea con sus datos
    public Tarea(String titulo, String descripcion, String estado) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    // Título de la tarea
    public String getTitulo() {
        return titulo;
    }

    // Descripción de la tarea
    public String getDescripcion() {
        return descripcion;
    }


    public String getEstado() {
        return estado;
    }

    // Estado de la tarea (Pendiente, En progreso, Completada)
    public void setEstado(String estado) {
        this.estado = estado;
    }
}