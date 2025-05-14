/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Datos;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import modelo.RolEmpleado;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class RolDAO {
    public List<RolEmpleado> listarRoles() {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Rol r", RolEmpleado.class).getResultList();
        } finally {
            em.close();
        }
    }

    public RolEmpleado buscarRolPorId(int id) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(RolEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public boolean agregarRol(RolEmpleado rol) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rol);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean eliminarRol(int id) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            RolEmpleado rol = em.find(RolEmpleado.class, id);
            if (rol != null) {
                em.getTransaction().begin();
                em.remove(rol);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } finally {
            em.close();
        }
    }
    
     private static final Logger LOGGER = Logger.getLogger(RolEmpleadoDAO.class.getName());
    
    // Método para verificar si el rol ya existe
    public boolean existeRol(String nombreRol) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            long count = em.createQuery("SELECT COUNT(r) FROM RolEmpleado r WHERE r.nombre = :nombre", Long.class)
                    .setParameter("nombre", nombreRol)
                    .getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // Método para guardar un nuevo rol
    public void guardarRol(RolEmpleado rolEmpleado) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rolEmpleado); // Guardar el nuevo rol en la base de datos
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.severe("Error al guardar el rol: " + ex.getMessage());
        } finally {
            em.close();
        }
    }
}
