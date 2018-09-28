package utils.listener.proxy;

import utils.event.RangeSelectionChangeEvent;
import utils.listener.RangeSelectionChangeListener;
import utils.listener.initiator.RangeSelectionChangeEventInitiator;

import java.util.ArrayList;
import java.util.List;

public class RangeSelectionChangeListenerProxy implements RangeSelectionChangeEventInitiator {
    private List<RangeSelectionChangeListener> listeners;

    public RangeSelectionChangeListenerProxy() {
        listeners = new ArrayList<RangeSelectionChangeListener>();
    }

    @Override
    public void addRangeSelectionChangeListener(RangeSelectionChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    public void addRangeSelectionChangeListeners(RangeSelectionChangeListener... listeners) {
        for (RangeSelectionChangeListener listener : listeners) {
            addRangeSelectionChangeListener(listener);
        }
    }

    @Override
    public void removeRangeSelectionChangeListener(RangeSelectionChangeListener listener) {
        listeners.remove(listener);
    }
    
    @Override
    public void removeRangeSelectionChangeListeners(RangeSelectionChangeListener... listeners) {
        for (RangeSelectionChangeListener listener : listeners) {
            removeRangeSelectionChangeListener(listener);
        }
    }

    @Override
    public void removeRangeSelectionChangeListener(int index) {
        if (index >= 0 && index < listeners.size()) {
            listeners.remove(index);
        }
    }

    @Override
    public void fireRangeSelectionChangeEvent(RangeSelectionChangeEvent evt) {
        if (listeners.size() > 0) {
            List<RangeSelectionChangeListener> snapshot = new ArrayList<RangeSelectionChangeListener>(listeners);

            for (RangeSelectionChangeListener listener : snapshot) {
                listener.rangeSelectionChanged(evt);
            }
        }
    }
}
