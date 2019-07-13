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
 * @author christinemalicdem
 */
public class BigLeg extends GameObject{
    
    private int id;
    private int health;
    private Game game;
    
    BigLeg(int x, int y, String img, Game currentGame, int ID){
        super(x, y, img, currentGame);
        id = ID;
        health = 50;       
    }

    @Override
    public void update(Observable obv, Object o) {
        checkCollision();
        checkHealth();
    }
    public void checkHealth(){
        if(health == 0)
            state = false;
    }
    
    @Override
    public void checkCollision() {
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if(state)
            g2.drawImage(sprite, null, x, y);
    }
}
