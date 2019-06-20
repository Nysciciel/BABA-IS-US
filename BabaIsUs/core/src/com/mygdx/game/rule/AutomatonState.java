package com.mygdx.game.rule;

public class AutomatonState {
	
	private String label;	
	
	public AutomatonState(String label) {
		super();
		this.label = label;
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

	public boolean isAnd() {
		return false;
	}

	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return getLabel();
	}

}
