package utils.ui;

//import java.awt.*;

import javax.swing.*;

public class FormComponent {
    private int row, col, rowSpan, colSpan;
    private double titleWeight = 0.3, componentWeight = 0.7;
    private JComponent component;
    private String title;
    
    public FormComponent(int r, int c) {
        this(null, r, c, 1, 1, null);
    }
    
    public FormComponent(String title, int r, int c) {
        this(null, r, c, 1, 1, null);
    }
    
    public FormComponent(int r, int c, JComponent comp) {
        this(null, r, c, 1, 1, comp);
    }
    
    public FormComponent(String t, int r, int c, JComponent comp) {
        this(t, r, c, 1, 1, comp);
    }
    
    public FormComponent(String t, int r, int c, int rs, int cs, JComponent comp) {
        title = t;
        row = r;
        col = c;
        rowSpan = rs;
        colSpan = cs;
        component = comp;
    }
    
    public void setTitle(String t) {
        title = t;
    }
    
    public void setRow(int r) {
        row = r;
    }
    
    public void setCol(int c) {
        col = c;
    }
    
    public void setRowSpan(int rs) {
        rowSpan = rs;
    }
    
    public void setColSpan(int cs) {
        colSpan = cs;
    }
    
    public void setComponent(JComponent jc) {
        component = jc;
    }
    
    public void setTitleWeight(double w) {
        titleWeight = w;
    }
    
    public void setComponentWeight(double w) {
        componentWeight = w;
    }
    
    public double getTitleWeight() {
        return titleWeight;
    }
    
    public double getComponentWeight() {
        return componentWeight;
    }
    
    public int getRowIndex() {
        return row;
    }
    
    public int getColumnIndex() {
        return col;
    }
    
    public int getRowSpan() {
        return rowSpan;
    }
    
    public int getColSpan() {
        return colSpan;
    }
    
    public String getTitle() {
        return title;
    }
    
    public JComponent getInputComponent() {
        return component;
    }
    
    public boolean hasTitle() {
        return (title != null);
    }
}
