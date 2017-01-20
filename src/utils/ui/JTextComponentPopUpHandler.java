package utils.ui;

import utils.ui.lang.LanguageHandler;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

public class JTextComponentPopUpHandler implements MouseListener {
    private JTextComponent source;
    
    private JPopupMenu popUpMenu;
    
    private JMenuItem copyItem, cutItem, pasteItem, selectAllItem;

    public JTextComponentPopUpHandler(JTextComponent textComp) {
        this.source = textComp;
        constructPopUpMenu();
    }
    
    public static void enablePopUpHandlerForJTextComponent(JTextComponent textComp) {
        textComp.addMouseListener(new JTextComponentPopUpHandler(textComp));
    }
    
    public static void enablePopUpHandlerForJTextComponents(JTextComponent ... textComps) {
        for (JTextComponent textComp : textComps) {
            enablePopUpHandlerForJTextComponent(textComp);
        }
    }
    
    private void constructPopUpMenu() {
        popUpMenu = new JPopupMenu();
        
        copyItem = new JMenuItem(LanguageHandler.variable("textPopUp.copy"));
        cutItem = new JMenuItem(LanguageHandler.variable("textPopUp.cut"));
        pasteItem = new JMenuItem(LanguageHandler.variable("textPopUp.paste"));
        selectAllItem = new JMenuItem(LanguageHandler.variable("textPopUp.selectAll"));
        
        popUpMenu.add(copyItem);
        popUpMenu.add(cutItem);
        popUpMenu.add(pasteItem);
        popUpMenu.add(selectAllItem);
        
        copyItem.setMnemonic('c');
        cutItem.setMnemonic('t');
        pasteItem.setMnemonic('p');
        selectAllItem.setMnemonic('a');
        
        copyItem.addActionListener(
            e -> source.copy()
        );
        
        cutItem.addActionListener(
            e -> source.cut()
        );
        pasteItem.addActionListener(
            e -> source.paste()
        );
        selectAllItem.addActionListener(
            e -> source.selectAll()
        );
    }
    
    private void refreshItemStatus() {
        Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        pasteItem.setEnabled(t != null);
    }
    
    public JTextComponent getSource() {
        return source;
    }
    
    @Override
    public void mouseClicked(MouseEvent evt) {
        Point point = evt.getPoint();
        if (evt.getButton() == MouseEvent.BUTTON3) {
            popUpMenu.show(source, point.x, point.y);
            
            pasteItem.setEnabled(Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null) != null);
            
            refreshItemStatus();
        }
    }
    
    @Override
    public void mousePressed(MouseEvent evt) {
        // Ignored
    }
    
    @Override
    public void mouseReleased(MouseEvent evt) {
        // Ignored
    }
    
    @Override
    public void mouseEntered(MouseEvent evt) {
        // Ignored
    }
    
    @Override
    public void mouseExited(MouseEvent evt) {
        // Ignored
    }
}
