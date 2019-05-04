package EditableImage;

/**
 * Pixel to pomocnicza klasa przechowująca dane o barwie piksela w standardowym modelu sRGB
 *
 * @see EditableImage
 */
public class Pixel {
    private byte r, g, b;

    public Pixel(int r, int g, int b) {
        r -= 128;
        g -= 128;
        b -= 128;
        this.r = (byte) r;
        this.g = (byte) g;
        this.b = (byte) b;
    }

    @Override
    public Pixel clone() {
        return new Pixel(r + 128, g + 128, b + 128);
    }

    public int getR() {
        return r + 128;
    }

    public void setR(int r) {
        r -= 128;
        this.r = (byte) r;
    }

    public int getG() {
        return g + 128;
    }

    public void setG(int g) {
        g -= 128;
        this.g = (byte) g;
    }

    public int getB() {
        return b + 128;
    }

    public void setB(int b) {
        b -= 128;
        this.b = (byte) b;
    }
}
