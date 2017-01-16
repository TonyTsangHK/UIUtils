package utils.ui.component;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Tony Tsang
 * Date: 2017-01-13
 * Time: 17:38
 * 
 * Reference implementation of StackOverflow: http://stackoverflow.com/questions/10368856/jcombobox-filter-in-java-look-and-feel-independent/10371443
 */
public class FilterComboBox<O> extends JComboBox<O> {
    private List<O> items;
    
    private boolean caseSensitive = false;

    public FilterComboBox() {
        this(false, FilterFieldChangeListener.DEFAULT_DELAY);
    }
    
    public FilterComboBox(boolean caseSensitive, int delay) {
        super();
        
        if (delay <= 0) {
            // Set invalid delay to default value
            delay = FilterFieldChangeListener.DEFAULT_DELAY;
        }
        
        this.caseSensitive = caseSensitive;
        this.items = new ArrayList<>();
        
        this.setEditable(true);
        
        final JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
        
        FilterFieldChangeListener listener = new FilterFieldChangeListener(this, textfield, delay);
        
        textfield.addKeyListener(listener);
        textfield.getDocument().addDocumentListener(listener);
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    @Override
    public void addItem(O item) {
        super.addItem(item);
        items.add(item);
    }
    
    @Override
    public void removeAllItems() {
        super.removeAllItems();
        items.clear();
    }
    
    @Override
    public void removeItemAt(int index) {
        if (index >= 0 && index < items.size()) {
            super.removeItemAt(index);
            items.remove(index);
        }
    }
    
    @Override
    public void removeItem(Object item) {
        boolean removed = items.remove(item);
        
        if (removed) {
            super.removeItem(item);
        }
    }

    public void comboFilter(String enteredText) {
        if (!this.isPopupVisible()) {
            this.showPopup();
        }

        List<O> filterArray= new ArrayList<>();

        for (O item : items) {
            boolean matched;
            
            if (caseSensitive) {
                matched = item.toString().contains(enteredText);
            } else {
                matched = item.toString().toLowerCase().contains(enteredText.toLowerCase());
            }
            
            if (matched) {
                filterArray.add(item);
            }
        }
        
        if (filterArray.size() > 0) {
            DefaultComboBoxModel<O> model = (DefaultComboBoxModel<O>) this.getModel();
            
            boolean changed;
            
            if (filterArray.size() != model.getSize()) {
                changed = true;
            } else {
                if (filterArray.size() == 1) {
                    changed = !filterArray.get(0).toString().equals(model.getElementAt(0).toString());
                } else {
                    // Check first and last elements, in most cases this should be enough 
                    changed = !filterArray.get(0).toString().equals(model.getElementAt(0).toString()) &&
                        !filterArray.get(filterArray.size()-1).equals(model.getElementAt(model.getSize()-1));
                }
            }
            
            JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
            
            // Only update the selection elements when it is changed
            if (changed) {
                model.removeAllElements();

                for (O s : filterArray) {
                    model.addElement(s);
                }
                
                // Set the textfield to represent input text instead of auto selected item text
                textfield.setText(enteredText);
            }
        }
    }
    
    private static class FilterFieldChangeListener implements DocumentListener, KeyListener {
        private static final int DEFAULT_DELAY = 1000;
        
        // Delay for one seconds before filtering
        private int delay;
        
        private Timer timer;
        
        private JTextField textfield;
        private FilterComboBox filterComboBox;
        
        // Track both the key and document event trigger, only user input will trigger both of them 
        private boolean keyTriggered = false, documentTriggered = false;
        
        public FilterFieldChangeListener(FilterComboBox comboBox, JTextField textfield, int delay) {
            this.filterComboBox = comboBox;
            this.textfield = textfield;
            this.delay = delay;
            this.timer = new Timer(this.delay, e -> runTrigger(this.textfield.getText()));
        }
        
        @Override
        public void keyTyped(KeyEvent e) {
            // Ignored
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Ignored
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();

            // Skip common control keys
            if (
                keyCode != KeyEvent.VK_ESCAPE && keyCode != KeyEvent.VK_ALT &&
                keyCode != KeyEvent.VK_CONTROL && keyCode != KeyEvent.VK_SHIFT
            ) {
                keyTriggered = true;
            }
            
            // reset timer
            timer.restart();
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            documentTriggered = true;
            // reset timer
            timer.restart();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            documentTriggered = true;
            // reset timer
            timer.restart();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            // Ignored
        }
        
        private void runTrigger(String text) {
            // Only trigger comboFilter when both document and key is triggered (user input)
            if (keyTriggered && documentTriggered) {
                SwingUtilities.invokeLater(
                    () -> filterComboBox.comboFilter(text)
                );
                keyTriggered = false;
                documentTriggered = false;
            } 
        }
    } 
}
