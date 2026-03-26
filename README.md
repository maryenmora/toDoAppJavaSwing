## Test practico Java Swing

Es una aplicación de escritorio en Java Swing que permite
gestionar una lista de tareas (To-Do App). Los datos se 
almacenan en memoria mientras la aplicación esta en ejecución.


## Como se ejecuta el proyecto?
1. Abrir el proyecto en un IDE 
2. Ejecutar la clase principal:
    Main.java


## Decisiones técnicas

Para el desarrollo de la aplicación se utilizó una estructura simple separando responsabilidades:

- **model**: representa la tarea como un objeto (título, descripción y estado)
- **repository**: se encarga de manejar las tareas en memoria
- **view**: contiene toda la interfaz gráfica y la interacción con el usuario

Se decidió usar un ArrayList para almacenar las tareas en memoria

Para mostrar la lista se utilizó un JTable con DefaultTableModel, lo que permite actualizar la tabla fácilmente cuando se agregan, eliminan o cuando cambia de estado una tarea.

