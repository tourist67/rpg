package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import main.Gamepanel;
import main.KeyHandler;

public class Player extends Entity {
	Gamepanel gp;
	KeyHandler keyH;

	int spriteCounter = 0;
	int spriteNum = 1;

	public Player(Gamepanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
		getPlayerImage();
	}

	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 4;
		direction = "down";
	}

	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/player_right_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
			if (keyH.upPressed == true) {
				direction = "up";
				y -= speed;
			}
			if (keyH.downPressed == true) { 
				direction = "down";
				y += speed;
			}
			if (keyH.leftPressed == true) {
				direction = "left";
				x -= speed;
			}
			if (keyH.rightPressed == true) {
				direction = "right";
				x += speed;
			}

			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;

		switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = up1;
				} else {
					image = up2;
				}
				break;
			case "down":
				if (spriteNum == 1) {
					image = down1;
				} else {
					image = down2;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					image = left1;
				} else {
					image = left2;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					image = right1;
				} else {
					image = right2;
				}
				break;
		}

		// *3 for scaling up the player size
		g2.drawImage(image, x, y, gp.tileSize*3, gp.tileSize*3, null);
	}
}
