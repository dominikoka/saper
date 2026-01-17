package model;

import javax.swing.*;
import java.awt.*;

public class UIConstants {
  public static JButton createButton(String text) {
    JButton button =
        new JButton("<html>"
            + "<div style='color: #FFFFFF; font-size: 16px; font-family: DialogInput; text-align: center;'>"
            + "<b>" + text + "</b>"
            + "</div>"
            + "</html>");
    button.setBackground(new Color(52, 152, 219));
    button.setOpaque(true);
    button.setBorderPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(41, 128, 185));
      }

      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setBackground(new Color(52, 152, 219));
      }
    });
    return button;
  }
}
