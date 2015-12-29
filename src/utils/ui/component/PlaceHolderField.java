package utils.ui.component;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class PlaceHolderField extends JTextField implements FocusListener {
    private String placeHolder;
    
    public PlaceHolderField() {
        this(null, "", 0, "");
    }
    
    public PlaceHolderField(String text) {
        this(null, text, 0, "");
    }
    
    public PlaceHolderField(String text, String placeHolder) {
        this(null, text, 0, placeHolder);
    }
    
    public PlaceHolderField(int columns) {
        this(null, "", columns, "");
    }
    
    public PlaceHolderField(int columns, String placeHolder) {
        this(null, "", columns, placeHolder);
    }
    
    public PlaceHolderField(String text, int columns) {
        this(null, text, columns, "");
    }
    
    public PlaceHolderField(String text, int columns, String placeHolder) {
        this(null, text, columns, placeHolder);
    }
    
    public PlaceHolderField(Document doc, String text, int columns) {
        this(doc, text, columns, "");
    }
    
    public PlaceHolderField(Document doc, String text, int columns, String placeHolder) {
        super(doc, text, columns);
        this.placeHolder = placeHolder;
        addFocusListener(this);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() != this && this.getText().equals("")) {
            Color oc = g.getColor();
            
            g.setColor(Color.GRAY);
            
            FontMetrics fontMetrics = g.getFontMetrics(this.getFont());
            
            int as = fontMetrics.getAscent(), y = as + ((getHeight()-as)/2), x = fontMetrics.charWidth(' ') * 2;
            
            g.drawString(placeHolder, x, y);
            
            g.setColor(oc);
        }
    }
    
    public void setPlaceHolder(String placeHolder) {
        this.placeHolder = placeHolder;
        
        repaint();
    }
    
    public String getPlaceHolder() {
        return placeHolder;
    }

    @Override
    public void focusGained(FocusEvent e) {
        repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
        repaint();
    }
}
