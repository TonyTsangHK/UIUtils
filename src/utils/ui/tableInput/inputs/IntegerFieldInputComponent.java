package utils.ui.tableInput.inputs;

import javax.swing.JComponent;
import javax.swing.JTextField;

import utils.document.IntegerDocument;
import utils.math.MathUtil;
import utils.ui.GuiUtils;

public class IntegerFieldInputComponent implements TableInputComponent<Integer> {
    private JTextField field;
    private Integer initValue;
    
    public IntegerFieldInputComponent(Integer v) {
        field = GuiUtils.createTextField(true);
        field.setDocument(new IntegerDocument());
        initValue = v;
    }
    
    public IntegerFieldInputComponent(Integer v, Integer sv) {
        field = GuiUtils.createTextField(true);
        field.setDocument(new IntegerDocument());
        initValue = v;
        setValue(sv);
    }

    public JComponent getComponent() {
        return field;
    }

    public Integer getValue() {
        return new Integer(MathUtil.parseInt(field.getText(), 10, 0));
    }

    public void setValue(Integer value) {
        if (value != null) {
            field.setText(value.toString());
        } else {
            field.setText("");
        }
    }

    public TableInputComponent<Integer> cloneComponent() {
        IntegerFieldInputComponent ifdc = new IntegerFieldInputComponent(initValue);
        ifdc.setValue(getValue());
        return ifdc;
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
