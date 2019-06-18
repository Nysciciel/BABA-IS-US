package com.mygdx.game.rule;

import java.util.HashSet;

public class AutomatonState extends HashSet<AutomatonState> {
	
	
	public AutomatonState() {
		super();
	}
	
	public boolean isWell() {
		return false;
	}

	public boolean isFinal() {
		return false;
	}

	public boolean isItemState() {
		return false;
	}

}
