package utils.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tony Tsang
 * Date: 2014-07-16
 * Time: 15:25
 */
public class MessageDialogUtils {
    private MessageDialogUtils() {}

    private static Component prepareMessageComponent(String message) {
        JTextArea textArea;
        JScrollPane scrollPane;

        textArea = new JTextArea();
        textArea.setOpaque(false);
        textArea.setBackground(new Color(255, 255, 255, 155));
        textArea.setEditable(false);

        textArea.setText(message);

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(350, 100));

        return scrollPane;
    }

    public static void showInfoMessageDialog(Component parentComponent, String title, String message) {
        showMessageDialog(parentComponent, title, message, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showErrorMessageDialog(Component parentComponent, String title, String message) {
        showMessageDialog(parentComponent, title, message, JOptionPane.WARNING_MESSAGE);
    }

    public static void showMessageDialog(Component parentComponent, String title, String message, int messageType) {
        Component messageComponent = prepareMessageComponent(message);

        JOptionPane.showMessageDialog(parentComponent, messageComponent, title, messageType);
    }

    public static int showOkCancelConfirmDialog(Component parentComponent, String title, String message) {
        return showConfirmDialog(
            parentComponent, title, message, JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE
        );
    }

    public static int showYesNoConfirmDialog(Component parentComponent, String title, String message) {
        return showConfirmDialog(
            parentComponent, title, message, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
        );
    }

    public static int showYesNoCancelConfirmDialog(Component parentComponent, String title, String message) {
        return showConfirmDialog(
            parentComponent, title, message, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE
        );
    }

    public static int showConfirmDialog(
        Component parentComponent, String title, String message, int optionType, int messageType
    ) {
        return JOptionPane.showConfirmDialog(
            parentComponent, prepareMessageComponent(message), title, optionType, messageType
        );
    }
}
