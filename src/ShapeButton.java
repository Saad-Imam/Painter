import java.awt.*;

public class ShapeButton extends ToggleButton {
    public String shapeName;
    public ShapeButton(int x, int y, int width, int height, Image i_depressed, Image i_pressed, String shapeName, Color color) {
        super(x, y, width, height, i_depressed, i_pressed, color);

        this.shapeName = shapeName;
    }


}
