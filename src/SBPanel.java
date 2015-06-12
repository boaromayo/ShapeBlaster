package ShapeBlaster;
import java.applet.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

public class SBPanel extends JPanel implements Runnable {
	
	// VARIABLES - DOUBLE BUFFERING.
	private Image dbi;
	private Graphics dbg;
	
	// FRAME COUNT VARIABLES.
	private static final int FPS = 60;
	
	// INTERNAL GAME VARIABLES.
	private boolean gameMode = false, pauseMode = false, finishMode = false;
	private boolean prepMode = false;
	private int mouseX, mouseY;
	private final int ARRAYMAX = 5;
	
	// MENU COMPONENT VARS.
	private Rectangle start, quit, cont, back;
	private Rectangle[] levelrect;
	private boolean startHover, quitHover, contHover, backHover;
	private boolean[] levelHover = new boolean[ARRAYMAX];
	
	// GAME COMPONENT VARS.
	protected BlasterShip ship;
	protected BlasterBall ball;
	protected ArrayList <BlasterBall> balltank;
	protected ArrayList <ShapeParticle> particles;
	protected ShapeParticle[][] particleset;
	protected ShapeBall shapeball;
	protected Level level;
	private int score;
	
	/**===============================================
	/* CONSTRUCTOR.
	/*===============================================**/
	public SBPanel() {
		// Set the size of the screen
		setSize(SBConstants.BSIZE);
		setBackground(Color.BLACK);
		// Set the listeners
		addMouseListener(new MouseAdapt());
		addMouseMotionListener(new MouseAdapt());
		addKeyListener(new KeyAdapt());
		// Init the game settings.
		initGame();
		// Set the window focusable.
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
		
	}
	
	//========================================
	// initGame() - Initialize game settings.
	//========================================
	public void initGame() {
		// Init the level settings.
		level = new Level();
		levelrect = new Rectangle[ARRAYMAX];
		// Create Rectangle buttons.
		start = new Rectangle(200, 140, 48, 48);
		quit = new Rectangle(380, 140, 48, 48);
		cont = new Rectangle(280, 380, 48, 48);
		back = new Rectangle(280, 380, 48, 48);
		// Create level access buttons.
		for (int i = 0; i < ARRAYMAX; i++) {
			levelrect[i] = new Rectangle(110 + (i * 90), 100, 48, 48);
		}
	}
	
	//-----------------------------------------
	// start() - start method for the applet.
	//-----------------------------------------
	public void start() {
		Thread t = new Thread(this);
		t.start();
	}
	
	public void stop() {
		pauseMode = true;
	}
	
	public void destroy() {
		
	}
	
	public void run() {
		try {
			while(true) {
				long start, diff, curr;
				int frames;
				
				start = System.currentTimeInMillis();
				
				updateGame();
				repaint();
				
				diff = System.currentTimeInMillis() - start;
				
				curr = diff / 1000;
				
				frames = (int)curr;
				Thread.sleep(frames);
			}
		} catch (Exception mainerr) {
			System.out.println("ERROR: "+mainerr);
			mainerr.printStackTrace();
			System.exit(0);
		}
	}
	
	//===========================================
	// updateGame() - updates the game elements.
	//===========================================
	public void updateGame() {
		if(gameMode) {
			// Update the ship.
			ship.update(this);
			
			// Update the ball array.
			for (int s = 0; s < balltank.size(); s++) {
				ball = (BlasterBall) balltank.get(s);
				
				if (ball != null)
					ball.update(this);
			}
			
			// Update the particles.
			for (int row = 0; row < particleset.length; row++) {		
				for (int col = 0; col < particleset[row].length; col++) {
					ShapeParticle sp = particleset[row][col];
						
					if (sp != null)
						sp.update(this);
				}
			}
				
			// If there are no particles in the ArrayList, go to scoreboard.
			if (particles.size() == 0 || ship.getHealth() <= 0) {
				gameMode = false; 
				finishMode = true;
			}
			
		}
	}
	
	//=====================================
	// Prep the game elements.
	//=====================================
	private void startGame() {
		// Initialize the game components.
		ship = new BlasterShip();
		balltank = ship.getBallTank();
		particleset = level.loadParticles(level.getLevel());
		particles = new ArrayList<ShapeParticle>();
		
		// Set score to 0.
		setScore(0);
		
				
		// INITIALIZE SHAPE PARTICLES FOR THE ARRAYLIST.
		for (int row = 0; row < particleset.length; row++) {
			for (int col = 0; col < particleset[row].length; col++) {
				particles.add(particleset[row][col]); // Add part.s into ArrayList as well.
			}
		}
		
		// Set gameMode to true if not true.
		if (!gameMode) {
			gameMode = true;
			prepMode = false;
		}
	}
	
	//====================================
	// update(Graphics g)
	//------------------------------------
	// METHOD FOR DOUBLE BUFFERING.
	//====================================
	@Override
	public void update(Graphics g) {
		dbi = createImage(this.getWidth(), this.getHeight());
		dbg = dbi.getGraphics();
		
		draw(dbg);
		g.drawImage(dbi, 0, 0, this);
	}
	
	//======================================
	// draw(Graphics g)
	//--------------------------------------
	// MAIN DRAWING METHOD.
	//======================================
	public void draw(Graphics g) {
		// Convert Graphics to Graphics2D for sharper images.
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (!prepMode && !pauseMode && !finishMode && !gameMode) {
			drawMenu(g2d);
		} else if (prepMode && !pauseMode && !finishMode && !gameMode) {
			drawSelect(g2d);
		} else if (!prepMode && pauseMode && !finishMode && !gameMode) {
			drawPause(g2d);
		} else if (!prepMode && !pauseMode && finishMode && !gameMode) {
			drawScore(g2d);
		} else if (!prepMode && !pauseMode && !finishMode && gameMode) {
		 	drawGame(g2d);
		}
		
		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
		
	}
	
	private void drawMenu(Graphics2D g2d) {
		
		String description = "You hate ShapeParticles! ShapeParticles hate you! " +
				"Show 'em who's boss and who's employee!";
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		g2d.drawString("ShapeBlaster!", 250, 40);
		
		if (!startHover) 
			g2d.setColor(Color.BLUE);
		else
			g2d.setColor(Color.CYAN);
		
		g2d.fillRoundRect(start.x, start.y, start.width, start.height, 20, 20);
		
		if (!quitHover)
			g2d.setColor(Color.RED);
		else
			g2d.setColor(Color.MAGENTA);
		
		g2d.fillRoundRect(quit.x, quit.y, quit.width, quit.height, 20, 20);
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 12));
		g2d.drawString("Start", start.x + 10, start.y + 28);
		g2d.drawString("Quit", quit.x + 12, quit.y + 28);
		
		g2d.drawString(description, 40, 400);
		
	}
	
	private void drawSelect(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		g2d.drawString("Level Select", 250, 40);
		
		for (int i = 0; i < ARRAYMAX - (ARRAYMAX - 1); i++) {
			if (!levelHover[i])
				g2d.setColor(Color.RED);
			else
				g2d.setColor(Color.MAGENTA);
			
			g2d.fillRoundRect(levelrect[i].x, levelrect[i].y, levelrect[i].width, levelrect[i].height, 
					20, 20);
			
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.BOLD, 30));
			g2d.drawString("T", levelrect[i].x + 16, levelrect[i].y + 34);
		}
		
		for (int i = ARRAYMAX - (ARRAYMAX - 1); i < ARRAYMAX; i++) {
			if (!levelHover[i])
				g2d.setColor(Color.BLUE);
			else
				g2d.setColor(Color.CYAN);
			
			g2d.fillRoundRect(levelrect[i].x, levelrect[i].y, levelrect[i].width, levelrect[i].height, 20, 20);
			
			g2d.setColor(Color.WHITE);
			g2d.setFont(new Font("Arial", Font.BOLD, 30));
			g2d.drawString(Integer.toString(i), levelrect[i].x + 16, levelrect[i].y + 34);			
		}
		
		if (!backHover)
			g2d.setColor(Color.BLUE);
		else
			g2d.setColor(Color.CYAN);
		
		g2d.fillRoundRect(back.x, back.y, back.width, back.height, 20, 20);
		
		if (!backHover)
			g2d.setColor(Color.CYAN);
		else
			g2d.setColor(Color.BLUE);
		
		g2d.fillPolygon(new int [] {back.x + 12, back.x + back.width - 12, back.x + back.width - 12}, 
				new int [] {back.y + (back.height / 2), back.y + back.height - 12, back.y + 12}, 3);
	}
	
	private void drawPause(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		g2d.drawString("PAUSED", 260, 120);
		g2d.drawString("Press PAUSE to continue.", 190, 320);
	}
	
	private void drawScore(Graphics2D g2d) {
		// Smooth out any shapes.
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Draw Game Over and the score.
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		g2d.drawString("GAME OVER!", 250, 40);
		
		g2d.drawString("Score: ", 100, 100);
		g2d.drawString(Integer.toString(getScore()), 480, 100);
		
		if (!contHover)
			g2d.setColor(Color.BLUE);
		else
			g2d.setColor(Color.CYAN);
		
		g2d.fillRoundRect(cont.x, cont.y, cont.width, cont.height, 20, 20);
		
		if (!contHover)
			g2d.setColor(Color.CYAN);
		else
			g2d.setColor(Color.BLUE);
		
		g2d.fillPolygon(new int [] {cont.x + 12, cont.x + cont.width - 12, cont.x + 12}, 
				new int [] {cont.y + 12, cont.y + (cont.height / 2), cont.y + cont.height - 12}, 3);
	}
	
	public void drawGame(Graphics2D g2d) {
		// Draw Ship and other elements.
		ship.draw(g2d);
		// Draw Ball if fired.
		for (int s = 0; s < balltank.size(); s++) {
			ball = (BlasterBall) balltank.get(s);
			
			if (ball.isVisible() && ball != null)
				ball.draw(g2d);
		}
		// Draw Particles from 2-D array.
		for (int row = 0; row < particleset.length; row++) {
			for (int col = 0; col < particleset[row].length; col++) {
				ShapeParticle sp = particleset[row][col];
				
				if (sp.isVisible() && sp != null)
					sp.draw(g2d);
			}
		}
		
		// Draw points and ship's health.
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		g2d.drawString(Integer.toString(score), 20, 40);
		g2d.drawString(Integer.toString(ship.getHealth()), (BlasterConstants.BWIDTH - 40), 40);
		
	}
	
	public void setScore(int newScore) {
		score = newScore;
	}
	
	public int getScore() {
		return score;
	}
	
	private class KeyAdapt extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent kpe) {
			if (gameMode) {
				ship.keyPressed(kpe);
				
				switch(kpe.getKeyCode()) {
					case KeyEvent.VK_P:
					case KeyEvent.VK_PAUSE:
						gameMode = false;
						pauseMode = true;
						break;
					case KeyEvent.VK_ESCAPE:
						System.exit(0);
						break;
				}
			} else if (pauseMode) {
				switch(kpe.getKeyCode()) {
					case KeyEvent.VK_P:
					case KeyEvent.VK_PAUSE:
						gameMode = true;
						pauseMode = false;
						break;
				}
			}
		}
		@Override
		public void keyReleased(KeyEvent kre) {
			ship.keyReleased(kre);
		}
	}	
	
	private class MouseAdapt extends MouseAdapter {
		@Override
		public void mouseMoved(MouseEvent mm) {
			mouseX = mm.getX();
			mouseY = mm.getY();
			if (!prepMode) {
				isStartHover();
				isQuitHover();
				isContinueHover();
			}
			else if (prepMode) {
				isBackHover();
				for (int i = 0; i < levelrect.length; i++)
					isLevelHover(i);
			}
		}
		@Override
		public void mouseClicked(MouseEvent mc) {
			if (!prepMode) {
				isStartClicked();
				isQuitClicked();
				isContinueClicked();
			}
			else if (prepMode) {
				isBackClicked();
				isLevelClicked();
			}
		}
		
		// For the start button.
		private boolean isStartHover() {
			if (mouseX > start.x && mouseX < start.x + start.width && mouseY > start.y &&
					mouseY < start.y + start.height)
				startHover = true;
			else
				startHover = false;
			
			return startHover; // Return global boolean.
		}
		private void isStartClicked() {
			if (mouseX > start.x && mouseX < start.x + start.width && mouseY > start.y &&
					mouseY < start.y + start.height) {
				prepMode = true;
			}
		}
		// For the quit button.
		private boolean isQuitHover() {
			if (mouseX > quit.x && mouseX < quit.x + quit.width && mouseY > quit.y &&
					mouseY < quit.y + quit.height)
				quitHover = true;
			else
				quitHover = false;
			
			return quitHover; // Return global boolean.
		}
		private void isQuitClicked() {
			if (mouseX > quit.x && mouseX < quit.x + quit.width && mouseY > quit.y &&
					mouseY < quit.y + quit.height)
				System.exit(0);
		}
		// For the continue button.
		private boolean isContinueHover() {
			if (mouseX > cont.x && mouseX < cont.x + cont.width && mouseY > cont.y 
					&& mouseY < cont.y + cont.height)
				contHover = true;
			else
				contHover = false;
			
			return contHover;
		}
		private void isContinueClicked() {
			if (mouseX > cont.x && mouseX < cont.x + cont.width && mouseY > cont.y 
					&& mouseY < cont.y + cont.height)
				finishMode = false;
		}
		// For the back button.
		private boolean isBackHover() {
			if (mouseX > back.x && mouseX < back.x + back.width && mouseY > back.y 
					&& mouseY < back.y + back.height)
				backHover = true;
			else
				backHover = false;
			
			return backHover;
		}
		private void isBackClicked() {
			if (mouseX > back.x && mouseX < back.x + back.width && mouseY > back.y 
					&& mouseY < back.y + back.height) {
				if (prepMode)
					prepMode = false;
			}
		}
		// For each level access button.
		private boolean isLevelHover(int i) {
			if (mouseX > levelrect[i].x && mouseX < levelrect[i].x + levelrect[i].width &&
					mouseY > levelrect[i].y && mouseY < levelrect[i].y + levelrect[i].height)
				levelHover[i] = true;
			else
				levelHover[i] = false;
			
			return levelHover[i];
		}
		private void isLevelClicked() {
			for (int i = 0; i < levelrect.length; i++) {
				if (mouseX > levelrect[i].x && mouseX < levelrect[i].x + levelrect[i].width 
						&& mouseY > levelrect[i].y && mouseY < levelrect[i].y + levelrect[i].height) {
					level.setLevel(i);
					startGame();
				}
			}
		}
		
	}
	
}
