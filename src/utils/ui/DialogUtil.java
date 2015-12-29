package utils.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import utils.string.StringUtil;

public class DialogUtil {
    public static final String 
        DEFAULT_MESSAGE_TITLE     = "MESSAGE",
        DEFAULT_INFORMATION_TITLE = "INFORMATION", 
        DEFAULT_ERROR_TITLE       = "ERROR",
        DEFAULT_WARNING_TITLE     = "WARNING", 
        DEFAULT_CONFIRM_TITLE     = "CONFIRM",
        DEFAULT_INPUT_TITLE       = "INPUT";
    
    public static final int 
        OPEN_FILE            = 6632,
        SAVE_FILE            = 6622,
        OK_OPTION            = JOptionPane.OK_OPTION,
        CANCEL_OPTION        = JOptionPane.CANCEL_OPTION,
        DEFAULT_OPTION       = JOptionPane.DEFAULT_OPTION,
        YES_NO_OPTION        = JOptionPane.YES_NO_OPTION,
        YES_NO_CANCEL_OPTION = JOptionPane.YES_NO_CANCEL_OPTION,
        OK_CANCEL_OPTION     = JOptionPane.OK_CANCEL_OPTION,
        YES_OPTION           = JOptionPane.YES_OPTION,
        NO_OPTION            = JOptionPane.NO_OPTION,
        CLOSED_OPTION        = JOptionPane.CLOSED_OPTION;
    
    public static JDialog showMessage(Component p, String m) {
        return showMessage(p, DEFAULT_MESSAGE_TITLE, m, JOptionPane.PLAIN_MESSAGE, false);
    }
    
    public static JDialog showMessage(Component p, String m, boolean modal) {
        return showMessage(p, DEFAULT_MESSAGE_TITLE, m, JOptionPane.PLAIN_MESSAGE, modal);
    }
    
    public static JDialog showMessage(Component p, String t, String m) {
        return showMessage(p, t, m, JOptionPane.PLAIN_MESSAGE, false);
    }
    
    public static JDialog showMessage(Component p, String t, String m, boolean modal) {
        return showMessage(p, t, m, JOptionPane.PLAIN_MESSAGE, modal);
    }
    
    public static JDialog showInfoMessage(Component p, String m) {
        return showMessage(p, DEFAULT_INFORMATION_TITLE, m, JOptionPane.INFORMATION_MESSAGE, false);
    }
    
    public static JDialog showInfoMessage(Component p, String m, boolean modal) {
        return showMessage(p, DEFAULT_INFORMATION_TITLE, m, JOptionPane.INFORMATION_MESSAGE, modal);
    }
    
    public static JDialog showInfoMessage(Component p, String t, String m) {
        return showMessage(p, t, m, JOptionPane.INFORMATION_MESSAGE, false);
    }
    
    public static JDialog showInfoMessage(Component p, String t, String m, boolean modal) {
        return showMessage(p, t, m, JOptionPane.INFORMATION_MESSAGE, modal);
    }
    
    public static JDialog showErrorMessage(Component p, String t, String m) {
        return showMessage(p, t, m, JOptionPane.ERROR_MESSAGE, false);
    }
    
    public static JDialog showErrorMessage(Component p, String t, String m, boolean modal) {
        return showMessage(p, t, m, JOptionPane.ERROR_MESSAGE, modal);
    }
    
    public static JDialog showErrorMessage(Component p, String m) {
        return showMessage(p, DEFAULT_ERROR_TITLE, m, JOptionPane.ERROR_MESSAGE, false);
    }
    
    public static JDialog showErrorMessage(Component p, String m, boolean modal) {
        return showMessage(p, DEFAULT_ERROR_TITLE, m, JOptionPane.ERROR_MESSAGE, modal);
    }
    
    public static JDialog showWarningMessage(Component p, String t, String m) {
        return showMessage(p, t, m, JOptionPane.WARNING_MESSAGE, false);
    }
    
    public static JDialog showWarningMessage(Component p, String t, String m, boolean modal) {
        return showMessage(p, t, m, JOptionPane.WARNING_MESSAGE, modal);
    }
    
    public static JDialog showWarningMessage(Component p, String m) {
        return showMessage(p, DEFAULT_WARNING_TITLE, m, JOptionPane.WARNING_MESSAGE, false);
    }
    
    public static JDialog showWarningMessage(Component p, String m, boolean modal) {
        return showMessage(p, DEFAULT_WARNING_TITLE, m, JOptionPane.WARNING_MESSAGE, modal);
    }
    
    public static JDialog showMessage(Component p, String t, String m, int mt) {
        return showMessage(p, t, m, mt, false);
    }
    
    public static JDialog showMessage(Component p, String t, String m, int mt, boolean modal) {
        JDialog dialog = new JOptionPane(m, mt).createDialog(p, t);
        dialog.setModal(p != null && modal);
        if (p != null) {
            GuiUtils.showDialogAtCenter(GuiUtils.getParentFrame(p), dialog);
        } else {
            GuiUtils.showDialogAtCenter(dialog);
        }
        return dialog;
    }
    
    public static int showConfirmMessage(Component p, String m) {
        return showConfirmMessage(p, DEFAULT_CONFIRM_TITLE, m, JOptionPane.OK_CANCEL_OPTION);
    }
    
    public static int showConfirmMessage(Component p, String t, String m) {
        return showConfirmMessage(p, t, m, JOptionPane.OK_CANCEL_OPTION);
    }
    
    public static int showOkCancelConfirmMessage(Component p, String t, String m) {
        return showConfirmMessage(p, t, m, JOptionPane.OK_CANCEL_OPTION);
    }
    
    public static int showYesNoConfirmMessage(Component p, String t, String m) {
        return showConfirmMessage(p, t, m, JOptionPane.YES_NO_OPTION);
    }
    
    public static int showYesNoCancelConfirmMessage(Component p, String t, String m) {
        return showConfirmMessage(p, t, m, JOptionPane.YES_NO_CANCEL_OPTION);
    }
    
    public static int showConfirmMessage(Component p, String t, String m, int ot) {
        return JOptionPane.showConfirmDialog(p, m, t, ot, JOptionPane.QUESTION_MESSAGE);
    }

    public static void showYesNoNonBlockingConfirmDialog(
        Component parent, String title, String message, ActionListener yesAction, ActionListener noAction,
        ActionListener callbackAction
    ) {
        JDialog dialog = new JDialog(GuiUtils.getParentFrame(parent), title, false);

        dialog.setLayout(new BorderLayout());

        JTextArea messageArea = new JTextArea(message, 6, 25);
        messageArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(messageArea);

        dialog.add(scroll, SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton yesButton = new JButton("是"), noButton = new JButton("否");
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);

        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();

        yesButton.addActionListener(
            e -> {
                _doDialogButtonAction(
                    dialog, yesAction, callbackAction, e
                );
            }
        );

        noButton.addActionListener(
            e -> {
                _doDialogButtonAction(
                    dialog, noAction, callbackAction, e
                );
            }
        );

        GuiUtils.showDialogAtCenter(dialog);
    }

    private static void _doDialogButtonAction(
        JDialog dialog, ActionListener targetAction, ActionListener callbackAction, ActionEvent e
    ) {
        if (targetAction != null) {
            targetAction.actionPerformed(e);
        }

        if (callbackAction != null) {
            callbackAction.actionPerformed(e);
        }

        dialog.dispose();
    }

    public static String showPromptDialog(Component parent, String title, String message) {
        return showPromptDialog(parent, title, message, JOptionPane.QUESTION_MESSAGE);
    }

    public static String showPromptDialog(Component parent, String title, String message, int messageType) {
        return JOptionPane.showInputDialog(parent, title, message, messageType);
    }
    
    public static String showInputMessage(Component p, String t, String m) {
        return showInputMessage(p, t, m, null);
    }
    
    public static String showInputMessage(Component p, String m) {
        return showInputMessage(p, m, m, null);
    }
    
    public static String showInputMessage(Component p, String t, String m, Object iv) {
        return JOptionPane.showInputDialog(p, m, t, JOptionPane.QUESTION_MESSAGE);
    }
    
    public static File showOpenFileDialog(JFileChooser chooser, Component parent, String currentDirectory) {
        return chooseFile(chooser, parent, OPEN_FILE, currentDirectory, false, "", "");
    }
    
    public static File showSaveFileDialog(
            JFileChooser chooser, Component parent, String currentDirectory, 
            boolean askOverwrite, String overwriteTitle, String overwriteMsg
    ) {
        return chooseFile(chooser, parent, SAVE_FILE, currentDirectory, askOverwrite, overwriteTitle, overwriteMsg);
    }
    
    public static File chooseFile(
            JFileChooser chooser, Component parent, int openDialogType, String currentDirectory,
            boolean askOverwrite, String overwriteTitle, String overwriteFormatString
    ) {
        if (chooser == null) {
            chooser = new JFileChooser();
        }
        chooser.setCurrentDirectory(new File(currentDirectory));
        int opt = -1;
        if (openDialogType == OPEN_FILE) {
            opt = chooser.showOpenDialog(parent);
        } else if (openDialogType == SAVE_FILE) {
            opt = chooser.showSaveDialog(parent);
        }
        if (opt == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            if (openDialogType == SAVE_FILE) {
                if (selectedFile.exists() && askOverwrite) {
                    int confirmOption = showYesNoConfirmMessage(parent, overwriteTitle, 
                            StringUtil.getFormatString(overwriteFormatString, selectedFile.getName()));
                    if (confirmOption == JOptionPane.YES_OPTION) {
                        return selectedFile;
                    } else {
                        return null;
                    }
                }
                return selectedFile;
            } else {
                return (selectedFile != null && selectedFile.exists())? selectedFile : null;
            }
        } else {
            return null;
        }
    }
}
