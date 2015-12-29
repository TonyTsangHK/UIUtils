package utils.ui.type;

import javax.swing.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tony Tsang
 * Date: 2014-08-21
 * Time: 10:44
 */
public enum IconType {
    INFORMATION("OptionPane.informationIcon", "Information"),
    QUESTION("OptionPane.questionIcon", "Question"),
    WARNING("OptionPane.warningIcon", "Warning"),
    ERROR("OptionPane.errorIcon", "Error");

    public final String key, desc;

    private IconType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public Icon getIcon() {
        return UIManager.getIcon(key);
    }

    @Override
    public String toString() {
        return desc;
    }
}
