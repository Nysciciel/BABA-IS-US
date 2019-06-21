package com.mygdx.game.rule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class RuleSet extends HashSet<Rule> {
	
	public RuleSet() {
		super();
	}
	
	public ArrayList<String> getRules(){
		
		ArrayList<String> rules = new ArrayList<String>();
		
		for (Rule rule : this) {
			rules.add(rule.toString());
		}
		
		return rules;
	}

}
