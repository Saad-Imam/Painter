import javax.swing.*;
import java.awt.*;

public class Window {
    int x;
    int y;
    private int width;
    private int height;
    private Color color;

    public Window(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

    }

    public void Clicked(int x, int y)
    {
        if(x > this.x + width -28 && x < this.x + width -28 + 50 && y > this.y && y < 30)
        {
           Board.open_window = false;
        }

    }

    public void paint(Graphics g){
        if (Board.open_window ) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
            g.setColor(Color.RED);
            g.fillRect(x + width -28, y, 30, 30);
            g.setColor(Color.WHITE);
            Font f1 = new Font("Serif", Font.BOLD, 25);
            g.setFont(f1);
            g.drawString("X", x+width - 20, y+20);
        }
    }


    private void setColor() {
        this.color = Color.WHITE;
    }
}
