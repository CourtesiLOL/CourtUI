package org.courtesilol.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import org.courtesilol.styles.ButtonStyle;

/**
 *
 * @author Javier
 */
public class Button extends JButton {

    private final int cornerRadius;
    
    private Color hoverBackGroundColor;
    private Color hoverTextColor;
    
    private Color textColor;
    private Color backGroundColor;
    
    private boolean isHovered = false;

    public Button(String text, int width, int heigth, ButtonStyle style, int cornerRadius) {
        setSize(width, heigth);
        setText(text);
        if (style != null) {
            setFocusable(style.focusable());
            if (style.textColor() != null) {
                setForeground(style.textColor());
                textColor = style.textColor();
            }
                
            if (style.backGround() != null) {
                setBackground(style.backGround());
                backGroundColor = style.backGround();
            }
                
            if (style.hoverBackGroundColor() != null) 
                hoverBackGroundColor = style.hoverBackGroundColor();
            else hoverBackGroundColor = getBackground();
            
            if (style.hoverTextColor() != null) 
                hoverTextColor = style.hoverTextColor();
            else hoverTextColor = getForeground();
        }

        this.cornerRadius = cornerRadius;
        setContentAreaFilled(false); // Evita el relleno predeterminado
        setBorder(BorderFactory.createEmptyBorder(heigth/2, width/2, heigth/2, width/2));

        //Event to focus
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true; // Cambia el estado a hovered
                setBackground(hoverBackGroundColor);
                setForeground(hoverTextColor);
                repaint(); // Redibuja el botón
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false; // Cambia el estado a no hovered
                setBackground(backGroundColor);
                setForeground(textColor);
                repaint(); // Redibuja el botón
            }
        });
    }

    public Button(String text, int width, int heigth, ButtonStyle style) {
        this(text, width, heigth, style, 0);
    }

    public Button(String text, int width, int heigth) {
        this(text, width, heigth, null, 0);
    }

    
    
    public void setHoverFocus(boolean isHover) {
        this.isHovered = isHover;
    }
    
    public void setHoverColor(Color hoverColor) {
        this.hoverBackGroundColor = hoverColor;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        // Dibuja el texto
        super.paintComponent(g);
    }
    

    @Override
    protected void paintBorder(Graphics g) {
        // Dibuja el borde redondeado
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getForeground());
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
    }

}
