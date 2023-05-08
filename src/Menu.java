import java.awt.*;

public class Menu {
int x;
int y;
protected int width;
protected int height;
protected Color color;

    public Menu(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }


    public void paint(Graphics g){
        g.setColor(color);
        g.drawRect(x, y, width, height);
    }


}
