import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

public class ShapesToolbar extends Toolbar{

    Button [] array1;
    ImageIcon[] array2;
    Color color;
    public ShapesToolbar(int x, int y, int width, int height, int num, ImageIcon[] array2, Color color) {
        super(x, y, width, height, num, array2, color);
        super.load_array(array2);
        this.array1 = super.array1;
        this.array2 = super.array2;
        this.color = color;
    }

    public void paint(Graphics g){

        super.paint(g);
        for (int i = 0; i < 6; i++) {
            g.drawRect(array1[i].x - 5, array1[i].y - 5  , array2[i].getIconWidth() + 10, array2[i].getIconHeight() + 10);
        }

    }
    @Override
    public void Clicked(int x, int y) {
        for (int i = 0; i < 6; i++) {
            array1[i].IsClicked(x,y);
        }
    }

    @Override
    public void Pressed(int x, int y) {

    }

    @Override
    public void Released(int x, int y) {

    }

    @Override
    public void Moved(int x, int y) {
        Tooltip.getCoords(x,y);
        String[] shapes = {"Right Angled Triangle", "Equilateral Triangle","Rectangle", "Circle","Pentagram","Hexagon"};
        String info = "";
        for (int i = 0; i < 6; i++) {
            if ( x > array1[i].x && x < array1[i].x + array1[i].width && y >array1[i].y && y < array1[i].y +array1[i].height ){
                    info = shapes[i];
            }
        }
        Tooltip.getInfo(info);

    }
}
