package utils.ui;

import java.awt.Component;
import java.awt.Container;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SpringLayout.Constraints;

/**
 * Chainer class to facilitate SpringLayout's constraint settings
 */
public class SpringLayoutChainer {
    public enum GridComponentOrientation {
        ROW_BASED("row"), COLUMN_BASED("column");

        public final String desc;

        private GridComponentOrientation(String desc) {
            this.desc = desc;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

    public static final int H_PAD = 5, V_PAD = 5;

    public static final String
            N = SpringLayout.NORTH, S = SpringLayout.SOUTH,
            W = SpringLayout.WEST,  E = SpringLayout.EAST,
            
            HC = SpringLayout.HORIZONTAL_CENTER,
            VC = SpringLayout.VERTICAL_CENTER,
            
            BASELINE = SpringLayout.BASELINE,
            HEIGHT   = SpringLayout.HEIGHT,
            WIDTH    = SpringLayout.WIDTH;

    private SpringLayout springLayout;

    public SpringLayoutChainer(SpringLayout springLayout) {
        this.springLayout = springLayout;
    }

    public Spring getNorthConstraint(Component component) {
        return springLayout.getConstraint(N, component);
    }

    public Spring getSouthConstraint(Component component) {
        return springLayout.getConstraint(S, component);
    }

    public Spring getWestConstraint(Component component) {
        return springLayout.getConstraint(W, component);
    }

    public Spring getEastConstraint(Component component) {
        return springLayout.getConstraint(E, component);
    }

    public Spring getHorizontalCenterConstraint(Component component) {
        return springLayout.getConstraint(HC, component);
    }

    public Spring getVerticalCenterConstraint(Component component) {
        return springLayout.getConstraint(VC, component);
    }

    public Spring getWidthConstraint(Component component) {
        return springLayout.getConstraint(WIDTH, component);
    }

    public Spring getHeightConstraint(Component component) {
        return springLayout.getConstraint(HEIGHT, component);
    }

    /**
     * Chain method for {@link javax.swing.SpringLayout#putConstraint(String, java.awt.Component, int, String, java.awt.Component)}
     *
     * @param edge1 the edge of the dependent
     * @param comp1 the component of the dependent
     * @param edge2 the edge of the anchor
     * @param comp2 the component of the anchor
     * @param pad the fixed distance between dependent and anchor
     * @return this SpringLayoutChainer
     */
    public SpringLayoutChainer setConstraint(String edge1, Component comp1, String edge2, Component comp2, int pad) {
        return setConstraint(edge1, comp1, edge2, comp2, Spring.constant(pad));
    }

    /**
     * Chain method for {@link javax.swing.SpringLayout#putConstraint(String, java.awt.Component, javax.swing.Spring, String, java.awt.Component)}
     *
     * @param edge1 the edge of the dependent
     * @param comp1 the component of the dependent
     * @param edge2 the edge of the anchor
     * @param comp2 the component of the anchor
     * @param spring the spring linking dependent and anchor
     * @return this SpringLayoutChainer
     *
     * @see javax.swing.SpringLayout#putConstraint(String, java.awt.Component, javax.swing.Spring, String, java.awt.Component)
     */
    public SpringLayoutChainer setConstraint(String edge1, Component comp1, String edge2, Component comp2, Spring spring) {
        springLayout.putConstraint(edge1, comp1, spring, edge2, comp2);
        return this;
    }

    /**
     * Chain method for linking north edge of target component (dependent) to reference component (anchor)
     *
     * @param targetComp target component (dependant - north edge)
     * @param edge edge of the reference component (anchor)
     * @param refComp reference component (anchor)
     * @param pad the fixed distance between dependent and anchor
     * @return this SpringLayoutChainer
     *
     * @see #setNorthConstraint(java.awt.Component, String, java.awt.Component, javax.swing.Spring)
     */
    public SpringLayoutChainer setNorthConstraint(Component targetComp, String edge, Component refComp, int pad) {
        return setNorthConstraint(targetComp, edge, refComp, Spring.constant(pad));
    }

    /**
     * Chain method for linking north edge of target component (dependent) to reference component (anchor)
     *
     * @param targetComp target component (dependant - north edge)
     * @param edge edge of the reference component (anchor)
     * @param refComp reference component (anchor)
     * @param spring the spring linking dependent and anchor
     * @return this SpringLayoutChainer
     */
    public SpringLayoutChainer setNorthConstraint(Component targetComp, String edge, Component refComp, Spring spring) {
        springLayout.putConstraint(N, targetComp, spring, edge, refComp);
        return this;
    }

    /**
     * Chain method for linking south edge of target component (dependant) to reference component (anchor)
     *
     * @param targetComp target component (dependant - north edge)
     * @param edge edge of the reference component (anchor)
     * @param refComp reference component (anchor)
     * @param pad the fixed distance between dependent and anchor
     * @return this SpringLayoutChainer
     */
    public SpringLayoutChainer setSouthConstraint(Component targetComp, String edge, Component refComp, int pad) {
        return setSouthConstraint(targetComp, edge, refComp,
                Spring.constant(pad));
    }

    public SpringLayoutChainer setSouthConstraint(Component targetComp, String edge, Component refComp, Spring spring) {
        springLayout.putConstraint(S, targetComp, spring, edge, refComp);
        return this;
    }

    public SpringLayoutChainer setWestConstraint(Component targetComp, String edge, Component refComp, int pad) {
        return setWestConstraint(targetComp, edge, refComp,
                Spring.constant(pad));
    }

    public SpringLayoutChainer setWestConstraint(Component targetComp, String edge, Component refComp, Spring spring) {
        springLayout.putConstraint(W, targetComp, spring, edge, refComp);
        return this;
    }

    public SpringLayoutChainer setEastConstraint(Component targetComp, String edge, Component refComp, int pad) {
        return setEastConstraint(targetComp, edge, refComp,
                Spring.constant(pad));
    }

    public SpringLayoutChainer setEastConstraint(Component targetComp, String edge, Component refComp, Spring spring) {
        springLayout.putConstraint(E, targetComp, spring, edge, refComp);
        return this;
    }

    public SpringLayoutChainer setHorizontalCenterConstraint(
            Component targetComp, String edge, Component refComp, int pad
    ) {
        return setHorizontalCenterConstraint(targetComp, edge, refComp,
                Spring.constant(pad));
    }

    public SpringLayoutChainer setHorizontalCenterConstraint(
            Component targetComp, String edge, Component refComp, Spring spring
    ) {
        springLayout.putConstraint(HC, targetComp, spring, edge, refComp);
        return this;
    }

    public SpringLayoutChainer setVerticalCenterConstraint(
            Component targetComp, String edge, Component refComp, int pad
    ) {
        return setVerticalCenterConstraint(targetComp, edge, refComp,
                Spring.constant(pad));
    }

    public SpringLayoutChainer setVerticalCenterConstraint(
            Component targetComp, String edge, Component refComp, Spring spring
    ) {
        springLayout.putConstraint(VC, targetComp, spring, edge, refComp);
        return this;
    }

    public SpringLayoutChainer setN2N(Component targetComp, Component refComp, int pad) {
        return setN2N(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setN2N(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(N, targetComp, spring, N, refComp);
        return this;
    }

    public SpringLayoutChainer setN2N(Component refComp, int pad, Component... comps) {
        return setN2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2N(Component refComp, int pad, Collection<? extends Component> comps) {
        return setN2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2N(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setN2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setN2N(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setN2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHorizontalFlow(int pad, Component ... flowComponents) {
        return setHorizontalFlow(Spring.constant(pad), flowComponents);
    }

    public SpringLayoutChainer setHorizontalFlow(Spring spring, Component ... flowComponents) {
        for (int i = 1; i < flowComponents.length; i++) {
            setW2E(flowComponents[i], flowComponents[i-1], spring);
        }

        return this;
    }

    public SpringLayoutChainer setVerticalFlow(int pad, Component ... flowComponents) {
        return setVerticalFlow(Spring.constant(pad), flowComponents);
    }

    public SpringLayoutChainer setVerticalFlow(Spring spring, Component ... flowComponents) {
        for (int i = 1; i < flowComponents.length; i++) {
            setN2S(flowComponents[i], flowComponents[i-1], spring);
        }

        return this;
    }

    public SpringLayoutChainer setN2S(Component targetComp, Component refComp, int pad) {
        return setN2S(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setN2S(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(N, targetComp, spring, S, refComp);
        return this;
    }

    public SpringLayoutChainer setN2S(Component refComp, int pad, Component... comps) {
        return setN2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2S(Component refComp, int pad, Collection<? extends Component> comps) {
        return setN2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2S(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setN2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setN2S(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setN2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setN2W(Component targetComp, Component refComp, int pad) {
        return setN2W(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setN2W(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(N, targetComp, spring, W, refComp);
        return this;
    }

    public SpringLayoutChainer setN2W(Component refComp, int pad, Component... comps) {
        return setN2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2W(Component refComp, int pad, Collection<? extends Component> comps) {
        return setN2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2W(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setN2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setN2W(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setN2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setN2E(Component targetComp, Component refComp, int pad) {
        return setN2E(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setN2E(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(N, targetComp, spring, E, refComp);
        return this;
    }

    public SpringLayoutChainer setN2E(Component refComp, int pad, Component... comps) {
        return setN2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2E(Component refComp, int pad, Collection<? extends Component> comps) {
        return setN2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2E(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setN2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setN2E(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setN2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setN2HC(Component targetComp, Component refComp, int pad) {
        return setN2HC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setN2HC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(N, targetComp, spring, HC, refComp);
        return this;
    }

    public SpringLayoutChainer setN2HC(Component refComp, int pad, Component... comps) {
        return setN2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2HC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setN2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2HC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setN2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setN2HC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setN2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setN2VC(Component targetComp, Component refComp, int pad) {
        return setN2VC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setN2VC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(N, targetComp, spring, VC, refComp);
        return this;
    }

    public SpringLayoutChainer setN2VC(Component refComp, int pad, Component... comps) {
        return setN2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2VC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setN2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setN2VC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setN2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setN2VC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setN2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2N(Component targetComp, Component refComp, int pad) {
        return setS2N(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setS2N(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(S, targetComp, spring, N, refComp);
        return this;
    }

    public SpringLayoutChainer setS2N(Component refComp, int pad, Component... comps) {
        return setS2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2N(Component refComp, int pad, Collection<? extends Component> comps) {
        return setS2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2N(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setS2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2N(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setS2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2S(Component targetComp, Component refComp, int pad) {
        return setS2S(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setS2S(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(S, targetComp, spring, S, refComp);
        return this;
    }

    public SpringLayoutChainer setS2S(Component refComp, int pad, Component... comps) {
        return setS2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2S(Component refComp, int pad, Collection<? extends Component> comps) {
        return setS2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2S(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setS2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2S(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setS2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2W(Component targetComp, Component refComp, int pad) {
        return setS2W(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setS2W(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(S, targetComp, spring, W, refComp);
        return this;
    }

    public SpringLayoutChainer setS2W(Component refComp, int pad, Component... comps) {
        return setS2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2W(Component refComp, int pad, Collection<? extends Component> comps) {
        return setS2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2W(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setS2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2W(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setS2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2E(Component targetComp, Component refComp, int pad) {
        return setS2E(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setS2E(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(S, targetComp, spring, E, refComp);
        return this;
    }

    public SpringLayoutChainer setS2E(Component refComp, int pad, Component... comps) {
        return setS2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2E(Component refComp, int pad, Collection<? extends Component> comps) {
        return setS2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2E(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setS2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2E(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setS2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2HC(Component targetComp, Component refComp, int pad) {
        return setS2HC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setS2HC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(S, targetComp, spring, HC, refComp);
        return this;
    }

    public SpringLayoutChainer setS2HC(Component refComp, int pad, Component... comps) {
        return setS2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2HC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setS2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2HC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setS2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2HC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setS2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2VC(Component targetComp, Component refComp, int pad) {
        return setS2VC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setS2VC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(S, targetComp, spring, VC, refComp);
        return this;
    }

    public SpringLayoutChainer setS2VC(Component refComp, int pad, Component... comps) {
        return setS2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2VC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setS2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setS2VC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setS2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setS2VC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setS2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2N(Component targetComp, Component refComp, int pad) {
        return setW2N(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setW2N(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(W, targetComp, spring, N, refComp);
        return this;
    }

    public SpringLayoutChainer setW2N(Component refComp, int pad, Component... comps) {
        return setW2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2N(Component refComp, int pad, Collection<? extends Component> comps) {
        return setW2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2N(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setW2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2N(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setW2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2S(Component targetComp, Component refComp, int pad) {
        return setW2S(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setW2S(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(W, targetComp, spring, S, refComp);
        return this;
    }

    public SpringLayoutChainer setW2S(Component refComp, int pad, Component... comps) {
        return setW2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2S(Component refComp, int pad, Collection<Component> comps) {
        return setW2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2S(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setW2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2S(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setW2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2W(Component targetComp, Component refComp, int pad) {
        return setW2W(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setW2W(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(W, targetComp, spring, W, refComp);
        return this;
    }

    public SpringLayoutChainer setW2W(Component refComp, int pad, Component... comps) {
        return setW2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2W(Component refComp, int pad, Collection<? extends Component> comps) {
        return setW2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2W(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setW2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2W(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setW2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2E(Component targetComp, Component refComp, int pad) {
        return setW2E(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setW2E(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(W, targetComp, spring, E, refComp);
        return this;
    }

    public SpringLayoutChainer setW2E(Component refComp, int pad, Component... comps) {
        return setW2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2E(Component refComp, int pad, Collection<? extends Component> comps) {
        return setW2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2E(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setW2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2E(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setW2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2HC(Component targetComp, Component refComp, int pad) {
        return setW2HC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setW2HC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(W, targetComp, spring, HC, refComp);
        return this;
    }

    public SpringLayoutChainer setW2HC(Component refComp, int pad, Component... comps) {
        return setW2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2HC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setW2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2HC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setW2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2HC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setW2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2VC(Component targetComp, Component refComp, int pad) {
        return setW2VC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setW2VC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(W, targetComp, spring, VC, refComp);
        return this;
    }

    public SpringLayoutChainer setW2VC(Component refComp, int pad, Component... comps) {
        return setW2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2VC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setW2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setW2VC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setW2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setW2VC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setW2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2N(Component targetComp, Component refComp, int pad) {
        return setE2N(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setE2N(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(E, targetComp, spring, N, refComp);
        return this;
    }

    public SpringLayoutChainer setE2N(Component refComp, int pad, Component... comps) {
        return setE2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2N(Component refComp, int pad, Collection<? extends Component> comps) {
        return setE2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2N(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setE2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2N(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setE2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2S(Component targetComp, Component refComp, int pad) {
        return setE2S(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setE2S(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(E, targetComp, spring, S, refComp);
        return this;
    }

    public SpringLayoutChainer setE2S(Component refComp, int pad, Component... comps) {
        return setE2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2S(Component refComp, int pad, Collection<? extends Component> comps) {
        return setE2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2S(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setE2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2S(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setE2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2W(Component targetComp, Component refComp, int pad) {
        return setE2W(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setE2W(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(E, targetComp, spring, W, refComp);
        return this;
    }

    public SpringLayoutChainer setE2W(Component refComp, int pad, Component... comps) {
        return setE2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2W(Component refComp, int pad, Collection<Component> comps) {
        return setE2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2W(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setE2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2W(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setE2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2E(Component targetComp, Component refComp, int pad) {
        return setE2E(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setE2E(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(E, targetComp, spring, E, refComp);
        return this;
    }

    public SpringLayoutChainer setE2E(Component refComp, int pad, Component... comps) {
        return setE2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2E(Component refComp, int pad, Collection<? extends Component> comps) {
        return setE2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2E(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setE2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2E(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setE2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2HC(Component targetComp, Component refComp, int pad) {
        return setE2HC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setE2HC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(E, targetComp, spring, HC, refComp);
        return this;
    }

    public SpringLayoutChainer setE2HC(Component refComp, int pad, Component... comps) {
        return setE2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2HC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setE2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2HC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setE2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2HC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setE2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2VC(Component targetComp, Component refComp, int pad) {
        return setE2VC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setE2VC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(E, targetComp, spring, VC, refComp);
        return this;
    }

    public SpringLayoutChainer setE2VC(Component refComp, int pad, Component... comps) {
        return setE2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2VC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setE2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setE2VC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setE2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setE2VC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setE2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2N(Component targetComp, Component refComp, int pad) {
        return setHC2N(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setHC2N(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(HC, targetComp, spring, N, refComp);
        return this;
    }

    public SpringLayoutChainer setHC2N(Component refComp, int pad, Component... comps) {
        return setHC2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2N(Component refComp, int pad, Collection<? extends Component> comps) {
        return setHC2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2N(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setHC2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2N(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setHC2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2S(Component targetComp, Component refComp, int pad) {
        return setHC2S(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setHC2S(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(HC, targetComp, spring, S, refComp);
        return this;
    }

    public SpringLayoutChainer setHC2S(Component refComp, int pad, Component... comps) {
        return setHC2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2S(Component refComp, int pad, Collection<? extends Component> comps) {
        return setHC2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2S(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setHC2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2S(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setHC2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2W(Component targetComp, Component refComp, int pad) {
        return setHC2W(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setHC2W(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(HC, targetComp, spring, W, refComp);
        return this;
    }

    public SpringLayoutChainer setHC2W(Component refComp, int pad, Component... comps) {
        return setHC2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2W(Component refComp, int pad, Collection<? extends Component> comps) {
        return setHC2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2W(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setHC2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2W(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setHC2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2E(Component targetComp, Component refComp, int pad) {
        return setHC2E(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setHC2E(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(HC, targetComp, spring, E, refComp);
        return this;
    }

    public SpringLayoutChainer setHC2E(Component refComp, int pad, Component... comps) {
        return setHC2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2E(Component refComp, int pad, Collection<? extends Component> comps) {
        return setHC2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2E(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setHC2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2E(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setHC2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2HC(Component targetComp, Component refComp, int pad) {
        return setHC2HC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setHC2HC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(HC, targetComp, spring, HC, refComp);
        return this;
    }

    public SpringLayoutChainer setHC2HC(Component refComp, int pad, Component... comps) {
        return setHC2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2HC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setHC2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2HC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setHC2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2HC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setHC2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2VC(Component targetComp, Component refComp, int pad) {
        return setHC2VC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setHC2VC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(HC, targetComp, spring, VC, refComp);
        return this;
    }

    public SpringLayoutChainer setHC2VC(Component refComp, int pad, Component... comps) {
        return setHC2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2VC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setHC2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHC2VC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setHC2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHC2VC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setHC2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2N(Component targetComp, Component refComp, int pad) {
        return setVC2N(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setVC2N(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(VC, targetComp, spring, N, refComp);
        return this;
    }

    public SpringLayoutChainer setVC2N(Component refComp, int pad, Component... comps) {
        return setVC2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2N(Component refComp, int pad, Collection<? extends Component> comps) {
        return setVC2N(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2N(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setVC2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2N(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setVC2N(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2S(Component targetComp, Component refComp, int pad) {
        return setVC2S(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setVC2S(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(VC, targetComp, spring, S, refComp);
        return this;
    }

    public SpringLayoutChainer setVC2S(Component refComp, int pad, Component... comps) {
        return setVC2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2S(Component refComp, int pad, Collection<? extends Component> comps) {
        return setVC2S(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2S(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setVC2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2S(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setVC2S(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2W(Component targetComp, Component refComp, int pad) {
        return setVC2W(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setVC2W(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(VC, targetComp, spring, W, refComp);
        return this;
    }

    public SpringLayoutChainer setVC2W(Component refComp, int pad, Component... comps) {
        return setVC2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2W(Component refComp, int pad, Collection<? extends Component> comps) {
        return setVC2W(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2W(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setVC2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2W(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setVC2W(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2E(Component targetComp, Component refComp, int pad) {
        return setVC2E(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setVC2E(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(VC, targetComp, spring, E, refComp);
        return this;
    }

    public SpringLayoutChainer setVC2E(Component refComp, int pad, Component... comps) {
        return setVC2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2E(Component refComp, int pad, Collection<? extends Component> comps) {
        return setVC2E(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2E(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setVC2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2E(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setVC2E(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2HC(Component targetComp, Component refComp, int pad) {
        return setVC2HC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setVC2HC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(VC, targetComp, spring, HC, refComp);
        return this;
    }

    public SpringLayoutChainer setVC2HC(Component refComp, int pad, Component... comps) {
        return setVC2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2HC(Component refComp, int pad,
            Collection<? extends Component> comps) {
        return setVC2HC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2HC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setVC2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2HC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setVC2HC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2VC(Component targetComp, Component refComp, int pad) {
        return setVC2VC(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setVC2VC(Component targetComp, Component refComp, Spring spring) {
        springLayout.putConstraint(VC, targetComp, spring, VC, refComp);
        return this;
    }

    public SpringLayoutChainer setVC2VC(Component refComp, int pad, Component... comps) {
        return setVC2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2VC(Component refComp, int pad, Collection<? extends Component> comps) {
        return setVC2VC(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVC2VC(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setVC2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVC2VC(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setVC2VC(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVerticalBound(Component targetComp, Component refComp, int pad) {
        return setVerticalBound(targetComp, refComp, Spring.constant(pad));
    }

    public SpringLayoutChainer setVerticalBound(Component targetComp, Component refComp, Spring spring) {
        return setN2N(targetComp, refComp, spring).setS2S(targetComp, refComp, Spring.scale(spring, -1));
    }
    
    public SpringLayoutChainer setVerticalBound(Component refComp, int pad, Component... comps) {
        return setVerticalBound(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVerticalBound(Component refComp, int pad, Collection<? extends Component> comps) {
        return setVerticalBound(refComp, Spring.constant(pad), comps);
    }
    
    public SpringLayoutChainer setVerticalBound(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setVerticalBound(comp, refComp, spring);
        }
        return this;
    }
    
    public SpringLayoutChainer setVerticalBound(Component refComp, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setVerticalBound(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHorizontalBound(Component targetComp, Component refComp, int pad) {
        return setE2E(targetComp, refComp, pad).setW2W(targetComp, refComp, -pad);
    }

    public SpringLayoutChainer setHorizontalBound(Component targetComp, Component refComp, Spring spring) {
        return setE2E(targetComp, refComp, spring).setW2W(targetComp, refComp, Spring.scale(spring, -1));
    }

    public SpringLayoutChainer setHorizontalBound(Component refComp, int pad, Component... comps) {
        return setHorizontalBound(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHorizontalBound(Component refComp, int pad, Collection<? extends Component> comps) {
        return setHorizontalBound(refComp, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHorizontalBound(Component refComp, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setHorizontalBound(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHorizontalBound(
            Component refComp, Spring spring, Collection<? extends Component> comps
    ) {
        for (Component comp : comps) {
            setHorizontalBound(comp, refComp, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHorizontalSpan(Component targetComp, Container container, int pad) {
        return setW2W(targetComp, container, pad).setE2E(targetComp, container,
                -pad);
    }

    public SpringLayoutChainer setHorizontalSpan(Container container, int pad, Component... comps) {
        return setHorizontalSpan(container, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHorizontalSpan(Container container, int pad, Collection<? extends Component> comps) {
        return setHorizontalSpan(container, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHorizontalSpan(Component targetComp, Container container, Spring spring) {
        return setW2W(targetComp, container, spring).setE2E(targetComp,
                container, Spring.scale(spring, -1f));
    }

    public SpringLayoutChainer setHorizontalSpan(Container container, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setHorizontalSpan(comp, container, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHorizontalSpan(
            Container container, Spring spring, Collection<? extends Component> comps
    ) {
        for (Component comp : comps) {
            setHorizontalSpan(comp, container, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVerticalSpan(Component targetComp, Container container, int pad) {
        return setN2N(targetComp, container, pad).setS2S(targetComp, container,
                -pad);
    }

    public SpringLayoutChainer setVerticalSpan(Container container, int pad, Component... comps) {
        return setVerticalSpan(container, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVerticalSpan(Container container, int pad, Collection<? extends Component> comps) {
        return setVerticalSpan(container, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVerticalSpan(Component targetComp, Container container, Spring spring) {
        return setN2N(targetComp, container, spring).setS2S(targetComp,
                container, Spring.scale(spring, -1f));
    }

    public SpringLayoutChainer setVerticalSpan(Container container, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setVerticalSpan(comp, container, spring);
        }
        return this;
    }

    public SpringLayoutChainer setVerticalSpan(Container container, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setVerticalSpan(comp, container, spring);
        }
        return this;
    }

    public SpringLayoutChainer setWholeSpan(Component targetComp, Container container, int pad) {
        return setHorizontalSpan(targetComp, container, pad).setVerticalSpan(
                targetComp, container, pad);
    }

    public SpringLayoutChainer setWholeSpan(Container container, int pad, Component... comps) {
        return setWholeSpan(container, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setWholeSpan(Container container, int pad, Collection<? extends Component> comps) {
        return setWholeSpan(container, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setWholeSpan(Component targetComp, Container container, Spring spring) {
        return setHorizontalSpan(targetComp, container, spring)
                .setVerticalSpan(targetComp, container, spring);
    }

    public SpringLayoutChainer setWholeSpan(Container container, Spring spring, Component... comps) {
        for (Component comp : comps) {
            setWholeSpan(comp, container, spring);
        }
        return this;
    }

    public SpringLayoutChainer setWholeSpan(Container container, Spring spring, Collection<? extends Component> comps) {
        for (Component comp : comps) {
            setWholeSpan(comp, container, spring);
        }
        return this;
    }

    public SpringLayoutChainer setHorizontalChain(Container container, int pad, Component... comps) {
        return setHorizontalChain(container, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHorizontalChain(Container container, int pad, Collection<? extends Component> comps) {
        return setHorizontalChain(container, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setHorizontalChain(Container container, Spring spring, Component... comps) {
        for (int i = 0; i < comps.length; i++) {
            Component comp = comps[i];
            if (i == 0) {
                setW2W(comp, container, spring);
            } else {
                setW2E(comp, comps[i - 1], spring);
            }
        }
        return this;
    }

    public SpringLayoutChainer setHorizontalChain(
            Container container, Spring spring, Collection<? extends Component> comps
    ) {
        Component prev = null;
        for (Component comp : comps) {
            if (prev == null) {
                setW2W(comp, container, spring);
            } else {
                setW2E(comp, prev, spring);
            }
            prev = comp;
        }
        return this;
    }

    public SpringLayoutChainer setVerticalChain(Container container, int pad, Component... comps) {
        return setVerticalChain(container, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVerticalChain(Container container, int pad, Collection<? extends Component> comps) {
        return setVerticalChain(container, Spring.constant(pad), comps);
    }

    public SpringLayoutChainer setVerticalChain(Container container, Spring spring, Component... comps) {
        for (int i = 0; i < comps.length; i++) {
            Component comp = comps[i];
            if (i == 0) {
                setN2N(comp, container, spring);
            } else {
                setN2S(comp, comps[i - 1], spring);
            }
        }
        return this;
    }

    public SpringLayoutChainer setVerticalChain(
            Container container, Spring spring, Collection<? extends Component> comps
    ) {
        Component prev = null;
        for (Component comp : comps) {
            if (prev == null) {
                setN2N(comp, container, spring);
            } else {
                setN2S(comp, prev, spring);
            }
            prev = comp;
        }
        return this;
    }

    public SpringLayoutChainer buildGrid(
            Container container, Spring hPad, Spring vPad, GridComponentOrientation orientation, Component[]... comps
    ) {
        Spring currentY = vPad, currentX = hPad;

        if (orientation == GridComponentOrientation.ROW_BASED) {
            for (int i = 0; i < comps.length; i++) {
                Spring maxHeight = Spring.constant(0);
                for (int j = 0; j < comps[i].length; j++) {
                    Component comp = comps[i][j];
                    Constraints cons = springLayout.getConstraints(comp);

                    maxHeight = Spring.max(maxHeight, cons.getHeight());
                }
                for (int j = 0; j < comps[i].length; j++) {
                    Component comp = comps[i][j];
                    Constraints cons = springLayout.getConstraints(comp);

                    cons.setHeight(maxHeight);
                    cons.setY(currentY);
                }
                currentY = Spring.sum(currentY, Spring.sum(maxHeight, vPad));
            }

            int c = 0;
            while (true) {
                Spring maxWidth = Spring.constant(0);
                boolean hasComponent = false;
                for (int i = 0; i < comps.length; i++) {
                    if (c < comps[i].length) {
                        Component comp = comps[i][c];

                        Constraints cons = springLayout.getConstraints(comp);

                        maxWidth = Spring.max(maxWidth, cons.getWidth());
                        hasComponent = true;
                    }
                }

                if (!hasComponent) {
                    break;
                }

                for (int i = 0; i < comps.length; i++) {
                    if (c < comps[i].length) {
                        Component comp = comps[i][c];
                        Constraints cons = springLayout.getConstraints(comp);

                        cons.setWidth(maxWidth);
                        cons.setX(currentX);
                    }
                }
                currentX = Spring.sum(currentX, Spring.sum(maxWidth, vPad));
                c++;
            }
        } else {
            int c = 0;
            while (true) {
                Spring maxHeight = Spring.constant(0);
                boolean hasComponent = false;
                for (int i = 0; i < comps.length; i++) {
                    if (c < comps[i].length) {
                        Component comp = comps[i][c];

                        Constraints cons = springLayout.getConstraints(comp);

                        maxHeight = Spring.max(maxHeight, cons.getHeight());
                        hasComponent = true;
                    }
                }

                if (!hasComponent) {
                    break;
                }

                for (int i = 0; i < comps.length; i++) {
                    if (c < comps[i].length) {
                        Component comp = comps[i][c];
                        Constraints cons = springLayout.getConstraints(comp);

                        cons.setHeight(maxHeight);
                        cons.setY(currentY);
                    }
                }
                currentY = Spring.sum(currentY, Spring.sum(maxHeight, vPad));

                c++;
            }

            for (int i = 0; i < comps.length; i++) {
                Spring maxWidth = Spring.constant(0);
                for (int j = 0; j < comps[i].length; j++) {
                    Component comp = comps[i][j];
                    Constraints cons = springLayout.getConstraints(comp);

                    maxWidth = Spring.max(maxWidth, cons.getWidth());
                }
                for (int j = 0; j < comps[i].length; j++) {
                    Component comp = comps[i][j];
                    Constraints cons = springLayout.getConstraints(comp);

                    cons.setWidth(maxWidth);
                    cons.setX(currentX);
                }
                currentX = Spring.sum(currentX, Spring.sum(maxWidth, hPad));
            }
        }

        return this;
    }

    public SpringLayoutChainer buildGrid(Container container, int hPad, int vPad, int cols, Component... comps) {
        return buildGrid(container, Spring.constant(hPad),
                Spring.constant(vPad), cols, comps);
    }

    public SpringLayoutChainer buildGrid(
            Container container, int hPad, int vPad, GridComponentOrientation orientation, Component[]... comps
    ) {
        return buildGrid(container, Spring.constant(hPad),
                Spring.constant(vPad), orientation, comps);
    }

    public SpringLayoutChainer buildGrid(Container container, Spring hPad, Spring vPad, int cols, Component... comps) {
        int rows = (int) Math.ceil(comps.length / (double) cols);

        Spring currentY = vPad;
        for (int i = 0; i < rows; i++) {
            Spring maxHeight = Spring.constant(0);
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j;
                if (index < comps.length) {
                    Component comp = comps[index];
                    Constraints cons = springLayout.getConstraints(comp);

                    maxHeight = Spring.max(maxHeight, cons.getHeight());
                }
            }
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j;
                if (index < comps.length) {
                    Component comp = comps[index];
                    Constraints cons = springLayout.getConstraints(comp);

                    cons.setHeight(maxHeight);
                    cons.setY(currentY);
                }
            }
            currentY = Spring.sum(currentY, Spring.sum(maxHeight, vPad));
        }

        Spring currentX = hPad;
        for (int i = 0; i < cols; i++) {
            Spring maxWidth = Spring.constant(0);
            for (int j = 0; j < rows; j++) {
                int index = j * cols + i;

                if (index < comps.length) {
                    Component comp = comps[index];

                    Constraints cons = springLayout.getConstraints(comp);

                    maxWidth = Spring.max(maxWidth, cons.getWidth());
                }
            }

            for (int j = 0; j < rows; j++) {
                int index = j * cols + i;
                if (index < comps.length) {
                    Component comp = comps[index];
                    Constraints cons = springLayout.getConstraints(comp);

                    cons.setWidth(maxWidth);
                    cons.setX(currentX);
                }
            }
            currentX = Spring.sum(currentX, Spring.sum(maxWidth, hPad));
        }

        return this;
    }

    public SpringLayoutChainer buildGrid(
            Container container, int hPad, int vPad, int col, Collection<? extends Component> comps
    ) {
        return buildGrid(container, Spring.constant(hPad), Spring.constant(vPad), col, comps);
    }

    public SpringLayoutChainer buildGrid(
            Container container, Spring hPad, Spring vPad, int cols, Collection<? extends Component> comps
    ) {
        Component[] compArr = new Component[comps.size()];
        comps.toArray(compArr);
        return buildGrid(container, hPad, vPad, cols, compArr);
    }

    public SpringLayoutChainer buildGrid(
            Container container, int hPad, int vPad, GridComponentOrientation orientation,
            Collection<? extends Component>... collections
    ) {
        return buildGrid(container, Spring.constant(hPad), Spring.constant(vPad), orientation, collections);
    }

    public SpringLayoutChainer buildGrid(
            Container container, Spring hPad, Spring vPad, GridComponentOrientation orientation,
            Collection<? extends Component>... collections
    ) {
        Component[][] compArr = new Component[collections.length][];

        for (int i = 0; i < compArr.length; i++) {
            Collection<? extends Component> components = collections[i];
            compArr[i] = new Component[components.size()];
            components.toArray(compArr[i]);
        }

        return buildGrid(container, hPad, vPad, orientation, compArr);
    }

    public SpringLayoutChainer buildGrid(
            Container container, int hPad, int vPad, GridComponentOrientation orientation,
            Collection<? extends Collection<? extends Component>> comps
    ) {
        return buildGrid(container, Spring.constant(hPad),
                Spring.constant(vPad), orientation, comps);
    }

    public SpringLayoutChainer buildGrid(
            Container container, Spring hPad, Spring vPad, GridComponentOrientation orienation,
            Collection<? extends Collection<? extends Component>> comps
    ) {
        Component[][] compArr = new Component[comps.size()][];
        Iterator<? extends Collection<? extends Component>> iter = comps.iterator();
        int c = 0;
        while (iter.hasNext()) {
            Collection<? extends Component> compCol = iter.next();
            Component[] arr = new Component[compCol.size()];
            compCol.toArray(arr);
            compArr[c++] = arr;
        }

        return buildGrid(container, hPad, vPad, orienation, compArr);
    }
}
