/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author DYWSK03
 */
public class KeyHandler implements KeyListener {

    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean upPressed2, downPressed2, leftPressed2, rightPressed2;
    public boolean bomb1Pressed, bomb2Pressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // TITLE STATE
        if (gp.gameState == gp.titleState) {
            if (gp.ui.titleScreenState == 0) {
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 1;
                    }
                }
                if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 1) {
                        gp.ui.commandNum = 0;
                    }
                }

                if (code == KeyEvent.VK_ENTER) {
                    //if comandNum == 0 ---> Start Game
                    if (gp.ui.commandNum == 0) {
                        // GO TO PAGE CHOOSE MODE 1 or 2 PLAYER 
                        gp.ui.titleScreenState = 2;
                    }
                    //if comandNum == 1 ---> How To Play
                    if (gp.ui.commandNum == 1) {
                        gp.ui.titleScreenState = 1;
                    }
                    gp.ui.commandNum = 0;

                }
            } else if (gp.ui.titleScreenState == 1) {
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        // GO TO CHOOSE MODE
                        gp.ui.titleScreenState = 2;
                    }
                }
            } else if (gp.ui.titleScreenState == 2) {
                if (code == KeyEvent.VK_UP || code == KeyEvent.VK_W) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 1;
                    }
                }
                if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_S) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 1) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        // GO TO PAGE 1 PLAYER Choose Color
                        gp.ui.titleScreenState = 3;

                    }
                    if (gp.ui.commandNum == 1) {
                        // GO TO PAGE 2 PLAYER Choose Color
                        gp.ui.titleScreenState = 4;
                    }
                }
            } // 1 PLAYER MODE
            else if (gp.ui.titleScreenState == 3) {
                if (code == KeyEvent.VK_LEFT) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    }
                }
                if (code == KeyEvent.VK_RIGHT) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum == 0) {
                        //Choose Color 1
                        gp.gameState = gp.playState;
                        System.out.println("color 1");
                    }
                    if (gp.ui.commandNum == 1) {
                        //Choose Color 2
                        gp.gameState = gp.playState;
                        System.out.println("color 2");
                    }
                    if (gp.ui.commandNum == 2) {
                        //Choose Color 3
                        gp.gameState = gp.playState;
                        System.out.println("color 3");
                    }
                    if (gp.ui.commandNum == 3) {
                        //Choose Color 4
                        gp.gameState = gp.playState;
                        System.out.println("color 4");
                    }
                }
            } // 2 PLAYER MODE
            else if (gp.ui.titleScreenState == 4) {
                if (code == KeyEvent.VK_A) {
                    gp.ui.commandNum--;
                    if (gp.ui.commandNum2 == 0 && (gp.ui.commandNum < 0 || gp.ui.commandNum == 0)) {
                        gp.ui.commandNum = 3;
                    } else if (gp.ui.commandNum2 == 1 && gp.ui.commandNum == 1) {
                        gp.ui.commandNum = 0;
                    } else if (gp.ui.commandNum2 == 1 && gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    } else if (gp.ui.commandNum2 == 2 && gp.ui.commandNum == 2) {
                        gp.ui.commandNum = 1;
                    } else if (gp.ui.commandNum2 == 2 && gp.ui.commandNum < 0) {
                        gp.ui.commandNum = 3;
                    } else if (gp.ui.commandNum2 == 3 && (gp.ui.commandNum < 0 || gp.ui.commandNum == 3)) {
                        gp.ui.commandNum = 2;
                    }
                }
                if (code == KeyEvent.VK_D) {
                    gp.ui.commandNum++;
                    if (gp.ui.commandNum2 == 0 && gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 1;
                    } else if (gp.ui.commandNum2 == 1 && gp.ui.commandNum == 1) {
                        gp.ui.commandNum = 2;
                    } else if (gp.ui.commandNum2 == 1 && gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                    } else if (gp.ui.commandNum2 == 2 && gp.ui.commandNum == 2) {
                        gp.ui.commandNum = 3;
                    } else if (gp.ui.commandNum2 == 2 && gp.ui.commandNum > 3) {
                        gp.ui.commandNum = 0;
                    } else if (gp.ui.commandNum2 == 3 && gp.ui.commandNum == 3) {
                        gp.ui.commandNum = 0;
                    }
                }
                if (code == KeyEvent.VK_SPACE) {
                    if (gp.ui.commandNum == 0) {
                        //Choose Color 1
                        gp.gameState = gp.playState;
                        System.out.println("1P color blue");
                    }
                    if (gp.ui.commandNum == 1) {
                        //Choose Color 2
                        gp.gameState = gp.playState;
                        System.out.println("1P color green");
                    }
                    if (gp.ui.commandNum == 2) {
                        //Choose Color 3
                        gp.gameState = gp.playState;
                        System.out.println("1P color red");
                    }
                    if (gp.ui.commandNum == 3) {
                        //Choose Color 4
                        gp.gameState = gp.playState;
                        System.out.println("1P color white");
                    }
                }

                if (code == KeyEvent.VK_LEFT) {
                    gp.ui.commandNum2--;
                    if (gp.ui.commandNum == 0 && (gp.ui.commandNum2 < 0 || gp.ui.commandNum2 == 0)) {
                        gp.ui.commandNum2 = 3;
                    } else if (gp.ui.commandNum == 1 && gp.ui.commandNum2 == 1) {
                        gp.ui.commandNum2 = 0;
                    } else if (gp.ui.commandNum == 1 && gp.ui.commandNum2 < 0) {
                        gp.ui.commandNum2 = 3;
                    } else if (gp.ui.commandNum == 2 && gp.ui.commandNum2 == 2) {
                        gp.ui.commandNum2 = 1;
                    } else if (gp.ui.commandNum == 2 && gp.ui.commandNum2 < 0) {
                        gp.ui.commandNum2 = 3;
                    } else if (gp.ui.commandNum == 3 && (gp.ui.commandNum2 < 0 || gp.ui.commandNum2 == 3)) {
                        gp.ui.commandNum2 = 2;
                    }
                }
                if (code == KeyEvent.VK_RIGHT) {
                    gp.ui.commandNum2++;
                    if (gp.ui.commandNum == 0 && gp.ui.commandNum2 > 3) {
                        gp.ui.commandNum2 = 1;
                    } else if (gp.ui.commandNum == 1 && gp.ui.commandNum2 == 1) {
                        gp.ui.commandNum2 = 2;
                    } else if (gp.ui.commandNum == 1 && gp.ui.commandNum2 > 3) {
                        gp.ui.commandNum2 = 0;
                    } else if (gp.ui.commandNum == 2 && gp.ui.commandNum2 == 2) {
                        gp.ui.commandNum2 = 3;
                    } else if (gp.ui.commandNum == 2 && gp.ui.commandNum2 > 3) {
                        gp.ui.commandNum2 = 0;
                    } else if (gp.ui.commandNum == 3 && gp.ui.commandNum2 == 3) {
                        gp.ui.commandNum2 = 0;
                    }
                }
                if (code == KeyEvent.VK_ENTER) {
                    if (gp.ui.commandNum2 == 0) {
                        //Choose Color 1
                        gp.gameState = gp.playState;
                        System.out.println("2P color blue");
                    }
                    if (gp.ui.commandNum2 == 1) {
                        //Choose Color 2
                        gp.gameState = gp.playState;
                        System.out.println("2P color green");
                    }
                    if (gp.ui.commandNum2 == 2) {
                        //Choose Color 3
                        gp.gameState = gp.playState;
                        System.out.println("2P color red");
                    }
                    if (gp.ui.commandNum2 == 3) {
                        //Choose Color 4
                        gp.gameState = gp.playState;
                        System.out.println("2P color white");
                    }
                }

            }

        }

        //PLAYER STATE
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            bomb1Pressed = true;
        }

        if (code == KeyEvent.VK_UP) {
            upPressed2 = true;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed2 = true;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed2 = true;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed2 = true;
        }
        if (code == KeyEvent.VK_P) {
            if (gp.gameState == gp.playState) {
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            bomb2Pressed = true;
        }

        // Game Over State
        if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }
        
        // Game Win State
        if(gp.gameState == gp.YouWinState){
            youWinState(code);
        }
    }
    public void youWinState(int code){
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                if (gp.ui.titleScreenState == 3) {
                    gp.retry();
                }if (gp.ui.titleScreenState == 4) {
                    gp.retry2();
                }
            } 
            else if (gp.ui.commandNum == 1) {
                gp.retry2();
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.ui.titleScreenState;
            }
            else if(gp.ui.commandNum == 2){
                System.exit(0);
            }
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                if (gp.ui.titleScreenState == 3) {
                    gp.retry();
                } if (gp.ui.titleScreenState == 4) {
                    gp.retry2();
                }
            } else if (gp.ui.commandNum == 1) {
                gp.retry2();
                gp.ui.titleScreenState = 0;
                gp.gameState = gp.ui.titleScreenState;

            }
            else if (gp.ui.commandNum == 2) {
                 System.exit(0);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        if (code == KeyEvent.VK_UP) {
            upPressed2 = false;
        }
        if (code == KeyEvent.VK_DOWN) {
            downPressed2 = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed2 = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed2 = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            bomb2Pressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            bomb1Pressed = false;
        }
    }

}
