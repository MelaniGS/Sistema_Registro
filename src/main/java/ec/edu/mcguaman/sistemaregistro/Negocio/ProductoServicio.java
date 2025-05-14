/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Negocio;

import ec.edu.mcguaman.sistemaregistro.Datos.ProductoDAO;
import java.util.List;
import java.util.logging.Logger;
import modelo.Persona;
import modelo.Producto;

/**
 *
 * @author ASUS
 */
public class ProductoServicio {

    private static final Logger LOGGER = Logger.getLogger(ProductoServicio.class.getName());
    private final ProductoDAO productoDao;

    public ProductoServicio() {
        this.productoDao = new ProductoDAO();
    }

    public int agregarProducto(Producto producto) {
        prepararParaGuardar(producto);
        int res = productoDao.registrarProducto(producto);
        switch (res) {
            case 1 ->
                LOGGER.info("Producto registrado: " + producto.getCodigo());
            case 0 ->
                LOGGER.warning("Ya existe producto con código: " + producto.getCodigo());
            case 2 ->
                LOGGER.severe("Error al registrar producto: " + producto.getCodigo());
        }
        return res;
    }

    public List<Producto> listarProductos() {
        return productoDao.listarProductos();
    }

    public Producto buscarProductoPorCodigo(String codigo) {
        Producto p = productoDao.buscarProductoPorCodigo(codigo);
        if (p == null) {
            LOGGER.warning("Producto no encontrado código: " + codigo);
        }
        return p;
    }

    public boolean actualizarProducto(Producto producto) {
        prepararParaGuardar(producto);
        boolean ok = productoDao.actualizarProducto(producto);
        if (ok) {
            LOGGER.info("Producto actualizado: " + producto.getCodigo());
        } else {
            LOGGER.warning("No se pudo actualizar producto: " + producto.getCodigo());
        }
        return ok;
    }

    public boolean eliminarProductoPorCodigo(String codigo) {
        Producto p = productoDao.buscarProductoPorCodigo(codigo);
        if (p == null) {
            LOGGER.warning("No se encontró producto a eliminar código: " + codigo);
            return false;
        }
        boolean ok = productoDao.eliminarProducto(p.getIdP());
        if (ok) {
            LOGGER.info("Producto eliminado código: " + codigo);
        } else {
            LOGGER.severe("Error al eliminar producto código: " + codigo);
        }
        return ok;
    }

    private void prepararParaGuardar(Producto producto) {
        if (producto.getCodigo() != null) {
            producto.setCodigo(producto.getCodigo().trim().toUpperCase());
        }
        if (producto.getNombre() != null) {
            producto.setNombre(producto.getNombre().trim());
        }
    }

    public boolean hayStock(String codigo, int cantidad) {
        Producto p = productoDao.buscarProductoPorCodigo(codigo);
        if (p == null) {
            LOGGER.warning("Producto no encontrado para el código: " + codigo);
            return false;  // Producto no encontrado
        }
        if (p.getStock() < cantidad) {
            LOGGER.warning("Stock insuficiente para el producto " + codigo + ". Stock disponible: " + p.getStock());
            return false;  // No hay suficiente stock
        }
        return true;  // Hay stock suficiente
    }

    public boolean descontarStock(String codigo, int cantidad) {
        Producto p = buscarProductoPorCodigo(codigo);
        if (p == null) {
            return false;
        }
        if (p.getStockActual() < cantidad) {
            return false;
        }
        p.descontarStock(cantidad);  // Actualiza el stockActual
        return productoDao.actualizarProducto(p);  // Guardamos la actualización
    }

}
