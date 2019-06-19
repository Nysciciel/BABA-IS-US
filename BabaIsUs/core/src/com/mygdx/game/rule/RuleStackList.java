package com.mygdx.game.rule;

import java.util.ArrayList;
import java.util.HashSet;

import com.mygdx.game.objects.text.Text;

public class RuleStackList extends ArrayList<RuleStack> {
	
	private HashSet<Rule> rules;

	public RuleStackList(HashSet<Rule> rules) {
		super();
		this.rules = rules;
	}

	public void buildNext(ArrayList<Text> textList, boolean thereIsAnOnOrNearOrFacingOrAnd) {
		
		ArrayList<RuleStack> toBeRemoved = new ArrayList<RuleStack>();
		ArrayList<RuleStack> newRuleStacks = new ArrayList<RuleStack>();

		// And handling in case of a final State
		if (thereIsAnd(textList))
			for (RuleStack ruleStack : this) {	
				if (ruleStack.isFinal()) {
						System.out.println("And + final");
						rules.add(new Rule(ruleStack));
						while(!ruleStack.isRelation()) {
							ruleStack.pop(); //pop and go back to the previous state
							System.out.print("pop : ");
							ruleStack.showPhrase();
						}
						newRuleStacks.add(ruleStack);
				}
			}
		// End of Logic phrases when not AND
		else {
			for (RuleStack ruleStack : this) {
				if (ruleStack.isFinal()) {
					System.out.println("just final");
					rules.add(new Rule(ruleStack));
					toBeRemoved.add(ruleStack);
				}
			}
		}
		this.removeAll(toBeRemoved);
				
		// Create a new Stack for each RuleStack that has an automaton in the AND state
		for (RuleStack ruleStack : this) {
			if (ruleStack.isAnd())
				for (Text text : textList) {
					RuleStack newRuleStack = new RuleStack();
					if (!newRuleStack.isNextHopAWell(text)) {
						newRuleStacks.add(newRuleStack);
						System.out.println("and -> new stack");
					}
				}
		}		
		
		toBeRemoved = new ArrayList<RuleStack>();
		// next state if not a well
		for (RuleStack ruleStack : this) {
			toBeRemoved.add(ruleStack);
			for (Text text : textList) {	
				RuleStack divRuleStack = ruleStack.clone();
				if (!divRuleStack.isNextHopAWell(text)) {
					if (!text.isAnd())
						divRuleStack.add(text);
					newRuleStacks.add(divRuleStack);
					System.out.println("next State + add");
				}	
			}
		}
		this.removeAll(toBeRemoved);
		this.addAll(newRuleStacks);
		
		
		newRuleStacks = new ArrayList<RuleStack>();
		// init new Stacks with an Item ref that is not following 
		if (!thereIsAnOnOrNearOrFacingOrAnd)
			for (Text text : textList) {
				RuleStack ruleStack = new RuleStack();
				if (!ruleStack.isNextHopAWell(text)) {
					ruleStack.add(text);
					newRuleStacks.add(ruleStack);
					System.out.println("init new stack");
				}
			}
		this.addAll(newRuleStacks);

	}
	
	private boolean thereIsAnd(ArrayList<Text> textList) {
		for (Text text : textList) {
			if (text.isAnd())
				return true;
		}
		return false;
	}

	public void show() {
		for (RuleStack stack : this)
			stack.showPhrase();
	}

	public void currentStates() {
		
		for (RuleStack stack : this)
			stack.printCurrentState();
	}

}
