/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Negocio;

import ec.edu.mcguaman.sistemaregistro.Datos.ProductoDAO;
import java.util.List;
import modelo.Persona;
import modelo.Producto;

/**
 *
 * @author ASUS
 */
public class ProductoServicio {

    private final ProductoDAO productoDao;

    public ProductoServicio() {
        this.productoDao = new ProductoDAO();
    }

    public int AgregarNuevoProducto(Producto producto) {
        return productoDao.RegistrarProducto(producto);
    }

    public List<Producto> ObtenerProducto() {
        return productoDao.obtenerProductos();
    }

    public boolean EliminarProductoPorId(int numId) {
        return productoDao.EliminarProducto(numId);
    }

    public boolean ActualizarProducto(int id, Producto producto) {
        return productoDao.ActualizarProducto(id, producto);
    }

    // Clase ProductoServicio
    public Producto BuscarProductoPorId(int id) {
        return productoDao.obtenerProductoPorId(id);  // Llama al método en ProductoDAO para obtener el producto por ID
    }
}
