package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Level;

public class Rock extends Item {
	public Rock(Level lvl,
			int x, int y, int orientation) {
		super(lvl, x, y, orientation);
		defaultisPush = true;
		isPush = defaultisPush;
	}
	@Override
	public void update() {
		this.texture = new Texture("rock" + Integer.toString(orientation)+".png");
	}
}
