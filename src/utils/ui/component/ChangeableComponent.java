package utils.ui.component;

import utils.listener.ValueChangeListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/*import java.awt.*;
import utils.event.*;*/

public interface ChangeableComponent<T> {
    public void fireValueChanged();
    public void setOldValue(T v);
    public void setNewValue(T v);
    public void setCurrentValue(T v);
    public T getOldValue();
    public T getCurrentValue();
    public T getNewValue();
    public void addValueChangeListener(ValueChangeListener<T> l);
    public void removeValueChangeListener(ValueChangeListener<T> l);
    public boolean isSameAsOldValue();
    
    public static class FocusPerformer<T> implements FocusListener {
        private ChangeableComponent<T> component;
        
        public FocusPerformer(ChangeableComponent<T> c) {
            component = c;
            component.setOldValue(component.getCurrentValue());
        }
        
        @Override
        public void focusGained(FocusEvent evt) {
            component.setOldValue(component.getCurrentValue());
        }
        
        @Override
        public void focusLost(FocusEvent evt) {
            if (!component.isSameAsOldValue()) {
                component.setNewValue(component.getCurrentValue());
                component.fireValueChanged();
            }
        }
        
        public ChangeableComponent<T> getChangeableComponent() {
            return component;
        }
    }
    
    public static class ActionPerformer<T> implements ActionListener {
        private ChangeableComponent<T> component;
        
        public ActionPerformer(ChangeableComponent<T> c) {
            component = c;
            component.setOldValue(component.getCurrentValue());
        }
        
        @Override
        public void actionPerformed(ActionEvent evt) {
            if (!component.isSameAsOldValue()) {
                component.setNewValue(component.getCurrentValue());
                component.fireValueChanged();
            }
        }
    }
}

