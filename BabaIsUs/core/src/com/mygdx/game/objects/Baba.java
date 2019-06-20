package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Baba extends Item{

	public Baba(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
		textureAtlas = new TextureAtlas(Gdx.files.internal("BabaFox" + "SheetU.txt"));
	}
	@Override
	public boolean isPush(){
		return true;
	}

	@Override
	public boolean isYou1() {
		return true;
	}

	public String[] getSpriteUsed(){
		animationChrono +=Gdx.graphics.getDeltaTime();
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Fox" + orientation + "-0";
		spriteUsed[1]="Fox" + orientation + "-1";
		return(spriteUsed);
	}

	public float[] getAffichePos(){
		float a,b;
		if(animationChrono<0.2){
			switch(orientation){
				case(0):
					a=x+1-animationChrono*5;
					b=y;
					break;
				case(1):
					a=x;
					b=y-1+animationChrono*5;
					break;
				case(2):
					a=x-1+animationChrono*5;
					b=y;
					break;
				default:
					a=x;
					b=y+1-animationChrono*5;
					break;
			}
		}else {
			a=x;
			b=y;
		}
		float[] tab={a,b};
		return(tab);
	}
}
