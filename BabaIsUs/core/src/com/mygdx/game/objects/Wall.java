package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.*;

public class Wall extends Item {

	public Wall(Location loc, int orientation) {
		super(loc, orientation);
	}

	public void loadTextureAtlas(){
		textureAtlas = new TextureAtlas(Gdx.files.internal("WallSheetGlued.txt"));
	}
	
	public String getSpriteID(int id) {
		String s = "";
		for(int i=0;i<=3;i++){
			if(isNeighbourEqual(i)){
				s += i;
			}
		}
		return this.getName()+"-"+s+"-"+id;	
	}
}
