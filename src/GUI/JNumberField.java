package GUI;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import java.awt.*;


public class JNumberField extends JTextField implements CaretListener {

    private static final Color ERROR_BACKGROUND = new Color(255, 71, 102);
    private static final Color NORMAL_BACKGROUND = new Color(255, 255, 255);

    private boolean correctData;

    JNumberField(int columns){
        super(columns);
        this.addCaretListener(this);
        this.correctData = false;
        this.setBackground(JNumberField.ERROR_BACKGROUND);
    }

    @Override
    public void caretUpdate(CaretEvent e) {
        for (int i = 0; i < this.getText().length(); i++){
            if (this.getText().charAt(i) < '0'|| this.getText().charAt(i) > '9' || this.getText().equals(null)){
                if (this.correctData) {
                    this.correctData = false;
                    this.setBackground(JNumberField.ERROR_BACKGROUND);
                }
                return;
            }
        }
        if (!this.correctData){
            this.correctData = true;
            this.setBackground(JNumberField.NORMAL_BACKGROUND);
        }
    }

    public boolean isCorrect(){
        return this.correctData;
    }
}
