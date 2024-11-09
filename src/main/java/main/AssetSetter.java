/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import monster.PacMon;
import object.HeartItem;
import object.OBJ_Boots;
import object.PowerBombItem;
import tiles_interactive.Grass;

/**
 *
 * @author DYWSK03
 */
public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
//        
//        gp.obj[0] = new OBJ_Boots(gp);
//        gp.obj[0].worldX = 4 * gp.tileSize;
//        gp.obj[0].worldY = 3 * gp.tileSize;
//        
//        gp.obj[1] = new OBJ_Boots(gp);
//        gp.obj[1].worldX = 10 * gp.tileSize;
//        gp.obj[1].worldY = 3 * gp.tileSize;
//        
//        gp.obj[2] = new OBJ_Boots(gp);
//        gp.obj[2].worldX = 10 * gp.tileSize;
//        gp.obj[2].worldY = 5 * gp.tileSize;
//        
//        gp.obj[3] = new OBJ_Boots(gp);
//        gp.obj[3].worldX = 10 * gp.tileSize;
//        gp.obj[3].worldY = 8 * gp.tileSize;
//        
//        gp.obj[4] = new PowerBombItem(gp);
//        gp.obj[4].worldX = 4 * gp.tileSize;
//        gp.obj[4].worldY = 8 * gp.tileSize;
//        
//        gp.obj[5] = new PowerBombItem(gp);
//        gp.obj[5].worldX = 4 * gp.tileSize;
//        gp.obj[5].worldY = 5 * gp.tileSize;
//        
//        gp.obj[5] = new HeartItem(gp);
//        gp.obj[5].worldX = 4 * gp.tileSize;
//        gp.obj[5].worldY = 10 * gp.tileSize;
    }

    public void setMonster() {
        gp.monster[0] = new PacMon(gp);
        gp.monster[0].worldX = 16 * gp.tileSize;
        gp.monster[0].worldY = 3 * gp.tileSize;

        gp.monster[1] = new PacMon(gp);
        gp.monster[1].worldX = 4 * gp.tileSize;
        gp.monster[1].worldY = 12 * gp.tileSize;

        gp.monster[2] = new PacMon(gp);
        gp.monster[2].worldX = 13 * gp.tileSize;
        gp.monster[2].worldY = 7 * gp.tileSize;

        gp.monster[3] = new PacMon(gp);
        gp.monster[3].worldX = 16 * gp.tileSize;
        gp.monster[3].worldY = 8 * gp.tileSize;
    }

    public void setInteractiveTile() {
        int i = 0;
        gp.iTile[i] = new Grass(gp, 4, 5);
        i++;
        i++;
        gp.iTile[i] = new Grass(gp, 10, 5);

    }

}
