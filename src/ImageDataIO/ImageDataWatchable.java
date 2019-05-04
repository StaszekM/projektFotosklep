package ImageDataIO;

import java.awt.image.BufferedImage;

public interface ImageDataWatchable {
    void registerWatcher(ImageDataWatcher watcher);
    void unregisterWatcher(ImageDataWatcher watcher);
    void notifyWatchers();
    BufferedImage getImage();
}
