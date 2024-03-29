/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatosp1;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sun.awt.image.IntegerInterleavedRaster;

/**
 *
 * @author Usuario
 */
public class Inventario extends javax.swing.JFrame {

    private int nivel;
    private DefaultTableModel modelo;
    public Inventario(int nivel) {
        initComponents();
        /*
        Imagen img1 = new Imagen("/basedatosp1/fonodo_menu_1.jpg", 737, 511);
        Imagen img2 = new Imagen("/basedatosp1/fonodo_menu_1.jpg", 737, 511);
       
        this.PanelAgregar.add(img1);
        this.PanelAgregar.repaint();
        
        this.PanelTabla.add(img2);
        this.PanelTabla.repaint();
        */
        this.nivel = nivel;
        
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/icono.jpg")).getImage());
        
        this.setResizable(false);
        setTitle("Rubix/Inventario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        Imagen img1 = new Imagen("/Imagenes/fondo4.jpg", 737, 570);
        Imagen img2 = new Imagen("/Imagenes/fondo5.jpg", 737, 570);
        this.PanelAgregar.add(img1);
        this.PanelAgregar.repaint();
        this.PanelTabla.add(img2);
        this.PanelTabla.repaint();
        
       //BotonAgregar.setEnabled(false);
        Tabla();
        Bloquear();
        Cerrar();
    }
    
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
        if(nivel==1){
            int confirmar = JOptionPane.showConfirmDialog(this,"¿Esta seguro de cerra la aplicacion?","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(confirmar==JOptionPane.YES_OPTION){
                   System.exit(0);
            }
        }
        else{
            int confirmar = JOptionPane.showConfirmDialog(this,"¿Esta seguro de cerra la aplicacion?. Cualquier modificacion que haya hecho se perdera si no guarda","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
            if(confirmar==JOptionPane.YES_OPTION){
                   System.exit(0);
            }
            
        }
    }
    public void ModificarLiquidez(int liquidez_modificada){
        Conexion con = new Conexion();
        Connection conexion = con.Conectar();
       
         try {
            PreparedStatement statement = conexion.prepareStatement("UPDATE dinero SET cantidad='"+liquidez_modificada+"'WHERE idDinero='"+1+"' ");
            statement.executeUpdate();
               
         } catch (SQLException ex) {
             System.out.println("error al Modificar liquidez en inventario");
         }
    }
    public int ObtenerLiquidez(){
        int liquidez = 0;
         
         String sql = "SELECT * FROM dinero"+" WHERE idDinero='"+1+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
           
            if(resultado.first()){
               liquidez = Integer.parseInt(resultado.getString("cantidad"));
              
                        }
                
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Datos incorrectos","",JOptionPane.ERROR_MESSAGE);
             
            //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return liquidez;
    }

    
    public void Bloquear(){
        try {
            if(nivel==1){
                BotonAgregar.setEnabled(false);
                Guardar.setEnabled(false);
                botonEliminar.setEnabled(false);
                boton_pdf.setEnabled(false);
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        
        
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
            
            Tabla1.setModel(modelo);
            
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        PanelAgregar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtnombre = new javax.swing.JTextField();
        txtCosto = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtExis = new javax.swing.JTextField();
        BotonAgregar = new javax.swing.JButton();
        BotonRegreasarMenu1 = new javax.swing.JButton();
        PanelTabla = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        Guardar = new javax.swing.JButton();
        txtbuscar = new javax.swing.JFormattedTextField();
        botonEliminar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNombreM = new javax.swing.JTextField();
        txtPrecioM = new javax.swing.JTextField();
        txtCostoM = new javax.swing.JTextField();
        txtExisM = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtIDm = new javax.swing.JTextField();
        boton_pdf = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelAgregar.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Nombre");

        jLabel3.setText("Costo");

        jLabel4.setText("Precio");

        jLabel5.setText("Existencias");

        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoKeyTyped(evt);
            }
        });

        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        txtExis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExisActionPerformed(evt);
            }
        });
        txtExis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExisKeyTyped(evt);
            }
        });

        BotonAgregar.setBackground(new java.awt.Color(255, 255, 255));
        BotonAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar1.1.png"))); // NOI18N
        BotonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonAgregarActionPerformed(evt);
            }
        });

        BotonRegreasarMenu1.setBackground(new java.awt.Color(255, 255, 255));
        BotonRegreasarMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir1.1.png"))); // NOI18N
        BotonRegreasarMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonRegreasarMenu1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelAgregarLayout = new javax.swing.GroupLayout(PanelAgregar);
        PanelAgregar.setLayout(PanelAgregarLayout);
        PanelAgregarLayout.setHorizontalGroup(
            PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAgregarLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(PanelAgregarLayout.createSequentialGroup()
                        .addGroup(PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel1))
                            .addGroup(PanelAgregarLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(12, 12, 12)))
                        .addGap(42, 42, 42)
                        .addGroup(PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtExis, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtnombre)
                            .addComponent(txtCosto)
                            .addComponent(txtPrecio))
                        .addGap(90, 90, 90)
                        .addComponent(BotonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(BotonRegreasarMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelAgregarLayout.setVerticalGroup(
            PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAgregarLayout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addGroup(PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelAgregarLayout.createSequentialGroup()
                        .addGroup(PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAgregarLayout.createSequentialGroup()
                        .addComponent(BotonAgregar)
                        .addGap(3, 3, 3)))
                .addGroup(PanelAgregarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtExis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(192, 192, 192)
                .addComponent(BotonRegreasarMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Agregar Inventario", PanelAgregar);

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        Guardar.setBackground(new java.awt.Color(255, 255, 255));
        Guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guadar1.2.png"))); // NOI18N
        Guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarActionPerformed(evt);
            }
        });

        txtbuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtbuscarKeyPressed(evt);
            }
        });

        botonEliminar.setBackground(new java.awt.Color(255, 255, 255));
        botonEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar3.1.png"))); // NOI18N
        botonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEliminarActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Buscar");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Nombre");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Precio");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Costo");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Existencias");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("ID");

        boton_pdf.setBackground(new java.awt.Color(255, 255, 255));
        boton_pdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pdf_1.1.jpg"))); // NOI18N
        boton_pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_pdfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelTablaLayout = new javax.swing.GroupLayout(PanelTabla);
        PanelTabla.setLayout(PanelTablaLayout);
        PanelTablaLayout.setHorizontalGroup(
            PanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTablaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelTablaLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(PanelTablaLayout.createSequentialGroup()
                        .addComponent(Guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80)
                        .addComponent(botonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(boton_pdf, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelTablaLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPrecioM, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtExisM, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelTablaLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombreM, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addGap(38, 38, 38)
                        .addComponent(txtCostoM, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel11)
                        .addGap(29, 29, 29)
                        .addComponent(txtIDm, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(156, 156, 156))))
        );
        PanelTablaLayout.setVerticalGroup(
            PanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTablaLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(PanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelTablaLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(PanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelTablaLayout.createSequentialGroup()
                        .addGroup(PanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNombreM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(txtCostoM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11)
                            .addComponent(txtIDm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(27, 27, 27)
                        .addGroup(PanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtExisM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(txtPrecioM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))))
                .addGap(61, 61, 61)
                .addGroup(PanelTablaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Guardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boton_pdf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(botonEliminar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inventario", PanelTabla);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtExisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtExisActionPerformed
//con esta funcion agregamos a la base de datos un nuevo producto 
    public void Agregar(){
        
        if(Integer.parseInt(txtCosto.getText())<=ObtenerLiquidez()){
            
            Conexion con = new Conexion();
            Connection conexion = con.Conectar();
                 try {

                     String query ="INSERT INTO inventario (nombre,costo,precio,existencia) values(?,?,?,?)";
                     PreparedStatement statement = conexion.prepareStatement(query);
                     statement.setString(1,txtnombre.getText());
                     statement.setFloat(2,Float.parseFloat(txtCosto.getText()));
                     statement.setFloat(3,Float.parseFloat(txtPrecio.getText()) );
                     statement.setInt(4,Integer.parseInt(txtExis.getText()));
                     
                     int liquidez_modificada = ObtenerLiquidez()-Integer.parseInt(txtPrecio.getText());
                     ModificarLiquidez(liquidez_modificada);
                     
                     statement.executeUpdate();
                     conexion.close(); 
                     JOptionPane.showMessageDialog(null,"Producto agregado correctamente","",JOptionPane.INFORMATION_MESSAGE); 

                     //limpiar las cajas de texto
                     txtnombre.setText(null);
                     txtCosto.setText(null);
                     txtPrecio.setText(null);
                     txtExis.setText(null);
                     Tabla();
                 } catch (Exception e) {
                     JOptionPane.showMessageDialog(null,"Error al conectarse a la base de datos","Error",JOptionPane.ERROR_MESSAGE);
                     e.printStackTrace();
                 } 
        }
        else{
            JOptionPane.showMessageDialog(null,"Ya no hay fondos para efectuar la compra","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        
       
}

//Agregar elementos. Ingresamos nuevos elementos a la base de datos 
    private void BotonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonAgregarActionPerformed
     
        if(txtnombre.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese un nombre","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if(txtCosto.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese el costo","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if(txtPrecio.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese el precio","Error",JOptionPane.ERROR_MESSAGE);
        }
        else if(txtExis.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Ingrese la existecias","Error",JOptionPane.ERROR_MESSAGE);
        }
        else{
            Agregar();//se llama a la funcion Agregar
        }
       
        

    }//GEN-LAST:event_BotonAgregarActionPerformed
// buscar un elemento 
    private void txtbuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtbuscarKeyPressed
        String[] titulos = {"ID","Nombre","Costo","Precio","Existencias"};
        String[] registro = new String[5];
        
        String sql = "SELECT *FROM inventario WHERE ID LIKE '%" + txtbuscar.getText() + "%'"
                + "OR nombre LIKE '%" + txtbuscar.getText() + "%'"
                + "OR costo LIKE '%" + txtbuscar.getText() + "%'"
                + "OR precio LIKE '%" + txtbuscar.getText() + "%'"
                + "OR existencia LIKE '%" + txtbuscar.getText() + "%'";
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
             Tabla1.setModel(modelo);
        } catch (SQLException ex) {
            System.out.println("ERROR");
        }
    }//GEN-LAST:event_txtbuscarKeyPressed
//Eliminar un elemento
    private void botonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEliminarActionPerformed
            
       
       String id = JOptionPane.showInputDialog(null,"Ingrese el ID del producto que desea eliminar");
       if(id!=null){
        System.out.println(id);
      
  
       Conexion con = new Conexion();
       Connection conect = con.Conectar();
      
        try {
          PreparedStatement pps = conect.prepareStatement("DELETE FROM Inventario WHERE ID=?");
          pps.setInt(1,Integer.parseInt(id));
          int num = pps.executeUpdate();
           if(num>0){
           Tabla();
          JOptionPane.showMessageDialog(null,"Producto eliminado","Mensaje",JOptionPane.INFORMATION_MESSAGE);  
           }
           else{
               JOptionPane.showMessageDialog(null,"El producto no existe","Error",JOptionPane.ERROR_MESSAGE);
           }
          
            
        } catch (SQLException ex) {
          JOptionPane.showMessageDialog(null,"Error! No puede eliminar el producto porque esta relacionado con una venta.\n"+"Solo puede eliminar "
                  + "productos que no se hayan vendido aun."
                  ,"Error",JOptionPane.ERROR_MESSAGE);
          //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
        
    }//GEN-LAST:event_botonEliminarActionPerformed
// cuando seleccione un fila de la tabla lo datos se pasaran a las cajas de texto del panel de la tabla inventario
    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
       int fila = Tabla1.getSelectedRow();
       txtIDm.setText(Tabla1.getValueAt(fila,0).toString());
       txtNombreM.setText(Tabla1.getValueAt(fila, 1).toString());
       txtCostoM.setText(Tabla1.getValueAt(fila, 2).toString());
       txtPrecioM.setText(Tabla1.getValueAt(fila, 3).toString());
       txtExisM.setText(Tabla1.getValueAt(fila, 4).toString());
 
    }//GEN-LAST:event_Tabla1MouseClicked
    //con esta funcion podemas modificar la informacion de la base de datos y de la tabla de inventario 
    public void Actualizar(String nombre, float costo, float precio, int existencia, int id){
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         try {
            PreparedStatement statement = conexion.prepareStatement("UPDATE inventario SET nombre='"+nombre+"',costo='"+costo+"',precio='"+precio
                    +"',existencia='"+existencia+"'WHERE ID='"+id+"' ");
            statement.executeUpdate();
           
            Tabla();
            JOptionPane.showMessageDialog(null,"Datos Actualizados","",JOptionPane.INFORMATION_MESSAGE);
            LimpiarTxtPanelTabla();
               
         } catch (SQLException ex) {
             System.out.println("error al actualizar");
         }
         
     }
     // con esta funcion dejamos el blanco las cajas de texto del panel de la tabla  de inventario
     public void LimpiarTxtPanelTabla(){
             txtNombreM.setText(null);
             txtCostoM.setText(null);
             txtPrecioM.setText(null);
             txtExisM.setText(null);
             txtIDm.setText(null);
         
     }
     // Este es el boton de guardar del panel de la tabla de inventario que manda los datos de las cajas de texto a la funcion Actualizar 
    private void GuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarActionPerformed
        int fila = Tabla1.getSelectedRow();
       
            
               if(fila>=0){
                      if(txtCostoM.getText()!=null){
                          JOptionPane.showMessageDialog(null,"Debe de llenar todos los campos","Advertencia!",JOptionPane.WARNING_MESSAGE);

                      }
                      else if(txtExisM.getText()!=null){
                          JOptionPane.showMessageDialog(null,"Debe de llenar todos los campos","Advertencia!",JOptionPane.WARNING_MESSAGE);

                      }
                      else if(txtNombreM.getText()!=null){
                          JOptionPane.showMessageDialog(null,"Debe de llenar todos los campos","Advertencia!",JOptionPane.WARNING_MESSAGE);

                      }
                      else if(txtPrecioM!=null){
                          JOptionPane.showMessageDialog(null,"Debe de llenar todos los campos","Advertencia!",JOptionPane.WARNING_MESSAGE);

                      }
                      else if(txtIDm.getText()!=null){
                          JOptionPane.showMessageDialog(null,"Debe de llenar todos los campos","Advertencia!",JOptionPane.WARNING_MESSAGE);

                      }
                      else{
                    Actualizar(txtNombreM.getText(),Float.parseFloat(txtCostoM.getText()),Float.parseFloat(txtPrecioM.getText()),Integer.parseInt(txtExisM.getText()),Integer.parseInt(txtIDm.getText()));
                      }
                }
                else{
                    JOptionPane.showMessageDialog(null,"No ha seleccionado una fila","Error",JOptionPane.ERROR_MESSAGE);
                }
        
              
       
    }//GEN-LAST:event_GuardarActionPerformed
//Accion de la caja de texto costo. solo permite numeros
    private void txtCostoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyTyped
        char validar = evt.getKeyChar();
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo ingrese numeros","Error",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_txtCostoKeyTyped
//Accion de la caja de texto Precio. solo permite numeros 
    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioKeyTyped
        char validar = evt.getKeyChar();
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo ingrese numeros","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtPrecioKeyTyped
//Accion de la caja de texto Exixstencia. Solo permite numeros
    private void txtExisKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExisKeyTyped
        char validar = evt.getKeyChar();
        if(Character.isLetter(validar)){
            getToolkit().beep();
            evt.consume();
            JOptionPane.showMessageDialog(null,"Solo ingrese numeros","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtExisKeyTyped

    private void BotonRegreasarMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonRegreasarMenu1ActionPerformed
            Menu menu = new Menu();
            menu.setVisible(true);
            this.dispose();
    }//GEN-LAST:event_BotonRegreasarMenu1ActionPerformed

    private void boton_pdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_pdfActionPerformed
        MessageFormat header  = new MessageFormat("Lista Inventario");
        MessageFormat footer = new MessageFormat("Page{0,number,integer}");
        try {
            Tabla1.print(JTable.PrintMode.NORMAL, header, footer);
            
        } catch (java.awt.print.PrinterException e) {
            System.err.format("error exportar",e.getMessage());
        }
    }//GEN-LAST:event_boton_pdfActionPerformed
  
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
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Inventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new Inventario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonAgregar;
    private javax.swing.JButton BotonRegreasarMenu1;
    private javax.swing.JButton Guardar;
    private javax.swing.JPanel PanelAgregar;
    private javax.swing.JPanel PanelTabla;
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton botonEliminar;
    private javax.swing.JToggleButton boton_pdf;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtCostoM;
    private javax.swing.JTextField txtExis;
    private javax.swing.JTextField txtExisM;
    private javax.swing.JTextField txtIDm;
    private javax.swing.JTextField txtNombreM;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPrecioM;
    private javax.swing.JFormattedTextField txtbuscar;
    private javax.swing.JTextField txtnombre;
    // End of variables declaration//GEN-END:variables
}
