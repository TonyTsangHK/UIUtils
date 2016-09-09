package utils.ui.component;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class DateEditDialog {
    private JDialog dialog;
    private JTextField hookedField;
    private DatePanel editor = new DatePanel();
    
    public DateEditDialog(Dialog dialog, JTextField tf) {
        this.dialog = new JDialog(dialog, true);
        initialize(tf);
    }
    
    public DateEditDialog(Frame parentFrame, JTextField tf) {
        dialog = new JDialog(parentFrame, true);
        initialize(tf);
    }
    
    public void initialize(JTextField tf) {
        hookedField = tf;
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(editor, BorderLayout.CENTER);
        final JButton confirmButton = new JButton("確定"),
            clearButton = new JButton("清除"),
            cancelButton = new JButton("取消");
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(confirmButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(cancelButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        ActionListener handler = evt -> {
            Object source = evt.getSource();
            if (source == confirmButton) {
                hookedField.setText(editor.getSelectedDateValue());
            } else if (source == clearButton) {
                hookedField.setText("");
            }
            hideEditorDialog();
        };
        confirmButton.addActionListener(handler);
        clearButton.addActionListener(handler);
        cancelButton.addActionListener(handler);
        dialog.setTitle("請選擇日期:");
    }
    
    public void showEditorDialog() {
        dialog.pack();
        Dimension preferredSize = dialog.getPreferredSize();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        dialog.setLocation((screenSize.width-preferredSize.width)/2, (screenSize.height-preferredSize.height)/2);
        dialog.setVisible(true);
    }
    
    public void hideEditorDialog() {
        dialog.setVisible(false);
    }
    
    public void setHookedField(JTextField tf) {
        hookedField = tf;
    }
}
