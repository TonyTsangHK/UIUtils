package utils.ui.component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class TrappingGlassPane extends JComponent {
    private JProgressBar progressBar;
    private boolean showProgressBar;
    
    public TrappingGlassPane() {
        this(false);
    }
    
    public TrappingGlassPane(boolean showProgressBar) {
        this.showProgressBar = showProgressBar;
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        
        setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 1;
        cons.gridheight = 1;
        cons.fill = GridBagConstraints.NONE;
        cons.gridy = 1;
        add(progressBar, cons);
        addMouseListener(new MouseAdapter(){});
        addMouseMotionListener(new MouseMotionAdapter() {});
        addKeyListener(new KeyAdapter(){});
    }
    
    public void trap() {
        trap(0);
    }
    
    public void trap(long trapTime) {
        setVisible(true);
        progressBar.setVisible(showProgressBar);
        progressBar.requestFocus();

        if (trapTime > 0) {
            Timer timer = new Timer((int) trapTime, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    stopTrap();
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

    public void setProgressBarFont(Font font) {
        progressBar.setFont(font);
    }

    public void setProgressStatusText(String text) {
        progressBar.setString(text);
    }
    
    public void clearProgressStatusText() {
        progressBar.setString("");
    }
    
    public void setProgressBarVisible(boolean visible) {
        progressBar.setVisible(visible);
    }
    
    public boolean isProgressBarVisible() {
        return progressBar.isVisible();
    }
    
    public void setProgressDeterminable(boolean determinable) {
        progressBar.setIndeterminate(!determinable);
    }
    
    public boolean isProgressDeterminable() {
        return !progressBar.isIndeterminate();
    }
    
    public void setProgressMaximum(int max) {
        progressBar.setMaximum(max);
    }
    
    public void setProgressMinimum(int min) {
        progressBar.setMinimum(min);
    }
    
    public void setProgressValue(int v) {
        progressBar.setValue(v);
    }
    
    public int getProgressValue() {
        if (isProgressDeterminable()) {
            return progressBar.getValue();
        } else {
            return -1;
        }
    }
    
    public JProgressBar getProgressBar() {
        return progressBar;
    }
    
    public void stopTrap() {
        progressBar.setVisible(false);
        setVisible(false);
        setOpaque(false);
    }
}
