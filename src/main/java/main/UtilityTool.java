/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author DYWSK03
 */
public class UtilityTool {

    public BufferedImage scaleImage(BufferedImage original, int width, int hegiht) {
        BufferedImage scaledImage = new BufferedImage(width, hegiht, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, hegiht, null);
        g2.dispose();

        return scaledImage;
    }

}
