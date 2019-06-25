package com.mygdx.game.rule;

import java.util.HashSet;

import com.mygdx.game.objects.text.Text;

public class Automaton {
	
	AutomatonState currentState;
	AutomatonState previousState;
	AutomatonState firstState;
	StateF finalState;
	AutomatonState stateA;
	AutomatonState stateOn;
	AutomatonState stateIs;
	AutomatonState stateO;
	HashSet<AutomatonState> states;
	AutomatonStateAnd stateAnd;
	
	public Automaton() {
		
		firstState = new AutomatonState("firstState");
		finalState = new StateF("finalState");
		stateA = new AutomatonStateItem("stateA");
		stateAnd = new AutomatonStateAnd("stateAnd");
		stateOn = new AutomatonState("stateOn");
		stateIs = new AutomatonState("stateIs");
		stateO = new AutomatonState("stateO");
		
		currentState = firstState;
	}
	
	public AutomatonState nextHop(Text text) {
		
		
		previousState = currentState;
		
		if (currentState.getLabel() == "firstState") {
			if (text.isNot())
				return currentState = (currentState);
			if (text.isItemRef())
				return currentState = (stateA);
		}
		
		if (currentState.getLabel() == "stateA") {
			if (text.isOn())
				return currentState = (stateOn);
			if (text.isIs())
				return currentState = (stateIs);
			if (text.isRelation())
				return currentState = (stateO);			
			if (text.isAnd())
				return currentState = (stateAnd);
		}
		
		if (currentState.getLabel() == "stateAnd") {
			if (text == null || text.isItemRef())
				return currentState = (stateA);
			if (text.isNot())
				return currentState = (stateAnd);
			
		}
		
		if (currentState.getLabel() == "stateOn") {
			if (text.isItemRef())
				return currentState = (stateA);
			if (text.isNot())
				return currentState = (currentState);
		}

		if (currentState.getLabel() == "stateIs") {
			if (text.isNot())
				return currentState = (currentState);
			if (text.isItemRef() || text.isProperty())
				return currentState = (finalState);
		}

		if (currentState.getLabel() == "stateO") {
			if (text.isNot())
				return currentState = (currentState);
			if (text.isItemRef())
				return currentState = (finalState);
		}

		return currentState = (new WellState("well"));
	}
	
	public void setCurrentState(AutomatonState newState) {
		currentState = newState;
	}
	
	public Automaton clone() {
		
		Automaton a = new Automaton();
		a.setCurrentState(new AutomatonState(this.currentState.getLabel()));
		return a;
	}

	public void goBackToPreviousState() {
		currentState = previousState;
	}

	public boolean isFinal() {
		return currentState.isFinal();
	}
	
	public boolean isAnd() {
		return currentState.isAnd();
	}

	public void printState() {
		System.out.print(" __"+currentState.toString()+"   ");
	}
}
