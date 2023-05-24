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

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
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
        ImageIcon right = new ImageIcon("src/Images/right angle.png");
        ImageIcon equilateral = new ImageIcon("src/Images/eq2.png");
        ImageIcon rectangle = new ImageIcon("src/Images/rect2.png");
        ImageIcon circle = new ImageIcon("src/Images/circle.png");
        ImageIcon pentagram = new ImageIcon("src/Images/pentagram.png");
        ImageIcon hexagon = new ImageIcon("src/Images/hexagon.png");
        ImageIcon [] array3 = {right, equilateral, rectangle, circle, pentagram, hexagon};
        ImageIcon depressed = new ImageIcon("src/Images/square_depressed.png");
        ImageIcon pressed = new ImageIcon("src/Images/square_pressed.png");
        fileMenu = new FileMenu(10,10, 50, 20, Color.BLACK);
        editMenu = new EditMenu(fileMenu.x + fileMenu.width + 5 , 10, 50,20, Color.BLACK);
        shapesToolbar = new ShapesToolbar(editMenu.x + 10 + edit.getIconWidth() , 5, (70*8), 100, 6, array3, Color.GRAY);
        colorsToolbar = new ColorsToolbar(shapesToolbar.x + (70*8) + 10, 5, 475,100 , pressed.getImage(), depressed.getImage(), Color.GRAY );
        layersToolbar = new LayersToolbar(screenSize.width - 350, 5, 300, screenSize.height, pressed.getImage(), depressed.getImage(),Color.GRAY );
        gradient = GradientWindow.getInstance();
        openWindow = new Window(200, 200,200, 300, Color.GRAY );
        tooltip =  Tooltip.getInstance();

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
        tooltip.paint(g);
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
        layersToolbar.Clicked(x, y);
        colorsToolbar.Clicked(x, y);
        gradient.Clicked(x, y);

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
    }

	@Override
	public void mouseMoved(MouseEvent e) {
        shapesToolbar.Moved(e.getX(), e.getY());
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



}