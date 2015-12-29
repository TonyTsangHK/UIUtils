package utils.ui;

import java.awt.*;
import javax.swing.*;

public class GridBagLayoutTool {
    public static void addTitledComponent(Container c, int startX, int startY, 
            double titleWeight, double componentWeight, String title, 
            Component component) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = startX;
            cons.gridy = startY;
            cons.weightx = titleWeight;
            cons.anchor = GridBagConstraints.CENTER;
            c.add(new JLabel(title), cons);
            cons.gridx = startX + 1;
            cons.weightx = componentWeight;
            cons.anchor = GridBagConstraints.LINE_START;
            cons.fill = GridBagConstraints.HORIZONTAL;
            c.add(component, cons);
        } else {
            return;
        }
    }
    
    public static void addTitledComponent(Container c, int startX, int startY, 
            double titleWWeight, double titleHWeight, double componentWWeight, 
            double componentHWeight, String title, Component component) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = startX;
            cons.gridy = startY;
            cons.weightx = titleWWeight;
            cons.weighty = titleHWeight;
            cons.anchor = GridBagConstraints.CENTER;
            c.add(new JLabel(title), cons);
            cons.gridx = startX + 1;
            cons.weightx = componentWWeight;
            cons.weighty = componentHWeight;
            cons.anchor = GridBagConstraints.LINE_START;
            cons.fill = GridBagConstraints.HORIZONTAL;
            c.add(component, cons);
        } else {
            return;
        }
    } 
    
    public static void addTitledComponent(Container c, int startX, int startY, 
            int titleSpan, int componentSpan, double titleWeight, 
            double componentWeight, String title, Component component) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = startX;
            cons.gridy = startY;
            cons.gridwidth = titleSpan;
            cons.weightx = titleWeight;
            cons.anchor = GridBagConstraints.CENTER;
            c.add(new JLabel(title), cons);
            cons.gridx = startX + titleSpan;
            cons.gridwidth = componentSpan;
            cons.weightx = componentWeight;
            cons.anchor = GridBagConstraints.LINE_START;
            cons.fill = GridBagConstraints.HORIZONTAL;
            c.add(component, cons);
        } else {
            return;
        }
    }
    
    public static void addTitledComponent(Container c, int startX, int startY, 
            int titleSpan, int componentSpan, double titleWWeight,
            double titleHWeight, double componentWWeight,
            double componentHWeight, String title, Component component) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = startX;
            cons.gridy = startY;
            cons.gridwidth = titleSpan;
            cons.weightx = titleWWeight;
            cons.weighty = titleHWeight;
            cons.anchor = GridBagConstraints.CENTER;
            c.add(new JLabel(title), cons);
            cons.gridx = startX + titleSpan;
            cons.gridwidth = componentSpan;
            cons.weightx = componentWWeight;
            cons.weighty = componentHWeight;
            cons.anchor = GridBagConstraints.LINE_START;
            cons.fill = GridBagConstraints.HORIZONTAL;
            c.add(component, cons);
        } else {
            return;
        }
    }
    
    public static void addComponent(Container c, int x, int y, int span, 
            double weight, Component component) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = x;
            cons.gridy = y;
            cons.gridwidth = span;
            cons.weightx = weight;
            c.add(component, cons);
        } else {
            return;
        }
    }
    
    public static void addComponent(Container c, int x, int y, int span, 
            double wWeight, double hWeight, Component component) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = x;
            cons.gridy = y;
            cons.gridwidth = span;
            cons.weightx = wWeight;
            cons.weighty = hWeight;
            c.add(component, cons);
        } else {
            return;
        }
    }
    
    public static void addComponent(Container c, int x, int y, int wSpan, int hSpan,
            double weight, Component component) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = x;
            cons.gridy = y;
            cons.gridwidth = wSpan;
            cons.gridheight = hSpan;
            cons.weightx = weight;
            c.add(component, cons);
        } else {
            return;
        }
    }
    
    public static void addComponent(Container c, int x, int y, int wSpan, int hSpan,
            double wWeight, double hWeight, Component component) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = x;
            cons.gridy = y;
            cons.gridwidth = wSpan;
            cons.gridheight = hSpan;
            cons.weightx = wWeight;
            cons.weighty = hWeight;
            c.add(component, cons);
        } else {
            return;
        }
    }
    
    public static void addComponent(Container c, int x, int y, int wSpan, int hSpan,
            double wWeight, double hWeight, int anchor, Component component) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = x;
            cons.gridy = y;
            cons.gridwidth = wSpan;
            cons.gridheight = hSpan;
            cons.weightx = wWeight;
            cons.weighty = hWeight;
            cons.anchor = anchor;
            c.add(component, cons);
        } else {
            return;
        }
    }
    
    public static void addComponent(Container c, int x, int y, int wSpan, int hSpan,
            double wWeight, double hWeight, int anchor, int fillMethod, 
            Component component) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.gridx = x;
            cons.gridy = y;
            cons.gridwidth = wSpan;
            cons.gridheight = hSpan;
            cons.weightx = wWeight;
            cons.weighty = hWeight;
            cons.anchor = anchor;
            cons.fill = fillMethod;
            c.add(component, cons);
        } else {
            return;
        }
    }
}
