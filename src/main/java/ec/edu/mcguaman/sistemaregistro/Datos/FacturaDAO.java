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
        EntityManager em = persistenceUtil.getEntityManagerFactory().createEntityManager();  // Usando PersistenceUtil
        try {
            return em.createQuery(
                    "SELECT f FROM Factura f "
                    + "JOIN FETCH f.persona "
                    + "LEFT JOIN FETCH f.detalles d "
                    + "LEFT JOIN FETCH d.producto "
                    + "WHERE f.idFactura = :idFactura", Factura.class)
                    .setParameter("idFactura", idFactura)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Retorna null si no se encuentra la factura
        } finally {
            em.close();  // Asegúrate de cerrar el EntityManager después de usarlo
        }
    }

}
