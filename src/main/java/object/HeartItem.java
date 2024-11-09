/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class HeartItem extends Entity {

    public HeartItem(GamePanel gp) {
        super(gp);
        name = "HeartItem";
        down1 = setup("/object/life2");
        down2 = setup("/object/life");
        down3 = setup("/object/life2");
    }

}
