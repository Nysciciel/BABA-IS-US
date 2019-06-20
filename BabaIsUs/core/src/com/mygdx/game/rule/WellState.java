package com.mygdx.game.rule;

public class WellState extends AutomatonState {
	
	public WellState(String label) {
		super(label);
	}
	
	@Override
	public boolean isWell() {
		return true;
	}

}
