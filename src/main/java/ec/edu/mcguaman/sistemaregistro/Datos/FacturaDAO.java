package ec.edu.mcguaman.sistemaregistro.Datos;

import java.util.logging.Logger;
import javax.persistence.*;
import modelo.Factura;
import util.persistenceUtil;

/**
 *
 * @author ASUS
 */
public class FacturaDAO {

    private static final Logger LOGGER = Logger.getLogger(FacturaDAO.class.getName());

    /**
     * 0 = éxito, 1 = error
     */
    public int registrarFactura(Factura factura) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(factura);
            em.getTransaction().commit();
            return 0;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.severe("Error al registrar Factura: " + ex.getMessage());
            return 1;
        } finally {
            em.close();
        }
    }

    public Factura obtenerFacturaCompletaPorId(int idFactura) {
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.createQuery(
                    "SELECT f FROM Factura f "
                    + "JOIN FETCH f.persona p "
                    + "LEFT JOIN FETCH f.detalles d "
                    + "LEFT JOIN FETCH d.producto pr "
                    + "WHERE f.idFactura = :idFactura", Factura.class)
                    .setParameter("idFactura", idFactura)
                    .getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.warning("Factura no encontrada con ID: " + idFactura);
            return null;
        } catch (Exception ex) {
            LOGGER.severe("Error al obtener Factura: " + ex.getMessage());
            return null;
        } finally {
            em.close();
        }
    }

}
