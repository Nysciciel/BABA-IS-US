package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.*;

public class Water extends Item {

	public Water(Location loc, int orientation) {
		super(loc, orientation);
		prio=1;
	}

	private float fishTime = 2;

	public void loadTextureAtlas(){
		textureAtlas = new TextureAtlas(Gdx.files.internal("WaterSheetU.txt"));
	}
	
	@Override
	public String getSpriteID(int id){
		String s = "";
		for(int i=0;i<=3;i++){
			if(isNeighbourEqual(i)){
				s += i;
			}
		}
		double fishRand = Math.random();
		if(fishRand<0.005 && fishTime>1){
			fishTime = 0;
		}
		
		if(s.equals("0123") && fishTime<0.6) {
			fishTime += Gdx.graphics.getDeltaTime();
			return "WaterFish-"+id;
		}
		else {
			fishTime += Gdx.graphics.getDeltaTime();
			return "Water-" + s + "-"+id;
		}
	}
}
