package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

public class Baba extends Item{
	public Baba(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	
	@Override
	public boolean isYou() {
		return true;
	}
	
	@Override
	public boolean isPull() {
		return false;
	}
	
	@Override
	public boolean isMove() {
		return false;
	}
	

	@Override
	public boolean isDefeat() {
		return false;
	}
}
