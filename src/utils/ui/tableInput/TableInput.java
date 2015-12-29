package utils.ui.tableInput;

import info.clearthought.layout.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import utils.data.*;
import utils.ui.*;
import utils.ui.tableInput.inputs.*;

public class TableInput implements ActionListener, FocusListener {
    private List<String> titles;
    @SuppressWarnings("rawtypes")
    private List<TableInputComponent> componentDefs;
    @SuppressWarnings("rawtypes")
    private List<List<TableInputComponent>> inputComponents;
    
    private JButton selectedAddButton, selectedRemoveButton;
    
    private Container container;
    private TableLayout tableLayout;
    private int selectedRowIndex;
    private boolean canAddRow, canRemoveRow, showSeq;
    
    private boolean isEnabled;
    
    @SuppressWarnings("rawtypes")
    public TableInput(Container c, List<String> titles, List<TableInputComponent> componentDefs) {
        this(c, titles, componentDefs, 0, true, true, true);
    }
    
    @SuppressWarnings("rawtypes")
    public TableInput(Container c, List<String> titles, List<TableInputComponent> componentDefs, int initalRows, boolean canAdd, boolean canRemove, boolean showSeq) {
        container = c;
        this.titles = titles;
        this.componentDefs = componentDefs;
        inputComponents = new ArrayList<List<TableInputComponent>>();
        tableLayout = new TableLayout();
        selectedRowIndex = -1;
        canAddRow = canAdd;
        canRemoveRow = canRemove;
        this.showSeq = showSeq;
        selectedAddButton = null;
        selectedRemoveButton = null;
        layout(initalRows);
    }
    
    public int getRows() {
        return inputComponents.size();
    }
    
    public int getCols() {
        return componentDefs.size();
    }
    
    @SuppressWarnings("rawtypes")
    private void addRowComponents(int rowIndex) {
        List<TableInputComponent> list = new ArrayList<TableInputComponent>();
        TableInputComponent addBt = null, removeBt = null;
        TableInputComponent seqLabel = null;
        if (canAddRow) {
            addBt = new ButtonInputComponent("+");
            JButton addButton = (JButton)addBt.getComponent();
            GuiUtils.setSmallButton(addButton);
            addButton.setActionCommand("add");
            addButton.setVisible(false);
            TableLayoutUtil.addComponent(container, addButton, rowIndex, 1, 0, 1, TableLayout.FULL, TableLayout.FULL);
            addButton.addActionListener(this);
        }
        if (canRemoveRow) {
            removeBt = new ButtonInputComponent("-");
            JButton removeButton = (JButton) removeBt.getComponent();
            GuiUtils.setSmallButton(removeButton);
            removeButton.setActionCommand("remove");
            removeButton.setVisible(false);
            TableLayoutUtil.addComponent(container, removeButton, rowIndex, 1, 1, 1, TableLayout.FULL, TableLayout.FULL);
            removeButton.addActionListener(this);
        }
        if (showSeq) {
            seqLabel = new LabelTableComponent(String.valueOf(rowIndex));
            TableLayoutUtil.addComponent(container, seqLabel.getComponent(), rowIndex, 1, 2, 1, TableLayout.CENTER, TableLayout.CENTER);
        }
        list.add(addBt);
        list.add(removeBt);
        list.add(seqLabel);
        for (int j = 0; j < componentDefs.size(); j++) {
            TableInputComponent inputComponent = componentDefs.get(j).cloneComponent();
            TableLayoutUtil.addComponent(container, inputComponent.getComponent(), 
                    rowIndex, 1, ((showSeq)?3:2)+j, 1, TableLayout.FULL, TableLayout.CENTER);
            list.add(inputComponent);
            inputComponent.getComponent().addFocusListener(this);
        }
        inputComponents.add(rowIndex-1, list);
        refreshRowSeq(rowIndex);
        refreshLayout();
    }
    
    public void setRowValues(int rowIndex, List<Object> values) {
        for (int i = 0; i < values.size(); i++) {
            setValue(rowIndex, i, values.get(i));
        }
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void setValue(int rowIndex, int colIndex, Object value) {
        if (rowIndex >= 0 && rowIndex < getRows()) {
            List<TableInputComponent> row = inputComponents.get(rowIndex);
            if (colIndex >= 0 && colIndex < getCols()) {
                TableInputComponent c = row.get(colIndex+((showSeq)? 3 : 2));
                if (c != null) {
                    c.setValue(value);
                }
            }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public List<Object> getRowValues(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < getRows()) {
            List<Object> values = new ArrayList<Object>();
            List<TableInputComponent> row = inputComponents.get(rowIndex);
            for (int i = 3; i < row.size(); i++) {
                values.add(row.get(i).getValue());
            }
            return values;
        } else {
            return null;
        }
    }
    
    @SuppressWarnings("rawtypes")
    public Object getValue(int rowIndex, int colIndex) {
        if (rowIndex >= 0 && rowIndex < getRows()) {
            List<TableInputComponent> row = inputComponents.get(rowIndex);
            if (colIndex >= 0 && colIndex < row.size() - 3) {
                TableInputComponent c = row.get(colIndex + 3);
                if (c != null) {
                    return c.getValue();
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
    public void addRow() {
        int rowIndex = inputComponents.size()+1;
        tableLayout.insertRow(rowIndex, TableLayout.PREFERRED);
        addRowComponents(rowIndex);
        selectRow(rowIndex-1);
    }
    
    public void addRow(List<Object> values) {
        addRow();
        int rowIndex = getRows()-1;
        setRowValues(rowIndex, values);
    }
    
    public int insertRow(int i) {
        if (i >= 0 && i <= getRows()) {
            int rowIndex = i+1;
            tableLayout.insertRow(rowIndex, TableLayout.PREFERRED);
            addRowComponents(rowIndex);
            selectRow(i);
            return i;
        } else {
            return -1;
        }
    }
    
    public int insertRow(int i, List<Object> values) {            
        int index = insertRow(i);
        if (index > -1) {
            setRowValues(index, values);
            return index;
        } else {
            return -1;
        }
    }
    
    @SuppressWarnings("rawtypes")
    public void removeRow(int i) {
        int rowIndex= i+1;
        tableLayout.deleteRow(rowIndex);
        List<TableInputComponent> list = inputComponents.get(i);
        for (TableInputComponent c : list) {
            if (c != null) {
                container.remove(c.getComponent());
            }
        }
        inputComponents.remove(i);
        if (i < getRows()) {
            selectRow(i);
        } else if (getRows() > 0) {
            selectRow(getRows()-1);
        }
        refreshRowSeq(i);
        refreshLayout();
    }
    
    public void removeAllRow() {
        while (getRows() > 0) {
            removeRow(0);
        }
    }
    
    @SuppressWarnings("rawtypes")
    public void refreshRowSeq(int i) {
        if (showSeq && i >= 0 && i < getRows()) {
            for (int r = i; r < getRows(); r++) {
                TableInputComponent c = inputComponents.get(r).get(2);
                if (c != null) {
                    ((JLabel)c.getComponent()).setText(String.valueOf(r+1));
                }
            }
        }
    }
    
    @SuppressWarnings("rawtypes")
    public void selectRow(int i) {
        if (i < 0 || i >= getRows()) {
            return;
        }
        if (selectedAddButton != null) {
            selectedAddButton.setVisible(false);
            selectedAddButton = null;
        }
        if (selectedRemoveButton != null) {
            selectedRemoveButton.setVisible(false);
            selectedRemoveButton = null;
        }
        if (i >= 0 && i < inputComponents.size()) {
            selectedRowIndex = i;
            List<TableInputComponent> list = inputComponents.get(i);
            if (list.get(0) != null) {
                selectedAddButton = (JButton)list.get(0).getComponent();
                selectedAddButton.setVisible(true);
            }
            if (list.get(1) != null) {
                selectedRemoveButton = (JButton)list.get(1).getComponent();
                selectedRemoveButton.setVisible(true);
            }
        }
    }
    
    public void layout(int initRows) {
        double[] rows = ArrayUtil.createDoubleArray(getRows()+1, TableLayout.PREFERRED),
            cols = new double[titles.size() + ((showSeq)?3:2)];
        cols[0] = cols[1] = TableLayout.PREFERRED;
        if (showSeq) {
            cols[2] = 0.05;
        }
        double avgWidth = (0.9-((showSeq)?0.1:0))/titles.size();
        for (int i = 2+((showSeq)?1:0); i < cols.length; i++) {
            cols[i] = avgWidth;
        }
        tableLayout.setRow(rows);
        tableLayout.setColumn(cols);
        container.setLayout(tableLayout);
        if (canAddRow) {
            JButton addButton = GuiUtils.createSmallButton("+");
            addButton.setActionCommand("parentAdd");
            addButton.addActionListener(this);
            TableLayoutUtil.addComponent(container, addButton, 0, 1, 0, 2, TableLayout.CENTER, TableLayout.CENTER);
        }
        if (showSeq) {
            JLabel label = new JLabel("項");
            TableLayoutUtil.addComponent(container, label, 0, 1, 2, 1, TableLayout.CENTER, TableLayout.CENTER);
        }
        for (int i = 0; i < titles.size(); i++) {
            TableLayoutUtil.addComponent(container, new JLabel(titles.get(i)), 0, 1, i + ((showSeq)?3:2), 1, TableLayout.CENTER, TableLayout.CENTER);
        }
        for (int i = 0; i < initRows; i++) {
            addRow();
        }
        refreshLayout();
    }
    
    @SuppressWarnings("rawtypes")
    public TableInputComponent getInputComponent(int r, int c) {
        if (r >= 0 && r < getRows()) {
            if (c >= 0 && c < getCols()) {
                return inputComponents.get(r).get(c+3);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    
    public int getSelectedRowIndex() {
        return selectedRowIndex;
    }
    
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        Object src = evt.getSource();
        int rowIndex = rowIndexOf(src);
        if (cmd.equals("parentAdd")) {
            insertRow(0);
        } else if (cmd.equals("add")) {
            insertRow(rowIndex+1);
        } else if (cmd.equals("remove")) {
            removeRow(rowIndex);
        }
    }
    
    public void focusGained(FocusEvent evt) {
        Object src = evt.getSource();
        int rowIndex = rowIndexOf(src);
        if (rowIndex > -1) {
            selectRow(rowIndex);
        }
    }
    
    @SuppressWarnings("rawtypes")
    public int rowIndexOf(Object src) {
        for (int i = 0; i < inputComponents.size(); i++) {
            List<TableInputComponent> list = inputComponents.get(i);
            for (int j = 0; j < list.size(); j++) {
                TableInputComponent c = list.get(j);
                if (c != null && c.getComponent() == src) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void focusLost(FocusEvent arg0) {
        // Ignored
    }
    
    @SuppressWarnings("rawtypes")
    public void setEnabled(boolean enable) {
        for (List<TableInputComponent> componentList : inputComponents) {
            for (TableInputComponent c : componentList) {
                if (c != null) {
                    c.setEnabled(enable);
                }
            }
        }
        isEnabled = enable;
    }
    
    public boolean isEnabled() {
        return isEnabled;
    }
    
    public void refreshLayout() {
        container.validate();
        container.repaint();
    }
}