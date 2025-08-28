

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
        panel = new JPanel();
        panel.setSize(this.getSize());
        panel.setBackground(Color.gray);
        this.add(panel);
        var btnStyle = new ButtonStyle(Color.darkGray, Color.lightGray, Color.lightGray, Color.darkGray, false);
        
        Button tem;
        for (int i=1;i<=5;i++) {
            tem = new Button("Button "+i, 50, 25, btnStyle, 20);
            tem.addActionListener(a -> {
                System.out.println("Button clicked");
            });
            panel.add(tem);
        }
        
        btnStyle = new ButtonStyle(Color.lightGray, Color.darkGray, Color.darkGray, Color.lightGray, false);
        for (int i=1;i<=5;i++) {
            tem = new Button("Button "+i, 50, 25, btnStyle, 10);
            tem.addActionListener(a -> {
                System.out.println("Button clicked");
            });
            panel.add(tem);
        }
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new MainWindow();
    }
    
}
