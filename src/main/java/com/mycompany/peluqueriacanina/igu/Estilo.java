package com.mycompany.peluqueriacanina.igu;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Image;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.UIManager;

public final class Estilo {

    private static final String ESTILO_BOTON = "font: +2; margin: 8,22,8,22";
    private static final ImageIcon PERRO = new ImageIcon(Estilo.class.getResource("/img/perro.png"));

    private Estilo() {
    }

    public static void aplicarTema() {
        FlatLaf.setGlobalExtraDefaults(Map.of("@accentColor", "#1E6FD9"));
        FlatLightLaf.setup();
        UIManager.put("Button.arc", 12);
        UIManager.put("Component.arc", 10);
        UIManager.put("TextComponent.arc", 10);
        UIManager.put("Table.showHorizontalLines", true);
        UIManager.put("Table.alternateRowColor", new Color(0xF3F7FC));
    }

    static JLabel titulo(String texto) {
        JLabel titulo = new JLabel(texto);
        titulo.putClientProperty(FlatClientProperties.STYLE, "font: bold +18; foreground: @accentColor");
        return titulo;
    }

    static JButton botonPrimario(String texto) {
        JButton boton = new JButton(texto);
        boton.putClientProperty(FlatClientProperties.STYLE, ESTILO_BOTON
                + "; font: bold +2; foreground: #FFFFFF; background: @accentColor; borderColor: @accentColor"
                + "; focusedBackground: @accentColor; hoverBackground: darken(@accentColor,8%)"
                + "; pressedBackground: darken(@accentColor,15%)"
                + "; default.foreground: #FFFFFF; default.background: @accentColor; default.borderColor: @accentColor");
        return boton;
    }

    static JButton boton(String texto) {
        JButton boton = new JButton(texto);
        boton.putClientProperty(FlatClientProperties.STYLE, ESTILO_BOTON);
        return boton;
    }

    static ImageIcon perro(int lado) {
        return new ImageIcon(PERRO.getImage().getScaledInstance(lado, lado, Image.SCALE_SMOOTH));
    }

    static Image iconoVentana() {
        return PERRO.getImage();
    }

    static void margen(JComponent componente, int px) {
        componente.setBorder(BorderFactory.createEmptyBorder(px, px, px, px));
    }
}
