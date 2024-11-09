package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

/**
 *
 * @author DYWSK03
 */
public class Entity {

    GamePanel gp;

    public int x, y;

    public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
    public BufferedImage image, image2;
    public BufferedImage CC1P_1, CC1P_2, CC1P_3, CC1P_4;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2,
            attackLeft1, attackLeft2, attackRight1, attackRight2;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collision = true;

    //STATE
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    //COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int bombAvailableCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    // CHARACTER ATTIBUTES
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int strength;
    public int attack;
    public int defense;
    public BombProjectile projectile;
    public Entity currentWeapon;

    // ITEM ATTIBUTSE
    public int attackValue;

    // TYPE 
    public int type;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imagePath) {

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tileSize / 2, gp.tileSize / 2);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void setAction() {
    }

    public void damageReaction() {
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

//        int screenX = worldX - gp.tileSize + gp.player.x;
//        int screenY = worldY - gp.tileSize + gp.player.y;
//
//        if ((worldX + gp.tileSize > gp.player.worldX - gp.player.x
//                && worldX - gp.tileSize < gp.player.worldX + gp.player.x
//                && worldY + gp.tileSize > gp.player.worldY - gp.player.y
//                && worldY - gp.tileSize < gp.player.worldY + gp.player.y)
//                || (worldX + gp.tileSize > gp.player2.worldX - gp.player2.x
//                && worldX - gp.tileSize < gp.player2.worldX + gp.player2.x
//                && worldY + gp.tileSize > gp.player2.worldY - gp.player2.y
//                && worldY - gp.tileSize < gp.player2.worldY + gp.player2.y)) 
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
        // monster hp bar
        if (type == 2 && hpBarOn == true) {

            g2.setColor(new Color(35, 35, 35));
            g2.fillRect(worldX - 1, worldY - 16, gp.tileSize + 2, 12);

            if (life > 0) {
                double oneScale = (double) gp.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(255, 0, 30)); // สีแดง
                g2.fillRect(worldX, worldY - 15, (int) hpBarValue, 10);
            }

            hpBarCounter++;

            if (hpBarCounter > 600) {
                hpBarCounter = 0;
                hpBarOn = false;

            }
        }

        if (invincible == true) {
            hpBarOn = true;
            hpBarCounter = 0;
            changeAlpha(g2, 0.4f);
        }
        if (dying == true) {
            dyingAnimation(g2);
        }
        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
        changeAlpha(g2, 1f);

    }

    public void dyingAnimation(Graphics2D g2) {

        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0F);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1F);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0F);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1F);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0F);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1F);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0F);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1F);
        }
        if (dyingCounter > i * 8) {
            dying = false;
            alive = false;
        }

    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public void update() {

        setAction();

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.monster);
        gp.cChecker.checkPlayer(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == 2 && contactPlayer == true) {
            damagePlayer(attack);
        }

        // If collision is false, player can move
        if (collisionOn == false) {
//          if(!collisionOn){
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
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
        if (bombAvailableCounter < 30) {
            bombAvailableCounter++;
        }

    }

    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
            // we can give damage
            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }
            gp.player.life -= damage;
            gp.player.invincible = true;
        }

    }

    public void damagePlayer(Player target) {
        if (!target.invincible) {
            // Calculate damage
            int damage = attack - target.defense;
            if (damage < 0) {
                damage = 0;
            }
            target.life -= damage;
            target.invincible = true; // Make the player invincible for a short time
        }
    }

    public int checkEntity(Entity entity, Entity[] target) {
        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                if (entity.solidArea.intersects(target[i].solidArea)) {
                    return i;
                }
            }
        }
        return 999;
    }

}
