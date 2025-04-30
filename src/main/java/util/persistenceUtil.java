/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 *
 * @author ASUS
 */
public class persistenceUtil {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("SistemaRegistroUsuario");
    
    public static EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }
    
    public static void Cerrar (){
        if(emf != null && emf.isOpen()){
            emf.close();
        }
    }
}
