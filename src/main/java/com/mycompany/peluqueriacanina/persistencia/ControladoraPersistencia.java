package com.mycompany.peluqueriacanina.persistencia;

import com.mycompany.peluqueriacanina.logica.Mascota;
import java.util.List;

public class ControladoraPersistencia {

    private final MascotaJpaController mascotaJpa = new MascotaJpaController();

    public void crearMascota(Mascota mascota) {
        mascotaJpa.crear(mascota);
    }

    public void modificarMascota(Mascota mascota) {
        mascotaJpa.editar(mascota);
    }

    public void borrarMascota(int numCliente) {
        mascotaJpa.borrar(numCliente);
    }

    public List<Mascota> traerMascotas() {
        return mascotaJpa.traerTodas();
    }
}
