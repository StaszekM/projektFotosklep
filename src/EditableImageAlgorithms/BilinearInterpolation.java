package EditableImageAlgorithms;

import EditableImage.*;

public class BilinearInterpolation {

    public static EditableImage resizeImage(int width, int height, EditableImage image) {
        Pixel[][] pixelMatrix = new Pixel[image.getImageWidth()][image.getImageHeight()];
        for (int i = 0; i < image.getImageWidth(); i++) {
            for (int j = 0; j < image.getImageHeight(); j++) {
                pixelMatrix[i][j] = image.getPixel(i, j).clone();
            }
        }

        Pixel[][] newPixelMatrix = new Pixel[width][height];

        double ratiox = ((double) image.getImageWidth()) / ((double) width);
        double ratioy = ((double) image.getImageHeight()) / ((double) height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double x = ((double) i) * ratiox;
                double y = ((double) j) * ratioy;

                int minX = (int) Math.floor(x);
                int maxX = (int) Math.floor(x + 1);
                int minY = (int) Math.floor(y);
                int maxY = (int) Math.floor(y + 1);

                if (maxY >= image.getImageHeight()) {
                    maxY = image.getImageHeight() - 1;
                }
                if (maxX >= image.getImageWidth()){
                    maxX = image.getImageWidth() - 1;
                }

                double a = x - Math.floor(x);
                double b = y - Math.floor(y);

                double r1 = (1 - a) * pixelMatrix[minX][minY].getR() + a * pixelMatrix[maxX][minY].getR();
                double r2 = (1 - a) * pixelMatrix[minX][maxY].getR() + a * pixelMatrix[maxX][maxY].getR();
                int red = (int) Math.floor((1 - b) * r1 + b * r2);

                double g1 = (1 - a) * pixelMatrix[minX][minY].getG() + a * pixelMatrix[maxX][minY].getG();
                double g2 = (1 - a) * pixelMatrix[minX][maxY].getG() + a * pixelMatrix[maxX][maxY].getG();
                int green = (int) Math.floor((1 - b) * g1 + b * g2);

                double b1 = (1 - a) * pixelMatrix[minX][minY].getB() + a * pixelMatrix[maxX][minY].getB();
                double b2 = (1 - a) * pixelMatrix[minX][maxY].getB() + a * pixelMatrix[maxX][maxY].getB();
                int blue = (int) Math.floor((1 - b) * b1 + b * b2);

                newPixelMatrix[i][j] = new Pixel(red, green, blue);
            }
        }
        return new EditableImage(newPixelMatrix);
    }

}
