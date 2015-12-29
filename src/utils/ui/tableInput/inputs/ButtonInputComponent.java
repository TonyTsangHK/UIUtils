package utils.ui.tableInput.inputs;

import javax.swing.*;

public class ButtonInputComponent implements TableInputComponent<String> {
    private JButton button;
    String initValue;
    
    public ButtonInputComponent(String value) {
        initValue = value;
        button = new JButton();
        setValue(value);
    }
    
    public JComponent getComponent() {
        return button;
    }

    public String getValue() {
        return button.getText();
    }

    public void setValue(String value) {
        button.setText(value);
    }
    
    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }
    
    public String getInitValue() {
        return initValue;
    }
    
    public void resetValue() {
        button.setText(initValue);
    }

    public TableInputComponent<String> cloneComponent() {
        ButtonInputComponent bic = new ButtonInputComponent(initValue);
        bic.setValue(button.getText());
        return bic;
    }
    
    public boolean isEnabled() {
        return button.isEnabled();
    }
    
    public void setEnabled(boolean enable) {
        button.setEnabled(enable);
    }
}
