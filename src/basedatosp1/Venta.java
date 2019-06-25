/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatosp1;


import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;
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
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Venta extends javax.swing.JFrame {
    //los estring sirven para ir guardando datos que luego que ingresaran en un pdf
    private String contenido_encebezado = "                RUBIX   \n"+"Guatemala, Quetzaltenango, Quetzaltenango\n"
   ,contenido_Empleado="***Empleado***\n",conte_cliente="***Cliente***\n", contenido_productos="\n\n***Producto***",contenidofinal;
    private  float total = 0;
    private boolean aux_boolean;
    // las variables idE_temp y idC_temp se utilizan como variables temporales a la hora de buscar los datos de un empleado o cliente. Se utiliza en Detalle Venta 
    private int clienteId,telefonoCliente, id_aux, existencia_aux, idE_temp, idC_temp;
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
   
    private Lista ventas, productos, productos2;
    public Venta() {
        ventas = new Lista();
        productos = new Lista();
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/icono.jpg")).getImage());
        setLocationRelativeTo(null);
        setTitle("RUBIX / VENTAS");
        this.setResizable(false);
        //Imagen img1 = new Imagen("/Imagenes/VentasFondo.jpg", 1078, 760);
        //this.jPanel2.add(img1);
        this.jPanel2.repaint();
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
        
        boto_agregarcompra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_concelarcompra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_finalizarcompra.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_DetalleDeVemta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        boton_cargar_factura.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_Facturar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Boton_cancelar_factura.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        boton_agregar_cliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Boton_cancelar_cliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        boton_buscar_archivo_.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_generar_pdf_.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        botonRegresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        TablaHistorialVenta();
        setModelo();
        setModelFactura();
        setModelDetalle();
        Tabla();
        Cerrar();
       CerrarFacturacion();
       CerrarNuevoCliente();
    }
    //en esta funcion hace que la ventana de venta no se cierre 
    public void Cerrar(){
        try {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e){
                    confirmarSalida();
                    
                }
               });
            this.setVisible(true);
        } catch (Exception e) {
        }
    }
    //es para poner una condicion a la hora de cerrar la ventana 
    public void confirmarSalida(){
        int confirmar = JOptionPane.showConfirmDialog(this,"Esta seguro de cerra la aplicacion?. Perdera la cotizacion que haya realizado","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(confirmar==JOptionPane.YES_OPTION){
               if(Tabla_productosC.getRowCount()!=0){
                Nodo aux = productos.getTope();
                while(aux!=null){
                    ModificarInventario(aux);
                    aux = aux.getSig();
                }
                LimpiarTablaC();
               }
               System.exit(0);
        }
        
    }
    //botn para que no se cierre el dialog de facturacion 
        public void CerrarFacturacion(){
        try {
            Facturacion.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            this.setVisible(true);
        } catch (Exception e) {
        }
    }
        //funcion que sirve para que no se cierre el dialog de nuevo cliente 
        public void CerrarNuevoCliente(){
            try {
                IngresarCliente.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                
            } catch (Exception e) {
            }
        }
    
       
    public void setModelo(){
        String[] cabecera = {"ID","Nombre","Precio","Cantidad"};
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
    //Limpia la tabla de productos a comprar
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
        Boton_cancelar_factura = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        IngresarCliente = new javax.swing.JDialog();
        txtnombreClien = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtTelefonoClin = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtEmailClin = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        boton_agregar_cliente = new javax.swing.JButton();
        Boton_cancelar_cliente = new javax.swing.JToggleButton();
        Dialog_DetalleVenta = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Table_Detalle = new javax.swing.JTable();
        label_nombreE = new javax.swing.JLabel();
        cuiE = new javax.swing.JLabel();
        TelefonoE = new javax.swing.JLabel();
        CorreoE = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        nombreClabel = new javax.swing.JLabel();
        label_nit = new javax.swing.JLabel();
        label_telefonoC = new javax.swing.JLabel();
        emailClien_label = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        txt_URL_pdf = new javax.swing.JTextField();
        boton_buscar_archivo_ = new javax.swing.JToggleButton();
        boton_generar_pdf_ = new javax.swing.JToggleButton();
        jLabel31 = new javax.swing.JLabel();
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
        txtCantida = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        txtprecio = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla_productosD = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla_productosC = new javax.swing.JTable();
        botonRegresar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
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

        jLabel15.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 51));
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

        Boton_cancelar_factura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/cancelar1.1.png"))); // NOI18N
        Boton_cancelar_factura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_cancelar_facturaActionPerformed(evt);
            }
        });

        jLabel8.setText("Cancelar");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(127, 127, 127))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtnombreF, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(127, 127, 127))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTlefonoF, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(127, 127, 127))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(152, 152, 152))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(187, 187, 187))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                                        .addComponent(boton_cargar_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(65, 65, 65)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(boton_Facturar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabel24)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addComponent(jLabel8))
                                            .addComponent(txtUsuarioF, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Boton_cancelar_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(23, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(jLabel23)))
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
                                        .addComponent(jLabel16)
                                        .addGap(125, 125, 125))))))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(boton_cargar_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_Facturar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(Boton_cancelar_factura, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel23)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addGap(54, 54, 54)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(txtNit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(0, 28, Short.MAX_VALUE))
        );

        IngresarCliente.setBounds(new java.awt.Rectangle(0, 0, 278, 310));

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

        boton_agregar_cliente.setBackground(new java.awt.Color(255, 255, 255));
        boton_agregar_cliente.setForeground(new java.awt.Color(255, 255, 255));
        boton_agregar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/agregar_cliente1.1.png"))); // NOI18N
        boton_agregar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_agregar_clienteActionPerformed(evt);
            }
        });

        Boton_cancelar_cliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/cancelar1.2.png"))); // NOI18N
        Boton_cancelar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Boton_cancelar_clienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout IngresarClienteLayout = new javax.swing.GroupLayout(IngresarCliente.getContentPane());
        IngresarCliente.getContentPane().setLayout(IngresarClienteLayout);
        IngresarClienteLayout.setHorizontalGroup(
            IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IngresarClienteLayout.createSequentialGroup()
                .addGroup(IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(IngresarClienteLayout.createSequentialGroup()
                        .addContainerGap(48, Short.MAX_VALUE)
                        .addGroup(IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel17)
                                .addComponent(jLabel18))
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtEmailClin)
                            .addComponent(txtTelefonoClin)
                            .addComponent(txtnombreClien, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)))
                    .addGroup(IngresarClienteLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(boton_agregar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Boton_cancelar_cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31))
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
                .addGap(36, 36, 36)
                .addGroup(IngresarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Boton_cancelar_cliente, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boton_agregar_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(48, Short.MAX_VALUE))
        );

        Dialog_DetalleVenta.setBounds(new java.awt.Rectangle(0, 0, 595, 930));

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
        Table_Detalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_DetalleMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(Table_Detalle);

        label_nombreE.setText("Nombre Empleado: ");

        cuiE.setText("CUI Empleado: ");

        TelefonoE.setText("Telefono Empleado:");

        CorreoE.setText("Email Empleado:");

        jLabel27.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 102, 255));
        jLabel27.setText("Datos Empleado");

        jLabel28.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 102, 255));
        jLabel28.setText("Datos Cliente");

        nombreClabel.setText("Nombre:");

        label_nit.setText("Nit:");

        label_telefonoC.setText("Telefono:");

        emailClien_label.setText("Email:");

        jLabel29.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 102, 225));
        jLabel29.setText("Productos");

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txt_URL_pdf.setBackground(new java.awt.Color(204, 204, 204));

        boton_buscar_archivo_.setText("Buscar");
        boton_buscar_archivo_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_buscar_archivo_ActionPerformed(evt);
            }
        });

        boton_generar_pdf_.setText("Generar");
        boton_generar_pdf_.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_generar_pdf_ActionPerformed(evt);
            }
        });

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/pdf2.1.png"))); // NOI18N
        jLabel31.setText("jLabel31");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(boton_buscar_archivo_, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_URL_pdf, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(boton_generar_pdf_, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(118, 118, 118))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(boton_buscar_archivo_, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_URL_pdf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(boton_generar_pdf_, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(emailClien_label)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(nombreClabel)
                                .addGap(175, 175, 175)
                                .addComponent(label_nit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(label_telefonoC))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TelefonoE)
                                    .addComponent(label_nombreE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cuiE)
                                    .addComponent(CorreoE))))
                        .addGap(97, 97, 97))))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGap(223, 223, 223)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel27)
                                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel28)
                                        .addComponent(jLabel29)))))
                        .addGap(0, 14, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jLabel27)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cuiE)
                            .addComponent(label_nombreE))
                        .addGap(97, 97, 97)
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombreClabel)
                            .addComponent(label_nit)
                            .addComponent(label_telefonoC)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CorreoE)
                            .addComponent(TelefonoE))))
                .addGap(18, 18, 18)
                .addComponent(emailClien_label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                .addComponent(jLabel29)
                .addGap(33, 33, 33)
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
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            .addGap(0, 42, Short.MAX_VALUE)
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

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        jLabel20.setText("Agregar");

        jLabel21.setText("Cancelar");

        jLabel22.setText("Facturar");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(jLabel20))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(boto_agregarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(boton_concelarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                    .addGap(12, 12, 12)
                                    .addComponent(jLabel21)))
                            .addComponent(txtCantida, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(74, 74, 74)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel22))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(boton_finalizarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtnombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtprecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(boto_agregarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_concelarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boton_finalizarcompra, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtCantida, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22))))
        );

        Tabla_productosD.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        Tabla_productosC.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        jLabel9.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 153, 255));
        jLabel9.setText("Productos Disponibles");

        jLabel30.setFont(new java.awt.Font("Arial", 1, 16)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 153, 225));
        jLabel30.setText("Producto A Vender");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(212, 212, 212)
                                .addComponent(jLabel9)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(191, 191, 191)
                                .addComponent(jLabel30)
                                .addGap(99, 99, 99)
                                .addComponent(botonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel30))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(botonRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Cotización", jPanel2);

        TablaHistorial.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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
       int confirmar = JOptionPane.showConfirmDialog(null,"Esta seguro que quiere cancelar ?","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
       if(confirmar==JOptionPane.YES_OPTION){
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
       }
    }//GEN-LAST:event_boton_concelarcompraActionPerformed

    private void txtIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIDActionPerformed
// es para buscar los productos del inventario, se buscan en la tabla de productos disponibles 
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
 
    private void boton_finalizarcompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_finalizarcompraActionPerformed
      if(this.Tabla_productosC.getRowCount() == 0 && this.Tabla_productosC.getSelectedRow() == -1){
          JOptionPane.showMessageDialog(this,"Debe de realizar una cotizacion antes de facturar","Error",JOptionPane.ERROR_MESSAGE);
      }
      else{
        int resp = JOptionPane.showConfirmDialog(null, "¿Esta seguro?", "Alerta!", JOptionPane.YES_NO_OPTION);
        if(resp == 0){
            setDatosF();
            Facturacion.setVisible(true);  
        }
      }
    }//GEN-LAST:event_boton_finalizarcompraActionPerformed

    private void botonRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegresarActionPerformed

        if(this.Tabla_productosC.getRowCount() == 0 && this.Tabla_productosC.getSelectedRow() == -1){
        Menu menu = new Menu();
        menu.setVisible(true);
        this.dispose();
         
        }
        else{
            int respuesta = JOptionPane.showConfirmDialog(null,"Si sale la cotizacion que tenga se perdera","Alerta!",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(respuesta == JOptionPane.YES_OPTION){
                Nodo aux = productos.getTope();
                while(aux!=null){
                    ModificarInventario(aux);// se modifica producto por producto para que regrese las existencias a como estaban antes de agregar el producto a la tabla de cotizacion
                    aux = aux.getSig();
                }
                LimpiarTablaC();// se limpia la tabla de cotizacion 
                    
                    Menu menu = new Menu();
                    menu.setVisible(true);
                    this.dispose();
            }
            
        }
              
        
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
    public void RelacionarProductoVenta(int id_venta, int id_producto,float precio_total,int cantidad){
        Conexion con = new Conexion();
         Connection conexion = con.Conectar();
        try {
           
            String query ="INSERT INTO detalleventa (venta_id,producto_id,cantidad,Total) values(?,?,?,?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setInt(1,id_venta);
            statement.setInt(2,id_producto);
            statement.setInt(3,cantidad );
            statement.setFloat(4,precio_total);
           
            statement.executeUpdate();
            conexion.close(); 
            System.out.println("relacion hecha con exito");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al conectarse a la base de datos","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } 
        
    }
    public void LimpiarCajasTextoFactura(){
        txtNit.setText(null);
        txtnombreF.setText(null);
        txtUsuarioF.setText(null);
        txtemail.setText(null);
        txtIDempleadoF.setText(null);
        txtTlefonoF.setText(null);
        
    }
 // es el boton que termina con la venta, y realiza la facturacion 
    private void boton_FacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_FacturarActionPerformed
         if(txtnombreF.getText().length()!=0){
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
             RelacionarProductoVenta(id_venta,aux.getProducto().getId_producto(),aux.getProducto().getPrecioTotal(),aux.getProducto().getCantidad());
             aux = aux.getSig();
         }
        LimpiarTablaC();
        TablaHistorialVenta();
        LimpiarCajasTextoFactura();
        Facturacion.dispose();
         }
         else{
             JOptionPane.showMessageDialog(null,"Debe de cargar para cumplir con todos los campos de la factura","Error",JOptionPane.ERROR_MESSAGE);
         }
    }//GEN-LAST:event_boton_FacturarActionPerformed

    private void TablaHistorialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaHistorialMouseClicked
        contenidofinal="";
        contenido_encebezado = "                RUBIX   \n"+"Guatemala, Quetzaltenango, Quetzaltenango\n";
        contenido_Empleado="***Empleado***\n";
        conte_cliente="***Cliente***\n";
        contenido_productos="\n\n***Producto***";
        
         int fila = TablaHistorial.getSelectedRow();
         id_aux = Integer.parseInt(TablaHistorial.getValueAt(fila,0).toString());
         idE_temp = Integer.parseInt(TablaHistorial.getValueAt(fila,1).toString());
         idC_temp = Integer.parseInt(TablaHistorial.getValueAt(fila,2).toString());
         contenido_encebezado += TablaHistorial.getValueAt(fila,3).toString()+"\n";//se le une al encabezado la fecha y hora 
         contenido_encebezado+="No. "+id_aux+"\n\n";
         
       
    }//GEN-LAST:event_TablaHistorialMouseClicked
     
    //con esta funcion encontramos todos id de los productos relacionados con una venta
    public ArrayList  ListaIdProductos(){
     String sql = "SELECT *FROM detalleventa WHERE venta_id LIKE '%" + id_aux + "%'";
      ArrayList<String> id_productos = new ArrayList<>();          
       // modelo = new DefaultTableModel(null,titulos);
        Conexion con = new Conexion();
        Connection conect = con.Conectar();
       int conta = 0;
        try {
            Statement st = (Statement) conect.createStatement();
            ResultSet rs = st.executeQuery(sql);
           while(rs.next()){
               id_productos.add(rs.getString("producto_id"));
               conta++;
           } 
           
        } catch (SQLException ex) {
            System.out.println("ERROR");
        }
        
      return id_productos;
 }   
 //llenamos el array list con los productos que se hicienron en cada venta 
    public void DetallesProductos(int id){
         Producto producto = null;
      
         int cantidad;
         String sql = "SELECT * FROM inventario"+" WHERE ID='"+id+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
           
            if(resultado.first()){
               String nombre1 = resultado.getString("nombre");
               float precio_total = RetornarTotalDetalleVenta(id);// se llama a la funcioon que retorna el total de la compra de un producto (detalleventa)
               cantidad = RetornarCantiDetalleVenta(id);//se llama a la funcion que retorna la cantidad que se conpro del producto (detalleventa)
               float precio_unidad = Float.parseFloat(resultado.getString("precio"));
               contenido_productos+="\n"+nombre1+" --------------------- Q"+precio_total;
              
               producto = new Producto(id,cantidad,nombre1,precio_unidad);// se inicializa la el objeto producto 
               producto.setPrecioTotal(precio_total);//se modifica el total del precio ya que no se ingresa en el constructor de la clase 
                        }
            
            
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Datos incorrectos","",JOptionPane.ERROR_MESSAGE);
             
            //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
         listaDetalle.add(producto);// se ingresa el producto o el objeto en el array list
    }
    //retorna la cantidad que se compro de ese producto
     public int RetornarCantiDetalleVenta(int id){
         int cantida=0;
         String sql = "SELECT * FROM detalleventa"+" WHERE venta_id='"+id_aux+"' && producto_id='"+id+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
           
            if(resultado.first()){
               cantida = Integer.parseInt(resultado.getString("cantidad"));
                        }
            
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Datos incorrectos","",JOptionPane.ERROR_MESSAGE);
             
            //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
         
       return cantida;
    }
     //retorna el total del prodcuto contidad * precio 
     public float RetornarTotalDetalleVenta(int id){
         float total1 = 0;
         String sql = "SELECT * FROM detalleventa"+" WHERE venta_id='"+id_aux+"' && producto_id='"+id+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
           
            if(resultado.first()){
               total1 = Float.parseFloat(resultado.getString("Total"));
                    
                        }
            
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Datos incorrectos","",JOptionPane.ERROR_MESSAGE);
             
            //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
         return total1;
    }
     //se ingresan los productos al modelo de detalle de venta, que se han ingresando antes en un array list 
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
    // con esta funcion se le ponen los encabezados a cado columna de la tabla de detalle de venta 
     public void setModelDetalle(){
        String[] cabecera = {"ID","Nombre","Precio","Cantidad","Total"};
        modeloDetalle.setColumnIdentifiers(cabecera);
        Table_Detalle.setModel(modeloDetalle);
                    
    }
 // con esta funcion se limpia la tabla detalle de venta
 public void LimpiarTablaDetalle(){
     modeloDetalle.setRowCount(0);
     Table_Detalle.setModel(modeloDetalle);
     listaDetalle.clear();
 }
 
  public void BuscarEmpleadoDetalle(){
         
         String nombre1="";
         String sql = "SELECT * FROM empleados"+" WHERE idempleado='"+idE_temp+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
           
            if(resultado.first()){
               nombre1 = "Nombre Empleado: "+resultado.getString("nombreempleado");
               String email ="Email Empleado: "+ resultado.getString("email_empleado");
               String cui ="CUI Empleado: "+resultado.getString("cui");
               String telefono ="Telefono Empleado: "+resultado.getString("telefono");
              label_nombreE.setText(nombre1);
              cuiE.setText(cui);
              CorreoE.setText(email);
              TelefonoE.setText(telefono);
              contenido_Empleado+="\n"+nombre1+"----"+email+"\n"+cui+"----"+telefono; // se agrega los datos del empleado a esta variable para prepadarla para un pdf
             
                         
                        }
            else{
                System.out.println("no entro");
            }
            
            
        } catch (SQLException ex) {
             System.out.println("error");
             
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  public void BuscarClienteDetalle(){
       String nombre1="";
         String sql = "SELECT * FROM cliente"+" WHERE id_cliente='"+idC_temp+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
           
            if(resultado.first()){
                nombreClabel.setText("Nombre: "+resultado.getString("nombre_cliente"));
                label_nit.setText("Nit: "+resultado.getString("nit_cliente"));
                label_telefonoC.setText("Telefono: "+resultado.getString("telefono_cliente"));
                emailClien_label.setText("Email: "+resultado.getString("email_cliente"));
             
                conte_cliente+="\n"+nombreClabel.getText()+"----"+label_nit.getText()+"\n"+label_telefonoC.getText()+"----"+emailClien_label.getText();// se prepara la variable para agregarla a un pdf
                        }
            else{
                System.out.println("no entro");
            }
            
            
        } catch (SQLException ex) {
             System.out.println("error");
             
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
      
  }
 //Este boton agrega los productos de la venta en la tabla que esta en el dialog: Dialog_DetalleVenta 
    private void boton_DetalleDeVemtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_DetalleDeVemtaActionPerformed
    if(TablaHistorial.getSelectedRow()>=0){
      LimpiarTablaDetalle();
      Dialog_DetalleVenta.setVisible(true);
      ArrayList<String> listaCopiada = new ArrayList<String>(ListaIdProductos());
      int size = listaCopiada.size();
      int conta =0;
      //con este ciclo se van ingresando los productos a la lista detalle
      while(conta<size){
          DetallesProductos(Integer.parseInt(listaCopiada.get(conta)));
          conta++;
      }
     
      setDatosDetalle();//con esta funcion se ingresan los productos de la Listadetalle a la tabla de historia 
      //System.out.println(idE_temp);
      BuscarEmpleadoDetalle();// se busca el empleado segun el empleado para pasar sus datos al Label correspondiente 
      BuscarClienteDetalle();// con esta funcion se busca el cliente para pasar sus datos al Label correspondiente 
      contenidofinal = contenido_encebezado+contenido_Empleado+"\n\n"+conte_cliente+contenido_productos;//se unen todos los encabezados 
        System.out.println(contenidofinal);
    }
    else{
        JOptionPane.showMessageDialog(this,"Debe de seleccionar una venta","Advertencia",JOptionPane.WARNING_MESSAGE);
    }
      
    }//GEN-LAST:event_boton_DetalleDeVemtaActionPerformed

    private void txtnombreFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreFActionPerformed
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
//obtenemos el id del empleado y de la venta 
    private void Table_DetalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_DetalleMouseClicked

       
    }//GEN-LAST:event_Table_DetalleMouseClicked

    private void Boton_cancelar_facturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_cancelar_facturaActionPerformed
       int confirmar = JOptionPane.showConfirmDialog(null,"Esta seguro que quiere cancelar la facturacion?. La venta no se pordra finalizar","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(confirmar==JOptionPane.YES_OPTION){
                if(Tabla_productosC.getRowCount()!=0){
                Nodo aux = productos.getTope();
                while(aux!=null){
                    ModificarInventario(aux);
                    aux = aux.getSig();
                }
                LimpiarTablaC();
               }
                LimpiarCajasTextoFactura();
                Facturacion.dispose();
       }
    }//GEN-LAST:event_Boton_cancelar_facturaActionPerformed

    private void Boton_cancelar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Boton_cancelar_clienteActionPerformed
       int confirmar = JOptionPane.showConfirmDialog(null,"Desea Cancelar la operacion","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE); 
        if(confirmar==JOptionPane.YES_OPTION){
           txtnombreClien.setText(null);
           txtTelefonoClin.setText(null);
           txtEmailClin.setText(null);
           IngresarCliente.dispose();
       }
    }//GEN-LAST:event_Boton_cancelar_clienteActionPerformed

    private void boton_buscar_archivo_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_buscar_archivo_ActionPerformed
        JFileChooser dlg = new JFileChooser();
        int opcion = dlg.showSaveDialog(null);
        if(opcion == JFileChooser.APPROVE_OPTION){
            File expediente = dlg.getSelectedFile();
            txt_URL_pdf.setText(expediente.toString());
        }
    }//GEN-LAST:event_boton_buscar_archivo_ActionPerformed

    private void boton_generar_pdf_ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_generar_pdf_ActionPerformed
       String ruta = txt_URL_pdf.getText();
        try {
            FileOutputStream archivo = new FileOutputStream(ruta+".pdf");
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            doc.add(new Paragraph(contenidofinal));
            doc.close();
            JOptionPane.showMessageDialog(null,"Archivo creado exitosamente","",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"Error","",JOptionPane.ERROR_MESSAGE);
           
        }
    }//GEN-LAST:event_boton_generar_pdf_ActionPerformed

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
    private javax.swing.JToggleButton Boton_cancelar_cliente;
    private javax.swing.JToggleButton Boton_cancelar_factura;
    private javax.swing.JLabel CorreoE;
    private javax.swing.JDialog Dialog_DetalleVenta;
    private javax.swing.JDialog Facturacion;
    private javax.swing.JDialog IngresarCliente;
    private javax.swing.JTable TablaHistorial;
    private javax.swing.JTable Tabla_productosC;
    private javax.swing.JTable Tabla_productosD;
    private javax.swing.JTable Table_Detalle;
    private javax.swing.JLabel TelefonoE;
    private javax.swing.JButton boto_agregarcompra;
    private javax.swing.JButton botonRegresar;
    private javax.swing.JButton boton_DetalleDeVemta;
    private javax.swing.JButton boton_Facturar;
    private javax.swing.JButton boton_agregar_cliente;
    private javax.swing.JToggleButton boton_buscar_archivo_;
    private javax.swing.JButton boton_cargar_factura;
    private javax.swing.JButton boton_concelarcompra;
    private javax.swing.JButton boton_finalizarcompra;
    private javax.swing.JToggleButton boton_generar_pdf_;
    private javax.swing.JLabel cuiE;
    private javax.swing.JLabel emailClien_label;
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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel label_nit;
    private javax.swing.JLabel label_nombreE;
    private javax.swing.JLabel label_telefonoC;
    private javax.swing.JLabel nombreClabel;
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
    private javax.swing.JTextField txt_URL_pdf;
    private javax.swing.JTextField txtemail;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtnombreClien;
    private javax.swing.JTextField txtnombreF;
    private javax.swing.JTextField txtprecio;
    // End of variables declaration//GEN-END:variables
}
