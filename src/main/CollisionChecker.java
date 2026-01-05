package main;

import entity.Entity;

public class CollisionChecker {
  Gamepanel gp;

  public CollisionChecker(Gamepanel gp) {
    this.gp = gp;

    
  }

  public void checkTile(Entity entity) {
    int entityLeftWorldX = entity.worldX + entity.solidArea.x;
      int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
      int entityTopWorldY = entity.worldY + entity.solidArea.y;
      int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

      int entityLeftCol = entityLeftWorldX / gp.tileSize;
      int entityRightCol = entityRightWorldX / gp.tileSize;
      int entityTopRow = entityTopWorldY / gp.tileSize;
      int entityBottomRow = entityBottomWorldY / gp.tileSize;

      int tileNum1, tileNum2;

      switch(entity.direction) {
        case "up":
          entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
          tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
          tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
          if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
            entity.collisionOn = true;
          }
          break;
        case "down":
          entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
          tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
          tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
          if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
            entity.collisionOn = true;
          }
          break;
        case "left":
          entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
          tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
          tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
          if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
            entity.collisionOn = true;
          }
          break;
        case "right":
          entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
          tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
          tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
          if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
            entity.collisionOn = true;
          }
          break;
      }
  }

  public int checkObject(Entity entity) {
    int index = 999; // 999 means no object found
    
    for (int i = 0; i < gp.obj.length; i++) {
      if (gp.obj[i] != null) {
        // Get entity's solid area position
        int entitySolidAreaX = entity.worldX + entity.solidArea.x;
        int entitySolidAreaY = entity.worldY + entity.solidArea.y;
        
        // Get object's solid area (using full tile size)
        int objSolidAreaX = gp.obj[i].worldX;
        int objSolidAreaY = gp.obj[i].worldY;
        
        // Check if player overlaps with object
        if (entitySolidAreaX < objSolidAreaX + gp.tileSize &&
            entitySolidAreaX + entity.solidArea.width > objSolidAreaX &&
            entitySolidAreaY < objSolidAreaY + gp.tileSize &&
            entitySolidAreaY + entity.solidArea.height > objSolidAreaY) {
          index = i;
          break;
        }
      }
    }
    return index;
  }

}
