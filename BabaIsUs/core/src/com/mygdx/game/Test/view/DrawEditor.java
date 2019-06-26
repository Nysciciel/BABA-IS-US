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
	private Level lvl;
	private ShapeRenderer shapeRenderer;
	private String file;
	
    public DrawEditor(int width, int height, String file) {
    	
        super();
        this.width = width;
        this.height = height;
        this.file = file;
        if(Gdx.files.internal("Level/"+file).exists()) {
        	lvl = new Level(file);
        } else {
        	lvl = new Level(width,height);
        }
        shapeRenderer = new ShapeRenderer();
        
    }
    
    public void setLarg(int W) {
    	this.width = W;
    	clear();
    }
    
    public void setHaut(int H) {
    	this.height = H;
    	clear();
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
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
 
    }
    
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
    	//super.draw(batch,parentAlpha);
    	
    	drawRepere(batch);
    	
    	lvl.render(batch,(int) Math.min(this.getWidth()/width, this.getHeight()/height));
    	
    }
    
    public void clear() {
    	lvl = new Level(this.width,this.height);
    }
    
    public void drawRepere(Batch batch) {
    	float ratioWidth = this.getWidth()/width;
    	float ratioHeight = this.getHeight()/height;
    	float size = Math.min(ratioWidth, ratioHeight);
    	batch.end();
    	shapeRenderer.begin(ShapeType.Line);
    	shapeRenderer.setColor(0.7f, 0.7f, 0.7f, 1);
        for(int i=0 ; i<= height ; i++) {
        	shapeRenderer.line(0, i*size, size*width, i*size);
        }
        for(int i=0 ; i<= width ; i++) {
        	shapeRenderer.line(i*size, 0 , i*size, size*height);
        }
        shapeRenderer.end();
        batch.begin();
    }
    
    public Level getLevel() {
    	return this.lvl;
    }
    
    
}