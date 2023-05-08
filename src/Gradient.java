import java.awt.*;

public class Gradient extends ToggleButton {
    static boolean window;
    public Gradient(int x, int y, int width, int height, Image i_depressed, Image i_pressed) {
        super(x, y, width, height, i_depressed, i_pressed, Color.GRAY);
    }
    @Override
    public void clicked(int x, int y) {
        if(x > this.x && x < this.x + width && y > this.y && y < this.y + height)
            window = true;
    }
}

