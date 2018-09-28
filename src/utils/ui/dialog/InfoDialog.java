package utils.ui.dialog;

import javax.swing.*;
import java.awt.*;

public class InfoDialog extends AbstractDialog {
    private static final long serialVersionUID = 1L;
    
    public static final String[] AlternateColor = {"#CCFFFF", "#FFFFFF"};
    public static final String TitleColor = "#FFFFCC", WarningColor = "#FF0000";
    public static final String PLAIN_TEXT = "text/plain", HTML_TEXT = "text/html";
    
    private JEditorPane htmlPane;
    
    public InfoDialog(Frame f, String t, boolean m) {
        super(f, t, m);
        
        initialize();
    }
    
    public InfoDialog(Dialog d, String t, boolean m) {
        super(d, t, m);
        
        initialize();
    }
    
    private void initialize() {
        htmlPane = new JEditorPane();
        htmlPane.setEditable(false);
        setContentType(HTML_TEXT);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JScrollPane scroll = new JScrollPane(htmlPane);
        add(scroll);
    }
    
    public void setContentType(String contentType) {
        htmlPane.setContentType(contentType);
    }
    
    public void setDisplayInfo(String info) {
        htmlPane.setText(info);
    }
    
    public void setDisplayHtml(String html) {
        htmlPane.setText(html);
    }
}