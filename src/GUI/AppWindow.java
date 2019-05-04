package GUI;

import EditableImage.EditableImage;
import EditableImage.ImageDataInterpreter;
import EditableImageAlgorithms.*;
import EditableImageMemento.Originator;
import EditableImageMemento.OriginatorListener;
import ImageDataIO.ImageInput;
import ImageDataIO.ImageOutput;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Klasa AppWindow jest odpowiedzialna za rysowanie okna głównego aplikacji - implementuje menubar na górze,
 * pole z obrazkiem pośrodku i statusbar na dole. Zawiera logikę poszczególnych komponentów
 * <p>
 * Okno może być tylko jedno.
 */
public class AppWindow implements MouseListener, ChangeListener, ActionListener, ImagePanelListener, OriginatorListener {

    private static AppWindow ourInstance;

    //    Sekcja okienka, paneli, menubaru i stopki
    private JFrame mainFrame;
    private JMenuBar menuBar;

    private ImagePanel imgPanel;
    private JScrollPane imgScrollPane;

    private JPanel algorithmParametersPanel;

    private JPanel footerPanel;
    private JSlider imgSizeSlider;
    private JLabel imgSizeLabel;

    //    Sekcja elementów menubaru
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu imageMenu;

    //    Sekcja elementów poszczególnych menu
//    Plik:
    private JMenuItem fileOpenMenuItem;
    private JMenuItem fileSaveMenuItem;
    //    Edycja:
    private JMenuItem undoMenuItem;
    //    Obraz:
    private JMenuItem brightnessAlgorithmMenuItem;
    private JMenuItem colorToGrayscaleAlgorithmMenuItem;
    private JMenuItem contrastAlgorithmMenuItem;
    private JMenuItem exposureAlgorithmMenuItem;
    private JMenuItem gammaAlgorithmMenuItem;
    private JMenuItem gaussianBlurAlgorithmMenuItem;
    private JMenuItem hueAlgorithmMenuItem;
    private JMenuItem reverseAlgorithmMenuItem;
    private JMenuItem saturationAlgorithmMenuItem;
    private JMenuItem thresholdAlgorithmMenuItem;

    public synchronized static AppWindow getInstance() {
        if (ourInstance == null) {
            ourInstance = new AppWindow();
        }
        return ourInstance;
    }

    private AppWindow() {
        Originator.getInstance().addOriginatorListener(this);

//        Okienko
        mainFrame = new JFrame("Fotosklep");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setSize(700, 600);

//        Panel obrazka
        imgPanel = new ImagePanel();
        imgPanel.addImagePanelListener(this);
        imgScrollPane = new JScrollPane(imgPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

//        Panel boczny
        algorithmParametersPanel = new JPanel();
        algorithmParametersPanel.setLayout(new BoxLayout(algorithmParametersPanel, BoxLayout.Y_AXIS));

//        Stopka
        footerPanel = new JPanel();
        footerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        imgSizeSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 200, 100);
        imgSizeSlider.addMouseListener(this);
        imgSizeSlider.addChangeListener(this);
        imgSizeSlider.setEnabled(false);

        imgSizeLabel = new JLabel("Rozmiar podglądu: 100%");

        footerPanel.add(imgSizeLabel);
        footerPanel.add(imgSizeSlider);


//        Menubar i menu
        menuBar = new JMenuBar();
        fileMenu = new JMenu("Plik");
        editMenu = new JMenu("Edycja");
        editMenu.setEnabled(false);
        imageMenu = new JMenu("Obraz");
        imageMenu.setEnabled(false);

//        MenuItems
        fileOpenMenuItem = new JMenuItem("Otwórz obrazek...");
        fileOpenMenuItem.addActionListener(this);

        fileSaveMenuItem = new JMenuItem("Zapisz obrazek...");
        fileSaveMenuItem.addActionListener(this);
        fileSaveMenuItem.setEnabled(false);

        undoMenuItem = new JMenuItem("Cofnij");
        undoMenuItem.setEnabled(false);
        undoMenuItem.addActionListener(this);

        brightnessAlgorithmMenuItem = new JMenuItem("Jasność...");
        brightnessAlgorithmMenuItem.addActionListener(this);

        colorToGrayscaleAlgorithmMenuItem = new JMenuItem("Skala szarości");
        colorToGrayscaleAlgorithmMenuItem.addActionListener(this);

        contrastAlgorithmMenuItem = new JMenuItem("Kontrast...");
        contrastAlgorithmMenuItem.addActionListener(this);

        exposureAlgorithmMenuItem = new JMenuItem("Ekspozycja...");
        exposureAlgorithmMenuItem.addActionListener(this);

        gammaAlgorithmMenuItem = new JMenuItem("Korekcja gamma...");
        gammaAlgorithmMenuItem.addActionListener(this);

        gaussianBlurAlgorithmMenuItem = new JMenuItem("Rozmycie Gaussa...");
        gaussianBlurAlgorithmMenuItem.addActionListener(this);

        hueAlgorithmMenuItem = new JMenuItem("Barwa...");
        hueAlgorithmMenuItem.addActionListener(this);

        reverseAlgorithmMenuItem = new JMenuItem("Odwróć kolory");
        reverseAlgorithmMenuItem.addActionListener(this);

        saturationAlgorithmMenuItem = new JMenuItem("Nasycenie...");
        saturationAlgorithmMenuItem.addActionListener(this);

        thresholdAlgorithmMenuItem = new JMenuItem("Przekształcenie progowe...");
        thresholdAlgorithmMenuItem.addActionListener(this);

//        Zagnieżdżanie komponentów
        fileMenu.add(fileOpenMenuItem);
        fileMenu.add(fileSaveMenuItem);

        editMenu.add(undoMenuItem);

        imageMenu.add(brightnessAlgorithmMenuItem);
        imageMenu.add(colorToGrayscaleAlgorithmMenuItem);
        imageMenu.add(contrastAlgorithmMenuItem);
        imageMenu.add(exposureAlgorithmMenuItem);
        imageMenu.add(gammaAlgorithmMenuItem);
        imageMenu.add(gaussianBlurAlgorithmMenuItem);
        imageMenu.add(hueAlgorithmMenuItem);
        imageMenu.add(reverseAlgorithmMenuItem);
        imageMenu.add(saturationAlgorithmMenuItem);
        imageMenu.add(thresholdAlgorithmMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(imageMenu);

        mainFrame.add(menuBar, BorderLayout.NORTH);

        mainFrame.add(imgScrollPane, BorderLayout.CENTER);

        mainFrame.add(algorithmParametersPanel, BorderLayout.EAST);

        mainFrame.add(footerPanel, BorderLayout.SOUTH);

        mainFrame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == imgSizeSlider && imgSizeSlider.isEnabled()) {
            imgPanel.clear();
            imgPanel.showPleaseWait();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    imgPanel.resizeImage(imgSizeSlider.getValue());

                    imgPanel.displayImage(imgPanel.getDisplayedImg());
                }
            });

        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == imgSizeSlider) {
            imgSizeLabel.setText("Rozmiar podglądu: " + imgSizeSlider.getValue() + "%");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fileOpenMenuItem) {
            fileOpenMenuItemAction();
        } else if (e.getSource() == fileSaveMenuItem) {
            fileSaveMenuItemAction();
        } else if (e.getSource() == brightnessAlgorithmMenuItem) {
            brightnessAlgorithmMenuItemAction();
        } else if (e.getSource() == undoMenuItem) {
            undoMenuItemAction();
        } else if (e.getSource() == colorToGrayscaleAlgorithmMenuItem) {
            colorToGrayscaleAlgorithmMenuItemAction();
        } else if (e.getSource() == contrastAlgorithmMenuItem){
            contrastAlgorithmMenuItemAction();
        } else if (e.getSource() == exposureAlgorithmMenuItem){
            exposureAlgorithmMenuItemAction();
        } else if (e.getSource() == gammaAlgorithmMenuItem){
            gammaAlgorithmMenuItemAction();
        } else if (e.getSource() == gaussianBlurAlgorithmMenuItem){
            gaussianBlurAlgorithmMenuItemAction();
        } else if (e.getSource() == hueAlgorithmMenuItem){
            hueAlgorithmMenuItemAction();
        } else if (e.getSource() == reverseAlgorithmMenuItem){
            reverseAlgorithmMenuItemAction();
        } else if (e.getSource() == saturationAlgorithmMenuItem){
            saturationAlgorithmMenuItemAction();
        } else if (e.getSource() == thresholdAlgorithmMenuItem){
            thresholdAlgorithmMenuItemAction();
        }
    }

    private void brightnessAlgorithmMenuItemAction() {
        JLabel title = new JLabel("Jasność");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, -255, 255, 0);
        JButton acceptButton = new JButton("Akceptuj");
        JButton cancelButton = new JButton("Odrzuć");

        fileMenu.setEnabled(false);
        editMenu.setEnabled(false);
        imageMenu.setEnabled(false);

        imgSizeSlider.setEnabled(false);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    EditableImage copy = imgPanel.getDisplayedImg().clone();
                    copy.acceptProcessor(new Brightness(slider.getValue()));
                    imgPanel.displayImage(copy);
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditableImage copy = imgPanel.getOriginalImg().clone();
                try {
                    copy.acceptProcessor(new Brightness(slider.getValue()));
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
                Originator.getInstance().saveState(imgPanel.getOriginalImg());

                imgPanel.loadImage(copy);
                imgPanel.resizeImage(imgSizeSlider.getValue());
                imgPanel.displayImage(imgPanel.getDisplayedImg());

                algorithmParametersPanel.removeAll();

                fileMenu.setEnabled(true);
                editMenu.setEnabled(true);
                imageMenu.setEnabled(true);

                imgSizeSlider.setEnabled(true);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelImageEditing();
            }
        });


        algorithmParametersPanel.add(title);
        algorithmParametersPanel.add(slider);
        algorithmParametersPanel.add(acceptButton);
        algorithmParametersPanel.add(cancelButton);

        mainFrame.validate();
        mainFrame.repaint();
    }

    private void colorToGrayscaleAlgorithmMenuItemAction() {
        EditableImage copy = imgPanel.getOriginalImg().clone();
        copy.acceptProcessor(new ColorToGrayscale());
        Originator.getInstance().saveState(imgPanel.getOriginalImg());

        imgPanel.loadImage(copy);
        imgPanel.resizeImage(imgSizeSlider.getValue());
        imgPanel.displayImage(imgPanel.getDisplayedImg());
    }

    private void contrastAlgorithmMenuItemAction() {
        JLabel title = new JLabel("Kontrast");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, -250, 250, 0);
        JButton acceptButton = new JButton("Akceptuj");
        JButton cancelButton = new JButton("Odrzuć");

        fileMenu.setEnabled(false);
        editMenu.setEnabled(false);
        imageMenu.setEnabled(false);

        imgSizeSlider.setEnabled(false);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    EditableImage copy = imgPanel.getDisplayedImg().clone();
                    copy.acceptProcessor(new Contrast(Math.pow(2, ((double)slider.getValue())/50.0)));
                    imgPanel.displayImage(copy);
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditableImage copy = imgPanel.getOriginalImg().clone();
                try {
                    copy.acceptProcessor(new Contrast(Math.pow(2, ((double)slider.getValue())/50.0)));
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
                Originator.getInstance().saveState(imgPanel.getOriginalImg());

                imgPanel.loadImage(copy);
                imgPanel.resizeImage(imgSizeSlider.getValue());
                imgPanel.displayImage(imgPanel.getDisplayedImg());

                algorithmParametersPanel.removeAll();

                fileMenu.setEnabled(true);
                editMenu.setEnabled(true);
                imageMenu.setEnabled(true);

                imgSizeSlider.setEnabled(true);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelImageEditing();
            }
        });


        algorithmParametersPanel.add(title);
        algorithmParametersPanel.add(slider);
        algorithmParametersPanel.add(acceptButton);
        algorithmParametersPanel.add(cancelButton);

        mainFrame.validate();
        mainFrame.repaint();
    }

    private void exposureAlgorithmMenuItemAction(){
        JLabel title = new JLabel("Ekspozycja");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, -250, 250, 0);
        JButton acceptButton = new JButton("Akceptuj");
        JButton cancelButton = new JButton("Odrzuć");

        fileMenu.setEnabled(false);
        editMenu.setEnabled(false);
        imageMenu.setEnabled(false);

        imgSizeSlider.setEnabled(false);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    EditableImage copy = imgPanel.getDisplayedImg().clone();
                    copy.acceptProcessor(new Exposure(Math.pow(2, ((double)slider.getValue())/50.0)));
                    imgPanel.displayImage(copy);
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditableImage copy = imgPanel.getOriginalImg().clone();
                try {
                    copy.acceptProcessor(new Exposure(Math.pow(2, ((double)slider.getValue())/50.0)));
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
                Originator.getInstance().saveState(imgPanel.getOriginalImg());

                imgPanel.loadImage(copy);
                imgPanel.resizeImage(imgSizeSlider.getValue());
                imgPanel.displayImage(imgPanel.getDisplayedImg());

                algorithmParametersPanel.removeAll();

                fileMenu.setEnabled(true);
                editMenu.setEnabled(true);
                imageMenu.setEnabled(true);

                imgSizeSlider.setEnabled(true);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelImageEditing();
            }
        });


        algorithmParametersPanel.add(title);
        algorithmParametersPanel.add(slider);
        algorithmParametersPanel.add(acceptButton);
        algorithmParametersPanel.add(cancelButton);

        mainFrame.validate();
        mainFrame.repaint();
    }

    private void gammaAlgorithmMenuItemAction(){
        JLabel title = new JLabel("Gamma");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, -250, 250, 0);
        JButton acceptButton = new JButton("Akceptuj");
        JButton cancelButton = new JButton("Odrzuć");

        fileMenu.setEnabled(false);
        editMenu.setEnabled(false);
        imageMenu.setEnabled(false);

        imgSizeSlider.setEnabled(false);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    EditableImage copy = imgPanel.getDisplayedImg().clone();
                    copy.acceptProcessor(new Gamma(Math.pow(2, ((double)slider.getValue())/50.0)));
                    imgPanel.displayImage(copy);
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditableImage copy = imgPanel.getOriginalImg().clone();
                try {
                    copy.acceptProcessor(new Gamma(Math.pow(2, ((double)slider.getValue())/50.0)));
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
                Originator.getInstance().saveState(imgPanel.getOriginalImg());

                imgPanel.loadImage(copy);
                imgPanel.resizeImage(imgSizeSlider.getValue());
                imgPanel.displayImage(imgPanel.getDisplayedImg());

                algorithmParametersPanel.removeAll();

                fileMenu.setEnabled(true);
                editMenu.setEnabled(true);
                imageMenu.setEnabled(true);

                imgSizeSlider.setEnabled(true);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelImageEditing();
            }
        });


        algorithmParametersPanel.add(title);
        algorithmParametersPanel.add(slider);
        algorithmParametersPanel.add(acceptButton);
        algorithmParametersPanel.add(cancelButton);

        mainFrame.validate();
        mainFrame.repaint();
    }

    private void gaussianBlurAlgorithmMenuItemAction(){
        JLabel title = new JLabel("Rozmycie Gaussa");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
        slider.setSnapToTicks(true);
        JButton acceptButton = new JButton("Akceptuj");
        JButton cancelButton = new JButton("Odrzuć");

        fileMenu.setEnabled(false);
        editMenu.setEnabled(false);
        imageMenu.setEnabled(false);

        imgSizeSlider.setEnabled(false);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    EditableImage copy = imgPanel.getDisplayedImg().clone();
                    copy.acceptProcessor(new GaussianBlur(slider.getValue()));
                    imgPanel.displayImage(copy);
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditableImage copy = imgPanel.getOriginalImg().clone();
                try {
                    copy.acceptProcessor(new GaussianBlur(slider.getValue()));
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
                Originator.getInstance().saveState(imgPanel.getOriginalImg());

                imgPanel.loadImage(copy);
                imgPanel.resizeImage(imgSizeSlider.getValue());
                imgPanel.displayImage(imgPanel.getDisplayedImg());

                algorithmParametersPanel.removeAll();

                fileMenu.setEnabled(true);
                editMenu.setEnabled(true);
                imageMenu.setEnabled(true);

                imgSizeSlider.setEnabled(true);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelImageEditing();
            }
        });


        algorithmParametersPanel.add(title);
        algorithmParametersPanel.add(slider);
        algorithmParametersPanel.add(acceptButton);
        algorithmParametersPanel.add(cancelButton);

        mainFrame.validate();
        mainFrame.repaint();
    }

    private void hueAlgorithmMenuItemAction(){
        JLabel title = new JLabel("Barwa");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        JButton acceptButton = new JButton("Akceptuj");
        JButton cancelButton = new JButton("Odrzuć");

        fileMenu.setEnabled(false);
        editMenu.setEnabled(false);
        imageMenu.setEnabled(false);

        imgSizeSlider.setEnabled(false);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    EditableImage copy = imgPanel.getDisplayedImg().clone();
                    copy.acceptProcessor(new Hue((float) slider.getValue()/100));
                    imgPanel.displayImage(copy);
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditableImage copy = imgPanel.getOriginalImg().clone();
                try {
                    copy.acceptProcessor(new Hue((float) slider.getValue()/100));
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
                Originator.getInstance().saveState(imgPanel.getOriginalImg());

                imgPanel.loadImage(copy);
                imgPanel.resizeImage(imgSizeSlider.getValue());
                imgPanel.displayImage(imgPanel.getDisplayedImg());

                algorithmParametersPanel.removeAll();

                fileMenu.setEnabled(true);
                editMenu.setEnabled(true);
                imageMenu.setEnabled(true);

                imgSizeSlider.setEnabled(true);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelImageEditing();
            }
        });


        algorithmParametersPanel.add(title);
        algorithmParametersPanel.add(slider);
        algorithmParametersPanel.add(acceptButton);
        algorithmParametersPanel.add(cancelButton);

        mainFrame.validate();
        mainFrame.repaint();
    }

    private void reverseAlgorithmMenuItemAction(){
        EditableImage copy = imgPanel.getOriginalImg().clone();
        copy.acceptProcessor(new Reverse());
        Originator.getInstance().saveState(imgPanel.getOriginalImg());

        imgPanel.loadImage(copy);
        imgPanel.resizeImage(imgSizeSlider.getValue());
        imgPanel.displayImage(imgPanel.getDisplayedImg());
    }

    private void saturationAlgorithmMenuItemAction(){
        JLabel title = new JLabel("Nasycenie");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
        JButton acceptButton = new JButton("Akceptuj");
        JButton cancelButton = new JButton("Odrzuć");

        fileMenu.setEnabled(false);
        editMenu.setEnabled(false);
        imageMenu.setEnabled(false);

        imgSizeSlider.setEnabled(false);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    EditableImage copy = imgPanel.getDisplayedImg().clone();
                    copy.acceptProcessor(new Saturation((float) slider.getValue()/100));
                    imgPanel.displayImage(copy);
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditableImage copy = imgPanel.getOriginalImg().clone();
                try {
                    copy.acceptProcessor(new Saturation((float) slider.getValue()/100));
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
                Originator.getInstance().saveState(imgPanel.getOriginalImg());

                imgPanel.loadImage(copy);
                imgPanel.resizeImage(imgSizeSlider.getValue());
                imgPanel.displayImage(imgPanel.getDisplayedImg());

                algorithmParametersPanel.removeAll();

                fileMenu.setEnabled(true);
                editMenu.setEnabled(true);
                imageMenu.setEnabled(true);

                imgSizeSlider.setEnabled(true);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelImageEditing();
            }
        });


        algorithmParametersPanel.add(title);
        algorithmParametersPanel.add(slider);
        algorithmParametersPanel.add(acceptButton);
        algorithmParametersPanel.add(cancelButton);

        mainFrame.validate();
        mainFrame.repaint();
    }

    private void thresholdAlgorithmMenuItemAction(){
        JLabel title = new JLabel("Przekształcenie progowe");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 255, 127);
        JButton acceptButton = new JButton("Akceptuj");
        JButton cancelButton = new JButton("Odrzuć");

        fileMenu.setEnabled(false);
        editMenu.setEnabled(false);
        imageMenu.setEnabled(false);

        imgSizeSlider.setEnabled(false);

        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    EditableImage copy = imgPanel.getDisplayedImg().clone();
                    copy.acceptProcessor(new Threshold(slider.getValue()));
                    imgPanel.displayImage(copy);
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
            }
        });

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditableImage copy = imgPanel.getOriginalImg().clone();
                try {
                    copy.acceptProcessor(new Threshold(slider.getValue()));
                } catch (ImageProcessorException e1) {
                    e1.printStackTrace();
                }
                Originator.getInstance().saveState(imgPanel.getOriginalImg());

                imgPanel.loadImage(copy);
                imgPanel.resizeImage(imgSizeSlider.getValue());
                imgPanel.displayImage(imgPanel.getDisplayedImg());

                algorithmParametersPanel.removeAll();

                fileMenu.setEnabled(true);
                editMenu.setEnabled(true);
                imageMenu.setEnabled(true);

                imgSizeSlider.setEnabled(true);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelImageEditing();
            }
        });


        algorithmParametersPanel.add(title);
        algorithmParametersPanel.add(slider);
        algorithmParametersPanel.add(acceptButton);
        algorithmParametersPanel.add(cancelButton);

        mainFrame.validate();
        mainFrame.repaint();
    }

    private void cancelImageEditing() {
        algorithmParametersPanel.removeAll();

        imgPanel.displayImage(imgPanel.getDisplayedImg());

        fileMenu.setEnabled(true);
        editMenu.setEnabled(true);
        imageMenu.setEnabled(true);

        imgSizeSlider.setEnabled(true);

        mainFrame.validate();
        mainFrame.repaint();
    }

    private void fileOpenMenuItemAction() {
        imgSizeSlider.setEnabled(false);

        ChooseFileWindow chooseFileWindow = new ChooseFileWindow(mainFrame);

        if (chooseFileWindow.getFileChoiceResult() == JFileChooser.APPROVE_OPTION) {
            imgPanel.clear();
            imgPanel.showPleaseWait();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    ImageInput.getSingleInstance().locateFile(chooseFileWindow.getSelectedFile());
                    try {
                        ImageInput.getSingleInstance().readImageFromFile();
                    } catch (IOException ioexception) {
                        ioexception.printStackTrace();
                    }

                    imgPanel.loadImage(ImageDataInterpreter.convertFromBuffered(ImageInput.getSingleInstance().getImage()));
                    imgPanel.displayImage(imgPanel.getDisplayedImg());

                }
            });

        }
    }

    private void fileSaveMenuItemAction() {
        SaveFileWindow saveFileWindow = new SaveFileWindow(mainFrame);

        if (saveFileWindow.getFileChoiceResult() == JFileChooser.APPROVE_OPTION) {
            ImageOutput.getSingleInstance().locateFile(saveFileWindow.getSelectedFile());
            ImageOutput.getSingleInstance().setImage(ImageDataInterpreter.convertFromEditableImage(imgPanel.getOriginalImg()));
            try {
                ImageOutput.getSingleInstance().saveImageToFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private void undoMenuItemAction() {
        imgPanel.loadImage(Originator.getInstance().returnToPrevious());
        imgPanel.resizeImage(imgSizeSlider.getValue());
        imgPanel.displayImage(imgPanel.getDisplayedImg());
    }



    @Override
    public void imagePanelUpdate(String notificationCode) {
        if (notificationCode.equals("loadImage")) {
            fileSaveMenuItem.setEnabled(true);
            imgSizeSlider.setEnabled(true);
            imgSizeSlider.setValue(100);

            imageMenu.setEnabled(true);
            editMenu.setEnabled(true);
        }
    }

    @Override
    public void originatorStateChanged() {
        undoMenuItem.setEnabled(!Originator.getInstance().isStackEmpty());
    }
}
