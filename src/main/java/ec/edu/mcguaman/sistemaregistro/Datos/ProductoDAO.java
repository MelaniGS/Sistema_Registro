/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Datos;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import modelo.Producto;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class ProductoDAO {

    public void AgregarProducto(Producto producto) {

        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(producto);
            em.getTransaction().commit();

        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.err.println("Error de sesion de trabajo" + ex.getMessage());

        } finally {
            em.close();
        }
    }

    public List<Producto> obtenerProductos() {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        List<Producto> productos = new ArrayList<>();

        try {
            em.getTransaction().begin();
            productos = em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            System.err.println("Error al obtener productos: " + ex.getMessage());
        } finally {
            em.close();
        }

        return productos;
    }

    public int VerificarAgregarProducto(Producto productoAgregar) {
        int result = 0;
        // Inicia la sesion de trabajo con la base de datos
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Producto productoExiste = em.createQuery(
                    "SELECT p FROM Producto p WHERE p.codigo = :cod", Producto.class)
                    .setParameter("cod", productoAgregar.getCodigo())
                    .getSingleResult();

            if (productoExiste != null) {
                System.out.println("YA EXISTE EL PRODUCTO");
                em.close();
                return result;
            }
        } catch (NoResultException ex) {
            // Se inicia la transicion
            em.getTransaction().begin();
            // Se inserta la persona
            em.persist(productoAgregar);
            // Confirmar y guardar los cambios
            em.getTransaction().commit();
            result = 1;
        } catch (Exception ex) {
            // Revertir todo, no guardar nada
            em.getTransaction().rollback();
            System.err.println("Error de sesion de trabajo: " + ex.getMessage());
            result = 2;
        } finally {
            em.close();
        }
        return result;
    }

    public boolean ActualizarProducto(int id, Producto productoActualizar) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Producto existente = em.find(Producto.class, id);
            if (existente == null) {
                return false;
            }

            em.getTransaction().begin();
            existente.setNombre(productoActualizar.getNombre());
            existente.setCodigo(productoActualizar.getCodigo());
            existente.setPrecio(productoActualizar.getPrecio());
            // em.merge(personaActualizar);
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

    // Eliminar persona
    // Si retorna true se elimino el registro, false no se pudo eliminar
    public boolean EliminarProducto(int numId) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            Producto producto = em.find(Producto.class, numId);

            if (producto == null) {
                return false;
            }

            em.getTransaction().begin();
            em.remove(producto);
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

    public int RegistrarProducto(Producto productoAgregar) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();

        try {
            // 🔧 Corrección: usar COUNT(p) para obtener un Long
            Long count = em.createQuery(
                    "SELECT COUNT(p) FROM Producto p WHERE p.codigo = :cod", Long.class)
                    .setParameter("cod", productoAgregar.getCodigo())
                    .getSingleResult();

            if (count > 0) {
                return 0; // Ya existe el producto con ese código
            }

            em.getTransaction().begin();
            em.persist(productoAgregar);
            em.getTransaction().commit();

            return 1; // Insertado correctamente
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            ex.printStackTrace(); // ✅ Agrega trazabilidad del error
            return 2; // Error
        } finally {
            em.close();
        }
    }
    // Devuelve en una lista todos los productos
    public List<Producto> ListarProductosRegistrados() {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();  // Updated to use persistenceUtil
        try {
            return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
        } finally {
            em.close();  // Ensure to close the EntityManager
        }
    }

    public Producto BuscarProductoPorCodigo(String codigo) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();  // Updated to use persistenceUtil
        try {
            return em.createQuery("SELECT p FROM Producto p WHERE p.codigo = :cod", Producto.class)
                    .setParameter("cod", codigo)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;  // Return null if no result is found
        } finally {
            em.close();  // Ensure to close the EntityManager
        }
    }
    public Producto obtenerProductoPorId(int id) {
    EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
    try {
        // Buscar el producto por su ID usando el método find de JPA
        return em.find(Producto.class, id);  // Devuelve el producto que coincide con el ID
    } finally {
        em.close();  // Asegúrate de cerrar la conexión
    }
}

}
