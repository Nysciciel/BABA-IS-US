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
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;

	public Baba(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	@Override
	public boolean isPush(){
		return true;
	}

	@Override
	public boolean isYou1() {
		return true;
	}

	public void render(Batch sb){
		animationChrono +=Gdx.graphics.getDeltaTime();
		textureAtlas = new TextureAtlas(Gdx.files.internal("BabaFoxSheetU.txt"));
		TextureRegion[] orientedBaba = new TextureRegion[2];
		orientedBaba[0] = textureAtlas.findRegion("Fox"+orientation+"-0");
		orientedBaba[1] = textureAtlas.findRegion("Fox"+orientation+"-1");
		animation = new Animation(1/3f, orientedBaba);
		elapsedTime += Gdx.graphics.getDeltaTime();
		TextureRegion test = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
		int h_ratio = Constants.WINDOW_HEIGHT/(loc.getLevelHeigh());
		int w_ratio = Constants.WINDOW_WIDTH/(loc.getLevelWidth());
		int size = Math.min(h_ratio,w_ratio);
		if(animationChrono<0.2){
			switch(orientation){
				case(0):
					sb.draw(test, (x+1-animationChrono*5) * size, y * size, size, size);
					break;
				case(1):
					sb.draw(test, x * size, (y-1+animationChrono*5) * size, size, size);
					break;
				case(2):
					sb.draw(test, (x-1+animationChrono*5) * size, y * size, size, size);
					break;
				case(3):
					sb.draw(test, x * size, (y+1-animationChrono*5) * size, size, size);
					break;
			}
		}else {
			sb.draw(test, x * size, y * size, size, size);
		}
	}

	public void dispose(){
		textureAtlas.dispose();
		texture.dispose();
	}

}
