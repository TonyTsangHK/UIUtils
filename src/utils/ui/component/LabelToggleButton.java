package utils.ui.component;

import utils.ui.SelectionState;

import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class LabelToggleButton extends JLabel {
    public static final Color DEFAULT_MOUSE_OVER_COLOR = Color.yellow;
    
    private String selectedText, unselectedText;
    
    private Color oBg = null, mouseOverColor = DEFAULT_MOUSE_OVER_COLOR;
    
    private SelectionState state;
    
    private List<ActionListener> actionListeners;
    private List<ChangeListener> changeListeners;
    
    public LabelToggleButton(String text) {
        this(text, text, SelectionState.UNSELECTED);
    }
    
    public LabelToggleButton(String text, SelectionState initialState) {
        this(text, text, initialState);
    }
    
    public LabelToggleButton(String selectedText, String unselectedText) {
        this(selectedText, unselectedText, SelectionState.UNSELECTED);
    }
    
    public LabelToggleButton(String selectedText, String unselectedText, SelectionState initialState) {
        Font oFont = super.getFont();
        super.setFont(new Font("monospaced", oFont.getStyle(), oFont.getSize()));
        super.setOpaque(true);
        
        setSelectedText(selectedText);
        setUnselectedText(unselectedText);
        setState(initialState);
        
        MouseAdapter clickAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                toggleState();
                
                ActionEvent aEvt = 
                    new ActionEvent(LabelToggleButton.this, ActionEvent.ACTION_PERFORMED, getText());
                
                fireActionEvent(aEvt);
            }
            
            @Override
            public void mouseEntered(MouseEvent evt) {
                oBg = getBackground();
                setBackground(mouseOverColor);
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
                setBackground(oBg);
            }
        };
        
        addMouseListener(clickAdapter);
        
        initializeListenerList();
    }
    
    private void initializeListenerList() {
        actionListeners = new ArrayList<ActionListener>();
        changeListeners = new ArrayList<ChangeListener>();
    }
    
    public void setSelectedText(String text) {
        this.selectedText = text;
        if (state == SelectionState.SELECTED) {
            super.setText(text);
        }
    }
    
    public void setUnselectedText(String text) {
        this.unselectedText = text;
        if (state == SelectionState.UNSELECTED) {
            super.setText(text);
        }
    }
    
    public String getSelectedText() {
        return selectedText;
    }
    
    public String getUnselectedText() {
        return unselectedText;
    }
    
    private void refreshText() {
        switch (state) {
            case SELECTED:
                super.setText(selectedText);
                break;
            case UNSELECTED:
                super.setText(unselectedText);
                break;
        }
    }
    
    private void toggleState() {
        state = state.oppositeState();
        refreshText();
        fireChangeEvent(new ChangeEvent(this));
    }
    
    public void setState(SelectionState state) {
        if (this.state == null) {
            if (state == null) {
                this.state = SelectionState.SELECTED;
            } else {
                this.state = state;
            }
            refreshText();
        } else if (this.state != state) {
            toggleState();
        }
    }
    
    public SelectionState getState() {
        return state;
    }
    
    public void setMouseOverColor(Color color) {
        if (color != null) {
            this.mouseOverColor = color;
        }
    }
    
    private void fireActionEvent(ActionEvent evt) {
        if (actionListeners.size() > 0) {
            List<ActionListener> snapshot = new ArrayList<ActionListener>(actionListeners);
            
            for (ActionListener listener : snapshot) {
                listener.actionPerformed(evt);
            }
        }
    }
    
    private void fireChangeEvent(ChangeEvent evt) {
        if (changeListeners.size() > 0) {
            List<ChangeListener> snapshot = new ArrayList<ChangeListener>(changeListeners);
            
            for (ChangeListener listener : snapshot) {
                listener.stateChanged(evt);
            }
        }
    }
    
    public void addActionListener(ActionListener l) {
        if (!actionListeners.contains(l)) {
            actionListeners.add(l);
        }
    }
    
    public void removeActionListener(ActionListener l) {
        actionListeners.remove(l);
    }
    
    public void addChangeListener(ChangeListener l) {
        if (!changeListeners.contains(l)) {
            changeListeners.add(l);
        }
    }
    
    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);
    }
}
