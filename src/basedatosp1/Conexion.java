/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basedatosp1;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class Conexion {
    private Connection conexion;
    public Connection Conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda","root","");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al canectarse");
            
        }
        return conexion;
    }
    
}
