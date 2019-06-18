package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Baba extends Item{
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;

	public Baba(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	@Override
	public void update() {
		this.texture = new Texture("baba" + Integer.toString(orientation)+".png");
	}
	
	@Override
	public boolean isYou() {
		return true;
	}

	public void render(SpriteBatch sb){
		textureAtlas = new TextureAtlas(Gdx.files.internal("BabaFoxSheet" + Integer.toString(orientation)+ ".txt"));
		animation = new Animation(1/3f, textureAtlas.getRegions());
		elapsedTime += Gdx.graphics.getDeltaTime();
		TextureRegion test = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
		sb.draw(test, x*32, y*32);
	}

	public void dispose(){
		textureAtlas.dispose();
		texture.dispose();
	}

}
