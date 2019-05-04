package GUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

class ChooseFileWindow extends JFileChooser {

    private int fileChoiceResult;

    ChooseFileWindow(Container container){
        super();

        this.setSelectedFile(new File("D:\\Użytkownicy\\stasi\\Pictures\\Lenna.jpg"));
        this.setAcceptAllFileFilterUsed(false);
        this.setDragEnabled(false);
        this.setMultiSelectionEnabled(false);
        this.addChoosableFileFilter(new FileNameExtensionFilter("Obrazy (*.jpg;*.jpeg)", "jpg", "jpeg"));

        fileChoiceResult = this.showDialog(container, "Otwórz");
    }

    public int getFileChoiceResult() {
        return fileChoiceResult;
    }
}
