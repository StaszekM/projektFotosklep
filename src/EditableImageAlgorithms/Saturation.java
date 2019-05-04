package EditableImageAlgorithms;

import EditableImage.Pixel;
import EditableImageMemento.Originator;

import java.awt.*;

/**
 * Zmiana nasycenia kolorów w obrazie, dopuszczalne wartośi modyfikatora [-1.0f;1.0f]
 */
public class Saturation implements ImageProcessor {
    private float[][][] HSVPixelData;
    private float saturationModifier;

    public Saturation(float saturationModifier) throws ImageProcessorException {
        if (saturationModifier < -1.0f || saturationModifier > 1.0f) throw new ImageProcessorException("Saturation " +
                "używa niewłaściwego modyfikatora "+saturationModifier);
        this.saturationModifier = saturationModifier;
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
                HSVPixelData[i][j][1] = HSVPixelData[i][j][1] + saturationModifier;

                modifyPixel(image, i, j);
            }
        }
    }

    @Override
    public void modifyPixel(ProcessableImage image, int x, int y) {
        Pixel p = image.getPixel(x,y);

        if (HSVPixelData[x][y][1] > 1.0f) HSVPixelData[x][y][1] = 1.0f;
        if (HSVPixelData[x][y][1] < 0.0f) HSVPixelData[x][y][1] = 0.0f;

        Color finalColor = new Color(Color.HSBtoRGB(HSVPixelData[x][y][0], HSVPixelData[x][y][1], HSVPixelData[x][y][2]));
        p.setR(finalColor.getRed());
        p.setG(finalColor.getGreen());
        p.setB(finalColor.getBlue());

        image.setPixel(x, y, p);
    }
}
