package utils.ui.component;

import javax.swing.*;

@SuppressWarnings("serial")
public abstract class InputComponentPanel extends JPanel {
    public abstract void setEnabled(boolean enabled);
    public abstract boolean isEnabled();
    
    public abstract Object getValue();
    public abstract void setValue(Object v);
    
    public abstract void reset();
}
