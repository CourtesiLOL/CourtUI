

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.courtesilol.components.Button;
import org.courtesilol.styles.ButtonStyle;

/**
 *
 * @author Javier
 */
public class MainWindow extends JFrame {
    private final JPanel panel;
    
    public MainWindow() {
        setMinimumSize(new Dimension(600,400));
        setBackground(Color.gray);
        setResizable(false);
        
        panel = new JPanel();
        panel.setSize(this.getSize());
        panel.setBackground(Color.gray);
        this.add(panel);
        
        //Button test
        testButtons(panel);
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void testButtons(JPanel panel) {
        //Estilos reutilizable para botones
        var btnStyle = new ButtonStyle(Color.darkGray, Color.lightGray, Color.lightGray, Color.darkGray);
        
        Button tem;
        int btnWidth = 100, btnHeight = 50;
        for (int i=1;i<=5;i++) {
            tem = new Button("Button "+i, btnWidth, btnHeight, btnStyle, 25);
            tem.addActionListener(a -> {
                System.out.println("Dark Button clicked");
            });
            panel.add(tem);
        }
        
        btnStyle = new ButtonStyle(Color.lightGray, Color.darkGray, Color.darkGray, Color.lightGray);
        for (int i=1;i<=5;i++) {
            tem = new Button("Button "+i, btnWidth, btnHeight, btnStyle, 15);
            tem.addActionListener(a -> {
                System.out.println("Ligth Button clicked");
            });
            panel.add(tem);
        }
        
        btnStyle = new ButtonStyle(Color.green, Color.white, Color.white, Color.green);
        for (int i=1;i<=5;i++) {
            tem = new Button("Button "+i, btnWidth, btnHeight, btnStyle, 5);
            tem.addActionListener(a -> {
                System.out.println("Green Button clicked");
            });
            panel.add(tem);
        }
        
        btnStyle = new ButtonStyle(Color.blue, Color.white, Color.white, Color.blue);
        for (int i=1;i<=5;i++) {
            tem = new Button("Button "+i, btnWidth, btnHeight, btnStyle);
            tem.addActionListener(a -> {
                System.out.println("Blue Button clicked");
            });
            panel.add(tem);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow();
        });
    }
    
}
