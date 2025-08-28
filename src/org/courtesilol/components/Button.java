package org.courtesilol.components;

import java.awt.Color;
import java.awt.Dimension;
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
    private Color currentBorderColor;
    
    private boolean isHovered = false;

    public Button(String text, int width, int heigth, ButtonStyle style, int cornerRadius) {
        
        setText(text);
        setSize(width, heigth);
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(width, heigth));
        setMargin(null);
        currentBorderColor = getBackground();
        this.cornerRadius = cornerRadius;
        
        if (style != null) {
            setFocusable(style.focusable);
            if (style.textColor != null) {
                setForeground(style.textColor);
                textColor = style.textColor;
            }
                
            if (style.backGround != null) {
                setBackground(style.backGround);
                backGroundColor = style.backGround;
                currentBorderColor = backGroundColor;
            }
                
            if (style.hoverBackGroundColor != null) hoverBackGroundColor = style.hoverBackGroundColor;
            else hoverBackGroundColor = getBackground();
            
            if (style.hoverTextColor != null) hoverTextColor = style.hoverTextColor;
            else hoverTextColor = getForeground();
        }
        
        //Event to focus
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true; // Cambia el estado a hovered
                setBackground(hoverBackGroundColor);
                setForeground(hoverTextColor);
                currentBorderColor = hoverBackGroundColor;
                repaint(); // Redibuja el botón
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false; // Cambia el estado a no hovered
                setBackground(backGroundColor);
                setForeground(textColor);
                currentBorderColor = backGroundColor;
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
    
    public boolean isHovered() {
        return isHovered;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        //smooth render
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        //Best cuality
        //g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        //Best speed render
        //g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        // Dibuja el texto
        super.paintComponent(g);
    }
    

    @Override
    protected void paintBorder(Graphics g) {
        // Dibuja el borde redondeado
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(currentBorderColor);
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
    }

}
