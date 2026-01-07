package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.io.IOException;
import java.io.InputStream;
import entity.Entity;

public class UI {
    Gamepanel gp;
    Font customFont;
    Font font_40;
    Font font_20;
    Font font_30;
    Font font_60;
    public String currentDialogue = "";
    
    // Title screen
    public int titleMenuSelection = 0; // 0 = Play, 1 = Quit
    public int titleSpriteCounter = 0;
    public int titleSpriteNum = 1;
    
    // Pause menu
    public int pauseMenuSelection = 0; // 0 = Music, 1 = Back to Game, 2 = Return to Menu
    public int musicVolume = 3; // 0-5 scale
    
    // Typing effect variables
    private String fullDialogue = "";
    private int charIndex = 0;
    private int typingCounter = 0;
    private int typingSpeed = 2; // Lower = faster typing
    public boolean dialogueFinished = false;
    
    // Track current speaking NPC for sequential dialogues
    public Entity currentNPC = null;
    public boolean dialogueSequenceComplete = true;

    public UI(Gamepanel gp) {
        this.gp = gp;
        
        // Load custom font
        try {
            InputStream is = getClass().getResourceAsStream("/res/fonts/new_font.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            font_60 = customFont.deriveFont(60f);
            font_40 = customFont.deriveFont(40f);
            font_30 = customFont.deriveFont(30f);
            font_20 = customFont.deriveFont(20f);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // Fallback to Arial if custom font fails
            font_60 = new Font("Arial", Font.BOLD, 60);
            font_40 = new Font("Arial", Font.BOLD, 40);
            font_30 = new Font("Arial", Font.BOLD, 30);
            font_20 = new Font("Arial", Font.PLAIN, 20);
        }
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.titleState) {
            drawTitleScreen(g2);
        } else {
            // Always draw inventory
            drawInventory(g2);
            
            // Always draw money UI
            drawMoneyUI(g2);
            
            if (gp.gameState == gp.pauseState) {
                drawPauseScreen(g2);
            }
            if (gp.gameState == gp.dialogueState) {
                drawDialogueScreen(g2);
            }
        }
    }
    
    public void drawTitleScreen(Graphics2D g2) {
        // Black background
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        // Title text
        g2.setFont(font_60);
        String title = "Whiskerfield";
        int titleWidth = (int) g2.getFontMetrics().getStringBounds(title, g2).getWidth();
        int titleX = gp.screenWidth / 2 - titleWidth / 2;
        int titleY = gp.tileSize * 3;
        
        // Shadow
        g2.setColor(Color.gray);
        g2.drawString(title, titleX + 4, titleY + 4);
        // Main title
        g2.setColor(Color.white);
        g2.drawString(title, titleX, titleY);
        
        // Animate player sprite
        titleSpriteCounter++;
        if (titleSpriteCounter > 20) {
            titleSpriteNum = (titleSpriteNum == 1) ? 2 : 1;
            titleSpriteCounter = 0;
        }
        
        // Draw player sprite (centered properly)
        int spriteSize = gp.tileSize * 2;
        int spriteX = gp.screenWidth / 2 - spriteSize / 2;
        int spriteY = titleY + gp.tileSize;
        if (titleSpriteNum == 1) {
            g2.drawImage(gp.player.down1, spriteX, spriteY, spriteSize, spriteSize, null);
        } else {
            g2.drawImage(gp.player.down2, spriteX, spriteY, spriteSize, spriteSize, null);
        }
        
        // Menu options
        g2.setFont(font_30);
        int menuY = spriteY + gp.tileSize * 3 + 20;
        
        // Play option
        String playText = "PLAY";
        int playWidth = (int) g2.getFontMetrics().getStringBounds(playText, g2).getWidth();
        int playX = gp.screenWidth / 2 - playWidth / 2;
        g2.setColor(Color.white);
        if (titleMenuSelection == 0) {
            g2.drawString(">", playX - 30, menuY);
        }
        g2.drawString(playText, playX, menuY);
        
        // Quit option
        String quitText = "QUIT";
        int quitWidth = (int) g2.getFontMetrics().getStringBounds(quitText, g2).getWidth();
        int quitX = gp.screenWidth / 2 - quitWidth / 2;
        int quitY = menuY + 50;
        g2.setColor(Color.white);
        if (titleMenuSelection == 1) {
            g2.drawString(">", quitX - 30, quitY);
        }
        g2.drawString(quitText, quitX, quitY);
    }

    public void drawInventory(Graphics2D g2) {
        int slotSize = gp.tileSize;
        int startX = gp.screenWidth / 2 - (slotSize * gp.player.maxInventorySize) / 2;
        int startY = gp.screenHeight - slotSize - 10;
        
        for (int i = 0; i < gp.player.maxInventorySize; i++) {
            int x = startX + (i * slotSize);
            int y = startY;
            
            // Draw slot background
            g2.setColor(new Color(50, 50, 50, 200));
            g2.fillRect(x, y, slotSize, slotSize);
            
            // Draw slot border
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(x, y, slotSize, slotSize);
            
            // Draw item if present
            if (gp.player.inventory[i] != null) {
                g2.drawImage(gp.player.inventory[i].image, x + 4, y + 4, slotSize - 8, slotSize - 8, null);
                
                // Draw stack count if stackable and count > 1
                if (gp.player.inventory[i].stackable && gp.player.inventory[i].stackCount > 1) {
                    g2.setFont(font_20);
                    g2.setColor(Color.white);
                    String countText = String.valueOf(gp.player.inventory[i].stackCount);
                    g2.drawString(countText, x + slotSize - 15, y + slotSize - 5);
                }
            }
        }
    }

    public void drawPauseScreen(Graphics2D g2) {
        // Draw semi-transparent overlay
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        
        // Options box dimensions
        int boxWidth = gp.tileSize * 6;
        int boxHeight = gp.tileSize * 5;
        int boxX = gp.screenWidth / 2 - boxWidth / 2;
        int boxY = gp.screenHeight / 2 - boxHeight / 2;
        
        // Draw options box background
        g2.setColor(new Color(30, 30, 30, 240));
        g2.fillRoundRect(boxX, boxY, boxWidth, boxHeight, 20, 20);
        
        // Draw border
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(boxX + 3, boxY + 3, boxWidth - 6, boxHeight - 6, 18, 18);
        
        // Draw "Options" title
        g2.setFont(font_30);
        g2.setColor(Color.white);
        String title = "Options";
        int titleWidth = (int) g2.getFontMetrics().getStringBounds(title, g2).getWidth();
        g2.drawString(title, boxX + (boxWidth - titleWidth) / 2, boxY + 45);
        
        // Draw Music option (0)
        g2.setFont(font_20);
        int optionX = boxX + 30;
        int musicY = boxY + 90;
        
        // Selection indicator for Music
        g2.setColor(Color.white);
        if (pauseMenuSelection == 0) {
            g2.drawString(">", optionX - 20, musicY);
        }
        g2.drawString("Music", optionX, musicY);
        
        // Draw volume bar
        int barX = optionX + 80;
        int barY = musicY - 15;
        int barWidth = 100;
        int barHeight = 20;
        
        // Bar background
        g2.setColor(new Color(60, 60, 60));
        g2.fillRect(barX, barY, barWidth, barHeight);
        
        // Bar fill based on volume
        g2.setColor(Color.white);
        int fillWidth = (int) (barWidth * (musicVolume / 5.0));
        g2.fillRect(barX, barY, fillWidth, barHeight);
        
        // Bar border
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(barX, barY, barWidth, barHeight);
        
        // Draw Return to Menu option (1)
        int menuY = musicY + 40;
        g2.setColor(Color.white);
        if (pauseMenuSelection == 1) {
            g2.drawString(">", optionX - 20, menuY);
        }
        g2.drawString("Return to Menu", optionX, menuY);
        
        // Draw Back to Game option (2)
        int backY = menuY + 40;
        g2.setColor(Color.white);
        if (pauseMenuSelection == 2) {
            g2.drawString(">", optionX - 20, backY);
        }
        g2.drawString("Back to Game", optionX, backY);
    }
    
    public void drawDialogueScreen(Graphics2D g2) {
        // Dialogue window position and size
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        
        // Draw semi-transparent black box
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRoundRect(x, y, width, height, 35, 35);
        
        // Draw white border
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
        
        // Update typing effect
        if (charIndex < fullDialogue.length()) {
            typingCounter++;
            if (typingCounter >= typingSpeed) {
                charIndex++;
                currentDialogue = fullDialogue.substring(0, charIndex);
                typingCounter = 0;
            }
        } else if (charIndex > 0 && charIndex == fullDialogue.length() && !dialogueFinished) {
            // Dialogue finished typing - stop the music
            dialogueFinished = true;
            gp.stopDialogueMusic();
        }
        
        // Draw dialogue text centered
        g2.setFont(font_20);
        int textWidth = (int) g2.getFontMetrics().getStringBounds(currentDialogue, g2).getWidth();
        int textX = x + (width - textWidth) / 2;
        int textY = y + height / 2;
        g2.drawString(currentDialogue, textX, textY);
    }
    
    // Call this method to start a new dialogue with typing effect
    public void setDialogue(String dialogue) {
        if (!dialogue.equals(fullDialogue)) {
            fullDialogue = dialogue;
            currentDialogue = "";
            charIndex = 0;
            typingCounter = 0;
            dialogueFinished = false;
        }
    }
    
    // Reset dialogue state when dialogue ends
    public void resetDialogue() {
        fullDialogue = "";
        currentDialogue = "";
        charIndex = 0;
        typingCounter = 0;
        dialogueFinished = false;
        currentNPC = null;
        dialogueSequenceComplete = true;
    }
    
    public void drawMoneyUI(Graphics2D g2) {
        // Position at top left
        int x = 10;
        int y = 10;
        int width = gp.tileSize * 3;
        int height = gp.tileSize;
        
        // Draw semi-transparent rounded box
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRoundRect(x, y, width, height, 20, 20);
        
        // Draw gold border
        g2.setColor(new Color(255, 215, 0));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x + 2, y + 2, width - 4, height - 4, 18, 18);
        
        // Draw money icon ($)
        g2.setFont(font_20);
        g2.setColor(new Color(255, 215, 0));
        g2.drawString("$", x + 12, y + height / 2 + 8);
        
        // Draw money amount
        g2.setFont(font_20);
        g2.setColor(Color.white);
        g2.drawString(String.valueOf(gp.player.money), x + 35, y + height / 2 + 7);
    }
}
