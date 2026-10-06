package com.mycompany.peluqueriacanina.igu;

import com.mycompany.peluqueriacanina.logica.Controladora;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Principal extends JFrame {

    public Principal(Controladora control) {
        super("Peluquería Canina");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(Estilo.iconoVentana());

        JButton btnCargar = Estilo.botonPrimario("Cargar mascota");
        JButton btnVer = Estilo.boton("Ver mascotas");
        JButton btnSalir = Estilo.boton("Salir");
        btnCargar.addActionListener(e -> new FormularioMascota(this, control, null).setVisible(true));
        btnVer.addActionListener(e -> new ListadoMascotas(this, control).setVisible(true));
        btnSalir.addActionListener(e -> dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));

        JPanel botones = new JPanel(new GridLayout(3, 1, 0, 16));
        botones.add(btnCargar);
        botones.add(btnVer);
        botones.add(btnSalir);
        JPanel columnaBotones = new JPanel(new BorderLayout());
        columnaBotones.add(botones, BorderLayout.NORTH);
        columnaBotones.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 24));

        JLabel titulo = Estilo.titulo("Peluquería Canina");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel contenido = new JPanel(new BorderLayout());
        Estilo.margen(contenido, 28);
        contenido.add(titulo, BorderLayout.NORTH);
        contenido.add(columnaBotones, BorderLayout.WEST);
        contenido.add(new JLabel(Estilo.perro(280)), BorderLayout.CENTER);
        setContentPane(contenido);

        pack();
        setResizable(false);
    }
}
