package tile;
 
import main.Gamepanel;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
  
  Gamepanel gp;
  Tile[] tile;
  int mapTileNum[][];

  public TileManager(Gamepanel gp) {
    this.gp = gp;
    tile = new Tile[10];
    mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
    getTileImage();
    loadMap();
  }

  public void loadMap() {
    try {
        InputStream is = getClass().getResourceAsStream("/res/maps/map01.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int col = 0;
        int row = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
            String line = br.readLine();

            while(col < gp.maxScreenCol) {
                String numbers[] = line.split(" ");
                int num = Integer.parseInt(numbers[col]);
                mapTileNum[col][row] = num;
                col++;
            }
            if(col == gp.maxScreenCol) {
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
      tile[0] = new Tile();
      tile[0].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/grass/grass1.png"));

      tile[1] = new Tile();
      tile[1].image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/res/tiles/dirt/dirt1.png"));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void draw(Graphics2D g2) {
    for (int row = 0; row < gp.maxScreenRow; row++) {
        for (int col = 0; col < gp.maxScreenCol; col++) {
            int tileNum = mapTileNum[col][row];
            int x = col * gp.tileSize;
            int y = row * gp.tileSize;
            g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
        }
    }
  }
}
