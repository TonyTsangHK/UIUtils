package utils.ui.tableInput.inputs;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import utils.ui.GuiUtils;

@SuppressWarnings("rawtypes")
public class ComboBoxInputComponent implements TableInputComponent {
    private JComboBox comboBox;
    private boolean hasEmptyOption;
    private Object initValue;
    
    public ComboBoxInputComponent(Object initValue) {
        this(initValue, false);
    }
    
    public ComboBoxInputComponent(Object initValue, boolean hasEmptyOption) {
        this.initValue = initValue;
        comboBox = new JComboBox();
        if (hasEmptyOption) {
            comboBox.addItem("不指定");
        }
    }
    
    public JComponent getComponent() {
        return comboBox;
    }

    public Object getValue() {
        if (hasEmptyOption && comboBox.getSelectedIndex() <= 0) {
            return null;
        } else {
            return comboBox.getSelectedItem();
        }
    }

    public void setValue(Object value) {
        if (value != null) {
            GuiUtils.setComboBoxItem(comboBox, value);
        } else if (hasEmptyOption) {
            comboBox.setSelectedIndex(0);
        }
    }

    public TableInputComponent cloneComponent() {
        ComboBoxInputComponent cbic = new ComboBoxInputComponent(getInitValue());
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            cbic.addItem(comboBox.getItemAt(i));
        }
        cbic.setValue(getValue());
        return cbic;
    }

    public Object getInitValue() {
        return initValue;
    }

    public void resetValue() {
        setValue(initValue);
    }

    public void setInitValue(Object value) {
        initValue = value;
    }
    
    public void addItem(Object item) {
        comboBox.addItem(item);
    }
    
    public void removeItem(int i) {
        if (i >= ((hasEmptyOption)? 1 : 0) && i < comboBox.getItemCount()) {
            comboBox.removeItemAt(i);
        }
    }
    
    public void removeItem(Object item) {
        if (item != null && !(hasEmptyOption && item == comboBox.getItemAt(0))) {
            comboBox.removeItem(item);
        }
    }
    
    public void setEnabled(boolean enable) {
        comboBox.setEnabled(enable);
    }
    
    public boolean isEnabled() {
        return comboBox.isEnabled();
    }
}
