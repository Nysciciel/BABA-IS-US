package com.mygdx.game.rule;

public class AutomatonStateAnd extends AutomatonState {
	
	public AutomatonStateAnd(String label) {
		super(label);
	}

	@Override
	public boolean isAnd() {
		return true;
	}

}
