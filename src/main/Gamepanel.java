
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.Plant;
import tile.TileManager;

public class Gamepanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int originalTileSize = 16;
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; 
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow; 

	// WORLD SETTINGS
	public final int maxWorldCol = 35;
	public final int maxWorldRow = 37;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;

	// GAME STATE
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	

	KeyHandler keyH = new KeyHandler(this);
	Thread gameThread;
	public Player player = new Player(this, keyH);
	public TileManager tileM = new TileManager(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	Sound sound = new Sound();
	public UI ui = new UI(this);

	public SuperObject obj[] = new SuperObject[10];
	public Entity npc[] = new Entity[10];
	public AssetSetter assetSetter = new AssetSetter(this);
	
	// Plants tracking
	public ArrayList<Plant> plants = new ArrayList<>();


	// FPS
	int FPS = 60;

	public Gamepanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		assetSetter.setObject();
		assetSetter.setNPC();
		gameState = playState;
		playMusic(0);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS; // 0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;

		while (gameThread != null) {
			update();
			repaint();

			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;

				if (remainingTime < 0) {
					remainingTime = 0;
				}

				Thread.sleep((long) remainingTime);

				nextDrawTime += drawInterval;

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		if (gameState == playState) {
			player.update();
			
			// Update NPCs
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}
			
			// Update all plants for growth
			for (Plant plant : plants) {
				plant.update(this);
			}
		}
		if (gameState == pauseState) {
			// Do nothing when paused
		}
	}
	
	public void addPlant(Plant plant) {
		plants.add(plant);
	}
	
	public void removePlant(int col, int row) {
		plants.removeIf(plant -> plant.tileCol == col && plant.tileRow == row);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Draw tiles first (background)
		tileM.draw(g2);

		// Draw objects
		for(int i = 0; i < obj.length; i++) {
			if(obj[i] != null) {
				obj[i].draw(g2, this);
			}
		}

		// Draw NPCs
		for(int i = 0; i < npc.length; i++) {
			if(npc[i] != null) {
				npc[i].draw(g2);
			}
		}

		// Draw player on top
		player.draw(g2);

		// Draw UI
		ui.draw(g2);

		g2.dispose();
	}

	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}

	public void stopMusic() {
		sound.stop();
	}

	public void playSoundEffect(int i) {
		sound.setFile(i);
		sound.play();
	}
}
