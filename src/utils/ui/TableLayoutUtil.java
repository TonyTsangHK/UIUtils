package utils.ui;

import info.clearthought.layout.*;

import java.awt.*;
import javax.swing.JLabel;

public class TableLayoutUtil {
    private TableLayoutUtil() {}
    
    public static void addComponents(Container container, int startRow, int rows, int startCol, int cols,
            int hAlign, int vAlign, Component ... comps) {
        int c = 0;
        for (int i = startRow; i < startRow + rows; i++) {
            for (int j = startCol; j < cols; j++) {
                if (c < comps.length) {
                    addComponent(container, comps[c++], i, 1, j, 1, hAlign, vAlign);
                } else {
                    return;
                }
            }
        }
    }

    public static void addComponents(Container c, int startX, int startY, int cols, int labelHAlign, int labelVAlign,
            int hAlign, int vAlign, String[] labels, Component[] components) {
        int x = startX, y = startY;
        TableLayoutConstraints constraints = new TableLayoutConstraints();
        
        for (int i = 0; i < labels.length && i < components.length; i++) {
            if (y >= cols) {
                y = 0;
                x++;
            }
            String labelString = labels[i];
            Component comp = components[i];

            if (labelString == null || comp == null) {
                y += 2;
            } else {
                constraints.col1 = constraints.col2 = y++;
                constraints.row1 = constraints.row2 = x;

                constraints.vAlign = labelVAlign;
                constraints.hAlign = labelHAlign;
                c.add(new JLabel(labelString), constraints);

                constraints.col1 = constraints.col2 = y++;
                constraints.vAlign = vAlign;
                constraints.hAlign = hAlign;
                c.add(comp, constraints);
            }
        }
    }
    
    public static void addComponent(Container c, Component comp, int row, int rSpan, 
            int col, int cSpan, int hAlign, int vAlign) {
        if (comp != null) {
            TableLayoutConstraints constraints = new TableLayoutConstraints();
            constraints.col1 = col;
            constraints.col2 = (cSpan>0)? (col+cSpan-1) : col;
            constraints.row1 = row;
            constraints.row2 = (rSpan>0)? (row+rSpan-1) : row;
            constraints.vAlign = vAlign;
            constraints.hAlign = hAlign;
            c.add(comp, constraints);
        }
    }
    
    public static void addComponent(Container c, Component comp, int row, int col) {
        addComponent(c, comp, row, 1, col, 1, TableLayoutConstraints.CENTER, 
                TableLayoutConstraints.TOP);
    }
    
    public static void addComponent(Container c, Component comp, int row, 
            int rSpan, int col, int cSpan) {
        addComponent(c, comp, row, rSpan, col, cSpan, 
                TableLayoutConstraints.CENTER, TableLayoutConstraints.TOP);
    }
}
