import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ColorsToolbar extends Toolbar{

    static ArrayList<Button> Buttons = new ArrayList<>();
    Button selectedColor;
    Gradient gradient;
    Color currentColor;
    static Button custom1;
    static Button custom2;
    static Button custom3;
    static Button custom4;
    static Button custom5;
    static Button stroke;
    static Button fill;
    private Color toolbar_color;
    private Grid grid;
    private StrokeSize strokeSize;
    boolean flag;
    Button [] array3;
    Image pressed;
    Image depressed;
    Image gradientPic= new ImageIcon("src/Images/gradient.png").getImage();
    Image strokePic = new ImageIcon("src/Images/stroke size.jpg").getImage();

    public ColorsToolbar(int x, int y, int width, int height, Image pressed, Image depressed, Color color) {
        super(x, y, width, height, pressed, depressed, color);
        this.toolbar_color = color;
        this.pressed = pressed;
        this.depressed = depressed;
        load_buttons();
    }


    private void load_buttons(){
        Button black = new  Button(x +10,y+10,30,30,depressed,pressed,Color.black);
        Button yellow = new  Button(black.x + 30 + 20, black.y,30,30,depressed,pressed,Color.yellow);
        Button pink = new  Button(yellow.x + 30 + 20, yellow.y, 30,30,depressed,pressed,Color.pink);
        Button red = new  Button(pink.x + 30 + 20, pink.y, 30,30,depressed,pressed,Color.red);
        Button blue = new  Button(red.x + 30 + 20, red.y, 30,30,depressed,pressed,Color.blue);
        custom1 = new Button(black.x, black.y + 30 + 20,30,30,depressed,pressed,Color.white);
        custom2 = new Button(yellow.x, yellow.y + 30 + 20,30,30,depressed,pressed,Color.white);
        custom3 = new Button(pink.x, pink.y + 30 + 20,30,30,depressed,pressed,Color.white);
        custom4 = new Button(red.x, red.y + 30 + 20,30,30,depressed,pressed,Color.white);
        custom5 = new Button(blue.x, blue.y + 30 + 20,30,30,depressed,pressed,Color.white);

        Buttons.add(black);
        Buttons.add(yellow);
        Buttons.add(pink);
        Buttons.add(red);
        Buttons.add(blue);
        Buttons.add(custom1);
        Buttons.add(custom2);
        Buttons.add(custom3);
        Buttons.add(custom4);
        Buttons.add(custom5);

        fill = new Button(blue.x + blue.width + 15, blue.y , pressed.getWidth(null) , pressed.getHeight(null) , depressed, pressed, Color.WHITE);
        stroke = new Button(fill.x + fill.getWidth() + 10, fill.y , pressed.getWidth(null) , pressed.getHeight(null), depressed, pressed, Color.WHITE);
        gradient = new Gradient(stroke.x + stroke.width + 10,stroke.y , gradientPic.getWidth(null), gradientPic.getHeight(null),gradientPic,gradientPic);
        selectedColor = fill;
        Buttons.add(fill);
        Buttons.add(stroke);
        grid = new Grid(gradient.x + gradient.width + 10 , gradient.y, pressed.getWidth(null), pressed.getHeight(null), depressed, pressed, toolbar_color);
        strokeSize = new StrokeSize(grid.x + grid.width + 10, grid.y, strokePic.getWidth(null),strokePic.getHeight(null), strokePic, strokePic, toolbar_color);
        array3 = new Button[]{ gradient, grid, strokeSize};


    }

    public static void addColor (Color color, int buttonNumber)
    {
        switch (buttonNumber) {
            case 1 -> custom1.color = color;
            case 2 -> custom2.color = color;
            case 3 -> custom3.color = color;
            case 4 -> custom4.color = color;
            case 5 -> custom5.color = color;

        }
    }


    public void paint(Graphics g){
        super.paint(g);
        g.setColor(toolbar_color);
        g.fillRect(x, y, width, height);

        if (!flag)
        {
            g.setColor(new Color(0, 0, 150));
            g.fillRect(fill.x-5,fill.y-5,75,75);
        }
        else
        {
            g.setColor(new Color(0,0,150));
            g.fillRect(stroke.x-5,stroke.y-5,75,75);
        }

        for (Button button : Buttons) {
            button.color(g);
            g.drawRect(button.x, button.y, button.getWidth()-4, button.getHeight()-4);
        }

       for(Button button: array3){
           g.drawImage(button.GetImage(), button.x, button.y, null);
           button.paint(g);
       }
       g.setColor(Color.WHITE);
       Font font = new Font("Serif", Font.BOLD, 15);
       g.setFont(font);
       g.drawString("Fill", fill.x + 20, fill.y + fill.width + 15);
       g.drawString("Stroke", stroke.x + 12, stroke.y + stroke.width + 15);
       g.drawString("Gradient",gradient.x + 2, gradient.y + gradient.width + 20);
       g.drawString("Grid size",grid.x + 2, grid.y + grid.width + 20);
       g.drawString("Stroke size",strokeSize.x + 2, strokeSize.y + strokeSize.width + 20);

    }
    @Override
    public void Clicked(int x, int y) {

        if (fill.IsClicked(x,y))
        {

            selectedColor = fill;
            flag = false;
        }

        else if (stroke.IsClicked(x,y)) {

            selectedColor = stroke;
            flag = true;
        }
        else if (gradient.IsClicked(x,y)){
            gradient.clicked(x,y);
        }
        else if (grid.IsClicked(x,y)){
            grid.clicked(x,y);

        }
        for (int i = 0; i < Buttons.size(); i++) {
            if (Buttons.get(i).IsClicked(x,y))
            {
                currentColor =  Buttons.get(i).getColor();
                selectedColor.color = currentColor;

                for (Button button1 : Buttons) {
                    if (button1 != fill) {
                        button1.pressed = false;
                    }
                }
            }
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
        String info = "";
        for (Button button : Buttons) {
            if ( x > button.x && x < button.x + button.width && y >button.y && y < button.y + button.height ){
                info = "R: " + button.getColor().getRed() + " G: " + button.getColor().getGreen() + " B: " + button.getColor().getBlue();
            }
        }
        if ( x > gradient.x && x < gradient.x + gradient.width && y >gradient.y && y < gradient.y + gradient.height ){
            info = "Gradient";
        }
        else if ( x > grid.x && x < grid.x + grid.width && y >grid.y && y < grid.y + grid.height ){
            info = "Grid size";
        }
        else if ( x > strokeSize.x && x < strokeSize.x + strokeSize.width && y >strokeSize.y && y < strokeSize.y + strokeSize.height ){
            info = "Stroke size";

        }

        Tooltip.getInfo(info);
    }
}
