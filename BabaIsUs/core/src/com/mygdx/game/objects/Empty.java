package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Level;

public class Empty extends Item{
	public Empty(Level lvl,
			int x, int y, int orientation) {
		super(lvl, x, y, orientation);
	}
	
	@Override
	public boolean isempty() {
		return true;
	}
	@Override
	public void update() {
		this.texture = new Texture("empty" + Integer.toString(orientation)+".png");
	}
}
