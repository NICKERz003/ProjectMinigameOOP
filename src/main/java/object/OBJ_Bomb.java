package object;

import entity.BombProjectile;
import main.GamePanel;

public class OBJ_Bomb extends BombProjectile{
    GamePanel gp;
    
    public OBJ_Bomb(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        name = "Bomb";
        speed = 10;
        maxLife = 80;
        life = maxLife;
        attack = 1;
        alive = false;
        getImage();
    }
    public void getImage(){
        up1 = setup("/projectile/Bomb1");
        up2 = setup("/projectile/Bomb5");
        up3 = setup("/projectile/Bomb7");
        down1 = setup("/projectile/Bomb1");
        down2 = setup("/projectile/Bomb5");
        down3 = setup("/projectile/Bomb7");
        left1 = setup("/projectile/Bomb1");
        left2 = setup("/projectile/Bomb5");
        left3 = setup("/projectile/Bomb7");
        right1 = setup("/projectile/Bomb1");
        right2 = setup("/projectile/Bomb5");
        right3 = setup("/projectile/Bomb7");
                
    }
    public int getAttack() {
        return attack;
    }
    
}
