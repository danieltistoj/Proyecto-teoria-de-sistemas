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
public class Nodo {
    private Nodo sig;
    private VentaClass venta;
    private Producto producto;

    public Nodo getSig() {
        return sig;
    }

    public void setSig(Nodo sig) {
        this.sig = sig;
    }

    public VentaClass getVenta() {
        return venta;
    }

    public void setVenta(VentaClass venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    
    
    
    
}
