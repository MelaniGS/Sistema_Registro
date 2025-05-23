/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Datos;

import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import modelo.Producto;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class ProductoDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());

    public int registrarProducto(Producto producto) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            long count = em.createQuery(
                    "SELECT COUNT(p) FROM Producto p WHERE p.codigo = :cod", Long.class)
                    .setParameter("cod", producto.getCodigo())
                    .getSingleResult();
            if (count > 0) {
                return 0;
            }

            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();
            return 1;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.severe("Error al registrar Producto: " + ex.getMessage());
            return 2;
        } finally {
            em.close();
        }
    }

    public List<Producto> listarProductos() {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Producto p", Producto.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Producto buscarProductoPorCodigo(String codigo) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Producto p WHERE p.codigo = :cod", Producto.class)
                    .setParameter("cod", codigo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    // Método para actualizar el stock de un producto
    public boolean actualizarProducto(Producto producto) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Producto p = em.find(Producto.class, producto.getIdP());
            if (p == null) {
                return false;
            }
            em.getTransaction().begin();
            p.setStockActual(producto.getStockActual()); // Actualizar stockActual
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    public boolean eliminarProducto(int idP) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Producto prod = em.find(Producto.class, idP);
            if (prod == null) {
                return false;
            }
            em.getTransaction().begin();
            em.remove(prod);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.severe("Error al eliminar Producto: " + ex.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Ajusta el stock de un producto dado su ID.
     *
     * @param idP ID interno del producto.
     * @param delta Cambio en stock (negativo para restar).
     * @return true si se aplicó y el stock resultante es >= 0.
     */
    public boolean ajustarStock(int idP, int delta) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Producto p = em.find(Producto.class, idP);
            if (p == null || p.getStock() + delta < 0) {
                return false;
            }
            em.getTransaction().begin();
            p.setStock(p.getStock() + delta);
            em.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            em.close();
        }
    }

    // Dentro de la clase ProductoDAO
    public boolean hayStock(String codigo, int cantidad) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Producto p = em.createQuery("SELECT p FROM Producto p WHERE p.codigo = :cod", Producto.class)
                    .setParameter("cod", codigo)
                    .getSingleResult();
            return p != null && p.getStock() >= cantidad; // Verifica si hay suficiente stock
        } catch (NoResultException ex) {
            return false; // Si no se encuentra el producto, retornamos false
        } finally {
            em.close();
        }
    }

}
