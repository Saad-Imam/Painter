import java.awt.*;

public class Layer extends ToggleButton{
    int x;
    int y;
    int width;
    int height;
    boolean visible;
    String name;

    public Layer(int x, int y, int width, int height, String name, Image i_depressed, Image i_pressed ) {
        super(x, y, width, height, i_depressed, i_pressed, Color.GRAY);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
        visible = true;
        current_image = image_depressed;
    }

    public void paint (Graphics g)
    {
        if (visible)
        {
            g.drawImage(current_image,x,y,null);
            //g.setColor(Color.BLACK);
            //g.fillRect(x,y,width,height);

            FontMetrics fontMetrics = g.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(name);
            int textX = x + (width - textWidth) / 2;
            int textY = y + 5 + (height/2);
            g.setColor(Color.gray);
            g.drawString(name, textX, textY);
        }
    }

    @Override
    public void clicked(int x, int y) {
        if(x > this.x && x < this.x + width && y > this.y && y < this.y + height)
        {
            if (!IsPressed())
            {
                current_image = image_pressed;
                SetPressed(true);
            }
            else {
                current_image = image_depressed;
                SetPressed(false);
            }
        }
    }

}
