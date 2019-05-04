package EditableImageAlgorithms;

import EditableImage.Pixel;

/**
 * Zmiana kontrastu z możliwością ustawienia stopnia kontrastowania
 * Po testach - zalecane wartości stopnia kontrastowania (do celów praktycznych): 1/32 (0.03125) - 32
 * Wartość stopnia równa 1 nie wpływa na kontrast.
 */
public class Contrast implements ImageProcessor{
    private double contrastMultiplier;

    public Contrast(double contrastMultiplier) throws ImageProcessorException {
        if (contrastMultiplier < 0.03125f || contrastMultiplier > 32f) throw new ImageProcessorException("Contrast " +
                "wykorzystuje nieprawidłowy próg " + contrastMultiplier);
        this.contrastMultiplier = contrastMultiplier;
    }

    @Override
    public void visitProcessableImage(ProcessableImage image){
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
        int r = image.getPixel(x,y).getR();
        int g = image.getPixel(x,y).getG();
        int b = image.getPixel(x,y).getB();

        r -= 128;
        g -= 128;
        b -= 128;

        r *= contrastMultiplier;
        g *= contrastMultiplier;
        b *= contrastMultiplier;

        r += 128;
        g += 128;
        b += 128;

        if (r > 255) r = 255;
        if (r < 0) r = 0;
        if (g > 255) g = 255;
        if (g < 0) g = 0;
        if (b > 255) b = 255;
        if (b < 0) b = 0;

        image.setPixel(x, y, new Pixel(r, g, b));
    }

    public double getContrastMultiplier() {
        return contrastMultiplier;
    }
}
