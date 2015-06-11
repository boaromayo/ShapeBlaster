package ShapeBlaster;
import java.awt.*;

import java.util.*;

public class CircleParticle extends ShapeParticle {

	private int radius;
	Rectangle circle;
	
	public CircleParticle(int newX, int newY) {
		super(newX, newY, 1);
		radius = 15;
		circle = new Rectangle(newX, newY, radius, radius);
	}
	
	public CircleParticle(int newX, int newY, int newH) {
		super(newX, newY, newH);
		radius = 15;
		circle = new Rectangle(newX, newY, radius, radius);
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.CYAN);
		
		//Random color = new Random();
		//setRandomColor(g2d, color);
		
		g2d.fillOval(x, y, radius, radius);

	}
	
	@Override
	protected void setRandomColor(Graphics g, Random color) {
		
		switch(color.nextInt(9)) {
		case 0:
			g.setColor(Color.RED);
			break;
		case 1:
			g.setColor(Color.YELLOW);
			break;
		case 2:
			g.setColor(Color.GREEN);
			break;
		case 3:
			g.setColor(Color.CYAN);
			break;
		case 4:
			g.setColor(Color.ORANGE);
			break;
		case 5:
			g.setColor(Color.MAGENTA);
			break;
		case 6:
			g.setColor(Color.WHITE);
			break;
		case 7:
			g.setColor(Color.BLUE);
			break;
		case 8:
			g.setColor(Color.PINK);
			break;
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, radius, radius);
	}

}
