package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.Gamepanel;

public class NPC2 extends Entity {

    private BufferedImage idle1, idle2;

    public NPC2(Gamepanel gp) {
        super(gp);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 19;
        worldY = gp.tileSize * 8;
        speed = 0; // Stationary NPC
        direction = "down";
        
        setDialogue();
    }
    
    public void setDialogue() {
        dialogues[0] = "Welcome to Whiskerfield!";
        dialogues[1] = "Press E to soil the ground with your hoe.";
        dialogues[2] = "Then press R to plant and T to harvest.";
        dialogues[3] = "Sell your fruits n get rich!";
    }

    public void getImage() {
        try {
            idle1 = ImageIO.read(getClass().getResourceAsStream("/res/npc/npc2/idle_1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/res/npc/npc2/idle_2.png"));
            // Use idle sprites for all directions since this NPC just stands still
            up1 = idle1;
            up2 = idle2;
            down1 = idle1;
            down2 = idle2;
            left1 = idle1;
            left2 = idle2;
            right1 = idle1;
            right2 = idle2;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        // Sprite animation - cycle through idle animations
        spriteCounter++;
        if (spriteCounter > 20) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = (spriteNum == 1) ? idle1 : idle2;
        
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // Only draw if on screen
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
            worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
            worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
            worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }

    @Override
    public void speak() {
        // Track this NPC as the current speaker
        gp.ui.currentNPC = this;
        
        // Show current dialogue
        if (dialogues[dialogueIndex] != null) {
            gp.ui.setDialogue(dialogues[dialogueIndex]);
            dialogueIndex++;
            
            // Check if we've reached the end of dialogues
            if (dialogueIndex >= 4 || dialogues[dialogueIndex] == null) {
                // Sequence complete - player can exit after this message
                gp.ui.dialogueSequenceComplete = true;
                dialogueIndex = 0; // Reset for next time
            } else {
                // More dialogues to come
                gp.ui.dialogueSequenceComplete = false;
            }
        }
        gp.gameState = gp.dialogueState;
        
        // Play dialogue music
        gp.playDialogueMusic();
    }
}
