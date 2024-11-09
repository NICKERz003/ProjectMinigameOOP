/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import entity.Entity;
import entity.Player;
import entity.Player2;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JPanel;
import tile.TileManager;
import tiles_interactive.InteractiveTile;

/**
 *
 * @author DYWSK03
 */
public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16; // 16*16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48*48 tile
    public final int maxScreenCol = 19;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol; // 912 pixels
    public final int screenHeight = tileSize * maxScreenRow;// 768 pixels

    //FPS
    int FPS = 60;

    private int countdownTime = 120; // 2 minutes in seconds
    private long startTime;

    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Player2 player2 = new Player2(this, keyH);
    public Entity obj[] = new Entity[10];
    public Entity monster[] = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();
    public InteractiveTile iTile[] = new InteractiveTile[50];

    // GAME STATE
    public int gameState;
    public int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int gameOverState = 555;
    public final int YouWinState = 666;
    private KeyHandler keyHandler;
    public Object objList;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.white);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        startTime = System.currentTimeMillis(); // Start the timer
        gameState = titleState;

    }

    public void retry() {
        player.setDefaultPositions();
        player.restoreLife();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        aSetter.setObject();
        startTime = System.currentTimeMillis();
    }

    public void retry2() {
        player.setDefaultPositions();
        player.restoreLife();
        player.alive = true;

        player2.setDefaultPositionsP2();
        player2.restoreLifeP2();
        player2.alive = true;

        aSetter.setInteractiveTile();
        aSetter.setMonster();
        startTime = System.currentTimeMillis();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//game loop
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public int getAvailableObjectIndex() {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] == null) {
                return i; // Return the first available index
            }
        }
        return -1; // Return -1 if no available index is found
    }

    public void update() {

        if (gameState == playState) {

            long elapsedTime = (System.currentTimeMillis() - startTime) / 1000; // Convert milliseconds to seconds
            countdownTime = 180 - (int) elapsedTime; // 120 seconds countdown

            if (countdownTime <= 0) {
                // Handle game over condition
                countdownTime = 0; // Make sure it doesn't go negative
                gameState = gameOverState; // Change to a game over state or any state you'd like
            }

            if (player.alive) {
                player.update();
            }
            if (player2.alive && ui.titleScreenState != 3) {
                player2.update();
            }

            boolean allMonstersDefeated = true;
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    if (monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                        allMonstersDefeated = false;
                    }
                    if (monster[i].alive == false) {
                        monster[i] = null;
                    }
                }

            }
            if (allMonstersDefeated) {
                gameState = YouWinState;
            }

            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    if (projectileList.get(i).alive == true) {
                        projectileList.get(i).update();
                    }
                    if (projectileList.get(i).alive == false) {
                        projectileList.remove(i);
                    }
                }
            }

            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].update();
                }
            }
            for (int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].update();
                }
            }
        }

        if (gameState == pauseState) {

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // TITLE SCREEN
        if (gameState == titleState) {
            ui.draw(g2);

        }// Ensure this function is called
        else {
            //Tile
            tileM.draw(g2);

            if (gameState == playState) {
                // Draw the countdown timer
                g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
                g2.setColor(Color.white);
                g2.drawString("Time Left: " + countdownTime, screenWidth / 2 - 50, 50); // Adjust position as needed
            }

            //INTERACTIVE TILE
            for (int i = 0; i < iTile.length; i++) {
                if (iTile[i] != null) {
                    iTile[i].draw(g2);
                }
            }

            // Player1
            if (gameState == playState && ui.titleScreenState == 3) {
                if (player.alive) {
                    player.draw(g2);
                }
            } else {
                if (player.alive) {
                    player.draw(g2);
                }
                if (player2.alive && ui.titleScreenState != 3) {
                    player2.draw(g2);
                }
            }
            // Monster
            for (int i = 0; i < monster.length; i++) {
                if (monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            // Object
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            // Projectile
            for (int i = 0; i < projectileList.size(); i++) {
                if (projectileList.get(i) != null) {
                    entityList.add(projectileList.get(i));
                }
            }

            // SORT
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    return Integer.compare(e1.worldY, e2.worldY);
                }
            });

            // DRAW ENTITIES
            for (Entity entity : entityList) {
                entity.draw(g2);
            }
            // EMPTY ENTITIES LIST
            entityList.clear();

            // Draw UI
            ui.draw(g2);
        }

        g2.dispose();
    }

}
