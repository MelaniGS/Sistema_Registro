/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Negocio;

import modelo.Persona;
import ec.edu.mcguaman.sistemaregistro.Datos.PersonaDAO;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class PersonaServicio {

    private static final Logger LOGGER = Logger.getLogger(PersonaServicio.class.getName());
    private final PersonaDAO personaDao;

    public PersonaServicio() {
        this.personaDao = new PersonaDAO();
    }

    /**
     * Agrega una nueva Persona tras validar edad y normalizar datos.
     *
     * @param persona La entidad Persona a agregar.
     * @return 0 si ya existe, 1 si éxito, 2 si error BD, 3 si menor de edad.
     */
    public int agregarPersona(Persona persona) {
        prepararParaGuardar(persona);
        if (persona.getEdad() < 18) {
            return 3;
        }
        return personaDao.registrarPersona(persona);
    }

    /**
     * Obtiene todas las Personas.
     */
    public List<Persona> obtenerPersonas() {
        return personaDao.listarPersonas();
    }

    /**
     * Busca una Persona por cédula.
     */
    public Persona buscarPersonaPorCedula(String cedula) {
        return personaDao.buscarPersonaPorCedula(cedula);
    }

    /**
     * Actualiza una Persona existente.
     */
    public boolean actualizarPersona(Persona persona) {
        prepararParaGuardar(persona);
        return personaDao.actualizarPersona(persona);
    }

    /**
     * Elimina una Persona por ID.
     */
    public boolean eliminarPersonaPorId(int id) {
        return personaDao.eliminarPersona(id);
    }

    /**
     * Normaliza datos y calcula edad.
     */
    private void prepararParaGuardar(Persona persona) {
        LocalDate fn = persona.getFecha_nacimiento();
        if (fn != null) {
            int edad = Period.between(fn, LocalDate.now()).getYears();
            persona.setEdad(edad);
            LOGGER.info("Edad calculada: " + edad);
        }
        if (persona.getNombre() != null) {
            persona.setNombre(persona.getNombre().toUpperCase());
        }
        if (persona.getApellido() != null) {
            persona.setApellido(persona.getApellido().toUpperCase());
        }
        if (persona.getCorreo() != null) {
            persona.setCorreo(persona.getCorreo().toLowerCase());
        }
    }

}
