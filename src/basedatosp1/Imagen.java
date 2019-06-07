package basedatosp1;

/**
 *
 * @author ferna
 */
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public class Imagen extends javax.swing.JPanel {
    private String Nombre;
    public Imagen(String Name) {
        this.setSize(645, 490); 
        this.Nombre = Name;
    }
    
    public void paint(Graphics grafico) {
        Dimension height = getSize();
        ImageIcon Img = new ImageIcon(getClass().getResource(Nombre)); 
        grafico.drawImage(Img.getImage(), 0, 0, height.width, height.height, null);
        setOpaque(false);
        super.paintComponent(grafico);
    }
}
