import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class LayersToolbar extends Toolbar{

    private Button add;
    private Button remove;
    private Button up;
    private Button down;
    int num;
    Button [] array = new Button[4];
    Image pressed;
    Image depressed;
    static LinkedList<Layer> layers = new LinkedList<>();
    private Layer layer;
    Color color;



    public LayersToolbar(int x, int y, int width, int height,Image pressed, Image depressed, Color color) {
        super(x, y, width, height, depressed, pressed, color);
        this.depressed = depressed.getScaledInstance(300,50,Image.SCALE_FAST);
        this.pressed = pressed.getScaledInstance(300,50,Image.SCALE_FAST);
        load_buttons();
        add();
        this.color = color;
    }

    void load_buttons(){
        ImageIcon up1 = new ImageIcon("src/Images/uparrow.png");
        ImageIcon down1 = new ImageIcon("src/Images/downarrow.png");
        ImageIcon add1 = new ImageIcon("src/Images/myimage_add.png");
        ImageIcon remove1 = new ImageIcon("src/Images/myimage_remove.png");

        add = new Button(x+20, y+20, add1.getIconWidth() + 10, add1.getIconHeight(), add1.getImage(), add1.getImage(), color );
        remove = new Button(add.x + add.getWidth() + 20, add.y, remove1.getIconWidth() +10, remove1.getIconHeight(), remove1.getImage(), remove1.getImage(), color);
        up = new Button(add.x, add.y + 50, up1.getIconWidth(), up1.getIconHeight(), up1.getImage(), up1.getImage(), color);
        down = new Button(up.x + up.getWidth() + 20, up.y, down1.getIconWidth(), down1.getIconHeight(), down1.getImage(), down1.getImage(), color);
        array = new Button[]{add, remove, up, down};

    }

    public void paint(Graphics g){
        super.paint(g);
        g.setColor(color);
        g.fillRect(x, y, width, height);

        for (Button b : array) {
            b.paint(g);
        }

        for (Layer layer : layers) {
            layer.paint(g);
        }
    }

    public void add () {
        if (layers.size() == 0)
            num = 1;
        Layer newLayer = new Layer(x, 160, width, 50, "Layer " + num,depressed,pressed);

        layers.add(newLayer);
        layer = newLayer;
        layer.current_image = pressed;
        num++;
        for (Layer layer : layers)
            layer.y += 60;
        deselect();
    }


    public void remove () {
        if (layer != null) {
            int index = layers.indexOf(layer);
            System.out.println(index);
            if (layers.size() > 1) // last layer not removed
            {
                layers.remove(layer);
                layer = null;

                for (int i = index-1; i >= 0; i--)
                {
                    Layer layer = layers.get(i);
                    if (layer.y >= 250)
                        layer.y -= 60;
                }

                // assigning a current layer
                if (!layers.isEmpty()) {
                    if (index == layers.size()) {
                        layer = layers.get(index - 1); // if top layer removed
                    } else {
                        layer = layers.get(index); // middle layers
                    }
                        layer.current_image = pressed;
                }
            }
        }
        //layerIndex--;
    }

    public void raise () {
        if (layer != null) {
            int currentIndex = layers.indexOf(layer);
            if (currentIndex < layers.size() - 1) {
                Layer nextLayer = layers.get(currentIndex + 1);
                int temp = layer.y;
                layer.y = nextLayer.y;
                nextLayer.y = temp;
                layers.set(currentIndex, nextLayer);
                layers.set(currentIndex + 1, layer);
                layer = nextLayer;
            }
        }
    }
    public void lower () {
        if (layer != null) {
            int currentIndex = layers.indexOf(layer);
            if (currentIndex > 0) {
                Layer prevLayer = layers.get(currentIndex - 1);
                int temp = layer.y;
                layer.y = prevLayer.y;
                prevLayer.y = temp;
                layers.set(currentIndex, prevLayer);
                layers.set(currentIndex - 1, layer);
                layer = prevLayer;
            }
        }
    }

    private void deselect ()
    {
        for (Layer l : layers) {
            if (l != layer) {
                l.pressed = false;
                l.current_image = depressed;
            }
        }
    }
    @Override
    public void Clicked(int x, int y) {
        for (Layer temp : layers) {
            temp.clicked(x, y);
            if (temp.pressed)
            {
                layer = temp;
                deselect();

            }

        }
    }

    @Override
    public void Pressed(int x, int y) {

        if (add.IsClicked(x, y)) {
            add();
        } else if (remove.IsClicked(x, y)) {
            remove();
        } else if (up.IsClicked(x, y)) {
            raise();
        } else if (down.IsClicked(x, y)) {
            lower();
        }
    }
    @Override
    public void Released(int x, int y) {
    add.IsReleased(x,y);
    remove.IsReleased(x,y);
    up.IsReleased(x,y);
    down.IsReleased(x,y);
    }

    @Override
    public void Moved(int x, int y) {

    }
}
