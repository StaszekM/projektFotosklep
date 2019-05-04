package EditableImageAlgorithms;

import EditableImage.Pixel;

/**
 * Zmiana jasności obrazu z modyfikacją +/- 255 do składowych.
 */
public class Brightness implements ImageProcessor{
    private int brightnessModifier;

    public Brightness(int brightnessModifier) throws ImageProcessorException {
        if (brightnessModifier < -255 || brightnessModifier > 255) throw new ImageProcessorException("Brightness wykorzystuje" +
                " nieprawidłowy próg " + brightnessModifier);
        this.brightnessModifier = brightnessModifier;
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

    public void modifyPixel(ProcessableImage image, int x, int y) {
        Pixel p = image.getPixel(x,y);

        int r = p.getR() + brightnessModifier;
        int g = p.getG() + brightnessModifier;
        int b = p.getB() + brightnessModifier;

        if (r > 255) r = 255;
        if (r < 0) r = 0;
        if (g > 255) g = 255;
        if (g < 0) g = 0;
        if (b > 255) b = 255;
        if (b < 0) b = 0;

        p.setR(r);
        p.setG(g);
        p.setB(b);

        image.setPixel(x, y, p);
    }

    public int getBrightnessModifier() {
        return brightnessModifier;
    }
}
