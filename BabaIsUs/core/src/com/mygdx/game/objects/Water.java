package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.*;
import com.mygdx.game.utils.Constants;

public class Water extends Item {
	private TextureAtlas textureAtlas;
	private Animation animation;
	private float elapsedTime = 0;

	public Water(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	@Override
	public boolean isPush() {
		return true;
	}

	public void render(SpriteBatch sb){
		String s = "";
		for(int i=0;i<=3;i++){
			if(isNeighbourEqual(i)){
				s += i;
			}
		}
		textureAtlas = new TextureAtlas(Gdx.files.internal("WaterSheetU.txt"));
		TextureRegion[] orientedWall = new TextureRegion[2];
		orientedWall[0]=textureAtlas.findRegion("Water-" + s + "-0");
		orientedWall[1]=textureAtlas.findRegion("Water-" + s + "-1");
		animation = new Animation(1/3f, orientedWall);
		elapsedTime += Gdx.graphics.getDeltaTime();
		TextureRegion test = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
		int h_ratio = Constants.WINDOW_HEIGHT/(loc.getLevelHeigh());
		int w_ratio = Constants.WINDOW_WIDTH/(loc.getLevelWidth());
		int size = Math.min(h_ratio,w_ratio);
		sb.draw(test,x*size,y*size,size,size);
	}

	public void dispose(){
		textureAtlas.dispose();
		texture.dispose();
	}
}
