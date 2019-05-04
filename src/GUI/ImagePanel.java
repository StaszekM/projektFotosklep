package GUI;

import EditableImage.*;
import EditableImageAlgorithms.ImageProcessor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

/**
 * Klasa ImagePanel wyświetla w AppWindow obrazek edytowany przez użytkownika.
 *
 *
 */
class ImagePanel extends JPanel {
    private EditableImage originalImg;
    private EditableImage displayedImg;
    private JLabel jlab;
    private ArrayList<ImagePanelListener> listeners;


    ImagePanel(){
        this.listeners = new ArrayList<ImagePanelListener>();

        this.setLayout(new FlowLayout());
        jlab = new JLabel();

        jlab.setVerticalAlignment(SwingConstants.CENTER);
        jlab.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(jlab);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    void showPleaseWait(){
        jlab.setText("Czekaj, ładuję podgląd...");

        this.validate();
        this.repaint();
    }

    void clear(){
        displayedImg = null;
        jlab.setIcon(null);
        jlab.setText(null);

        this.validate();
        this.repaint();
    }

    void loadImage(EditableImage img){
        boolean notificationFlag = (displayedImg == null);

        this.originalImg = img.clone();
        this.displayedImg = img.clone();

        if (notificationFlag) notifyListeners("loadImage");
    }

    synchronized void resizeImage(int percent){
        BufferedImage tmpOriginalBuffered = ImageDataInterpreter.convertFromEditableImage(originalImg);
        Image tmp = tmpOriginalBuffered.getScaledInstance(tmpOriginalBuffered.getWidth()*percent/100,tmpOriginalBuffered.getHeight()*percent/100,Image.SCALE_SMOOTH);
        BufferedImage b = new BufferedImage(tmp.getWidth(null), tmp.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = b.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        displayedImg = ImageDataInterpreter.convertFromBuffered(b);
    }

    void displayImage(EditableImage displayedImg){
        if (displayedImg != null) {
            jlab.setText(null);
            jlab.setSize(new Dimension(displayedImg.getImageWidth(), displayedImg.getImageHeight()));
            jlab.setIcon(new ImageIcon(ImageDataInterpreter.convertFromEditableImage(displayedImg)));

            this.validate();
            this.repaint();
        }
    }

    void processDisplayedImage(ImageProcessor p){
        displayedImg.acceptProcessor(p);
    }

    EditableImage getOriginalImg(){
        return originalImg;
    }
    EditableImage getDisplayedImg() {return displayedImg;}

    private void notifyListeners(String notificationCode){
        for (int i =0; i < listeners.size(); i++){
            listeners.get(i).imagePanelUpdate(notificationCode);
        }
    }

    void addImagePanelListener(ImagePanelListener listener){
        this.listeners.add(listener);
    }

}
