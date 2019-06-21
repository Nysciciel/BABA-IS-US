package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.*;
import com.mygdx.game.utils.Constants;

public class Rock extends Item {

	public Rock(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	@Override
	public boolean isPull() {
		return true;
	}
	@Override
	public boolean isPush() {
		return true;
	}
	@Override
	public boolean isYou() {
		return false;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Rock0";
		spriteUsed[1]="Rock1";
		return(spriteUsed);
	}

}
