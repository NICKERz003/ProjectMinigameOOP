/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class PowerBombItem extends Entity {

    public PowerBombItem(GamePanel gp) {
        super(gp);
        name = "PowerBomb";
        down1 = setup("/projectile/Bomb1");
        down2 = setup("/projectile/Bomb2");
        down3 = setup("/projectile/Bomb3");
        down1 = setup("/projectile/Bomb4");
    }

}
