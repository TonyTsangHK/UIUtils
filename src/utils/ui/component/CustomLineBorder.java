package utils.ui.component;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class CustomLineBorder extends LineBorder {
    private static Border blackLine;
    private static Border grayLine;
    
    private boolean paintWest = true, paintEast = true, paintNorth = true, paintSouth = true;
    
    private int northAdjust = 0, southAdjust = 0, westAdjust = 0, eastAdjust = 0;
    
    public static Border createBlackLineBorder() {
        if (blackLine == null) {
            blackLine = new CustomLineBorder(Color.black, 1);
        }
        return blackLine;
    }
    
    public static Border createGrayLineBorder() {
        if (grayLine == null) {
            grayLine = new CustomLineBorder(Color.gray, 1);
        }
        return grayLine;
    }

    public CustomLineBorder(Color color) {
        super(color, 1);
    }
    
    public CustomLineBorder(Color color, int thickness)  {
        super(color, thickness);
    }
    
    public CustomLineBorder(
            Color color, int thickness, boolean paintNorth, boolean paintEast,
            boolean paintSouth, boolean paintWest
    ) {
        super(color, thickness);
        setPaintEdges(paintNorth, paintEast, paintSouth, paintWest);
    }
    
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Color oldColor = g.getColor();
        
        g.setColor(lineColor);
        for(int i = 0; i < thickness; i++)  {
            if (paintNorth) {
                int ny = y + northAdjust + i;
                if (ny > y + height - 1) {
                    ny = y + height - 1;
                }
                g.drawLine(x, ny, x + width, ny);
            }
            if (paintSouth) {
                int sy = y + height - southAdjust - i - 1;
                if (sy < y) {
                    sy = y;
                }
                g.drawLine(x, sy, x+width, sy);
            }
            if (paintEast) {
                int ex = x + width - eastAdjust - i - 1;
                if (ex < x) {
                    ex = x;
                }
                g.drawLine(ex, y, ex, y+height);
            }
            if (paintWest) {
                int wx = x + westAdjust + i;
                if (wx > x + width - 1) {
                    wx = x + width - 1;
                }
                g.drawLine(wx, y, wx, y+height);
            }
        }
        g.setColor(oldColor);
    }

    @Override
    public boolean getRoundedCorners() {
        // always false, as rounded corners is not supported
        return false;
    }
    
    public boolean isBorderOpaque() {
        // always true, as rounded corners is not supported
        return true; 
    }
    
    public void setPaintWest(boolean paint) {
        paintWest = paint;
    }
    
    public boolean isPaintWest() {
        return paintWest;
    }
    
    public void setPaintEast(boolean paint) {
        paintEast = paint;
    }
    
    public boolean isPaintEast() {
        return paintEast;
    }
    
    public void setPaintNorth(boolean paint) {
        paintNorth = paint;
    }
    
    public boolean isPaintNorth() {
        return paintNorth;
    }
    
    public void setPaintSouth(boolean paint) {
        paintSouth = paint;
    }
    
    public boolean isPaintSouth() {
        return paintSouth;
    }
    
    public void setPaintEdges(boolean north, boolean east, boolean south, boolean west) {
        setPaintNorth(north);
        setPaintEast(east);
        setPaintSouth(south);
        setPaintWest(west);
    }
    
    public void setNorthAdjust(int adjust) {
        northAdjust = adjust;
    }
    
    public void setSouthAdjust(int adjust) {
        southAdjust = adjust;
    }
    
    public void setWestAdjust(int adjust) {
        westAdjust = adjust;
    }
    
    public void setEastAdjust(int adjust) {
        eastAdjust = adjust;
    }
    
    public int getNorthAdjust() {
        return northAdjust;
    }
    
    public int getSouthAdjust() {
        return southAdjust;
    }
    
    public int getWestAdjust() {
        return westAdjust;
    }
    
    public int getEastAdjust() {
        return eastAdjust;
    }
    
    public boolean isNorthAdjusted() {
        return northAdjust != 0;
    }
    
    public boolean isSouthAdjusted() {
        return southAdjust != 0;
    }
    
    public boolean isWestAdjusted() {
        return westAdjust != 0;
    }
    
    public boolean isEastAdjusted() {
        return eastAdjust != 0;
    }
    
    public boolean isAdjusted() {
        return isNorthAdjusted() || isSouthAdjusted() || isWestAdjusted() || isEastAdjusted();
    }
    
    public void setAdjust(int northAdjust, int southAdjust, int westAdjust, int eastAdjust) {
        setNorthAdjust(northAdjust);
        setSouthAdjust(southAdjust);
        setWestAdjust(westAdjust);
        setEastAdjust(eastAdjust);
    }
}
