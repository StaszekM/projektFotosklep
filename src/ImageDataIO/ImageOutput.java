package ImageDataIO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Klasa, która przyjmuje obiekt typu {@code BufferedImage} i zapisuje do pliku.
 *
 * Klasa korzysta ze wzorca Singleton.
 */
public class ImageOutput {
    private BufferedImage image;
    private File fileToWrite;

    public static ImageOutput instance;

    private ImageOutput(){

    }

    public synchronized static ImageOutput getSingleInstance(){
        if (instance == null){
            instance = new ImageOutput();
        }
        return instance;
    }

    public void locateFile(String location){
        fileToWrite = new File(location);
    }
    public void locateFile(File f){
        fileToWrite = f;
    }

    public void setImage(BufferedImage image){
        this.image = image;
    }

    public void saveImageToFile() throws IOException {
        ImageIO.write(image, "jpg", fileToWrite);
    }
}
