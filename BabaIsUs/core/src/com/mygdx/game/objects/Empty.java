package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.*;

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
		sb.draw(texture, x*32, y*32);
	}

}
