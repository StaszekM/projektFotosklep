package EditableImage;

import EditableImageAlgorithms.ImageProcessor;
import EditableImageAlgorithms.ProcessableImage;
import EditableImageMemento.Originator;

/**
 * Klasa EditableImage przechowuje informacje o obrazie jako macierz obiektów typu Pixel
 */
public class EditableImage implements ProcessableImage {
    private Pixel[][] pixelMatrix;

    public EditableImage (Pixel[][] pixelMatrix){
        this.pixelMatrix = pixelMatrix;
    }

    public Pixel[][] getPixelMatrix() {
        return pixelMatrix;
    }

    @Override
    public void requestForMemento(Originator originator){
        originator.saveState(this);
    }

    @Override
    public EditableImage clone(){
        Pixel[][] pixels = new Pixel[getImageWidth()][getImageHeight()];
        for (int i = 0; i < getImageWidth(); i++){
            for (int j = 0; j < getImageHeight(); j++){
                pixels[i][j] = pixelMatrix[i][j].clone();
            }
        }
        return new EditableImage(pixels);
    }

    @Override
    public Pixel getPixel(int x, int y){
        return pixelMatrix[x][y];
    }

    @Override
    public void setPixel(int x, int y, Pixel p) {
        this.pixelMatrix[x][y] = p;
    }

    @Override
    public int getImageWidth(){
        return pixelMatrix.length;
    }

    @Override
    public int getImageHeight(){
        return pixelMatrix[0].length;
    }

    @Override
    public void acceptProcessor(ImageProcessor processor){
        processor.visitProcessableImage(this);
    }
}
