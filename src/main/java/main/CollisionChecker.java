/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import java.awt.Rectangle;

/**
 *
 * @author DYWSK03
 */
public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public boolean checkTile(Entity entity) {

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;
        boolean collisionDetected = false;

        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                    collisionDetected = tileNum1 != 0 && tileNum2 != 0;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                    collisionDetected = tileNum1 != 0 && tileNum2 != 0;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                    collisionDetected = tileNum1 != 0 && tileNum2 != 0;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
                    entity.collisionOn = true;
                    collisionDetected = tileNum1 != 0 && tileNum2 != 0;
                }
                break;
        }
        return collisionDetected;
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {

                //Get entity's solid area posiiion
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //Get object's solid area posiiion
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                    if (gp.obj[i].collision == true) {
                        entity.collisionOn = true;
                    }
                    if (player == true) {
                        index = i;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;

    }

    // MONSTER
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {

            if (target[i] != null) {

                //Get entity's solid area posiiion
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                //Get object's solid area posiiion
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(target[i].solidArea)) {
                    if (target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;

    }

    public boolean checkPlayer(Entity entity) {

        boolean contactPlayer = false;
        //Get entity's solid area posiiion
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Check for interaction with interactive tiles
        int iTileIndex = checkInteractiveTile(entity);
        if (iTileIndex != 999) {
            gp.player.damageInteractiveTile(iTileIndex); // Call method to damage or destroy tile
        }

        //Get object's solid area posiiion
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        gp.player2.solidArea.x = gp.player2.worldX + gp.player2.solidArea.x;
        gp.player2.solidArea.y = gp.player2.worldY + gp.player2.solidArea.y;

        switch (entity.direction) {
            case "up":
                entity.solidArea.y -= entity.speed;
                break;
            case "down":
                entity.solidArea.y += entity.speed;
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                break;
            case "right":
                entity.solidArea.x += entity.speed;
                break;
        }
        if (entity.solidArea.intersects(gp.player.solidArea) || entity.solidArea.intersects(gp.player2.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
        }

        if (entity.solidArea.intersects(gp.player.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
            if (!gp.player.invincible) {
                gp.player.damagePlayer(entity.attack);
                return true;
            }
        }

        if (entity.solidArea.intersects(gp.player2.solidArea)) {
            entity.collisionOn = true;
            contactPlayer = true;
            if (!gp.player2.invincible) {
                gp.player2.damagePlayer(entity.attack);
                return true;
            }
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        gp.player2.solidArea.x = gp.player2.solidAreaDefaultX;
        gp.player2.solidArea.y = gp.player2.solidAreaDefaultY;

        return contactPlayer;
    }

    public boolean checkPlayerHit(Entity attacker, Entity targetPlayer) {
        Rectangle attackerArea = new Rectangle(attacker.worldX + attacker.solidArea.x,
                attacker.worldY + attacker.solidArea.y,
                attacker.solidArea.width, attacker.solidArea.height);

        Rectangle targetArea = new Rectangle(targetPlayer.worldX + targetPlayer.solidArea.x,
                targetPlayer.worldY + targetPlayer.solidArea.y,
                targetPlayer.solidArea.width, targetPlayer.solidArea.height);

        return attackerArea.intersects(targetArea);
    }

    // New method to check interaction with InteractiveTiles
    public int checkInteractiveTile(Entity entity) {
        int index = 999;

        for (int i = 0; i < gp.iTile.length; i++) {
            if (gp.iTile[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get interactive tile's solid area position
                gp.iTile[i].solidArea.x = gp.iTile[i].worldX + gp.iTile[i].solidArea.x;
                gp.iTile[i].solidArea.y = gp.iTile[i].worldY + gp.iTile[i].solidArea.y;

                switch (entity.direction) {
                    case "up":
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        break;
                }
                if (entity.solidArea.intersects(gp.iTile[i].solidArea)) {
                    if (gp.iTile[i].destructible) {
                        index = i; // Store index of the interactive tile
                        entity.collisionOn = true; // Mark collision
                    }
                }

                // Reset solid area positions
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.iTile[i].solidArea.x = gp.iTile[i].solidAreaDefaultX;
                gp.iTile[i].solidArea.y = gp.iTile[i].solidAreaDefaultY;
            }
        }
        return index;
    }

}
