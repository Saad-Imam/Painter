import java.awt.*;

public class Grid extends ToggleButton {
     static int size = 0;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public Grid(int x, int y, int width, int height, Image i_depressed, Image i_pressed, Color color) {
        super(x, y, width, height, i_depressed, i_pressed, color);
    }


    public void clicked(int x, int y) {
        if(x > this.x && x < this.x + width && y > this.y && y < this.y + height)
        {
            if (size == 0)
                size += 2;
            else if (size == 64)
                size = 0;
            else
                size *= 2;
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        g.fillRect(x,y,image_depressed.getWidth(null), image_depressed.getHeight(null));



        g.setColor(Color.BLACK);
        g.drawRect(x,y,image_depressed.getWidth(null), image_depressed.getHeight(null));

        Font font = new Font("Arial",Font.PLAIN,30);
        g.setFont(font);
//        if (size == 0)
//            g.drawString("0",x+5,y+40);
//        else
        {
            FontMetrics metrics = g.getFontMetrics();
            int stringWidth = metrics.stringWidth(String.valueOf(size));
            int stringHeight = metrics.getHeight();
            int centerX = x + 30 - stringWidth / 2;
            int centerY = y + 20 + stringHeight / 2;
            g.drawString(String.valueOf(size),centerX,centerY);
        }


        if (size > 0) {
            g.setColor(new Color(92, 98, 116));
            int xStart = 0;
            int xEnd = (int)(screenSize.getWidth()) - 350;
            int y = 105;
            while (y <= screenSize.height) {
                g.drawLine(xStart, y, xEnd, y);
                y += size;
            }
            int yStart = 105;
            int yEnd =  screenSize.height;
            int x = 0;
            while (x <= (int)(screenSize.getWidth()) - 350) {
                g.drawLine(x, yStart, x, yEnd);
                x += size;
            }
        }
    }
}
