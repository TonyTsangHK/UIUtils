package utils.ui.component;

import utils.constants.Orientation;
import utils.ui.GuiUtils;
import utils.ui.SpringLayoutChainer;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("serial")
public class CenterizedPanel extends JPanel {
    private List<Component> components;
    private Orientation orientation;
    private SpringLayout springLayout;
    private SpringLayoutChainer chainer;
    
    public CenterizedPanel() {
        this(Orientation.VERTICAL);
    }
    
    public CenterizedPanel(Orientation orientation) {
        components = new ArrayList<Component>();
        
        springLayout = new SpringLayout();
        
        chainer = new SpringLayoutChainer(springLayout);
        
        setLayout(springLayout);
        
        this.orientation = (orientation != null)? orientation : Orientation.VERTICAL;
        
        presentLayout();
    }
    
    private void presentLayout() {
        removeAll();
        
        for (Component comp : components) {
            springLayout.removeLayoutComponent(comp);
        }
        
        GuiUtils.addComponents(this, components);
        
        int preferredWidth = 0, preferredHeight = 0;
        
        boolean plural = (components.size() & 1) == 0;
        int midIndex = components.size() / 2;
        
        switch (orientation) {
            case HORIZONTAL:
                if (plural) {
                    Component previous = null;
                    for (int i = midIndex - 1; i >= 0; i--) {
                        Component comp = components.get(i);
                        
                        if (previous == null) {
                            chainer.setE2HC(comp, this, -(SpringLayoutChainer.H_PAD));
                        } else {
                            chainer.setE2W(comp, previous, -SpringLayoutChainer.H_PAD);
                        }
                        
                        chainer.setN2N(comp, this,  SpringLayoutChainer.V_PAD)
                               .setS2S(comp, this, -SpringLayoutChainer.V_PAD);
                        
                        previous = comp;
                        
                        preferredWidth += comp.getPreferredSize().width;
                        preferredHeight = Math.max(preferredHeight, comp.getPreferredSize().height);
                    }
                    
                    previous = null;
                    for (int i = midIndex; i < components.size(); i++) {
                        Component comp = components.get(i);
                        
                        if (previous == null) {
                            chainer.setW2HC(comp, this, SpringLayoutChainer.H_PAD);
                        } else {
                            chainer.setW2E(comp, previous, SpringLayoutChainer.H_PAD);
                        }
                        
                        chainer.setN2N(comp, this,  SpringLayoutChainer.V_PAD)
                               .setS2S(comp, this, -SpringLayoutChainer.V_PAD);
                        
                        previous = comp;
                        
                        preferredWidth += comp.getPreferredSize().width;
                        preferredHeight = Math.max(preferredHeight, comp.getPreferredSize().height);
                    }
                } else {
                    Component centerComponent = components.get(midIndex);
                    
                    chainer.setHC2HC(centerComponent, this, 0)
                           .setN2N(centerComponent, this,  SpringLayoutChainer.V_PAD)
                           .setS2S(centerComponent, this, -SpringLayoutChainer.V_PAD);
                    
                    Component previous = centerComponent;
                    for (int i = midIndex - 1; i >= 0; i--) {
                        Component comp = components.get(i);
                        chainer.setE2W(comp, previous, -SpringLayoutChainer.H_PAD)
                               .setN2N(comp, this,      SpringLayoutChainer.V_PAD)
                               .setS2S(comp, this,     -SpringLayoutChainer.V_PAD);
                        
                        previous = comp;
                        
                        preferredWidth += comp.getPreferredSize().width;
                        preferredHeight = Math.max(preferredHeight, comp.getPreferredSize().height);
                    }
                    
                    previous = centerComponent;
                    
                    for (int i = midIndex + 1; i < components.size(); i++) {
                        Component comp = components.get(i);
                        chainer.setW2E(comp, previous,  SpringLayoutChainer.H_PAD)
                               .setN2N(comp, this,      SpringLayoutChainer.V_PAD)
                               .setS2S(comp, this,     -SpringLayoutChainer.V_PAD);
                        
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
                if (plural) {
                    Component previous = null;
                    for (int i = midIndex - 1; i >= 0; i--) {
                        Component comp = components.get(i);
                        
                        if (previous == null) {
                            chainer.setS2VC(comp, this, -(SpringLayoutChainer.V_PAD/2));
                        } else {
                            chainer.setS2N(comp, previous, -SpringLayoutChainer.V_PAD);
                        }
                        
                        chainer.setW2W(comp, this,  SpringLayoutChainer.H_PAD)
                               .setE2E(comp, this, -SpringLayoutChainer.H_PAD);
                        
                        previous = comp;
                        
                        preferredHeight += comp.getPreferredSize().height;
                        preferredWidth= Math.max(preferredWidth, comp.getPreferredSize().width);
                    }
                    
                    previous = null;
                    for (int i = midIndex; i < components.size(); i++) {
                        Component comp = components.get(i);
                        
                        if (previous == null) {
                            chainer.setN2VC(comp, this, SpringLayoutChainer.V_PAD/2);
                        } else {
                            chainer.setN2S(comp, previous, SpringLayoutChainer.V_PAD);
                        }
                        
                        chainer.setW2W(comp, this,  SpringLayoutChainer.H_PAD)
                               .setE2E(comp, this, -SpringLayoutChainer.H_PAD);
                        
                        previous = comp;
                        
                        preferredHeight += comp.getPreferredSize().height;
                        preferredWidth = Math.max(preferredWidth, comp.getPreferredSize().width);
                    }
                } else {
                    Component centerComponent = components.get(midIndex);
                    
                    chainer.setVC2VC(centerComponent, this, 0)
                           .setW2W(centerComponent, this,  SpringLayoutChainer.H_PAD)
                           .setE2E(centerComponent, this, -SpringLayoutChainer.H_PAD);
                    
                    Component previous = centerComponent;
                    for (int i = midIndex - 1; i >= 0; i--) {
                        Component comp = components.get(i);
                        chainer.setS2N(comp, previous, -SpringLayoutChainer.V_PAD)
                               .setW2W(comp, this,      SpringLayoutChainer.H_PAD)
                               .setE2E(comp, this,     -SpringLayoutChainer.H_PAD);
                        
                        previous = comp;
                        
                        preferredHeight += comp.getPreferredSize().height;
                        preferredWidth = Math.max(preferredWidth, comp.getPreferredSize().width);
                    }
                    
                    previous = centerComponent;
                    
                    for (int i = midIndex + 1; i < components.size(); i++) {
                        Component comp = components.get(i);
                        chainer.setN2S(comp, previous,  SpringLayoutChainer.V_PAD)
                               .setW2W(comp, this,      SpringLayoutChainer.H_PAD)
                               .setE2E(comp, this,     -SpringLayoutChainer.H_PAD);
                        
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
        revalidate();
        repaint();
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
    
    public void addComponents(Component ... components) {
        for (Component comp : components) {
            this.components.add(comp);
        }
        presentLayout();
    }
    
    public void addComponents(int index, Component ... components) {
        for (int i = components.length - 1; i >= 0; i--) {
            this.components.add(index, components[i]);
        }
        presentLayout();
    }
    
    public void addComponents(Collection<? extends Component> components) {
        this.components.addAll(components);
        presentLayout();
    }
    
    public void addComponents(int index, Collection<? extends Component> components) {
        this.components.addAll(index, components);
        presentLayout();
    }
    
    public void addComponent(int index, Component component) {
        components.add(index, component);
        presentLayout();
    }
    
    public void addComponent(Component component) {
        components.add(component);
        presentLayout();
    }
    
    public boolean removeComponent(Component component) {
        if (components.remove(component)) {
            presentLayout();
            return true;
        } else {
            return false;
        }
    }
    
    public void removeComponents(Component ... components) {
        for (Component comp : components) {
            this.components.remove(comp);
        }
        
        presentLayout();
    }
    
    public void removeComponents(Collection<? extends Component> components) {
        this.components.removeAll(components);
        
        presentLayout();
    }
    
    public Component removeComponent(int index) {
        if (index >= 0 && index < components.size()) {
            Component removed = components.remove(index);
            presentLayout();
            return removed;
        } else {
            return null;
        }
    }
    
    public Orientation getOrientation() {
        return orientation;
    }
    
    public void setOrientation(Orientation orientation) {
        if (orientation != null && this.orientation != orientation) {
            this.orientation = orientation;
            presentLayout();
        }
    }
}
