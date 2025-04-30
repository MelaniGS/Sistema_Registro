/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Negocio;

import ec.edu.mcguaman.sistemaregistro.Datos.FacturaDAO;
import ec.edu.mcguaman.sistemaregistro.Datos.PersonaDAO;
import ec.edu.mcguaman.sistemaregistro.Datos.ProductoDAO;
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

    public Persona BuscarPersonaPorId(int id) {
        Persona personaEncontrada = this.personaDao.obtenerPersonaPorId(id);  // Buscar por ID
        if (personaEncontrada == null) {
            System.out.println("No existe esa persona con ese ID");
        } else {
            System.out.println("Se encontraron los detalles de la persona");
        }
        return personaEncontrada;
    }

    public Producto BuscarProductoPorId(int id) {
        // Buscar el producto por ID usando el método 'find' de JPA
        Producto productoEncontrado = this.productoDao.obtenerProductoPorId(id);  // Buscar por ID
        if (productoEncontrado == null) {
            System.out.println("No existe ese producto con ese ID");
        } else {
            System.out.println("Se encontraron los detalles del producto");
        }
        return productoEncontrado;  // Retornar el producto encontrado (o null si no se encuentra)
    }

    public Factura ObtenerFacturaCompleta(int idFactura) {
        return this.facturaDao.obtenerFacturaCompletaPorId(idFactura);
    }

    public void RegistrarNuevaFactura(Factura nuevaFactura) {
        this.facturaDao.RegistrarFactura(nuevaFactura);
    }

    private FacturaDAO facturaDAO = new FacturaDAO();  // Non-static instance

    // Method to save the invoice
    public boolean guardarFactura(Factura factura) {
        return facturaDAO.guardarFactura(factura);  // Call the non-static method
    }

}
