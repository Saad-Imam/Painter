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
    public Button selected;
    public Stack<Shape> selectedStack;
    Board b;



    public LayersToolbar(int x, int y, int width, int height,Image pressed, Image depressed, Color color, Board b) {
        super(x, y, width, height, depressed, pressed, color);
        this.b = b;
        this.depressed = depressed.getScaledInstance(300,50,Image.SCALE_FAST);
        this.pressed = pressed.getScaledInstance(300,50,Image.SCALE_FAST);
        load_buttons();
        add();
        this.color = color;
        if (b.layer.size() == 0)
            b.layer.add(new Stack<>());

        selected = layers.get(0);
        selectedStack = b.layer.get(0);
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
        Layer newLayer = new Layer(x, down.y + down.height, width, 50, "Layer " + num,depressed,pressed);
        b.layer.add(new Stack<Shape>());
        System.out.println(b.layer.size());
        layers.add(newLayer);
        selected = layers.get(layers.size() - 1);
        selectedStack = b.layer.get(b.layer.size() - 1);

        layer = newLayer;
        layer.current_image = pressed;
        num++;
        for (Layer layer : layers)
            layer.y += 60;
        deselect();
    }


    public void remove () {
        if (layer != null) {
            if (layers.size() > 1) {
                int i = layers.indexOf(selected);
                layers.remove(i);
                b.layer.remove(i);
                for (int j = i; j < layers.size(); j++)
                    layers.get(j).y += 32;
                if (i > 0) {
                    selected = layers.get(i - 1);
                    selectedStack = b.layer.get(i - 1);
                } else {
                    selected = layers.get(i);
                    selectedStack = b.layer.get(i);
                }

                // assigning a current layer
                if (!layers.isEmpty()) {
                    if (i == layers.size()) {
                        layer = layers.get(i - 1); // if top layer removed
                    } else {
                        layer = layers.get(i); // middle layers
                    }
                    layer.current_image = pressed;
                }
            }

        }
    }
        //layerIndex--;


    public void moveUp() {
        if (layers.size() > 1) {
            for (int i = 0; i < b.layer.size() - 1; i++) {
                if (b.layer.get(i) == selectedStack) {
                    Stack temp = b.layer.get(i);
                    b.layer.set(i, b.layer.get(i + 1));
                    b.layer.set(i + 1, temp);
                    break;
                }
            }
            for (int i = 0; i < layers.size() - 1; i++) {
                if (layers.get(i) == selected) {
                    layers.get(i).y -= 32;
                    layers.get(i + 1).y += 32;

                    Layer button = layers.get(i);
                    layers.set(i, layers.get(i + 1));
                    layers.set(i + 1, button);
                    return;
                }
            }
        }
    }

    public void moveDown() {
        if (layers.size() > 1) {
            for (int i = 1; i < b.layer.size(); i++) {
                if (b.layer.get(i) == selectedStack) {
                    Stack temp = b.layer.get(i);
                    b.layer.set(i, b.layer.get(i - 1));
                    b.layer.set(i - 1, temp);
                    break;
                }
            }
            for (int i = 1; i < layers.size(); i++) {
                if (layers.get(i) == selected) {
                    layers.get(i).y += 32;
                    layers.get(i - 1).y -= 32;

                    Layer button = layers.get(i);
                    layers.set(i, layers.get(i - 1));
                    layers.set(i - 1, button);
                    return;
                }
            }
        }
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
//            raise();
            moveUp();
        } else if (down.IsClicked(x, y)) {
            //lower();
            moveDown();
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

        Tooltip.getCoords(x,y);
        String info = "";

        if(x > add.x && x < add.x + add.width  && y > add.y && y < add.y + add.height)
            info = "Add Layer";
        else if(x > remove.x && x < remove.x + remove.width  && y > remove.y && y < remove.y + remove.height)
            info = "Remove Layer";
        else if(x > up.x && x < up.x + up.width  && y > up.y && y < up.y + up.height)
            info = "Raise Layer";
        else if(x > down.x && x < down.x + down.width  && y > down.y && y < down.y + down.height)
            info = "Lower Layer";

        Tooltip.getInfo(info);
    }
}
