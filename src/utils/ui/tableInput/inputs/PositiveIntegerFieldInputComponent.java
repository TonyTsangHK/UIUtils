package utils.ui.tableInput.inputs;

import utils.document.PositiveIntegerDocument;

import javax.swing.*;

public class PositiveIntegerFieldInputComponent implements TableInputComponent<Integer> {
    private JTextField field;
    private Integer initValue;
    
    public PositiveIntegerFieldInputComponent() {
        field = new JTextField();
        field.setDocument(new PositiveIntegerDocument());
        initValue = null;
        field.setText("");
    }
    
    public PositiveIntegerFieldInputComponent(Integer v) {
        field = new JTextField();
        field.setDocument(new PositiveIntegerDocument());
        initValue = v;
        field.setText(initValue.toString());
    }

    public JComponent getComponent() {
        return field;
    }

    public Integer getValue() {
        if (field.getText() == null || field.getText().equals("")) {
            return null;
        } else { 
            return new Integer(Integer.parseInt(field.getText()));
        }
    }

    public void setValue(Integer value) {
        if (value == null) {
            field.setText("");
        } else {
            field.setText(value.toString());
        }
    }

    public TableInputComponent<Integer> cloneComponent() {
        PositiveIntegerFieldInputComponent pifdc = new PositiveIntegerFieldInputComponent(initValue);
        pifdc.setValue(getValue());
        return pifdc;
    }

    public Integer getInitValue() {
        return initValue;
    }

    public void resetValue() {
        setValue(initValue);
    }

    public void setInitValue(Integer value) {
        initValue = value;
    }
    
    public void setEnabled(boolean enable) {
        field.setEnabled(enable);
    }
    
    public boolean isEnabled() {
        return field.isEnabled();
    }
}
