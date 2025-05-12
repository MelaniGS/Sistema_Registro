/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Datos;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.*;
import modelo.Cliente;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class ClienteDAO {

    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());

    /**
     * Registra un nuevo Cliente si no existe otra entidad con la misma cédula.
     *
     * @param cliente La entidad Cliente a persistir.
     * @return 0 si ya existe, 1 si se registró con éxito, 2 si ocurrió un
     * error.
     */
    public int registrarCliente(Cliente cliente) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            long count = em.createQuery(
                    "SELECT COUNT(c) FROM Cliente c WHERE c.cedula = :ced", Long.class)
                    .setParameter("ced", cliente.getCedula())
                    .getSingleResult();
            if (count > 0) {
                return 0;
            }
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return 1;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.severe("Error al registrar Cliente: " + ex.getMessage());
            return 2;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los Clientes.
     *
     * @return Lista de Clientes.
     */
    public List<Cliente> listarClientes() {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT c FROM Cliente c", Cliente.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Busca un Cliente por su ID.
     *
     * @param id Identificador del Cliente.
     * @return Cliente encontrado o null.
     */
    public Cliente buscarClientePorId(int id) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * Busca un Cliente por su cédula.
     *
     * @param cedula La cédula a buscar.
     * @return Cliente encontrado o null.
     */
    public Cliente buscarClientePorCedula(String cedula) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Cliente c WHERE c.cedula = :ced", Cliente.class)
                    .setParameter("ced", cedula)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza un Cliente existente.
     *
     * @param cliente La entidad Cliente con cambios.
     * @return true si se actualizó, false si no existía o hubo error.
     */
    public boolean actualizarCliente(Cliente cliente) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            if (em.find(Cliente.class, cliente.getId()) == null) {
                return false;
            }
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.severe("Error al actualizar Cliente: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un Cliente por su ID.
     *
     * @param id Identificador del Cliente.
     * @return true si se eliminó, false si no existía o hubo error.
     */
    public boolean eliminarCliente(int id) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Cliente cliente = em.find(Cliente.class, id);
            if (cliente == null) {
                return false;
            }
            em.getTransaction().begin();
            em.remove(cliente);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.severe("Error al eliminar Cliente: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
    }
}
