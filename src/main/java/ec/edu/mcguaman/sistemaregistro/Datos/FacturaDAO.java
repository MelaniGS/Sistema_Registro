package ec.edu.mcguaman.sistemaregistro.Datos;

import javax.persistence.*;
import modelo.Factura;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class FacturaDAO {

    private static EntityManagerFactory emf;

    static {
        emf = Persistence.createEntityManagerFactory("SistemaRegistroUsuario");
    }

    // This method returns the EntityManager for interacting with the database
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // [0] registro exitoso [1] ocurrio un error 
    public int RegistrarFactura(Factura facturaAgregar) {
        // Inicia la sesion de trabajo con la base de datos
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();  // Using your PersistenceUtil
        try {
            // Se inicia la transicion
            em.getTransaction().begin();

            // Se inserta la persona
            em.persist(facturaAgregar);

            // Confirmar y guardar los cambios
            em.getTransaction().commit();
            return 0;
        } catch (Exception ex) {
            // Revertir todo, no guardar nada
            em.getTransaction().rollback();
            System.err.println("Error de sesion de trabajo: " + ex.getMessage());
            return 1;
        } finally {
            em.close();  // Make sure to close the entity manager
        }
    }

    public Factura obtenerFacturaCompletaPorId(int idFactura) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();  // Using your PersistenceUtil
        try {
            return em.createQuery(
                    "SELECT f FROM Factura f "
                    + "JOIN FETCH f.persona "
                    + "LEFT JOIN FETCH f.detalles d "
                    + "LEFT JOIN FETCH d.producto "
                    + "WHERE f.id = :idFactura", Factura.class)
                    .setParameter("idFactura", idFactura)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();  // Ensure entity manager is closed after use
        }
    }

    public Factura guardarFactura(Factura factura) {
        EntityManager em = getEntityManager();  // Asumiendo que tienes un EntityManager configurado

        try {
            em.getTransaction().begin();  // Iniciar transacción
            em.persist(factura);  // Guardar la factura
            em.getTransaction().commit();  // Confirmar la transacción
            return factura;  // Devolver la factura con el ID generado
        } catch (Exception e) {
            em.getTransaction().rollback();  // Si ocurre un error, hacer rollback
            e.printStackTrace();
            return null;
        } finally {
            em.close();  // Cerrar la conexión
        }
    }

    public Factura buscarFacturaPorId(int id) {
        EntityManager em = getEntityManager();  // Asegúrate de tener un EntityManager configurado
        return em.find(Factura.class, id);  // Devuelve la factura por su ID
    }

}
