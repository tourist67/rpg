package entity;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import main.Gamepanel;

public class NPC_OldMan extends Entity {

    private Random random = new Random();
    private int actionLockCounter = 0;

    public NPC_OldMan(Gamepanel gp) {
        super(gp);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 21;
        worldY = gp.tileSize * 21;
        speed = 1;
        direction = "down";
    }

    public void getImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);

        // If collision is false, move
        if (!collisionOn) {
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

        // Sprite animation
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

    public void setAction() {
        actionLockCounter++;

        // Change direction every 120 frames (~2 seconds)
        if (actionLockCounter >= 120) {
            int i = random.nextInt(100) + 1; // 1-100

            if (i <= 25) {
                direction = "up";
            } else if (i <= 50) {
                direction = "down";
            } else if (i <= 75) {
                direction = "left";
            } else {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }
}
