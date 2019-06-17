package com.mygdx.game;

import java.util.Stack;

import com.mygdx.game.objects.Item;

public class RuleStack extends Stack<Item> {
	
	private boolean hasRelation;
	
	public RuleStack() {
		super();
		hasRelation = false;
	}
	
	public boolean getHasRelation() {
		return hasRelation;
	}
	
	public void setHasRelation(boolean b) {
		hasRelation = b;
	}

}
