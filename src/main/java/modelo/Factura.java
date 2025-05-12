/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFactura;

    @ManyToOne
    @JoinColumn(name = "idpersona", nullable = false)
    private Persona persona;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleFactura> detalles;

    @Column(nullable = false)
    private double montoTotal;

    public Factura() {}

    public Factura(Persona persona, List<DetalleFactura> detalles) {
        this.persona = persona;
        this.detalles = detalles;
        this.detalles.forEach(d -> d.setFactura(this));
        this.montoTotal = calcularMontoTotal();
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<DetalleFactura> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles;
        this.detalles.forEach(d -> d.setFactura(this));
        this.montoTotal = calcularMontoTotal();
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double calcularMontoTotal() {
        return detalles.stream()
                       .mapToDouble(DetalleFactura::getTotal)
                       .sum();
    }
}
