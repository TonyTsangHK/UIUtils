package utils.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import utils.string.StringSubstitute;
import utils.string.StringUtil;

public class GuiUtils {
    public static ArrayList<BlinkingAnimationThread> actionThreads = new ArrayList<BlinkingAnimationThread>();

    public static boolean changeFont(Component component, String fontName) {
        return changeFont(component, fontName, -1, -1);
    }

    public static boolean changeFont(Component component, String fontName, int fontSize) {
        return changeFont(component, fontName, fontSize, -1);
    }

    public static boolean changeFont(Component component, String fontName, int fontSize, int fontStyle) {
        try {
            if (component == null) {
                return false;
            } else {
                Font originalFont = component.getFont();
                Font font = new Font(
                    (fontName == null || "".equals(fontName))? originalFont.getFontName() : fontName,
                    (fontStyle != -1)? fontStyle : originalFont.getStyle(),
                    (fontSize > 0)? fontSize : originalFont.getSize()
                );
                component.setFont(font);
                return true;
            }
        } catch (ClassCastException cce) {
            // Don't know why this will be thrown, it just happen randomly, doing nothing in case it occur
            return false;
        }
    }

    public static void addOrderedStringItem(JComboBox c, String item, int startIndex) {
        if (c.getItemCount() <= startIndex) {
            c.addItem(item);
        } else {
            boolean inserted = false;
            for (int i = 1; i < c.getItemCount(); i++) {
                String current = (String) c.getItemAt(i);
                int r = current.compareTo(item);
                if (r >= 0) {
                    c.insertItemAt(item, i);
                    inserted = true;
                    break;
                }
            }
            if (!inserted) {
                c.addItem(item);
            }
        }
    }
    
    public static void setComboBoxItem(JComboBox c, Object item) {
        setComboBoxItem(c, item, 0, false);
    }
    
    public static void setComboBoxItem(JComboBox c, Object item, int startIndex, boolean addWhenNotExist) {
        boolean found = false;
        int count = c.getItemCount();
        for (int i = startIndex; i < count; i++) {
            Object cItem = c.getItemAt(i);
            if (cItem.equals(item)) {
                c.setSelectedIndex(i);
                found = true;
                return;
            }
        }
        if (!found && addWhenNotExist) {
            c.addItem(item);
            c.setSelectedIndex(c.getItemCount() - 1);
        }
    }
    
    public static void addComboBoxItem(JComboBox combo, String displayText, String value) {
        StringSubstitute stringSubstitute = 
            new StringSubstitute(value, displayText, StringSubstitute.DisplayType.TEXT);
        combo.addItem(stringSubstitute);
    }
    
    public static void clearComboBoxItems(JComboBox c, int startIndex) {
        while (c.getItemCount() > startIndex) {
            c.removeItemAt(startIndex);
        }
    }
    
    public static void insertText(JTextComponent textComponent, String text) {
        insertText(textComponent, text, false);
    }
    
    public static void insertText(JTextComponent textComponent, String text, boolean focusAfterInsert) {
        if (text == null || text.length() == 0) {
            return;
        }
        
        Document doc = textComponent.getDocument();
        int s = textComponent.getSelectionStart(), e = textComponent.getSelectionEnd();
        
        try {
            if (e > s) {
                doc.remove(s, e-s);
            }
            doc.insertString(s, text, null);
            
            textComponent.setCaretPosition(s + text.length());
        } catch (BadLocationException ble) {}
        
        if (focusAfterInsert) {
            textComponent.requestFocus();
        }
    }
    
    public static void removeSelectedText(JTextComponent textComponent) {
        int s = textComponent.getSelectionStart(), e = textComponent.getSelectionEnd();
        
        if (e > s) {
            try {
                textComponent.getDocument().remove(s, e-s);
            } catch (BadLocationException ble) {
                // Impossible catch!
            }
        }
    }
    
    public static JScrollPane appendScrollPane(Component c) {
        return new JScrollPane(c);
    }
    
    public static JFrame createDefaultFrame(String title) {
        return createDefaultFrame(title, 500, 500);
    }
    
    public static JFrame createDefaultFrame(int width, int height) {
        return createDefaultFrame("", width, height);
    }
    
    public static JFrame createDefaultFrame(String title, int width, int height) {
        return createFrame(title, width, height, WindowConstants.EXIT_ON_CLOSE);
    }
    
    public static JFrame createFrame(String title, int width, int height, int defaultCloseOperation) {
        JFrame frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(defaultCloseOperation);
        return frame;
    }
    
    public static JPanel createJPanel() {
        return new JPanel();
    }
    
    public static JPanel createJPanel(LayoutManager2 lm) {
        if (lm == null) {
            return new JPanel();
        } else {
            return new JPanel(lm);
        }
    }
    
    public static JMenu createMenu(String text, char mnemonic) {
        JMenu menu = new JMenu(text);
        menu.setMnemonic(mnemonic);
        return menu;
    }
    
    public static JMenuItem createMenuItem(String text, char mnemonic) {
        JMenuItem item = new JMenuItem(text);
        item.setMnemonic(mnemonic);
        return item;
    }
    
    public static JMenuItem createMenuItem(String text, char mnemonic, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        item.setMnemonic(mnemonic);
        if (listener != null) {
            item.addActionListener(listener);
        }
        return item;
    }
    
    public static JMenuItem createMenuItem(String text, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        if (listener != null) {
            item.addActionListener(listener);
        }
        return item;
    }
    
    public static JMenu createMenu(String text, char mnenomic, JMenuBar parentBar) {
        JMenu menu = new JMenu(text);
        menu.setMnemonic(mnenomic);
        if (parentBar != null) {
            parentBar.add(menu);
        }
        return menu;
    }
    
    public static JMenuItem createMenuItem(String text, char mnenomic, JMenu parentMenu, ActionListener listener) {
        JMenuItem item = new JMenuItem(text);
        item.setMnemonic(mnenomic);
        
        if (parentMenu != null) {
            parentMenu.add(item);
        }
        if (listener != null) {
            item.addActionListener(listener);
        }
        
        return item;
    }
    
    public static JCheckBoxMenuItem createCheckBoxMenuItem(
            String text, char mnenomic, JMenu parentMenu, ButtonGroup targetButtonGroup, ItemListener listener
    ) {
        JCheckBoxMenuItem item = new JCheckBoxMenuItem(text);
        item.setMnemonic(mnenomic);
        
        if (parentMenu != null) {
            parentMenu.add(item);
        }
        if (targetButtonGroup != null) {
            targetButtonGroup.add(item);
        }
        if (listener != null) {
            item.addItemListener(listener);
        }
        
        return item;
    }
    
    public static ButtonGroup createCheckBoxMenuItemGroup(JMenu parentMenu, ItemListener listener, String ... texts) {
        ButtonGroup buttonGroup = new ButtonGroup();
        for (String text : texts) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem(text);
            
            if (listener != null) {
                item.addItemListener(listener);
            }
            
            buttonGroup.add(item);
            
            if (parentMenu != null) {
                parentMenu.add(item);
            }
        }
        
        return buttonGroup;
    }
    
    public static Frame getParentFrame(Component comp) {
        if (comp == null) {
            return null;
        }
        Container c = comp.getParent();
        while (c != null) {
            if (c instanceof Frame) {
                return (Frame) c;
            }
            c = c.getParent();
        }
        return null;
    }
    
    public static Dimension getScreenResolution() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
    
    public static JSplitPane createSplitPane(int splitOrientation, boolean continues) {
        return new JSplitPane(splitOrientation, continues);
    }
    
    public static void setGridBagConstraints(GridBagConstraints con, int gridx, int gridy) {
        if (con != null) {
            con.gridx = gridx;
            con.gridy = gridy;
        }
    }
    
    public static void setGridBagConstraints(GridBagConstraints con, int gridx, int gridy, 
            double weightx, double weighty) {
        if (con != null) {
            con.gridx = gridx;
            con.gridy = gridy;
            con.weightx = weightx;
            con.weighty = weighty;
        }
    }
    
    public static void setGridBagConstraints(GridBagConstraints con, int gridx, int gridy,
            double weightx, double weighty, int gridwidth, int gridheight) {
        if (con != null) {
            con.gridx = gridx;
            con.gridy = gridy;
            con.weightx = weightx;
            con.weighty = weighty;
            con.gridwidth = gridwidth;
            con.gridheight = gridheight;
        }
    }
    
    public static void addConstraintedComponent(Container c, Component comp, GridBagConstraints constraints) {
        c.add(comp, constraints);
    }
    
    public static void addConstraintedComponent(Container c, Component comp, int gridx, int gridy) {
        GridBagConstraints con = new GridBagConstraints();
        setGridBagConstraints(con, gridx, gridy);
        addConstraintedComponent(c, comp, con);
    }
    
    public static void addConstraintedComponent(Container c, Component comp, int gridx, int gridy,
            double weightx, double weighty) {
        GridBagConstraints con = new GridBagConstraints();
        setGridBagConstraints(con, gridx, gridy, weightx, weighty);
        addConstraintedComponent(c, comp, GridBagConstraints.CENTER, GridBagConstraints.NONE, 
                gridx, gridy, 1, 1, new Insets(0,0,0,0), 0, 0, 0, 0);
    }
    
    public static void addConstraintedComponent(Container c, Component comp, int gridx, int gridy, 
            double weightx, double weighty, int gridwidth, int gridheight) {
        GridBagConstraints con = new GridBagConstraints();
        setGridBagConstraints(con, gridx, gridy, weightx, weighty, gridwidth, gridheight);
        addConstraintedComponent(c, comp, con);
    }
    
    public static void addConstraintedComponent(Container c, Component comp, int anchor, 
            int fill, int gridx, int gridy, int gridwidth, int gridheight,
            Insets insets, int ipadx, int ipady, double weightx, double weighty) {
        GridBagConstraints con = new GridBagConstraints();
        con.anchor = anchor;
        con.fill = fill;
        con.gridwidth = gridwidth;
        con.gridheight = gridheight;
        con.gridx = gridx;
        con.gridy = gridy;
        con.insets = insets;
        con.ipadx = ipadx;
        con.ipady = ipady;
        con.weightx = weightx;
        con.weighty = weighty;
        addConstraintedComponent(c, comp, con);
    }
    
    public static JLabel createEtchedLabel(String text, Dimension minSize) {
        JLabel l = createCenteredEtchLabel(text);
        l.setPreferredSize(minSize);
        return l;
    }
    
    public static JLabel createCenteredEtchLabel(String text) {
        JLabel l = createEtchedLabel(text);
        l.setHorizontalAlignment(JLabel.CENTER);
        return l;
    }
    
    public static JLabel createEtchedLabel(String text) {
        JLabel l = new JLabel(text);
        appendEtchedBorder(l);
        return l;
    }
    
    public static void appendEtchedBorder(JComponent c) {
        c.setBorder(BorderFactory.createEtchedBorder());
    }
    
    public static void appendRaisedEtchBorder(JComponent c) {
        c.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }
    
    public static void appendLoweredEtchBorder(JComponent c) {
        c.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    }
    
    public static void appendLineBorder(JComponent c) {
        c.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
    public static void appendLineBorder(JComponent c, Color cl) {
        c.setBorder(BorderFactory.createLineBorder(cl));
    }
    
    public static void appendLineBorder(JComponent c, Color cl, int thickness) {
        c.setBorder(BorderFactory.createLineBorder(cl, thickness));
    }
    
    public static JPanel createTitledPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
        return panel;
    }
    
    public static JPanel creatTitlePanel(String title, LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
        return panel;
    }
    
    public static void buildGridLikeForm(Container c, String[] titles, Component[] components, 
            int cols, double ... weights) {
        if (c == null || titles == null || components == null || cols <= 0) {
            return;
        }
        int totalColumns = cols * 2;
        double[] ws = null;
        if (weights.length == totalColumns) {
            ws = weights;
        } else {
            ws = new double[totalColumns];
            for (int i = 0; i < ws.length; i++) {
                if (i < weights.length) {
                    ws[i] = weights[i];
                } else {
                    ws[i] = 0;
                }
            }
        }
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints con = new GridBagConstraints();
        con.fill = GridBagConstraints.HORIZONTAL;
        c.setLayout(gridBagLayout);
        
        for (int i = 0; i < titles.length && i < components.length; i++) {
            int gridx = i % cols;
            int titlex = gridx * 2;
            int componentx = gridx * 2 + 1;
            int gridy = i / cols;
            JLabel titleLabel = new JLabel(titles[i]);
            setGridBagConstraints(con, titlex, gridy, ws[titlex], 0);
            addConstraintedComponent(c, titleLabel, con);
            setGridBagConstraints(con, componentx, gridy, ws[componentx], 0);
            addConstraintedComponent(c, components[i], con);
        }
    }
    
    public static void buildGridLikeForm(Container c, String[] titles, 
            Component[] components, int cols, double titleWeight, double componentWeight) {
        if (c == null || titles == null || components == null || cols <= 0 || 
                titleWeight <= 0 || componentWeight <= 0) {
            return;
        }
        double colWeight = 1.0 / cols;
        double totalWeight = titleWeight + componentWeight;
        double adjustedTitleWeight = colWeight * (titleWeight / totalWeight);
        double adjustedComponentWeight = colWeight * (componentWeight / totalWeight);
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints con = new GridBagConstraints();
        con.fill = GridBagConstraints.HORIZONTAL;
        c.setLayout(gridBagLayout);
        
        for (int i = 0; i < titles.length && i < components.length; i++) {
            int gridx = i % cols;
            int titlex = gridx * 2;
            int componentx = gridx * 2 + 1;
            int gridy = i / cols;
            JLabel titleLabel = new JLabel(titles[i]);
            setGridBagConstraints(con, titlex, gridy, adjustedTitleWeight, 0);
            addConstraintedComponent(c, titleLabel, con);
            setGridBagConstraints(con, componentx, gridy, adjustedComponentWeight, 0);
            addConstraintedComponent(c, components[i], con);
        }
    }
    
    public static JLabel createLabel(String text) {
        return new JLabel(text);
    }
    
    public static JLabel createLabel(String text, boolean opaque) {
        JLabel l = createLabel(text);
        l.setOpaque(opaque);
        return l;
    }
    
    public static JToggleButton createToggleButton(String text) {
        return new JToggleButton(text);
    }
    
    public static JToggleButton createToggleButton(String text, ActionListener listener) {
        return createToggleButton(text, null, listener);
    }
    
    public static JToggleButton createToggleButton(String text, String actionCommand, ActionListener listener) {
        JToggleButton toggleButton = new JToggleButton(text);
        if (actionCommand != null) {
            toggleButton.setActionCommand(actionCommand);
        }
        if (listener != null) {
            toggleButton.addActionListener(listener);
        }
        return toggleButton;
    }
    
    public static JButton createButton(String text) {
        return new JButton(text);
    }
    
    public static JButton createButton(String text, ActionListener l) {
        return createButton(text, null, l);
    }
    
    public static JButton createButton(String text, char mnemonic) {
        return createButton(text, mnemonic, null);
    }
    
    public static JButton createButton(String text, char mnemonic, ActionListener listener) {
        JButton button = new JButton(text);
        
        button.setMnemonic(mnemonic);
        
        if (listener != null) {
            button.addActionListener(listener);
        }
        return button;
    }
    
    public static JButton createButton(String text, String actionCommand, ActionListener listener) {
        JButton bt = createButton(text);
        if (actionCommand != null) {
            bt.setActionCommand(actionCommand);
        }
        if (listener != null) {
            bt.addActionListener(listener);
        }
        return bt;
    }
    
    public static JButton createIconButton(String path, String iconText) {
        JButton button = new JButton(iconText, new ImageIcon(path));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        return button;
    }
    
    public static JButton createIconButton(Icon icon, String iconText) {
        JButton button = new JButton(iconText, icon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        return button;
    }
    
    public static JButton createIconButton(String path, String iconText, String actionCommand, ActionListener l) {
        JButton button = createIconButton(path, iconText);
        if (actionCommand != null) {
            button.setActionCommand(actionCommand);
        }
        if (l != null) {
            button.addActionListener(l);
        }
        return button;
    }
    
    public JButton createIconButton(Icon icon, String iconText, String actionCommand, ActionListener l) {
        JButton button = createIconButton(icon, iconText);
        if (actionCommand != null) {
            button.setActionCommand(actionCommand);
        }
        if (l != null) {
            button.addActionListener(l);
        }
        return button;
    }
    
    public JButton createIconButton(String path, String iconText, ActionListener l) {
        return createIconButton(path, iconText, null, l);
    }
    
    public JButton createIconButton(Icon icon, String iconText, ActionListener l) {
        return createIconButton(icon, iconText, null, l);
    }
    
    public static JTextField createTextField(boolean withPopUp) {
        JTextField fld = new JTextField();
        if (withPopUp) {
            fld.addMouseListener(new JTextComponentPopUpHandler(fld));
        }
        return fld;
    }
    
    public static JTextField createTextField(int size, boolean withPopUp) {
        JTextField fld = new JTextField(size);
        if (withPopUp) {
            fld.addMouseListener(new JTextComponentPopUpHandler(fld));
        }
        return fld;
    }
    
    public static JTextField createTextField(int size, String initText, boolean withPopUp) {
        JTextField textField = createTextField(size, withPopUp);
        textField.setText(initText);
        return textField;
    }
    
    public static JTextField createTextField(
            int size, String initText, boolean enabled, boolean withPopUp
    ) {
        JTextField textField = createTextField(size, initText, withPopUp);
        textField.setEnabled(enabled);
        return textField;
    }
    
    public static JTextField createTextField(int size, boolean editable, boolean withPopUp) {
        JTextField textField = createTextField(size, withPopUp);
        textField.setEditable(editable);
        return textField;
    }
    
    public static void addSpringComponent(
            SpringLayout layout, 
            Component component, Component topComponent, 
            Component bottomComponent, Component leftComponent,
            Component rightComponent, Container c, int topPadding, 
            int bottomPadding, int leftPadding, int rightPadding
    ) {
        if (topComponent != null) {
            if (topComponent == c) {
                layout.putConstraint(SpringLayout.NORTH, component, topPadding,
                        SpringLayout.NORTH, topComponent);
            } else {
                layout.putConstraint(SpringLayout.NORTH, component, topPadding, 
                        SpringLayout.SOUTH, topComponent);
            }
        }
        if (bottomComponent != null) {
            if (bottomComponent == c) {
                layout.putConstraint(SpringLayout.SOUTH, component, -bottomPadding,
                        SpringLayout.SOUTH, bottomComponent);
            } else {
                layout.putConstraint(SpringLayout.SOUTH, component, -bottomPadding,
                        SpringLayout.NORTH, bottomComponent);
            }
        }
        if (leftComponent != null) {
            if (leftComponent == c) {
                layout.putConstraint(SpringLayout.WEST, component, leftPadding,
                        SpringLayout.WEST, leftComponent);
            } else {
                layout.putConstraint(SpringLayout.WEST, component, leftPadding,
                        SpringLayout.EAST, leftComponent);
            }
        }
        if (rightComponent != null) {
            if (rightComponent == c) {
                layout.putConstraint(SpringLayout.EAST, component, rightPadding,
                        SpringLayout.EAST, rightComponent);
            } else {
                layout.putConstraint(SpringLayout.EAST, component, rightPadding,
                        SpringLayout.WEST, rightComponent);
            }
        }
        c.add(component);
    }
    
    public static void addSpringComponentBottomUp(SpringLayout layout, 
            Component component, Component previousComponent, Container c,
            int topPadding, int bottomPadding, int leftPadding, int rightPadding) {
        if (previousComponent == null) {
            layout.putConstraint(SpringLayout.SOUTH, component, -bottomPadding, SpringLayout.SOUTH, c);
        } else {
            layout.putConstraint(SpringLayout.SOUTH, component, -bottomPadding, SpringLayout.NORTH, previousComponent);
        }
        layout.putConstraint(SpringLayout.EAST, component, -rightPadding, SpringLayout.EAST, c);
        layout.putConstraint(SpringLayout.WEST, component, leftPadding, SpringLayout.WEST, c);
        c.add(component);
    }
    
    public static void scrollComponentIntoView(Component comp, JComponent parent) {
        if (parent != null && comp != null) {
            parent.scrollRectToVisible(comp.getBounds());
        }
    }
    
    public static void scrollComponentIntoView(Component comp, JScrollPane scroll) {
        if (scroll != null && comp != null) {
            scroll.getViewport().scrollRectToVisible(comp.getBounds());
        }
    }
    
    public static void blinkComponent(Component comp, Color color, int blinkTimes) {
        blinkComponent(comp, color, blinkTimes, 200);
    }
    
    public static void blinkComponent(Component comp, Color color, int blinkTimes, long interval) {
        boolean newBlinkingComponent = true;
        ListIterator<BlinkingAnimationThread> iter = actionThreads.listIterator();
        while (iter.hasNext()) {
            BlinkingAnimationThread thread = iter.next();
            if (thread.isAlive()) {
                if (thread.getComponent() == comp) {
                    thread.addBlinkTimes(blinkTimes);
                    newBlinkingComponent = false;
                    break;
                }
            } else {
                iter.remove();
            }
        }
        if (newBlinkingComponent) {
            BlinkingAnimationThread blinkAni = new BlinkingAnimationThread(comp, color, blinkTimes, interval);
            blinkAni.start();
            actionThreads.add(blinkAni);
        }
    }

    public static void showFrameAtCenter(Window ref, Frame frame) {
        showFrameAtCenter(ref, frame, true);
    }

    public static void showFrameAtCenter(Window ref, Frame frame, boolean toFront) {
        GraphicsConfiguration config = (ref != null)? ref.getGraphicsConfiguration() : frame.getGraphicsConfiguration();

        Rectangle bounds = config.getBounds();

        double w = bounds.getWidth(), h = bounds.getHeight();

        Dimension sizes = frame.getSize();

        frame.setLocation(bounds.x + (int)(w-sizes.getWidth())/2, bounds.y + (int)(h-sizes.getHeight())/2);
        frame.setVisible(true);
        if (toFront) {
            frame.toFront();
        }
    }
    
    public static void showFrameAtCenter(Frame frame) {
        showFrameAtCenter(frame, true);
    }
    
    public static void showFrameAtCenter(Frame frame, boolean toFront) {
        showFrameAtCenter(null, frame, toFront);
    }

    public static void showFrameAtCenter(Window ref, Frame frame, int width, int height) {
        frame.setSize(width, height);
        showFrameAtCenter(ref, frame);
    }
    
    public static void showFrameAtCenter(Frame frame, int width, int height) {
        showFrameAtCenter(frame, true, width, height);
    }

    public static void showFrameAtCenter(Window ref, Frame frame, boolean toFront, int width, int height) {
        frame.setSize(width, height);
        showFrameAtCenter(ref, frame, toFront);
    }
    
    public static void showFrameAtCenter(Frame frame, boolean toFront, int width, int height) {
        frame.setSize(width, height);
        showFrameAtCenter(frame, toFront);
    }

    public static void showFrameAtCenter(Window ref, Frame frame, Dimension size) {
        showFrameAtCenter(ref, frame, true, size);
    }
    
    public static void showFrameAtCenter(Frame frame, Dimension size) {
        showFrameAtCenter(frame, true, size);
    }

    public static void showFrameAtCenter(Window ref, Frame frame, boolean toFront, Dimension size) {
        frame.setSize(size);
        showFrameAtCenter(ref, frame, toFront);
    }

    public static void showFrameAtCenter(Frame frame, boolean toFront, Dimension size) {
        frame.setSize(size);
        showFrameAtCenter(frame, toFront);
    }

    public static void showFrameMaximized(Window ref, Frame frame) {
        showFrameAtCenter(ref, frame, true);
    }
    
    public static void showFrameMaximized(Frame frame) {
        showFrameMaximized(frame, true);
    }

    public static void showFrameMaximized(Window ref, Frame frame, boolean toFront) {
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        showFrameAtCenter(ref, frame, toFront);
    }
    
    public static void showFrameMaximized(Frame frame, boolean toFront) {
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        showFrameAtCenter(frame, toFront);
    }

    public static void showDialogAtCenter(Dialog dialog) {
        showDialogAtCenter(dialog, true);
    }

    public static void showDialogAtCenter(Window ref, Dialog dialog) {
        showDialogAtCenter(ref, dialog, true);
    }

    public static void showDialogAtCenter(Dialog dialog, int width, int height) {
        dialog.setSize(width, height);
        showDialogAtCenter(dialog, true);
    }

    public static void showDialogAtCenter(Window ref, Dialog dialog, int width, int height) {
        dialog.setSize(width, height);
        showDialogAtCenter(ref, dialog, true);
    }
    
    public static void showDialogAtCenter(Dialog dialog, Dimension size) {
        dialog.setSize(size);
        showDialogAtCenter(dialog, true);
    }

    public static void showDialogAtCenter(Window ref, Dialog dialog, Dimension size) {
        dialog.setSize(size);
        showDialogAtCenter(ref, dialog, size);
    }

    public static void showDialogAtCenter(Dialog dialog, boolean toFront) {
        showDialogAtCenter(dialog.getOwner(), dialog, toFront);
    }

    public static void showDialogAtCenter(Window ref, Dialog dialog, boolean toFront) {
        GraphicsConfiguration config = (ref != null)? ref.getGraphicsConfiguration() : dialog.getGraphicsConfiguration();

        Rectangle bounds = config.getBounds();

        double w = bounds.getWidth(), h = bounds.getHeight();

        Dimension sizes = dialog.getSize();

        dialog.setLocation(bounds.x + (int)(w-sizes.getWidth())/2, bounds.y + (int)(h-sizes.getHeight())/2);
        dialog.setVisible(true);
        if (toFront) {
            dialog.toFront();
        }
    }
    
    public static void showDialogAtCenter(Dialog dialog, int width, int height, boolean toFront) {
        dialog.setSize(width, height);
        showDialogAtCenter(dialog, toFront);
    }

    public static void showDialogAtCenter(Window ref, Dialog dialog, int width, int height, boolean toFront) {
        dialog.setSize(width, height);
        showDialogAtCenter(ref, dialog, toFront);
    }
    
    public static void showDialogAtCenter(Dialog dialog, Dimension size, boolean toFront) {
        dialog.setSize(size);
        showDialogAtCenter(dialog, toFront);
    }

    public static void showDialogAtCenter(Window ref, Dialog dialog, Dimension size, boolean toFront) {
        dialog.setSize(size);
        showDialogAtCenter(ref, dialog, toFront);
    }
    
    public static void addComponents(Container container, Component ... components) {
        for (Component c : components) {
            container.add(c);
        }
    }
    
    public static void addComponents(Container container, Collection<? extends Component> components) {
        for (Component c : components) {
            container.add(c);
        }
    }
    
    public static void setSmallButtons(JButton ... buttons) {
        for (JButton button : buttons) {
            setSmallButton(button);
        }
    }
    
    public static JButton setSmallButton(JButton bt) {
        setSmallFont(bt);
        Insets insets = new Insets(0, 5, 0, 5);
        bt.setMargin(insets);
        return bt;
    }
    
    public static Component setSmallFont(Component c) {
        Font f = c.getFont();
        Font smallFont = new Font(f.getFontName(), f.getStyle(), 11);
        c.setFont(smallFont);
        return c;
    }
    
    public static JButton createSmallButton(String text) {
        JButton button = new JButton(text);
        setSmallButton(button);
        return button;
    }
    
    public static JDialog openSimpleInputDialog(Container container, String title, String buttonDesc,
            Component inputComponent, int defaultCloseOperation, ActionListener buttonAction) {
        Container parentContainer = container.getParent();
        Frame parentFrame = null;
        while (parentContainer != null) {
            if (container instanceof Frame) {
                parentFrame = (Frame) parentContainer;
                break;
            }
            parentContainer = parentContainer.getParent();
        }
        final JDialog dialog = new JDialog(parentFrame, true);
        dialog.setTitle(title);
        dialog.setDefaultCloseOperation(defaultCloseOperation);
        JButton button = new JButton(buttonDesc);
        Container c = dialog.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(inputComponent, BorderLayout.CENTER);
        c.add(button, BorderLayout.SOUTH);
        if (buttonAction == null) {
            button.addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            dialog.dispose();
                        }
                    }
            );
        } else {
            button.addActionListener(buttonAction);
        }
        dialog.pack();
        GuiUtils.showDialogAtCenter(dialog);
        return dialog;
    }
    
    public static JSlider createSlider(int min, int max, int value, int orientation,
            boolean paintLabels, int increment, double unit,
            boolean paintTicks, int majorSpacing, int minorSpacing, 
            boolean paintTracks) {
        JSlider slider = new JSlider(orientation, min, max, value);
        slider.setPaintLabels(paintLabels);
        if (paintLabels) {
            slider.setLabelTable(createSliderLabels(slider, increment, unit));
        }
        slider.setPaintTicks(paintTicks);
        if (paintTicks) {
            slider.setMajorTickSpacing(majorSpacing);
            slider.setMinorTickSpacing(minorSpacing);
        }
        slider.setPaintTrack(paintTracks);
        return slider;
    }
    
    public static Hashtable<Integer, JLabel> createSliderLabels(JSlider slider, int increment, double unit) {
        Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
        int min = slider.getMinimum(), max = slider.getMaximum();
        for (int v = min; v <= max; v += increment) {
            double displayValue = v * unit;
            String displayText = (displayValue % 1.0 != 0)? String.valueOf(displayValue) : String.valueOf((int)displayValue);
            labelTable.put(new Integer(v), new JLabel(displayText));
        }
        return labelTable;
    }
    
    public static ButtonGroup createButtonGroup(AbstractButton ... buttons) {
        ButtonGroup buttonGroup = new ButtonGroup();
        
        for (AbstractButton button : buttons) {
            buttonGroup.add(button);
        }
        
        return buttonGroup;
    }
    
    public static boolean changeLookAndFeel(String lookAndFeelName, Component root) {
        if (changeLookAndFeel(lookAndFeelName)) {
            SwingUtilities.updateComponentTreeUI(root);
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean changeLookAndFeel(LookAndFeel lookAndFeel, Component root) {
        if (changeLookAndFeel(lookAndFeel)) {
            SwingUtilities.updateComponentTreeUI(root);
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean changeLookAndFeel(String lookAndFeelName) {
        try {
            LookAndFeelInfo info = getLookAndFeelInfoByName(lookAndFeelName);
            if (info != null) {
                UIManager.setLookAndFeel(info.getClassName());
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean changeLookAndFeel(LookAndFeel lookAndFeel) {
        if (lookAndFeel == null) {
            return false;
        }
        try {
            UIManager.setLookAndFeel(lookAndFeel);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public static LookAndFeelInfo getLookAndFeelInfoByName(String lookAndFeelName) {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (lookAndFeelName.equals(info.getName())) {
                return info;
            }
        }
        return null;
    }
    
    public static void addButtonsToButtonGroup(ButtonGroup buttonGroup, AbstractButton ... buttons) {
        for (AbstractButton button : buttons) {
            buttonGroup.add(button);
        }
    }
    
    public static Font requestFont(int style, int size, String ... preferredFontFamilyNames) {
        for (String n : GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()) {
            if (StringUtil.stringMatchOnceIgnoreCase(n, preferredFontFamilyNames)) {
                return new Font(n, style, size);
            }
        }
        return new Font(Font.SERIF, style, size);
    }
}
