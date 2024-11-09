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

public class Player2 extends Player {

    KeyHandler keyH;

    public Player2(GamePanel gp, KeyHandler keyH) {
        super(gp, keyH);
        this.keyH = keyH;
        this.gp = gp;
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

    @Override
    public void setDefaultValues() {
        worldX = gp.tileSize * 14;
        worldY = gp.tileSize * 12;
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

    public void setDefaultPositionsP2() {
        worldX = gp.tileSize * 14;
        worldY = gp.tileSize * 12;
        direction = "down";
    }

    public void restoreLifeP2() {
        life = maxLife;
        speed = 4;
        projectile.attack = 1;
        invincible = false;
    }

    @Override
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

    @Override
    public void getPlayerImage() {
        up1 = setup("/player2/up1");
        up2 = setup("/player2/up2");
        up3 = setup("/player2/up3");
        down1 = setup("/player2/down1");
        down2 = setup("/player2/down2");
        down3 = setup("/player2/down3");
        left1 = setup("/player2/left1");
        left2 = setup("/player2/left2");
        left3 = setup("/player2/left3");
        right1 = setup("/player2/right1");
        right2 = setup("/player2/right2");
        right3 = setup("/player2/right3");
    }

    @Override
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
        }
        if (keyH.upPressed2 == true || keyH.downPressed2 == true
                || keyH.leftPressed2 == true || keyH.rightPressed2 == true) {
            if (keyH.upPressed2 == true) {
                direction = "up";
            } else if (keyH.downPressed2 == true) {
                direction = "down";
            } else if (keyH.leftPressed2 == true) {
                direction = "left";
            } else if (keyH.rightPressed2 == true) {
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

        }
        if (gp.keyH.bomb2Pressed == true && projectile != null && projectile.alive == false && bombAvailableCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            bombAvailableCounter = 0;
            System.out.println("Projectile created!");
        } else {
            standCounter++;

            if (standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
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

    @Override
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

    @Override
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

    @Override
    public void contactMonter(int i) {
        if (i != 999) {
            if (!invincible && !gp.monster[i].dying) {
                int damage = gp.monster[i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }
                life -= damage;
                invincible = true;
            }

        }
    }

    @Override
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

    @Override
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        if (dying) {
            dyingAnimation(g2);
        }
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
                if (spriteNum == 2) {
                    image = up2;
                }
                if (spriteNum == 3) {
                    image = up3;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                if (spriteNum == 2) {
                    image = down2;
                }
                if (spriteNum == 3) {
                    image = down3;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                if (spriteNum == 3) {
                    image = left3;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2) {
                    image = right2;
                }
                if (spriteNum == 3) {
                    image = right3;
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
        g2.drawString("PLAYER 2", worldX - 10, worldY - 5);
//        g2.drawString("X: " + x + " Y: " + y, x - 10, y - 20);
        g2.drawString("Speed: " + speed, 680, 40);
        g2.drawString("Power: " + projectile.attack, 680, 60);
//          g2.drawRect(x+5,y+20,gp.tileSize-8,gp.tileSize/2);

        g2.drawString("PLAYER 2", 48 * 16 - 5, 15);
        g2.drawImage(down1, 48 * 16, 20, null);

    }

    @Override
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
        }
    }

}
