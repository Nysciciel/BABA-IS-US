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
import com.badlogic.gdx.graphics.g2d.Batch;

public class Rock extends Item {
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;

	public Rock(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
		textureAtlas = new TextureAtlas(Gdx.files.internal("RockSheet.txt"));
	}
	@Override
	public boolean isPull() {
		return false;
	}
	@Override
	public boolean isPush() {
		return false;
	}
	@Override
	public boolean isMove() {
		return true;
	}
	@Override
	public boolean isYou2() {
		return false;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Rock0";
		spriteUsed[1]="Rock1";
		return(spriteUsed);
	}

	public float[] getAffichePos(){
		float[] tab = {x,y};
		return(tab);
	}

}
