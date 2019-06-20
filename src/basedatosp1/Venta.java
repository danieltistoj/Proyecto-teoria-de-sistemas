/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatosp1;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Venta extends javax.swing.JFrame {
    private  float total = 0;
    private boolean aux_boolean;
    private int clienteId,telefonoCliente, id_aux, existencia_aux;
    private DefaultTableModel modeloProducto = new DefaultTableModel();
    private DefaultTableModel modeloProductoF = new DefaultTableModel();
    private DefaultTableModel modeloProductoH = new DefaultTableModel();
     private DefaultTableModel modeloDetalle= new DefaultTableModel();//modelo para detalle venta 
    private DefaultTableModel modelo;
    private DefaultTableModel modelo1;
    ArrayList<Producto> listaProductos = new ArrayList<>();
    ArrayList<Venta> listaVenta = new ArrayList<>();
    ArrayList<VentaClass> listaHistorial = new ArrayList<>();
    ArrayList<Producto> listaDetalle = new ArrayList<>();// array para detalle
   
    private Lista ventas, productos;
    public Venta() {
         
        ventas = new Lista();
        productos = new Lista();
        
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/icono.jpg")).getImage());
        setLocationRelativeTo(null);
        this.setResizable(false);
        Imagen img1 = new Imagen("/Imagenes/VentasFondo.jpg");
        Facturacion.setLocationRelativeTo(null);
        Facturacion.setTitle("Facturar");
        IngresarCliente.setLocationRelativeTo(null);
        IngresarCliente.setTitle("Ingresar Cliente");
        Dialog_DetalleVenta.setLocationRelativeTo(null);
        
        txtUsuarioF.setEnabled(false);
        txtTotal.setEnabled(false);
        txtnombre.setEnabled(false);
        txtprecio.setEnabled(false);
        txtID.setEnabled(false);
        txtnombreF.setEnabled(false);
        txtTlefonoF.setEnabled(false);
        txtemail.setEnabled(false);
        
        TablaHistorialVenta();
        setModelo();
        setModelFactura();
        setModelDetalle();
        Tabla();
    }
    
    public void setModelo(){
        String[] cabecera = {"id","nombre","precio","contidad"};
        modeloProducto.setColumnIdentifiers(cabecera);
        Tabla_productosC.setModel(modeloProducto);
        
    }
    
    public void setModelFactura(){
        String[] cabecera = {"nombre","contidad","total"};
        modeloProductoF.setColumnIdentifiers(cabecera);
        tabla_factura.setModel(modeloProductoF);
                
        
    }
    
    public void setDatosF(){
        Object[] datos = new Object[modeloProductoF.getColumnCount()];
        int i=1;
        modeloProductoF.setRowCount(0);
        for(Producto producto: listaProductos){
            datos[0] = producto.getNombre();
            datos[1] = producto.getCantidad();
            datos[2] = producto.getPrecio();
            
            i++;
            modeloProductoF.addRow(datos);
        }
        tabla_factura.setModel(modeloProductoF);
    }

    public void LlamarLista(){
        String nombre = txtnombre.getText();
        String precio = txtprecio.getText();
        String id = txtID.getText();
        String cantidad = txtCantida.getText();
       
        
        Producto producto = new Producto(Integer.parseInt(id),Integer.parseInt(cantidad),nombre,Float.parseFloat(precio));
        producto.setPrecioTotal(Integer.parseInt(cantidad)*Float.parseFloat(precio));
        
        Nodo nodo = new Nodo();
        nodo.setProducto(producto);
        productos.InsertarFondo(nodo);
        
        listaProductos.add(producto);
    }
    public void setDatos(){
        Object[] datos = new Object[modeloProducto.getColumnCount()];
        int i=1;
        modeloProducto.setRowCount(0);
        for(Producto producto: listaProductos){
            datos[0] = producto.getId_producto();
            datos[1] = producto.getNombre();
            datos[2] = producto.getPrecio();
            datos[3] = producto.getCantidad();
            i++;
            modeloProducto.addRow(datos);
        }
        Tabla_productosC.setModel(modeloProducto);
        
    }
    public void LimpiarCajasTxt(){
        txtCantida.setText(null);
        txtID.setText(null);
        txtTotal.setText(null);
        txtprecio.setText(null);
        txtnombre.setText(null);
        
        
    }
    public void LimpiarTablaC(){
        modeloProducto.setRowCount(0);
        Tabla_productosC.setModel(modeloProducto);
        listaProductos.clear();
        total=0;
        LimpiarCajasTxt();
        
        productos.setTope(null);
        productos.setFondo(null);
        productos.setSize(0);
        
    }
    public void Tabla(){
        
            Conexion con = new Conexion();
            Connection conexion = con.Conectar();
            
            String sql = "SELECT * FROM inventario";
            
            Statement st;
            
            modelo = new DefaultTableModel();
            modelo.addColumn("ID");
            modelo.addColumn("Nombre");
            modelo.addColumn("Costo");
            modelo.addColumn("Precio");
            modelo.addColumn("Existencias");
            
            Tabla_productosD.setModel(modelo);
            
            String[] dato = new String[5];
        try{
            st = conexion.createStatement();   
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()){
                dato[0] = result.getString(1);
                dato[1] = result.getString(2);
                dato[2] = result.getString(3);
                dato[3] = result.getString(4);
                dato[4] = result.getString(5);
                modelo.addRow(dato);
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //MODIFICAR INVENTARIO . esto es para cuando se cancela una compra en la cotizacion 
    public void ModificarInventario(Nodo producto){
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
       int existencia_actual  =   RetornarExistenciaReferencia(producto.getProducto().getNombre());
       
         try {
            PreparedStatement statement = conexion.prepareStatement("UPDATE inventario SET nombre='"+producto.getProducto().getNombre()
                    +"',precio='"+producto.getProducto().getPrecio()
                    +"',existencia='"+(existencia_actual+producto.getProducto().getCantidad())+"'WHERE ID='"+producto.getProducto().getId_producto()+"' ");
            statement.executeUpdate();
           
            Tabla();
            //JOptionPane.showMessageDialog(null,"Datos Actualizados","",JOptionPane.INFORMATION_MESSAGE);
            
               
         } catch (SQLException ex) {
             System.out.println("error al actualizar");
         }
    }
    //RETORNAR EXISTENCIA POR REFERENCIA 
   public int RetornarExistenciaReferencia(String nombre_producto){
        int exis_actual=0;
         String sql = "SELECT * FROM inventario"+" WHERE nombre='"+nombre_producto+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
           
            if(resultado.first()){
               String ex = resultado.getString("existencia");
               
               exis_actual = Integer.parseInt(ex);
            }
            
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"No se ha podido retornar existencia actual del producto","",JOptionPane.ERROR_MESSAGE);
             
         //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
      return exis_actual;
   }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Facturacion = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        txtNit = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtnombreF = new javax.swing.JTextField();
        txtTlefonoF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIDempleadoF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtUsuarioF = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_factura = new javax.swing.JTable();
        boton_cargar_factura = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtemail = new javax.swing.JTextField();
        boton_Facturar = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        IngresarCliente = new javax.swing.JDialog();
        txtnombreClien = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTelefonoClin = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtEmailClin = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        boton_agregar_cliente = new javax.swing.JButton();
        Dialog_DetalleVenta = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Table_Detalle = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        boto_agregarcompra = new javax.swing.JButton();
        boton_concelarcompra = new javax.swing.JButton();
        boton_finalizarcompra = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCantida = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        txtprecio = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        BotonTemp = new javax.swing.JToggleButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla_productosD = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla_productosC = new javax.swing.JTable();
        botonRegresar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaHistorial = new javax.swing.JTable();
        boton_DetalleDeVemta = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();

        Facturacion.setBounds(new java.awt.Rectangle(0, 0, 445, 930));

        txtNit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNitKeyTyped(evt);
            }
        });

        jLabel1.setText("NIT");

        jLabel2.setText("Nombre");

        txtnombreF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreFActionPerformed(evt);
            }
        });

        jLabel5.setText("Telefono");

        txtIDempleadoF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDempleadoFKeyTyped(evt);
            }
        });

        jLabel6.setText(" Id empleado");

        jLabel14.setText("nombre");

        jLabel15.setText("RUBIX");

        tabla_factura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tabla_factura);

        boton_cargar_factura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/cargar1.1.png"))); // NOI18N
        boton_cargar_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_cargar_facturaActionPerformed(evt);
            }
        });

        jLabel16.setText("Quetzaltenango, Quetzaltenango");

        jLabel4.setText("email");

        boton_Facturar.setBackground(new java.awt.Color(255, 255, 255));
        boton_Facturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/facturar2.1.png"))); // NOI18N
        boton_Facturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_FacturarActionPerformed(evt);
            }
        });

        jLabel23.setText("Cargar");

        jLabel24.setText("Facturar");

        jLabel25.setText("2da calle 19-22 zona 3");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(boton_Facturar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtUsuarioF, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtIDempleadoF, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                    .addComponent(txtemail, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel14)
                                .addGap(134, 134, 134))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(106, 106, 106)
                                .addComponent(jLabel24)
                                .addGap(125, 125, 125))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(23, 23, 23))
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(boton_cargar_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtnombreF, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtTlefonoF, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(jLabel16)))
                                .addGap(111, 111, 111))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(150, 150, 150))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(192, 192, 192))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(boton_cargar_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_Facturar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addGap(52, 52, 52)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnombreF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTlefonoF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(41, 41, 41)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIDempleadoF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtUsuarioF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout FacturacionLayout = new javax.swing.GroupLayout(Facturacion.getContentPane());
        Facturacion.getContentPane().setLayout(FacturacionLayout);
        FacturacionLayout.setHorizontalGroup(
            FacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        FacturacionLayout.setVerticalGroup(
            FacturacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FacturacionLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 36, Short.MAX_VALUE))
        );

        IngresarCliente.setBounds(new java.awt.Rectangle(0, 0, 278, 298));

        txtnombreClien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreClienActionPerformed(evt);
            }
        });
        txtnombreClien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtnombreClienKeyTyped(evt);
            }
        });

        jLabel17.setText("nombre");

        txtTelefonoClin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClinKeyTyped(evt);
            }
        });

        jLabel18.setText("telefono");

        jLabel19.setText("email");

        boton_agregar_cliente.setText("Agregar");
        boton_agregar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_agregar_clienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout IngresarClienteLayout = new javax.swing.GroupLayout(IngresarCliente.getContentPane());
        IngresarCliente.getContentPane().setLayout(IngresarClienteLayout);
        IngresarClienteLayout.setHorizontalGroup(
            IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IngresarClienteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel17)
                        .addComponent(jLabel18))
                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtEmailClin)
                    .addComponent(txtTelefonoClin)
                    .addComponent(txtnombreClien, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                .addGap(31, 31, 31))
            .addGroup(IngresarClienteLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(boton_agregar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        IngresarClienteLayout.setVerticalGroup(
            IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(IngresarClienteLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnombreClien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTelefonoClin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmailClin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(48, 48, 48)
                .addComponent(boton_agregar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        Dialog_DetalleVenta.setBounds(new java.awt.Rectangle(0, 0, 509, 700));

        Table_Detalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(Table_Detalle);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(285, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout Dialog_DetalleVentaLayout = new javax.swing.GroupLayout(Dialog_DetalleVenta.getContentPane());
        Dialog_DetalleVenta.getContentPane().setLayout(Dialog_DetalleVentaLayout);
        Dialog_DetalleVentaLayout.setHorizontalGroup(
            Dialog_DetalleVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        Dialog_DetalleVentaLayout.setVerticalGroup(
            Dialog_DetalleVentaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 39, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });

        jLabel3.setText("Buscar");

        jLabel7.setText("Total");

        boto_agregarcompra.setBackground(new java.awt.Color(255, 255, 255));
        boto_agregarcompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/agregar4.1.png"))); // NOI18N
        boto_agregarcompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boto_agregarcompraActionPerformed(evt);
            }
        });

        boton_concelarcompra.setBackground(new java.awt.Color(255, 255, 255));
        boton_concelarcompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/cancelar1.1.png"))); // NOI18N
        boton_concelarcompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_concelarcompraActionPerformed(evt);
            }
        });

        boton_finalizarcompra.setBackground(new java.awt.Color(255, 255, 255));
        boton_finalizarcompra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/facturar1.1.png"))); // NOI18N
        boton_finalizarcompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_finalizarcompraActionPerformed(evt);
            }
        });

        jLabel8.setText("Productos a comprar");

        jLabel9.setText("Productos disponibles");

        txtCantida.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidaKeyTyped(evt);
            }
        });

        jLabel10.setText("Cantidad");

        jLabel11.setText("nombre");

        jLabel12.setText("precio unidad");

        txtID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIDActionPerformed(evt);
            }
        });

        jLabel13.setText("ID");

        BotonTemp.setText("llamar las ventas");
        BotonTemp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonTempActionPerformed(evt);
            }
        });

        jLabel20.setText("Agregar");

        jLabel21.setText("Cancelar");

        jLabel22.setText("Facturar");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(BotonTemp)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel10)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(boto_agregarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(47, 47, 47)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCantida, javax.swing.GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                                    .addComponent(txtBuscar))
                                .addGap(285, 285, 285)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel21)
                                .addGap(106, 106, 106)
                                .addComponent(jLabel22))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(boton_concelarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(62, 62, 62)
                                .addComponent(boton_finalizarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(60, 60, 60)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtprecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                    .addComponent(txtnombre, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addComponent(jLabel20)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(boton_concelarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boton_finalizarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boto_agregarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCantida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(BotonTemp, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addContainerGap())
        );

        Tabla_productosD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Tabla_productosD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla_productosDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla_productosD);

        Tabla_productosC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(Tabla_productosC);

        botonRegresar.setBackground(new java.awt.Color(255, 255, 255));
        botonRegresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir1.1.png"))); // NOI18N
        botonRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(botonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cotización", jPanel2);

        TablaHistorial.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        TablaHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaHistorialMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TablaHistorial);

        boton_DetalleDeVemta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/detalle1.1.png"))); // NOI18N
        boton_DetalleDeVemta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_DetalleDeVemtaActionPerformed(evt);
            }
        });

        jLabel26.setText("Detalle Venta");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1051, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(482, 482, 482)
                        .addComponent(boton_DetalleDeVemta, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(496, 496, 496)
                .addComponent(jLabel26)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(boton_DetalleDeVemta, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66))
        );

        jTabbedPane1.addTab("Historial", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
//boton agregar 
    private void boto_agregarcompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boto_agregarcompraActionPerformed
        if(txtnombre.getText().length()!=0){// este  if ve si se a seleccionado un producto
       
        if(txtCantida.getText().length()!=0){// este if ve si se a ingresado una cantidad de producto
            if(existencia_aux==0){// este if ve si hay existencias del producto
                JOptionPane.showMessageDialog(null,"Ya no hay existencias de este producto","Error",JOptionPane.ERROR_MESSAGE);
            }
            else if(Integer.parseInt(txtCantida.getText())<=existencia_aux){//este else if ve si la cantidad ingresada es menor o igual a las existencias del producto
        LlamarLista();
        setDatos();
        float res = Integer.parseInt(txtCantida.getText()) * Integer.parseInt(txtprecio.getText());
        total+=res;
        txtTotal.setText(Float.toString(total));
           
       ModificarExistencia(RetornarExistencias());
            }
            else if(Integer.parseInt(txtCantida.getText())>existencia_aux){// si en dado caso la cantidad ingresada es mayor a las existencias, va a dar un error
                JOptionPane.showMessageDialog(null,"La cantidad que ingreso es mayor a las existencias del producto","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(null,"Ingrese una cantidad","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    else{
           JOptionPane.showMessageDialog(null,"Seleccione antes un producto","Error",JOptionPane.ERROR_MESSAGE);
      }
    }//GEN-LAST:event_boto_agregarcompraActionPerformed
//boton cancelar pedido
    private void boton_concelarcompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_concelarcompraActionPerformed
       if(Tabla_productosC.getRowCount()!=0){
        Nodo aux = productos.getTope();
        while(aux!=null){
            ModificarInventario(aux);
            aux = aux.getSig();
        }
        LimpiarTablaC();
       }
       else{
           JOptionPane.showMessageDialog(null,"No se ha realizado una cotizacion","Errro",JOptionPane.ERROR_MESSAGE);
       }
    }//GEN-LAST:event_boton_concelarcompraActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        String[] titulos = {"ID","Nombre","Costo","Precio","Existencias"};
        String[] registro = new String[5];
        
        String sql = "SELECT *FROM inventario WHERE ID LIKE '%" + txtBuscar.getText() + "%'"
                + "OR nombre LIKE '%" + txtBuscar.getText() + "%'"
                + "OR costo LIKE '%" + txtBuscar.getText() + "%'"
                + "OR precio LIKE '%" + txtBuscar.getText() + "%'"
                + "OR existencia LIKE '%" + txtBuscar.getText() + "%'";
        modelo = new DefaultTableModel(null,titulos);
        Conexion con = new Conexion();
        Connection conect = con.Conectar();
        try {
            Statement st = (Statement) conect.createStatement();
            ResultSet rs = st.executeQuery(sql);
           
            while(rs.next()){
                registro[0] = rs.getString("ID");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("costo");
                registro[3] = rs.getString("precio");
                registro[4] = rs.getString("existencia");
                modelo.addRow(registro);
            }
             Tabla_productosD.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("ERROR");
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void Tabla_productosDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla_productosDMouseClicked
        int fila = Tabla_productosD.getSelectedRow();
       txtID.setText(Tabla_productosD.getValueAt(fila,0).toString());
       txtnombre.setText(Tabla_productosD.getValueAt(fila, 1).toString());
       txtprecio.setText(Tabla_productosD.getValueAt(fila, 3).toString()); 
       existencia_aux = Integer.parseInt(Tabla_productosD.getValueAt(fila,4).toString());
       
    }//GEN-LAST:event_Tabla_productosDMouseClicked
 public void Agregarventa(){
     
   Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         
        try {
             String formato = "yyyy-MM-dd HH:mm:ss";
            DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
            LocalDateTime ahora = LocalDateTime.now();
            String FechaHora=formateador.format(ahora);
            
            String query ="INSERT INTO venta (Usuario_id,precio,idProducto,cliente_id,FechaHora) values(?,?,?,?,?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1,Integer.parseInt(txtIDempleadoF.getText()));
            statement.setFloat(2,total);
            statement.setInt(3,clienteId);
            statement.setInt(4,9);
            statement.setString(5,FechaHora);

            statement.executeUpdate();
            conexion.close(); 
            JOptionPane.showMessageDialog(null,"La venta se ha agregado","",JOptionPane.INFORMATION_MESSAGE); 
            
            //limpiar las cajas de texto
            total = 0;
            clienteId =0;
            txtIDempleadoF.setText(null);
            //Tabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al conectarse a la base de datos","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } 
    
}
    
    private void boton_finalizarcompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_finalizarcompraActionPerformed
        int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Alerta!", JOptionPane.YES_NO_OPTION);
        if(resp == 0){
            setDatosF();
            Facturacion.setVisible(true);
            
        }
        else{
     
            
        }
    }//GEN-LAST:event_boton_finalizarcompraActionPerformed

    private void botonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresarActionPerformed
        Menu menu = new Menu();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botonRegresarActionPerformed
 //Esta 
    public void BuscarUsuario(){
         
         String id = txtIDempleadoF.getText();
         String sql = "SELECT * FROM empleados"+" WHERE idempleado='"+id+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
           
            if(resultado.first()){
               String nombre1 = resultado.getString("nombreempleado");
               String email = resultado.getString("email_empleado");
              
               txtUsuarioF.setText(nombre1);
               txtemail.setText(email);     
                         
                        }
            
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Datos incorrectos","",JOptionPane.ERROR_MESSAGE);
             
            //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  public void IngresarCliente1(){
      
       Conexion con = new Conexion();
       Connection conexion = con.Conectar();
        try {
          
           
            String query ="INSERT INTO cliente (nit_cliente,nombre_cliente,telefono_cliente,email_cliente) values(?,?,?,?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1,txtNit.getText());
            statement.setString(2,txtnombreClien.getText());
            statement.setInt(3,Integer.parseInt(txtTelefonoClin.getText()) );
            statement.setString(4,txtEmailClin.getText());

            statement.executeUpdate();
            conexion.close(); 
            JOptionPane.showMessageDialog(null,"Cliente agregado","",JOptionPane.INFORMATION_MESSAGE); 
            
            //limpiar las cajas de texto
            /*
            txtNit.setText(null);
            txtnombreClien.setText(null);
            txtTlefonoF.setText(null);
            txtEmailClin.setText(null);
            */
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al conectarse a la base de datos","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } 
      
  }
  //Esta funcion modifica el producto del inventario cuando se agrega un producto en la cotizacion (se modifica la existencia)
  public void ModificarExistencia(int existen){
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         int nuevo_exist = existen-Integer.parseInt(txtCantida.getText());
         try {
            PreparedStatement statement = conexion.prepareStatement("UPDATE inventario SET existencia='"+nuevo_exist+"'WHERE nombre ='"+txtnombre.getText()+"'");
            statement.executeUpdate();
           
            Tabla();
            //JOptionPane.showMessageDialog(null,"Datos Actualizados","",JOptionPane.INFORMATION_MESSAGE);
           
               
         } catch (SQLException ex) {
             System.out.println("error al actualizar");
         }
      
  }
  //Retorna la existencia del producto seleccionado y se lo manda a ModificarExistencia. esta funcion es a la hora de agregar un producto en la cotizacion
  public int RetornarExistencias(){
         int exis_actual=0;
         String sql = "SELECT * FROM inventario"+" WHERE nombre='"+txtnombre.getText()+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
           
            if(resultado.first()){
               String ex = resultado.getString("existencia");
               
               exis_actual = Integer.parseInt(ex);
            }
            
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Datos incorrectos","",JOptionPane.ERROR_MESSAGE);
             
         //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
      return exis_actual;
  }
  //Esta funcion es para cargar los datos del cliente en la ventana facturacion 
  public void CargarFactura(){
      aux_boolean = false;
      BuscarUsuario();
         String nit = txtNit.getText();
         String sql = "SELECT * FROM cliente"+" WHERE nit_cliente='"+nit+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
           
            if(resultado.first()){
               String nombre = resultado.getString("nombre_cliente");
               String id = resultado.getString("id_cliente");
               String telefono = resultado.getString("telefono_cliente");
               
               clienteId= Integer.parseInt(id);
               telefonoCliente = Integer.parseInt(telefono);
               
               
               txtnombreF.setText(nombre);
               txtTlefonoF.setText(telefono);
                   
                    
                   
                        }
            else{
                IngresarCliente.setVisible(true);
                
            }
            
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Datos incorrectos","",JOptionPane.ERROR_MESSAGE);
             
         //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
      
  }
    private void boton_cargar_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_cargar_facturaActionPerformed
        
        if(txtNit.getText().length()!=0&&txtID.getText().length()!=0){
        //aux_boolean=false;
        CargarFactura();
        }
        else{
            JOptionPane.showMessageDialog(null,"Llene cada campo","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_boton_cargar_facturaActionPerformed

    private void txtnombreClienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreClienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreClienActionPerformed

    private void boton_agregar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_agregar_clienteActionPerformed
        IngresarCliente1();
        IngresarCliente.dispose();
    }//GEN-LAST:event_boton_agregar_clienteActionPerformed
    //actualizar tabla de historial 
    public void TablaHistorialVenta(){
            Conexion con = new Conexion();
            Connection conexion = con.Conectar();
            
            String sql = "SELECT * FROM venta";
            
            Statement st;
            DefaultTableModel modelo1;
            modelo1 = new DefaultTableModel();
            modelo1.addColumn("ID Venta");
            modelo1.addColumn("ID Empleado");
            modelo1.addColumn("ID Cliente");
            modelo1.addColumn("Fecha Hora");
            
            
            TablaHistorial.setModel(modelo1);
            
            String[] dato = new String[4];
        try{
            st = conexion.createStatement();   
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()){
                dato[0] = result.getString(1);
                dato[1] = result.getString(2);
                dato[2] = result.getString(3);
                dato[3] = result.getString(4);
                modelo1.addRow(dato);
            }
            TablaHistorial.setModel(modelo1);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
    public void setModelDetalle(){
        String[] cabecera = {"ID","Nombre","Precio","Cantidad","Total"};
        modeloDetalle.setColumnIdentifiers(cabecera);
        Table_Detalle.setModel(modeloDetalle);
                    
    }
 public void LlamarListaDetalle(Producto producto){
     listaDetalle.add(producto);
     
 }
 
 public void setDatosDetalle(){
     Object[] datos = new Object[modeloDetalle.getColumnCount()];
        int i=1;
        modeloDetalle.setRowCount(0);
        for(Producto producto: listaDetalle){
            datos[0] = producto.getId_producto();
            datos[1] = producto.getNombre();
            datos[2] = producto.getPrecio();
            datos[3] = producto.getCantidad();
            datos[4] = producto.getPrecioTotal();
            i++;
            modeloDetalle.addRow(datos);
        }
        Table_Detalle.setModel(modeloDetalle);
    
} 
    public Lista Productos(Lista productos){
        Lista nuevaLista = new Lista();
        Nodo aux = productos.getTope();
        while(aux!=null){
            nuevaLista.InsertarFondo(aux);
            aux = aux.getSig();
        }
        return nuevaLista;
    }
    //ingresa los datos de la facturacion a la tabla venta en la base de datos
    public void IngresarVentaBaseDatos(int id_cliente,int id_usuario, String FechaHora) {
        
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
        try {
           
            String query ="INSERT INTO venta (Usuario_id,cliente_id,FechaHora) values(?,?,?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1,id_usuario);
            statement.setInt(2,id_cliente);
            statement.setString(3,FechaHora);

            statement.executeUpdate();
            conexion.close(); 
            JOptionPane.showMessageDialog(null,"Producto agregado correctamente","",JOptionPane.INFORMATION_MESSAGE); 
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al conectarse a la base de datos","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } 
      
    }
    //Con esta funcion retornamos el id de la venta a la hora de facturar para luego poder relacionarla cono los productos que se vendieron 
    public int ObtenerIdVenta(){
        int id_producto =0;
            Conexion con = new Conexion();
            Connection conexion = con.Conectar();
            String sql = "SELECT * FROM venta";
            Statement st;         
            //String[] dato = new String[5];
           
        try{
            st = conexion.createStatement();   
            ResultSet result = st.executeQuery(sql);
            int conta=0;
            
            //este primer while es para contar cuantas ventas hay, para luego ponerselo al vector que va a contener a los IDs 
            while(result.next()){
               result.getString(1);
               conta++; 
            }
          //Al vector se le ingresa el valor como esta, y no si por ejemplo conta es 9 no se pone 8 (conta-1), esto porque resulta un error 
          //al final para retornar el ultimo valor si se pone el contador en este caso conta2 como (conta-1)
            String[] ids = new String[conta];
            // debe de volverce a hacer la consulta por que el result se queda digamos vacio.
            //.........................................................
           String sql2 = "SELECT * FROM venta";
           Statement st2;
            try {
                int conta2=0;
                st2 = conexion.createStatement();
                ResultSet result2 = st2.executeQuery(sql2);
                while(result2.next()){
                    ids[conta2] =result2.getString(1);//ingresamos los IDs de ventas en el vector 
                    conta2++;
                }
                id_producto = Integer.parseInt(ids[conta-1]);
            } catch (Exception e) {
                System.out.println("erro ***");
                 Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, e);
            }
            
         //................................................................  
         
        } catch (SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
       return id_producto;
    }
    
    //esta funcion relaciona todos los productos con la venta. Las relaciones las va ingresando en detalleventa.
    public void RelacionarProductoVenta(int id_venta, int id_producto){
        Conexion con = new Conexion();
         Connection conexion = con.Conectar();
        try {
           
            String query ="INSERT INTO detalleventa (venta_id,producto_id) values(?,?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1,id_venta);
            statement.setInt(2,id_producto);
           
            statement.executeUpdate();
            conexion.close(); 
            System.out.println("relacion hecha con exito");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al conectarse a la base de datos","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } 
        
    }
 // es el boton que termina con la venta, y realiza la facturacion 
    private void boton_FacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_FacturarActionPerformed
         String formato = "yyyy-MM-dd HH:mm:ss";
         DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
         LocalDateTime ahora = LocalDateTime.now();
         String FechaHora=formateador.format(ahora); //fecha y hora 
         
         Nodo aux = productos.getTope();
       
         int id_cliente = clienteId;
         int id_usuario = Integer.parseInt(txtIDempleadoF.getText());
         IngresarVentaBaseDatos(id_cliente, id_usuario, FechaHora);
         
         int id_venta = ObtenerIdVenta();
         while(aux!=null){
             RelacionarProductoVenta(id_venta,aux.getProducto().getId_producto());
             aux = aux.getSig();
         }
         
         
         
      /*   
      VentaClass venta = new VentaClass();
      Nodo nodo = new Nodo();
      venta.setId_cliente(clienteId);
      venta.setId_usuario(Integer.parseInt(txtIDempleadoF.getText()));
      venta.setHora_fecha(FechaHora);
     
      venta.setProductos(Productos(productos));
      float aux_total = Float.parseFloat(txtTotal.getText());
      venta.setTotal_venta(aux_total);
         
              
      nodo.setVenta(venta);
      ventas.InsertarFondo(nodo);
      Nodo aux1 = ventas.getTope();
      
        LlamarListaHistorial(venta);
        setDatosHistorial();
        */
        LimpiarTablaC();
         
    }//GEN-LAST:event_boton_FacturarActionPerformed

    private void TablaHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaHistorialMouseClicked
         int fila = TablaHistorial.getSelectedRow();
         id_aux = Integer.parseInt(TablaHistorial.getValueAt(fila,0).toString());
       
    }//GEN-LAST:event_TablaHistorialMouseClicked
 public void ListaIdProductos(){
     String sql = "SELECT *FROM detalleventa WHERE venta_id LIKE '%" + id_aux + "%'";
                
       // modelo = new DefaultTableModel(null,titulos);
        Conexion con = new Conexion();
        Connection conect = con.Conectar();
        try {
            Statement st = (Statement) conect.createStatement();
            ResultSet rs = st.executeQuery(sql);
           int conta=0;
            while(rs.next()){
                System.out.println(rs.getString("producto_id")); 
             
            }
           
        } catch (SQLException ex) {
            System.out.println("ERROR");
        }
 }   
 
 public void LimpiarTablaDetalle(){
     modeloDetalle.setRowCount(0);
     Table_Detalle.setModel(modeloDetalle);
 }
 
    private void boton_DetalleDeVemtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_DetalleDeVemtaActionPerformed
     
        
        
        
        
        
        /*
       Dialog_DetalleVenta.setVisible(true);
       Nodo aux = ventas.getTope();
        System.out.println(id_aux);
       while(aux!=null){
           System.out.println(aux.getVenta().getId_venta());
           if(id_aux==aux.getVenta().getId_venta()){
               
              Nodo aux2 = aux.getVenta().getProductos().getTope();
               System.out.println(aux2);
              while(aux2!=null){
                  
                  System.out.println(aux2.getProducto().getNombre());
                  LlamarListaDetalle(aux2.getProducto());
                  setDatosDetalle();
                  aux2 = aux2.getSig();
              }
               aux = aux.getSig();
           }
       }
       */
    }//GEN-LAST:event_boton_DetalleDeVemtaActionPerformed

    private void txtnombreFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreFActionPerformed

    private void BotonTempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonTempActionPerformed
         System.out.println(ObtenerIdVenta());
    }//GEN-LAST:event_BotonTempActionPerformed
   //es para que en la cantidad no ingresen letras 
    private void txtCantidaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidaKeyTyped
       char validar = evt.getKeyChar();
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo ingrese numeros","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtCantidaKeyTyped

    private void txtNitKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNitKeyTyped
        char validar = evt.getKeyChar();
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo ingrese numeros","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNitKeyTyped

    private void txtIDempleadoFKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDempleadoFKeyTyped
        char validar = evt.getKeyChar();
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo ingrese numeros","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtIDempleadoFKeyTyped

    private void txtnombreClienKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtnombreClienKeyTyped
       char validar = evt.getKeyChar();
       if(Character.isDigit(validar)){
           getToolkit().beep();
           evt.consume();
           JOptionPane.showMessageDialog(null,"Solo ingrese Letras","",JOptionPane.ERROR_MESSAGE);
       }
    }//GEN-LAST:event_txtnombreClienKeyTyped

    private void txtTelefonoClinKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClinKeyTyped
         char validar = evt.getKeyChar();
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo ingrese numeros","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtTelefonoClinKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Venta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BotonTemp;
    private javax.swing.JDialog Dialog_DetalleVenta;
    private javax.swing.JDialog Facturacion;
    private javax.swing.JDialog IngresarCliente;
    private javax.swing.JTable TablaHistorial;
    private javax.swing.JTable Tabla_productosC;
    private javax.swing.JTable Tabla_productosD;
    private javax.swing.JTable Table_Detalle;
    private javax.swing.JButton boto_agregarcompra;
    private javax.swing.JButton botonRegresar;
    private javax.swing.JButton boton_DetalleDeVemta;
    private javax.swing.JButton boton_Facturar;
    private javax.swing.JButton boton_agregar_cliente;
    private javax.swing.JButton boton_cargar_factura;
    private javax.swing.JButton boton_concelarcompra;
    private javax.swing.JButton boton_finalizarcompra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tabla_factura;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCantida;
    private javax.swing.JTextField txtEmailClin;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtIDempleadoF;
    private javax.swing.JTextField txtNit;
    private javax.swing.JTextField txtTelefonoClin;
    private javax.swing.JTextField txtTlefonoF;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtUsuarioF;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtnombreClien;
    private javax.swing.JTextField txtnombreF;
    private javax.swing.JTextField txtprecio;
    // End of variables declaration//GEN-END:variables
}
