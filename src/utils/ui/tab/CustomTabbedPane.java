package utils.ui.tab;

import java.awt.*;

import javax.swing.*;

public abstract class CustomTabbedPane extends JTabbedPane {
    private static final long serialVersionUID = 1L;
    
    private int previousSelectedIndex, currentSelectedIndex;
    private TabComponent previousTab, currentTab;
    
    public CustomTabbedPane() {
        super();
        previousTab = null;
        currentTab = null;
        previousSelectedIndex = -1;
        currentSelectedIndex = -1;
    }
    
    public int getCurrentSelectedIndex() {
        return currentSelectedIndex;
    }
    
    public int getPreviousSelectedIndex() {
        return previousSelectedIndex;
    }
    
    public TabComponent getPreviousTab() {
        return previousTab;
    }
    
    public TabComponent getCurrentTab() {
        return currentTab;
    }
    
    public String getCurrentTabTitle() {
        return getTitleAt(currentSelectedIndex);
    }
    
    public String getPreviousTabTitle() {
        return getTitleAt(previousSelectedIndex);
    }
    
    public void addTabComponent(String title, TabComponent tab) {
        super.addTab(title, tab.getComponent());
    }
    
    public void setSelectedIndex(int i) {
        if (i >= 0 && i < getTabCount()) {
            boolean allowOut = (currentTab==null)?true:currentTab.fireTabEvent(TabEvent.Type.LEAVE);
            if (allowOut) {
                TabComponent cComponent = (TabComponent)getComponentAt(i);
                boolean allowIn = (cComponent==null)?true:cComponent.fireTabEvent(TabEvent.Type.ENTER);
                if (allowIn) {
                    super.setSelectedIndex(i);
                    previousSelectedIndex = currentSelectedIndex;
                    currentSelectedIndex = i;
                    previousTab = currentTab;
                    currentTab = cComponent;
                }
            }
        }
    }
    
    public void setSelectedComponent(Component c) {
        super.setSelectedComponent(c);
    }
}
