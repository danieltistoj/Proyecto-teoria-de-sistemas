/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatosp1;

import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kelvin Cano
 */
public class FinanzaWIN2 extends javax.swing.JFrame {
    private DefaultTableModel modelo; 
    private String A1;
    private String A2;
    private String A3;
    private String A4;
    private String A5;
    private String A6;
    private String A7;
    private String A8;
    private DefaultTableModel modelo1 = new DefaultTableModel();
    private ArrayList<Producto> lista_productos = new ArrayList<>();
    private int nivel;
    /**
     * Creates new form pprueb
     */
    public FinanzaWIN2() {
        initComponents();
        Imagen img1 = new Imagen("/basedatosp1/fondo_modulo_2.jpg", 653, 658);
        Imagen img2 = new Imagen("/basedatosp1/fondo_modulo_2.jpg", 653, 658);
        this.jPanel3.add(img1);
        this.jPanel3.repaint();
        this.jPanel4.add(img2);
        this.jPanel4.repaint();
        DateFormat df = new SimpleDateFormat("dd/MM/yy");       
        Calendar calobj = Calendar.getInstance();       
        jLabel4.setText(df.format(calobj.getTime()));
        jLabel5.setText(df.format(calobj.getTime()));
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/icono.jpg")).getImage());
        setLocationRelativeTo(null);
        setTitle("Rubix/Finanzas");
        Tabla();
        Cerrar();
        Pdf.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_regresar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_top_pro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
        int confirmar = JOptionPane.showConfirmDialog(this,"¿Esta seguro de cerra la aplicacion?.","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(confirmar==JOptionPane.YES_OPTION){
               System.exit(0);
        }
        
    }
    public void Tabla(){
        
            Conexion con = new Conexion();
            Connection conexion = con.Conectar();
            
            String sql = "SELECT * FROM dinero";
            
            Statement st;
            
            modelo = new DefaultTableModel();
            modelo.addColumn("Nombre");
            modelo.addColumn("Cantidad");
            jTable2.setModel(modelo);
            
            String[] dato = new String[2];
            int total = 0;
            int contador = 0;
        try{
            st = conexion.createStatement();   
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()){
                if(contador == 0 && contador < 1){
                    A1 = result.getString(2);
                    A2 = result.getString(3);
                }else if (contador == 1 && contador < 2){
                    A3 = result.getString(2);
                    A4 = result.getString(3);
                }
                contador ++;
                dato[0] = result.getString(2);
                dato[1] = result.getString(3);
                total = total + Integer.valueOf(result.getString(3));
                modelo.addRow(dato);
            }
            String[] otrasColumnas = new String[2];
            otrasColumnas[0] = "Valor de Inventario";
            A5 = "Valor de inventario";
            A6  = String.valueOf(this.saberCuantoHayInventario());
            otrasColumnas[1] = String.valueOf(this.saberCuantoHayInventario());
            modelo.addRow(otrasColumnas);
            String[] otrasColumnas1 = new String[2];
            otrasColumnas1[0] = "Valor total";
            total = total + this.saberCuantoHayInventario();
            A7 = "Total";
            A8 = String.valueOf(total);
            otrasColumnas1[1] = String.valueOf(total);
            modelo.addRow(otrasColumnas1);
        } catch (SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public int saberCuantoHayInventario(){
        int cant = 0;
        Conexion con = new Conexion();
        Connection conexion = con.Conectar();

        String sql = "SELECT * FROM inventario";

        Statement st;
        try{
            st = conexion.createStatement();   
            ResultSet result = st.executeQuery(sql);

            while(result.next()){
                //aqui se multipilica existencias por valor
                cant = cant + (Integer.valueOf(result.getString(4)) * Integer.valueOf(result.getString(5)));
            }


        } catch (SQLException ex) {
            Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cant;
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
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        boton_top_pro = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_productosM = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        boton_regresar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        Pdf = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Modulo Finanza");
        setLocation(new java.awt.Point(350, 150));

        jLabel4.setFont(new java.awt.Font("Dosis", 3, 24)); // NOI18N
        jLabel4.setText("jLabel1");

        boton_top_pro.setText("Top Productos Mas Vendidos");
        boton_top_pro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_top_proActionPerformed(evt);
            }
        });

        Tabla_productosM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(Tabla_productosM);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(boton_top_pro)
                        .addGap(80, 80, 80)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(boton_top_pro)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Estadisticos", jPanel3);

        boton_regresar.setBackground(new java.awt.Color(255, 255, 255));
        boton_regresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir1.1.png"))); // NOI18N
        boton_regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_regresarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dosis", 3, 24)); // NOI18N
        jLabel5.setText("jLabel1");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTable2);

        Pdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/pdf1.png"))); // NOI18N
        Pdf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PdfMouseClicked(evt);
            }
        });
        Pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PdfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_regresar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 68, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Pdf)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(56, 56, 56)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Pdf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(boton_regresar)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Balance General", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void boton_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_regresarActionPerformed
        Menu menu = new Menu();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_boton_regresarActionPerformed

    private void PdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PdfActionPerformed
        JFileChooser jf = new JFileChooser();
        jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//para que solo pueda guardar al seleccionar carpetas
        int option = jf.showOpenDialog(FinanzaWIN2.this);
        if(option == 0){
            String nombre = JOptionPane.showInputDialog("Cual es el nombre del archivo a guardar ");
            String nombreArchivo = jf.getSelectedFile().getAbsolutePath() + "/" +  nombre + ".pdf";
            nombreArchivo  = nombreArchivo.replace("\\", "/");
            System.out.println(nombreArchivo);
            PruebaPDF pdf = new PruebaPDF( nombreArchivo, A1, A2, A3, A4, A5, A6, A7, A8);
        }
    }//GEN-LAST:event_PdfActionPerformed
public ArrayList lista_id_pro_detalle(Nodo tope_productos){
    Conexion con = new Conexion();
    Connection conexion = con.Conectar();
    ArrayList<String> lista_ventas_id = new ArrayList<>();       
            String sql = "SELECT * FROM detalleventa";
            Statement st;
                   
        try{
            st = conexion.createStatement();   
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()){
             lista_ventas_id.add(result.getString("producto_id"));
            }
            
            
        } catch (SQLException ex) {
            //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error*/*/*");
        }
    return lista_ventas_id;
}
     public void Ordenar(Lista lista_pro){
         Nodo aux = lista_pro.getTope();
         while(aux!=null){
             Nodo aux2 = aux.getSig();
             while(aux2!=null){
                 if(aux.getProducto().getCuanto_seV()<aux2.getProducto().getCuanto_seV()){
                     
                     int temp_CuantoSeV = aux2.getProducto().getCuanto_seV();
                     int  id = aux2.getProducto().getId_producto();
                     String nombre = aux2.getProducto().getNombre();
                     
                     
                     aux2.getProducto().setCuanto_seV(aux.getProducto().getCuanto_seV());
                     aux2.getProducto().setId_producto(aux.getProducto().getId_producto());
                     aux2.getProducto().setNombre(aux.getProducto().getNombre());
                     
                     aux.getProducto().setCuanto_seV(temp_CuantoSeV);
                     aux.getProducto().setId_producto(id);
                     aux.getProducto().setNombre(nombre);
                     
                 }
                 aux2=aux2.getSig();
             }
             aux = aux.getSig();
         }
               
         
       }

public void llenar_lista(Lista lista_productos){
  Conexion con = new Conexion();
  Connection conexion = con.Conectar();
            
            String sql = "SELECT * FROM inventario";
            
            Statement st;
                   
        try{
            st = conexion.createStatement();   
            ResultSet result = st.executeQuery(sql);
            
            while(result.next()){
              String nombre = result.getString("nombre");
              int id = Integer.parseInt(result.getString("ID"));
              Producto nuevo_producto = new Producto(id,0,nombre,0);
              Nodo nuevo_nodo = new Nodo();
              nuevo_nodo.setProducto(nuevo_producto);
              lista_productos.InsertarFondo(nuevo_nodo);
            }
            
            
        } catch (SQLException ex) {
            //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error*/*/*");
        }
         
}

public void setDatosProductos(){
        Object[] datos = new Object[modelo1.getColumnCount()];
        int i=1;
        modelo1.setRowCount(0);
        for(Producto producto: lista_productos){
            datos[0] = producto.getId_producto();
            datos[1] = producto.getNombre();
            datos[2] = producto.getCuanto_seV();
            i++;
            modelo1.addRow(datos);
        }
        Tabla_productosM.setModel(modelo1);
        
    }
    // con esta funcion se le ponen los encabezados a cado columna de la tabla de detalle de venta 
     public void setModelProductos(){
        String[] cabecera = {"ID","Nombre","Cantidad vendida"};
        modelo1.setColumnIdentifiers(cabecera);
        Tabla_productosM.setModel(modelo1);
                    
    }
     public void Ingresar_Productos_En_Array(Lista lista_producto){
         Nodo aux = lista_producto.getTope();
         while(aux!=null){
             this.lista_productos.add(aux.getProducto());
             aux = aux.getSig();
         }
     }
    private void boton_top_proActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_top_proActionPerformed
     Lista lista_productos = new Lista();
        llenar_lista(lista_productos);
        Nodo aux = lista_productos.getTope();
        ArrayList<String> listaIDs = new ArrayList<String>(lista_id_pro_detalle(aux));
        int size = listaIDs.size();
        int conta =0;
        while(aux!=null){
            conta=0;
            while(conta<size){
                if(aux.getProducto().getId_producto()==Integer.parseInt(listaIDs.get(conta))){
                    aux.getProducto().setCuanto_seV(aux.getProducto().getCuanto_seV()+1);

                }
                conta++;
            }
            aux = aux.getSig();
        }
        Ordenar(lista_productos);
        Ingresar_Productos_En_Array(lista_productos);
        setModelProductos();
        setDatosProductos();
        boton_top_pro.setEnabled(false);
    }//GEN-LAST:event_boton_top_proActionPerformed

    private void PdfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PdfMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_PdfMouseClicked

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
            java.util.logging.Logger.getLogger(FinanzaWIN2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FinanzaWIN2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FinanzaWIN2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FinanzaWIN2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FinanzaWIN2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Pdf;
    private javax.swing.JTable Tabla_productosM;
    private javax.swing.JButton boton_regresar;
    private javax.swing.JButton boton_top_pro;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
