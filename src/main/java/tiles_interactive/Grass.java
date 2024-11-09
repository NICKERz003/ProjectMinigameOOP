/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tiles_interactive;

import main.GamePanel;

/**
 *
 * @author DYWSK03
 */
public class Grass extends InteractiveTile {

    GamePanel gp;

    public Grass(GamePanel gp, int col, int row) {
        super(gp, col, row);
        this.gp = gp;

        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;

        down1 = setup("/tiles_interactive/grass");
        destructible = true;
    }

}
