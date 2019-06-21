package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.*;

public class Water extends Item {

	public Water(Location loc, int orientation) {
		super(loc, orientation);
	}

	private float fishTime = 2;

	
	@Override
	public boolean isPush() {
		return true;
	}

	public void loadTextureAtlas(){
		textureAtlas = new TextureAtlas(Gdx.files.internal("WaterSheetU.txt"));
	}

	public String[] getSpriteUsed(){
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
			String[] spriteUsed = new String[2];
			spriteUsed[0]="WaterFish-0";
			spriteUsed[1]="WaterFish-1";
			return(spriteUsed);
		}else {
			fishTime += Gdx.graphics.getDeltaTime();
			String[] spriteUsed = new String[2];
			spriteUsed[0]="Water-" + s + "-0";
			spriteUsed[1]="Water-" + s + "-1";
			return(spriteUsed);
		}
	}

}
