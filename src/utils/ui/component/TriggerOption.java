package utils.ui.component;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class TriggerOption {
    private JButton button;
    private String actionCommand;
    private ActionListener listener;
    private boolean closeOnFinish;
    
    public TriggerOption(JButton button, String actionCommand,
            ActionListener listener, boolean closeOnFinish) {
        this.button = button;
        this.actionCommand = actionCommand;
        this.listener = listener;
        this.closeOnFinish = closeOnFinish;
    }
    
    public JButton getButton() {
        return button;
    }
    
    public String getActionCommand() {
        return actionCommand;
    }
    
    public ActionListener getListener() {
        return listener;
    }
    
    public boolean isCloseOnFinish() {
        return closeOnFinish;
    }
}