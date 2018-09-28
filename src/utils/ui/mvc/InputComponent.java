package utils.ui.mvc;

public interface InputComponent {
    public void setEnabled(boolean enable);
    public boolean isEnabled();
    
    public Object getValue();
    public void setValue(Object value);
    
    public void reset();
}
