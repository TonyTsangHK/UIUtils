package utils.listener.proxy;

import java.util.ArrayList;
import java.util.List;

import utils.event.TabbedPaneEvent;
import utils.listener.TabbedPaneListener;
import utils.listener.initiator.TabbedPaneListenerInitiator;

public class TabbedPaneListenerProxy implements TabbedPaneListenerInitiator {
    private List<TabbedPaneListener> listeners;
    
    public TabbedPaneListenerProxy() {
        listeners = new ArrayList<TabbedPaneListener>();
    }
    
    @Override
    public void addTabbedPaneListener(TabbedPaneListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void addTabbedPaneListeners(TabbedPaneListener... listeners) {
        for (TabbedPaneListener listener : listeners) {
            addTabbedPaneListener(listener);
        }
    }

    @Override
    public boolean fireTabbedPaneEvent(TabbedPaneEvent evt) {
        if (listeners.size() > 0) {
            List<TabbedPaneListener> snapshot = new ArrayList<TabbedPaneListener>();
            snapshot.addAll(listeners);
            
            boolean allowToken = true;
            for (TabbedPaneListener listener : snapshot) {
                allowToken &= listener.tabbedPaneChanged(evt);
            }
            return allowToken;
        } else {
            return true;
        }
    }
    
    @Override
    public void removeTabbedPaneListener(TabbedPaneListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeTabbedPaneListener(int index) {
        if (index >= 0 && index < listeners.size()) {
            listeners.remove(index);
        }
    }

    @Override
    public void removeTabbedPaneListeners(TabbedPaneListener... listeners) {
        for (TabbedPaneListener listener : listeners) {
            this.listeners.remove(listener);
        }
    }
    
    @Override
    public void clearTabbedPaneListener() {
        listeners.clear();
    }
}