package utils.ui;

import java.awt.Color;
import java.awt.Component;

public class BlinkingAnimationThread extends Thread {
    private Component component;
    private Color color, originColor;
    private boolean colorReset = false;
    private int blinkTimes;
    private long intervalTime;
    
    public BlinkingAnimationThread(Component comp, Color cl, int time, long interval) {
        component = comp;
        color = cl;
        blinkTimes = time;
        intervalTime = interval;
        originColor = component.getBackground();
    }
    
    public Component getComponent() {
        return component;
    }
    
    public void addBlinkTimes(int times) {
        blinkTimes += times;
    }
    
    public void run() {
        for (int i = 0; i < blinkTimes * 2; i++) {
            try {
                if (colorReset) {
                    component.setBackground(originColor);
                    colorReset = false;
                    Thread.sleep(intervalTime);
                } else {
                    component.setBackground(color);
                    colorReset = true;
                    Thread.sleep(intervalTime);
                }
            } catch (InterruptedException ie) {
                component.setBackground(originColor);
                break;
            }
        }
        component.setBackground(originColor);
    }
}