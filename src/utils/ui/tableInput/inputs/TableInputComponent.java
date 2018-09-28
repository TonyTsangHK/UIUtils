package utils.ui.tableInput.inputs;

import javax.swing.*;

public interface TableInputComponent<K> {
     public void setValue(K value);
     public K getValue();
     public K getInitValue();
     public void setInitValue(K value);
     public void resetValue();
     public JComponent getComponent();
     public TableInputComponent<K> cloneComponent();
     public void setEnabled(boolean enabled);
     public boolean isEnabled();
}