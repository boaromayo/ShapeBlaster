package ShapeBlaster;
import java.awt.*;

import java.util.*;

public class RhombParticle extends ShapeParticle {

	Rectangle rhombus;
	
	public RhombParticle(int newX, int newY) {
		super(newX, newY, 4);
		rhombus = new Rectangle(newX, newY, 16, 16);
	}
	
	public RhombParticle(int newX, int newY, int newH) {
		super(newX, newY, newH);
		rhombus = new Rectangle(newX, newY, 16, 16);
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.YELLOW);
		
		//Random color = new Random();
		
		//setRandomColor(g2d, color);
		
		int [] xVert = {x + 8, x + 16, x + 8, x};
		int [] yVert = {y, y + 8, y + 16, y + 8};
		
		g2d.fillPolygon(xVert, yVert, 4);

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
	public void update(BlasterFrame bf) {
		

	}

	@Override
	public Rectangle getBounds() {
		return rhombus;
	}

}
