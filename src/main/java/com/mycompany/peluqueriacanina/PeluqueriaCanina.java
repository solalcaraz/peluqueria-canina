package com.mycompany.peluqueriacanina;

import com.mycompany.peluqueriacanina.igu.Estilo;
import com.mycompany.peluqueriacanina.igu.Principal;
import com.mycompany.peluqueriacanina.logica.Controladora;
import javax.swing.SwingUtilities;

public class PeluqueriaCanina {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Estilo.aplicarTema();
            Principal principal = new Principal(new Controladora());
            principal.setLocationRelativeTo(null);
            principal.setVisible(true);
        });
    }
}
