/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import object.OBJ_Heart;

/**
 *
 * @author DYWSK03
 */
public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font PermanentMarker, SuperLegendBoy;
    BufferedImage selectbutton1, selectbutton2, howtoplay, selectcolor2p;
    BufferedImage heart_full, heart_blank;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum;
    public int commandNum2;
    public int titleScreenState = 0;

    public int getCommandNum() {
        return commandNum;
    }

    public int getCommandNum2() {
        return commandNum2;
    }

    public UI(GamePanel gp) {
        this.gp = gp;
        try {
            InputStream is = getClass().getResourceAsStream("/font/PermanentMarker-Regular.ttf");
            PermanentMarker = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/font/SuperLegendBoy-4w8Y.ttf");
            SuperLegendBoy = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // CREATED HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_blank = heart.image2;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(PermanentMarker);
        g2.setColor(Color.white);

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // PLAY STATE
        if (gp.gameState == gp.playState) {
            drawLifePlayer1();
            if (titleScreenState != 3) {
                drawLifePlayer2();
            }

        }
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }
        if( gp.gameState == gp.YouWinState){
            drawYouWinState();
        }

        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawLifePlayer1();
            if (titleScreenState != 3) {
                drawLifePlayer2();
            }

        }

    }

    public void drawLifePlayer1() {

        int x = gp.tileSize * 2 - 13;
        int y = gp.tileSize + 22;
        int i = 0;

        // DRAW MAX LIFE
        while (i < gp.player.maxLife) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize / 2;
        }

        //RESET
        x = gp.tileSize * 2 - 13;
        y = gp.tileSize + 22;
        i = 0;

        // DRAW BLANK LIFE
        while (i < gp.player.life) {
            g2.drawImage(heart_full, x, y, null);
            i++;
            x += gp.tileSize / 2;
        }

    }

    public void drawLifePlayer2() {

        int x = gp.tileSize * 16 - gp.tileSize / 4;
        int y = gp.tileSize + 22;
        int i = 0;

        // DRAW MAX LIFE
        while (i < gp.player2.maxLife) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize / 2;
        }

        //RESET
        x = gp.tileSize * 16 - gp.tileSize / 4;
        y = gp.tileSize + 22;
        i = 0;

        // DRAW BLANK LIFE
        while (i < gp.player2.life) {
            g2.drawImage(heart_full, x, y, null);
            i++;
            x += gp.tileSize / 2;
        }

    }

    public void drawChooseColor_1P() {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Footer
        g2.drawImage(selectbutton2, gp.tileSize - 30, gp.tileSize * 8, 815, 280, null);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "1 PLAYER";
        int x = getXforCenterText(text);
        int y = gp.tileSize * 2;
        // SHADOW
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);
        // MAIN COLOR
        g2.setColor(Color.ORANGE);
        g2.drawString(text, x, y);

        y = gp.tileSize * 5;
        // BOMBERMAN IMAGE
        g2.drawImage(gp.player.CC1P_1, x - gp.tileSize * 2 - 10, y + 10, gp.tileSize * 3, gp.tileSize * 3, null);
        g2.drawImage(gp.player.CC1P_2, x + gp.tileSize + 10, y + 10, gp.tileSize * 3, gp.tileSize * 3, null);
        g2.drawImage(gp.player.CC1P_3, 2 * x + gp.tileSize / 2 - 25, y + 10, gp.tileSize * 3, gp.tileSize * 3, null);
        g2.drawImage(gp.player.CC1P_4, x + gp.tileSize * 8 + 20, y + 10, gp.tileSize * 3, gp.tileSize * 3, null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        if (commandNum == 0) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("1P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("1P", x - gp.tileSize, y - gp.tileSize);
        }
        x += gp.tileSize * 3.5;
        if (commandNum == 1) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("1P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("1P", x - gp.tileSize, y - gp.tileSize);
        }
        x += gp.tileSize * 3.5;
        if (commandNum == 2) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("1P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("1P", x - gp.tileSize, y - gp.tileSize);
        }
        x += gp.tileSize * 3.5;
        if (commandNum == 3) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("1P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("1P", x - gp.tileSize, y - gp.tileSize);
        }
    }

    public void drawChooseColor_2P() {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Footer
        g2.drawImage(selectcolor2p, gp.tileSize * 2, gp.tileSize * 9, 670, 230, null);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "2 PLAYER";
        int x = getXforCenterText(text);
        int y = gp.tileSize * 2;
        // SHADOW
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);
        // MAIN COLOR
        g2.setColor(Color.ORANGE);
        g2.drawString(text, x, y);

        y = gp.tileSize * 5;
        // BOMBERMAN IMAGE
        g2.drawImage(gp.player.CC1P_1, x - gp.tileSize * 2 - 10, y + 10, gp.tileSize * 3, gp.tileSize * 3, null);
        g2.drawImage(gp.player.CC1P_2, x + gp.tileSize + 10, y + 10, gp.tileSize * 3, gp.tileSize * 3, null);
        g2.drawImage(gp.player.CC1P_3, 2 * x + gp.tileSize / 2 - 15, y + 10, gp.tileSize * 3, gp.tileSize * 3, null);
        g2.drawImage(gp.player.CC1P_4, x + gp.tileSize * 8 + 20, y + 10, gp.tileSize * 3, gp.tileSize * 3, null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        if (commandNum == 0 && commandNum2 != 0) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("1P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("1P", x - gp.tileSize, y - gp.tileSize);
        }
        if (commandNum2 == 0 && commandNum != 0) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("2P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("2P", x - gp.tileSize, y - gp.tileSize);
        }

        x += gp.tileSize * 3.5;
        if (commandNum == 1 && commandNum2 != 1) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("1P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("1P", x - gp.tileSize, y - gp.tileSize);

        }
        if (commandNum2 == 1 && commandNum != 1) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("2P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("2P", x - gp.tileSize, y - gp.tileSize);
        }

        x += gp.tileSize * 3.5;
        if (commandNum == 2 && commandNum2 != 2) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("1P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("1P", x - gp.tileSize, y - gp.tileSize);
        }

        if (commandNum2 == 2 && commandNum != 2) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("2P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("2P", x - gp.tileSize, y - gp.tileSize);
        }

        x += gp.tileSize * 3.5;
        if (commandNum == 3 && commandNum2 != 3) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("1P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("1P", x - gp.tileSize, y - gp.tileSize);
        }

        if (commandNum2 == 3 && commandNum != 3) {
            g2.setColor(Color.black);
            g2.drawString("V", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString("V", x - gp.tileSize, y);
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            g2.setColor(Color.black);
            g2.drawString("2P", x - gp.tileSize + 5, y - gp.tileSize + 5);
            g2.setColor(Color.white);
            g2.drawString("2P", x - gp.tileSize, y - gp.tileSize);
        }

    }

    public void drawChooseMode() {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Footer
        g2.drawImage(selectbutton1, gp.tileSize - 30, gp.tileSize * 12, 800, 160, null);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Choose Mode";
        int x = getXforCenterText(text);
        int y = gp.tileSize * 3;
        // SHADOW
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);
        // MAIN COLOR
        g2.setColor(Color.ORANGE);
        g2.drawString(text, x, y);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "1 PLAYER";
        x = getXforCenterText(text);
        y += gp.tileSize * 4;
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.setColor(Color.black);
            g2.drawString(">", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.orange);
            g2.drawString(">", x - gp.tileSize, y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "2 PLAYER";
        x = getXforCenterText(text);
        y += gp.tileSize * 2;
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.setColor(Color.black);
            g2.drawString(">", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.orange);
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawHowToPlayScreen() {
        g2.setColor(new Color(70, 120, 80));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Footer
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
        String text = "ENTER to confirm.";
        int x = getXforCenterText(text);
        int y = gp.tileSize * 14;
        g2.setColor(Color.black);
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        g2.drawImage(howtoplay, 200, 200, 440, 355, null);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        text = "HOW TO PLAY";
        x = getXforCenterText(text);
        y = gp.tileSize * 3;
        // SHADOW
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);
        // MAIN COLOR
        g2.setColor(Color.ORANGE);
        g2.drawString(text, x, y);

        // drawImage later
        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "START GAME";
        x = getXforCenterText(text);
        y += gp.tileSize * 10;
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.setColor(Color.black);
            g2.drawString(">", x - gp.tileSize + 5, y + 5);
            g2.setColor(Color.orange);
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    // 00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000
    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            g2.setColor(new Color(70, 120, 80));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            // Footer
            try {
                selectbutton1 = ImageIO.read(getClass().getResourceAsStream("/buttonImage/selectbutton1.png"));
                selectbutton2 = ImageIO.read(getClass().getResourceAsStream("/buttonImage/selectbutton2.png"));
                howtoplay = ImageIO.read(getClass().getResourceAsStream("/buttonImage/howtoplay.png"));
                selectcolor2p = ImageIO.read(getClass().getResourceAsStream("/buttonImage/selectcolor2p.png"));
            } catch (IOException e) {
            }
            g2.drawImage(selectbutton1, gp.tileSize - 30, gp.tileSize * 12, 800, 160, null);

            // TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "BOMB BATTLE";
            int x = getXforCenterText(text);
            int y = gp.tileSize * 3;

            // SHADOW
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 5, y + 5);
            // MAIN COLOR
            g2.setColor(Color.ORANGE);
            g2.drawString(text, x, y);

            // BOMBERMAN IMAGE
            x = gp.screenWidth / 2 - (gp.tileSize);
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            // MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "START GAME";
            x = getXforCenterText(text);
            y += gp.tileSize * 4;
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.setColor(Color.black);
                g2.drawString(">", x - gp.tileSize + 5, y + 5);
                g2.setColor(Color.orange);
                g2.drawString(">", x - gp.tileSize, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "How To Play ?";
            x = getXforCenterText(text);
            y += gp.tileSize * 2;
            g2.setColor(Color.BLACK);
            g2.drawString(text, x + 5, y + 5);
            g2.setColor(Color.white);
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.setColor(Color.black);
                g2.drawString(">", x - gp.tileSize + 5, y + 5);
                g2.setColor(Color.orange);
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
        if (titleScreenState == 1) {
            drawHowToPlayScreen();
        }
        if (titleScreenState == 2) {
            drawChooseMode();
        }
        if (titleScreenState == 3) {
            drawChooseColor_1P();
        }
        if (titleScreenState == 4) {
            drawChooseColor_2P();
            //GAME OVER
        }
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }


    }
    
    public void drawYouWinState(){
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int x;
        int y;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 110F));
        String text = "YOU WIN!";
        // Shadow
        g2.setColor(Color.black);
        x = getXforCenterText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.yellow);
        // Main
        g2.drawString(text, x, y);

        // Retry
        g2.setFont(g2.getFont().deriveFont(50F));
        text = "Retry";
        x = getXforCenterText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        //Back to the title screen
        text = "Home";
        x = getXforCenterText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
        
        //Back to the title screen
        text = "Quit";
        x = getXforCenterText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 40, y);
        }
    }

    public void drawGameOverScreen() {
        
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        int x;
        int y;
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 110F));
        String text = "GAME OVER";
        // Shadow
        g2.setColor(Color.black);
        x = getXforCenterText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.white);
        // Main
        g2.drawString(text, x, y);

        // Retry
        g2.setFont(g2.getFont().deriveFont(50F));
        text = "Retry";
        x = getXforCenterText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }
        
        //Back to the title screen
        text = "Home";
        x = getXforCenterText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }

        //Back to the title screen
        text = "Quit";
        x = getXforCenterText(text);
        y += 55;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 40, y);
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "GAME PAUSED";
        int x = getXforCenterText(text);
        int y = gp.screenHeight / 2;
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        g2.setColor(Color.white);
        g2.drawString(text, x, y);
    }

    public int getXforCenterText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

}
