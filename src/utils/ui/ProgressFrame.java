package utils.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import utils.listener.ActionListenerWrapper;

public class ProgressFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JLabel progressLabel, statusLabel;
    private JProgressBar progressBar;
    private JTextArea statusArea;
    private JScrollPane scroll;
    private JButton confirmButton;
    private ActionListenerWrapper confirmActionWrapper;
    
    public ProgressFrame(String t) {
        super(t);
        
        initialize();
    }
    
    public void initialize() {
        progressLabel = new JLabel("狀態");
        statusLabel = new JLabel("---");
        confirmButton = new JButton("確認");
        progressBar = new JProgressBar();
        statusArea = new JTextArea();
        statusArea.setEditable(false);
        scroll = new JScrollPane(statusArea);
        GuiUtils.setSmallButton(confirmButton);
        progressBar.setStringPainted(true);
        
        confirmActionWrapper = new ActionListenerWrapper(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    ProgressFrame.this.dispose();
                }
            }
        );
        
        confirmButton.addActionListener(confirmActionWrapper);
        
        presentLayout();
    }
    
    private void presentLayout() {
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(progressLabel);
        topPanel.add(progressBar);
        topPanel.add(statusLabel);
        
        Container c = getContentPane();
        c.add(topPanel, BorderLayout.NORTH);
        c.add(scroll, BorderLayout.CENTER);
        c.add(confirmButton, BorderLayout.SOUTH);
        
        setSize(250, 200);
    }
    
    public void setDeterminable(boolean determinable) {
        progressBar.setIndeterminate(!determinable);
    }
    
    public void setMinimum(int min) {
        progressBar.setMinimum(min);
    }
    
    public void setMaximum(int max) {
        progressBar.setMaximum(max);
    }
    
    public void setProgressValue(int value) {
        progressBar.setValue(value);
    }
    
    public void setStatusLabel(String status) {
        statusLabel.setText(status);
    }
    
    public void setStatus(String status) {
        statusArea.setText(status);
    }
    
    public void appendStatus(String status) {
        statusArea.append(status + "\n");
        int h = statusArea.getHeight(); //, w = statusArea.getWidth();
        Rectangle viewRect = scroll.getViewport().getViewRect();
        Point viewPosition = new Point((int)viewRect.getMinX(), (int)(h - viewRect.getHeight()));
        scroll.getViewport().setViewPosition(viewPosition);
        requestFocus();
    }
    
    public void clearStatus() {
        statusArea.setText("");
    }
    
    public String getStatus() {
        return statusArea.getText();
    }
    
    public boolean isStatusEmpty() {
        return statusArea.getText() == null || statusArea.getText().equals("");
    }

    public void setConfirmEnabled(boolean enabled) {
        confirmButton.setEnabled(enabled);
    }

    public void setConfirmAction(ActionListener l) {
        confirmActionWrapper.setActionListener(l);
    }
}
