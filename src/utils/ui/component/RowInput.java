package utils.ui.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import utils.event.RowInputEvent;
import utils.event.RowInputListener;

@SuppressWarnings("serial")
public class RowInput extends JPanel implements ActionListener {
    private JPanel buttonPanel;
    
    private JButton addButton, removeButton;
    
    private Component component;
    
    private RowComponentPanel parentPanel;
    
    private boolean addEnabled, removeEnabled;
    
    private List<RowInputListener> rowInputListeners;
    
    private SpringLayout springLayout;
    
    public RowInput(RowComponentPanel parentPanel, Component comp) {
        this(parentPanel, comp, true, true);
    }
    
    public RowInput(
            RowComponentPanel parentPanel, Component comp, boolean addEnabled, boolean removeEnabled
    ) {
        this.parentPanel = parentPanel;
        this.component = comp;
        this.addEnabled = addEnabled;
        this.removeEnabled = removeEnabled;
        
        rowInputListeners = new ArrayList<RowInputListener>();
        
        removeButton = new JButton("-");
        addButton = new JButton("+");
        
        addButton.setVisible(addEnabled);
        addButton.setEnabled(addEnabled);
        removeButton.setVisible(removeEnabled);
        removeButton.setEnabled(removeEnabled);
        
        removeButton.addActionListener(this);
        addButton.addActionListener(this);
        
        buildButtonPanel();
        
        presentLayout();
    }
    
    private void buildButtonPanel() {
        buttonPanel = new JPanel(new GridLayout(1, 2));
        
        if (addEnabled) {
            buttonPanel.add(addButton);
        }
        if (removeEnabled) {
            buttonPanel.add(removeButton);
        }
    }
    
    private void presentLayout() {
        springLayout = new SpringLayout();
        setLayout(springLayout);
        
        springLayout.putConstraint(
                SpringLayout.WEST, buttonPanel, RowComponentPanel.COMPONENT_HORIZONTAL_PADDING,
                SpringLayout.WEST, this
        );
        
        springLayout.putConstraint(
                SpringLayout.EAST, component, -RowComponentPanel.COMPONENT_HORIZONTAL_PADDING,
                SpringLayout.EAST, this
        );
        
        springLayout.putConstraint(
                SpringLayout.NORTH, this, 0, SpringLayout.NORTH, component
        );
        
        springLayout.putConstraint(
                SpringLayout.SOUTH, this, 0, SpringLayout.SOUTH, component
        );
        
        springLayout.putConstraint(
                SpringLayout.WEST, component, RowComponentPanel.COMPONENT_HORIZONTAL_PADDING,
                SpringLayout.EAST, buttonPanel
        );
        
        add(buttonPanel);
        add(component);
        
        refreshPreferredSize();
    }
    
    private void refreshPreferredSize() {
        Dimension buttonPanelPref = buttonPanel.getPreferredSize(),
        componentPref   = component.getPreferredSize();
        
        setPreferredSize(
                new Dimension(
                    buttonPanelPref.width + RowComponentPanel.COMPONENT_HORIZONTAL_PADDING + componentPref.width,
                    Math.max(buttonPanelPref.height, componentPref.height)
                )
        );
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == removeButton) {
            fireRowInputEvent(RowInputEvent.Type.REMOVE);
        } else if (source == addButton) {
            fireRowInputEvent(RowInputEvent.Type.ADD);
        }
    }
    
    public void addRowInputListener(RowInputListener listener) {
        if (!rowInputListeners.contains(listener)) {
            rowInputListeners.add(listener);
        }
    }
    
    public void addRowInputListeners(RowInputListener ... listeners) {
        for (RowInputListener listener : listeners) {
            addRowInputListener(listener);
        }
    }
    
    public void removeRowInputListener(RowInputListener listener) {
        rowInputListeners.remove(listener);
    }
    
    public void removeRowInputListener(int index) {
        if (index >= 0 && index < rowInputListeners.size()) {
            rowInputListeners.remove(index);
        }
    }
    
    private void fireRowInputEvent(RowInputEvent.Type eventType) {
        if (!rowInputListeners.isEmpty()) {
            List<RowInputListener> snapShot = new ArrayList<RowInputListener>(rowInputListeners.size());
            snapShot.addAll(rowInputListeners);
            
            RowInputEvent evt = new RowInputEvent(this, eventType);
            for (RowInputListener listener : snapShot) {
                listener.rowInputManipulated(evt);
            }
        }
    }
    
    public Component getComponent() {
        return component;
    }
    
    protected void setParentPanel(RowComponentPanel panel) {
        this.parentPanel = panel;
    }
    
    public RowComponentPanel getParentPanel() {
        return parentPanel;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        boolean oldEnabled = super.isEnabled();
        super.setEnabled(enabled);
        
        if (oldEnabled != enabled) {
            removeButton.setEnabled(enabled);
            component.setEnabled(enabled);
        }
    }
    
    public void setAddEnabled(boolean enabled) {
        if (addEnabled != enabled) {
            this.addEnabled = enabled;
            
            addButton.setVisible(addEnabled);
            addButton.setEnabled(addEnabled);
            
            if (addEnabled) {
                buttonPanel.add(addButton, 0);
            } else {
                buttonPanel.remove(addButton);
            }
            
            refreshPreferredSize();
        }
    }
    
    public boolean isAddEnabled() {
        return addEnabled;
    }
    
    public void setRemoveEnabled(boolean enabled) {
        if (removeEnabled != enabled) {
            this.removeEnabled = enabled;
            removeButton.setVisible(enabled);
            removeButton.setEnabled(enabled);
            
            if (removeEnabled) {
                buttonPanel.add(removeButton);
            } else {
                buttonPanel.remove(removeButton);
            }
            
            refreshPreferredSize();
        }
    }
    
    public boolean isRemoveEnabled() {
        return removeEnabled;
    }
}