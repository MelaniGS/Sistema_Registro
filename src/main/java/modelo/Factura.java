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
    @JoinColumn(name = "idpersona")
    private Persona persona;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    private List<DetalleFactura> detalles;

    @Column
    private float montoTotal;  // Añadimos la propiedad para el monto total

    public Factura() {
    }

    public Factura(Persona persona, List<DetalleFactura> detalles) {
        this.persona = persona;
        this.detalles = detalles;
        for (DetalleFactura detalle : detalles) {
            detalle.setFactura(this);
        }
        this.montoTotal = calcularMontoTotal();  // Calcular el monto total al momento de crear la factura
    }

    public Factura(int idFactura, Persona persona, List<DetalleFactura> detalles) {
        this.idFactura = idFactura;
        this.persona = persona;
        this.detalles = detalles;
        this.montoTotal = calcularMontoTotal();  // Calcular el monto total al momento de crear la factura
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
        this.montoTotal = calcularMontoTotal();  // Actualizar el monto total si los detalles cambian
    }

    public float getMontoTotal() {
        return montoTotal;
    }

    // Método para calcular el monto total sumando los detalles
    public float calcularMontoTotal() {
        float total = 0;
        for (DetalleFactura detalle : detalles) {
            total += detalle.getTotal();
        }
        return total;
    }

    // Método para asignar el monto total, si es necesario (aunque lo calculamos automáticamente)
    public void setMontoTotal(float montoTotal) {
        this.montoTotal = montoTotal;
    }
}
