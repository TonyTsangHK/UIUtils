package utils.ui.tableInput.inputs;

import javax.swing.*;

public class TextFieldInputComponent implements TableInputComponent<String> {
    private JTextField textField;
    private String initValue;
    
    public TextFieldInputComponent() {
        this("");
    }
    
    public TextFieldInputComponent(String initValue) {
        textField = new JTextField();
        this.initValue = initValue;
        setValue(initValue);
    }
    
    public JComponent getComponent() {
        return textField;
    }
    
    public void setValue(String value) {
        textField.setText(value);
    }
    
    public String getValue() {
        return textField.getText();
    }

    public TableInputComponent<String> cloneComponent() {
        TableInputComponent<String> tic = new TextFieldInputComponent(initValue);
        tic.setValue(getValue());
        return tic;
    }

    public String getInitValue() {
        return initValue;
    }

    public void resetValue() {
        setValue(initValue);
    }

    public void setInitValue(String value) {
        initValue = value;
    }
    
    public void setEnabled(boolean enable) {
        textField.setEnabled(enable);
    }
    
    public boolean isEnabled() {
        return textField.isEnabled();
    }
}
