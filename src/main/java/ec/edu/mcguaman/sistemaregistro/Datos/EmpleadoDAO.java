/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Datos;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import modelo.Empleados;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class EmpleadoDAO {

    private static final Logger LOGGER = Logger.getLogger(EmpleadoDAO.class.getName());

    /**
     * Registra un nuevo Empleado si no existe con la misma cédula.
     *
     * @param empleado La entidad Empleados a persistir.
     * @return 0 si ya existe, 1 si éxito, 2 si error.
     */
    public int registrarEmpleado(Empleados empleado) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            long count = em.createQuery(
                    "SELECT COUNT(e) FROM Empleados e WHERE e.cedula = :ced", Long.class)
                    .setParameter("ced", empleado.getCedula())
                    .getSingleResult();
            if (count > 0) {
                return 0;
            }
            em.getTransaction().begin();
            em.persist(empleado);
            em.getTransaction().commit();
            return 1;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.severe("Error al registrar Empleado: " + ex.getMessage());
            return 2;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los Empleados.
     *
     * @return Lista de Empleados.
     */
    public List<Empleados> listarEmpleados() {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Empleados e", Empleados.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Busca un Empleado por su ID.
     *
     * @param id Identificador del Empleado.
     * @return Empleado encontrado o null.
     */
    public Empleados buscarEmpleadoPorId(int id) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Empleados.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Busca un Empleado por su cédula.
     *
     * @param cedula La cédula a buscar.
     * @return Empleado o null si no existe.
     */
    public Empleados buscarEmpleadoPorCedula(String cedula) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT e FROM Empleados e WHERE e.cedula = :ced", Empleados.class)
                    .setParameter("ced", cedula)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza un Empleado existente.
     *
     * @param empleado La entidad Empleados con cambios.
     * @return true si se actualizó, false si no existía o hubo error.
     */
    public boolean actualizarEmpleado(Empleados empleado) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            if (em.find(Empleados.class, empleado.getId()) == null) {
                return false;
            }
            em.getTransaction().begin();
            em.merge(empleado);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.severe("Error al actualizar Empleado: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un Empleado por su ID.
     *
     * @param id Identificador del Empleado.
     * @return true si se eliminó, false si no existía o hubo error.
     */
    public boolean eliminarEmpleado(int id) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Empleados empleado = em.find(Empleados.class, id);
            if (empleado == null) {
                return false;
            }
            em.getTransaction().begin();
            em.remove(empleado);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.severe("Error al eliminar Empleado: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
}
