package utils.ui.component;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

import utils.event.FileChoosedEvent;
import utils.event.FileChoosedListener;
import utils.event.ValueChangeEvent;
import utils.listener.ValueChangeListener;
import utils.string.StringUtil;

import java.io.File;
import java.util.List;

public class FileField extends InputComponentPanel implements ActionListener, FocusListener, 
        ValueChangeListener<String> {
    private static final long serialVersionUID = 1L;
    
    private JLabel label;
    private ChangeableTextField pathField;
    private JPanel buttonPanel;
    private JButton browseButton, clearButton;
    private JFileChooser fileChooser;
    private EventListenerList fileChoosedListeners;
    
    private File choosedFile = null;
    
    private boolean isEnabled;
    
    public FileField(String labelText) {
        this(labelText, "瀏覽", "清除");
    }
    
    public FileField(String labelText, String buttonText, String clearText) {
        label = new JLabel(labelText);
        pathField = new ChangeableTextField();
        browseButton = new JButton(buttonText);
        clearButton = new JButton(clearText);
        
        pathField.addActionListener(this);
        pathField.addFocusListener(this);
        
        browseButton.addActionListener(this);
        clearButton.addActionListener(this);
        
        fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(true);
        
        fileChoosedListeners = new EventListenerList();
        
        buttonPanel = new JPanel(new GridLayout(1, 2));
        
        setLayout(new BorderLayout());
        presentLayout();
    }
    
    public void addFileChoosedListener(FileChoosedListener l) {
        fileChoosedListeners.add(FileChoosedListener.class, l);
    }
    
    public void removeFileChoosedListener(FileChoosedListener l) {
        fileChoosedListeners.remove(FileChoosedListener.class, l);
    }
    
    private void fireFileChoosedEvent(File originalFile, File choosedFile) {
        Object[] listeners = fileChoosedListeners.getListenerList();
        for (int i = 0; i < listeners.length; i+=2) {
            if (listeners[i] == FileChoosedListener.class) {
                ((FileChoosedListener)listeners[i+1]).fileChoosed(
                        new FileChoosedEvent(this, originalFile, choosedFile)
                );
            }
        }
    }
    
    public JFileChooser getFileChooser() {
        return fileChooser;
    }
    
    public void setFileView(FileView fileView) {
        fileChooser.setFileView(fileView);
    }
    
    public void setFilters(List<FileFilter> filters) {
        FileFilter currentFileFilter = fileChooser.getFileFilter();
        fileChooser.resetChoosableFileFilters();
        for (FileFilter filter : filters) {
            fileChooser.addChoosableFileFilter(filter);
        }
        fileChooser.addChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
        fileChooser.setFileFilter(currentFileFilter);
    }
    
    public void setFilter(FileFilter filter) {
        fileChooser.setFileFilter(filter);
    }
    
    @Override
    public void focusGained(FocusEvent evt) {
        // Ignored
    }
    
    @Override
    public void focusLost(FocusEvent evt) {
        File file = new File(pathField.getText());
        if (!file.exists()) {
            pathField.setText("");
            if (choosedFile != null) {
                File oFile = choosedFile;
                choosedFile = null;
                fireFileChoosedEvent(oFile, choosedFile);
            }
        } else {
            if (choosedFile == null || !choosedFile.getAbsoluteFile().equals(file.getAbsoluteFile())) {
                File oFile = choosedFile;
                choosedFile = file;
                fireFileChoosedEvent(oFile, choosedFile);
            }
        }
    }
    
    @Override
    public void valueChanged(ValueChangeEvent<String> evt) {
        if (evt.getSource() == pathField) {
            handlePathFieldValue();
        }
    }
    
    private void handlePathFieldValue() {
        if (StringUtil.isEmptyString(pathField.getText())) {
            if (choosedFile != null) {
                File oFile = choosedFile;
                choosedFile = null;
                fireFileChoosedEvent(oFile, choosedFile);
            }
        } else {
            File file = new File(pathField.getText());
            if (!file.exists()) {
                pathField.setText("");
                if (choosedFile != null) {
                    File oFile = choosedFile;
                    choosedFile = null;
                    fireFileChoosedEvent(oFile, choosedFile);
                }
            } else {
                if (choosedFile == null || !choosedFile.getAbsoluteFile().equals(file.getAbsoluteFile())) {
                    File oFile = choosedFile;
                    choosedFile = file;
                    fireFileChoosedEvent(oFile, choosedFile);
                }
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == browseButton) {
            int opt = fileChooser.showOpenDialog(browseButton);
            if (opt == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (file.exists()) {
                    pathField.setText(file.getAbsolutePath());
                    if (choosedFile == null || !choosedFile.getAbsoluteFile().equals(file.getAbsoluteFile())) {
                        File oFile = choosedFile;
                        choosedFile = file;
                        fireFileChoosedEvent(oFile, choosedFile);
                    }
                }
            }
        } else if (source == clearButton) {
            reset();
        } else if (source == pathField) {
            handlePathFieldValue();
        }
    }
    
    private void presentLayout() {
        add(label, BorderLayout.WEST);
        add(pathField, BorderLayout.CENTER);
        
        buttonPanel.add(browseButton);
        buttonPanel.add(clearButton);
        
        add(buttonPanel, BorderLayout.EAST);
    }
    
    public File getChoosedFile() {
        return choosedFile;
    }
    
    @Override
    public Object getValue() {
        return getChoosedFile();
    }
    
    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
    
    @Override
    public void reset() {
        pathField.setText("");
        if (choosedFile != null) {
            File oFile = choosedFile;
            choosedFile = null;
            fireFileChoosedEvent(oFile, null);
        }
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        if (isEnabled != enabled) {
            pathField.setEnabled(enabled);
            browseButton.setEnabled(enabled);
            clearButton.setEnabled(enabled);
            isEnabled = enabled;
        }
    }
    
    @Override
    public void setValue(Object v) {
        if (v == null) {
            pathField.setText("");
            if (choosedFile != null) {
                choosedFile = null;
            }
        } else if (v instanceof File) {
            File f = (File) v;
            pathField.setText(f.getAbsolutePath());
            choosedFile = f;
        } else if (v instanceof String) {
            File file = new File(v.toString());
            if (file.exists() && file.isFile()) {
                pathField.setText(file.getAbsolutePath());
                choosedFile = file;
            }
        }
    }
}
