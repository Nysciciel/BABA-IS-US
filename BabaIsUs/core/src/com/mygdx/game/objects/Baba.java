package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Level;

public class Baba extends Item{
	public Baba(Level lvl,
			int x, int y, int orientation) {
		super(lvl, x, y, orientation);
		isYou = true;
	}
	@Override
	public void update() {
		this.texture = new Texture("baba" + Integer.toString(orientation)+".png");
	}
}
