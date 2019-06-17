package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Level;

public class Wall extends Item {
	public Wall(Level lvl,
			int x, int y, int orientation) {
		super(lvl, x, y, orientation);
		isStop = true;
	}
	@Override
	public void update() {
		this.texture = new Texture("wall" + Integer.toString(orientation)+".png");
	}

}
