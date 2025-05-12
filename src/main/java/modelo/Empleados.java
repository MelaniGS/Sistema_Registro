/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;
import javax.persistence.*;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "empleados")
public class Empleados extends Persona {

    public enum RolEmpleado {
        Cajero,
        Administrador,
        Gerente
    }

    @Enumerated(EnumType.STRING)
    private RolEmpleado rol;

    private LocalDate fechaIngreso;

    private String direccion;

    private boolean activo;

    public Empleados() {

    }

    public Empleados(String nombre, String apellido, String correo, String telefono, LocalDate fecha_nacimiento, String cedula, int edad, RolEmpleado rol, LocalDate fechaIngreso, String direccion, boolean activo) {
        super(nombre, apellido, correo, telefono, fecha_nacimiento, cedula, edad);
        this.rol = rol;
        this.fechaIngreso = fechaIngreso;
        this.direccion = direccion;
        this.activo = activo;
    }

    public RolEmpleado getRol() {
        return rol;
    }

    public void setRol(RolEmpleado rol) {
        this.rol = rol;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

}
