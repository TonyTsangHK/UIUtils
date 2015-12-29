package utils.ui.dialog;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import utils.ui.component.TriggerOption;

public class InfoOptionDialog extends AbstractDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    private JEditorPane htmlPane;
    private JPanel optionPanel;
    
    private HashMap<String, TriggerOption> actionMap;
    
    public InfoOptionDialog(Frame frame, String title, boolean modality) {
        super(frame, title, modality);
        
        initialize();
    }
    
    public InfoOptionDialog(Dialog dialog, String title, boolean modality) {
        super(dialog, title, modality);
        
        initialize();
    }
    
    private void initialize() {
        actionMap = new HashMap<String, TriggerOption>();
        optionPanel = new JPanel(new GridLayout(0, 1));
        setLayout(new GridLayout(2, 1));
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        htmlPane.setContentType("text/html");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JScrollPane scroll = new JScrollPane(htmlPane);
        add(scroll);
        add(optionPanel);
    }
    
    public void setDisplayHtml(String html) {
        htmlPane.setText(html);
    }
    
    public void addActionTrigger(String optionDesc, String actionCommand, ActionListener l, boolean closeOnFinish) {
        JButton button = new JButton(optionDesc);
        button.setActionCommand(actionCommand);
        TriggerOption opt = new TriggerOption(button, actionCommand, l, closeOnFinish);
        actionMap.put(actionCommand, opt);
        button.addActionListener(this);
        optionPanel.add(button);
        optionPanel.revalidate();
        optionPanel.repaint();
    }
    
    public void removeActionTrigger(String actionCommand) {
        if (actionMap.containsKey(actionCommand)) {
            TriggerOption opt = actionMap.remove(actionCommand);
            optionPanel.remove(opt.getButton());
            optionPanel.revalidate();
            optionPanel.repaint();
        }
    }
    
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        if (actionMap.containsKey(cmd)) {
            TriggerOption opt = actionMap.get(cmd);
            if (opt != null) {
                if (opt.getListener() != null) {
                    opt.getListener().actionPerformed(evt);
                }
                if (opt.isCloseOnFinish()) {
                    dispose();
                }
            }
        }
    }
}