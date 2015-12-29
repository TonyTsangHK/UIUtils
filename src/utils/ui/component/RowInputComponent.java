package utils.ui.component;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import utils.event.ValueChangeEvent;
import utils.listener.ValueChangeListener;
import utils.string.StringUtil;
import utils.ui.TableLayoutUtil;

public class RowInputComponent extends InputComponentPanel implements ValueChangeListener<String> {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<ChangeableTextField> fields;
    
    private TableLayout tableLayout;
    private JPanel componentPanel;
    
    private JLabel headerLabel;
    private JButton resetButton;
    
    private boolean isEnabled = true;
    
    public RowInputComponent(String titleText) {
        this(titleText, "重設");
    }
    
    public RowInputComponent(String titleText, String resetText) {
        headerLabel = new JLabel(titleText);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fields = new ArrayList<ChangeableTextField>();
        resetButton = new JButton(resetText);
        resetButton.addActionListener(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    reset();
                }
            }
        );
        
        presentLayout();
    }
    
    private void presentLayout() {
        setLayout(new BorderLayout());
        double[] cols = {0.99}, rows = {TableLayout.PREFERRED};
        tableLayout = new TableLayout(cols, rows);
        componentPanel = new JPanel(tableLayout);
        
        ChangeableTextField field = new ChangeableTextField(5);
        field.addValueChangeListener(this);
        fields.add(field);
        TableLayoutUtil.addComponent(componentPanel, field, 0, 1, 0, 1, TableLayout.FULL, TableLayout.CENTER);
        
        add(headerLabel, BorderLayout.NORTH);
        add(resetButton, BorderLayout.SOUTH);
        
        add(new JScrollPane(componentPanel), BorderLayout.CENTER);
    }
    
    public boolean removeField(ChangeableTextField field) {
        int index = fields.indexOf(field);
        if (index != -1) {
            if (index < fields.size() - 1 && fields.size() > 0) {
                tableLayout.deleteRow(index);
                componentPanel.remove(field);
                fields.remove(field);
                field.removeValueChangeListener(this);
                componentPanel.revalidate();
                componentPanel.repaint();
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    public boolean removeLastField() {
        if (fields.size() > 1) {
            int index = fields.size() - 1;
            ChangeableTextField field = fields.get(index);
            tableLayout.deleteRow(index);
            componentPanel.remove(field);
            fields.remove(field);
            componentPanel.revalidate();
            componentPanel.repaint();
            return true;
        } else {
            return false;
        }
    }
    
    public void addField() {
        ChangeableTextField field = new ChangeableTextField();
        field.addValueChangeListener(this);
        tableLayout.insertRow(fields.size(), TableLayout.PREFERRED);
        TableLayoutUtil.addComponent(componentPanel, field, fields.size(), 1, 0, 1, 
                TableLayout.FULL, TableLayout.CENTER);
        fields.add(field);
        componentPanel.revalidate();
        componentPanel.repaint();
    }
    
    public List<String> getTexts() {
        List<String> values = new ArrayList<String>(fields.size());
        for (int i = 0; i < fields.size(); i++) {
            JTextField field = fields.get(i);
            if (!StringUtil.isEmptyString(field.getText())) {
                values.add(field.getText());
            }
        }
        return values;
    }
    
    @Override
    public Object getValue() {
        return getTexts();
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
    
    @Override
    public void reset() {
        for (int i = fields.size() - 1; i >= 0; i--) {
            ChangeableTextField field = fields.get(i);
            if (!removeField(field)) {
                field.setText("");
            }
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (isEnabled != enabled) {
            isEnabled = enabled;
            for (ChangeableTextField fld : fields) {
                fld.setEnabled(isEnabled);
            }
        }
    }
    
    private void ensureFields(int l) {
        while (fields.size() < l+1) {
            addField();
        }
        while (fields.size() > l+1) {
            removeLastField();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setValue(Object v) {
        if (v == null) {
            reset();
        } else if (v instanceof String[]) {
            String[] values = (String[]) v;
            ensureFields(values.length);
            for (int i = 0; i < values.length; i++) {
                fields.get(i).setTextSuppressed(values[i]);
            }
        } else if (v instanceof Collection) {
            Collection<String> values = (Collection<String>) v;
            ensureFields(values.size());
            int counter = 0;
            Iterator<String> iter = values.iterator();
            while (iter.hasNext()) {
                String value = iter.next();
                fields.get(counter++).setTextSuppressed(value);
            }
        }
    }

    @Override
    public void valueChanged(ValueChangeEvent<String> evt) {
        ChangeableTextField field = (ChangeableTextField) evt.getSource();
        if (StringUtil.isEmptyString(evt.getNewValue())) {
            removeField(field);
        } else {
            if (field == fields.get(fields.size() - 1)) {
                addField();
            }
        }
    }
}
