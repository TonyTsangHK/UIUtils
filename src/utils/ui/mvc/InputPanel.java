package utils.ui.mvc;

public interface InputPanel extends ViewUI {
    public void setEnabled(boolean status);
    public boolean isEnabled();
    
    public Object retrieveValue(String key);
    
    public void modifyValue(String key, Object value);
}
