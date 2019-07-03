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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario
 */
public class Menu extends javax.swing.JFrame {
    /* 
    clave inventario =1
    clave competidores = 2
    clave ventas = 3
    clave finanazas =4 
    clave recursos humanos = 5
    *
    *
    clave algo = num
    
    clave_entrar_modulo
    */
    private int clave_entrar_modulo;
    public Menu() {
        
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/Imagenes/icono.jpg")).getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Rubix");
        BotonInventario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_competidores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_modulo_venta.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_finanzas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton_Rhumanos_modulo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
       
        
        boton_ingre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        dialog_contra.setLocationRelativeTo(null);
        dialog_contra.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/icono.jpg")).getImage());
        Imagen img1 = new Imagen("/basedatosp1/fonodo_menu_1.jpg", 400, 350);
        this.jPanel1.add(img1);
        this.jPanel1.repaint();
        Cerrar();        
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
        int confirmar = JOptionPane.showConfirmDialog(this,"¿Esta seguro de cerra la aplicacion?","Advertencia",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
        if(confirmar==JOptionPane.YES_OPTION){
               System.exit(0);
        }
        
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialog_contra = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtContrasena = new javax.swing.JPasswordField();
        boton_ingre = new javax.swing.JButton();
        BotonInventario = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        boton_competidores = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        boton_modulo_venta = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        boton_finanzas = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        boton_Rhumanos_modulo = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        fondo_menu = new javax.swing.JLabel();

        dialog_contra.setBounds(new java.awt.Rectangle(0, 0, 400, 350));

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Usuario ");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Contraseña");

        boton_ingre.setBackground(new java.awt.Color(255, 255, 255));
        boton_ingre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acceso3.1.png"))); // NOI18N
        boton_ingre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_ingreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(txtContrasena)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(boton_ingre, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(boton_ingre, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout dialog_contraLayout = new javax.swing.GroupLayout(dialog_contra.getContentPane());
        dialog_contra.getContentPane().setLayout(dialog_contraLayout);
        dialog_contraLayout.setHorizontalGroup(
            dialog_contraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dialog_contraLayout.setVerticalGroup(
            dialog_contraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(1, 2, 1920, 1080));
        setMinimumSize(new java.awt.Dimension(802, 477));
        getContentPane().setLayout(null);

        BotonInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/inventario2.png"))); // NOI18N
        BotonInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonInventarioActionPerformed(evt);
            }
        });
        getContentPane().add(BotonInventario);
        BotonInventario.setBounds(57, 132, 101, 85);

        jLabel1.setText("INVENTARIO");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(70, 110, 80, 14);

        boton_competidores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/competidores2.png"))); // NOI18N
        getContentPane().add(boton_competidores);
        boton_competidores.setBounds(205, 132, 100, 85);

        jLabel2.setText("COMPETIDORES");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(220, 110, 100, 14);

        boton_modulo_venta.setBackground(new java.awt.Color(255, 255, 255));
        boton_modulo_venta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ventas5.1.png"))); // NOI18N
        boton_modulo_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_modulo_ventaActionPerformed(evt);
            }
        });
        getContentPane().add(boton_modulo_venta);
        boton_modulo_venta.setBounds(351, 132, 96, 85);

        jLabel3.setText("VENTAS");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(370, 110, 70, 14);

        boton_finanzas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/finanzas4.1.png"))); // NOI18N
        boton_finanzas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_finanzasActionPerformed(evt);
            }
        });
        getContentPane().add(boton_finanzas);
        boton_finanzas.setBounds(498, 132, 96, 85);

        jLabel4.setText("FINANZAS");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(514, 110, 70, 14);

        boton_Rhumanos_modulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/recursoshumanos3.1.png"))); // NOI18N
        boton_Rhumanos_modulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boton_Rhumanos_moduloActionPerformed(evt);
            }
        });
        getContentPane().add(boton_Rhumanos_modulo);
        boton_Rhumanos_modulo.setBounds(650, 132, 95, 85);

        jLabel5.setText("RECURSOS HUMANOS");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(635, 110, 130, 14);

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/configuracion3.2.png"))); // NOI18N
        getContentPane().add(jButton6);
        jButton6.setBounds(260, 290, 94, 97);

        jLabel6.setText("CONFIGURACION");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(260, 260, 100, 20);

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ayuda1.1.png"))); // NOI18N
        getContentPane().add(jButton7);
        jButton7.setBounds(430, 290, 96, 95);

        jLabel7.setText("AYUDA");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(460, 260, 60, 14);

        fondo_menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/basedatosp1/fondo_menu_3.jpg"))); // NOI18N
        getContentPane().add(fondo_menu);
        fondo_menu.setBounds(0, 0, 810, 430);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonInventarioActionPerformed
        clave_entrar_modulo=1;
        dialog_contra.setVisible(true);
    }//GEN-LAST:event_BotonInventarioActionPerformed

    private void boton_ingreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_ingreActionPerformed
        String nombre = txtUsuario.getText();
        String contra = new String(txtContrasena.getPassword());
        String contra2 = null;
    

         String sql = "SELECT * FROM empleados"+" WHERE contrasena='"+contra+"' && nombreempleado='"+nombre+"'";
         Conexion con = new Conexion();
         Connection conexion = con.Conectar();
         Statement st;
         try{
            st = conexion.createStatement();   
            ResultSet resultado = st.executeQuery(sql);
            //contra2 = resultado.getString("contraseña");
            
            resultado.first();
             contra2 = resultado.getString("contrasena");
             int nivel = Integer.parseInt(resultado.getString("nivel_acceso"));
             int id_empleado = Integer.parseInt(resultado.getString("idempleado"));
            
            if(resultado.first()){
                // contra2 = resultado.getString("contraseña");
                txtUsuario.setText(null);
                txtContrasena.setText(null);
                if(clave_entrar_modulo==1){
                    Inventario inventario = new Inventario(nivel);
                    inventario.setVisible(true);
                    dialog_contra.dispose();
                    this.dispose();
                }
                if(clave_entrar_modulo==3){
                    Venta venta = new Venta(id_empleado,nivel);
                    venta.setVisible(true);
                    dialog_contra.dispose();
                    this.dispose();
                }
                if(clave_entrar_modulo==4){
                            if(nivel==1){
                                JOptionPane.showMessageDialog(null,"No tiene acceso a este modulo","Advertencia!",JOptionPane.WARNING_MESSAGE);
                                dialog_contra.dispose();
                            }
                            else{
                            FinanzaWIN2 wind = new FinanzaWIN2();
                            wind.setVisible(true);
                            dialog_contra.dispose();
                            this.dispose();
                            }
                    
                }
                if(clave_entrar_modulo==5){
                    recursosHumanos recursos = new recursosHumanos();
                    recursos.setVisible(true);
                    dialog_contra.dispose();
                    this.dispose();
                }
                
                    
                   
                        }
            
            
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Datos incorrectos","",JOptionPane.ERROR_MESSAGE);
             txtUsuario.setText(null);
             txtContrasena.setText(null);
            //Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        //pasar a la ventana inventario
    }//GEN-LAST:event_boton_ingreActionPerformed

    private void boton_modulo_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_modulo_ventaActionPerformed
        clave_entrar_modulo = 3;
        dialog_contra.setVisible(true);
    }//GEN-LAST:event_boton_modulo_ventaActionPerformed

    private void boton_Rhumanos_moduloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_Rhumanos_moduloActionPerformed
        clave_entrar_modulo = 5;
        dialog_contra.setVisible(true);
    }//GEN-LAST:event_boton_Rhumanos_moduloActionPerformed

    private void boton_finanzasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boton_finanzasActionPerformed
        clave_entrar_modulo = 4;
        dialog_contra.setVisible(true);
    }//GEN-LAST:event_boton_finanzasActionPerformed

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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonInventario;
    private javax.swing.JButton boton_Rhumanos_modulo;
    private javax.swing.JButton boton_competidores;
    private javax.swing.JButton boton_finanzas;
    private javax.swing.JButton boton_ingre;
    private javax.swing.JButton boton_modulo_venta;
    private javax.swing.JDialog dialog_contra;
    private javax.swing.JLabel fondo_menu;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
