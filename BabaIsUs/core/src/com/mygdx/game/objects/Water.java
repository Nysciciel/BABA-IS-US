package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

public class Water extends Item {
	public Water(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	@Override
	public void update() {
		this.texture = new Texture("Water" + Integer.toString(orientation)+".png");
	}
	@Override
	public boolean isSink() {
		return true;
	}
}
