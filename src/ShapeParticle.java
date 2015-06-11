package ShapeBlaster;
import java.awt.*;

import java.util.*;

public abstract class ShapeParticle {
	
	protected int x, y, dx, dy;
	protected int health;
	protected boolean visible;
	protected Rectangle partRect;
	
	public ShapeParticle(int newX, int newY) {
		x = newX;
		y = newY;
		health = 1;
		visible = true;
		partRect = new Rectangle(x, y, 0, 0);
	}
	
	public ShapeParticle(int newX, int newY, int newH) {
		x = newX;
		y = newY;
		health = newH;
		visible = true;
		partRect = new Rectangle(x, y, 0, 0);
	}
	
	public void move() {
		partRect.x += dx;
		partRect.y += dy;
		
		if (partRect.x >= BlasterConstants.BWIDTH - 15)
			partRect.x -= dx;
		else if (partRect.x <= 15)
			partRect.x += dx;
		else if (partRect.y >= BlasterConstants.BHEIGHT)
			partRect.y -= dy;
		else if (partRect.y <= 0)
			partRect.y += dy;
	}
	
	public void update(BlasterFrame bf) {
		
		for (int row = 0; row < bf.particleset.length; row++) {
			for (int col = 0; col < bf.particleset[row].length; col++) {
				
				//bf.particleset[row][col].move();
				
				if (bf.particleset[row][col].health <= 0) {
					bf.particleset[row][col].setVisible(false);
					bf.particles.remove(bf.particleset[row][col]);
				}
			}
		}
		
	}
	
	public void ballShoot() {
		ShapeBall sb = new ShapeBall(partRect.x + (partRect.width / 2), partRect.y / 2);
	}
	
	public abstract void draw(Graphics g);
	
	protected abstract void setRandomColor(Graphics g, Random color);
	
	public abstract Rectangle getBounds();
	
	public void setX(int newX) {
		partRect.x = newX;
	}
	public void setY(int newY) {
		partRect.y = newY;
	}
	public void setHealth(int newHealth) {
		health = newHealth;
	}
	public void setDX(int newDX) {
		dx = newDX;
	}
	public void setDY(int newDY) {
		dy = newDY;
	}
	public void setVisible(boolean newVis) {
		visible = newVis;
	}
 	
	public int getX() {
		return partRect.x;
	}
	public int getY() {
		return partRect.y;
	}
	public int getHealth() {
		return health;
	}
	public int getDX() {
		return dx;
	}
	public int getDY() {
		return dy;
	}
	public boolean isVisible() {
		return visible;
	}
}
