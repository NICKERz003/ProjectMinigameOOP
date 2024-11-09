/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monster;

import entity.Entity;
import java.awt.image.BufferedImage;
import java.util.Random;
import main.GamePanel;
import object.HeartItem;
import object.OBJ_Boots;
import object.OBJ_Fire;
import object.PowerBombItem;

public class PacMon extends Entity {

    GamePanel gp;

    public PacMon(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = 2;
        name = "PacMon";
        direction = "down";
        speed = 2;
        maxLife = 5;
        life = maxLife;
        attack = 1;
        defense = 0;
        projectile = new OBJ_Fire(gp);

        solidArea.x = 3;
        solidArea.y = 10;
        solidArea.width = 42;
        solidArea.height = 38;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = setup("/monster/MonLeft1");
        up2 = setup("/monster/MonLeft2");
        up3 = setup("/monster/MonLeft3");
        down1 = setup("/monster/MonRight1");
        down2 = setup("/monster/MonRight2");
        down3 = setup("/monster/MonRight3");
        left1 = setup("/monster/MonLeft1");
        left2 = setup("/monster/MonLeft2");
        left3 = setup("/monster/MonLeft3");
        right1 = setup("/monster/MonRight1");
        right2 = setup("/monster/MonRight2");
        right3 = setup("/monster/MonRight3");
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter >= 60) {
            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up a number from 1 to 100
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
        int i = new Random().nextInt(100) + 1;
        if (i > 99 && projectile.alive == false && bombAvailableCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            bombAvailableCounter = 0;
        }
    }

    @Override
    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction;

        // Check if the monster's life is zero or less
        if (life <= 0) {
            dropItem(); // Call the dropItem method when the monster dies
            alive = false; // Set alive to false
            dying = true; // Set dying to true to handle dying animation
        }
    }

    public void dropItem() {
        Random random = new Random();
        int dropChance = random.nextInt(100); // Random number between 0-99

        if (dropChance < 50) { // 50% chance to drop an item
            // Create a new instance of the item you want to drop
            Entity droppedItem = null;
            int itemType = random.nextInt(3); // Randomly choose an item type (0, 1, or 2)

            switch (itemType) {
                case 0:
                    droppedItem = new HeartItem(gp);
                    break;
                case 1:
                    droppedItem = new OBJ_Boots(gp);
                    break;
                case 2:
                    droppedItem = new PowerBombItem(gp);
                    break;
            }

            // Set the dropped item's position to the monster's position
            if (droppedItem != null) {
                droppedItem.worldX = this.worldX;
                droppedItem.worldY = this.worldY;
                gp.obj[gp.getAvailableObjectIndex()] = droppedItem; // Add the item to the game panel's object array
            }
        }
    }
    


}
