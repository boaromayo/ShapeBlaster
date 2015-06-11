package ShapeBlaster;
import java.awt.*;

public class BlasterBall {
	
	protected int x, y, dy;
	protected boolean visible;
	protected Rectangle ballRect;
	
	private final int radius;
	
	public BlasterBall(int newX, int newY) {
		 x = newX;
		 y = newY;
		 visible = true;
		 radius = 4;
		 ballRect = new Rectangle(x, y, radius, radius);
	}
	
	public void move() {
		setDY(-4);
		y += dy;
	}
	
	public void update(BlasterFrame bf) {
		for (int s = 0; s < bf.balltank.size(); s++) {
			// Get the ball from the ammo tank
			BlasterBall uball = bf.ball = (BlasterBall) bf.balltank.get(s);
	
			collision(bf, uball, s);
			
			if (uball != null && uball.isVisible()) {
				uball.move();
			}
			
		}
		
	}
	
	private void collision(BlasterFrame bf, BlasterBall cball, int s) {
		BlasterShip cship = bf.ship;
		
		if (cball.getY() <= -5 || cball.getY() >= BlasterConstants.BHEIGHT + 5 || 
				cball.getX() <= -5 || cball.getX() >= BlasterConstants.BWIDTH + 5) {
			cball.setVisible(false);
			bf.balltank.remove(s);
			cship.setBallAmmo(cship.getBallAmmo() + 1);
		}
		
		Rectangle rball = cball.getBounds();
	
		for (int q = 0; q < bf.particles.size(); q++) {
			ShapeParticle sp = (ShapeParticle) bf.particles.get(q);
			Rectangle rsp = sp.getBounds();
			
			if (rball.intersects(rsp)) {
				sp.setHealth(sp.getHealth() - 1);
				cship.setBallAmmo(cship.getBallAmmo() + 1);
				cball.setVisible(false);
				bf.setScore(bf.getScore() + 1);
				bf.balltank.remove(s);
			}
			
		}
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.BLUE);
		g2d.fillOval(x, y, radius, radius);
	}
	
	public void setX(int newX) {
		x = newX;
	}
	public void setY(int newY) {
		y = newY;
	}
	public void setDY(int newDY) {
		dy = newDY;
	}
	public void setVisible(boolean newVis) {
		visible = newVis;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getDY() {
		return dy;
	}
	public int getRadius() {
		return radius;
	}
	public Rectangle getBounds() {
		return new Rectangle(x, y, radius, radius);
	}
	public boolean isVisible() {
		return visible;
	}
	
}
