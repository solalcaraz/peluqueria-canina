package com.mycompany.peluqueriacanina.logica;

import com.mycompany.peluqueriacanina.persistencia.ControladoraPersistencia;
import java.util.List;

public class Controladora {

    private final ControladoraPersistencia persistencia = new ControladoraPersistencia();

    public void crearMascota(Mascota mascota) {
        validar(mascota);
        persistencia.crearMascota(mascota);
    }

    public void modificarMascota(Mascota mascota) {
        validar(mascota);
        persistencia.modificarMascota(mascota);
    }

    public void borrarMascota(int numCliente) {
        persistencia.borrarMascota(numCliente);
    }

    public List<Mascota> traerMascotas() {
        return persistencia.traerMascotas();
    }

    private void validar(Mascota mascota) {
        if (mascota.getNombre().isBlank()) {
            throw new IllegalArgumentException("Ingresá el nombre de la mascota.");
        }
        if (mascota.getDueno().getNombre().isBlank()) {
            throw new IllegalArgumentException("Ingresá el nombre del dueño.");
        }
    }
}
