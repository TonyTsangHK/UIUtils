package utils.ui.component;

import utils.date.DateTimeParser;
import utils.ui.GuiUtils;
import utils.ui.lang.LanguageHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatePicker extends JDialog implements DateEditorObserver, ActionListener {
    private DateField hookedField;
    private DateEditorPanel datePanel;
    private boolean hideOnSelect;
    private JPanel buttonPanel;
    private JButton clearButton;
    
    public DatePicker(DateField field) {
        this(field, LanguageHandler.variable("messages.msgChooseDate"));
    }
    
    public DatePicker(DateField field, String title) {
        this(field, title, true);
    }
    
    public DatePicker(DateField field, String title, String dateFormat) {
        this(field, title, true, dateFormat);
    }
    
    public DatePicker(DateField field, String title, boolean hideOnSelect) {
        this(field, title, hideOnSelect, DateTimeParser.NORMAL_DATE_FORMAT);
    }
    
    public DatePicker(DateField field, String title, boolean hideOnSelect, String dateFormat) {
        super(GuiUtils.getParentFrame(field), title, true);

        setModal(true);
        setHideOnSelect(hideOnSelect);
        
        hookedField = field;
        
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        
        datePanel = new DateEditorPanel(dateFormat, this);
        
        clearButton = GuiUtils.createButton(
            LanguageHandler.variable("labels.clear"), "clear", this
        );
        
        buttonPanel = new JPanel(new FlowLayout());
        
        buttonPanel.add(clearButton);
        
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(datePanel, BorderLayout.CENTER);
        
        container.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public void setSelection(String selection) {
        datePanel.setValue(selection);
    }
    
    public void setHideOnSelect(boolean hideOnSelect) {
        this.hideOnSelect = hideOnSelect;
    }
    
    public boolean isHideOnSelect() {
        return hideOnSelect;
    }
    
    public String getDateFormat() {
        return datePanel.getOutputPattern();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        if (cmd.equals("clear")) {
            dateSelected("");
        }
    }
    
    @Override
    public void dateSelected(String dateString) {
        hookedField.setText(dateString);
        if (hideOnSelect) {
            setVisible(false);
        }
    }
}