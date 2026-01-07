package main;

import entity.NPC1;
import entity.NPC2;
import object.OBJ_Decor;
import java.util.Random;

public class AssetSetter {
  Gamepanel gp;
  Random random = new Random();

  public AssetSetter(Gamepanel gp) {
    this.gp = gp;
  }

  public void setObject() {
    gp.obj[0] = new object.OBJ_hoe();
    gp.obj[0].worldX = gp.tileSize * 22;
    gp.obj[0].worldY = gp.tileSize * 13;

    gp.obj[1] = new object.OBJ_seed();
    gp.obj[1].worldX = gp.tileSize * 19;
    gp.obj[1].worldY = gp.tileSize * 13;
    
    // Place decorations
    setDecorations();
  }
  
  public void setDecorations() {
    int objIndex = 2; // Start after hoe and seed
    
    // Track used positions to avoid stacking
    java.util.HashSet<String> usedPositions = new java.util.HashSet<>();
    
    // Helper to check and mark position
    // Returns true if position is available
    java.util.function.BiFunction<Integer, Integer, Boolean> tryPosition = (col, row) -> {
      String key = col + "," + row;
      if (usedPositions.contains(key)) return false;
      usedPositions.add(key);
      return true;
    };
    
    // Place trees around the edges
    int[][] edgeTrees = {
      // Top edge (avoid house cols 16-26)
      {14, 9}, {28, 9}, {32, 9},
      // Bottom edge
      {14, 37}, {18, 37}, {22, 37}, {26, 37}, {30, 37}, {34, 37},
      // Left edge
      {13, 11}, {13, 16}, {13, 21}, {13, 26}, {13, 31}, {13, 36},
      // Right edge
      {35, 11}, {35, 16}, {35, 21}, {35, 26}, {35, 31}, {35, 36}
    };
    for (int[] pos : edgeTrees) {
      if (objIndex < gp.obj.length && tryPosition.apply(pos[0], pos[1])) {
        gp.obj[objIndex] = new OBJ_Decor(random.nextBoolean() ? "tree" : "tree_1");
        gp.obj[objIndex].worldX = gp.tileSize * pos[0];
        gp.obj[objIndex].worldY = gp.tileSize * pos[1];
        objIndex++;
      }
    }
    
    // Interior trees (scattered inside the farm, avoid house and dirt)
    int[][] interiorTrees = {
      // Upper right area
      {29, 12}, {33, 14},
      // Middle area (avoid house 16-26, 10-15)
      {14, 18}, {28, 18}, {32, 22},
      // Lower middle
      {27, 24}, {33, 28}, {14, 26},
      // Lower area (avoid dirt patch 15-23, 28-34)
      {25, 30}, {30, 32}, {14, 34}
    };
    for (int[] pos : interiorTrees) {
      if (objIndex < gp.obj.length && tryPosition.apply(pos[0], pos[1])) {
        gp.obj[objIndex] = new OBJ_Decor(random.nextBoolean() ? "tree" : "tree_1");
        gp.obj[objIndex].worldX = gp.tileSize * pos[0];
        gp.obj[objIndex].worldY = gp.tileSize * pos[1];
        objIndex++;
      }
    }
    
    // Sunflowers - spread out nicely
    int[][] sunflowerPositions = {
      {27, 10}, {31, 11}, {34, 13},
      {29, 17}, {33, 19}, {31, 23},
      {28, 27}, {32, 30}, {34, 34},
      {26, 36}, {30, 38}, {22, 38}
    };
    for (int[] pos : sunflowerPositions) {
      if (objIndex < gp.obj.length && tryPosition.apply(pos[0], pos[1])) {
        gp.obj[objIndex] = new OBJ_Decor("sunflower");
        gp.obj[objIndex].worldX = gp.tileSize * pos[0];
        gp.obj[objIndex].worldY = gp.tileSize * pos[1];
        objIndex++;
      }
    }
    
    // Pink plants - spread out
    int[][] pinkPlantPositions = {
      {14, 10}, {15, 17}, {14, 24},
      {27, 15}, {33, 21}, {30, 26},
      {25, 36}, {18, 38}, {34, 38}
    };
    for (int[] pos : pinkPlantPositions) {
      if (objIndex < gp.obj.length && tryPosition.apply(pos[0], pos[1])) {
        gp.obj[objIndex] = new OBJ_Decor("pink_plant");
        gp.obj[objIndex].worldX = gp.tileSize * pos[0];
        gp.obj[objIndex].worldY = gp.tileSize * pos[1];
        objIndex++;
      }
    }
    
    // Rocks - scattered naturally
    int[][] rockPositions = {
      {15, 12}, {16, 20}, {15, 28},
      {28, 14}, {32, 17}, {29, 22}, {34, 25},
      {27, 29}, {31, 33}, {24, 36},
      {16, 36}, {20, 38}
    };
    for (int[] pos : rockPositions) {
      if (objIndex < gp.obj.length && tryPosition.apply(pos[0], pos[1])) {
        gp.obj[objIndex] = new OBJ_Decor(random.nextBoolean() ? "rock_1" : "rock_2");
        gp.obj[objIndex].worldX = gp.tileSize * pos[0];
        gp.obj[objIndex].worldY = gp.tileSize * pos[1];
        objIndex++;
      }
    }
    
    // Tall grass - fill in spaces with variety
    int[][] tallGrassPositions = {
      // Top area
      {15, 9}, {30, 10}, {33, 11}, {27, 13},
      // Upper middle
      {14, 15}, {16, 17}, {28, 16}, {31, 18}, {34, 20},
      // Middle
      {15, 21}, {17, 23}, {29, 21}, {32, 24}, {27, 25},
      // Lower middle
      {14, 27}, {16, 29}, {28, 28}, {33, 31}, {30, 29},
      // Bottom
      {15, 35}, {17, 37}, {19, 36}, {23, 37}, {27, 38},
      {29, 36}, {32, 38}, {34, 36}
    };
    for (int[] pos : tallGrassPositions) {
      if (objIndex < gp.obj.length && tryPosition.apply(pos[0], pos[1])) {
        gp.obj[objIndex] = new OBJ_Decor(random.nextBoolean() ? "tall_grass_1" : "tall_grass_2");
        gp.obj[objIndex].worldX = gp.tileSize * pos[0];
        gp.obj[objIndex].worldY = gp.tileSize * pos[1];
        objIndex++;
      }
    }
  }

  public void setNPC() {
    gp.npc[0] = new NPC1(gp);
    gp.npc[1] = new NPC2(gp); // Tutorial NPC at entrance
  }
}
 