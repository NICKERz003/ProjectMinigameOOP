/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import entity.BombProjectile;
import entity.Player;
import entity.Player2;
import main.GamePanel;

/**
 *
 * @author DYWSK03
 */
public class OBJ_Fire extends BombProjectile {

    GamePanel gp;

    public OBJ_Fire(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fire";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setup("/projectile/Bomb8");
        up2 = setup("/projectile/Bomb8");
        up3 = setup("/projectile/Bomb8");
        down1 = setup("/projectile/Bomb8");
        down2 = setup("/projectile/Bomb8");
        down3 = setup("/projectile/Bomb8");
        left1 = setup("/projectile/Bomb8");
        left2 = setup("/projectile/Bomb8");
        left3 = setup("/projectile/Bomb8");
        right1 = setup("/projectile/Bomb8");
        right2 = setup("/projectile/Bomb8");
        right3 = setup("/projectile/Bomb8");

    }

    public int getAttack() {
        return attack;
    }

    @Override
    public void update() {

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

        // Check for collision with Player 1
        if (gp.cChecker.checkPlayerHit(this, gp.player)) {
            gp.player.damagePlayer(gp.player); // Reduce life of Player 1
            alive = false; // Remove projectile after hitting
        }

        // Check for collision with Player 2
        if (gp.cChecker.checkPlayerHit(this, gp.player2)) {
            gp.player2.damagePlayer(gp.player2); // Reduce life of Player 2
            alive = false; // Remove projectile after hitting
        }

        // Check for tile collision
        if (gp.cChecker.checkTile(this)) {
            alive = false; // Remove projectile if it hits a tile
        }

//
//        // Existing code for checking player hits
//        if (gp.cChecker.checkPlayerHit(this, gp.player)) {
//            gp.player.damagePlayer(attack); // Apply damage to player
//            alive = false; // Remove projectile after hitting player
//            
//        }
//        if (gp.cChecker.checkPlayerHit(this, gp.player2)) {
//            gp.player2.damagePlayer(attack); // Apply damage to player2
//            alive = false; // Remove projectile after hitting player2
//        }
//
//        // Existing code for checking player hits
//        if (gp.cChecker.checkPlayerHit(this, gp.player)) {
//            gp.player.damagePlayer(attack); // Apply damage to player
//            alive = false; // Remove projectile after hitting player
//            
//        }
//        if (gp.cChecker.checkPlayerHit(this, gp.player2)) {
//            gp.player2.damagePlayer(attack); // Apply damage to player2
//            alive = false; // Remove projectile after hitting player2
//        }
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
}
