package utils.ui.component;

import java.awt.Component;

import utils.event.TabbedPaneEvent;
import utils.listener.TabbedPaneListener;

public interface TabbedPaneComponent {
     public Component getComponent();
     public void addTabbedPaneListener(TabbedPaneListener listener);
     public void removeTabbedPaneListener(TabbedPaneListener listener);
     public void removeTabbedPaneListener(int i);
     public boolean fireTabbedPaneEvent(TabbedPaneEvent evt);
}
