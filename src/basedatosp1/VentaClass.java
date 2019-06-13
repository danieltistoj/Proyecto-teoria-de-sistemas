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
public class VentaClass {
    private int id_venta , id_usuario, id_cliente, num_productos;
    private float total_venta;
    private String hora_fecha;
    private Lista productos;

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getHora_fecha() {
        return hora_fecha;
    }

    public void setHora_fecha(String hora_fecha) {
        this.hora_fecha = hora_fecha;
    }

    public Lista getProductos() {
        return productos;
    }

    public void setProductos(Lista productos) {
        this.productos = productos;
    }

    public int getNum_productos() {
        return num_productos;
    }

    public void setNum_productos(int num_productos) {
        this.num_productos = num_productos;
    }

    public float getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(float total_venta) {
        this.total_venta = total_venta;
    }
    
    
    
    
    
}
