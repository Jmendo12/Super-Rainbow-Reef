/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rrgame;

import java.awt.*;
import java.util.Observable;

/**
 *
 * @author curbsidece
 */
public class Bricks extends GameObject {
    
    private int id;
    private int score;
    boolean powerup = false, heal = false;
    
    Bricks(int x, int y, String img, Game game){
        super(x,y,img,game);
        
        switch (img){
            case "Block1": 
                this.id = 1;
                breakable = true;
                score = 10;
                break;
            case "Block2": 
                this.id = 2;
                breakable = true;
                score = 20;
                break;
            case "Block3": 
                this.id = 3;
                breakable = true;
                score = 30;
                break;
            case "Block4": 
                this.id = 4;
                breakable = true;
                score = 40;
                break;
            case "Block5": 
                this.id = 5;
                breakable = true;
                score = 50;
                break;
            case "Block6": 
                this.id = 6;
                breakable = true;
                score = 60;
                break;
            case "Block7": 
                this.id = 7;
                breakable = true;
                score = 70;
                break;  
            case "Block_life":
                this.id = 8;
                breakable = true;
                heal = true;
                break;
            case "Block_split":
                this.id = 9;
                breakable = true;
                powerup = true;
                break;
            default:
                break;
        }
        
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    @Override
    public void update(Observable obv, Object o) {
         
    }

    public void checkCollision() {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(state){
            g2.drawImage(sprite, x, y, null);
        }
    }
    
    @Override
    public String toString(){
        return this.getHitBox().toString();
    }
}
