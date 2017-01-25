package utils.ui.component;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import utils.date.*;
import utils.string.StringUtil;
import utils.ui.GuiUtils;
import utils.ui.lang.LanguageHandler;

public class DateEditorPanel extends JPanel implements ActionListener{
    /**
     * Date input panel layout
     */
    private DateCalendar calendar;
    private JButton[] dayButtons = new JButton[42];
    private JButton minusMonth, minusYear, addMonth, addYear, selectedButton;
    private String origin, outputPattern, currentSelection;
    private JLabel[] weekdayLabels = new JLabel[7];
    private JLabel yearMonthLabel;
    private ArrayList<DateEditorObserver> observers;
    private static final Font commonFont;
    private static final Insets commonInsets = new Insets(0, 2, 0, 2), 
        buttonInsets = new Insets(0, 5, 0, 5);
    private static final Color grey = new Color(238, 238, 238);
    private Color currentDateHighlightColor = DEFAULT_CURRENT_DATE_HIGHLIGHT_COLOR, selectedDateHighlightColor = DEFAULT_SELECTED_DATE_HIGHLIGHT_COLOR;

    public static final Color DEFAULT_CURRENT_DATE_HIGHLIGHT_COLOR = Color.YELLOW, DEFAULT_SELECTED_DATE_HIGHLIGHT_COLOR = Color.ORANGE;
    
    static {
        double scalingFactor = GuiUtils.determineScalingFactor();

        if (scalingFactor > 1) {
            commonFont = new Font("Monospaced", Font.BOLD, (int)(12*scalingFactor*0.95));
        } else {
            commonFont = new Font("Monospaced", Font.BOLD, 12);
        }
    }
    
    public DateEditorPanel() {
        this(DateTimeParser.NORMAL_DATE_FORMAT);
    }
    
    public DateEditorPanel(String outputPattern) {
        this("", outputPattern, null);
    }
    
    public DateEditorPanel(DateEditorObserver obs) {
        this(DateTimeParser.NORMAL_DATE_FORMAT, obs);
    }
    
    public DateEditorPanel(String outputPattern, DateEditorObserver obs) {
        this("", outputPattern, obs);
    }
    
    public DateEditorPanel(String origin, String outputPattern, DateEditorObserver obs) {
        observers = new ArrayList<DateEditorObserver>();
        if (obs != null) {
            addObserver(obs);
        }
        this.origin = origin;
        currentSelection = origin;
        calendar = new DateCalendar();
        calendar.setDateFormat(outputPattern);
        this.outputPattern = outputPattern;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JPanel dayPanel = new JPanel(new GridLayout(7, 7));
        
        addYear = new JButton(">>");
        minusYear = new JButton("<<");
        addMonth = new JButton(">");
        minusMonth = new JButton("<");
        
        yearMonthLabel = new JLabel("");
        setYearMonthLabel(calendar.getYear(), calendar.getMonth());
        
        addButton(buttonPanel, minusYear, commonFont, buttonInsets, grey, this);
        addButton(buttonPanel, minusMonth, commonFont, buttonInsets, grey, this);
        buttonPanel.add(yearMonthLabel);
        addButton(buttonPanel, addMonth, commonFont, buttonInsets, grey, this);
        addButton(buttonPanel, addYear, commonFont, buttonInsets, grey, this);
        
        weekdayLabels[0] = new JLabel(LanguageHandler.variable("daysOfWeek.sunday"), JLabel.CENTER);
        weekdayLabels[1] = new JLabel(LanguageHandler.variable("daysOfWeek.monday"), JLabel.CENTER);
        weekdayLabels[2] = new JLabel(LanguageHandler.variable("daysOfWeek.tuesday"), JLabel.CENTER);
        weekdayLabels[3] = new JLabel(LanguageHandler.variable("daysOfWeek.wednesday"), JLabel.CENTER);
        weekdayLabels[4] = new JLabel(LanguageHandler.variable("daysOfWeek.thursday"), JLabel.CENTER);
        weekdayLabels[5] = new JLabel(LanguageHandler.variable("daysOfWeek.friday"), JLabel.CENTER);
        weekdayLabels[6] = new JLabel(LanguageHandler.variable("daysOfWeek.saturday"), JLabel.CENTER);
        weekdayLabels[0].setForeground(Color.RED);
        
        int firstDay = DateCalendar.getFirstWeekDay(calendar.getYear(), calendar.getMonth());
        int availableDays = DateCalendar.getAvailableMonthdays(calendar.getYear(), calendar.getMonth());

        for (JLabel weekdayLabel : weekdayLabels) {
            weekdayLabel.setAlignmentX(JLabel.CENTER);
            dayPanel.add(weekdayLabel);
        }
        
        for (int count = 0; count < dayButtons.length; count++) {
            int d = count + 2 - firstDay;
            if (d <= availableDays && d > 0) {
                dayButtons[count] = new JButton(String.valueOf(d));
                // No need to check year & month, they should be equal to current year & month during initialization
                if (d == calendar.getDay()) {
                    // Selected date highlight always precede current date highlight
                    dayButtons[count].setBackground(selectedDateHighlightColor);
                    selectedButton = dayButtons[count];
                } else {
                    dayButtons[count].setBackground(grey);
                }
            } else {
                dayButtons[count] = new JButton("");
                dayButtons[count].setBackground(grey);
            }
            dayButtons[count].addActionListener(this);
            dayButtons[count].setFont(commonFont);
            dayButtons[count].setMargin(commonInsets);
            dayPanel.add(dayButtons[count]);
            if (count % 7 == 0) {
                dayButtons[count].setForeground(Color.RED);
            }
        }
        
        setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        add(buttonPanel, cons);
        cons.gridy = 1;
        add(dayPanel, cons);
    }
    
    public String getOutputPattern() {
        return outputPattern;
    }
    
    private void setYearMonthLabel(int y, int m) {
        yearMonthLabel.setText("   " + y + "-" + ((m<10)?("0"+m):String.valueOf(m)) + "   ");
    }
    
    public void resetDays() {
        if (calendar == null) {
            calendar = new DateCalendar();
        }
        DateCalendar currentDate = new DateCalendar();
        setYearMonthLabel(calendar.getYear(), calendar.getMonth());
        int firstDay = DateCalendar.getFirstWeekDay(calendar.getYear(), calendar.getMonth());
        int availableDays = DateCalendar.getAvailableMonthdays(calendar.getYear(), calendar.getMonth());
        
        String calendarYearMonthPrefix = calendar.getYear() + "-" + ((calendar.getMonth() < 10)? "0" + calendar.getMonth() : "" + calendar.getMonth());
        boolean isCurrentYearMonth = currentDate.getYear() == calendar.getYear() && currentDate.getMonth() == calendar.getMonth(),
            isSelectedYearMonth = currentSelection != null && currentSelection.startsWith(calendarYearMonthPrefix);
        
        for (int count = 0; count < dayButtons.length; count++) {
            int d = count + 2 - firstDay;
            if (d <= availableDays && d > 0) {
                boolean isSelectedDate = false;
                if (isSelectedYearMonth) {
                    String dateStr = calendarYearMonthPrefix + "-" + ((d < 10) ? "0" + d : "" + d);
                    isSelectedDate = dateStr.equals(currentSelection);
                }
                
                dayButtons[count].setText(String.valueOf(d));
                if (isSelectedDate) {
                    // Selected date highlight always precede current date highlight
                    dayButtons[count].setBackground(selectedDateHighlightColor);
                    
                    // set selected button in case, in case calendar's year month changed without marking the selectedButton.
                    selectedButton = dayButtons[count];
                } else if (isCurrentYearMonth && d == currentDate.getDay()) {
                    dayButtons[count].setBackground(currentDateHighlightColor);
                } else {
                    dayButtons[count].setBackground(grey);
                }
            } else {
                dayButtons[count].setText("");
                dayButtons[count].setBackground(grey);
            }
        }
    }
    
    public void addButton(JPanel panel, JButton button) {
        panel.add(button);
    }
    
    public void addButton(JPanel panel, JButton button, ActionListener actionListener) {
        if (actionListener != null) {
            button.addActionListener(actionListener);
        }
        panel.add(button);
    }
    
    public void addButton(JPanel panel, JButton button, Font font, ActionListener actionListener) {
        addButton(panel, button, font, null, null, actionListener);
    }
    
    public void addButton(JPanel panel, JButton button, Font font, Insets insets, ActionListener actionListener) {
        addButton(panel, button, font, insets, null, actionListener);
    }
    
    public void addButton(JPanel panel, JButton button, Font font, Color color, ActionListener actionListener) {
        addButton(panel, button, font, null, color, actionListener);
    }
    
    public void addButton(
        JPanel panel, JButton button, Font font, Insets insets, Color color, ActionListener actionListener
    ) {
        if (font != null) {
            button.setFont(font);
        }
        if (insets != null) {
            button.setMargin(insets);
        }
        if (color != null) {
            button.setBackground(color);
        }
        if (actionListener != null) {
            button.addActionListener(actionListener);
        }
        panel.add(button);
    }

    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if (source == addYear) {
            calendar.setYear(calendar.getYear() + 1);
            resetDays();
        } else if (source == addMonth) {
            calendar.setMonth(calendar.getMonth() + 1);
            resetDays();
        } else if (source == minusYear) {
            calendar.setYear(calendar.getYear() - 1);
            resetDays();
        } else if (source == minusMonth) {
            calendar.setMonth(calendar.getMonth() - 1);
            resetDays();
        } else {
            if (!source.getText().equals("")) {
                clearSelectedButtonHighlight();
                
                selectedButton = source;
                
                selectedButton.setBackground(selectedDateHighlightColor);
                
                calendar.setDay(Integer.parseInt(source.getText()));
                
                String selection = calendar.toString();
                if (!selection.equals(currentSelection)) {
                    currentSelection = calendar.toString();
                    notifyObservers(currentSelection);
                }
            } else {
                return;
            }
        }
    }
    
    public Color getCurrentDateHighlightColor() {
        return currentDateHighlightColor;
    }

    public void setCurrentDateHighlightColor(Color currentDateHighlightColor) {
        this.currentDateHighlightColor = currentDateHighlightColor;
    }
    
    public Color getSelectedDateHighlightColor() {
        return selectedDateHighlightColor;
    }

    public void setSelectedDateHighlightColor(Color selectedDateHighlightColor) {
        this.selectedDateHighlightColor = selectedDateHighlightColor;
    }

    public void addObserver(DateEditorObserver obs) {
        observers.add(obs);
    }
    
    public void removeObserver(DateEditorObserver obs) {
        observers.remove(obs);
    }
    
    public void clearObserver() {
        observers.clear();
    }
    
    private void notifyObservers(String currentSelection) {
        for (DateEditorObserver obs : observers) {
            obs.dateSelected(currentSelection);
        }
    }
    
    public void setYear(int year) {
        calendar.setYear(year);
    }
    
    public void setMonth(int month) {
        calendar.setMonth(month);
    }
    
    public void setDay(int day) {
        calendar.setDay(day);
    }
    
    public void setDate(int year, int month, int day) {
        calendar.setDate(year, month, day);
    }
    
    public void setDate(String dateStr) {
        calendar.setDate(dateStr);
    }
    
    public void setCurrentDate() {
        calendar.setCurrentDate();
    }
    
    public void setDate(String dateStr, String formatStr) {
        calendar.setDate(dateStr, formatStr);
    }
    
    // setValue should be used, this is reserved for internal use
    // set currentSelection without triggering observer
    protected void setSelection(String selection) {
        this.currentSelection = selection;
        this.origin = this.currentSelection;
    }
    
    private void clearSelectedButtonHighlight() {
        if (selectedButton != null) {
            String dayStr = selectedButton.getText();
            DateCalendar currentDate = new DateCalendar();

            boolean isCurrentDate = currentDate.getYear() == calendar.getYear() && currentDate.getMonth() == calendar.getMonth() &&
                String.valueOf(currentDate.getDay()).equals(dayStr);

            // If selectedButton is also current date, highlight it again
            if (isCurrentDate) {
                selectedButton.setBackground(currentDateHighlightColor);
            } else {
                selectedButton.setBackground(grey);
            }
        }
    }
    
    private void updateHighlight() {
        // Leave current date highlight to resetDays, only search for selected date button, except selectedButton is also current date
        
        if (!StringUtil.isEmptyString(currentSelection)) {
            // reset current highlighted button
            clearSelectedButtonHighlight();
            
            int selectedDay = Integer.parseInt(currentSelection.substring(currentSelection.lastIndexOf("-")+1), 10);
            
            String calendarYearMonthPrefix = calendar.getYear() + "-" + ((calendar.getMonth() < 10)? "0" + calendar.getMonth() : "" + calendar.getMonth());
            boolean isSelectedYearMonth = currentSelection != null && currentSelection.startsWith(calendarYearMonthPrefix);

            if (isSelectedYearMonth) {
                for (int i = selectedDay-1; i < dayButtons.length; i++) {
                    String dayStr = dayButtons[i].getText();
                    
                    String dateStr = calendarYearMonthPrefix + "-" + ((dayStr.length()==1)? "0" + dayStr : dayStr);
                    
                    if (dateStr.equals(currentSelection)) {
                        selectedButton = dayButtons[i];
                        dayButtons[i].setBackground(selectedDateHighlightColor);
                        break;
                    }
                }
            }
        }
    }
    
    public void setValue(String value) {
        if (!StringUtil.equals(value, currentSelection)) {
            currentSelection = DateTimeParser.format(value, outputPattern, outputPattern);
            updateHighlight();
            notifyObservers(currentSelection);
        }
        origin = currentSelection;
    }
    
    public void resetValue() {
        if (!currentSelection.equals(origin)) {
            currentSelection = origin;
            updateHighlight();
            notifyObservers(currentSelection);
        }
    }
    
    public String getValue() {
        return currentSelection;
    }
}
