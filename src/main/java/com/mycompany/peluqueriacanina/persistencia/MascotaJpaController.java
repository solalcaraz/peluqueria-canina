package com.mycompany.peluqueriacanina.persistencia;

import com.mycompany.peluqueriacanina.logica.Mascota;
import java.util.List;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MascotaJpaController {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PeluCaninaPU");

    public void crear(Mascota mascota) {
        enTransaccion(em -> em.persist(mascota));
    }

    public void editar(Mascota mascota) {
        enTransaccion(em -> em.merge(mascota));
    }

    public void borrar(int numCliente) {
        enTransaccion(em -> {
            Mascota mascota = em.find(Mascota.class, numCliente);
            if (mascota != null) {
                em.remove(mascota);
            }
        });
    }

    public List<Mascota> traerTodas() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT m FROM Mascota m ORDER BY m.numCliente", Mascota.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    private void enTransaccion(Consumer<EntityManager> operacion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            operacion.accept(em);
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
