package com.mygdx.game.objects.text.operator;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import com.mygdx.game.*;
import com.mygdx.game.objects.text.Operator;
import com.mygdx.game.utils.Constants;

public class And extends Operator {

	public And(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}

	@Override
	public boolean isAnd() {
		return true;
	}

}
