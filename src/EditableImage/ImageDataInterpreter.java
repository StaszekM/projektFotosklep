package EditableImage;

import java.awt.*;
import java.awt.image.*;

/**
 * Klasa mająca za zadanie konwertowąć między typami EditableImage (używanym do obróbki obrazu w warstwie logiki biznesowej)
 * a BufferedImage (używanym do zapisu/odczytu obrazów z dysku)
 *
 * Klasa może też kopiować obiekty typu BufferedImage (oryginalna klasa nie zawiera metody clone())/
 */
public class ImageDataInterpreter {
    public static EditableImage convertFromBuffered(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        Pixel[][] pixelMatrix = new Pixel[width][height];
        Color currentColor;

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                currentColor = new Color(image.getRGB(i,j));
                pixelMatrix[i][j] = new Pixel(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
            }
        }

        return new EditableImage(pixelMatrix);
    }

    public static BufferedImage convertFromEditableImage(EditableImage image){
        BufferedImage wyn = new BufferedImage(image.getImageWidth(), image.getImageHeight(), BufferedImage.TYPE_INT_RGB);
        Color currentColor;
        Pixel currentPixel;
        int width = image.getImageWidth();
        int height = image.getImageHeight();

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                currentPixel = image.getPixel(i,j);
                currentColor = new Color(currentPixel.getR(), currentPixel.getG(), currentPixel.getB());
                wyn.setRGB(i,j, currentColor.getRGB());
            }
        }

        return wyn;
    }

    public static BufferedImage cloneBufferedImage(BufferedImage img){
        ColorModel cm = img.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = img.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
