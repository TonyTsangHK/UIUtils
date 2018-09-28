package utils.ui.tableInput.inputs;

import javax.swing.*;

public class LabelTableComponent implements TableInputComponent<String> {
    private JLabel label;
    private String initValue;
    
    public LabelTableComponent() {
        label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        initValue = "";
    }
    
    public LabelTableComponent(String initValue) {
        label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        this.initValue = initValue;
        label.setText(initValue);
    }

    public TableInputComponent<String> cloneComponent() {
        LabelTableComponent l = new LabelTableComponent(initValue);
        l.setValue(getValue());
        return l;
    }

    public JComponent getComponent() {
        return label;
    }

    public String getInitValue() {
        return initValue;
    }

    public String getValue() {
        return label.getText();
    }

    public void resetValue() {
        label.setText(initValue);
    }

    public void setInitValue(String value) {
        initValue = value;
    }

    public void setValue(String value) {
        label.setText(value);
    }
    
    public void setEnabled(boolean enable) {
        label.setEnabled(enable);
    }
    
    public boolean isEnabled() {
        return label.isEnabled();
    }
}
