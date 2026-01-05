package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import main.Gamepanel;
import main.KeyHandler;
import object.SuperObject;
import tile.Plant;

public class Player extends Entity {
	Gamepanel gp;
	KeyHandler keyH;

	public final int screenX;
	public final int screenY;
	
	int spriteCounter = 0;
	int spriteNum = 1;
	
	public boolean isActioning = false;
	int actionCounter = 0;
	int actionSpriteNum = 1;
	public boolean hasHoe = false;
	
	// Inventory
	public final int maxInventorySize = 5;
	public SuperObject[] inventory = new SuperObject[maxInventorySize];
	public int inventoryCount = 0;
	public boolean hasSeedPacket = false;
	public int harvestCount = 0;
	
	BufferedImage actionUp1, actionUp2, actionDown1, actionDown2;
	BufferedImage actionLeft1, actionLeft2, actionRight1, actionRight2;


	public Player(Gamepanel gp, KeyHandler keyH) {
		super(gp);
		this.gp = gp;
		this.keyH = keyH;

		solidArea = new Rectangle(8,16,32,32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		screenX = gp.screenWidth / 2 - (gp.tileSize * 3) / 2;
		screenY = gp.screenHeight / 2 - (gp.tileSize * 3) / 2;
		setDefaultValues();
		getPlayerImage();
		getActionImage();
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 19;
		worldY = gp.tileSize * 19;
		speed = 3;
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

	public void getActionImage() {
		try {
			actionUp1 = ImageIO.read(getClass().getResourceAsStream("/res/player/action/up_1.png"));
			actionUp2 = ImageIO.read(getClass().getResourceAsStream("/res/player/action/up_2.png"));
			actionDown1 = ImageIO.read(getClass().getResourceAsStream("/res/player/action/down_1.png"));
			actionDown2 = ImageIO.read(getClass().getResourceAsStream("/res/player/action/down_2.png"));
			actionLeft1 = ImageIO.read(getClass().getResourceAsStream("/res/player/action/left_1.png"));
			actionLeft2 = ImageIO.read(getClass().getResourceAsStream("/res/player/action/left_2.png"));
			actionRight1 = ImageIO.read(getClass().getResourceAsStream("/res/player/action/right_1.png"));
			actionRight2 = ImageIO.read(getClass().getResourceAsStream("/res/player/action/right_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update() {
		// Check for object pickup
		int objIndex = gp.cChecker.checkObject(this);
		pickUpObject(objIndex);
		
		// Handle action animation (only if player has hoe)
		if (keyH.ePressed && !isActioning && hasHoe) {
			isActioning = true;
			actionCounter = 0;
			actionSpriteNum = 1; 
			keyH.ePressed = false;
			
			// Convert grass to tilled dirt in front of player
			convertGrassToTilledDirt();
		} else if (keyH.ePressed && !hasHoe) {
			keyH.ePressed = false; // Consume the key press even without hoe
		}
		
		// Handle planting with R key (infinite if we have seed packet)
		if (keyH.rPressed && hasSeedPacket) {
			plantSeed();
			keyH.rPressed = false;
		} else if (keyH.rPressed) {
			keyH.rPressed = false; // Consume the key press even without seed packet
		}
		
		// Handle harvesting with T key
		if (keyH.tPressed) {
			harvestPlant();
			keyH.tPressed = false;
		}
		
		if (isActioning) {
			actionCounter++;
			if (actionCounter > 12) { 
				if (actionSpriteNum == 1) {
					actionSpriteNum = 2; 
				} else {

					isActioning = false;
					actionSpriteNum = 1; 
				}
				actionCounter = 0;
			}
			return; 
		}
		
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
			if (keyH.upPressed == true) {
				direction = "up";
			}
			if (keyH.downPressed == true) { 
				direction = "down";
			}
			if (keyH.leftPressed == true) {
				direction = "left";
			}
			if (keyH.rightPressed == true) {
				direction = "right";
			}

			collisionOn = false;
			gp.cChecker.checkTile(this);

			if (collisionOn == false) {
				switch (direction) {
					case "up":
						worldY -= speed;
						break;
					case "down":
						worldY += speed;
						break;
					case "left":
						worldX -= speed;
						break;
					case "right":
						worldX += speed;
						break;
				}
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

		if (isActioning) {
			switch (direction) {
				case "up":
					image = (actionSpriteNum == 2) ? actionUp2 : actionUp1;
					break;
				case "down":
					image = (actionSpriteNum == 2) ? actionDown2 : actionDown1;
					break;
				case "left":
					image = (actionSpriteNum == 2) ? actionLeft2 : actionLeft1;
					break;
				case "right":
					image = (actionSpriteNum == 2) ? actionRight2 : actionRight1;
					break;
			}
		} else {

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
		}


		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
	
	public void convertGrassToTilledDirt() {
		int playerCenterX = worldX + gp.tileSize / 2;
		int playerCenterY = worldY + gp.tileSize / 2;
		int tileCol = playerCenterX / gp.tileSize;
		int tileRow = playerCenterY / gp.tileSize;
		
		if (tileCol >= 0 && tileCol < gp.maxWorldCol && tileRow >= 0 && tileRow < gp.maxWorldRow) {
			int tileNum = gp.tileM.mapTileNum[tileCol][tileRow];
			if (gp.tileM.tile[tileNum].type != null && gp.tileM.tile[tileNum].type.equals("grass")) {
				gp.tileM.mapTileNum[tileCol][tileRow] = 4; // 4 is tilled dirt
			}
		}
	}
	
	public void plantSeed() {
		int playerCenterX = worldX + gp.tileSize / 2;
		int playerCenterY = worldY + gp.tileSize / 2;
		int tileCol = playerCenterX / gp.tileSize;
		int tileRow = playerCenterY / gp.tileSize;
		
		if (tileCol >= 0 && tileCol < gp.maxWorldCol && tileRow >= 0 && tileRow < gp.maxWorldRow) {
			int tileNum = gp.tileM.mapTileNum[tileCol][tileRow];
			// Can only plant on tilled dirt (tile 4)
			if (gp.tileM.tile[tileNum].type != null && gp.tileM.tile[tileNum].type.equals("tilled")) {
				// Plant the seed - set to plant stage 1 (tile 5)
				gp.tileM.mapTileNum[tileCol][tileRow] = 5;
				// No decrement - seed packet has infinite seeds
				
				// Add plant to the growth tracking list
				Plant newPlant = new Plant(tileCol, tileRow);
				gp.addPlant(newPlant);
			}
		}
	}
	
	public void harvestPlant() {
		int playerCenterX = worldX + gp.tileSize / 2;
		int playerCenterY = worldY + gp.tileSize / 2;
		int tileCol = playerCenterX / gp.tileSize;
		int tileRow = playerCenterY / gp.tileSize;
		
		if (tileCol >= 0 && tileCol < gp.maxWorldCol && tileRow >= 0 && tileRow < gp.maxWorldRow) {
			int tileNum = gp.tileM.mapTileNum[tileCol][tileRow];
			// Can only harvest fully grown plants (tile 7)
			if (gp.tileM.tile[tileNum].type != null && gp.tileM.tile[tileNum].type.equals("plant_fully_grown")) {
				// Harvest the plant - reset tile to tilled dirt
				gp.tileM.mapTileNum[tileCol][tileRow] = 4;
				harvestCount++;
				
				// Add plant pickup to inventory (stackable)
				addHarvestToInventory();
				
				// Remove plant from tracking list
				gp.removePlant(tileCol, tileRow);
			}
		}
	}
	
	public void addHarvestToInventory() {
		// Check if we already have a plant pickup stack in inventory
		for (int i = 0; i < inventoryCount; i++) {
			if (inventory[i] != null && inventory[i].name.equals("Plant Pickup")) {
				// Found existing stack, increment count
				inventory[i].stackCount++;
				return;
			}
		}
		
		// No existing stack, create new one if there's space
		if (inventoryCount < maxInventorySize) {
			object.OBJ_plant_pickup pickup = new object.OBJ_plant_pickup();
			inventory[inventoryCount] = pickup;
			inventoryCount++;
		}
	}
	
	public void pickUpObject(int index) {
		if (index != 999) {
			String objectName = gp.obj[index].name;
			
			if (objectName.equals("Hoe")) {
				hasHoe = true;
				// Add to inventory if there's space
				if (inventoryCount < maxInventorySize) {
					inventory[inventoryCount] = gp.obj[index];
					inventoryCount++;
				}
				gp.obj[index] = null; // Remove the hoe from the world
			}
			else if (objectName.equals("Seed Packet")) {
				hasSeedPacket = true;
				// Add to inventory if there's space
				if (inventoryCount < maxInventorySize) {
					inventory[inventoryCount] = gp.obj[index];
					inventoryCount++;
				}
				gp.obj[index] = null; // Remove the seed packet from the world
			}
		}
	}
}
