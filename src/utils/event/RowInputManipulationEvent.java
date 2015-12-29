package utils.event;

@SuppressWarnings("serial")
public class RowInputManipulationEvent extends RowInputEvent {
    private int rowIndex;
    
    public RowInputManipulationEvent(Object source, RowInputEvent.Type eventType, int rowIndex) {
        super(source, eventType);
        
        this.rowIndex = rowIndex;
    }
    
    public int getRowIndex() {
        return rowIndex;
    }
}
