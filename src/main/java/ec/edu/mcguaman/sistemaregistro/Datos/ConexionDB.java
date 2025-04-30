/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Datos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class ConexionDB {
    
    public static Connection AbrirConexion(){
        try{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/persona_database", "root", "");
        }catch(SQLException ex){
            return null;
        }
    }
    
    public static void CerrarConexion(Connection conn){
        if(conn != null){
            try{
                conn.close();
            }catch(SQLException ex){
                System.out.println(ex+ "no se cerro la conexion");
            }
        }
    }
}
