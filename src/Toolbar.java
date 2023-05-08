import javax.swing.*;
import java.awt.*;

abstract class Toolbar implements ToolBarListener{
    int x;
    int y;
    int width;
    int height;
    private Button button;
    Button[] array1;
    ImageIcon [] array2;
    private Color color;


    public Toolbar(int x, int y , int width, int height, Image not_pres, Image pres, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;

    }
    public Toolbar(int x, int y, int width, int height, int num, ImageIcon [] array2, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.array1 = new Button[num];
        this.array2 = array2;
    }
    void load_array( ImageIcon[] array2){
        button = new Button(x+15, y + 20, array2[0].getIconWidth(), array2[0].getIconHeight(), array2[0].getImage(), array2[0].getImage(), color);
        array1[0] = button;
        for (int i = 1; i < array1.length ; i++) {
            array1[i]  = new Button(array1[i-1].x + array1[i-1].getWidth() +30, y + 15, array2[i].getIconWidth(),array2[i].getIconHeight(), array2[i].getImage(), array2[i].getImage(), color);
        }
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.drawRect(x, y, width, height);

    }


}