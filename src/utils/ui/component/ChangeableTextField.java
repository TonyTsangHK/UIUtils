package utils.ui.component;

import java.awt.EventQueue;

import javax.swing.JTextField;
import javax.swing.event.EventListenerList;
import javax.swing.text.Document;

import utils.event.ValueChangeEvent;
import utils.listener.ValueChangeListener;
import utils.string.StringUtil;

@SuppressWarnings("serial")
public class ChangeableTextField extends JTextField implements ChangeableComponent<String> {
    private EventListenerList valueChangeListeners;
    
    private String oldValue, newValue;
    
    public ChangeableTextField() {
        super();
        constructField();
    }
    
    public ChangeableTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
        constructField();
    }
    
    public ChangeableTextField(int columns) {
        super(columns);
        constructField();
    }
    
    public ChangeableTextField(String text) {
        super(text);
        constructField();
    }
    
    public ChangeableTextField(String text, int columns) {
        super(text, columns);
        constructField();
    }
    
    private void constructField() {
        valueChangeListeners = new EventListenerList();
        super.addFocusListener(new ChangeableComponent.FocusPerformer<String>(this));
        super.addActionListener(new ChangeableComponent.ActionPerformer<String>(this));
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void fireValueChanged() {
        if (valueChangeListeners != null && valueChangeListeners.getListenerCount() > 0) {
            ValueChangeEvent<String> e =
                new ValueChangeEvent<String>(
                    this, ValueChangeEvent.VALUE_CHANGED,
                    EventQueue.getMostRecentEventTime(),
                    oldValue, getText()
                );
            Object[] listeners = valueChangeListeners.getListenerList();
            for (int i = 0; i < listeners.length; i += 2) {
                if (listeners[i] == ValueChangeListener.class) {
                    ((ValueChangeListener<String>)listeners[i+1]).valueChanged(e);
                }
            }
        }
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
        return StringUtil.equals(getCurrentValue(), getNewValue());
    }
    
    @Override
    public void setCurrentValue(String v) {
        setText(v);
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
    public void addValueChangeListener(ValueChangeListener<String> l) {
        valueChangeListeners.add(ValueChangeListener.class, l);
    }
    
    @Override
    public void removeValueChangeListener(ValueChangeListener<String> l) {
        valueChangeListeners.remove(ValueChangeListener.class, l);
    }
}
