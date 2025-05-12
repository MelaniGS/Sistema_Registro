/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Negocio;

import ec.edu.mcguaman.sistemaregistro.Datos.FacturaDAO;
import ec.edu.mcguaman.sistemaregistro.Datos.PersonaDAO;
import ec.edu.mcguaman.sistemaregistro.Datos.ProductoDAO;
import java.util.List;
import java.util.logging.Logger;
import modelo.DetalleFactura;
import modelo.Factura;
import modelo.Persona;
import modelo.Producto;

/**
 *
 * @author ASUS
 */
public class FacturaServicio {

    private static final Logger LOGGER = Logger.getLogger(FacturaServicio.class.getName());
    private final PersonaDAO personaDao;
    private final ProductoDAO productoDao;
    private final FacturaDAO facturaDao;

    public FacturaServicio() {
        this.personaDao = new PersonaDAO();
        this.productoDao = new ProductoDAO();
        this.facturaDao = new FacturaDAO();
    }

    public Persona buscarPersonaPorCedula(String cedula) {
        Persona p = personaDao.buscarPersonaPorCedula(cedula);
        if (p == null) {
            LOGGER.warning("Persona no encontrada cédula: " + cedula);
        }
        return p;
    }

    public Producto buscarProductoPorCodigo(String codigo) {
        Producto p = productoDao.buscarProductoPorCodigo(codigo);
        if (p == null) {
            LOGGER.warning("Producto no encontrado código: " + codigo);
        }
        return p;
    }

    public Factura obtenerFacturaCompleta(int idFactura) {
        Factura f = facturaDao.obtenerFacturaCompletaPorId(idFactura);
        if (f == null) {
            LOGGER.warning("Factura no encontrada ID: " + idFactura);
        }
        return f;
    }

    /**
     * @return 0 si éxito, 1 si error (persona nula, sin detalles o excepción)
     */
    public int registrarFactura(Factura factura) {
        if (factura.getPersona() == null) {
            LOGGER.warning("Factura sin persona asociada");
            return 1;
        }
        List<DetalleFactura> detalles = factura.getDetalles();
        if (detalles == null || detalles.isEmpty()) {
            LOGGER.warning("Factura sin detalles");
            return 1;
        }
        // asegurar cascada
        detalles.forEach(d -> d.setFactura(factura));
        factura.setMontoTotal(calcularMontoTotal(factura));

        int res = facturaDao.registrarFactura(factura);
        if (res == 0) {
            LOGGER.info("Factura registrada ID: " + factura.getIdFactura());
        } else {
            LOGGER.severe("Error al registrar factura");
        }
        return res;
    }

    public double calcularMontoTotal(Factura factura) {
        return (float) factura.getDetalles()
                .stream()
                .mapToDouble(DetalleFactura::getTotal)
                .sum();
    }

}
