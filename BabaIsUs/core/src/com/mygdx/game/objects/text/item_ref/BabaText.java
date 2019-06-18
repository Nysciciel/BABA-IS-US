package com.mygdx.game.objects.text.item_ref;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.text.ItemRef;

public class BabaText extends ItemRef {

	public BabaText(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}

	public void render(SpriteBatch sb){
		sb.draw(texture, x*32, y*32);
	}

}
