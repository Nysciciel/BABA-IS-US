package com.mygdx.game.rule;

public class AutomatonStateItem extends AutomatonState {
	
	public AutomatonStateItem(String label) {
		super(label);
	}

	@Override
	public boolean isItemState() {
		return true;
	}

}
