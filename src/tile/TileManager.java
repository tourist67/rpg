package tile;
 
import main.Gamepanel;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class TileManager {
  
  Gamepanel gp;
  public Tile[] tile;
  public int mapTileNum[][];
  Random random = new Random();

  public TileManager(Gamepanel gp) {
    this.gp = gp;
    tile = new Tile[30];
    mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
    getTileImage();
    loadMap();
    randomizeGrass();
  }

  public void loadMap() {
    try {
        InputStream is = getClass().getResourceAsStream("/res/maps/map01.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
            String line = br.readLine();
            if (line == null) {
              break;
            }
            while(col < gp.maxWorldCol) {
              String numbers[] = line.split(" ");
              int num = Integer.parseInt(numbers[col]);
              mapTileNum[col][row] = num;
              col++;
            }
            if(col == gp.maxWorldCol) {
              col = 0;
              row++;
            }
        }
    }catch(Exception e) {
        e.printStackTrace();
    }
  }

  public void getTileImage() {
    try {
      // 0: Grass center
      tile[0] = new Tile();
      tile[0].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass1.png"));
      tile[0].type = "grass";

      // 1: Dirt
      tile[1] = new Tile();
      tile[1].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/dirt/dirt1.png"));
      tile[1].type = "dirt";

      // 2: Wood (house wall)
      tile[2] = new Tile();
      tile[2].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/house/wood1.png"));
      tile[2].collision = true;

      // 3: Water
      tile[3] = new Tile();
      tile[3].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/water.png"));
      tile[3].collision = true;

      // 4: Tilled dirt (created by hoe) - plantable
      tile[4] = new Tile();
      tile[4].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/dirt/dirt1.png"));
      tile[4].type = "tilled";

      // 5-7: Plant growth stages
      tile[5] = new Tile();
      tile[5].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/plants/plant_state_1.png"));
      tile[5].type = "plant_stage1";

      tile[6] = new Tile();
      tile[6].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/plants/plant_state_2.png"));
      tile[6].type = "plant_stage2";

      tile[7] = new Tile();
      tile[7].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/plants/plant_state_3.png"));
      tile[7].type = "plant_fully_grown";

      // 8: Grass top edge
      tile[8] = new Tile();
      tile[8].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass_top.png"));
      tile[8].type = "grass";

      // 9: Grass bottom edge
      tile[9] = new Tile();
      tile[9].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass_bottom.png"));
      tile[9].type = "grass";

      // 10: Grass left edge
      tile[10] = new Tile();
      tile[10].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass_left.png"));
      tile[10].type = "grass";

      // 11: Grass right edge
      tile[11] = new Tile();
      tile[11].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass_right.png"));
      tile[11].type = "grass";

      // 12: Grass left top corner
      tile[12] = new Tile();
      tile[12].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass_left_top_corner.png"));
      tile[12].type = "grass";

      // 13: Grass right top corner
      tile[13] = new Tile();
      tile[13].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass_right_top_corner.png"));
      tile[13].type = "grass";

      // 14: Grass left bottom corner
      tile[14] = new Tile();
      tile[14].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass_left_bottom_corner.png"));
      tile[14].type = "grass";

      // 15: Grass right bottom corner
      tile[15] = new Tile();
      tile[15].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass_right_bottom_corner.png"));
      tile[15].type = "grass";

      // 16: Grass variant 2
      tile[16] = new Tile();
      tile[16].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass2.png"));
      tile[16].type = "grass";

      // 17: Grass variant 3
      tile[17] = new Tile();
      tile[17].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass3.png"));
      tile[17].type = "grass";

      // 18: Grass variant 4
      tile[18] = new Tile();
      tile[18].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass4.png"));
      tile[18].type = "grass";

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  // Randomize grass tiles (tile 0) to add variety with grass2, 3, 4
  public void randomizeGrass() {
    for (int col = 0; col < gp.maxWorldCol; col++) {
      for (int row = 0; row < gp.maxWorldRow; row++) {
        // Only randomize base grass tiles (tile 0)
        if (mapTileNum[col][row] == 0) {
          int chance = random.nextInt(100);
          if (chance < 60) {
            // 60% chance: keep grass1 (tile 0)
            mapTileNum[col][row] = 0;
          } else if (chance < 80) {
            // 20% chance: grass2 (tile 16)
            mapTileNum[col][row] = 16;
          } else if (chance < 92) {
            // 12% chance: grass3 (tile 17)
            mapTileNum[col][row] = 17;
          } else {
            // 8% chance: grass4 (tile 18)
            mapTileNum[col][row] = 18;
          }
        }
      }
    }
  }

  public void draw(Graphics2D g2) {
    // Fill the screen with water color for areas outside the map
    g2.setColor(new java.awt.Color(133, 194, 194)); // Water color
    g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    
    int worldCol = 0;
    int worldRow = 0;

    while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
        int tileNum = mapTileNum[worldCol][worldRow];

        int worldX = worldCol * gp.tileSize;
        int worldY = worldRow * gp.tileSize;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        
        // Only draw tiles that are visible on screen
        if (screenX + gp.tileSize > 0 &&
            screenX < gp.screenWidth &&
            screenY + gp.tileSize > 0 &&
            screenY < gp.screenHeight) {
            
            // For plant tiles (5, 6, 7), draw tilled dirt first as background
            if (tileNum >= 5 && tileNum <= 7) {
                g2.drawImage(tile[4].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
            
            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }

        worldCol++;

        if(worldCol == gp.maxWorldCol) {
            worldCol = 0;
            worldRow++;
        }
    }
  }
}
