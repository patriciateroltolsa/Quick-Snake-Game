/* 
 * Author: Patricia Terol 
 * Project: snake 
 */
 
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class Snake {
    // random generator
    private static final Random RNG = new Random (Long.getLong ("seed",
            System.nanoTime ()));
    public int x;
    public int y;
    public static int speed = 70;
    public static int score = 0;
    public static ArrayList<Snake> snake = new ArrayList<Snake> ();
    public static String name = "";
    public static int foodX = 500;
    public static int foodY = 200;
    public static boolean winner = false;
    public static int boardSize = 1000;
    public static String direction = "left";
    public static int level = 0;
    public static ArrayList<Snake> blocks = new ArrayList<Snake> ();

    public Snake(final int xCoord, final int yCoord) {
        x = xCoord;
        y = yCoord;
    }

    // // method to setup the game
    public static void setup () {
        StdDraw.setCanvasSize (800, 650);
        StdDraw.setXscale (0, 800);
        StdDraw.setYscale (0, 650);
        StdDraw.setPenRadius (0.005);
    }

    // // method to draw the image with everything on the screen
    public static void screen () {
        StdDraw.clear (new Color (82, 173, 218));
        header ();
        box ();
        drawFood ();
        drawSnake ();
    }

    //
    // //method to draw the score board
    public static void header () {
        StdDraw.setPenColor (StdDraw.WHITE);
        StdDraw.text (200, 630, "THE SNAKE!!");
        StdDraw.text (600, 630, "Score: " + score);
    }

    //
    // //method to draw the box
    public static void box () {
        StdDraw.line (5, 5, 5, 595);
        StdDraw.line (5, 5, 795, 5);
        StdDraw.line (795, 5, 795, 595);
        StdDraw.line (795, 595, 5, 595);
    }

    public static void menuScreen () {
        StdDraw.clear (new Color (82, 173, 218));
        StdDraw.setPenColor (StdDraw.WHITE);
        while (true) {
            if (StdDraw.mousePressed ()) {
                if ( ( StdDraw.mouseX () > 100 && StdDraw.mouseX () < 300)
                        && ( StdDraw.mouseY () < 250 && StdDraw.mouseY () > 150)) {
                    level = 1;
                    break;
                } else if ( ( StdDraw.mouseX () > 300 && StdDraw.mouseX () < 500)
                        && ( StdDraw.mouseY () < 250 && StdDraw.mouseY () > 150)) {
                    level = 2;
                    break;
                } else if ( ( StdDraw.mouseX () > 500 && StdDraw.mouseX () < 700)
                        && ( StdDraw.mouseY () < 250 && StdDraw.mouseY () > 150)) {
                    level = 3;
                    break;
                }
            }
            box ();
            StdDraw.text (400, 500, "THE SNAKE!!");
            StdDraw.text (400, 450, "(Use the space bar to pause the game)");
            StdDraw.text (400, 350, "Select a level");
            drawButtons ();
            StdDraw.show ();
        }
    }

    public static void drawButtons () {
        StdDraw.rectangle (200, 200, 100, 50);
        StdDraw.rectangle (400, 200, 100, 50);
        StdDraw.rectangle (600, 200, 100, 50);
        StdDraw.text (200, 200, "EASY");
        StdDraw.text (400, 200, "MEDIUM");
        StdDraw.text (600, 200, "HARD");
    }

    // method to create the snake at beggining
    public static void createSnake () {
        snake.add (new Snake (370, 300));
        snake.add (new Snake (380, 300));
        snake.add (new Snake (390, 300));
        snake.add (new Snake (400, 300));
        snake.add (new Snake (410, 300));
        snake.add (new Snake (420, 300));
        snake.add (new Snake (430, 300));
    }

    // method to draw the snake given the points
    public static void drawSnake () {
        StdDraw.setPenColor (new Color (219, 219, 219));
        StdDraw.setPenRadius (0.005);
        StdDraw.circle (snake.get (0).x, snake.get (0).y, 5);
        for (int i = 1; i < snake.size (); i++) {
            StdDraw.circle (snake.get (i).x, snake.get (i).y, 5);

        }
    }

    public static void drawFood () {
        StdDraw.setPenColor (new Color (181, 217, 84));
        StdDraw.filledCircle (foodX, foodY, 5);
    }

    public static boolean checkEnd () {
        // check for the right wall
        if (snake.get (0).x - 5 == 5) {
            return false;
        }
        // check for the left wall
        if (snake.get (0).x + 5 == 795) {
            return false;
        }
        // check for the top wall
        if (snake.get (0).y + 5 == 595) {
            return false;
        }
        // check for the bottom wall
        if (snake.get (0).y - 5 == 5) {
            return false;
        }
        // check for the snake crashing itself
        for (int i = 1; i < snake.size () - 1; i++) {
            if (snake.get (0).x == snake.get (i).x
                    && snake.get (0).y == snake.get (i).y) {
                return false;
            }
        }
        // check for the snake being the size of the screen
        if (snake.size () == boardSize) {
            winner = true;
            return false;
        }
        return true;
    }

    public static void moveSnake () {
        // Move the head of the snake by checking and updating the snake if any
        // of the arrow keys are pressed
        if (StdDraw.isKeyPressed (KeyEvent.VK_UP) && direction != "down") {
            snake.add (0, new Snake (snake.get (0).x, snake.get (0).y + 10));
            direction = "up";
        } else if (StdDraw.isKeyPressed (KeyEvent.VK_DOWN) && direction != "up") {
            snake.add (0, new Snake (snake.get (0).x, snake.get (0).y - 10));
            direction = "down";
        } else if (StdDraw.isKeyPressed (KeyEvent.VK_LEFT)
                && direction != "right") {
            snake.add (0, new Snake (snake.get (0).x - 10, snake.get (0).y));
            direction = "left";
        } else if (StdDraw.isKeyPressed (KeyEvent.VK_RIGHT)
                && direction != "left") {
            snake.add (0, new Snake (snake.get (0).x + 10, snake.get (0).y));
            direction = "right";
        } else {
            // otherwise move the snake one position ahead in the direction it
            // was headed
            if (direction.equals ("up")) {
                snake.add (0, new Snake (snake.get (0).x, snake.get (0).y + 10));
            } else if (direction.equals ("down")) {
                snake.add (0, new Snake (snake.get (0).x, snake.get (0).y - 10));
            } else if (direction.equals ("left")) {
                snake.add (0, new Snake (snake.get (0).x - 10, snake.get (0).y));
            } else if (direction.equals ("right")) {
                snake.add (0, new Snake (snake.get (0).x + 10, snake.get (0).y));
            }
        }
        // remove the last point in the snake which will be no longer a part of
        // it since the snake has move ahead from that position
        snake.remove (snake.size () - 1);
    }

    public static void updateFood () {
        if (snake.get (0).x == foodX && snake.get (0).y == foodY) {
            score++;
            foodX = ( RNG.nextInt (74) * 10) + 20;
            foodY = ( RNG.nextInt (55) * 10) + 20;
            snake.add (new Snake (snake.get (snake.size () - 1).x, snake
                    .get (snake.size () - 1).y));
            if (level == 1) {

            } else if (level == 2) {
                speed -= 3;
            } else {
                speed -= 9;
            }
        }
    }

    public static void pauseMessage () {
        StdDraw.setPenColor (StdDraw.WHITE);
        StdDraw.filledRectangle (400, 300, 200, 50);
        StdDraw.setPenColor (new Color (82, 173, 218));
        StdDraw.text (400, 300, "PAUSED! Click the space bar to continue");
    }

    public static void checkPause () {
        if (StdDraw.isKeyPressed (KeyEvent.VK_SPACE)) {
            while (true) {
                screen ();
                pauseMessage ();
                StdDraw.show (speed);
                if (StdDraw.isKeyPressed (KeyEvent.VK_SPACE)) {
                    break;
                }
            }
        }
    }

    public static void finalBox () {
        StdDraw.setPenColor (StdDraw.WHITE);
        StdDraw.filledRectangle (400, 300, 200, 100);
        StdDraw.line (200, 400, 190, 410);
        StdDraw.line (200, 200, 190, 190);
        StdDraw.line (600, 400, 610, 410);
        StdDraw.line (600, 200, 610, 190);
        StdDraw.line (190, 410, 190, 190);
        StdDraw.line (610, 410, 610, 190);
        StdDraw.line (190, 410, 610, 410);
        StdDraw.line (190, 190, 610, 190);
    }

    // method to play the easy mode game (constant speed)
    public static void easyGame () {
        while (true) {
            // conditions for stopping and finishing the game
            boolean check = checkEnd ();
            if (!check) {
                break;
            }
            // process the keys pressed
            moveSnake ();
            // if the snake eats the food, score a point and put another food at
            // another random location
            updateFood ();
            // check if it's paused
            checkPause ();
            // draw the screen
            screen ();
            StdDraw.show (speed);
        }
    }

    // method to play the medium mode game (increasing speed)
    public static void mediumGame () {
        while (true) {
            // conditions for stopping and finishing the game
            boolean check = checkEnd ();
            if (!check) {
                break;
            }
            // process the keys pressed
            moveSnake ();
            // if the snake eats the food, score a point and put another food at
            // another random location
            updateFood ();
            // check if it's paused
            checkPause ();
            // draw the screen
            screen ();
            StdDraw.show (speed);
        }
    }

    // method to play the hard mode game (increasing speed and obstacles)
    public static void hardGame () {
        while (true) {
            // conditions for stopping and finishing the game
            boolean check = checkEnd ();
            if (!check) {
                break;
            }
            // process the keys pressed
            moveSnake ();
            // if the snake eats the food, score a point and put another food at
            // another random location
            updateFood ();
            // check if it's paused
            checkPause ();
            // draw the screen
            screen ();
            StdDraw.show (speed);
        }
    }

    public static boolean checkHardEnd () {
        // check for the right wall
        if (snake.get (0).x - 5 == 5) {
            return false;
        }
        // check for the left wall
        if (snake.get (0).x + 5 == 795) {
            return false;
        }
        // check for the top wall
        if (snake.get (0).y + 5 == 595) {
            return false;
        }
        // check for the bottom wall
        if (snake.get (0).y - 5 == 5) {
            return false;
        }
        // check for the snake crashing itself
        for (int i = 1; i < snake.size () - 1; i++) {
            if (snake.get (0).x == snake.get (i).x
                    && snake.get (0).y == snake.get (i).y) {
                return false;
            }
        }
        // check for the snake being the size of the screen
        if (snake.size () == boardSize) {
            winner = true;
            return false;
        }
        return true;
    }

    public static void main (String[] args) {
        // setup the screen for the game
        setup ();
        // initial menu
        menuScreen ();
        // create the snake for the start of the game
        createSnake ();
        // start the game
        if (level == 1) {
            easyGame ();
        } else if (level == 2) {
            mediumGame ();
        } else if (level == 3) {
            hardGame ();
        }
        // show results when the game ends
        if (winner) {
            screen ();
            finalBox ();
            StdDraw.setPenColor (new Color (82, 173, 218));
            StdDraw.text (400, 300, "CONGRATULATIONS! YOU WON!");
            StdDraw.show ();
        } else {
            screen ();
            finalBox ();
            StdDraw.setPenColor (new Color (82, 173, 218));
            StdDraw.text (400, 350, "SORRY... you lost..");
            StdDraw.text (400, 250, "Your score is: " + score);
            StdDraw.show ();
        }
    }
}
