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

    /**
     * Verifica si hay stock suficiente para la cantidad solicitada.
     *
     * @param codigo Código único del producto.
     * @param cantidad Cantidad que se quiere vender.
     * @return true si stock >= cantidad, false si no.
     */
    public boolean hayStock(String codigo, int cantidad) {
        Producto p = productoDao.buscarProductoPorCodigo(codigo);
        return p != null && p.getStock() >= cantidad;
    }

    /**
     * Descuenta la cantidad vendida del stock del producto.
     *
     * @param codigo Código del producto.
     * @param cantidad Cantidad a restar del inventario.
     * @return true si la operación tuvo éxito, false si el producto no existe o
     * no había suficiente stock.
     */
    public boolean descontarStock(String codigo, int cantidad) {
        Producto p = productoDao.buscarProductoPorCodigo(codigo);
        if (p == null) {
            return false;
        }
        // Llama al DAO para ajustar el stock (delta negativo)
        return productoDao.ajustarStock(p.getIdP(), -cantidad);
    }
}
