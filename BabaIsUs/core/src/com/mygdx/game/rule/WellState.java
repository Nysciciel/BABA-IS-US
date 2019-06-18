package com.mygdx.game.rule;

public class WellState extends AutomatonState {
	
	public WellState() {
		super();
	}
	
	@Override
	public boolean isWell() {
		return true;
	}

}
