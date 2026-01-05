package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.Gamepanel;

public class Entity {
  public Gamepanel gp;
  public int worldX, worldY;
  public int speed;

  public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
  public String direction;

  public int spriteCounter = 0;
  public int spriteNum = 1;

  public Rectangle solidArea;
  public int solidAreaDefaultX, solidAreaDefaultY;
  public boolean collisionOn = false;

  public Entity(Gamepanel gp) {
    this.gp = gp;
  }

  public void update() {
    // Override in subclasses
  }

  public void draw(Graphics2D g2) {
    BufferedImage image = null;
    int screenX = worldX - gp.player.worldX + gp.player.screenX;
    int screenY = worldY - gp.player.worldY + gp.player.screenY;

    // Only draw if on screen
    if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
        worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
        worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
        worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

      switch (direction) {
        case "up":
          image = (spriteNum == 1) ? up1 : up2;
          break;
        case "down":
          image = (spriteNum == 1) ? down1 : down2;
          break;
        case "left":
          image = (spriteNum == 1) ? left1 : left2;
          break;
        case "right":
          image = (spriteNum == 1) ? right1 : right2;
          break;
      }
      g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
  }
}
