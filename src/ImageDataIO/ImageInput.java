package ImageDataIO;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;


/**
 * Klasa, której zadaniem jest odczyt pliku graficznego i zapisanie jego stanu w obiekcie typu {@code BufferedImage}
 * Obiekt jest przekazywany Obserwatorom za pomocą metody {@code getImage()}.
 * <p>
 * Klasa wykorzystuje wzorzec projektowy Singleton i Obserwator dla powiadamiania zainteresowanych obiektów o zmianach
 * swojego stanu
 */
public class ImageInput implements ImageDataWatchable {
    private BufferedImage image;
    private ArrayList<ImageDataWatcher> watchers;
    private File fileToRead;

    /*
        instance, konstruktor i getSingleInstance() są konieczne ze względu na wzorzec Singleton.
        getSingleInstance() pozwala na uzyskanie dostępu do obiektu tylko przez jeden wątek naraz.
    */
    private static ImageInput instance;

    private ImageInput() {
        watchers = new ArrayList<ImageDataWatcher>();
        instance = null;
    }

    public synchronized static ImageInput getSingleInstance() {
        if (instance == null) {
            instance = new ImageInput();
        }
        return instance;
    }

    public void locateFile(String location) {
        fileToRead = new File(location);
    }
    public void locateFile(File f) {
        fileToRead = f;
    }

    /**
     * Metoda może zwrócić {@code null}, jeśli, mimo poprawnego zlokalizowania pliku, nie dojdzie do poprawnego odczytu.
     *
     * @throws IOException
     */
    public void readImageFromFile() throws IOException {
        image = ImageIO.read(fileToRead);
        notifyWatchers();
    }

    @Override
    public void registerWatcher(ImageDataWatcher watcher) {
        watchers.add(watcher);
    }

    @Override
    public void unregisterWatcher(ImageDataWatcher watcher) {
        watchers.remove(watcher);
    }

    @Override
    public void notifyWatchers() {
        for (int i = 0; i < watchers.size(); i++) {
            watchers.get(i).receiveNotification(this);
        }
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }
}
