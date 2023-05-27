import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class Board extends JPanel implements ActionListener , MouseInputListener{

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int DELAY = 25;

    private Timer timer;
    private int key = 0;
    private boolean keyPressed = false;
    private boolean mousePressed = false;
    private boolean file_clicked = false;
    private boolean edit_clicked = false;

    private Button [] array;
    private Button button;
    private FileMenu fileMenu;
    private EditMenu editMenu;
    Window openWindow ;
    private ShapesToolbar shapesToolbar;
    private ColorsToolbar colorsToolbar;
    private LayersToolbar layersToolbar;
    static boolean open_window;
    GradientWindow gradient;
    Tooltip tooltip;
    // Drawing variables
    private int x1, y1, x2, y2, x3, y3;
    private int occur;
    public int stroke = 4;
    private Circle circle;
    private Rectangle rectangle;
    private Triangle triangle;
    private Pentagram pentagram;
    private Hexagon hexagon;
    private FreeDraw drawing;
    public String shape = "";
    private Random random = new Random();
    public Queue<Shape> redo = new Queue<>();
    public LinkedList<Stack<Shape>> layer = new LinkedList<>();
    DrawingWindow panel;



    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {

            keyPressed = true;
            if (Character.toLowerCase(e.getKeyChar()) == 'f'){
                fileMenu.isClicked(fileMenu.x + 5, fileMenu.y + 5);
                array = fileMenu.getArray();
                file_clicked = true;
            }
            else {
                file_clicked = false;
            }
            if (Character.toLowerCase(e.getKeyChar()) == 'e'){
                editMenu.isClicked(editMenu.x + 5,  editMenu.y + 5);
                array = editMenu.getArray();
                edit_clicked = true;
            }

            else{
                edit_clicked = false;
            }
        }
    }

    public Board() {

        initBoard();
    }

    private void InitializeAssets() {
        ImageIcon edit = new ImageIcon("src/Images/myimage_edit.png");
        ImageIcon file = new ImageIcon("src/Images/myimage_file.png");
        ImageIcon right = new ImageIcon("src/Images/right angle.png");
        ImageIcon equilateral = new ImageIcon("src/Images/eq2.png");
        ImageIcon rectangle = new ImageIcon("src/Images/rect2.png");
        ImageIcon circle = new ImageIcon("src/Images/circle.png");
        ImageIcon pentagram = new ImageIcon("src/Images/pentagram.png");
        ImageIcon hexagon = new ImageIcon("src/Images/hexagon.png");
        ImageIcon free = new ImageIcon("src/Images/freedraw.png");
        ImageIcon [] array3 = {right, equilateral, rectangle, circle, pentagram, hexagon, free};
        ImageIcon depressed = new ImageIcon("src/Images/square_depressed.png");
        ImageIcon pressed = new ImageIcon("src/Images/square_pressed.png");
        fileMenu = new FileMenu(10,10, file.getIconWidth(), file.getIconHeight(), Color.BLACK);
        editMenu = new EditMenu(fileMenu.x + fileMenu.width + 5 , 10, edit.getIconWidth(), edit.getIconHeight(), Color.BLACK);
        shapesToolbar = new ShapesToolbar(fileMenu.x, fileMenu.height + 20, (660), 100, 7, array3, Color.GRAY);
        colorsToolbar = new ColorsToolbar(shapesToolbar.x + (660) + 10, fileMenu.height + 20, 630,100 , pressed.getImage(), depressed.getImage(), Color.GRAY );
        layersToolbar = new LayersToolbar(screenSize.width - 305, colorsToolbar.y + colorsToolbar.height + 10, 300, screenSize.height, pressed.getImage(), depressed.getImage(),Color.GRAY, this );
        gradient = GradientWindow.getInstance();
        openWindow = new Window(200, 200,200, 300, Color.GRAY );
        tooltip =  Tooltip.getInstance();
        panel = new DrawingWindow(1, fileMenu.y +fileMenu.height + 20 + 100, screenSize.width - 305, screenSize.height - 200, Color.WHITE, Color.LIGHT_GRAY, 1);
    }

    private void initBoard() {

    	addMouseListener( this );
    	addMouseMotionListener( this );
    	addKeyListener(new TAdapter());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight()));
        setFocusable(true);

        InitializeAssets();
        
        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        panel.paint(g);
        for (int i = 0; i < layer.size(); i++) {
            Stack<Shape> temp = new Stack<>();
            while(!layer.get(i).isEmpty()) {
                Shape test = layer.get(i).pop();
                temp.push(test);
            }
            while(!temp.isEmpty()) {
                Shape temp2 = temp.pop();
                temp2.draw(g);
                layer.get(i).push(temp2);
            }
        }
        g.setColor(Color.BLACK);
        g.drawString(shape, panel.centre.x + panel.width - 100, panel.centre.y + panel.height - 50);



        shapesToolbar.paint(g);
        for (int i = 0; i < shapesToolbar.array1.length; i++) {
            if(shapesToolbar.array1[i].pressed){
                shapesToolbar.array1[i].background(g);
            }
            drawButton(g, shapesToolbar.array1[i]);
        }
        colorsToolbar.paint(g);
        layersToolbar.paint(g);
        gradient.paint(g);
        openWindow.paint(g);
        if (file_clicked || edit_clicked){}
        else tooltip.paint(g);

        drawFileMenu(g);
        g.setColor(fileMenu.getColor());
        g.drawRect(fileMenu.x,  fileMenu.y, fileMenu.width, fileMenu.height);
        if (file_clicked){
            for (int i = 0; i < 3; i++) {
                button = array[i];
                drawButton(g, button);
                fileMenu.paint(g);
            }
        }
        drawEditMenu(g);

        if (edit_clicked){
            for (int i = 0; i < 2; i++) {
                button = array[i];
                drawButton(g, button);
                editMenu.paint(g);
            }
        }

    }
    private void drawButton(Graphics g, Button  par) {

        g.drawImage(par.GetImage(), par.x, par.y, this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Toolkit.getDefaultToolkit().sync();
        repaint();
    }
    
    public void IsClicked(int x, int y) {
        if (file_clicked || edit_clicked){
            if (fileMenu.isClicked(x, y)) {
                array = fileMenu.getArray();
                file_clicked = true;
            } else {
                file_clicked = false;
            }
            if (editMenu.isClicked(x, y)) {
                array = editMenu.getArray();
                edit_clicked = true;
            } else {
                edit_clicked = false;
            }
            layersToolbar.Clicked(x, y);
            colorsToolbar.Clicked(x, y);
            gradient.Clicked(x, y);

        }
        else {
            if (fileMenu.isClicked(x, y)) {
                array = fileMenu.getArray();
                file_clicked = true;
            } else {
                file_clicked = false;
            }
            if (editMenu.isClicked(x, y)) {
                array = editMenu.getArray();
                edit_clicked = true;
            } else {
                edit_clicked = false;
            }
            shapesToolbar.Clicked(x, y);
            shape = ShapesToolbar.getSelectedShapeName();
            layersToolbar.Clicked(x, y);
            colorsToolbar.Clicked(x, y);
            gradient.Clicked(x, y);

        }

        open_window = x > 10 && x < 10 + 50 && y > 60 && y < 60 + 20;
        openWindow.Clicked(x,y);
    }
    

	@Override
	public void mouseClicked(MouseEvent e) {
		IsClicked(e.getX(), e.getY());
	}

	@Override
	public void mousePressed(MouseEvent e) {
        layersToolbar.Pressed(e.getX(), e.getY());
    }

	@Override
	public void mouseReleased(MouseEvent e) {
        layersToolbar.Released(e.getX(),e.getY());
    }

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
        gradient.Dragged(e.getX(),e.getY());
        // TODO Auto-generated method stub
        if(SwingUtilities.isLeftMouseButton(e))
        {
            int x = e.getX(), y = e.getY();
            if (panel.inBounds(x, y)) {
                // Repeated draw and erase the shapes while dragging for the user to judge how big they should be

                // Erase the intermediate drawing of the shapes
                if (occur != 0 && !shape.equals("Free Draw"))
                    layer.get(layer.size() - 1).pop();
                // Purge the redo queue whenever the user draws a new shape
                purge();
                x2 = x;
                y2 = y;
                Point start = new Point(x1, y1);
                int width, height;
                width = x2 - x1;
                height = y2 - y1;
                if (width < 0) {
                    start.x = x2;
                    width *= -1;
                }
                if (height < 0) {
                    start.y = y2;
                    height *= -1;
                }
                int radius = (int)Math.sqrt((width * width) + (height * height));
                if (shape.equals("Rectangle")) {
                    rectangle = new Rectangle(start, ColorsToolbar.fill.color, width, height, ColorsToolbar.stroke.color, stroke);
                    layer.get(layer.size() - 1).push(rectangle);
                }
                else if (shape.equals("Circle")) {
                    Point center = new Point(x1, y1);
                    circle = new Circle(2 * radius, center, ColorsToolbar.fill.color, ColorsToolbar.stroke.color, stroke);
                    layer.get(layer.size() - 1).push(circle);
                }
                else if (shape.equals("Right-Angled-Triangle")) {
                    Point center = new Point(x1, y1);
                    Point corner1 = new Point(x1, y2);
                    Point corner2 = new Point(x2, y2);
                    if (x2 < x1) {
                        center.x = x2;
                        corner1.x = x2;
                        corner2.x = x1;
                    }
                    if (y2 < y1) {
                        center.y = y2;
                        corner1.y = y1;
                        corner2.y = y1;
                    }
                    triangle = new Triangle(center, corner1, corner2, ColorsToolbar.fill.color, ColorsToolbar.stroke.color, stroke);
                    layer.get(layer.size() - 1).push(triangle);
                }
                else if (shape.equals("Equilateral-Triangle")) {
                    int distance = (int) ((Math.sqrt((Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)))) / 2);
                    x3 = x1 + distance;
                    Point center = new Point(x3, y1);
                    Point corner1 = new Point(x2, y2);
                    Point corner2 = new Point(x1, y2);
                    if (x2 < x1) {
                        center.x = x1 - distance;
                        corner1.x = x2;
                        corner2.x = x1;
                    }
                    if (y2 < y1) {
                        center.y = y2;
                        corner1.y = y1;
                        corner2.y = y1;
                    }
                    triangle = new Triangle(center, corner1, corner2, ColorsToolbar.fill.color, ColorsToolbar.stroke.color, stroke);
                    layer.get(layer.size() - 1).push(triangle);
                }
                else if (shape.equals("Pentagram")) {
                    pentagram = new Pentagram(radius, new Point(x1, y1), ColorsToolbar.fill.color, ColorsToolbar.stroke.color, stroke);
                    layer.get(layer.size() - 1).push(pentagram);
                }
                else if (shape.equals("Hexagon")) {
                    hexagon = new Hexagon(radius, new Point(x1, y1), ColorsToolbar.fill.color, ColorsToolbar.stroke.color, stroke);
                    layer.get(layer.size() - 1).push(hexagon);
                }
                else if (shape.equals("Free-Drawing")) {
                    if (occur == 0) {
                        drawing = new FreeDraw(new Point(x1, y1), ColorsToolbar.stroke.color, stroke);
                        layer.get(layer.size() - 1).push(drawing);
                    }
                    drawing.freeDrawing(x, y);
                }
                occur++;
            }
        }
        }




	@Override
	public void mouseMoved(MouseEvent e) {
        if (e.getX() > fileMenu.x && e.getX() < fileMenu.x + fileMenu.width && e.getY() > fileMenu.y && e.getY() < fileMenu.y + fileMenu.height)
            fileMenu.Moved(e.getX(),e.getY());
        else if (e.getX() > editMenu.x && e.getX() < editMenu.x + editMenu.width && e.getY() > editMenu.y && e.getY() < editMenu.y + editMenu.height)
            editMenu.Moved(e.getX(),e.getY());
        else if (e.getX() > shapesToolbar.x && e.getX() < shapesToolbar.x + shapesToolbar.width && e.getY() > shapesToolbar.y && e.getY() < shapesToolbar.y + shapesToolbar.height)
            shapesToolbar.Moved(e.getX(), e.getY());
        else if (e.getX() > colorsToolbar.x && e.getX() < colorsToolbar.x + colorsToolbar.width && e.getY() > colorsToolbar.y && e.getY() < colorsToolbar.y + colorsToolbar.height)
            colorsToolbar.Moved(e.getX(), e.getY());
        else if (e.getX() > layersToolbar.x && e.getX() < layersToolbar.x + layersToolbar.width && e.getY() > layersToolbar.y && e.getY() < layersToolbar.y + layersToolbar.height )
            layersToolbar.Moved(e.getX(),e.getY());

    }
    private void drawFileMenu(Graphics g) {
        g.drawImage(fileMenu.GetImage(), fileMenu.x, fileMenu.y, this);
        g.setColor(fileMenu.getColor());
        g.drawRect(fileMenu.x,  fileMenu.y, fileMenu.width, fileMenu.height);
    }

    private void drawEditMenu(Graphics g) {
        g.drawImage(editMenu.GetImage(), editMenu.x, editMenu.y, this);
        g.setColor(editMenu.getColor());
        g.drawRect(editMenu.x, editMenu.y, editMenu.width, editMenu.height);
    }

    // Purge the redo queue
    public void purge() {
        while (!redo.isEmpty()) {
            redo.dequeue();
        }
    }

    // Remove a shape from the stack
    public void undo(){
        if (layer.get(0).size() > 0)
            redo.enqueue(layer.get(0).pop());
    }


}