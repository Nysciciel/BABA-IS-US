package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;
import com.mygdx.game.rule.Logic;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.RuleSet;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Baba extends Item{

	public Baba(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	public void loadTextureAtlas(){
		textureAtlas = new TextureAtlas(Gdx.files.internal("BabaFox" + "SheetU.txt"));
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Fox" + orientation + "-0";
		spriteUsed[1]="Fox" + orientation + "-1";
		return(spriteUsed);
	}

}
