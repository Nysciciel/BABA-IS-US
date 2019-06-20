package com.mygdx.game.rule;

import java.util.LinkedList;

import com.mygdx.game.objects.Item;
import com.mygdx.game.objects.text.Text;

public class RuleStack {
	
	Automaton automaton;
	LinkedList<Text> stack;
		
	public RuleStack() {
		super();
		automaton = new Automaton();
		stack = new LinkedList<Text>();
	}
	
	
	public RuleStack(Automaton a, LinkedList<Text> s) {
		automaton = a;
		stack = s;
	}
	
	public RuleStack clone() {
		
		LinkedList<Text> s = new LinkedList<Text>(stack);
		Automaton a = automaton.clone();
		
		return new RuleStack(a, s);
	}
	
	public boolean isNextHopAWell(Text text) {		
		
		AutomatonState nextState;
		nextState = automaton.nextHop(text);
		
		return nextState.isWell();
	}
	
	public void add(Text text) {
		stack.add(text);
	}


	public boolean isItemState() {
		return automaton.currentState.isItemState();
	}


	public void pop() {
		stack.removeLast();
		automaton.goBackToPreviousState();
	}


	public boolean isFinal() {
		return automaton.isFinal();
	}


	public boolean isAnd() {
		return automaton.isAnd();
	}


	public void showPhrase() {
		if (stack.isEmpty())
			System.out.print("_");
		else
			for (Text text : stack)
				System.out.print(text.getClass().getSimpleName() + "     ");
		System.out.println();
	}


	public void printCurrentState() {
		automaton.printState();
	}


	public boolean isRelation() {
		return stack.getLast().isRelation();
	}


	public void show() {
		
		printCurrentState();
		showPhrase();
	}


}
