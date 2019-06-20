package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.GL20;

public class Keke extends Item{

	public Keke(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
		textureAtlas = new TextureAtlas(Gdx.files.internal("KekeSheet.txt"));
	}
	
	@Override
	public boolean isMove() {
		return true;
	}
	
	@Override
	public boolean isPush() {
		return true;
	}
	
	@Override
	public boolean isWin() {
		return true;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Keke0-0";
		spriteUsed[1]="Keke0-1";
		return(spriteUsed);
	}

	public float[] getAffichePos(){
		float[] tab = {x,y};
		return(tab);
	}

}
