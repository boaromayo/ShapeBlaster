package ShapeBlaster;
import java.awt.*;

import java.util.*;

public class SquareParticle extends ShapeParticle {

	private int sqRadius;
	Rectangle square;
	
	public SquareParticle(int newX, int newY) {
		super(newX, newY, 2);
		sqRadius = 15;
		square = new Rectangle(x, y, sqRadius, sqRadius);
	}
	
	public SquareParticle(int newX, int newY, int newH) {
		super(newX, newY, newH);
		sqRadius = 15;
		square = new Rectangle(x, y, sqRadius, sqRadius);
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.RED);
		//Random color = new Random();
		
		//setRandomColor(g2d, color);
		
		g2d.fillRect(x, y, sqRadius, sqRadius);

	}

	@Override
	protected void setRandomColor(Graphics g, Random color) {

		switch(color.nextInt(7)) {
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
		}

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, sqRadius, sqRadius);
	}

}
