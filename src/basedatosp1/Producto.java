/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatosp1;

/**
 *
 * @author Usuario
 */
public class Producto {
    private int  id_producto, cantidad;
    private float precio, precioTotal, costo;
    private String nombre;

    public Producto(int id_producto,int cantidad, String nombre, float precio) {
       
        this.precio = precio;
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costa) {
        this.costo = costa;
    }
    
    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(float precioTotal) {
        this.precioTotal = precioTotal;
    }

   

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
    
    
}
