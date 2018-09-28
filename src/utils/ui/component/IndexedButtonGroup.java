package utils.ui.component;

import utils.data.DataManipulator;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import java.io.Serializable;
 
public class IndexedButtonGroup extends ButtonGroup implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public boolean validateIndex(int index) {
        return index >= 0 && index < buttons.size();
    }
    
    public int getSelectedIndex() {
         ButtonModel selectedModel = getSelection();
         if (selectedModel == null) {
             return -1;
         } else {
             for (int i = 0; i < buttons.size(); i++) {
                 AbstractButton button = buttons.get(i);
                 if (button.getModel() == selectedModel) {
                     return i;
                 }
             }
             return -1;
         }
    }
    
    public void add(int index, AbstractButton b) {
        if(b == null) {
            return;
        }
        buttons.insertElementAt(b, index);
        
        if (b.isSelected()) {
            if (getSelection() == null) {
                super.setSelected(b.getModel(), true);
            } else {
                b.setSelected(false);
            }
        }

        b.getModel().setGroup(this);
    }
    
    public void swapButton(int i1, int i2) {
        if (validateIndex(i1) && validateIndex(i2)) {
            DataManipulator.swapData(buttons, i1, i2);
        }
    }
    
    public AbstractButton remove(int index) {
        if (validateIndex(index)) {
            AbstractButton button = getButton(index);
            remove(button);
            return button;
        } else {
            return null;
        }
    }
    
    public AbstractButton getButton(int index) {
        if (validateIndex(index)) {
            return buttons.get(index);
        } else {
            return null;
        }
    }
    
    public void clear() {
        buttons.clear();
    }
}
