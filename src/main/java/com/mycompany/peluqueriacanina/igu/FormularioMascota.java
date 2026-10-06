package com.mycompany.peluqueriacanina.igu;

import com.mycompany.peluqueriacanina.logica.Controladora;
import com.mycompany.peluqueriacanina.logica.Dueno;
import com.mycompany.peluqueriacanina.logica.Mascota;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** Alta y edición de una mascota con su dueño. Si recibe mascota null, carga una nueva. */
class FormularioMascota extends JDialog {

    private static final String[] OPCIONES_SI_NO = {"-", "Si", "No"};

    private final Controladora control;
    private final Mascota mascota;

    private final JTextField txtNombre = new JTextField(22);
    private final JTextField txtRaza = new JTextField(22);
    private final JTextField txtColor = new JTextField(22);
    private final JComboBox<String> cmbAlergico = new JComboBox<>(OPCIONES_SI_NO);
    private final JComboBox<String> cmbAtencionEspecial = new JComboBox<>(OPCIONES_SI_NO);
    private final JTextField txtNombreDueno = new JTextField(22);
    private final JTextField txtCelDueno = new JTextField(22);
    private final JTextField txtDireccionDueno = new JTextField(22);
    private final JTextArea txtObservaciones = new JTextArea(4, 22);

    private final JPanel campos = new JPanel(new GridBagLayout());
    private int filas = 0;

    FormularioMascota(Window padre, Controladora control, Mascota mascota) {
        super(padre, mascota == null ? "Cargar mascota" : "Editar mascota", ModalityType.APPLICATION_MODAL);
        this.control = control;
        this.mascota = mascota;
        setIconImage(Estilo.iconoVentana());

        agregarFila("Nombre:", txtNombre);
        agregarFila("Raza:", txtRaza);
        agregarFila("Color:", txtColor);
        agregarFila("Alérgico:", cmbAlergico);
        agregarFila("Atención especial:", cmbAtencionEspecial);
        agregarFila("Nombre dueño:", txtNombreDueno);
        agregarFila("Cel. dueño:", txtCelDueno);
        agregarFila("Dirección dueño:", txtDireccionDueno);
        txtObservaciones.setLineWrap(true);
        txtObservaciones.setWrapStyleWord(true);
        agregarFila("Observaciones:", new JScrollPane(txtObservaciones));

        JButton btnLimpiar = Estilo.boton("Limpiar");
        JButton btnGuardar = Estilo.botonPrimario("Guardar");
        btnLimpiar.addActionListener(e -> limpiar());
        btnGuardar.addActionListener(e -> guardar());
        getRootPane().setDefaultButton(btnGuardar);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        botones.add(btnLimpiar);
        botones.add(btnGuardar);
        botones.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JLabel titulo = Estilo.titulo(mascota == null ? "Nueva mascota" : "Cliente Nº " + mascota.getNumCliente());
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel contenido = new JPanel(new BorderLayout());
        Estilo.margen(contenido, 28);
        contenido.add(titulo, BorderLayout.NORTH);
        contenido.add(campos, BorderLayout.WEST);
        contenido.add(new JLabel(Estilo.perro(240)), BorderLayout.EAST);
        contenido.add(botones, BorderLayout.SOUTH);
        setContentPane(contenido);

        if (mascota != null) {
            cargarDatos();
        }
        pack();
        setResizable(false);
        setLocationRelativeTo(padre);
    }

    private void agregarFila(String etiqueta, JComponent campo) {
        boolean multilinea = campo instanceof JScrollPane;
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = filas++;
        c.insets = new Insets(multilinea ? 9 : 5, 0, 5, 12);
        c.anchor = multilinea ? GridBagConstraints.NORTHWEST : GridBagConstraints.WEST;
        campos.add(new JLabel(etiqueta), c);

        c.gridx = 1;
        c.insets = new Insets(5, 0, 5, 0);
        c.anchor = GridBagConstraints.WEST;
        c.fill = campo instanceof JComboBox ? GridBagConstraints.NONE : GridBagConstraints.HORIZONTAL;
        campos.add(campo, c);
    }

    private void cargarDatos() {
        txtNombre.setText(mascota.getNombre());
        txtRaza.setText(mascota.getRaza());
        txtColor.setText(mascota.getColor());
        cmbAlergico.setSelectedItem(mascota.getAlergico());
        cmbAtencionEspecial.setSelectedItem(mascota.getAtencionEspecial());
        txtNombreDueno.setText(mascota.getDueno().getNombre());
        txtCelDueno.setText(mascota.getDueno().getCel());
        txtDireccionDueno.setText(mascota.getDueno().getDireccion());
        txtObservaciones.setText(mascota.getObservaciones());
    }

    private void limpiar() {
        for (JTextField campo : new JTextField[]{txtNombre, txtRaza, txtColor, txtNombreDueno, txtCelDueno, txtDireccionDueno}) {
            campo.setText("");
        }
        txtObservaciones.setText("");
        cmbAlergico.setSelectedIndex(0);
        cmbAtencionEspecial.setSelectedIndex(0);
        txtNombre.requestFocusInWindow();
    }

    private void guardar() {
        boolean esNueva = mascota == null;
        Mascota destino = esNueva ? new Mascota() : mascota;
        if (esNueva) {
            destino.setDueno(new Dueno());
        }
        destino.setNombre(txtNombre.getText().trim());
        destino.setRaza(txtRaza.getText().trim());
        destino.setColor(txtColor.getText().trim());
        destino.setAlergico((String) cmbAlergico.getSelectedItem());
        destino.setAtencionEspecial((String) cmbAtencionEspecial.getSelectedItem());
        destino.setObservaciones(txtObservaciones.getText().trim());
        destino.getDueno().setNombre(txtNombreDueno.getText().trim());
        destino.getDueno().setCel(txtCelDueno.getText().trim());
        destino.getDueno().setDireccion(txtDireccionDueno.getText().trim());

        try {
            if (esNueva) {
                control.crearMascota(destino);
                Mensajes.info(this, destino.getNombre() + " quedó registrada como cliente Nº " + destino.getNumCliente() + ".");
                limpiar();
            } else {
                control.modificarMascota(destino);
                Mensajes.info(this, "Los cambios se guardaron correctamente.");
                dispose();
            }
        } catch (IllegalArgumentException e) {
            Mensajes.error(this, e.getMessage());
        } catch (RuntimeException e) {
            Mensajes.error(this, "No se pudo guardar en la base de datos.\n" + e.getMessage());
        }
    }
}
