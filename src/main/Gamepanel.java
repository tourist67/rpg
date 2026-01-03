
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Gamepanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;

	final int tileSize = originalTileSize * scale; 
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow; 

	KeyHandler keyH = new KeyHandler();
	Thread gameThread;

	// PLAYER SETTINGS
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;

	public Gamepanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		while (gameThread != null) {
			update();
			repaint();
		}
	}

	public void update() {
		if (keyH.upPressed) {
			playerY -= playerSpeed;
		}
		if (keyH.downPressed) {
			playerY += playerSpeed;
		}
		if (keyH.leftPressed) {
			playerX -= playerSpeed;
		}
		if (keyH.rightPressed) {
			playerX += playerSpeed;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
 
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, tileSize, tileSize);

		g2.dispose();
	}
}
