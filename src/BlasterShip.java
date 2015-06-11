package ShapeBlaster;
import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class BlasterShip {
	
	private int x, y, shipWidth, shipHeight, dx; 
	private int ballAmmo = 3;
	private int health = 4;
	private ArrayList <BlasterBall> balltank;
	Rectangle shipRect;
	
	public BlasterShip() {
		x = 160;
		y = 400;
		shipWidth = 80;
		shipHeight = 16;
		shipRect = new Rectangle(x, y, shipWidth, shipHeight);
		balltank = new ArrayList<BlasterBall>();
	}
	
	public BlasterShip(int newX, int newY) {
		x = newX;
		y = newY;
		shipWidth = 80;
		shipHeight = 16;
		shipRect = new Rectangle(x, y, shipWidth, shipHeight);
		balltank = new ArrayList<BlasterBall>();
	}
	
	public void move() {
		shipRect.x += dx;
		
		// Set boundaries for the ship.
		if (shipRect.x >= ((BlasterConstants.BWIDTH - 30) - shipWidth))
			shipRect.x = ((BlasterConstants.BWIDTH - 30) - shipWidth);
		else if (shipRect.x <= 15)
			shipRect.x = 15;
	}
	
	public void update(BlasterFrame bf) {
		move();	
	}
	
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.BLUE);
		g2d.fillRect(shipRect.x + 32, shipRect.y - shipHeight, 
				shipWidth / 5, shipHeight);
		g2d.fillRect(shipRect.x, shipRect.y, shipWidth, shipHeight);
		
		int [] xTrap = {shipRect.x + 16, shipRect.x + 24, shipRect.x + 56, shipRect.x + 64};
		int [] yTrap = {shipRect.y + shipHeight, shipRect.y + (shipHeight * 2), 
				shipRect.y + (shipHeight * 2), shipRect.y + shipHeight};
		
		g2d.fillPolygon(xTrap, yTrap, 4);
		
	}
	
	public void keyPressed(KeyEvent kpe) {
		switch (kpe.getKeyCode()) {
			case KeyEvent.VK_A: 
			case KeyEvent.VK_LEFT:
				setDX(-4);
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				setDX(4);
				break;
			case KeyEvent.VK_SPACE:
				ballShoot(balltank);
				break;
			case KeyEvent.VK_X:
				setHealth(getHealth() - 1);
				break;
		}
	}
	
	public void keyReleased(KeyEvent kre) {
		switch (kre.getKeyCode()) {
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				setDX(0);
				break;
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				setDX(0);
				break;
		}
	}
	
	public void ballShoot(ArrayList<BlasterBall> shottank) {
		if (ballAmmo > 0) {
			ballAmmo--;
			BlasterBall bb = new BlasterBall(shipRect.x + (shipWidth / 2), shipRect.y - 16);
			shottank.add(bb);
		}
	}
	
	// SET METHODS.
	public void setX(int newX) {
		shipRect.x = newX;
	}
	public void setDX(int newDX) {
		dx = newDX;
	}
	public void setBallAmmo(int newAmmo) {
		ballAmmo = newAmmo;
	}
	public void setHealth(int newHealth) {
		health = newHealth;
	}
	
	// GET METHODS.
	public int getX() {
		return shipRect.x;
	}
	public int getDX() {
		return dx;
	}
	public int getBallAmmo() {
		return ballAmmo;
	}
	public int getHealth() {
		return health;
	}
	public Rectangle getCannonBounds() {
		return new Rectangle(shipRect.x + 32, shipRect.y - shipHeight, 
				shipWidth / 5, shipHeight);
	}
	public Rectangle getBounds() {
		return new Rectangle(shipRect.x, shipRect.y, shipWidth, shipHeight);
	}
	public ArrayList<BlasterBall> getBallTank() {
		return balltank;
	}
}
