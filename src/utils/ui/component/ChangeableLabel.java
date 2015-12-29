package utils.ui.component;

import java.awt.EventQueue;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.event.EventListenerList;

import utils.event.ValueChangeEvent;
import utils.listener.ValueChangeListener;
import utils.string.StringUtil;

public class ChangeableLabel extends JLabel implements ChangeableComponent<String> {
    private static final long serialVersionUID = 1L;

    private EventListenerList valueChangeListeners;
    
    private String oldValue, newValue;
    
    public ChangeableLabel(){
        super();
        constructField();
    }
    
    public ChangeableLabel(Icon image) {
        super(image);
        constructField();
    }
    
    public ChangeableLabel(Icon image, int horizontalAlignment) {
        super(image, horizontalAlignment);
        constructField();
    }
    
    public ChangeableLabel(String text) {
        super(text);
        constructField();
    }
    
    public ChangeableLabel(String text, Icon icon, int horizontalAlignment) {
        super(text, icon, horizontalAlignment);
        constructField();
    }
    
    public ChangeableLabel(String text, int horizontalAlignment) {
        super(text, horizontalAlignment);
        constructField();
    }
    
    @Override
    public void setText(String text) {
        if (!StringUtil.equals(oldValue, text)) {
            oldValue = getText();
            super.setText(text);
            fireValueChanged();
        }
    }
    
    public void setTextSuppressed(String text) {
        super.setText(text);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public void fireValueChanged() {
        if (valueChangeListeners != null && valueChangeListeners.getListenerCount() > 0) {
            ValueChangeEvent<String> e = new ValueChangeEvent<String>(this,
                    ValueChangeEvent.VALUE_CHANGED,
                    EventQueue.getMostRecentEventTime(),
                    oldValue, getText());
            Object[] listeners = valueChangeListeners.getListenerList();
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == ValueChangeListener.class) {
                    ((ValueChangeListener<String>)listeners[i+1]).valueChanged(e);
                }
            }
        }
    }
    
    private void constructField() {
        valueChangeListeners = new EventListenerList();
        super.addFocusListener(new ChangeableComponent.FocusPerformer<String>(this));
    }
    
    @Override
    public String getCurrentValue() {
        return getText();
    }
    
    @Override
    public String getNewValue() {
        return newValue;
    }
    
    @Override
    public String getOldValue() {
        return oldValue;
    }
    
    @Override
    public boolean isSameAsOldValue() {
        return StringUtil.equals(getCurrentValue(), getOldValue());
    }
    
    @Override
    public void setNewValue(String v) {
        newValue = v;
    }

    @Override
    public void setOldValue(String v) {
        oldValue = v;        
    }
    
    @Override
    public void setCurrentValue(String v) {
        setText(v);
    }
    
    @Override
    public void addValueChangeListener(ValueChangeListener<String> l) {
        valueChangeListeners.add(ValueChangeListener.class, l);
    }
    
    @Override
    public void removeValueChangeListener(ValueChangeListener<String> l) {
        valueChangeListeners.remove(ValueChangeListener.class, l);
    }
}