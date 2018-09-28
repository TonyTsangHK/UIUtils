package utils.listener.initiator;

import utils.event.TabbedPaneEvent;
import utils.listener.TabbedPaneListener;

public interface TabbedPaneListenerInitiator {
    public void addTabbedPaneListener(TabbedPaneListener listener);
    public void addTabbedPaneListeners(TabbedPaneListener ... listeners);
    public void removeTabbedPaneListener(TabbedPaneListener listener);
    public void removeTabbedPaneListener(int index);
    public void removeTabbedPaneListeners(TabbedPaneListener ... listeners);
    public void clearTabbedPaneListener();
    public boolean fireTabbedPaneEvent(TabbedPaneEvent evt);
}
