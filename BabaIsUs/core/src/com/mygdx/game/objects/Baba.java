package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Baba extends Item{

	public Baba(Location loc, int orientation) {
		super(loc, orientation);
		isTextureOriented = true;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Fox" + orientation + "-0";
		spriteUsed[1]="Fox" + orientation + "-1";
		return(spriteUsed);
	}

	@Override
	public String toString() {
		return "b";
	}
	

}
