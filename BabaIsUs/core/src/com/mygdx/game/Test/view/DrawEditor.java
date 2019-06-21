package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.*;
import com.mygdx.game.utils.MyTextInputListener;
import com.mygdx.game.utils.ObjectList;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DrawEditor extends Actor{
	
	private int height, width;
	Level lvl;

    public DrawEditor(int width, int height) {
    	
        super();
        this.width = width;
        this.height = height;
        
        lvl = new Level(width,height);
                
    }
    
    public void setItem(ObjectList object, int x, int y, int direction) {
    	float ratioWidth = Gdx.graphics.getWidth()/width;
    	float ratioHeight = Gdx.graphics.getHeight()/height;
    	Location loc = lvl.getLocation((int)(x/ratioWidth), (int)(y/ratioHeight));
    	switch(object) {
    	case BABA:		
    		loc.add(new Baba(loc, direction));
    		break;
    	case ROCK:
    		loc.add(new Rock(loc,direction));
    		break;
    	case WATER:
    		loc.add(new Water(loc,direction));
    		break;
    	case WALL:
    		loc.add(new Wall(loc,direction));
    		break;
    	case EMPTY:
    		break;
    	}
    }
    
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
    	super.draw(batch,parentAlpha);
    	for(int i=0 ; i < height ; i++) {
        	for(int j=0 ; j < width ; j++) {
        		lvl.getLocation(i, j).render(batch);
            }
        }
    	
    }
    
}