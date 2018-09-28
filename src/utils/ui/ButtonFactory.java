package utils.ui;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ButtonFactory {
    public static JMenuItem createJMenuItem(String name) {
        return new JMenuItem(name);
    }
    
    public static JMenuItem createJMenuItem(String name, char c) {
        JMenuItem item = new JMenuItem(name);
        setMnemonic(item, c);
        return item;
    }
    
    public static JMenuItem createJMenuItem(String name, int n) {
        JMenuItem item = createJMenuItem(name);
        setMnemonic(item, n);
        return item;
    }
    
    public static JMenu createJMenu(String name) {
        return new JMenu(name);
    }
    
    public static JMenu createJMenu(String name, char c) {
        JMenu item = createJMenu(name);
        setMnemonic(item, c);
        return item;
    }
    
    public static JMenu createJMenu(String name, int n) {
        JMenu item = createJMenu(name);
        setMnemonic(item, n);
        return item;
    }
    
    public static JMenuItem createJMenItem(String name, int n, ActionListener l) {
        JMenuItem item = createJMenuItem(name, n);
        item.addActionListener(l);
        return item;
    }
    
    public static JMenuItem createJMenuItem(String name, String cmd, int n, ActionListener l) {
        JMenuItem item = createJMenuItem(name, cmd, l);
        setMnemonic(item, n);
        return item;
    }
    
    public static JMenuItem createJMenuItem(String name, char c, ActionListener l) {
        JMenuItem item = createJMenuItem(name, c);
        item.addActionListener(l);
        return item;
    }
    
    public static JMenuItem createJMenuItem(String name, String cmd, char c, ActionListener l) {
        JMenuItem item = createJMenuItem(name, cmd, l);
        setMnemonic(item, c);
        return item;
    }
    
    public static JMenuItem createJMenuItem(String name, String cmd, ActionListener l) {
        JMenuItem item = createJMenuItem(name);
        item.setActionCommand(cmd);
        item.addActionListener(l);
        return item;
    }
    
    public static JMenuItem createJMenuItem(String name, ActionListener l) {
        JMenuItem item = createJMenuItem(name);
        item.addActionListener(l);
        return item;
    }
    
    public static JButton createJButton(String name) {
        return new JButton(name);
    }
    
    public static JButton createJButton(String name, String cmd, ActionListener l) {
        JButton item = createJButton(name);
        item.setActionCommand(cmd);
        item.addActionListener(l);
        return item;
    }
    
    public static JButton createJButton(String name, int n) {
        JButton item = createJButton(name);
        setMnemonic(item, n);
        return item;
    }
    
    public static JButton createJButton(String name, char c) {
        JButton item = createJButton(name);
        setMnemonic(item, c);
        return item;
    }
    
    public static JButton createJButton(String name, int n, ActionListener l) {
        JButton item = createJButton(name, n);
        item.addActionListener(l);
        return item;
    }
    
    public static JButton createJButton(String name, char c, ActionListener l) {
        JButton item = createJButton(name, c);
        item.addActionListener(l);
        return item;
    }
    
    public static JButton createJButton(String name, String cmd, int n, ActionListener l) {
        JButton item = createJButton(name, cmd, l);
        setMnemonic(item, n);
        return item;
    }
    
    public static JButton createJButton(String name, String cmd, char c, ActionListener l) {
        JButton item = createJButton(name, cmd, l);
        setMnemonic(item, c);
        return item;
    }
    
    public static void setMnemonic(AbstractButton button, char c) {
        c = Character.toLowerCase(c);
        if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            button.setMnemonic(c);
            button.setText(button.getText() + "(" + Character.toUpperCase(c) + ")");
            button.setDisplayedMnemonicIndex(button.getText().length() - 2);
        }
    }
    
    public static void setMnemonic(AbstractButton button, int n) {
        if ((n >= KeyEvent.VK_0 && n <= KeyEvent.VK_9) ||
                (n >= KeyEvent.VK_A && n <= KeyEvent.VK_Z)) {
            setMnemonic(button, (char)n);
        } else {
            button.setMnemonic(n);
        }
    }
}
