package EditableImageAlgorithms;

import EditableImage.Pixel;

/**
 * Zmiana ekspozycji z możliwością ustalenia stopnia ekspozycji
 * Po testach - zalecane wartości stopnia ekspozycji (do celów praktycznych) 1/32 (0.03125) - 32
 * Wartość stopnia równa 1 nie wpływa na ekspozycję
 */
public class Exposure implements ImageProcessor {
    private double exposureModifier;

    public Exposure(double exposureModifier) throws ImageProcessorException {
        if (exposureModifier < 0.03125 || exposureModifier > 32f) throw new ImageProcessorException("Exposure używa " +
                "niewłaściwego stopnia "+exposureModifier);
        this.exposureModifier = exposureModifier;
    }

    @Override
    public void visitProcessableImage(ProcessableImage image) {
        int width = image.getImageWidth();
        int height = image.getImageHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                modifyPixel(image, i, j);
            }
        }
    }

    @Override
    public void modifyPixel(ProcessableImage image, int x, int y) {
        Pixel p = image.getPixel(x, y);
        int r = p.getR();
        int g = p.getG();
        int b = p.getB();

        r *= exposureModifier;
        g *= exposureModifier;
        b *= exposureModifier;

        if (r > 255) r = 255;
        if (g > 255) g = 255;
        if (b > 255) b = 255;

        p.setR(r);
        p.setG(g);
        p.setB(b);

        image.setPixel(x, y, p);
    }
}
