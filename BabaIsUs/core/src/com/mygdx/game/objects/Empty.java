package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.*;
import com.mygdx.game.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Empty extends Item{

	public Empty(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}

	@Override
	public boolean isempty() {
		return true;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[1];
		spriteUsed[0]="Empty0";
		return(spriteUsed);
	}
	
	@Override
	public String toString() {
		return "e";
	}

}
