package utils.ui.component;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

public class ClosableTabComponent extends JPanel {
    private final JTabbedPane pane;

    public ClosableTabComponent(final JTabbedPane pane) {
        super(new BorderLayout());
        if (pane == null) {
            throw new NullPointerException("TabbedPane is null");
        }
        this.pane = pane;
        setOpaque(false);

        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(ClosableTabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };

        add(label, BorderLayout.CENTER);

        JButton button = new TabButton();
        add(button, BorderLayout.EAST);
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

        addMouseListener(tabMouseListener);
    }

    private void closeTab() {
        int i = pane.indexOfTabComponent(this);
        if (i != -1) {
            pane.remove(i);
        }
    }

    private void selectTab() {
        int i = pane.indexOfTabComponent(this);
        if (i != -1) {
            pane.setSelectedIndex(i);
        }
    }

    private class TabButton extends JButton implements ActionListener {
        public TabButton() {
            int size = 17;
            setPreferredSize(new Dimension(size, size));
            setToolTipText("close this tab");
            setUI(new BasicButtonUI());
            setContentAreaFilled(false);
            setFocusable(false);
            setBorderPainted(false);
            setRolloverEnabled(true);
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            ClosableTabComponent.this.closeTab();
        }

        public void updateUI() {
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            if (getModel().isPressed()) {
                g2.translate(1, 1);
                g2.setColor(Color.GRAY);
            } else {
                if (getModel().isRollover()) {
                    g2.setColor(Color.RED);
                } else {
                    g2.setColor(Color.BLACK);
                }
            }
            g2.setStroke(new BasicStroke(2));
            int delta = 6;
            g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
            g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
            g2.dispose();
        }
    }

    private final static MouseListener tabMouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof ClosableTabComponent) {
                ClosableTabComponent c = (ClosableTabComponent) component;
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        c.selectTab();
                        break;
                    case MouseEvent.BUTTON2:
                        c.closeTab();
                        break;
                }
            }
        }
    };
}