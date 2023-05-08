import java.awt.*;

public class Button 
{
	public int x;
	public int y;
 	int width;
  	int height;
	Image image_depressed;
	Image image_pressed;
 	Image current_image;
	boolean pressed;
	Color color;

	public Button(int x, int y, int width, int height, Image i_depressed, Image i_pressed, Color color)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		image_depressed = i_depressed;
		image_pressed = i_pressed;
		current_image = i_depressed;
		this.color = color;
	}	

	public Image GetImage() 
	{
		return current_image;
	}
	

	public boolean IsClicked(int x, int y)
	{
		if(x > this.x && x < this.x + width && y > this.y && y < this.y + height)
		{
			pressed = true;
			current_image = image_pressed;
			return true;
		}
		else pressed =  false;
		return false;
	}
	public void IsReleased(int x, int y) {
		if(x > this.x && x < this.x + width && y > this.y && y < this.y + height)
		{
			current_image = image_depressed;
			pressed = false;
		}
	}

	public int getWidth() {
		return width;
	}

	public void paint(Graphics g){
		g.drawImage(current_image, x, y, null);

		Font font = new Font("Arial", Font.PLAIN, 13);
		g.setFont(font);
	}

	public int getHeight() {
		return height;
	}

	public Color getColor() {
		return color;
	}

	void color(Graphics g){
		g.setColor(color);
		g.fillRect(x,y,width, height);
	}
	void background(Graphics g){
		g.setColor(new Color(0, 0, 150));
		g.fillRect(x-5, y-5, width+10, height+10);
	}




}
