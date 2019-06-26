package com.mygdx.game.history;

import java.util.Stack;

public class HistoryStack extends Stack<TurnStack> {
	
	public void unStack() {
		TurnStack stack = this.pop();
		while (!stack.empty()) {
			stack.pop().revertChange();
		}
	}

}
