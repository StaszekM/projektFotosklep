package EditableImageAlgorithms;

import EditableImage.Pixel;
import EditableImageMemento.Originator;

public class Reverse implements ImageProcessor {
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
        Pixel p = new Pixel(255 - image.getPixel(x,y).getR(), 255 - image.getPixel(x,y).getG(), 255 - image.getPixel(x,y).getB());
        image.setPixel(x,y, p);
    }
}
