package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.*;
import com.mygdx.game.utils.Constants;

public class Wall extends Item {

	public Wall(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	@Override
	public boolean isPush() {
		return true;
	}

	public void loadTextureAtlas(){
		textureAtlas = new TextureAtlas(Gdx.files.internal("WallSheetU.txt"));
	}

	public String[] getSpriteUsed(){
		String s = "";
		for(int i=0;i<=3;i++){
			if(isNeighbourEqual(i)){
				s += i;
			}
		}
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Wall-" + s + "-0";
		spriteUsed[1]="Wall-" + s + "-1";
		return(spriteUsed);
	}
	
	@Override
	public String toString() {
		return "w";
	}

}
