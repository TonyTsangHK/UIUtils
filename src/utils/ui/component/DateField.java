package utils.ui.component;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.text.Document;

import utils.date.DateTimeParser;
import utils.ui.lang.LanguageHandler;

@SuppressWarnings("serial")
public class DateField extends JTextField implements MouseListener {
    private DatePicker datePicker;
    
    public DateField() {
        this(null, null, 0);
    }
    
    public DateField(int columns) {
        this(null, null, columns);
    }
    
    public DateField(String text) {
        this(null, text, 0);
    }
    
    public DateField(String text, int columns) {
        this(null, text, columns);
    }
    
    public DateField(Document doc, String text, int columns) {
        this(doc, text, columns, DateTimeParser.NORMAL_DATE_FORMAT);
    }
    
    public DateField(Document doc, String text, int columns, String dateFormat) {
        super(doc, text, columns);
        initialize(dateFormat);
    }
    
    private void initialize(String dateFormat) {
        setEditable(false);
        addMouseListener(this);
        datePicker = new DatePicker(this, LanguageHandler.variable("messages.msgChooseDate"), true, dateFormat);
    }
    
    public String getDateFormat() {
        return datePicker.getDateFormat();
    }
    
    public void setDate(Date date) {
        if (date == null) {
            super.setText("");
        } else {
            super.setText(DateTimeParser.format(date, getDateFormat()));
        }
    }
    
    public Date getDate() {
        String text = super.getText();
        return (text.equals(""))? null : DateTimeParser.parse(getText(), getDateFormat());
    }
    
    @Override
    public String getText() {
        return super.getText();
    }
    
    @Override
    public void mouseClicked(MouseEvent evt) {
        datePicker.setSelection(getText());
        datePicker.setLocation(this.getLocationOnScreen());
        datePicker.pack();
        datePicker.setVisible(true);
        datePicker.requestFocus();
    }
    
    @Override
    public void mouseEntered(MouseEvent evt) {
        // Ignored
    }
    
    @Override
    public void mouseExited(MouseEvent evt) {
        // Ignored
    }
    
    @Override
    public void mousePressed(MouseEvent evt) {
        // Ignored
    }
    
    @Override
    public void mouseReleased(MouseEvent evt) {
        // Ignored
    }
}
