/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Datos;

import modelo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUtil;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class PersonaDAO {

    public void AgregarPersona(Persona personaAgregar) {

        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(personaAgregar);
            em.getTransaction().commit();

        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.err.println("Error de sesion de trabajo" + ex.getMessage());

        } finally {
            em.close();
        }
    }

    public int VerificarAgregarPersona(Persona personaAgregar) {
        int result = 0;
        // Inicia la sesion de trabajo con la base de datos
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Persona personaExiste = em.createQuery(
                    "SELECT p FROM Persona p WHERE p.cedula = :numId", Persona.class)
                    .setParameter("numId", personaAgregar.getCedula())
                    .getSingleResult();

            if (personaExiste != null) {
                System.out.println("YA EXISTE LA PERSONA");
                em.close();
                return result;
            }
        } catch (NoResultException ex) {
            // Se inicia la transicion
            em.getTransaction().begin();
            // Se inserta la persona
            em.persist(personaAgregar);
            // Confirmar y guardar los cambios
            em.getTransaction().commit();
            result = 1;
        } catch (Exception ex) {
            // Revertir todo, no guardar nada
            em.getTransaction().rollback();
            System.err.println("Error de sesion de trabajo: " + ex.getMessage());
            result = 2;
        } finally {
            em.close();
        }
        return result;
    }

    public List<Persona> obtenerPersona() {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        List<Persona> personas = new ArrayList<>();

        try {
            em.getTransaction().begin();
            personas = em.createQuery("SELECT p FROM Persona p", Persona.class).getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.err.println("Error al obtener personas: " + ex.getMessage());
        } finally {
            em.close();
        }

        return personas;
    }

    // Metodo que permite actulizar la persona
    public boolean ActualizarPersona(int id, Persona personaActualizar) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Persona existente = em.find(Persona.class, id);
            if (existente == null) {
                return false;
            }

            em.getTransaction().begin();
            existente.setNombre(personaActualizar.getNombre());
            existente.setApellido(personaActualizar.getApellido());
            existente.setCedula(personaActualizar.getCedula());
            existente.setCorreo(personaActualizar.getCorreo());
            existente.setFecha_nacimiento(personaActualizar.getFecha_nacimiento());
            existente.setEdad(personaActualizar.getEdad());
            existente.setTelefono(personaActualizar.getTelefono());
            // em.merge(personaActualizar);
            em.getTransaction().commit();
            return true;

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // Eliminar persona
    // Si retorna true se elimino el registro, false no se pudo eliminar
    public boolean EliminarPersona(int numId) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Persona persona = em.find(Persona.class, numId);

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
            return false;
        } finally {
            em.close();
        }
    }

    public int RegistrarPersona(Persona personaAgregar) {
        // Inicia la sesion de trabajo con la base de datos
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(p) FROM Persona p WHERE p.cedula = :ced", Long.class)
                    .setParameter("ced", personaAgregar.getCedula())
                    .getSingleResult();

            // Existe la persona, porque el contador dio un resultado
            if (count > 0) {
                return 0;
            }

            // Se inicia la transicion
            em.getTransaction().begin();
            // Se inserta la persona
            em.persist(personaAgregar);
            // Confirmar y guardar los cambios
            em.getTransaction().commit();
            return 1;
        } catch (Exception ex) {
            // Revertir todo, no guardar nada
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return 2;

        } finally {
            em.close();
        }
    }

    public List<Persona> ListarPersonasRegistradas() {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Persona p", Persona.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Persona obtenerPersonaPorCed(String cedula) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            String query = "SELECT p FROM Persona p WHERE p.cedula = :cedula";
            return em.createQuery(query, Persona.class)
                    .setParameter("cedula", cedula) 
                    .getSingleResult();  
        } catch (NoResultException e) {
            return null;  // En caso de no encontrar ninguna persona con esa cédula, retornar null
        } finally {
            em.close();
        }
    }

    public Persona BuscarPersonaPorCedula(String cedula) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Persona p WHERE p.numIdentificacion = :cedula", Persona.class)
                    .setParameter("cedula", cedula)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

}
