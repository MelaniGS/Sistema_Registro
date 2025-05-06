/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Negocio;

import ec.edu.mcguaman.sistemaregistro.Datos.FacturaDAO;
import ec.edu.mcguaman.sistemaregistro.Datos.PersonaDAO;
import ec.edu.mcguaman.sistemaregistro.Datos.ProductoDAO;
import modelo.DetalleFactura;
import modelo.Factura;
import modelo.Persona;
import modelo.Producto;

/**
 *
 * @author ASUS
 */
public class FacturaServicio {

    private final PersonaDAO personaDao;
    private final ProductoDAO productoDao;
    private final FacturaDAO facturaDao;

    public FacturaServicio() {
        this.personaDao = new PersonaDAO();
        this.productoDao = new ProductoDAO();
        this.facturaDao = new FacturaDAO();
    }

    public Persona BuscarPersonaPorCedula(String cedula) {
        Persona personaEncontrada = this.personaDao.obtenerPersonaPorCed(cedula);
        if (personaEncontrada == null) {
            System.out.println("No existe esa persona con ese num de cedula");
        } else {
            System.out.println("Se encontrodo los detalles de la persona");
        }
        return personaEncontrada;
    }

    public Producto BuscarProductoPorCodigo(String codigo) {
        Producto productoEncontrado = this.productoDao.BuscarProductoPorCodigo(codigo);
        if (productoEncontrado == null) {
            System.out.println("No existe ese producto con ese num de codigo");
        } else {
            System.out.println("Se encontrodo los detalles del producto");
        }
        return productoEncontrado;
    }

    public Factura ObtenerFacturaCompleta(int idFactura) {
        return this.facturaDao.obtenerFacturaCompletaPorId(idFactura);
    }

    public void RegistrarNuevaFactura(Factura nuevaFactura) {
        // Asegurarnos de que el monto total se calcule antes de registrar la factura
        nuevaFactura.setMontoTotal(nuevaFactura.calcularMontoTotal());
        this.facturaDao.RegistrarFactura(nuevaFactura);
    }

    public float calcularMontoTotal(Factura factura) {
        float montoTotal = 0.0f;

        for (DetalleFactura detalle : factura.getDetalles()) {
            montoTotal += detalle.getTotal();
        }

        return montoTotal;
    }

}
