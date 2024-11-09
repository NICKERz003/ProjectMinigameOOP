package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;
import object.OBJ_Bomb;
import object.OBJ_Boots;
import tile.Tile;

public class Player extends Entity {

    KeyHandler keyH;
    int standCounter = 0;
    public OBJ_Bomb projectile;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;
        solidArea = new Rectangle();
        solidArea.x = 12;
        solidArea.y = 24;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 28;
        solidArea.height = 20;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
    }

    public GamePanel getGamePanel() {
        return gp;
    }

    public void setDefaultPositions() {
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 3;
        direction = "down";
    }

    public void restoreLife() {
        life = maxLife;
        speed = 4;
        projectile.attack = 1;
        invincible = false;
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 2;
        worldY = gp.tileSize * 3;
        speed = 4;
        direction = "down";

        //PLAYER STATUS
        maxLife = 3;
        life = maxLife;
        strength = 1;
        attack = 1;
        projectile = new OBJ_Bomb(gp);
        getPlayerAttackImage();
    }

    public int getAttack() {
        return attack = strength * currentWeapon.attackValue;
    }

    public void getPlayerAttackImage() {
        attackRight1 = setup("/projectile/Bomb1");
        attackRight2 = setup("/projectile/Bomb2");
        attackDown1 = setup("/projectile/Bomb2");
        attackDown2 = setup("/projectile/Bomb2");
        attackLeft1 = setup("/projectile/Bomb1");
        attackLeft2 = setup("/projectile/Bomb2");
        attackUp1 = setup("/projectile/Bomb1");
        attackUp2 = setup("/projectile/Bomb2");
    }

    public void getPlayerImage() {

        up1 = setup("/player/up1");
        up2 = setup("/player/up2");
        up3 = setup("/player/up3");
        down1 = setup("/player/down1");
        down2 = setup("/player/down2");
        down3 = setup("/player/down3");
        left1 = setup("/player/left1");
        left2 = setup("/player/left2");
        left3 = setup("/player/left3");
        right1 = setup("/player/right1");
        right2 = setup("/player/right2");
        right3 = setup("/player/right3");

        CC1P_1 = setup("/player/CC1P_1");
        CC1P_2 = setup("/player/CC1P_2");
        CC1P_3 = setup("/player/CC1P_3");
        CC1P_4 = setup("/player/CC1P_4");
    }

    public BufferedImage setup(String imageName) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {

            image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {

        if (life <= 0 && !dying) {
            dying = true;
            dyingCounter = 0;
        }

        if (dying) {
            return;
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (attacking == true) {
            attacking();
        } else if (keyH.upPressed == true || keyH.downPressed == true
                || keyH.leftPressed == true || keyH.rightPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            } else if (keyH.downPressed == true) {
                direction = "down";
            } else if (keyH.leftPressed == true) {
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }

            // Check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            // Check Object collision
            int objindex = gp.cChecker.checkObject(this, true);
            pickupObject(objindex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonter(monsterIndex);

            // CHECK INTERACTIIVE TILE COLLISION
            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);

            // If collision is false, player can move
            if (collisionOn == false) {
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

            spriteCounter++;
            if (spriteCounter > 8) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        } else {
            standCounter++;

            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
        }

        if (gp.keyH.bomb1Pressed == true && projectile.alive == false && bombAvailableCounter == 30) {
            // SET DEFAULT COORDINATES , DIRECTION AND USER
            projectile.set(worldX, worldY, direction, true, this);
            // ADD IT TO THE LIST
            gp.projectileList.add(projectile);
            bombAvailableCounter = 0;
        }

        //This needs to be outside of key if statement!
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (bombAvailableCounter < 30) {
            bombAvailableCounter++;
        }
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (gp.player.life <= 0 && gp.ui.titleScreenState == 3) {
            gp.gameState = gp.gameOverState;
        }
        if (gp.player.life <= 0 && gp.player2.life <= 0) {
            gp.gameState = gp.gameOverState;
        }
    }

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // SAVE THE CURRENT worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeigh = solidArea.height;

            // Adjust player's worldX/Y for the attackArea
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            // Check monster collision with the updated worldX ,worldY and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            // After checking collission, restore the original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeigh;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            attacking = false;
        }
    }

    public void pickupObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;
            switch (objectName) {
                case "Boots":
                    speed += 2;
                    gp.obj[i] = null;
                    break;
                case "PowerBomb":
                    projectile.attack++;
                    gp.obj[i] = null;
                    break;
                case "HeartItem":
                    life++;
                    gp.obj[i] = null;
                    break;
            }
        }
    }

    public void contactMonter(int i) {
        if (i != 999) {
            if (invincible == false && gp.monster[i].dying == false) {
                int damage = Math.max(0, gp.monster[i].attack - defense);
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }

        }
    }

    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (gp.monster[i].invincible == false) {

                int damage = attack - gp.monster[i].defense;
                if (damage < 0) {
                    damage = 0;
                }
                gp.monster[i].life -= damage;
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        if (dying) {
            dyingAnimation(g2);
        }
        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    if (spriteNum == 3) {
                        image = up3;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                    if (spriteNum == 3) {
                        image = attackUp1;
                    }
                }

                break;
            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    if (spriteNum == 3) {
                        image = down3;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                    if (spriteNum == 3) {
                        image = attackDown1;
                    }
                }

                break;
            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    if (spriteNum == 3) {
                        image = left3;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                    if (spriteNum == 3) {
                        image = attackLeft1;
                    }
                }
                break;
            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    if (spriteNum == 3) {
                        image = right3;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                    if (spriteNum == 3) {
                        image = attackRight1;
                    }
                }
                break;

        }
        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        if (dying == true) {
            dyingAnimation(g2);
        }
        g2.drawImage(image, worldX, worldY, null);
        // RESET ALPHA
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        }
        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        g2.setFont(new Font("Arial Rounded MT", Font.BOLD, 14));
        g2.setColor(Color.black);
        g2.fillOval(worldX, worldY + 47, gp.tileSize, gp.tileSize / 6);
        g2.setColor(Color.white);
        g2.drawString("PLAYER 1", worldX - 10, worldY - 5);
//        g2.drawString("X: " + x + " Y: " + y, x - 10, y - 20);
        g2.drawRect(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);

        g2.drawString("PLAYER 1", 91, 15);
        g2.drawString("Speed: " + speed, 180, 40);
        g2.drawString("Power: " + projectile.attack, 180, 60);
        g2.drawImage(down1, 96, 20, null);

    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 5; // กำหนดเวลาแต่ละช่วงการกระพริบ

        // ตั้งค่าการกระพริบ
        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            dying = false;
            alive = false; // หยุดการแสดงผลของผู้เล่นเมื่อการตายเสร็จสิ้น

            worldX = -100;
            worldY = -100;
        }
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile[i].destructible == true) {
            gp.iTile[i] = null;
        }
    }

}

//    public void loadPlayerImage(int commandNum) {
//    String colorPath;
//    switch (commandNum) {
//        case 0:
//            colorPath = "/playerColorBlue/";
//            break;
//        case 1:
//            colorPath = "/playerColorGreen/";
//            break;
//        case 2:
//            colorPath = "/playerColorRed/";
//            break;
//        default:
//            colorPath = "/playerColorWhite/";
//            break;
//    }
//
//    up1 = setup2(colorPath + "up1");
//    up2 = setup2(colorPath + "up2");
//    up3 = setup2(colorPath + "up3");
//    down1 = setup2(colorPath + "down1");
//    down2 = setup2(colorPath + "down2");
//    down3 = setup2(colorPath + "down3");
//    left1 = setup2(colorPath + "left1");
//    left2 = setup2(colorPath + "left2");
//    left3 = setup2(colorPath + "left3");
//    right1 = setup2(colorPath + "right1");
//    right2 = setup2(colorPath + "right2");
//    right3 = setup2(colorPath + "right3");
//}
//    public BufferedImage setup2(String imageName){
//        UtilityTool uTool = new UtilityTool();
//        BufferedImage image = null ;
//        try {
//
//            image = ImageIO.read(getClass().getResourceAsStreaAm(imageName+".png"));
//            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return image;
