package utils.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.io.Serializable;

public class ExtendedBorderLayout implements LayoutManager2, Serializable {
    int hgap, vgap;
    Component north, west, east, south, center;
    Component northWest, northEast, southWest, southEast;
    
    public static final int MINIMUM = 1, MAXIMUM = 2, PREFERRED = 3, WIDTH = 4, HEIGHT = 5, MAX = 6, MIN = 7;
    
    public static final String NORTH = "North", SOUTH = "South", EAST = "East", WEST = "West",
        NORTH_WEST = "NorthWest", NORTH_EAST = "NorthEst",
        SOUTH_WEST = "SouthWest", SOUTH_EAST = "SouthEast",
        CENTER = "Center";

     private static final long serialVersionUID = -8658291919501921765L;

    public ExtendedBorderLayout() {
        this(0, 0);
    }
    
    public ExtendedBorderLayout(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }
    
    public int getHgap() {
        return hgap;
    }
    
    public void setHgap(int hgap) {
        this.hgap = hgap;
    }
    
    public int getVgap() {
        return vgap;
    }
    
    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    public void addLayoutComponent(Component comp, Object constraints) {
        synchronized (comp.getTreeLock()) {
            if ((constraints == null) || (constraints instanceof String)) {
                String name = (String)constraints;
                if (name == null) {
                    name = "Center";
                }
                if (CENTER.equals(name)) {
                    center = comp;
                } else if (NORTH.equals(name)) {
                    north = comp;
                } else if (SOUTH.equals(name)) {
                    south = comp;
                } else if (EAST.equals(name)) {
                    east = comp;
                } else if (WEST.equals(name)) {
                    west = comp;
                } else if (NORTH_WEST.equals(name)) {
                    northWest = comp;
                } else if (NORTH_EAST.equals(name)) {
                    northEast = comp;
                } else if (SOUTH_WEST.equals(name)) {
                    southWest = comp;
                } else if (SOUTH_EAST.equals(name)) {
                    southEast = comp;
                } else {
                    throw new IllegalArgumentException("cannot add to layout: unknown constraint: " + name);
                }
            } else {
                throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
            }
        }
    }

    @Deprecated
    public void addLayoutComponent(String name, Component comp) {
        synchronized (comp.getTreeLock()) {
            if (name != null) {
                addLayoutComponent(comp, name);
            } else {
                throw new IllegalArgumentException("cannot add to layout: constraint must be a string (or null)");
            }
        }
    }
        
    public void removeLayoutComponent(Component comp) {
        synchronized (comp.getTreeLock()) {
            if (comp == center) {
                center = null;
            } else if (comp == north) {
                north = null;
            } else if (comp == south) {
                south = null;
            } else if (comp == east) {
                east = null;
            } else if (comp == west) {
                west = null;
            } else if (comp == northWest) {
                northWest = null;
            } else if (comp == northEast) {
                northEast = null;
            } else if (comp == southWest) {
                southWest = null;
            } else if (comp == southEast) {
                southEast = null;
            }
        }
    }
    
    public Component getLayoutComponent(Object constraints) {
        if (CENTER.equals(constraints)) {
            return center;
        } else if (NORTH.equals(constraints)) {
            return north;
        } else if (SOUTH.equals(constraints)) {
            return south;
        } else if (WEST.equals(constraints)) {
            return west;
        } else if (EAST.equals(constraints)) {
            return east;
        } else if (NORTH_WEST.equals(constraints)) {
            return northWest;
        } else if (NORTH_EAST.equals(constraints)) {
            return northEast;
        } else if (SOUTH_WEST.equals(constraints)) {
            return southWest;
        } else if (SOUTH_EAST.equals(constraints)) {
            return southEast;
        } else {
            throw new IllegalArgumentException("cannot get component: unknown constraint: " + constraints);
        }
    }

    public Component getLayoutComponent(Container target, Object constraints) {
        //boolean ltr = target.getComponentOrientation().isLeftToRight();
        Component result = null;
        if (NORTH.equals(constraints)) {
            result = north;
        } else if (SOUTH.equals(constraints)) {
            result = south;
        } else if (WEST.equals(constraints)) {
            result = west;
        } else if (EAST.equals(constraints)) {
            result = east;
        } else if (CENTER.equals(constraints)) {
            result = center;
        } else {
            throw new IllegalArgumentException("cannot get component: invalid constraint: " + constraints);
        }
        return result;
    }
    
    public Object getConstraints(Component comp) {
        if (comp == null){
            return null;
        }
        if (comp == center) {
            return CENTER;
        } else if (comp == north) {
            return NORTH;
        } else if (comp == south) {
            return SOUTH;
        } else if (comp == west) {
            return WEST;
        } else if (comp == east) {
            return EAST;
        } else if (comp == northWest) {
            return NORTH_WEST;
        } else if (comp == northEast) {
            return NORTH_EAST;
        } else if (comp == southWest) {
            return SOUTH_WEST;
        } else if (comp == southEast) {
            return SOUTH_EAST;
        } else {
            return null;
        }
    }
    
    private int lengthOf(int sizeType, int lengthType, int gap, Component ... comps) {
        int result = 0;
        Component prev = null;
        for (Component comp : comps) {
            if (comp == null) {
                continue;
            }
            Dimension d = null;
            switch (sizeType) {
                case MINIMUM:
                    d = comp.getMinimumSize();
                    break;
                case MAXIMUM:
                    d = comp.getMaximumSize();
                    break;
                case PREFERRED:
                    d = comp.getPreferredSize();
                    break;
            }
            if (d != null && comp != null) {
                result += ((lengthType == HEIGHT)? d.height : d.width) + ((prev != null && gap > 0)? gap : 0);
            }
            prev = comp;
        }
        return result;
    }
    
    private int lengthOfComponents(int sizeType, int lengthType, int compareType, int suggestedLength,
            Component ... comps) {
        int result = suggestedLength;
        for (Component comp : comps) {
            if (comp == null) {
                continue;
            }
            Dimension d = null;
            switch (sizeType) {
                case MINIMUM:
                    d = comp.getMinimumSize();
                    break;
                case MAXIMUM:
                    d = comp.getMaximumSize();
                    break;
                case PREFERRED:
                    d = comp.getPreferredSize();
                    break;
            }
            int l = (lengthType == WIDTH)? d.width : d.height;
            if (d != null) {
                if (compareType == MAX && l > result) {
                    result = l;
                } else if (compareType == MIN && l < result) {
                    result = l;
                }
            }
        }
        return result;
    }

    public Dimension minimumLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            Dimension dim = new Dimension(0, 0);
            
            dim.width = Math.max(
                Math.max(
                    lengthOf(MINIMUM, WIDTH, hgap, northWest, north, northEast),
                    lengthOf(MINIMUM, WIDTH, hgap, west, center, east)
                ),
                lengthOf(MINIMUM, WIDTH, hgap, southWest, south, southEast)
            );
            dim.height = Math.max(
                Math.max(
                    lengthOf(MINIMUM, HEIGHT, vgap, northWest, west, northWest),
                    lengthOf(MINIMUM, HEIGHT, vgap, north, center, south)
                ),
                lengthOf(MINIMUM, HEIGHT, vgap, northEast, east, southEast)
            );
            
            Insets insets = target.getInsets();
            dim.width += insets.left + insets.right;
            dim.height += insets.top + insets.bottom;
            
            return dim;
        }
    }
    
    public Dimension preferredLayoutSize(Container target) {
        synchronized (target.getTreeLock()) {
            Dimension dim = new Dimension(0, 0);
            
            dim.width = Math.max(
                Math.max(
                    lengthOf(PREFERRED, WIDTH, hgap, northWest, north, northEast),
                    lengthOf(PREFERRED, WIDTH, hgap, west, center, east)
                ),
                lengthOf(PREFERRED, WIDTH, hgap, southWest, south, southEast)
            );
            dim.height = Math.max(
                Math.max(
                    lengthOf(PREFERRED, HEIGHT, vgap, northWest, west, southWest),
                    lengthOf(PREFERRED, HEIGHT, vgap, north, center, south)
                ),
                lengthOf(PREFERRED, HEIGHT, vgap, northEast, east, southEast)
            );

            Insets insets = target.getInsets();
            dim.width += insets.left + insets.right;
            dim.height += insets.top + insets.bottom;
            
            return dim;
        }
    }
    
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    
    public float getLayoutAlignmentX(Container parent) {
        return 0.5f;
    }
    
    public float getLayoutAlignmentY(Container parent) {
        return 0.5f;
    }

    public void invalidateLayout(Container target) {}
    
    public void layoutContainer(Container target) {
        synchronized (target.getTreeLock()) {
            Insets insets = target.getInsets();
            int top = insets.top, bottom = target.getHeight() - insets.bottom, left = insets.left,
                right = target.getWidth() - insets.right,
                topHeight = lengthOfComponents(PREFERRED, HEIGHT, MAX, 0, northWest, north, northEast),
                bottomHeight = lengthOfComponents(PREFERRED, HEIGHT, MAX, 0, southWest, south, southEast),
                centerHeight = lengthOfComponents(PREFERRED, HEIGHT, MAX, 0, west, center, east),
                leftWidth = lengthOfComponents(PREFERRED, WIDTH, MAX, 0, northWest, west, southWest),
                rightWidth = lengthOfComponents(PREFERRED, WIDTH, MAX, 0, northEast, east, southEast),
                centerWidth = lengthOfComponents(PREFERRED, WIDTH, MAX, 0, north, center, south);
            
            int centerHeightSpan = bottom - top - topHeight - bottomHeight,
                centerWidthSpan = right - left - leftWidth - rightWidth;
            
            if (leftWidth > 0) {
                centerWidthSpan -= hgap;
            }
            if (rightWidth > 0) {
                centerWidthSpan -= hgap;
            }
            if (topHeight > 0) {
                centerHeightSpan -= vgap;
            }
            if (bottomHeight > 0) {
                centerHeightSpan -= vgap;
            }
            
            if (centerHeight > 0) {
                centerHeight = Math.max(centerHeight, centerHeightSpan);
            } else {
                if (topHeight > 0) {
                    topHeight += centerHeightSpan + vgap;
                } else if (bottomHeight > 0) {
                    bottomHeight += centerHeightSpan + vgap;
                }
            }
            if (centerWidth > 0) {
                centerWidth = Math.max(centerWidth, centerWidthSpan);
            } else {
                if (leftWidth > 0) {
                    leftWidth += centerWidthSpan + hgap;
                } else if (rightWidth > 0) {
                    rightWidth += centerWidthSpan + hgap;
                }
            }
            
            int secondLeft = left + leftWidth + ((leftWidth > 0)? hgap : 0), lastLeft = right - rightWidth,
                secondTop = top + topHeight + ((topHeight > 0)? vgap : 0), lastTop = bottom - bottomHeight;
            if (northWest != null) {
                northWest.setSize(leftWidth, topHeight);
                northWest.setBounds(left, top, leftWidth, topHeight);
            }
            if (north != null) {
                north.setSize(centerWidth, topHeight);
                north.setBounds(secondLeft, top, centerWidth, topHeight);
            }
            if (northEast != null) {
                northEast.setSize(rightWidth, topHeight);
                northEast.setBounds(lastLeft, top, rightWidth, topHeight);
            }
            if (west != null) {
                west.setSize(leftWidth, centerHeight);
                west.setBounds(left, secondTop, leftWidth, centerHeight);
            }
            if (center != null) {
                center.setSize(centerWidth, centerHeight);
                center.setBounds(secondLeft, secondTop, centerWidth, centerHeight);
            }
            if (east != null) {
                east.setSize(rightWidth, centerHeight);
                east.setBounds(lastLeft, secondTop, rightWidth, centerHeight);
            }
            if (southWest != null) {
                southWest.setSize(leftWidth, bottomHeight);
                southWest.setBounds(left, lastTop, leftWidth, bottomHeight);
            }
            if (south != null) {
                south.setSize(centerWidth, bottomHeight);
                south.setBounds(secondLeft, lastTop, centerWidth, bottomHeight);
            }
            if (southEast != null) {
                southEast.setSize(rightWidth, bottomHeight);
                southEast.setBounds(lastLeft, lastTop, rightWidth, bottomHeight);
            }
        }
    }
    
    @Override
    public String toString() {
        return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + "]";
    }
}
