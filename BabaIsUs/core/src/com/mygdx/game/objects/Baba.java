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

	public Baba(Location loc, LogicHashtable ruleTable, int x, int y, int orientation) {
		super(loc, ruleTable, x, y, orientation);
		// TODO Auto-generated constructor stub
	}

	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;


	@Override
	public boolean isYou() {
		try {

			System.out.println(getRuleTable());
			System.out.println(" "+((Logic)getRuleTable().get("You").get("Baba")).getTruth(this));
			System.out.println("On "+((Logic)getRuleTable().get("You").get("Baba")).onTruth(this));
			System.out.println("Near "+((Logic)getRuleTable().get("You").get("Baba")).nearTruth(this));
			System.out.println("Facing "+((Logic)getRuleTable().get("You").get("Baba")).facingTruth(this));
		}
		catch(Exception e) {
			System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+- Nope");
		}

		return super.isYou();
	}

	public void loadTextureAtlas(){
		textureAtlas = new TextureAtlas(Gdx.files.internal("BabaFox" + "SheetU.txt"));
	}
	@Override
	public boolean isPush(){
		return false;
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Fox" + orientation + "-0";
		spriteUsed[1]="Fox" + orientation + "-1";
		return(spriteUsed);
	}

}
