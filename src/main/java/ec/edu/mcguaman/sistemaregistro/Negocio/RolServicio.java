/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.mcguaman.sistemaregistro.Negocio;

import ec.edu.mcguaman.sistemaregistro.Datos.RolDAO;
import java.util.List;
import modelo.RolEmpleado;

/**
 *
 * @author ASUS
 */
public class RolServicio {

    private final RolDAO rolDAO;

    public RolServicio() {
        this.rolDAO = new RolDAO();
    }

    public List<RolEmpleado> listarRoles() {
        return rolDAO.listarRoles();
    }

    public boolean agregarRol(RolEmpleado rol) {
        return rolDAO.agregarRol(rol);
    }

    public boolean eliminarRol(int id) {
        return rolDAO.eliminarRol(id);
    }

}
