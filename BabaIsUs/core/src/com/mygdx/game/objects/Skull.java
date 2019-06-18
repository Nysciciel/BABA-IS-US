package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

public class Skull extends Item{
	public Skull(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	@Override
	public void update() {
		this.texture = new Texture("Skull" + Integer.toString(orientation)+".png");
	}
	
	@Override
	public boolean isDefeat() {
		return true;
	}
	
	@Override
	public boolean isMove() {
		return true;
	}
}
