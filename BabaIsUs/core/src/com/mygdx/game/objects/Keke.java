package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.*;

public class Keke extends Item{
	public Keke(Location loc,
			int x, int y, int orientation) {
		super(loc, x, y, orientation);
	}
	
	@Override
	public boolean isShift() {
		return true;
	}
	
	@Override
	public boolean isPush() {
		return false;
	}
	
	@Override
	public boolean isWin() {
		return false;
	}
}
