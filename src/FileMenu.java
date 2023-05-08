import javax.swing.*;
import java.awt.*;

public class FileMenu extends Menu{

    private ImageIcon New = new ImageIcon("src/Images/myimage_new.jpg");
    private ImageIcon open = new ImageIcon("src/Images/myimage_open.png");
    private ImageIcon save= new ImageIcon("src/Images/myimage_save.png");
    private ImageIcon file = new ImageIcon("src/Images/myimage_file.png");
    private Button [] array;
    private Color color;


    public FileMenu(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        this.color = color;
    }

    public boolean isClicked(int x, int y)
    {
        if(x > this.x && x < this.x + width && y > this.y && y < this.y + height)
        {
            Button new_ = new Button(this.x, this.y + height + 5, New.getIconWidth(), New.getIconHeight(), New.getImage(), New.getImage(), color);
            Button open_ = new Button(this.x, new_.y + New.getIconHeight() + 5, open.getIconWidth(), open.getIconHeight(), open.getImage(), open.getImage(), color);
            Button save_ = new Button(this.x, open_.y + open.getIconHeight() + 5, save.getIconWidth(), save.getIconHeight(), save.getImage(), save.getImage(), color);
            array = new Button[]{new_, open_, save_};
            return true;
        }
        else return false;
    }

    public Image GetImage()
    {
        return file.getImage();
    }

    public Button[] getArray() {
        return array;
    }

    public void paint(Graphics g){
        g.setColor(color);
        g.drawRect(x, y, file.getIconWidth(), file.getIconHeight());
        g.drawRect(x, array[0].y, New.getIconWidth(), New.getIconHeight());
        g.drawRect(x, array[1].y, open.getIconWidth(), open.getIconHeight());
        g.drawRect(x, array[2].y, save.getIconWidth(), save.getIconHeight());
    }
    public Color getColor() {
        return color;
    }
}
