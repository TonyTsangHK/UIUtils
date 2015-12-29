package utils.ui.component;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import utils.event.TabbedPaneEvent;
import utils.listener.TabbedPaneListener;

@SuppressWarnings("serial")
public class LabelTabbedPane extends JTabbedPane {
    private int previousSelectedIndex, currentSelectedIndex;
    private TabbedPaneComponent previousSelectedPane, currentSelectedPane;
    
    private List<TabbedPaneListener> listeners;
    
    public LabelTabbedPane() {
        super();
        previousSelectedPane = null;
        currentSelectedPane = null;
        previousSelectedIndex = -1;
        currentSelectedIndex = -1;
        
        listeners = new ArrayList<TabbedPaneListener>();
    }
    
    public int getCurrentSelectedIndex() {
        return currentSelectedIndex;
    }
    
    public int getPreviousSelectedIndex() {
        return previousSelectedIndex;
    }
    
    public TabbedPaneComponent getPreviousSelectedPane() {
        return previousSelectedPane;
    }
    
    public TabbedPaneComponent getCurrentSelectedPane() {
        return currentSelectedPane;
    }
    
    public String getCurrentSelectedTitle() {
        return getTitleAt(currentSelectedIndex);
    }
    
    public String getPreviousSelectedTitle() {
        return getTitleAt(previousSelectedIndex);
    }
    
    public void addTabbedPaneComponent(String title, TabbedPaneComponent paneComponent) {
        addTab(title, paneComponent.getComponent());
    }
    
    public void addTabbedPaneListener(TabbedPaneListener tabbedPaneListener) {
        listeners.add(tabbedPaneListener);
    }
    
    public TabbedPaneListener removeTabbedPaneListener(int index) {
        if (index >= 0 && index < listeners.size()) {
            return listeners.remove(index);
        } else {
            return null;
        }
    }
    
    public boolean removeTabbedPaneListener(TabbedPaneListener listener) {
        return listeners.remove(listener);
    }
    
    @Override
    public void removeTabAt(int i) {
        Component tabComp = getTabComponentAt(i);
        if (tabComp == currentSelectedPane || tabComp == previousSelectedPane) {
            previousSelectedPane = currentSelectedPane = null;
        }
        
        super.removeTabAt(i);
    }
    
    @Override
    public void setSelectedIndex(int i) {
        if (i >= 0 && i < getTabCount()) {
            boolean allowOut = (currentSelectedPane == null)? 
                    true : 
                    currentSelectedPane.fireTabbedPaneEvent(
                            new TabbedPaneEvent(currentSelectedPane, TabbedPaneEvent.Type.LEAVE)
                    );
            if (currentSelectedPane != null && allowOut) {
                TabbedPaneEvent leaveEvent = 
                    new TabbedPaneEvent(currentSelectedPane, TabbedPaneEvent.Type.LEAVE);
                allowOut = fireTabbedPaneEvent(leaveEvent);
            }
            if (allowOut) {
                TabbedPaneComponent cComponent = (TabbedPaneComponent)getComponentAt(i);
                boolean allowIn = (cComponent == null)? 
                        true :
                        cComponent.fireTabbedPaneEvent(
                                new TabbedPaneEvent(cComponent, TabbedPaneEvent.Type.ENTER)
                        );
                
                if (cComponent != null && allowIn) {
                    TabbedPaneEvent enterEvent = 
                        new TabbedPaneEvent(cComponent, TabbedPaneEvent.Type.ENTER);
                    allowIn = fireTabbedPaneEvent(enterEvent);
                }
                if (allowIn) {
                    super.setSelectedIndex(i);
                    previousSelectedIndex = currentSelectedIndex;
                    currentSelectedIndex = i;
                    previousSelectedPane = currentSelectedPane;
                    currentSelectedPane = cComponent;
                }
            }
        }
    }
    
    @Override
    public void setSelectedComponent(Component c) {
        super.setSelectedComponent(c);
    }
    
    public boolean fireTabbedPaneEvent(TabbedPaneEvent evt) {
        boolean success = true;
        for (TabbedPaneListener listener : listeners) {
            success &= listener.tabbedPaneChanged(evt);
        }
        return success;
    }
}
