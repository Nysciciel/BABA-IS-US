package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.*;
import com.mygdx.game.utils.Constants;

public class Empty extends Item{
	public Empty(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	
	@Override
	public boolean isempty() {
		return true;
	}
	@Override
	public void update() {
		this.texture = new Texture("empty" + Integer.toString(orientation)+".png");
	}

	public void render(SpriteBatch sb){
		int h_ratio = Constants.WINDOW_HEIGHT/(loc.getLevelHeigh());
		int w_ratio = Constants.WINDOW_WIDTH/(loc.getLevelWidth());
		int size = Math.min(h_ratio,w_ratio);
		sb.draw(texture,x*size,y*size,size,size);
	}

}
