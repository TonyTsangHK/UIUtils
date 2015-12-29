package utils.ui.component;

import info.clearthought.layout.*;

import javax.swing.*;

import utils.ui.*;

import java.awt.event.*;
import java.util.*;

public class MultipleInputComponentPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<InputComponentPanel> inputComponents;
    private ArrayList<JButton> addButtons, removeButtons;
    private JButton mainAddButton;
    private TableLayout tableLayout;
    private JLabel titleLabel;
    private InputComponentCreator creator;
    
    public MultipleInputComponentPanel(InputComponentCreator creator) {
        this.creator = creator;
        inputComponents = new ArrayList<InputComponentPanel>();
        addButtons = new ArrayList<JButton>();
        removeButtons = new ArrayList<JButton>();
        tableLayout = new TableLayout();
        mainAddButton = GuiUtils.createSmallButton("+");
        mainAddButton.addActionListener(this);
        titleLabel = new JLabel("");
        presentLayout();
    }
    
    public void addInputComponent() {
        InputComponentPanel ic = null;
        if (creator != null) {
            ic = creator.createComponent();
        }
        inputComponents.add(ic);
        int r = tableLayout.getNumRow();
        tableLayout.insertRow(tableLayout.getNumRow(), TableLayout.PREFERRED);
        JButton addButton = GuiUtils.createSmallButton("+"), removeButton = GuiUtils.createSmallButton("-");
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        addButtons.add(addButton);
        removeButtons.add(removeButton);
        TableLayoutUtil.addComponent(this, addButton, r, 1, 0, 1, TableLayout.CENTER, TableLayout.CENTER);
        TableLayoutUtil.addComponent(this, removeButton, r, 1, 1, 1, TableLayout.CENTER, TableLayout.CENTER);
        TableLayoutUtil.addComponent(this, ic, r, 1, 2, 1, TableLayout.FULL, TableLayout.CENTER);
        revalidate();
        repaint();
    }
    
    public void insertInputComponent(int i) {
        InputComponentPanel ic = null;
        if (creator != null) {
            ic = creator.createComponent();
        }
        inputComponents.add(i, ic);
        int r = i + 1;
        tableLayout.insertRow(r, TableLayout.PREFERRED);
        JButton addButton = GuiUtils.createSmallButton("+"), removeButton = GuiUtils.createSmallButton("-");
        addButton.addActionListener(this);
        removeButton.addActionListener(this);
        addButtons.add(i, addButton);
        removeButtons.add(i, removeButton);
        TableLayoutUtil.addComponent(this, addButton, r, 1, 0, 1, TableLayout.CENTER, TableLayout.CENTER);
        TableLayoutUtil.addComponent(this, removeButton, r, 1, 1, 1, TableLayout.CENTER, TableLayout.CENTER);
        TableLayoutUtil.addComponent(this, ic, r, 1, 2, 1, TableLayout.FULL, TableLayout.CENTER);
        
        revalidate();
        repaint();
    }
    
    public void removeInputComponent(int i) {
        tableLayout.deleteRow(i+1);
        inputComponents.remove(i);
        addButtons.remove(i);
        removeButtons.remove(i);
        revalidate();
        repaint();
    }
    
    public InputComponentPanel getInputComponent(int i) {
        if (i >= 0 && i < inputComponents.size()) {
            return inputComponents.get(i);
        } else {
            return null;
        }
    }
    
    public ArrayList<InputComponentPanel> getInputComponents() {
        return inputComponents;
    }
    
    public void setTitle(String t) {
        titleLabel.setText(t);
    }
    
    public String getTitle() {
        return titleLabel.getText();
    }
    
    private void presentLayout() {
        setLayout(tableLayout);
        double[] rows = {TableLayout.PREFERRED}, cols = {0.1, 0.1, 0.8};
        tableLayout.setRow(rows);
        tableLayout.setColumn(cols);
        
        TableLayoutUtil.addComponent(this, mainAddButton, 0, 1, 0, 2, TableLayout.CENTER, TableLayout.CENTER);
        TableLayoutUtil.addComponent(this, titleLabel, 0, 1, 2, 1, TableLayout.CENTER, TableLayout.CENTER);
    }
    
    public void actionPerformed(ActionEvent evt) {
        JButton sourceButton = (JButton) evt.getSource();
        if (sourceButton == mainAddButton) {
            insertInputComponent(0);
        } else {
            int ai = addButtons.indexOf(sourceButton),
                    ri = removeButtons.indexOf(sourceButton);
            if (ai != -1) {
                insertInputComponent(ai+1);
            } else {
                removeInputComponent(ri);
            }
        }
    }
    
    public static abstract class InputComponentCreator {
        public abstract InputComponentPanel createComponent();
    }
}
