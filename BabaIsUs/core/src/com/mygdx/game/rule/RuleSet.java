package com.mygdx.game.rule;

import java.util.HashSet;
import java.util.LinkedList;

public class RuleSet extends HashSet<Rule> {
	
	public RuleSet() {
		super();
	}
	
	public LinkedList<String> getRules(){
		
		LinkedList<String> rules = new LinkedList<String>();
		
		for (Rule rule : this) {
			rules.add(rule.toString());
		}
		
		return rules;
	}

}
