/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Negocio;

import ec.edu.mcguaman.sistemaregistro.Datos.EmpleadoDAO;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.logging.Logger;
import modelo.Empleados;

/**
 *
 * @author ASUS
 */
public class EmpleadoServicio {

    private static final Logger LOGGER = Logger.getLogger(EmpleadoServicio.class.getName());
    private final EmpleadoDAO empleadoDao;

    public EmpleadoServicio() {
        this.empleadoDao = new EmpleadoDAO();
    }

    /**
     * Agrega un Empleado tras validar edad y normalizar datos.
     *
     * @param empleado La entidad Empleados a agregar.
     * @return 0=ya existe, 1=éxito, 2=error BD, 3=menor de edad.
     */
    public int agregarEmpleado(Empleados empleado) {
        prepararParaGuardar(empleado);
        if (empleado.getEdad() < 18) {
            return 3;
        }
        return empleadoDao.registrarEmpleado(empleado);
    }

    /**
     * Lista todos los Empleados.
     */
    public List<Empleados> listarEmpleados() {
        return empleadoDao.listarEmpleados();
    }

    /**
     * Busca un Empleado por cédula.
     */
    public Empleados buscarEmpleadoPorCedula(String cedula) {
        return empleadoDao.buscarEmpleadoPorCedula(cedula);
    }

    /**
     * Actualiza un Empleado existente.
     */
    public boolean actualizarEmpleado(Empleados empleado) {
        prepararParaGuardar(empleado);
        return empleadoDao.actualizarEmpleado(empleado);
    }

    /**
     * Elimina un Empleado por ID.
     */
    public boolean eliminarEmpleado(int id) {
        return empleadoDao.eliminarEmpleado(id);
    }

    /**
     * Normaliza datos y calcula edad.
     */
    private void prepararParaGuardar(Empleados empleado) {
        LocalDate fn = empleado.getFecha_nacimiento();
        if (fn != null) {
            int edad = Period.between(fn, LocalDate.now()).getYears();
            empleado.setEdad(edad);
            LOGGER.info("Edad Empleado: " + edad);
        }
        if (empleado.getNombre() != null) {
            empleado.setNombre(empleado.getNombre().toUpperCase());
        }
        if (empleado.getApellido() != null) {
            empleado.setApellido(empleado.getApellido().toUpperCase());
        }
        if (empleado.getDireccion() != null) {
            empleado.setDireccion(empleado.getDireccion().toUpperCase());
        }
    }
}
