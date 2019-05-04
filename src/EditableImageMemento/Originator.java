package EditableImageMemento;

import EditableImage.EditableImage;

import java.util.ArrayList;

/**
 * Klasa Originator jest uprawniona do zapisu obrazu na stosie oraz zwracania obrazów ze stosu.
 */
public class Originator {
    private ArrayList<OriginatorListener> listeners;

    private static Originator ourInstance = new Originator();

    public synchronized static Originator getInstance() {
        return ourInstance;
    }

    private Originator() {
        listeners = new ArrayList<OriginatorListener>();
    }

    public void saveState(EditableImage image){
        Caretaker.getInstance().push(image.clone());
        notifyOriginatorListeners();
    }

    public EditableImage peek(){
        return Caretaker.getInstance().peek();
    }

    public EditableImage returnToPrevious(){
        EditableImage wyn = Caretaker.getInstance().pop();
        notifyOriginatorListeners();
        return wyn;
    }

    public boolean isStackEmpty(){
        return Caretaker.getInstance().empty();
    }

    public void notifyOriginatorListeners(){
        for (OriginatorListener listener: listeners){
            listener.originatorStateChanged();
        }
    }

    public void addOriginatorListener(OriginatorListener listener){
        listeners.add(listener);
    }

    public void removeOriginatorListener(OriginatorListener listener){
        listeners.remove(listener);
    }
}
