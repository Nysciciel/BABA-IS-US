package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.*;

public class Wall extends Item {

	public Wall(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
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
