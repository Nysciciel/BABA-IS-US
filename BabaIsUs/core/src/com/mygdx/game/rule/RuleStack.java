package com.mygdx.game.rule;

import java.util.LinkedList;

import com.mygdx.game.objects.Item;
import com.mygdx.game.objects.text.Text;

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
		
		LinkedList<Item> s = new LinkedList<Item>(stack);
		Automaton a = automaton.clone();
		
		return new RuleStack(a, s);
	}
	
	public boolean isNextHopAWell(Text text) {		
		
		AutomatonState nextState;
		nextState = automaton.nextHop(text);
		
		return nextState.isWell();
	}
	
	public void add(Item item) {
		stack.add(item);
	}


	public boolean isItemState() {
		return automaton.currentState.isItemState();
	}


	public void pop() {
		stack.pop();
		automaton.goBackToPreviousState();
	}


	public boolean isFinal() {
		return automaton.isFinal();
	}

}
