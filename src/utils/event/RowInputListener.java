package utils.event;

import java.util.EventListener;

public interface RowInputListener extends EventListener {
    public void rowInputManipulated(RowInputEvent evt);
}
