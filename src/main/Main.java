package main;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import ui.LoginUI;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException var2) {
            System.err.println("FlatLaf failed to load. Falling back to default.");
        }

        SwingUtilities.invokeLater(() -> (new LoginUI()).setVisible(true));
    }
}
