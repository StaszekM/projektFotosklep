package EditableImageAlgorithms;

import EditableImage.Pixel;
import EditableImageMemento.Originator;

import java.awt.*;

/**
 * Zmiana barwy obrazu z wykorzystaniem konwersji RGB-HSV. Modyfikator wykorzystuje argument z przedziału [-1.0f;+1.0f]
 * Wartość modyfikatora 0.0f nie zmienia barwy.
 */
public class Hue implements ImageProcessor {
    private float[][][] HSVPixelData;
    private float hueModifier;

    public Hue(float hueModifier) throws ImageProcessorException {
        if (hueModifier < -1.0f || hueModifier > 1.0f) throw new ImageProcessorException("Hue wykorzystuje nieprawidłowy " +
                "argument " + hueModifier);
        this.hueModifier = hueModifier;
    }

    @Override
    public void visitProcessableImage(ProcessableImage image) {
        int width = image.getImageWidth();
        int height = image.getImageHeight();
        HSVPixelData = new float[width][height][3];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pixel p = image.getPixel(i,j);
                HSVPixelData[i][j] = Color.RGBtoHSB(p.getR(), p.getG(), p.getB(), null);
                HSVPixelData[i][j][0] = (HSVPixelData[i][j][0]+ hueModifier);
                modifyPixel(image, i, j);
            }
        }
    }

    @Override
    public void modifyPixel(ProcessableImage image, int x, int y) {
        Pixel p = image.getPixel(x,y);
        Color finalColor = new Color(Color.HSBtoRGB(HSVPixelData[x][y][0], HSVPixelData[x][y][1], HSVPixelData[x][y][2]));
        p.setR(finalColor.getRed());
        p.setG(finalColor.getGreen());
        p.setB(finalColor.getBlue());

        image.setPixel(x, y, p);
    }
}
