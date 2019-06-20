package com.mygdx.game.objects.text.relation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Relation;
import com.mygdx.game.utils.Constants;

public class Is extends Relation {

	public Is(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}

	
	@Override
	public boolean isIs() {
		return true;
	}

	public void render(SpriteBatch sb){
		int h_ratio = Constants.WINDOW_HEIGHT/(loc.getLevelHeigh());
		int w_ratio = Constants.WINDOW_WIDTH/(loc.getLevelWidth());
		int size = Math.min(h_ratio,w_ratio);
		sb.draw(texture,x*size,y*size,size,size);
	}

}
