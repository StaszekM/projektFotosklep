package EditableImageAlgorithms;

import EditableImage.EditableImage;
import EditableImage.Pixel;
import EditableImageAlgorithms.ImageProcessor;
import EditableImageMemento.Originator;

/**
 * Interfejs ProcessableImage wykorzystywany jest przy wzorcu Wizytator, pozwala na wykonywanie algorytmów na obrazie typu
 * EditableImage
 * @see EditableImage
 * @see ImageProcessor
 * @see Pixel
 */
public interface ProcessableImage {
    void acceptProcessor(ImageProcessor processor);
    void requestForMemento(Originator originator);
    Pixel getPixel(int x, int y);
    void setPixel(int x, int y, Pixel p);
    int getImageWidth();
    int getImageHeight();
}
