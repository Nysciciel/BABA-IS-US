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
import com.mygdx.game.Location;
import com.mygdx.game.objects.*;
import com.mygdx.game.utils.MyTextInputListener;
import com.mygdx.game.utils.ObjectList;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DrawEditor extends Actor{
	
	private Location[][] location;
	private int height, width;

    public DrawEditor(int width, int height) {
    	
        super();
        this.width = width;
        this.height = height;
        
        location = new Location[height][width];
        
        for(int i=0 ; i < height ; i++) {
        	for(int j=0 ; j < width ; j++) {
        		ArrayList<Item> item = new ArrayList<Item>();
        		item.add(new Empty(location[i][j],i,j,0));
            	location[i][j] =  new Location(item,null,i,j);
            }
        }
    
        
    }
    
    public void setItem(ObjectList object, int x, int y, int direction) {
    	switch(object) {
    	case BABA:
    		location[x][y].add(new Baba(location[x][y],x,y,direction));
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
        		for(int k=0 ; k<location[i][j].items.size() ; k++) {
        			location[i][j].items.get(k).draw(batch,(int)(i*32 + getX()),(int)( j*32 + getY()));
        		}
            }
        }
    	
    }
    
}