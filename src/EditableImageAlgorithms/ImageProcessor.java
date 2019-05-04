package EditableImageAlgorithms;

/**
 * Interfejs ImageProcessor jest implementowany przez niektóre obiekty realizujące algorytmy na obrazie typu EditableImage
 * @see ProcessableImage
 * @see EditableImage.EditableImage
 */
public interface ImageProcessor {
    void visitProcessableImage(ProcessableImage image);
    void modifyPixel(ProcessableImage image, int x, int y);
}
