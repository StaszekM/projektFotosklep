package EditableImageAlgorithms;

import EditableImage.Pixel;

/**
 * Przekształca obraz kolorowy na czarno-biały
 */
public class ColorToGrayscale implements ImageProcessor {
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

        Pixel p = new Pixel((r+g+b)/3, (r+g+b)/3, (r+g+b)/3);

        image.setPixel(x, y, p);
    }


}
