package EditableImageMemento;

import EditableImage.EditableImage;

import java.util.Stack;

/**
 * Caretaker zawiera stos przechowujący historię edycji obrazu.
 */
public class Caretaker {
    private Stack<EditableImage> imageStack = new Stack<EditableImage>();

    private static Caretaker ourInstance = new Caretaker();

    synchronized static Caretaker getInstance() {
        return ourInstance;
    }

    private Caretaker() {
    }

    void push(EditableImage image){
        imageStack.push(image);
    }

    EditableImage pop(){
        return imageStack.pop();
    }

    boolean empty(){
        return imageStack.empty();
    }

    EditableImage peek(){
        return imageStack.peek();
    }

    int search(EditableImage image){
        return imageStack.search(image);
    }
}
