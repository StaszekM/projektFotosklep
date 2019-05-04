package EditableImageAlgorithms;

import EditableImage.*;

/**
 * Rozmycie Gaussa na masce 5x5 i zdefiniowanym przez użytkownika odchyleniem standardowym różnym od zera.
 *
 */
public class GaussianBlur implements ImageProcessor {
    private int[][] mask;

    public GaussianBlur(double standardDeviation) throws ImageProcessorException {
        if (standardDeviation == 0.0) throw new ImageProcessorException("Gaussian Blur korzysta z niewłaściwego " +
                "odchylenia standardowego " + standardDeviation);
        mask = createMask(standardDeviation);
    }

    private double GaussDistribution2D(double x, double y, double standardDeviation, double multiplier) {
        return multiplier * (1 / (2 * Math.PI * Math.pow(standardDeviation, 2))) * Math.exp(-(Math.pow(x, 2) + Math.pow(y, 2)) / (2 * Math.pow(standardDeviation, 2)));
    }

    public int[][] createMask(double standardDeviation) {
        int[][] wyn = new int[5][5];
        int multiplier = 1;
        int centralValue = 0;
        do {
            centralValue = (int) Math.floor(GaussDistribution2D(0, 0, standardDeviation, multiplier));
            if (centralValue < 25) multiplier++;
        } while (centralValue < 25);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                wyn[i][j] = (int) Math.floor(GaussDistribution2D(i - (5 / 2), j - (5 / 2), standardDeviation, multiplier));
            }
        }
        return wyn;
    }


    @Override
    public void visitProcessableImage(ProcessableImage image) {
        int width = image.getImageWidth();
        int height = image.getImageHeight();

        Pixel[][] pixelMatrix = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Pixel p = image.getPixel(i, j);
                pixelMatrix[i][j] = new Pixel(p.getR(), p.getG(), p.getB());
            }
        }

        if (width >= mask.length && height >= mask.length) {
            //ta macierz będzie zawierać zmodyfikowane piksele
            Pixel[][] copiedPixels = new Pixel[width - mask.length + 1][height - mask.length + 1];

            int sum = 0;
            for (int i = 0; i < mask.length; i++) {
                for (int j = 0; j < mask.length; j++) {
                    sum += mask[i][j];
                }
            }

            for (int i = 0; i < width - mask.length; i++) {
                for (int j = 0; j < height - mask.length; j++) {
                    int r = 0;
                    int g = 0;
                    int b = 0;
                    for (int fx = 0; fx < mask.length; fx++) {
                        for (int fy = 0; fy < mask.length; fy++) {
                            r += pixelMatrix[i + fx][j + fy].getR() * mask[fx][fy];
                            g += pixelMatrix[i + fx][j + fy].getG() * mask[fx][fy];
                            b += pixelMatrix[i + fx][j + fy].getB() * mask[fx][fy];
                        }
                    }
                    r /= sum;
                    g /= sum;
                    b /= sum;

                    copiedPixels[i][j] = new Pixel(r, g, b);
                }
            }

            for (int i = mask.length / 2; i < copiedPixels.length; i++) {
                for (int j = mask.length / 2; j < copiedPixels[0].length; j++) {
                    pixelMatrix[i][j] = copiedPixels[i - (mask.length / 2)][j - (mask.length / 2)].clone();
                }
            }

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    image.setPixel(i, j, pixelMatrix[i][j].clone());
                }
            }
        }
    }

    @Override
    public void modifyPixel(ProcessableImage image, int x, int y) {

    }
}
