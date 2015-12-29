package utils.listener.initiator;

import utils.event.RangeSelectionChangeEvent;
import utils.listener.RangeSelectionChangeListener;

public interface RangeSelectionChangeEventInitiator {
    public void addRangeSelectionChangeListener(RangeSelectionChangeListener listener);
    public void addRangeSelectionChangeListeners(RangeSelectionChangeListener... listeners);
    public void removeRangeSelectionChangeListener(RangeSelectionChangeListener listener);
    public void removeRangeSelectionChangeListeners(RangeSelectionChangeListener... listeners);
    public void removeRangeSelectionChangeListener(int index);
    public void fireRangeSelectionChangeEvent(RangeSelectionChangeEvent evt);
}
