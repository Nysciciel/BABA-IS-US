package com.mygdx.game.rule;

import java.util.LinkedList;

import com.mygdx.game.objects.Item;

public class RuleStack {
	
	Automaton automaton;
	LinkedList<Item> stack;
		
	public RuleStack() {
		super();
		automaton = new Automaton();
		stack = new LinkedList<Item>();
		
	}
	
	
	public RuleStack(Automaton a, LinkedList<Item> s) {
		automaton = a;
		stack = s;
	}
	
	public RuleStack clone() {
		return new RuleStack(automaton, stack);
	}
}
