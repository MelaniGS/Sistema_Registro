/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Negocio;

import ec.edu.mcguaman.sistemaregistro.Datos.ClienteDAO;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.logging.Logger;
import modelo.Cliente;

/**
 *
 * @author ASUS
 */
public class ClienteServicio {

    private static final Logger LOGGER = Logger.getLogger(ClienteServicio.class.getName());
    private final ClienteDAO clienteDao;

    public ClienteServicio() {
        this.clienteDao = new ClienteDAO();
    }

    /**
     * Agrega un Cliente tras validar edad y normalizar datos.
     *
     * @param cliente La entidad Cliente a agregar.
     * @return 0=ya existe, 1=éxito, 2=error BD, 3=menor de edad.
     */
    public int agregarCliente(Cliente cliente) {
        prepararParaGuardar(cliente);
        if (cliente.getEdad() < 18) {
            return 3;
        }
        return clienteDao.registrarCliente(cliente);
    }

    /**
     * Lista todos los Clientes.
     */
    public List<Cliente> listarClientes() {
        return clienteDao.listarClientes();
    }

    /**
     * Busca un Cliente por cédula.
     */
    public Cliente buscarClientePorCedula(String cedula) {
        return clienteDao.buscarClientePorCedula(cedula);
    }

    /**
     * Actualiza un Cliente existente.
     */
    public boolean actualizarCliente(Cliente cliente) {
        prepararParaGuardar(cliente);
        return clienteDao.actualizarCliente(cliente);
    }

    /**
     * Elimina un Cliente por ID.
     */
    public boolean eliminarCliente(int id) {
        return clienteDao.eliminarCliente(id);
    }

    /**
     * Normaliza datos y calcula edad.
     */
    private void prepararParaGuardar(Cliente cliente) {
        LocalDate fn = cliente.getFecha_nacimiento();
        if (fn != null) {
            int edad = Period.between(fn, LocalDate.now()).getYears();
            cliente.setEdad(edad);
            LOGGER.info("Edad Cliente: " + edad);
        }
        if (cliente.getNombre() != null) {
            cliente.setNombre(cliente.getNombre().toUpperCase());
        }
        if (cliente.getApellido() != null) {
            cliente.setApellido(cliente.getApellido().toUpperCase());
        }
        if (cliente.getDireccion() != null) {
            cliente.setDireccion(cliente.getDireccion().toUpperCase());
        }
    }
}
