package utils.ui.tableInput.inputs;

import javax.swing.JComponent;
import javax.swing.JTextField;

import utils.document.DoubleDocument;
import utils.math.MathUtil;
import utils.ui.GuiUtils;

public class DoubleFieldInputComponent implements TableInputComponent<Double> {
    private JTextField field;
    private Double initValue;
    
    public DoubleFieldInputComponent(Double initValue) {
        this.initValue = initValue;
        field = GuiUtils.createTextField(true);
        field.setDocument(new DoubleDocument());
        if (initValue != null) {
            field.setText(initValue.toString());
        }
    }
    
    public JComponent getComponent() {
        return field;
    }
    
    public Double getValue() {
        return new Double(MathUtil.parseDouble(field.getText(), 0));
    }
    
    public void setValue(Double value) {
        if (value != null) {
            field.setText(value.toString());
        } else {
            field.setText("");
        }
    }

    public TableInputComponent<Double> cloneComponent() {
        DoubleFieldInputComponent dfic = new DoubleFieldInputComponent(initValue);
        dfic.setValue(getValue());
        return dfic;
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
