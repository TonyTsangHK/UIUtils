package utils.ui.tableInput.inputs;

import javax.swing.*;

import utils.document.PositiveDoubleDocument;

public class PositiveDoubleFieldInputComponent implements TableInputComponent<Double> {
    private JTextField field;
    private Double initValue;
    
    public PositiveDoubleFieldInputComponent() {
        field = new JTextField();
        field.setDocument(new PositiveDoubleDocument());
        initValue = null;
        field.setText("");
    }
    
    public PositiveDoubleFieldInputComponent(Double v) {
        field = new JTextField();
        field.setDocument(new PositiveDoubleDocument());
        initValue = v;
        field.setText(initValue.toString());
    }

    public JComponent getComponent() {
        return field;
    }

    public Double getValue() {
        if (field.getText() == null || field.getText().equals("")) {
            return null;
        } else { 
            return new Double(Double.parseDouble(field.getText()));
        }
    }

    public void setValue(Double value) {
        if (value == null) {
            field.setText("");
        } else {
            field.setText(value.toString());
        }
    }

    public TableInputComponent<Double> cloneComponent() {
        PositiveDoubleFieldInputComponent pifdc = new PositiveDoubleFieldInputComponent(initValue);
        pifdc.setValue(getValue());
        return pifdc;
    }

    public Double getInitValue() {
        return initValue;
    }

    public void resetValue() {
        setValue(initValue);
    }

    public void setInitValue(Double value) {
        initValue = value;
    }
    
    public void setEnabled(boolean enable) {
        field.setEnabled(enable);
    }
    
    public boolean isEnabled() {
        return field.isEnabled();
    }
}
