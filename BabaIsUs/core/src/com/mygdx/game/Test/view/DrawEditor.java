package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.*;
import com.mygdx.game.objects.text.item_ref.BabaText;
import com.mygdx.game.objects.text.item_ref.RockText;
import com.mygdx.game.utils.MyTextInputListener;
import com.mygdx.game.utils.ObjectList;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class DrawEditor extends Actor{
	
	private int height, width;
	Level lvl;
	ShapeRenderer shapeRenderer;
	
    public DrawEditor(int width, int height) {
    	
        super();
        this.width = width;
        this.height = height;
        
        shapeRenderer = new ShapeRenderer();
        lvl = new Level(width,height);
                
    }
    
    public void setItem(ObjectList object, int x, int y, int direction) {
    	float ratioWidth = this.getWidth()/width;
    	float ratioHeight = this.getHeight()/height;
    	float size = Math.min(ratioWidth, ratioHeight);
    	Location loc = lvl.getLocationMatrix()[(int) (y/size)][(int) (x/size)];
    	
    	if(object == ObjectList.EMPTY) {
    		loc.getItems().clear();
    	} else {
    		try {
    			loc.add((Item) object.getClazz().getConstructor(Location.class, int.class).newInstance(loc,direction));
    		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
    				| NoSuchMethodException | SecurityException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
 
    }
    
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
    	super.draw(batch,parentAlpha);
    	
    	for(int i=0 ; i < height ; i++) {
        	for(int j=0 ; j < width ; j++) {
        		lvl.render(batch,(int) Math.min(this.getWidth()/width, this.getHeight()/height));
            }
        }
    	drawRepere(batch);
    	
    }
    
    public void clear() {
    	lvl = new Level(10,10);
    }
    
    public void drawRepere(Batch batch) {
    	float ratioWidth = this.getWidth()/width;
    	float ratioHeight = this.getHeight()/height;
    	float size = Math.min(ratioWidth, ratioHeight);
    	shapeRenderer.begin(ShapeType.Line);
    	shapeRenderer.setColor(0.7f, 0.7f, 0.7f, 1);
        for(int i=0 ; i<= height ; i++) {
        	shapeRenderer.line(0, i*size, size*width, i*size);
        }
        for(int i=0 ; i<= width ; i++) {
        	shapeRenderer.line(i*size, 0 , i*size, size*height);
        }
        shapeRenderer.end();
    }
    
    public Level getLevel() {
    	return this.lvl;
    }
    
    
}