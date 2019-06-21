package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.*;

public class Skull extends Item{

	public Skull(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	
	@Override
	public boolean isDefeat() {
		return false;
	}
	
	@Override
	public boolean isMove() {
		return false;
	}
	
	@Override
	public String toString() {
		return "s";
	}

}
