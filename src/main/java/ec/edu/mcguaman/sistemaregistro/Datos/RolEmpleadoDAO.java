/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Datos;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import modelo.RolEmpleado;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class RolEmpleadoDAO {

    private static final Logger LOGGER = Logger.getLogger(RolEmpleadoDAO.class.getName());

    // Método para verificar si el rol ya existe en la base de datos
    public boolean existeRol(String nombreRol) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            long count = em.createQuery(
                    "SELECT COUNT(r) FROM RolEmpleado r WHERE r.nombre = :nombre", Long.class)
                    .setParameter("nombre", nombreRol)
                    .getSingleResult();
            return count > 0;
        } catch (NoResultException ex) {
            return false;
        } finally {
            em.close();
        }
    }

    // En RolEmpleadoDAO
    public void guardarRol(RolEmpleado rol) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rol);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public RolEmpleado buscarRolPorNombre(String nombre) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT r FROM RolEmpleado r WHERE r.nombre = :nombre", RolEmpleado.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Si no se encuentra el rol, devuelve null
        } finally {
            em.close();
        }
    }

    public List<String> listarTodosLosRoles() {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        List<String> roles = new ArrayList<>();
        try {
            return em.createQuery("SELECT r.nombre FROM RolEmpleado r", String.class) // Usar r.nombre
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
