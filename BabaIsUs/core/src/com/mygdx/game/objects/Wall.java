package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.*;

public class Wall extends Item {
	public Wall(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	@Override
	public void update() {
		this.texture = new Texture("wall" + Integer.toString(orientation)+".png");
	}
	@Override
	public boolean isStop() {
		return true;
	}

	public void render(SpriteBatch sb){
		sb.draw(texture, x*32, y*32);
	}

}
