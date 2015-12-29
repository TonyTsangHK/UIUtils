package utils.ui;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Locale;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.text.JTextComponent;

import utils.ui.lang.TextMenuLang;

public class JTextComponentPopUpHandler implements MouseListener {
    private JTextComponent source;
    
    private JPopupMenu popUpMenu;
    
    private JMenuItem copyItem, cutItem, pasteItem, selectAllItem;
    
    public JTextComponentPopUpHandler(JTextComponent textComp) {
        this(textComp, Locale.getDefault().toString());
    }

    public JTextComponentPopUpHandler(JTextComponent textComp, String lang) {
        this(textComp, TextMenuLang.getLangMap(lang));
    }

    public JTextComponentPopUpHandler(JTextComponent textComp, Map<String, String> langMap) {
        this.source = textComp;
        constructPopUpMenu(langMap);
    }
    
    public static void enablePopUpHandlerForJTextComponent(JTextComponent textComp) {
        textComp.addMouseListener(new JTextComponentPopUpHandler(textComp));
    }
    
    public static void enablePopUpHandlerForJTextComponent(JTextComponent textComp, String lang) {
        textComp.addMouseListener(
            new JTextComponentPopUpHandler(textComp, lang)
        );
    }
    
    public static void enablePopUpHandlerForJTextComponents(JTextComponent ... textComps) {
        for (JTextComponent textComp : textComps) {
            enablePopUpHandlerForJTextComponent(textComp);
        }
    }
    
    public static void enablePopUpHandlerForJTextComponets(String lang, JTextComponent ... textComps) {
        for (JTextComponent textComp : textComps) {
            enablePopUpHandlerForJTextComponent(textComp, lang);
        }
    }
    
    private void constructPopUpMenu(Map<String, String> langMap) {
        popUpMenu = new JPopupMenu();
        
        copyItem = new JMenuItem(langMap.get("copy"));
        cutItem = new JMenuItem(langMap.get("cut"));
        pasteItem = new JMenuItem(langMap.get("paste"));
        selectAllItem = new JMenuItem(langMap.get("selectAll"));
        
        popUpMenu.add(copyItem);
        popUpMenu.add(cutItem);
        popUpMenu.add(pasteItem);
        popUpMenu.add(selectAllItem);
        
        copyItem.setMnemonic('c');
        cutItem.setMnemonic('t');
        pasteItem.setMnemonic('p');
        selectAllItem.setMnemonic('a');
        
        copyItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        source.copy();
                    }
                }
        );
        
        cutItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        source.cut();
                    }
                }
        );
        pasteItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        source.paste();
                    }
                }
        );
        selectAllItem.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        source.selectAll();
                    }
                }
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
