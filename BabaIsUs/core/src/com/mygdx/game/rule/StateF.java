package com.mygdx.game.rule;

public class StateF extends AutomatonStateItem {
	
	public StateF(String label) {
		super(label);
	}
	
	@Override
	public boolean isFinal() {
		return true;
	}

}
