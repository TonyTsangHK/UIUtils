package utils.listener;

import java.util.EventListener;

import utils.event.RangeSelectionChangeEvent;

public interface RangeSelectionChangeListener extends EventListener {
    public void rangeSelectionChanged(RangeSelectionChangeEvent evt);
}
