package utils.ui.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import utils.event.RowInputEvent;
import utils.event.RowInputListener;
import utils.event.RowInputManipulationEvent;
import utils.event.RowInputManipulationListener;

@SuppressWarnings("serial")
public class RowComponentPanel extends JPanel implements RowInputListener, ActionListener {
    public enum Orientation {
        BOTTOM_UP("bottomUp"), TOP_DOWN("topDown");
        
        public final String desc;
        
        private Orientation(String desc) {
            this.desc = desc;
        }
        
        @Override
        public String toString() {
            return desc;
        }
    }
    
    public static final int COMPONENT_HORIZONTAL_PADDING = 5, COMPONENT_VERTICAL_PADDING = 5;
    
    private List<RowInput> rowInputs;
    
    private SpringLayout springLayout;
    
    private Orientation orientation = Orientation.TOP_DOWN;
    
    private boolean addEnabled, removeEnabled;
    
    private JButton addButton;
    
    private List<RowInputManipulationListener> rowInputManipulationListeners;
    
    public RowComponentPanel() {
        this(true, true);
    }
    
    public RowComponentPanel(boolean addEnabled, boolean removeEnabled) {
        this.addEnabled = addEnabled;
        this.removeEnabled = removeEnabled;
        
        rowInputManipulationListeners = new ArrayList<RowInputManipulationListener>();
        
        rowInputs = new ArrayList<RowInput>();
        
        addButton = new JButton("+");
        addButton.addActionListener(this);
        
        presentLayout();
    }
    
    private void presentLayout() {
        springLayout = new SpringLayout();
        setLayout(springLayout);
    }
    
    private void showAddButton() {
        if (rowInputs.isEmpty()) {
            add(addButton);
            setDefaultConstraints(addButton);
            setComponentConstraint(null, addButton);
        }
    }
    
    private void removeAddButton() {
        remove(addButton);
        springLayout.removeLayoutComponent(addButton);
    }
    
    public void setOrientation(Orientation orientation) {
        if (orientation != null && this.orientation != orientation) {
            this.orientation = orientation;
            rebuildLayoutConstraints();
        }
    }
    
    public Orientation getOrientation() {
        return orientation;
    }
    
    private void rebuildLayoutConstraints() {
        for (RowInput rowInput : rowInputs) {
            springLayout.removeLayoutComponent(rowInput);
        }
        
        RowInput prev = null;
        
        for (int i = 0; i < rowInputs.size(); i++) {
            RowInput rowInput = rowInputs.get(i);
            
            setDefaultConstraints(rowInput);
            setComponentConstraint(prev, rowInput);
            
            prev = rowInput;
        }
        
        revalidate();
        repaint();
    }
    
    private void refreshPreferredSize() {
        if (rowInputs.size() > 0) {
            int height = 0, width = 0;
            for (RowInput rowInput : rowInputs) {
                Dimension rowPref = rowInput.getPreferredSize();
                height += rowInput.getPreferredSize().height + COMPONENT_VERTICAL_PADDING;
                width = Math.max(width, rowPref.width);
            }
            height += COMPONENT_VERTICAL_PADDING;
            
            Dimension pref = getPreferredSize();
            pref.setSize(width, height);
            setPreferredSize(pref);
        }
    }
    
    private void setDefaultConstraints(Component comp) {
        springLayout.putConstraint(
                SpringLayout.WEST, comp, COMPONENT_HORIZONTAL_PADDING, SpringLayout.WEST, this
        );
        
        springLayout.putConstraint(
                SpringLayout.EAST, comp, -COMPONENT_HORIZONTAL_PADDING, SpringLayout.EAST, this
        );
    }
    
    private void setComponentConstraint(Component prevComponent, Component targetComponent) {
        if (prevComponent != null) {
            if (orientation == Orientation.BOTTOM_UP) {
                springLayout.putConstraint(
                        SpringLayout.SOUTH, targetComponent, -COMPONENT_VERTICAL_PADDING,
                        SpringLayout.NORTH, prevComponent
                );
            } else {
                springLayout.putConstraint(
                        SpringLayout.NORTH, targetComponent, COMPONENT_VERTICAL_PADDING,
                        SpringLayout.SOUTH, prevComponent
                );
            }
        } else {
            if (orientation == Orientation.BOTTOM_UP) {
                springLayout.putConstraint(
                        SpringLayout.SOUTH, targetComponent, -COMPONENT_VERTICAL_PADDING,
                        SpringLayout.SOUTH, this
                );
            } else {
                springLayout.putConstraint(
                        SpringLayout.NORTH, targetComponent, COMPONENT_VERTICAL_PADDING,
                        SpringLayout.NORTH, this
                );
            }
        }
    }
    
    public void addRowComponent(Component comp) {
        if (rowInputs.isEmpty()) {
            removeAddButton();
        }
        RowInput rowInput = createRowInput(comp);
        rowInputs.add(rowInput);
        
        add(rowInput);
        
        setDefaultConstraints(rowInput);
        setComponentConstraint(
                (rowInputs.size() > 1)? rowInputs.get(rowInputs.size() - 2) : null, rowInput
        );
        
        refreshLayout();
    }
    
    public Component getRowComponent(int i) {
        if (i >= 0 && i < rowInputs.size()) {
            RowInput rowInput = rowInputs.get(i);
            
            return rowInput.getComponent();
        } else {
            return null;
        }
    }
    
    public Component removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < rowInputs.size()) {
            RowInput rowInput = rowInputs.get(rowIndex),
                prevRowInput = (rowIndex > 0)? rowInputs.get(rowIndex - 1) : null,
                nextRowInput = (rowIndex + 1 < rowInputs.size())? rowInputs.get(rowIndex + 1) : null;
            
            RowInput rowInputToBeRemoved = rowInputs.get(rowIndex);
            rowInputs.remove(rowIndex);
            
            rowInputToBeRemoved.setParentPanel(null);
            rowInputToBeRemoved.removeRowInputListener(this);
            
            remove(rowInput);
            
            springLayout.removeLayoutComponent(rowInput);
            
            if (nextRowInput != null) {
                setComponentConstraint(prevRowInput, nextRowInput);
            }
            
            if (rowInputs.isEmpty() && addEnabled) {
                showAddButton();
            }
            
            refreshLayout();
            
            return rowInput.getComponent();
        } else {
            return null;
        }
    }
    
    public boolean removeRowComponent(Component component) {
        int index = indexOf(component);
        if (index != -1) {
            return removeRow(index) != null;
        } else {
            return false;
        }
    }
    
    public void insertRowComponent(int index, Component component) {
        if (index == rowInputs.size()) {
            addRowComponent(component);
        } else if (index >= 0 && index < rowInputs.size()) {
            if (rowInputs.isEmpty()) {
                removeAddButton();
            }
            RowInput rowInput = createRowInput(component);
            
            RowInput originalRowInput = rowInputs.get(index),
                prevRowInput = (index > 0)? rowInputs.get(index - 1) : null;
            
            rowInputs.add(index, rowInput);
            add(rowInput);
            
            springLayout.removeLayoutComponent(originalRowInput);
            
            setDefaultConstraints(rowInput);
            setDefaultConstraints(originalRowInput);
            setComponentConstraint(rowInput, originalRowInput);
            setComponentConstraint(prevRowInput, rowInput);
            
            refreshLayout();
        }
    }
    
    private void refreshLayout() {
        refreshPreferredSize();
        
        revalidate();
        repaint();
    }
    
    public int indexOf(Component comp) {
        for (int i = 0; i < rowInputs.size(); i++) {
            if (rowInputs.get(i).getComponent() == comp) {
                return i;
            }
        }
        return -1;
    }
    
    public int getRowInputCount() {
        return rowInputs.size();
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        boolean oldEnabled = super.isEnabled();
        super.setEnabled(enabled);
        if (oldEnabled != enabled) {
            for (RowInput rowInput : rowInputs) {
                rowInput.setEnabled(enabled);
            }
        }
    }
    
    private RowInput createRowInput(Component component) {
        RowInput rowInput = new RowInput(this, component, addEnabled, removeEnabled);
        rowInput.addRowInputListener(this);
        return rowInput;
    }
    
    @Override
    public void rowInputManipulated(RowInputEvent evt) {
        Object source = evt.getSource();
        int rowIndex = -1;
        for (int i = 0; i < rowInputs.size(); i++) {
            if (rowInputs.get(i) == source) {
                rowIndex = i;
                break;
            }
        }
        if (evt.getEventType() == RowInputEvent.Type.ADD) {
            if (rowIndex != -1) {
                fireRowInputManipulationListener(RowInputEvent.Type.ADD, rowIndex);
            }
        } else if (evt.getEventType() == RowInputEvent.Type.REMOVE) {
            if (rowIndex != -1) {
                removeRow(rowIndex);
                
                fireRowInputManipulationListener(RowInputEvent.Type.REMOVE, rowIndex);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == addButton) {
            fireRowInputManipulationListener(RowInputEvent.Type.ADD, -1);
        }
    }
    
    public boolean isAddEnabled() {
        return addEnabled;
    }
    
    public boolean isRemoveEnabled() {
        return removeEnabled;
    }
    
    public void setAddEnabled(boolean addEnabled) {
        if (this.addEnabled != addEnabled) {
            this.addEnabled = addEnabled;
            
            for (RowInput rowInput : rowInputs) {
                rowInput.setAddEnabled(this.addEnabled);
            }
        }
    }
    
    public void setRemoveEnabled(boolean removeEnabled) {
        if (this.removeEnabled != removeEnabled) {
            this.removeEnabled = removeEnabled;
            
            for (RowInput rowInput : rowInputs) {
                rowInput.setRemoveEnabled(this.removeEnabled);
            }
        }
    }
    
    public void addRowInputManipulationListener(RowInputManipulationListener listener) {
        if (!rowInputManipulationListeners.contains(listener)) {
            rowInputManipulationListeners.add(listener);
        }
    }
    
    public void addRowInputManipulationListeners(RowInputManipulationListener ... listeners) {
        for (RowInputManipulationListener listener : listeners) {
            addRowInputManipulationListener(listener);
        }
    }
    
    public void removeRowInputManipulationListener(RowInputManipulationListener listener) {
        rowInputManipulationListeners.remove(listener);
    }
    
    public void removeRowInputManipulationListener(int index) {
        if (index >= 0 && index < rowInputManipulationListeners.size()) {
            rowInputManipulationListeners.remove(index);
        }
    }
    
    private void fireRowInputManipulationListener(RowInputEvent.Type eventType, int rowIndex) {
        if (!rowInputManipulationListeners.isEmpty()) {
            List<RowInputManipulationListener> snapShot = new ArrayList<RowInputManipulationListener>();
            snapShot.addAll(rowInputManipulationListeners);
            
            RowInputManipulationEvent evt = new RowInputManipulationEvent(this, eventType, rowIndex);
            for (RowInputManipulationListener listener : snapShot) {
                listener.rowInputManipulationRequested(evt);
            }
        }
    }
}