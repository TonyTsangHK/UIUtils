package utils.ui.component;

import utils.ui.SelectionState;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CollapsiblePanel extends JPanel implements ChangeListener {
    private JLabel titleLabel;
    private LabelToggleButton toggleButton;
    
    private Component component;
    
    private JPanel titlePanel;
    
    private boolean collapsed;
    
    private List<ChangeListener> changeListeners;
    
    public CollapsiblePanel(String title, Component component) {
        this(title, component, false);
    }
    
    public CollapsiblePanel(String title, Component component, boolean initialCollapse) {
        changeListeners = new ArrayList<ChangeListener>();
        titleLabel = new JLabel(title);
        toggleButton = new LabelToggleButton(
                "+", "-", (initialCollapse)? SelectionState.SELECTED : SelectionState.UNSELECTED
        );
        toggleButton.addChangeListener(this);
        this.component = component;
        this.collapsed = initialCollapse;
        
        titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        setLayout(new BorderLayout());
        
        presentLayout();
    }
    
    private void presentLayout() {
        titlePanel.add(titleLabel);
        titlePanel.add(toggleButton);
        
        add(titlePanel, BorderLayout.NORTH);
        
        refreshComponentLayout();
    }
    
    private void refreshComponentLayout() {
        if (collapsed) {
            remove(component);
        } else {
            add(component, BorderLayout.CENTER);
        }
        
        revalidate();
        repaint();
    }
    
    public void setCollapsed(boolean collapsed) {
        if (this.collapsed != collapsed) {
            this.collapsed = collapsed;
            toggleButton.setState((this.collapsed)? SelectionState.SELECTED : SelectionState.UNSELECTED);
            refreshComponentLayout();
            
            fireChangeEvent(new ChangeEvent(this));
        }
    }
    
    public boolean isCollapsed() {
        return collapsed;
    }
    
    public String getTitle() {
        return titleLabel.getText();
    }
    
    public Component getComponent() {
        return component;
    }
    
    @Override
    public void stateChanged(ChangeEvent evt) {
        switch (toggleButton.getState()) {
            case SELECTED:
                setCollapsed(true);
                break;
            case UNSELECTED:
                setCollapsed(false);
                break;
        }
    }
    
    public void addChangeListener(ChangeListener l) {
        if (!changeListeners.contains(l)) {
            changeListeners.add(l);
        }
    }
    
    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);
    }
    
    public void clearChangeListeners() {
        changeListeners.clear();
    }
    
    private void fireChangeEvent(ChangeEvent evt) {
        if (changeListeners.size() > 0) {
            List<ChangeListener> snapshot = new ArrayList<ChangeListener>(changeListeners);
            
            for (ChangeListener listener : snapshot) {
                listener.stateChanged(evt);
            }
        }
    }
}
