
import java.awt.*;
import java.util.ArrayList;

public class DrawingWindow extends RectangleTemplate {
    private ArrayList<Toolbar> toolBars;
    public DrawingWindow(int x, int y, int width, int height, Color rectColor, Color lineColor, int stroke) {
        super(x, y, width, height, rectColor, lineColor, stroke);
        toolBars = new ArrayList<>();
    }

    public void addToolBar(Toolbar toolBar) {
        toolBars.add(toolBar);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (!toolBars.isEmpty()) {
            for (Toolbar toolBar : toolBars) {
                toolBar.paint(g);
            }
        }
    }

    public boolean inBounds(int x, int y) {
        return (x > centre.x && x < centre.x + width && y > centre.y && y < centre.y + height);
    }


}
