package utils.listener;

import utils.event.RangeSelectionChangeEvent;

import java.util.EventListener;

public interface RangeSelectionChangeListener extends EventListener {
    public void rangeSelectionChanged(RangeSelectionChangeEvent evt);
}
