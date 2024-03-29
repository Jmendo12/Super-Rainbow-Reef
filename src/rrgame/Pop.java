/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rrgame;

import java.awt.*;
import java.util.Arrays;
import java.util.Observable;

/**
 *
 * @author jmendo12
 */
public class Pop extends GameObject{
    
    private int lives;
    private double angle;
    private double yVel;
    private double xVel;
    private final int SPAWN_Y;
    private final int SPAWN_X;
    private int previous_x;
    private final int SPEED = 9;
    boolean collide = false;
    private Player player;
    
    Pop(int x, int y, String img, Game game){
        
        super(x, y, img, game);
        yVel = SPEED;
        xVel = 0;
        angle = 0;
        SPAWN_Y = y;
        SPAWN_X = x;
        lives = 10;
    }
    
    public int getLives(){
        return lives;
    }
    
    @Override
    public void update(Observable obv, Object o) {
        move();
        checkLives();
    }
    
    private void move(){
        checkCollision();
        checkBorder();
        y += yVel;
        x += xVel;
        this.getHitBox().setBounds(x, y, width, height);
    }
    
    @Override
    public void checkCollision() {
        int size = game.getObjectListSize();
        
        for(int i =0; i< size; i++){
            GameObject temp = game.getObject(i);
            
            if(temp instanceof Pop){
                continue;
            }
            if(this.getBounds().intersects(temp.getBounds())){
                
                if(temp instanceof Player){
                    this.player = (Player) temp;
                    SoundPlayer effect = game.getSoundEffects();
                    effect.setFile("./resources/Sound_katch.wav");
                    Thread sound = new Thread(effect);
                    sound.start();
                    try{
                        sound.join();
                    }catch(InterruptedException e){
                        System.out.println(e.getMessage());
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }

                    if(getX() < temp.getX() + (temp.getWidth() / 2 - 15))
                    {   
                            this.setxVel((xVel - 2));
                            collide = true;
                        
                    }
                    else if(getX() > temp.getX() + (temp.getWidth() / 2 + 15))
                    {   
                            this.setxVel((xVel + 2));
                            collide = true;

                    }
                    this.setyVel(-yVel);
                    break;
                }else if(temp instanceof Bricks){
                    
                    SoundPlayer effect = game.getSoundEffects();
                    effect.setFile("./resources/Sound_block.wav");
                    Thread sound = new Thread(effect);
                    sound.start();
                    try{
                        sound.join();
                    }catch(InterruptedException e){
                        System.out.println(e.getMessage());
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }
                    
                    Rectangle intersectionP = 
                            this.getBounds().intersection(temp.getBounds());
                    
                    if(intersectionP.width >= intersectionP.height){
                        this.setyVel(-yVel);
                    }
                    if(intersectionP.height >= intersectionP.width){
                        this.setxVel(-xVel);
                    }
                    
                    if(temp.isBreakable()){
                        temp.setState(false);
                        Bricks brick = (Bricks) temp;
                        //Check for special bricks
                        if(brick.getId() == 9){
                            game.setDoublePoints(true);
                        }else if(brick.getId() == 8){
                            this.lives += 1;
                        }
                        
                        //Check for double points, then assign points
                        if(game.isDoublePoints()){
                            game.updateScore(brick.getScore() * 2);
                        }else{
                            game.updateScore(brick.getScore());
                        }
                    }
                    break;
                }else if(temp instanceof BigLeg){
                    
                    SoundPlayer effect = game.getSoundEffects();
                    effect.setFile("./resources/Sound_bigleg.wav");
                    Thread sound = new Thread(effect);
                    sound.start();
                    try{
                        sound.join();
                    }catch(InterruptedException e){
                        System.out.println(e.getMessage());
                        System.out.println(Arrays.toString(e.getStackTrace()));
                    }
                    
                    Rectangle intersectionP = 
                            this.getBounds().intersection(temp.getBounds());
                    if(intersectionP.width >= intersectionP.height)
                        this.setyVel(-yVel);
                    if(intersectionP.height >= intersectionP.width)
                        this.setxVel(-xVel);
                    
                    temp.setState(false);
                    game.updateScore(50);
                    game.decrementBigLeg();
                    
                    if(game.getNumBigLegs() == 0){
                        System.out.println("You win!");
                        
                        System.out.println("Score: " + game.getScore());
                    }
                    
                    break;
                }
            }
        }
    }
    
    private void checkLives(){
        if(lives == 0){
            state = false;
        }else{}
    }
    
    @Override
    void checkBorder(){
        if(x + getWidth() >= Game.MAX_X + 2){
            this.xVel = -xVel;
            this.yVel = -yVel;
        }
        else if(x <= Game.MIN_X){
            this.xVel = -xVel;
            this.yVel = -yVel;
        }
        
        if(y >= Game.SCREEN_HEIGHT){
            y = this.player.y - 200;
            x = this.player.x + 20;
            this.setxVel(0);
            this.setyVel(SPEED);
            playLiveLost();
            lives--;
            collide = false;
            
        }
        else if(y <= Game.MIN_Y - 40){
            this.setyVel(-yVel);
        }
    }
    
    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(state){
            g2.drawImage(sprite, null, x, y);
            //System.out.println(toString());
        }
    }
    private void playLiveLost(){
        
        SoundPlayer effect = game.getSoundEffects();
        effect.setFile("./resources/Sound_lost.wav");
        Thread sound = new Thread(effect);
        sound.start();
        try{
            sound.join();
        }catch(InterruptedException e){
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
    @Override
    public String toString(){
        return "y is " + y + " x is " + x + " collide: " + collide;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public double getxVel() {
        return xVel;
    }

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }
}