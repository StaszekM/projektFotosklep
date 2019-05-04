package EditableImageAlgorithms;

import EditableImage.Pixel;
import EditableImageMemento.Originator;

/**
 * Przekształcenie progowe z możliwością ustawienia progu
 *
 * Określa jasność na podstawie wzoru Y = 0.2126R + 0.7152G + 0.0722B,
 * co daje zakres Y [0, 255]
 *
 *
 */
public class Threshold implements ImageProcessor{
    private int luminosityThreshold;

    public Threshold(int luminosityThreshold) throws ImageProcessorException{
        if (luminosityThreshold < 0 || luminosityThreshold > 255) throw new ImageProcessorException("Threshold wykorzystuje " +
                "nieprawidłowy próg "+ luminosityThreshold);
        this.luminosityThreshold = luminosityThreshold;
    }

    @Override
    public void visitProcessableImage(ProcessableImage image) {
        int width = image.getImageWidth();
        int height = image.getImageHeight();

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                modifyPixel(image, i, j);
            }
        }
    }

    @Override
    public void modifyPixel(ProcessableImage image, int x, int y) {
        Pixel p = image.getPixel(x,y);
        double luminosity = 0.2126 * p.getR() + 0.7152 * p.getG() + 0.0722 * p.getB();
        if (luminosity <= getLuminosityThreshold()){
            p = new Pixel(0,0,0);
        } else p = new Pixel(255,255,255);

        image.setPixel(x,y, p);
    }

    public int getLuminosityThreshold() {
        return luminosityThreshold;
    }


}
