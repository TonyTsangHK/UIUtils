package utils.ui.component;

import utils.constants.Orientation;
import utils.event.RangeSelectionChangeEvent;
import utils.event.RangeSelectionChangeEvent.Type;
import utils.listener.RangeSelectionChangeListener;
import utils.listener.initiator.RangeSelectionChangeEventInitiator;
import utils.listener.proxy.RangeSelectionChangeListenerProxy;
import utils.math.IntegerRange;
import utils.math.MathUtil;
import utils.ui.GuiUtils;
import utils.ui.SpringLayoutChainer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("serial")
public class RangeSelectionPanel extends JPanel implements RangeSelectionChangeEventInitiator {
    private List<Component> components;
    private Orientation orientation;
    
    private boolean reverse = false;
    
    private SpringLayout springLayout;
    private SpringLayoutChainer chainer;
    
    private List<RangeComponent> rangeComponents;
    
    private RangeSelectionChangeListenerProxy rangeSelectionChangeListenerProxy = new RangeSelectionChangeListenerProxy();
    
    private RangeComponent selectedRangeComponent = null;
    
    private boolean suppressRangeSelectionChangeEvent = false;
    
    public RangeSelectionPanel() {
        this(Orientation.VERTICAL, false);
    }
    
    public RangeSelectionPanel(Orientation orientation) {
        this(orientation, false);
    }
    
    public RangeSelectionPanel(Orientation orientation, boolean reverse) {
        this.reverse = reverse;
        
        components      = new ArrayList<Component>();
        rangeComponents = new ArrayList<RangeComponent>();
        
        RangeComponent firstRangeComponent = new RangeComponent(" 1 ", -1, -1);
        
        rangeComponents.add(firstRangeComponent);
        
        springLayout = new SpringLayout();
        
        chainer = new SpringLayoutChainer(springLayout);
        
        setLayout(springLayout);
        
        this.orientation = (orientation != null)? orientation : Orientation.VERTICAL;
        
        MouseHandle mouseHandler = new MouseHandle();
        
        this.addMouseMotionListener(mouseHandler);
        this.addMouseListener(mouseHandler);
        
        presentLayout();
    }
    
    private Dimension refreshRangeLabels() {
        int w = 0, h = 0;
        
        for (int i = 0; i < rangeComponents.size(); i++) {
            CustomLineBorder labelLineBorder = (orientation == Orientation.HORIZONTAL)?
                    new CustomLineBorder(Color.BLACK, 2, false, true, false, true) :
                    new CustomLineBorder(Color.BLACK, 2, true, false, true, false);
            RangeComponent rangeComponent = rangeComponents.get(i);
            
            JLabel label = rangeComponent.getLabel();
            
            IntegerRange range = rangeComponent.getRange();
            
            Dimension size = label.getPreferredSize();
            
            w = Math.max(w, size.width);
            h = Math.max(h, size.height);
            
            if (orientation == Orientation.HORIZONTAL) {
                chainer.setN2N(label, this, SpringLayoutChainer.V_PAD);
                
                if (i == 0) {
                    if (components.size() > 0) {
                        if (reverse) {
                            chainer.setE2E(label, components.get(0), 0);
                        } else {
                            chainer.setW2W(label, components.get(0), 0);
                        }
                    } else {
                        if (reverse) {
                            chainer.setE2E(label, this, -SpringLayoutChainer.H_PAD);
                        } else {
                            chainer.setW2W(label, this, SpringLayoutChainer.H_PAD);
                        }
                    }
                } else {
                    if (reverse) {
                        chainer.setE2E(label, components.get(range.getMin()), 0);
                    } else {
                        chainer.setW2W(label, components.get(range.getMin()), 0);
                    }
                }
                
                if (i == rangeComponents.size()-1) {
                    if (components.size() > 0) {
                        if (reverse) {
                            chainer.setW2W(label, components.get(components.size()-1), 0);
                        } else {
                            chainer.setE2E(label, components.get(components.size()-1), 0);
                        }
                    } else {
                        if (reverse) {
                            chainer.setW2W(label, this, SpringLayoutChainer.H_PAD);
                        } else {
                            chainer.setE2E(label, this, -SpringLayoutChainer.H_PAD);
                        }
                    }
                } else {
                    if (reverse) {
                        chainer.setW2W(label, components.get(range.getMax()), 0);
                    } else {
                        chainer.setE2E(label, components.get(range.getMax()), 0);                        
                    }
                }
            } else {
                chainer.setW2W(label, this, SpringLayoutChainer.H_PAD);
                
                if (i == 0) {
                    if (components.size() > 0) {
                        if (reverse) {
                            chainer.setS2S(label, components.get(0), 0);
                        } else {
                            chainer.setN2N(label, components.get(0), 0);
                        }
                    } else {
                        if (reverse) {
                            chainer.setS2S(label, this, -SpringLayoutChainer.V_PAD);
                        } else {
                            chainer.setN2N(label, this, SpringLayoutChainer.V_PAD);
                        }
                    }
                } else {
                    if (reverse) {
                        chainer.setS2S(label, components.get(range.getMin()), 0);
                    } else {
                        chainer.setN2N(label, components.get(range.getMin()), 0);
                    }
                }
                
                if (i == rangeComponents.size()-1) {
                    if (components.size() > 0) {
                        if (reverse) {
                            chainer.setN2N(label, components.get(components.size()-1), 0);
                        } else {
                            chainer.setS2S(label, components.get(components.size()-1), 0);
                        }
                    } else {
                        if (reverse) {
                            chainer.setN2N(label, this, SpringLayoutChainer.V_PAD);
                        } else {
                            chainer.setS2S(label, this, -SpringLayoutChainer.V_PAD);
                        }
                    }
                } else {
                    if (reverse) {
                        chainer.setN2N(label, components.get(range.getMax()), 0);
                    } else {
                        chainer.setS2S(label, components.get(range.getMax()), 0);
                    }
                }
            }
            
            label.setBorder(labelLineBorder);
        }
        
        return new Dimension(w, h);
    }
    
    private void presentLayout() {
        for (Component comp : getComponents()) {
            springLayout.removeLayoutComponent(comp);
        }
        
        super.removeAll();
        
        GuiUtils.addComponents(this, components);
        
        for (RangeComponent rangeComponent : rangeComponents) {
            add(rangeComponent.getLabel());
        }
        
        Dimension maxLabelSize = refreshRangeLabels();
        
        int preferredWidth = 0, preferredHeight = 0;
        
        boolean plural = (components.size() & 1) == 0;
        int midIndex = components.size() / 2;
        
        int padLen = 0;
        
        switch (orientation) {
            case HORIZONTAL:
                padLen = maxLabelSize.height + SpringLayoutChainer.V_PAD;
                
                if (plural) {
                    Component previous = null;
                    
                    for (int i = midIndex - 1; i >= 0; i--) {
                        Component comp = components.get(i);
                        
                        if (previous == null) {
                            if (reverse) {
                                chainer.setW2HC(comp, this, SpringLayoutChainer.H_PAD);
                            } else {
                                chainer.setE2HC(comp, this, -SpringLayoutChainer.H_PAD);
                            }
                        } else {
                            if (reverse) {
                                chainer.setW2E(comp, previous,  SpringLayoutChainer.H_PAD);
                            } else {
                                chainer.setE2W(comp, previous, -SpringLayoutChainer.H_PAD);
                            }
                        }
                        
                        chainer.setN2N(comp, this, padLen).setS2S(comp, this, -SpringLayoutChainer.V_PAD);
                        
                        previous = comp;
                        
                        preferredWidth += comp.getPreferredSize().width;
                        preferredHeight = Math.max(preferredHeight, comp.getPreferredSize().height);
                    }
                    
                    previous = null;
                    for (int i = midIndex; i < components.size(); i++) {
                        Component comp = components.get(i);
                        
                        if (previous == null) {
                            if (reverse) {
                                chainer.setE2HC(comp, this, -SpringLayoutChainer.H_PAD);
                            } else {
                                chainer.setW2HC(comp, this,  SpringLayoutChainer.H_PAD);
                            }
                        } else {
                            if (reverse) {
                                chainer.setE2W(comp, previous, -SpringLayoutChainer.H_PAD);
                            } else {
                                chainer.setW2E(comp, previous,  SpringLayoutChainer.H_PAD);
                            }
                        }
                        
                        chainer.setN2N(comp, this,  padLen).setS2S(comp, this, -SpringLayoutChainer.V_PAD);
                        
                        previous = comp;
                        
                        preferredWidth += comp.getPreferredSize().width;
                        preferredHeight = Math.max(preferredHeight, comp.getPreferredSize().height);
                    }
                } else {
                    Component centerComponent = components.get(midIndex);
                    
                    chainer.setHC2HC(centerComponent, this,  0)
                           .setN2N  (centerComponent, this,  padLen)
                           .setS2S  (centerComponent, this, -SpringLayoutChainer.V_PAD);
                    
                    Component previous = centerComponent;
                    for (int i = midIndex - 1; i >= 0; i--) {
                        Component comp = components.get(i);
                        
                        chainer.setE2W(comp, previous, -SpringLayoutChainer.H_PAD)
                               .setS2S(comp, this,     -SpringLayoutChainer.V_PAD)
                               .setN2N(comp, this,      padLen);
                        
                        previous = comp;
                        
                        preferredWidth += comp.getPreferredSize().width;
                        preferredHeight = Math.max(preferredHeight, comp.getPreferredSize().height);
                    }
                    
                    previous = centerComponent;
                    
                    for (int i = midIndex + 1; i < components.size(); i++) {
                        Component comp = components.get(i);
                        
                        chainer.setW2E(comp, previous,  SpringLayoutChainer.H_PAD)
                               .setS2S(comp, this,     -SpringLayoutChainer.V_PAD)
                               .setN2N(comp, this,      padLen);
                        
                        previous = comp;
                        
                        preferredWidth += comp.getPreferredSize().width;
                        preferredHeight = Math.max(preferredHeight, comp.getPreferredSize().height);
                    }
                    
                    preferredWidth += centerComponent.getPreferredSize().width;
                    preferredHeight = 
                        Math.max(preferredHeight, centerComponent.getPreferredSize().height) +
                        SpringLayoutChainer.V_PAD * 2;
                }
                
                preferredWidth += SpringLayoutChainer.H_PAD * (components.size() + 1);
                break;
            case VERTICAL:
                padLen = maxLabelSize.width + SpringLayoutChainer.H_PAD;
                if (plural) {
                    Component previous = null;
                    for (int i = midIndex - 1; i >= 0; i--) {
                        Component comp = components.get(i);
                        
                        if (previous == null) {
                            if (reverse) {
                                chainer.setN2VC(comp, this,  SpringLayoutChainer.V_PAD/2);
                            } else {
                                chainer.setS2VC(comp, this, -(SpringLayoutChainer.V_PAD/2));
                            }
                        } else {
                            if (reverse) {
                                chainer.setN2S(comp, previous,  SpringLayoutChainer.V_PAD);
                            } else {
                                chainer.setS2N(comp, previous, -SpringLayoutChainer.V_PAD);
                            }
                        }
                    
                        chainer.setW2W(comp, this,  padLen).setE2E(comp, this, -SpringLayoutChainer.H_PAD);
                        
                        previous = comp;
                        
                        preferredHeight += comp.getPreferredSize().height;
                        preferredWidth= Math.max(preferredWidth, comp.getPreferredSize().width);
                    }
                    
                    previous = null;
                    for (int i = midIndex; i < components.size(); i++) {
                        Component comp = components.get(i);
                        
                        if (previous == null) {
                            if (reverse) {
                                chainer.setS2VC(comp, this, -SpringLayoutChainer.V_PAD/2);
                            } else {
                                chainer.setN2VC(comp, this,  SpringLayoutChainer.V_PAD/2);
                            }
                        } else {
                            if (reverse) {
                                chainer.setS2N(comp, previous, -SpringLayoutChainer.V_PAD);
                            } else {
                                chainer.setN2S(comp, previous,  SpringLayoutChainer.V_PAD);
                            }
                        }
                        
                        chainer.setW2W(comp, this,  padLen).setE2E(comp, this, -SpringLayoutChainer.H_PAD);
                        
                        previous = comp;
                        
                        preferredHeight += comp.getPreferredSize().height;
                        preferredWidth = Math.max(preferredWidth, comp.getPreferredSize().width);
                    }
                } else {
                    Component centerComponent = components.get(midIndex);
                    
                    chainer.setVC2VC(centerComponent, this,  0)
                           .setW2W  (centerComponent, this,  padLen)
                           .setE2E  (centerComponent, this, -SpringLayoutChainer.H_PAD);
                    
                    Component previous = centerComponent;
                    for (int i = midIndex - 1; i >= 0; i--) {
                        Component comp = components.get(i);
                        
                        chainer.setS2N(comp, previous, -SpringLayoutChainer.V_PAD)
                               .setW2W(comp, this,      padLen)
                               .setE2E(comp, this,     -SpringLayoutChainer.H_PAD);
                        
                        previous = comp;
                        
                        preferredHeight += comp.getPreferredSize().height;
                        preferredWidth = Math.max(preferredWidth, comp.getPreferredSize().width);
                    }
                    
                    previous = centerComponent;
                    
                    for (int i = midIndex + 1; i < components.size(); i++) {
                        Component comp = components.get(i);
                        
                        chainer.setN2S(comp, previous,  SpringLayoutChainer.V_PAD)
                               .setE2E(comp, this,     -SpringLayoutChainer.H_PAD)
                               .setW2W(comp, this,      padLen);
                        
                        previous = comp;
                        
                        preferredHeight += comp.getPreferredSize().height;
                        preferredWidth = Math.max(preferredWidth, comp.getPreferredSize().width);
                    }
                    
                    preferredHeight += centerComponent.getPreferredSize().height;
                    preferredWidth =
                        Math.max(preferredWidth, centerComponent.getPreferredSize().width) +
                        SpringLayoutChainer.H_PAD * 2;
                }
                
                preferredHeight += SpringLayoutChainer.V_PAD * (components.size() + 1);
                break;
        }
        
        setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        setMinimumSize(new Dimension(preferredWidth, preferredHeight));
        revalidate();
        repaint();
    }
    
    public void suppressRangeSelectionChangeEvent() {
        suppressRangeSelectionChangeEvent = true;
    }
    
    public void releaseSuppressRangeSelectionChangeEvent() {
        suppressRangeSelectionChangeEvent = false;
    }
    
    @Override
    public void removeAll() {
        super.removeAll();
        
        for (Component comp : components) {
            springLayout.removeLayoutComponent(comp);
        }
        
        components.clear();
    }
    
    public int getCenterizedComponentCount() {
        return components.size();
    }
    
    public Component getCenterizedComponent(int index) {
        return components.get(index);
    }
    
    private void adjustComponentRange(int index, int adjust) {
        if (index >= 0 && index < rangeComponents.size()) {
            IntegerRange range = rangeComponents.get(index).getRange();
            
            if (range.getMin() == -1 && range.getMax() == -1) {
                range.setMax(adjust-1);
                range.setMin(0);
                
                fireRangeSelectionChangeEvent(
                        new RangeSelectionChangeEvent(
                                this, Type.RANGE_MODIFY, null, null, range.clone(), null, new IntegerRange(-1, -1)
                        )
                );
            } else {
                IntegerRange rangeBefore = range.clone();
                range.setMax(range.getMax() + adjust);
                IntegerRange rangeAfter = range.clone();
                if (adjust > 0) {
                    fireRangeSelectionChangeEvent(
                            new RangeSelectionChangeEvent(this, Type.RANGE_MODIFY, null, null, rangeAfter, null, rangeBefore)
                    );
                } else {
                    fireRangeSelectionChangeEvent(
                            new RangeSelectionChangeEvent(this, Type.RANGE_MODIFY, null, rangeAfter, null, rangeBefore, null)
                    );
                }
            }
            
            for (int i = index + 1; i < rangeComponents.size(); i++) {
                rangeComponents.get(i).shiftRange(adjust);
            }
            
            if (range.getRangeSize() == 0) {
                if (rangeComponents.size() > 1) {
                    rangeComponents.remove(index);
                } else {
                    range.setMin(-1);
                    range.setMax(-1);
                }
            }
        }
    }
    
    public void addComponents(Component ... components) {
        for (Component comp : components) {
            this.components.add(comp);
        }
        
        adjustComponentRange(rangeComponents.size() - 1, components.length);
        presentLayout();
    }
    
    public void addComponents(int index, Component ... components) {
        for (int i = components.length - 1; i >= 0; i--) {
            this.components.add(index, components[i]);
        }
        
        adjustComponentRange(findRangeIndexByComponentIndex(index), components.length);
        presentLayout();
    }
    
    public void addComponents(Collection<? extends Component> components) {
        this.components.addAll(components);
        
        adjustComponentRange(rangeComponents.size()-1, components.size());
        presentLayout();
    }
    
    public void addComponents(int index, Collection<? extends Component> components) {
        this.components.addAll(index, components);
        
        adjustComponentRange(findRangeIndexByComponentIndex(index), components.size());
        presentLayout();
    }
    
    public void addComponent(int index, Component component) {
        components.add(index, component);
        
        adjustComponentRange(findRangeIndexByComponentIndex(index), 1);
        presentLayout();
    }
    
    public void addComponent(Component component) {
        components.add(component);
        
        adjustComponentRange(rangeComponents.size()-1, 1);
        presentLayout();
    }
    
    public boolean removeComponent(Component component) {
        int foundIndex = components.indexOf(component);
        
        if (foundIndex != -1) {
            return removeComponent(foundIndex) == component;
        } else {
            return false;
        }
    }
    
    public void removeComponents(Component ... components) {
        for (Component comp : components) {
            int index = this.components.indexOf(comp);
            
            if (index != -1) {
                adjustComponentRange(findRangeIndexByComponentIndex(index), -1);
                
                this.components.remove(index);
            }
        }
        
        presentLayout();
    }
    
    public void removeComponents(Collection<? extends Component> components) {
        Iterator<? extends Component> iter = components.iterator();
        
        while (iter.hasNext()) {
            Component comp = iter.next();
            int index = this.components.indexOf(comp);
            
            if (index != -1) {
                adjustComponentRange(findRangeIndexByComponentIndex(index), -1);
                
                this.components.remove(index);
            }
        }
        
        presentLayout();
    }
    
    public Component removeComponent(int index) {
        if (index >= 0 && index < components.size()) {
            Component removed = components.remove(index);
            
            int ri = findRangeIndexByComponentIndex(index);
            
            adjustComponentRange(ri, -1);
            
            presentLayout();
            return removed;
        } else {
            return null;
        }
    }
    
    public Orientation getOrientation() {
        return orientation;
    }
    
    public boolean isReverse() {
        return reverse;
    }
    
    public void setReverse(boolean reverse) {
        if (this.reverse != reverse) {
            this.reverse = reverse;
            presentLayout();
        }
    }
    
    public void setOrientation(Orientation orientation) {
        if (orientation != null && this.orientation != orientation) {
            this.orientation = orientation;
            presentLayout();
        }
    }
    
    private void mergeRange(int fromRangeIndex, int toRangeIndex) {        
        if (
                fromRangeIndex >= 0 && fromRangeIndex < rangeComponents.size() && 
                toRangeIndex >= 0 && toRangeIndex < rangeComponents.size()
        ) {
            IntegerRange fromRange = rangeComponents.get(fromRangeIndex).getRange(), fromRangeBefore = fromRange.clone(),
                         toRange = rangeComponents.get(toRangeIndex).getRange(), toRangeBefore = toRange.clone();
            
            if (toRange.getMax() == fromRange.getMin()-1) {
                toRange.setMax(fromRange.getMax());
            } else if (toRange.getMin() == fromRange.getMax() + 1) {
                toRange.setMin(fromRange.getMin());
            }
            
            IntegerRange toRangeAfter = toRange.clone();
            
            RangeComponent removedRangeComponent = rangeComponents.remove(fromRangeIndex);
            
            fireRangeSelectionChangeEvent(
                    new RangeSelectionChangeEvent(
                            this, Type.RANGE_DELETE_MODIFY, removedRangeComponent, 
                            null, toRangeAfter, fromRangeBefore, toRangeBefore
                    )
            );
        }
    }
    
    private int findRangeIndexByComponentIndex(int index) {
        for (int i = 0; i < rangeComponents.size(); i++) {
            if (rangeComponents.get(i).getRange().contains(index)) {
                return i;
            }
        }
        return -1;
    }
    
    private int findComponentIndexByLocation(double v, int startIndex, int endIndex, boolean fromStart) {
        if (startIndex == endIndex) {
            return startIndex;
        }
        
        double lowerBound = -1, upperBound = -1, prevBound = -1;
        int si = -1, ei = -1, inc = -1;
        if (startIndex >= 0 && startIndex < components.size() && endIndex >= 0 && endIndex < components.size()) {
            if (fromStart) {
                si  = startIndex + 1;
                ei  = endIndex;
                inc = 1;
            } else {
                si  = endIndex - 1;
                ei  = startIndex;
                inc = -1;
            }
            
            Component comp = components.get((fromStart)? startIndex: endIndex);
            
            Rectangle bounds = comp.getBounds();
            
            if (orientation == Orientation.HORIZONTAL) {
                if (reverse) {
                    lowerBound = bounds.getMaxX();
                    upperBound = bounds.getMinX();
                } else {
                    lowerBound = bounds.getMinX();
                    upperBound = bounds.getMaxX();
                }
            } else {
                if (reverse) {
                    lowerBound = bounds.getMaxY();
                    upperBound = bounds.getMinY();
                } else {
                    lowerBound = bounds.getMinY();
                    upperBound = bounds.getMaxY();
                }
            }
            
            if (MathUtil.withinRange(v, lowerBound, upperBound)) {
                double midBound = (upperBound + lowerBound) / 2.0;
                
                if (
                        (fromStart && MathUtil.withinRange(v, midBound, upperBound)) ||
                        (!fromStart && MathUtil.withinRange(v, midBound, lowerBound))
                ) {
                    return si;
                } else {
                    return si - inc;
                }
            }
            
            prevBound = (fromStart)? upperBound : lowerBound;
            
            for (int i = si; i != ei + inc; i += inc) {
                Component c = components.get(i);
                
                bounds = c.getBounds();
                
                if (orientation == Orientation.HORIZONTAL) {
                    if (reverse) {
                        lowerBound = bounds.getMaxX();
                        upperBound = bounds.getMinX();
                    } else {
                        lowerBound = bounds.getMinX();
                        upperBound = bounds.getMaxX();
                    }
                } else {
                    if (reverse) {
                        lowerBound = bounds.getMaxY();
                        upperBound = bounds.getMinY();
                    } else {
                        lowerBound = bounds.getMinY();
                        upperBound = bounds.getMaxY();
                    }
                }
                
                if (
                        (fromStart && MathUtil.withinRange(v, lowerBound, prevBound)) ||
                        (!fromStart && MathUtil.withinRange(v, upperBound, prevBound))
                ) {
                    return i;
                } else if (MathUtil.withinRange(v, lowerBound, upperBound)){
                    double midBound = (upperBound + lowerBound) / 2.0;
                    
                    if (
                            (fromStart && MathUtil.withinRange(v, midBound, upperBound)) ||
                            (!fromStart && MathUtil.withinRange(v, midBound, lowerBound))
                    ) {
                        return (i == ei)? ei : i + inc;
                    } else {
                        return i;
                    }
                }
                
                prevBound = (fromStart)? upperBound : lowerBound;
            }
        }
        return -1;
    }
    
    private void modifyRange(int componentIndex, IntegerRange range, int rangeIndex, boolean modifyMin) {
        if ((modifyMin && componentIndex != range.getMin()) || (!modifyMin && componentIndex != range.getMax())) {
            IntegerRange shrinkingRangeBefore = range.clone();
            
            if (modifyMin) {
                range.setMin(componentIndex);
            } else {
                range.setMax(componentIndex);
            }
            
            IntegerRange shrinkingRangeAfter = range.clone();
            
            if ((modifyMin && rangeIndex - 1 >= 0) || (!modifyMin && rangeIndex + 1 < rangeComponents.size())) {
                IntegerRange 
                    expandingRange = (modifyMin)?
                            rangeComponents.get(rangeIndex - 1).getRange() : 
                            rangeComponents.get(rangeIndex + 1).getRange(),
                    expandingRangeBefore = expandingRange.clone();
                
                if (modifyMin) {
                    expandingRange.setMax(componentIndex-1);
                } else {
                    expandingRange.setMin(componentIndex+1);
                }
                
                IntegerRange expandingRangeAfter = expandingRange.clone();
                
                fireRangeSelectionChangeEvent(
                        new RangeSelectionChangeEvent(
                                this, Type.RANGE_MODIFY, null,
                                shrinkingRangeAfter,  expandingRangeAfter,
                                shrinkingRangeBefore, expandingRangeBefore
                        )
                );
            } else {
                if (modifyMin) {
                    RangeComponent newRangeComponent = new RangeComponent(" 1 ", 0, componentIndex-1);
                    
                    rangeComponents.add(0, newRangeComponent);
                    
                    fireRangeSelectionChangeEvent(
                            new RangeSelectionChangeEvent(
                                    this, Type.RANGE_ADD_MODIFY, newRangeComponent,
                                    shrinkingRangeAfter,  newRangeComponent.getRange().clone(),
                                    shrinkingRangeBefore, null
                            )
                    );
                } else {
                    RangeComponent newRangeComponent = new RangeComponent(" 1 ", componentIndex + 1, components.size()-1);
                    
                    IntegerRange expandingRangeAfter = newRangeComponent.getRange().clone();
                    
                    rangeComponents.add(newRangeComponent);
                    
                    fireRangeSelectionChangeEvent(
                            new RangeSelectionChangeEvent(
                                    this, Type.RANGE_ADD_MODIFY, newRangeComponent,
                                    shrinkingRangeAfter,  expandingRangeAfter,
                                    shrinkingRangeBefore, null
                            )
                    );
                }
            }
        }
    }
    
    private void refreshRanges() {
        for (int i = 0; i < rangeComponents.size(); i++) {
            JLabel label = rangeComponents.get(i).getLabel();
            
            IntegerRange range = rangeComponents.get(i).getRange();
            
            Rectangle bounds = label.getBounds();
            
            CustomLineBorder clb = (CustomLineBorder) label.getBorder();
            
            double v = -1;
            int adjust = -1;
            double min = -1, max = -1;
            
            if (
                    (clb.isNorthAdjusted() && clb.getNorthAdjust() > 0) ||
                    (clb.isWestAdjusted() && clb.getWestAdjust() > 0)
            ) {
                if (clb.isNorthAdjusted()) {
                    min = bounds.getMinY();
                    max = bounds.getMaxY();
                    
                    adjust = clb.getNorthAdjust();
                } else {
                    min = bounds.getMinX();
                    max = bounds.getMaxX();
                    
                    adjust = clb.getWestAdjust();
                }
                v = min + adjust;
                
                if (min + adjust >= max) {
                    if (reverse) {
                        if (i + 1 < rangeComponents.size()) {
                            mergeRange(i, i+1);
                            break;
                        } else if (i - 1 >= 0) {
                            mergeRange(i, i-1);
                            break;
                        }
                    } else {
                        if (i - 1 >= 0) {
                            mergeRange(i, i-1);
                            break;
                        } else if (i + 1 < rangeComponents.size()) {
                            mergeRange(i, i+1);
                            break;
                        }
                    }
                } else {
                    int componentIndex = findComponentIndexByLocation(v, range.getMin(), range.getMax(), !reverse);
                    
                    modifyRange(componentIndex, range, i, !reverse);
                }
                break;
            } else if (
                    (clb.isSouthAdjusted() && clb.getSouthAdjust() > 0) ||
                    (clb.isEastAdjusted() && clb.getEastAdjust() > 0)
            ) {
                if (clb.isSouthAdjusted()) {
                    min = bounds.getMinY();
                    max = bounds.getMaxY();
                    
                    adjust = clb.getSouthAdjust();
                } else {
                    min = bounds.getMinX();
                    max = bounds.getMaxX();
                    
                    adjust = clb.getEastAdjust();
                }
                
                v = max - adjust;
                
                if (max - adjust <= min) {
                    if (reverse) {
                        if (i - 1 >= 0) {
                            mergeRange(i, i-1);
                            break;
                        } else if (i + 1 < rangeComponents.size()) {
                            mergeRange(i, i+1);
                            break;
                        }
                    } else {
                        if (i + 1 < rangeComponents.size()) {
                            mergeRange(i, i+1);
                            break;
                        } else if (i - 1 >= 0) {
                            mergeRange(i, i-1);
                            break;
                        }
                    }
                } else {
                    int componentIndex = findComponentIndexByLocation(v, range.getMin(), range.getMax(), reverse);
                    
                    modifyRange(componentIndex, range, i, reverse);
                }
                break;
            }
        }
        
        for (int j = 0; j < rangeComponents.size(); j++) {
            rangeComponents.get(j).getLabel().setText(" " + String.valueOf(j+1) + " ");
        }
    }
    
    private RangeComponent getHittedRangeComponent(Point p) {
        return getHittedRangeComponent(p.x, p.y);
    }
    
    private RangeComponent getHittedRangeComponent(int x, int y) {
        Component component = getComponentAt(x, y);
        
        for (RangeComponent rangeComponent : rangeComponents) {
            if (component == rangeComponent.getLabel()) {
                return rangeComponent;
            }
        }
        
        return null;
    }
    
    private class MouseHandle implements MouseMotionListener, MouseListener {
        private JLabel targetLabel = null;
        
        private int selectedPart = -1;
        
        @Override
        public void mouseClicked(MouseEvent e) {
            RangeComponent hittedRangeComponent = getHittedRangeComponent(e.getPoint());
            
            if (selectedRangeComponent != hittedRangeComponent) {
                if (selectedRangeComponent != null) {
                    selectedRangeComponent.getLabel().setBackground(null);
                    selectedRangeComponent.getLabel().repaint();
                }
                
                RangeComponent oComp = selectedRangeComponent;
                
                selectedRangeComponent = hittedRangeComponent;
                
                fireRangeSelectionChangeEvent(
                        new RangeSelectionChangeEvent(RangeSelectionPanel.this, oComp, hittedRangeComponent)
                );
                
                if (selectedRangeComponent != null) {
                    selectedRangeComponent.getLabel().setBackground(Color.YELLOW);
                    selectedRangeComponent.getLabel().repaint();
                }
            }
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            // Ignored
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            if (selectedPart == -1 || targetLabel == null) { 
                return;
            }
            
            refreshRanges();
            
            presentLayout();
            
            targetLabel = null;
        }
        
        @Override
        public void mouseEntered(MouseEvent e) {
            // Ignored
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            // Ignored
        }
        
        @Override
        public void mouseDragged(MouseEvent e) {
            if (selectedPart == -1 || targetLabel == null) {
                return;
            }
            
            CustomLineBorder clb = (CustomLineBorder)targetLabel.getBorder();
            
            Rectangle bounds = targetLabel.getBounds();
            
            switch (selectedPart) {
                case 1:
                    if (orientation == Orientation.HORIZONTAL) {
                        clb.setAdjust(0, 0, (int)(e.getX() - bounds.getMinX()), 0);
                    } else {
                        clb.setAdjust((int)(e.getY() - bounds.getMinY()), 0, 0, 0);
                    }
                    break;
                case 2:
                    if (orientation == Orientation.HORIZONTAL) {
                        clb.setAdjust(0, 0, 0, (int)(bounds.getMaxX() - e.getX()));
                    } else {
                        clb.setAdjust(0, (int)(bounds.getMaxY() - e.getY()), 0, 0);
                    }
                    break;
            }
            
            targetLabel.repaint();
        }
        
        @Override
        public void mouseMoved(MouseEvent e) {
            int x = e.getX(), y = e.getY();
            
            RangeComponent rangeComponent = getHittedRangeComponent(x, y);
            
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if (rangeComponent != null) {
                targetLabel = rangeComponent.getLabel();
                
                Rectangle bounds = targetLabel.getBounds();
                if (orientation == Orientation.HORIZONTAL) {
                    double minX = bounds.getMinX(), maxX = bounds.getMaxX();
                    
                    if (x >= minX && x <= minX + 5) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                        selectedPart = 1;
                    } else if (x <= maxX && x >= maxX - 5) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
                        selectedPart = 2;
                    } else {
                        selectedPart = -1;
                        
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                } else {
                    double minY = bounds.getMinY(), maxY = bounds.getMaxY();
                    
                    if (y >= minY && y <= minY + 5) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
                        selectedPart = 1;
                    } else if (y <= maxY && y >= maxY - 5) {
                        setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                        selectedPart = 2;
                    } else {
                        selectedPart = -1;
                        
                        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    }
                }
            }
        }
    }
    
    public int getRangeCount() {
        return rangeComponents.size();
    }
    
    public RangeComponent getRangeComponent(int i) {
        if (i >= 0 && i < rangeComponents.size()) {
            return rangeComponents.get(i);
        } else {
            return null;
        }
    }
    
    @Override
    public void addRangeSelectionChangeListener(RangeSelectionChangeListener listener) {
        rangeSelectionChangeListenerProxy.addRangeSelectionChangeListener(listener);
    }
    
    @Override
    public void addRangeSelectionChangeListeners(RangeSelectionChangeListener... listeners) {
        rangeSelectionChangeListenerProxy.addRangeSelectionChangeListeners(listeners);
    }
    
    @Override
    public void removeRangeSelectionChangeListener(RangeSelectionChangeListener listener) {
        rangeSelectionChangeListenerProxy.removeRangeSelectionChangeListener(listener);
    }
    
    @Override
    public void removeRangeSelectionChangeListeners(RangeSelectionChangeListener... listeners) {
        rangeSelectionChangeListenerProxy.removeRangeSelectionChangeListeners(listeners);
    }
    
    @Override
    public void removeRangeSelectionChangeListener(int index) {
        rangeSelectionChangeListenerProxy.removeRangeSelectionChangeListener(index);
    }
    
    @Override
    public void fireRangeSelectionChangeEvent(RangeSelectionChangeEvent evt) {
        if (!suppressRangeSelectionChangeEvent) {
            rangeSelectionChangeListenerProxy.fireRangeSelectionChangeEvent(evt);
        }
    }
    
    public RangeComponent getSelectedRangeComponent() {
        return selectedRangeComponent;
    }
    
    public int getSelectedRangeComponentIndex() {
        if (selectedRangeComponent == null) {
            return -1;
        } else {
            for (int i = 0; i < rangeComponents.size(); i++) {
                if (selectedRangeComponent == rangeComponents.get(i)) {
                    return i;
                }
            }
            return -1;
        }
    }
    
    public int indexOfRangeComponent(RangeComponent rangeComponent) {
        for (int i = 0; i < rangeComponents.size(); i++) {
            if (rangeComponent == rangeComponents.get(i)) {
                return i;
            }
        }
        return -1;
    }
    
    public static class RangeComponent {
        private JLabel label;
        private IntegerRange range;
        
        public RangeComponent(String text) {
            label = new JLabel(text, SwingConstants.CENTER);
            label.setOpaque(true);
            
            range = new IntegerRange(-1, -1);
        }
        
        public RangeComponent(String text, int min, int max) {
            label = new JLabel(text, SwingConstants.CENTER);
            label.setOpaque(true);
            
            range = new IntegerRange(min, max);
        }
        
        public void setMin(int v) {
            range.setMin(v);
        }
        
        public void setMax(int v) {
            range.setMax(v);
        }
        
        public void shiftRange(int v) {
            range.shift(v);
        }
        
        public JLabel getLabel() {
            return label;
        }
        
        public IntegerRange getRange() {
            return range;
        }
    }
}
