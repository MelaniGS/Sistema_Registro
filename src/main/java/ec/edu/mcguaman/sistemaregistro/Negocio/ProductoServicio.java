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

    public boolean EliminarProductoPorIdP(int numIdP) {
        return productoDao.EliminarProducto(numIdP);
    }

    public boolean ActualizarProducto(int idP, Producto producto) {
        return productoDao.ActualizarProducto(idP, producto);
    }

    public Producto BuscarProductoPorId(int idP) {
        return productoDao.obtenerProductoPorId(idP);  // Llama al método en ProductoDAO para obtener el producto por ID
    }
}
