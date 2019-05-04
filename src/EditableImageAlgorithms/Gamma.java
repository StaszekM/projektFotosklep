package EditableImageAlgorithms;

import EditableImage.Pixel;

/**
 * Modyfikacja gamma obrazu z możliwością ustawienia modyfikatora
 * Po testach - zalecane wartości modyfikatora 1/32 (0.03125) - 32
 */
public class Gamma implements ImageProcessor {
    private double gammaModifier;

    public Gamma(double gammaModifier) throws ImageProcessorException {
        if (gammaModifier < 0.03125 || gammaModifier > 32.0) throw new ImageProcessorException("Gamma używa niepoprawnego " +
                "modyfikatora "+gammaModifier);
        this.gammaModifier = gammaModifier;
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
        Pixel p = image.getPixel(x, y);
        int r = p.getR();
        int g = p.getG();
        int b = p.getB();

        r = (int)(255 * Math.pow((double)r/255.0, 1.0/gammaModifier));
        g = (int)(255 * Math.pow((double)g/255.0, 1.0/gammaModifier));
        b = (int)(255 * Math.pow((double)b/255.0, 1.0/gammaModifier));

        image.setPixel(x, y, new Pixel(r, g, b));
    }
}
