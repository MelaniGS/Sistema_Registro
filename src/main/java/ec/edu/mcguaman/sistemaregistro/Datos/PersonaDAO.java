/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Datos;

import modelo.Persona;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class PersonaDAO {

    /**
     * Registra una nueva Persona si no existe otra con la misma cédula.
     *
     * @param persona La entidad Persona a persistir.
     * @return 0 si ya existe, 1 si se registró con éxito, 2 si ocurrió un
     * error.
     */
    public int registrarPersona(Persona persona) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            long count = em.createQuery(
                    "SELECT COUNT(p) FROM Persona p WHERE p.cedula = :ced", Long.class)
                    .setParameter("ced", persona.getCedula())
                    .getSingleResult();
            if (count > 0) {
                return 0; // Ya existe
            }
            em.getTransaction().begin();
            em.persist(persona);
            em.getTransaction().commit();
            return 1; // Éxito
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            return 2; // Error BD
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todas las Personas.
     *
     * @return Lista de Personas.
     */
    public List<Persona> listarPersonas() {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Persona p", Persona.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Busca una Persona por su cédula.
     *
     * @param cedula La cédula a buscar.
     * @return Persona encontrada o null si no existe.
     */
    public Persona buscarPersonaPorCedula(String cedula) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Persona p WHERE p.cedula = :ced", Persona.class)
                    .setParameter("ced", cedula)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza una Persona existente.
     *
     * @param persona La entidad Persona con cambios.
     * @return true si se actualizó, false si no existía o hubo error.
     */
    public boolean actualizarPersona(Persona persona) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            if (em.find(Persona.class, persona.getId()) == null) {
                return false;
            }
            em.getTransaction().begin();
            em.merge(persona);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina una Persona por su ID.
     *
     * @param id Identificador de la Persona.
     * @return true si se eliminó, false si no existía o hubo error.
     */
    public boolean eliminarPersona(int id) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Persona persona = em.find(Persona.class, id);
            if (persona == null) {
                return false;
            }
            em.getTransaction().begin();
            em.remove(persona);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

}
