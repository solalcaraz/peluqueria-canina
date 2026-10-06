package com.mycompany.peluqueriacanina.igu;

import com.mycompany.peluqueriacanina.logica.Controladora;
import com.mycompany.peluqueriacanina.logica.Mascota;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Window;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

class ListadoMascotas extends JDialog {

    private static final String[] COLUMNAS = {"Nº", "Nombre", "Raza", "Color", "Alérgico", "At. especial", "Dueño", "Celular", "Dirección"};

    private final Controladora control;
    private final DefaultTableModel modelo = new DefaultTableModel(COLUMNAS, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modelo);
    private List<Mascota> mascotas = List.of();

    ListadoMascotas(Window padre, Controladora control) {
        super(padre, "Mascotas registradas", ModalityType.APPLICATION_MODAL);
        this.control = control;
        setIconImage(Estilo.iconoVentana());

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(28);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getColumnModel().getColumn(0).setMaxWidth(50);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(920, 380));

        JButton btnEditar = Estilo.botonPrimario("Editar");
        JButton btnEliminar = Estilo.boton("Eliminar");
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());

        JPanel botones = new JPanel(new GridLayout(2, 1, 0, 12));
        botones.add(btnEditar);
        botones.add(btnEliminar);
        JPanel columnaBotones = new JPanel(new BorderLayout());
        columnaBotones.add(botones, BorderLayout.NORTH);
        columnaBotones.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));

        JPanel contenido = new JPanel(new BorderLayout(0, 20));
        Estilo.margen(contenido, 28);
        contenido.add(Estilo.titulo("Mascotas registradas"), BorderLayout.NORTH);
        contenido.add(scroll, BorderLayout.CENTER);
        contenido.add(columnaBotones, BorderLayout.EAST);
        setContentPane(contenido);

        pack();
        setLocationRelativeTo(padre);
        cargarTabla();
    }

    private void cargarTabla() {
        try {
            mascotas = control.traerMascotas();
        } catch (RuntimeException e) {
            Mensajes.error(this, "No se pudo leer la base de datos.\n" + e.getMessage());
            return;
        }
        modelo.setRowCount(0);
        for (Mascota m : mascotas) {
            modelo.addRow(new Object[]{
                m.getNumCliente(), m.getNombre(), m.getRaza(), m.getColor(), m.getAlergico(),
                m.getAtencionEspecial(), m.getDueno().getNombre(), m.getDueno().getCel(), m.getDueno().getDireccion()
            });
        }
    }

    private Mascota mascotaSeleccionada(String accion) {
        if (mascotas.isEmpty()) {
            Mensajes.error(this, "No hay mascotas para " + accion + ".");
            return null;
        }
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            Mensajes.error(this, "Seleccioná una mascota de la tabla para " + accion + ".");
            return null;
        }
        return mascotas.get(fila);
    }

    private void editar() {
        Mascota mascota = mascotaSeleccionada("editar");
        if (mascota != null) {
            new FormularioMascota(this, control, mascota).setVisible(true);
            cargarTabla();
        }
    }

    private void eliminar() {
        Mascota mascota = mascotaSeleccionada("eliminar");
        if (mascota == null
                || !Mensajes.confirmar(this, "¿Eliminar a " + mascota.getNombre() + " y los datos de su dueño?")) {
            return;
        }
        try {
            control.borrarMascota(mascota.getNumCliente());
            Mensajes.info(this, mascota.getNombre() + " fue eliminada.");
        } catch (RuntimeException e) {
            Mensajes.error(this, "No se pudo eliminar.\n" + e.getMessage());
        }
        cargarTabla();
    }
}
