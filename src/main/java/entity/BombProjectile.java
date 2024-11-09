/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import main.GamePanel;

public class BombProjectile extends Entity {

    Entity user;

    public BombProjectile(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direcion, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direcion;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    public void damageInteractiveTile(int i) {
        if (i != 999 && gp.iTile[i].destructible) {
            gp.iTile[i] = null;

        }
    }

    @Override
    public void update() {

        int iTileIndex = gp.cChecker.checkInteractiveTile(this);
        if (iTileIndex != 999) {
            damageInteractiveTile(iTileIndex);
            alive = false;
        }

        if (gp.cChecker.checkTile(this)) {
            alive = false;
            return;
        }

        // ตรวจสอบการชนของ projectile ที่สร้างโดย Player1 กับ Player2
        if (user == gp.player && gp.cChecker.checkPlayerHit(this, gp.player2)) {
            System.out.println("Player1 โจมตี Player2!");
            gp.player2.life -= attack;  // ลดพลังชีวิตของ Player2
            gp.player2.invincible = true;
            alive = false; // ทำให้ projectile หายไปหลังโจมตี
        }
        // ตรวจสอบการชนของ projectile ที่สร้างโดย Player2 กับ Player1
        if (user == gp.player2 && gp.cChecker.checkPlayerHit(this, gp.player)) {
            System.out.println("Player2 โจมตี Player1!");
            gp.player.life -= attack;  // ลดพลังชีวิตของ Player1
            gp.player.invincible = true;
            alive = false; // ทำให้ projectile หายไปหลังโจมตี
        }

        if (user == gp.player) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if (monsterIndex != 999) {
                gp.player.damageMonster(monsterIndex, attack);
                alive = false;
            }
        }
        if (user == gp.player2) {
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            if (monsterIndex != 999) {
                gp.player2.damageMonster(monsterIndex, attack);
                alive = false;
            }
        }

        if (user != gp.player && user != gp.player2) {
            boolean contactPlayer = gp.cChecker.checkPlayer(this);
            if (gp.player.invincible == false && gp.player2.invincible == false && contactPlayer == true) {
                damagePlayer(attack);
                alive = false;
            }

        }

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
