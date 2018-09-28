package utils.event;

import utils.ui.component.TabbedPaneComponent;

public class TabbedPaneEvent {
    public enum Type {
        ENTER("enter"), LEAVE("leave");
        
        public final String desc;
        
        private Type(String desc) {
            this.desc = desc;
        }
        
        @Override
        public String toString() {
            return this.desc;
        }
    }
    
    private TabbedPaneComponent sourceComponent;
    private Type eventType;
    
    public TabbedPaneEvent(TabbedPaneComponent component, Type eventType) {
        this.sourceComponent = component;
        this.eventType = eventType;
    }
    
    public TabbedPaneComponent getSource() {
        return sourceComponent;
    }
    
    public Type getEventType() {
        return eventType;
    }
}
