import java.awt.*;

public class ToggleButton extends Button{
    private Color color;
    public ToggleButton(int x, int y, int width, int height, Image i_depressed, Image i_pressed, Color color) {
        super(x, y, width, height, i_depressed, i_pressed, Color.GRAY);
        this.color = color;
    }

    public ToggleButton(int x, int y, int width, int height) {
        super(x,y,width,height);
    }

    public Boolean IsPressed()
    {
        return pressed;
    }

    public void SetPressed(boolean pressed)
    {
        this.pressed = pressed;
    }


    public void clicked(int x, int y) {
        if(x > this.x && x < this.x + width && y > this.y && y < this.y + height)
        {
            if (!IsPressed())
            {
                current_image = image_pressed;
                SetPressed(true);
                //System.out.println(1);
            }
            else {
                current_image = image_depressed;
                SetPressed(false);
                //System.out.println(2);
            }

        }

    }
    @Override
    public Color getColor() {
        return color;
    }
}