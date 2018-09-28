package utils.ui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.ListIterator;

public class SelectablePanel extends JPanel implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;
    
    public static final Color DragRectangleColor = new Color(0xFF, 0xFF, 0, 90), 
        DragBorderColor = new Color(0xFF, 0xFF, 0x00, 90),
        SelectedComponentColor = Color.CYAN;
    private Point2D startDraggingPoint;
    private Rectangle2D rect;
    private ArrayList<HighLightedComponent> comps;
    private ArrayList<Component> excludedComponents;
    
    private boolean isDragging;
    
    public SelectablePanel() {
        super();
        constructFields();
    }
    
    public SelectablePanel (boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        constructFields();
    }
    
    public SelectablePanel(LayoutManager layout) {
        super(layout);
        constructFields();
    }
    
    public SelectablePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        constructFields();
    }
    
    private void constructFields() {
        isDragging = false;
        startDraggingPoint = null;
        rect = null;
        comps = new ArrayList<HighLightedComponent>();
        excludedComponents = new ArrayList<Component>();
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public void addExcludedComponent(Component c) {
        excludedComponents.add(c);
    }
    
    public void clearExcludedComponents() {
        excludedComponents.clear();
    }
    
    public void clearHighlightedComponents() {
        for (HighLightedComponent hc : comps) {
            hc.resetColor();
        }
        comps.clear();
    }
    
    public Component[] getExcludedComponents() {
        Component[] components = new Component[excludedComponents.size()];
        for (int i = 0; i < excludedComponents.size(); i++) {
            components[i] = excludedComponents.get(i);
        }
        return components;
    }
    
    public Component[] getSelectedComponents() {
        Component[] components = new Component[comps.size()];
        for (int i = 0; i < comps.size(); i++) {
            components[i] = comps.get(i).getComponent();
        }
        return components;
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        if (startDraggingPoint != null) {
            Graphics2D g2d = (Graphics2D) g;
            Color oc = g2d.getColor();
            g2d.setColor(DragBorderColor);
            g2d.draw(rect);
            Rectangle2D r = new Rectangle2D.Double(rect.getX() + 1,
                    rect.getY() + 1, rect.getWidth() - 1,
                    rect.getHeight() - 1);
            g2d.setColor(DragRectangleColor);
            g2d.fill(r);
            g2d.setColor(oc);
        }
    }
    
    public void mouseClicked(MouseEvent evt) {
        // Ignored
    }

    public void mouseEntered(MouseEvent evt) {
        // Ignored
    }

    public void mouseExited(MouseEvent evt) {
        // Ignored
    }

    public void mousePressed(MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON1) {
            isDragging = true;
            startDraggingPoint = evt.getPoint();
            rect = new Rectangle2D.Double(startDraggingPoint.getX(), startDraggingPoint.getY(), 0, 0);
            for (HighLightedComponent c : comps) {
                c.resetColor();
            }
            comps.clear();
        }
    }

    public void mouseReleased(MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON1) {
            isDragging = false;
            startDraggingPoint = null;
            rect = null;
            repaint();
        }
    }

    public void mouseDragged(MouseEvent evt) {
        if (isDragging) {
            Point p = evt.getPoint();
            rect.setRect(Math.min(startDraggingPoint.getX(), p.getX()),
                    Math.min(startDraggingPoint.getY(), p.getY()),
                    Math.abs(startDraggingPoint.getX() - p.getX()),
                    Math.abs(startDraggingPoint.getY() - p.getY()));
            ListIterator<HighLightedComponent> iter = comps.listIterator();
            while (iter.hasNext()) {
                HighLightedComponent hc = iter.next();
                if (!rect.intersects(hc.getComponent().getBounds())) {
                    hc.resetColor();
                    iter.remove();
                }
            }
            for(int i = 0; i < getComponentCount(); i++) {
                Component c = getComponent(i);
                if (rect.intersects(c.getBounds()) && !excludedComponents.contains(c)) {
                    HighLightedComponent hc = new HighLightedComponent(c, SelectedComponentColor);
                    if (!comps.contains(hc)) {
                        hc.setColor();
                        comps.add(hc);
                    }
                }
            }
            repaint();
        }
    }

    public void mouseMoved(MouseEvent evt) {
        // Ignored
    }
    
    public static class HighLightedComponent {
        public static final Color selectedColor = Color.cyan;
        private Component component;
        private Color originalBackground, backgroundColor;
        
        public HighLightedComponent(Component c, Color backgroundColor) {
            component = c;
            originalBackground = c.getBackground();
            this.backgroundColor = backgroundColor;
        }
        
        public void resetColor() {
            component.setBackground(originalBackground);
        }
        
        public void setColor() {
            component.setBackground(backgroundColor);
        }
        
        public Component getComponent() {
            return component;
        }
        
        public Color getBackgroundColor() {
            return backgroundColor;
        }
        
        public Color getOriginalBackground() {
            return originalBackground;
        }
        
        public boolean equals(Object o) {
            if (o instanceof HighLightedComponent) {
                HighLightedComponent hc = ((HighLightedComponent)o);
                return hc.getComponent() == component && hc.getBackgroundColor().equals(backgroundColor);
            } else {
                return false;
            }
        }
    }
}