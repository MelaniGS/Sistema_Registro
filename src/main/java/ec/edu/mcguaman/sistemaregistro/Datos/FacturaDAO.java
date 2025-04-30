package ec.edu.mcguaman.sistemaregistro.Datos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import modelo.Factura;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class FacturaDAO {

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

    public boolean guardarFactura(Factura factura) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(factura);  // Save the invoice in the database
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

}
