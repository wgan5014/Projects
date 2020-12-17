package ghost; 

import processing.core.PImage;
import processing.core.PApplet;
/**
 * Player class which extends Nonstatic Cell
 * Is main class user interacts with
 */
public class Waka extends Nonstatic {
    private int lives, animtime, fxv, fyv;
    private boolean futureDirIsX, flip;
    private PImage futureSprite;

    public Waka(PImage spr, int x, int y, long vel) {
        super(spr, x, y, vel);
        xVel = (int) (-1*vel);
        yVel = 0;
        isX = true;
        flip = freeFound = false;
    }
    /**
     * deals with logic of the next move; i.e. move inputted by user
     * @param  move int - keycode of move made (passed from App object)
     * @param  app  App object
     */
    public void tick(int move, App app) {

        boolean currentDirIsX = isX; // save current state of direction            
        int cyv = yVel; // save current state of yVel
        int cxv = xVel; // save current state of xVel
        PImage tmp = defSprite;
        // debug mode
        if (move == 37) { // left 
            xVel = -1*Math.abs(xVel);
            isX = true;
            defSprite = app.loadImage("src/main/resources/playerLeft.png");
        } else if (move == 38) { // up 
            yVel = -1*Math.abs(xVel);
            isX = false;
            defSprite = app.loadImage("src/main/resources/playerUp.png");
        } else if (move == 40) { // down
            yVel = Math.abs(xVel);
            isX = false;
            defSprite = app.loadImage("src/main/resources/playerDown.png");
        } else if (move == 39) { // right 
            xVel = Math.abs(xVel);
            isX = true;
            defSprite = app.loadImage("src/main/resources/playerRight.png");
        } else if (move == 32) { // space
            Ghost.debug = !Ghost.debug;
        }
        // check if future change of movement will cause a collision or not
        collision(app);
        if (collided) {
            // set future attributes 
            fxv = xVel;
            fyv = yVel;
            futureDirIsX = isX;
            futureSprite = defSprite;

            // revert to current state
            isX = currentDirIsX;
            xVel = cxv;
            yVel = cyv;
            defSprite = tmp;
            flip = true; 
        } else {
            // if the future movement won't cause a collision
            // then no need to consider flipping into the nearest empty intersection
            flip = false;
        }
    }
    /**
     * deals with logic of the player while it's moving: accounts for collision, finding next free intersection etc.
     * @param map Cell[][] map from Game object
     * @param app   App object
     */
    public void move(Cell[][] map, App app) {
        ++animtime;
        if (animtime%16 == 8) {
            this.sprite = app.loadImage("src/main/resources/playerClosed.png");
        } else if (animtime%16 == 0) {
            sprite = defSprite;
            animtime = 0;
        }
        // if going to move into a new direction since the last move changed has collision
        if (flip) { 
            calcNextFree(map); 
        }
        if (freeFound && xCoord == xFree && yCoord + 5 == yFree) {
            freeFound = false;
            isX = futureDirIsX;
            defSprite = futureSprite;
            if (futureDirIsX) { 
                xVel = fxv;
                xCoord += xVel; 
            } else {
                yVel = fyv;  
                yCoord += yVel; 
            }
        }
        collision(app);
        if (!collided) {
            if (isX) { 
                xCoord += xVel; 
            } else { 
                yCoord += yVel; 
            }
        }
        for (Cell fruit : app.getGame().getAllFruits()) {
            if (fruit.getX() == xCoord && fruit.getY() == this.getY()) {
                map[fruit.getY()/16][fruit.getX()/16] = null;

                if (fruit.toString().equals("Superfruit")) {
                    Ghost.isFrightened = true;
                }
                
                app.getGame().getAllFruits().remove(fruit);
                break;
            }
        }
    }
    /**
     * Calculates the next free space to move into at an intersection in the local region of Waka.
     * Will run if the current user move is going to cause collision:
     * the user move gets queued, and this executes so as to find said intersection, and then perform queued move
     * @param  map  Cell[][] map from Game object
     */
    public void calcNextFree(Cell[][] map) {
        int xpos = Math.floorDiv(this.xCoord, 16);
        int ypos = Math.floorDiv(this.yCoord + 5, 16);
        if (isX && !futureDirIsX) { // vert move
            if (map[ypos - 1][xpos+xVel] == null || map[ypos - 1][xpos+xVel].toString().equals("Fruit") && fyv < 0) { // UP
                xFree = (xpos + xVel)*16;
                yFree = (--ypos)*16 + 16;
                flip = false;
            } else if (map[ypos + 1][xpos+xVel] == null || map[ypos + 1][xpos+xVel].toString().equals("Fruit") && fyv > 0) { // DOWN
                xFree = (xpos + xVel)*16;
                yFree = (++ypos)*16 - 16;
                flip = false;
            }
        } else if (!isX && futureDirIsX) { // horizontal move
            if (map[ypos+yVel][xpos - 1] == null || map[ypos+yVel][xpos - 1].toString().equals("Fruit") && fxv < 0) { // LHS
                xFree = (--xpos)*16 + 16;
                yFree = (ypos+yVel)*16;
                flip = false;
            } else if (map[ypos+yVel][xpos + 1] == null || map[ypos+yVel][xpos + 1].toString().equals("Fruit") && fxv > 0) { // RHS
                xFree = (++xpos)*16 - 16;
                yFree = (ypos+yVel)*16;
                flip = false;
            }
        }
        if (!flip) { 
            freeFound = true; 
        }
    }
    /**
     * Verifies the Cell object is of type Waka specifically, and not its super-classes
     * @return  String - "Waka"
     */
    public String toString() {
        return "Waka";
    }
    /**
     * Return lives of Waka
     * @return  int lives
     */
    public int getLives() {
        return this.lives;
    }
    /**
     * Set waka lives
     * @param   lives   integer of new lives set
     */
    public void setLives(int lives) {
        if (lives <= 0) {
            this.lives = 0;
        } else {
            this.lives = lives;
        }
    }
    /**
     * Restore waka to its starting position
     * Used after Waka occupies same tile as ghost
     * @param   app App object
     */
    public void setDefault(App app) {
        defSprite = app.loadImage("src/main/resources/playerLeft.png");
        isX = true;
        xVel = -1;
        xCoord = startX;
        yCoord = startY;
        lives -= 1;
    }
    /**
     * check if waka is going to "flip" at the nearest intersection or not
     * @return  boolean - going to flip at nearest intersection or not
     */
    public boolean isFlip() {
        return this.flip;
    }
}