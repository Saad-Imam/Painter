import javax.swing.*;
import java.awt.*;

public class GradientWindow {

    private GradientWindow () {}
    private static GradientWindow instance = null;

    private static Image gradient = new ImageIcon("src/Images/gradient.png").getImage().getScaledInstance(380,250,Image.SCALE_FAST);
    int r;
    int g;
    int b;
    Button custom1 = new Button(480,370,30,30,gradient,gradient,ColorsToolbar.custom1.color);
    Button custom2 = new Button(480,410,30,30,gradient,gradient,ColorsToolbar.custom2.color);
    Button custom3 = new Button(480,450,30,30,gradient,gradient,ColorsToolbar.custom3.color);
    Button custom4 = new Button(480,490,30,30,gradient,gradient,ColorsToolbar.custom4.color);
    Button custom5 = new Button(520,370,30,30,gradient,gradient,ColorsToolbar.custom5.color);

    Button[] customButtons = {custom1,custom2,custom3,custom4,custom5};

    Button preview;
    int counter = 1;
    private Color selectedColor = Color.white;
    private Button selectedButton = custom1;
    private boolean drag;
    int x;
    int y;

    public static GradientWindow getInstance() {
        if (instance == null) {
            instance = new GradientWindow();
        }
        return instance;
    }

    public void paint (Graphics g) {
        if (Gradient.window)
        {
            g.setColor(Color.white);
            g.fillRect(349,49,502,502);
            g.setColor(new Color(44, 44, 44));
            g.fillRect(350,50,500,500);

            g.drawImage(gradient,400,100,null);
            if (drag)
            {
                g.setColor(Color.black);
                g.drawString("*",x,y);
            }
            // add button
            g.setColor(Color.white);
            g.fillRect(719,489,82,32);
            g.setColor(new Color(10, 10, 10));
            g.fillRect(720,490,80,30);
            Font f = new Font("Serif", Font.BOLD, 15);
            g.setFont(f);
            g.setColor(Color.white);
            g.drawString("ADD",740,510);

            // exit button
            g.setColor(Color.RED);
            g.fillRect(800,50,50,30);
            Font f1 = new Font("Serif", Font.BOLD, 25);
            g.setFont(f1);
            g.setColor(Color.white);
            g.drawString("Color Pallet",360,74);
            g.drawString("X",817,74);

            // highlight
            g.setColor(new Color(0,0,150));
            g.fillRect(selectedButton.x-2,selectedButton.y-2,34,34);

            preview = new Button(400,370,70,150,gradient,gradient,selectedColor);
            preview.color(g);

            for (Button customs : customButtons)
                customs.color(g);
        }
    }

    public void Clicked (int x, int y)
    {
        for (int i = 0; i < customButtons.length; i++) {
            if (customButtons[i].IsClicked(x,y))
            {
                selectedButton = customButtons[i];
                counter = i+1;

                for (Button customs : customButtons) {
                    if (customs != selectedButton) {
                        customs.pressed = false;
                    }
                }
            }

        }

        //close
        if (x > 800 && x < 850 && y > 50 && y < 80)
            Gradient.window = false;

        // add
        if (x > 720 && x < 800 && y > 490 && y < 520)
        {
            ColorsToolbar.addColor(selectedColor, counter);
            selectedButton.color = selectedColor;
        }
    }

    public void Dragged(int x, int y) {
        if (Gradient.window) {
            this.x = x; this.y = y;
            if (x > 400 && x < 800 && y >= 100 && y < 350) {
                drag = true;

                int x1 = x-400;
                if (x1 <= 400/6)
                {
                    r = 250;
                    g = Math.min(x1 * 5, 255);  // limit to 255
                    b = 0;
                }
                else if (x1 <= 400/3)
                {
                    r = Math.max(250 - (x1 - 150) * 5, 0);  // limit to 0
                    g = 250;
                    b = 0;
                }
                else if (x1 <= 400/2)
                {
                    r = 0;
                    g = 250;
                    b = Math.min((x1 - 100) * 5, 255);  // limit to 255
                }
                else if (x1 <= 2 * 400/3)
                {
                    r = 0;
                    g = Math.max(250 - (x1 - 150) * 5, 0);  // limit to 0
                    b = 250;
                }
                else if (x1 <= 5 * 400/6)
                {
                    r = Math.min((x1 - 200) * 5, 255);  // limit to 255
                    g = 0;
                    b = 250;
                }
                else if (x1 <= 400)
                {
                    r = 250;
                    g = 0;
                    b = Math.max(250 - (x1 - 250) * 5, 0);  // limit to 0
                }


                int num = y * Math.abs(r - 125) / 250;

                // limit r value to 0-255
                if (r > 125)
                {
                    int newR = r - num;
                    if (newR < 0)
                        r = 0;
                    else
                        r = Math.min(newR, 255);
                }
                else
                {
                    int newR = r + num;
                    if (newR > 255)
                        r = 255;
                    else
                        r = Math.max(newR, 0);
                }
                if (g > 125)
                {
                    int newG = g - num;
                    if (newG < 0)
                        g = 0;
                    else
                        g = Math.min(newG, 255);
                }
                else
                {
                    int newG = g + num;
                    if (newG > 255)
                        g = 255;
                    else
                        g = Math.max(newG, 0);
                }


                if (b > 125)
                {
                    int newB = b - num;
                    if (newB < 0)
                        b = 0;
                    else
                        b = Math.min(newB, 255);
                }
                else
                {
                    int newB = b + num;
                    if (newB > 255)
                        b = 255;
                    else
                        b = Math.max(newB, 0);
                }
                selectedColor = new Color(r, g, b);
            }
            else drag = false;
        }
    }
}

