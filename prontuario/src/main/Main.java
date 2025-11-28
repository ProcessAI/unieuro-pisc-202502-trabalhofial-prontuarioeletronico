package main;

import view.TelaLogin;
import javax.swing.SwingUtilities;

public class Main {

    public static void MAin(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new TelaLogin().setVisible(true);
        });
    }
}