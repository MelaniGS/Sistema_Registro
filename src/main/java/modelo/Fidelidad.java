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
@Table(name = "fidelidad")

public class Fidelidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFidelidad;

    @OneToOne
    @JoinColumn(name = "id_cliente", nullable = false, unique = true)
    private Cliente cliente;

    @Column(nullable = false)
    private int puntosAcumulados;

    @Column(nullable = false)
    private int puntosGastados;

    @Column(nullable = false)
    private LocalDate fechaAfiliacion;

    public Fidelidad() {

    }

    public Fidelidad(int idFidelidad, Cliente cliente, int puntosAcumulados, int puntosGastados, LocalDate fechaAfiliacion) {
        this.idFidelidad = idFidelidad;
        this.cliente = cliente;
        this.puntosAcumulados = puntosAcumulados;
        this.puntosGastados = puntosGastados;
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public Fidelidad(Cliente cliente, int puntosAcumulados, int puntosGastados, LocalDate fechaAfiliacion) {
        this.cliente = cliente;
        this.puntosAcumulados = puntosAcumulados;
        this.puntosGastados = puntosGastados;
        this.fechaAfiliacion = fechaAfiliacion;
    }

    public int getIdFidelidad() {
        return idFidelidad;
    }

    public void setIdFidelidad(int idFidelidad) {
        this.idFidelidad = idFidelidad;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public int getPuntosGastados() {
        return puntosGastados;
    }

    public void setPuntosGastados(int puntosGastados) {
        this.puntosGastados = puntosGastados;
    }

    public LocalDate getFechaAfiliacion() {
        return fechaAfiliacion;
    }

    public void setFechaAfiliacion(LocalDate fechaAfiliacion) {
        this.fechaAfiliacion = fechaAfiliacion;
    }

}
