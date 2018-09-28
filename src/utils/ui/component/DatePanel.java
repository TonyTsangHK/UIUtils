package utils.ui.component;

import utils.date.DateCalendar;
import utils.ui.lang.LanguageHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @deprecated Not used in other UI components, except DateEditDialog which is deprecated
 * 
 * Found no use in other UI components and this should not be used standalone
 * Duplicated functionality with DateEditorPanel
 */
public class DatePanel extends JPanel implements ActionListener{
    private DateCalendar calendar;
    private JButton[] dayButtons = new JButton[42];
    private JButton minusMonth, minusYear, addMonth, addYear, selectedButton;
    private JLabel[] weekdayLabels = new JLabel[7];
    private JLabel yearMonthLabel;
    private static final Font commonFont = new Font("Monospaced", Font.BOLD, 12);
    private static final Insets commonInsets = new Insets(0, 2, 0, 2), 
        buttonInsets = new Insets(0, 5, 0, 5);
    private static final Color grey = new Color(238, 238, 238);
    
    public DatePanel() {
        calendar = new DateCalendar();
        
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
        weekdayLabels[4] = new JLabel(LanguageHandler.variable("daysOfWeek.tuesday"), JLabel.CENTER);
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
                    dayButtons[count].setBackground(Color.YELLOW);
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
        for (int count = 0; count < dayButtons.length; count++) {
            int d = count + 2 - firstDay;
            if (d <= availableDays && d > 0) {
                dayButtons[count].setText(String.valueOf(d));
                if (d == currentDate.getDay() && currentDate.getYear() == calendar.getYear() && currentDate.getMonth() == calendar.getMonth()) {
                    dayButtons[count].setBackground(Color.YELLOW);
                    selectedButton = dayButtons[count];
                } else {
                    dayButtons[count].setBackground(grey);
                }
            } else {
                dayButtons[count].setText("");
                dayButtons[count].setBackground(grey);
            }
        }
    }
    
    private void addButton(
        JPanel panel, JButton button, Font font, 
        Insets insets, Color color, ActionListener actionListener
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
                if (selectedButton != null) {
                    selectedButton.setBackground(grey);
                }
                selectedButton = source;
                
                // Only highlight current date but not selected date
                //source.setBackground(Color.YELLOW);
                
                calendar.setDay(Integer.parseInt(source.getText()));
            } else {
                return;
            }
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
    
    public String getSelectedDateValue() {
        return calendar.toString();
    }
}
