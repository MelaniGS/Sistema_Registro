/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import javax.persistence.*;

/**
 *
 * @author ASUS
 */

@Entity
@Table (name= "usuario")
public class Persona {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable=false)
    private String nombre;
    
    @Column(nullable=false)
    private String apellido;
    
    @Column(nullable=false)
    private String correo;
    
    @Column(nullable=false)
    private String telefono;
    
    @Column(nullable=false)
    private LocalDate fecha_nacimiento;
    
    @Column(nullable=false)
    private String cedula;
    
    @Column
    private int edad;
    
    

    public Persona() {
    }

    public Persona(String nombre, String apellido, String correo, String telefono, LocalDate fecha_nacimiento, String cedula) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.cedula = cedula;
    }

    public Persona(int id, String nombre, String apellido, String correo, String telefono, LocalDate fecha_nacimiento, String cedula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.cedula = cedula;
    }

    public Persona(String nombre, String apellido, String correo, String telefono, LocalDate fecha_nacimiento, String cedula, int edad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.fecha_nacimiento = fecha_nacimiento;
        this.cedula = cedula;
        this.edad = edad;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public void CalcularEdad () {
        Period periodo = Period.between(this.fecha_nacimiento, LocalDate.now());
        this.edad = periodo.getYears();
    }
    
}
