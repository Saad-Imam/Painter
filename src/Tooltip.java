import java.awt.*;
public class Tooltip{
private static Tooltip instance = null;

private Tooltip() {}

public static Tooltip getInstance() {
        if (instance == null) {
        instance = new Tooltip();
        }
        return instance;
        }

static int mouseX;
static int mouseY;
static String information = "";
static boolean show;

public static void getInfo (String info)
        {
        information =  info;
        show = true;
        }

public static void getCoords (int x, int y)
        {
        mouseX = x;
        mouseY = y;
        }

public void paint(Graphics g) {
        if (!information.isEmpty()) {

        Font font = new Font("Times New Roman", Font.BOLD, 10);
        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(information);
        int textHeight = metrics.getAscent() - metrics.getDescent();

        int boxHeight = 30;
        int boxWidth = Math.max(100,textWidth+10);

        g.setColor(Color.GRAY);
        g.fillRect(mouseX + 10, mouseY + 10, boxWidth, boxHeight);

        g.setColor(Color.black);
        g.setFont(font);
        g.drawString(information, 10+mouseX + (boxWidth - textWidth) / 2, 10+mouseY + (textHeight + boxHeight)/2);
        }
        }
}
