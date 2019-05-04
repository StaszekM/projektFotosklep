package Main;

import GUI.AppWindow;

import javax.swing.*;

public class Main {

    private static AppWindow mainWindow;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainWindow = AppWindow.getInstance();
            }
        });
    }
}
