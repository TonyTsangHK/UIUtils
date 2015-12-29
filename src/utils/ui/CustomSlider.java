package utils.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import utils.document.DoubleDocument;

public class CustomSlider extends JPanel implements PropertyChangeListener {
    private static final long serialVersionUID = 1L;

    public static final int VERTICAL = JSlider.VERTICAL, HORIZONTAL = JSlider.HORIZONTAL;
    
    private JSlider slider;
    private JTextField valueField;
    private double unit;
    
    private ArrayList<ChangeListener> changeListeners;
    
    boolean suppress = false;
    
    public CustomSlider(double min, double max, double value, double unit, int orientation,
            boolean paintLabels, double increment,
            boolean paintTicks, double majorSpacing, double minorSpacing, 
            boolean paintTracks) {
        this.unit = unit;
        changeListeners = new ArrayList<ChangeListener>();
        int actualMin = getIntegerValue(min), actualMax = getIntegerValue(max),
            actualValue = getIntegerValue(value),
            actualMajorSpacing = getIntegerValue(majorSpacing),
            actualMinorSpacing = getIntegerValue(minorSpacing),
            actualIncrement = getIntegerValue(increment);
        slider = GuiUtils.createSlider(actualMin, actualMax, actualValue, orientation,
                paintLabels, actualIncrement, unit, paintTicks, actualMajorSpacing, actualMinorSpacing,
                paintTracks);
        valueField = new JTextField(3);
        valueField.setHorizontalAlignment(JTextField.CENTER);
        valueField.setDocument(new DoubleDocument());
        valueField.setText(String.valueOf(getValue()));
        
        valueField.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        sendValue();
                    }
                }
        );
        
        valueField.addFocusListener(
                new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        // Ignored
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        sendValue();
                    }
                }
        );
        slider.addChangeListener(
                new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        valueField.setText(String.valueOf(getValue()));
                        fireChangeEvent();
                    }
                }
        );
        slider.addPropertyChangeListener(this);
        builderLayout();
    }
    
    public void suppress() {
        suppress = true;
    }
    
    public void release() {
        suppress = false;
    }
    
    public void fireChangeEvent() {
        if (!suppress) {
            for (ChangeListener listener : changeListeners) {
                listener.stateChanged(new ChangeEvent(this));
            }
        }
    }
    
    public boolean getValueIsAdjusting() {
        return slider.getValueIsAdjusting();
    }
    
    public void addChangeListener(ChangeListener changeListener) {
        changeListeners.add(changeListener);
    }
    
    public boolean removeChangeListener(ChangeListener changeListener) {
        return changeListeners.remove(changeListener);
    }
    
    public ChangeListener removeChangeListener(int index) {
        if (index >= 0 && index < changeListeners.size()) {
            return changeListeners.remove(index);
        } else {
            return null;
        }
    }
    
    private void sendValue() {
        double v = 0;
        if (!valueField.getText().equals("")) {
            v = Double.parseDouble(valueField.getText());
        }
        if (v > getMaximum()) {
            v = getMaximum();
            setValue(v);
            valueField.setText(String.valueOf(v));
        } else if (v < getMinimum()) {
            v = getMinimum();
            setValue(v);
            valueField.setText(String.valueOf(v));
        } else {
            setValue(v);
        }
    }
    
    public Hashtable<Integer, JLabel> createSliderLabels(double increment) {
        return GuiUtils.createSliderLabels(slider, getIntegerValue(increment), unit);
    }
    
    public double getMinimum() {
        return slider.getMinimum() * unit;
    }
    
    public double getMaximum() {
        return slider.getMaximum() * unit;
    }
    
    public double getValue() {
        return slider.getValue() * unit;
    }
    
    public void setMinimum(double min) {
        slider.setMinimum(getIntegerValue(min));
    }
    
    public void setMaximum(double max) {
        slider.setMaximum(getIntegerValue(max));
    }
    
    public void setValue(double value) {
        slider.setValue(getIntegerValue(value));
    }
    
    public int getIntegerValue(double value) {
        return (int)(value/unit);
    }
    
    private void builderLayout() {
        setLayout(new BorderLayout());
        add(slider, BorderLayout.CENTER);
        if (slider.getOrientation() == JSlider.HORIZONTAL) {
            add(valueField, BorderLayout.EAST);
        } else if (slider.getOrientation() == JSlider.VERTICAL) {
            add(valueField, BorderLayout.SOUTH);
        }
    }
    
    public void setEnabled(boolean enabled) {
        slider.setEnabled(enabled);
        valueField.setEnabled(enabled);
    }
    
    public boolean isEnabled() {
        return slider.isEnabled();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource() == slider) {
            String name = evt.getPropertyName();
            Object oldValue = evt.getOldValue(), newValue = evt.getNewValue();
            if (name.equals("orientation")) {
                if (!oldValue.equals(newValue)) {
                    Object location = null;
                    if (newValue.equals(new Integer(HORIZONTAL))) {
                        location = BorderLayout.EAST;
                    } else {
                        location = BorderLayout.SOUTH;
                    }
                    remove(valueField);
                    add(valueField, location);
                    revalidate();
                    repaint();
                }
            }
        }
    }
}
