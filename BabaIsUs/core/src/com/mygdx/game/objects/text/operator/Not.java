package com.mygdx.game.objects.text.operator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Operator;

public class Not extends Operator {

	public Not(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);

	}

	public void render(SpriteBatch sb){
		sb.draw(texture, x*32, y*32);
	}

}