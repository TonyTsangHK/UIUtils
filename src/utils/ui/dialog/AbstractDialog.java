package utils.ui.dialog;

import utils.ui.GuiUtils;

import javax.swing.*;
import java.awt.*;

public class AbstractDialog extends JDialog {
    public AbstractDialog(Frame frame) {
        this(frame, "", false);
    }
    
    public AbstractDialog(Frame frame, String title) {
        this(frame, title, false);
    }
    
    public AbstractDialog(Frame frame, boolean modality) {
        this(frame, "", false);
    }
    
    public AbstractDialog(Frame frame, String title, boolean modality) {
        super(frame, title, modality);
    }
    
    public AbstractDialog(Dialog owner) {
        this(owner, "", false);
    }
    
    public AbstractDialog(Dialog owner, String title) {
        this(owner, title, false);
    }
    
    public AbstractDialog(Dialog owner, boolean modality) {
        this(owner, "", modality);
    }
    
    public AbstractDialog(Dialog owner, String title, boolean modality) {
        super(owner, title, modality);
    }
    
    public void showFixedSizeDialog(int w, int h, int x, int y) {
        setSize(w, h);
        setLocation(x, y);
        setVisible(true);
    }
    
    public void showPackedDialog(int x, int y) {
        setLocation(x, y);
        setVisible(true);
    }
    
    public void showDialogAtCenter() {
        Dimension size = getPreferredSize();
        Dimension screenSize = GuiUtils.getScreenResolution();
        setLocation((screenSize.width - size.width)/2, (screenSize.height - size.height) / 2);
        setVisible(true);
    }
    
    public void showFixedSizeDialogAtCenter(int w, int h) {
        setSize(w, h);
        Dimension size = getPreferredSize();
        Dimension screenSize = GuiUtils.getScreenResolution();
        setLocation((screenSize.width - size.width)/2, (screenSize.height - size.height)/2);
        setVisible(true);
    }
    
    public void showPackedDialogAtCenter() {
        pack();
        Dimension preferredSize = getPreferredSize();
        Dimension screenSize = GuiUtils.getScreenResolution();
        setLocation((screenSize.width - preferredSize.width)/2, (screenSize.height - preferredSize.height)/2);
        setVisible(true);
    }
}
