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
	
	public Automaton() {
		
		firstState = new AutomatonState();
		finalState = new StateF();
		stateA = new AutomatonStateItem();
		stateOn = new AutomatonState();
		stateIs = new AutomatonState();
		stateO = new AutomatonState();
		
		currentState = firstState;
		
	}
	
	public AutomatonState nextHop(Text text) {
		
		previousState = currentState;
		
		if (currentState == firstState) {
			if (text.isNot())
				return setCurrentState(currentState);
			if (text.isItemRef())
				return setCurrentState(stateA);
		}
		
		if (currentState == stateA) {
			if (text.isOn())
				return setCurrentState(stateOn);
			if (text.isIs())
				return setCurrentState(stateIs);
			if (text.isRelation())
				return setCurrentState(stateO);			
		}
		
		if (currentState == stateOn) {
			if (text.isItemRef())
				return setCurrentState(stateA);
			if (text.isNot())
				return setCurrentState(currentState);
		}

		if (currentState == stateIs) {
			if (text.isNot())
				return setCurrentState(currentState);
			if (text.isItemRef() || text.isProperty())
				return setCurrentState(finalState);
		}

		if (currentState == stateO) {
			if (text.isNot())
				return setCurrentState(currentState);
			if (text.isItemRef())
				return setCurrentState(finalState);
		}

		return setCurrentState(new WellState());
	}
	
	public AutomatonState setCurrentState(AutomatonState newState) {
		currentState = newState;
		return newState;
	}
	
	public Automaton clone() {
		
		Automaton a = new Automaton();
		a.setCurrentState(currentState);
		return a;
	}

	public void goBackToPreviousState() {
		currentState = previousState;
	}

	public boolean isFinal() {
		return currentState.isFinal();
	}

}
