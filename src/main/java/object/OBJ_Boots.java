/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        name = "Boots";
        down1 = setup("/object/boots");
        down2 = setup("/object/boots2");
        down3 = setup("/object/boots3");
        down1 = setup("/object/boots");

    }

}
