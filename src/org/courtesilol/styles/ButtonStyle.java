package org.courtesilol.styles;

import java.awt.Color;

/**
 *
 * @author Javier
 */
public final class ButtonStyle {

    public Color backGround;
    public Color textColor;
    public Color hoverBackGroundColor;
    public Color hoverTextColor;
    public boolean focusable;
    public boolean hasBorder;

    public ButtonStyle(Color backGround, Color textColor, Color hoverBackGroundColor, Color hoverTextColor, boolean focusable, boolean hasBorder) {
        this.backGround = backGround;
        this.textColor = textColor;
        this.hoverBackGroundColor = hoverBackGroundColor;
        this.hoverTextColor = hoverTextColor;
        this.focusable = focusable;
        this.hasBorder = hasBorder;
    }

    public ButtonStyle(Color backGround, Color textColor, Color hoverBackGroundColor, Color hoverTextColor) {
        this(backGround, textColor, hoverBackGroundColor, hoverTextColor, false, false);
    }

    public ButtonStyle(Color backGround, Color textColor, boolean focusable, boolean hasBorder) {
        this(backGround, textColor, null, null, focusable, hasBorder);
    }

    public ButtonStyle(Color backGround, Color textColor) {
        this(backGround, textColor, null, null, false, false);
    }
}
