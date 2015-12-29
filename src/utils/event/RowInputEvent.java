package utils.event;

import java.util.EventObject;

@SuppressWarnings("serial")
public class RowInputEvent extends EventObject {
    public enum Type {
        ADD("add"), REMOVE("remove");
        
        public final String desc;
        
        private Type(String desc) {
            this.desc = desc;
        }
        
        @Override
        public String toString() {
            return desc;
        }
    }
    
    private Type eventType;
    
    public RowInputEvent(Object source, Type eventType) {
        super(source);
        this.eventType = eventType;
    }
    
    public Type getEventType() {
        return eventType;
    }
}
