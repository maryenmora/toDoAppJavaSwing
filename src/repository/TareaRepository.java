package repository;

import model.Tarea;
import java.util.ArrayList;
import java.util.List;

// Repositorio en memoria encargado de gestionar las tareas
public class TareaRepository {

    // Lista en memoria de las tareas
    private List<Tarea> tareas = new ArrayList<>();

    // Agrega una nueva tarea
    public void agregar(Tarea tarea) {
        tareas.add(tarea);
    }

    // Elimina una tarea
    public void eliminar(int index) {
        tareas.remove(index);
    }

    // Retorna la lista de las tareas
    public List<Tarea> listar() {
        return tareas;
    }

    // Obtiene una tarea específica por su índice
    public Tarea obtener(int index) {
        return tareas.get(index);
    }
}