package utils.ui;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class FormComponentManager {
    private ArrayList<FormComponent> components;
    private int currentRow = 0, currentColumn = 0;
    private int componentColumns;
    
    public FormComponentManager(int c) {
        componentColumns = c;
        components = new ArrayList<FormComponent>();
    }
    
    public void addComponent(String t, JComponent c) {
        FormComponent fc = new FormComponent(t, currentRow, currentColumn, c);
        incrementCurrentIndexes();
        components.add(fc);
    }
    
    private void incrementCurrentIndexes() {
        if (currentColumn + 1 > componentColumns) {
            currentColumn = 0;
            currentRow++;
        } else {
            currentColumn++;
        }
    }
    
    public void addComponent(JComponent c) {
        FormComponent fc = new FormComponent(currentRow, currentColumn, c);
        incrementCurrentIndexes();
        components.add(fc);
    }
    
    public ArrayList<FormComponent> getComponentList() {
        return components;
    }
    
    public int computeTotalRows() {
        if (components.isEmpty()) {
            return 0;
        } else {
            return (int)Math.ceil(components.size() / componentColumns);
        }
    }
    
    public double computeWeighty() {
        return 1.0 / computeTotalRows();
    }
    
    public void addComponents(Container c) {
        if (c.getLayout() instanceof GridBagLayout) {
            GridBagConstraints cons = new GridBagConstraints();
            cons.anchor = GridBagConstraints.PAGE_START;
            cons.insets = new Insets(2, 2, 2, 2);
            double weightx = 1.0 / componentColumns;
            cons.weighty = 1.0 / computeTotalRows();
            for (FormComponent fc : components) {
                if (fc.hasTitle()) {
                    cons.gridy = fc.getRowIndex();
                    cons.gridx = fc.getColumnIndex() * 2;
                    cons.gridheight = fc.getRowSpan();
                    cons.anchor = GridBagConstraints.FIRST_LINE_END;
                    cons.weightx = weightx * fc.getTitleWeight();
                    JLabel titleLabel = new JLabel(fc.getTitle());
                    c.add(titleLabel, cons);
                    cons.gridx = fc.getColumnIndex() * 2 + 1;
                    cons.gridwidth = fc.getColSpan();
                    cons.anchor = GridBagConstraints.PAGE_START;
                    cons.weightx = weightx * fc.getComponentWeight();
                    c.add(fc.getInputComponent(), cons);
                } else {
                    cons.weightx = weightx;
                    cons.gridy = fc.getRowIndex();
                    cons.gridx = fc.getColumnIndex() * 2;
                    cons.gridwidth = fc.getColSpan() + 1;
                    cons.gridheight = fc.getRowSpan();
                    c.add(fc.getInputComponent(), cons);
                }
            }
        }
    }
}
