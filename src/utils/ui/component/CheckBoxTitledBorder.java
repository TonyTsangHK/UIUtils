package utils.ui.component;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CheckBoxTitledBorder implements Border, MouseListener {
    private JCheckBox checkBox;
    private Rectangle checkBoxRect;
    private Border border;
    private JComponent container;
    
    public CheckBoxTitledBorder(JComponent c, String title, boolean checked) {
        checkBox = new JCheckBox(title, checked);
        border = BorderFactory.createEtchedBorder();
        container = c;
        container.addMouseListener(this);
    }
    
    public Insets getBorderInsets(Component c) {
        Dimension size = checkBox.getPreferredSize();
        Insets insets = border.getBorderInsets(c);
        if (size.height > insets.top) {
            insets.top = size.height;
        }
        if (size.width > insets.left) {
            insets.left = size.width;
        }
        return insets;
    }

    public boolean isBorderOpaque() {
        return true;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
        Insets borderInsets = border.getBorderInsets(c); 
        Insets insets = getBorderInsets(c); 
        int hShift = (insets.top - borderInsets.top)/2;
        
        border.paintBorder(c, g, x, y + hShift, w, h - hShift);
        Dimension checkBoxSize = checkBox.getPreferredSize();
        checkBoxRect = new Rectangle(5, 0, checkBoxSize.width, checkBoxSize.height);
        SwingUtilities.paintComponent(g, checkBox, (Container)c, checkBoxRect);
    }
    
    public void setChecked(boolean checked) {
        checkBox.setSelected(checked);
    }
    
    public boolean isChecked() {
        return checkBox.isSelected();
    }
    
    public void addItemListener(ItemListener l) {
        checkBox.addItemListener(l);
    }
    
    public void mouseClicked(MouseEvent evt) {
        if (checkBoxRect != null && checkBoxRect.contains(evt.getPoint())) {
            checkBox.setSelected(!checkBox.isSelected());
            container.repaint(checkBoxRect);
        }
    }
    
    public void mouseEntered(MouseEvent evt) {
        // Ignored
    }
    
    public void mouseExited(MouseEvent evt) {
        // Ignored
    }
    
    public void mousePressed(MouseEvent evt) {
        // Ignored
    }
    
    public void mouseReleased(MouseEvent evt) {
        // Ignored
    }
}
