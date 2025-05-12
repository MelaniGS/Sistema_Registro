/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.List;
import javax.persistence.*;

/**
 *
 * @author ASUS
 */
@Entity
@Table(name = "detalle_factura")

public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDetalle;

    @ManyToOne
    @JoinColumn(name = "idFactura", nullable = false)
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "idP", nullable = false)
    private Producto producto;

    @Column(nullable = false)
    private int cantidad;

    @Column(nullable = false)
    private float precioUnitario;

    @Column(nullable = false)
    private float total;

    public DetalleFactura() {
    }

    public DetalleFactura(Producto producto, int cantidad, float precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = cantidad * precioUnitario;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        recalcTotal();
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
        recalcTotal();
    }
    
    public void setTotal(float total) {
        this.total = total;
    }
    
    public float getTotal() {
        return total;
    }

    private void recalcTotal() {
        this.total = this.cantidad * this.precioUnitario;
    }
}
