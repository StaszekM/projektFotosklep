package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

class SaveFileWindow extends JFileChooser {
    private int fileChoiceResult;

    SaveFileWindow(Component component){
        this.setAcceptAllFileFilterUsed(false);
        this.setDragEnabled(false);
        this.setMultiSelectionEnabled(false);
        this.addChoosableFileFilter(new FileNameExtensionFilter("Obrazy (*.jpg;*.jpeg)", "jpg", "jpeg"));

        showDialog(component, "Zapisz");
    }

    public int getFileChoiceResult() {
        return fileChoiceResult;
    }
}
