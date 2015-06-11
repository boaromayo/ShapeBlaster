package ShapeBlaster;
import java.awt.*;

import java.util.*;

public class TriParticle extends ShapeParticle {

	Rectangle triangle;
	
	public TriParticle(int newX, int newY) {
		super(newX, newY, 3);
		triangle = new Rectangle();
	}
	
	public TriParticle(int newX, int newY, int newH) {
		super(newX, newY, newH);
		triangle = new Rectangle();
	}
	
	@Override
	public void draw(Graphics g) {
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.GREEN);
		
		int [] xTri = {x + 7, x + 15, x};
		int [] yTri = {y, y + 15, y + 15};
		g2d.fillPolygon(xTri, yTri, 3);
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
		return new Rectangle(x, y, 15, 15);
	}

}
