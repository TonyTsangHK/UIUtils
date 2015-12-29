package utils.ui.tab;

public class TabEvent {
    public enum Type {
        ENTER("enter"), LEAVE("leave");
        
        public final String desc;
        
        private Type(String desc) {
            this.desc = desc;
        }
        
        @Override
        public String toString() {
            return desc;
        }
    }
    
    private TabComponent component;
    private Type eventType;
    
    public TabEvent(TabComponent component, Type eventType) {
        this.component = component;
        this.eventType = eventType;
    }
    
    public TabComponent getSource() {
        return component;
    }
    
    public Type getEventType() {
        return eventType;
    }
}