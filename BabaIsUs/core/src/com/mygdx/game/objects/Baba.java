package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Baba extends Item{

	public Baba(Location loc, int orientation) {
		super(loc, orientation);
		isTextureOriented = true;
	}
}
