/*
* @au
* */
package view;

import model.Tarea;
import repository.TareaRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Clase principal de la UI (interfaz gráfica)
public class TodoView extends JFrame {

    // Componentes de entrada para el formulario
    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JComboBox<String> cbEstado;

    // Filtro
    private JComboBox<String> cbFiltro;
    //Table
    private JTable tabla;
    private DefaultTableModel modelo;

    // Reposotory para el manejo de los datos
    private TareaRepository repository;

    // Colores
    private final Color Background = new Color(232, 232, 232);
    private final Color PRIMARY = new Color(29, 26, 128);
    private final Color SUCCESS = new Color(0, 138, 52);
    private final Color DANGER = new Color(216, 40, 40);

    // Constructor de la vista: configura la ventana principal e inicializa la interfaz gráfica
    public TodoView(TareaRepository repository) {
        this.repository = repository;

        setTitle("Test Java Swing To-Do App");
        setSize(750, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Background);

        initUI();
    }

    // Inicializa y construye todos los componentes de la interfaz gráfica
    private void initUI() {

        // Panel superior que contiene el formulario
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 0, 10)
        );
        panelTop.setBackground(Background);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Registro de Tareas"));
        panelForm.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Título"), gbc);

        gbc.gridx = 1;
        txtTitulo = new JTextField();
        txtTitulo.setPreferredSize(new Dimension(550, 25));
        panelForm.add(txtTitulo, gbc);

        // Descripción
        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Descripción"), gbc);

        gbc.gridx = 1;
        txtDescripcion = new JTextArea(4, 20);
        panelForm.add(new JScrollPane(txtDescripcion), gbc);

        // Estado
        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Estado"), gbc);

        gbc.gridx = 1;
        cbEstado = new JComboBox<>(new String[]{
                "Pendiente", "En progreso", "Completada"
        });
        cbEstado.setPreferredSize(new Dimension(550, 25));
        panelForm.add(cbEstado, gbc);

        // Botón agregar tarea
        gbc.gridx = 1; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAgregar = crearBoton("Agregar Tarea", PRIMARY);
        btnAgregar.setPreferredSize(new Dimension(200, 35));

        panelForm.add(btnAgregar, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        panelTop.add(panelForm, BorderLayout.CENTER);
        add(panelTop, BorderLayout.NORTH);

        // Tabla
        modelo = new DefaultTableModel(new String[]{"Título", "Descripción", "Estado"}, 0);
        tabla = new JTable(modelo);

        tabla.setRowHeight(25);
        tabla.setSelectionBackground(new Color(199, 210, 254));

        JScrollPane scroll = new JScrollPane(tabla);

        scroll.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(Background);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelTabla.add(scroll, BorderLayout.CENTER);

        // Filtro
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelFiltro.setBackground(Background);

        panelFiltro.add(new JLabel("Filtrar por estado:"));

        cbFiltro = new JComboBox<>(new String[]{
                "Todos", "Pendiente", "En progreso", "Completada"
        });

        panelFiltro.add(cbFiltro);


        panelTabla.add(panelFiltro, BorderLayout.NORTH);
        add(panelTabla, BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Background);

        JButton btnEliminar = crearBoton("Eliminar Tarea Seleccionada", DANGER);
        JButton btnCompletar = crearBoton("Cambiar Estado", SUCCESS);

        panelBotones.add(btnEliminar);
        panelBotones.add(btnCompletar);

        add(panelBotones, BorderLayout.SOUTH);

        // Acciones
        btnAgregar.addActionListener(e -> agregarTarea());
        btnEliminar.addActionListener(e -> eliminarTarea());
        btnCompletar.addActionListener(e -> completarTarea());
        cbFiltro.addActionListener(e -> refrescarTabla());
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        return btn;
    }

    // Metodo para agregar una nueva tarea
    private void agregarTarea() {
        String titulo = txtTitulo.getText().trim();

        // Muestra advertencia que el titulo es obligatorio
        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título es obligatorio");
            return;
        }
        // Crea objeto
        Tarea tarea = new Tarea(
                titulo,
                txtDescripcion.getText(),
                (String) cbEstado.getSelectedItem()
        );

        repository.agregar(tarea);
        refrescarTabla();
        limpiarCampos();
    }

    // Metodo para eliminar una tarea seleccionada
    private void eliminarTarea() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar tarea?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            repository.eliminar(fila);
            refrescarTabla();
        }
    }
    // Metodo para cambiar una tarea a estado completado
    private void completarTarea() {
        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una tarea");
            return;
        }

        repository.obtener(fila).setEstado("Completada");
        refrescarTabla();
    }

    // Actualiza la tabla aplicando el filtro seleccionado
    private void refrescarTabla() {
        // Limpia la tabla
        modelo.setRowCount(0);

        String filtro = (String) cbFiltro.getSelectedItem();

        // Recorre la lista de tareas y aplica filtro
        for (Tarea tarea : repository.listar()) {
            if (filtro.equals("Todos") || tarea.getEstado().equals(filtro)) {
                modelo.addRow(new Object[]{tarea.getTitulo(), tarea.getDescripcion(), tarea.getEstado()});
            }
        }
    }

    // Limpia los campos del formulario
    private void limpiarCampos() {
        txtTitulo.setText("");
        txtDescripcion.setText("");
        cbEstado.setSelectedIndex(0);
    }
}