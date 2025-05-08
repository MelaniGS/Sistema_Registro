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
@Table(name = "cliente")

public class Cliente extends Persona {

    private String direccion;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Fidelidad fidelidad;

    public Cliente() {

    }

    public Cliente(String nombre, String apellido, String correo, String telefono, LocalDate fecha_nacimiento, String cedula, String direccion) {
        super(nombre, apellido, correo, telefono, fecha_nacimiento, cedula);
        this.direccion = direccion;
    }

    public Cliente(String nombre, String apellido, String correo, String telefono, LocalDate fecha_nacimiento, String cedula, String direccion, Fidelidad fidelidad) {
        super(nombre, apellido, correo, telefono, fecha_nacimiento, cedula);
        this.direccion = direccion;
        this.fidelidad = fidelidad;
    }

    public Cliente(String nombre, String apellido, String correo, String telefono, LocalDate fecha_nacimiento, String cedula, int edad, String direccion, Fidelidad fidelidad) {
        super(nombre, apellido, correo, telefono, fecha_nacimiento, cedula, edad);
        this.direccion = direccion;
        this.fidelidad = fidelidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Fidelidad getFidelidad() {
        return fidelidad;
    }

    public void setFidelidad(Fidelidad fidelidad) {
        this.fidelidad = fidelidad;
    }

}
