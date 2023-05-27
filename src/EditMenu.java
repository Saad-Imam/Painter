import javax.swing.*;
import java.awt.*;

public class EditMenu extends Menu implements ToolBarListener{

    ImageIcon edit = new ImageIcon("src/Images/myimage_edit.png");
    private ImageIcon undo = new ImageIcon("src/Images/myimage_undo.png");
    private ImageIcon redo= new ImageIcon("src/Images/myimage_redo.png");
    private boolean pressed;
    private Button [] array;
    private Color color;

    public EditMenu(int x, int y, int width, int height, Color color) {
        super(x, y, width, height, color);
        this.color = color;
    }

    public boolean isClicked(int x, int y)
    {
        if(x > this.x && x < this.x + width && y > this.y && y < this.y + height)
        {
            pressed = true;
            Button undo_ = new Button(this.x, this.y + height + 5, undo.getIconWidth(), undo.getIconHeight(), undo.getImage(), undo.getImage(),color);
            Button redo_ = new Button(this.x, undo_.y + undo.getIconHeight() + 5, redo.getIconWidth(), redo.getIconHeight(), redo.getImage(), redo.getImage(), color);
            array = new Button[]{undo_, redo_};
            return true;
        }
        else return false;
    }

    public Button[] getArray() {
        return array;
    }
    public Image GetImage()
    {
        return edit.getImage();
    }
    public void paint(Graphics g){
        g.setColor(color);
        g.drawRect(x, y, edit.getIconWidth(), edit.getIconHeight());
        g.drawRect(x, array[0].y, undo.getIconWidth(), undo.getIconHeight());
        g.drawRect(x, array[1].y, redo.getIconWidth(), redo.getIconHeight());

    }

    public Color getColor() {
        return color;
    }

    @Override
    public void Clicked(int x, int y) {}
    @Override
    public void Pressed(int x, int y) {}
    @Override
    public void Released(int x, int y) {}
    @Override
    public void Moved(int x, int y) {
        Tooltip.getCoords(x,y);
        String info = "";
        if (x > this.x && x < this.x + this.width && y >this.y && y < this.y + this.height ){
            info = "Edit";
        }
        Tooltip.getInfo(info);

    }
}
