package utils.event;

import java.util.EventListener;

public interface RowInputManipulationListener extends EventListener {
    public void rowInputManipulationRequested(RowInputManipulationEvent evt);
}
