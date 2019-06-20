package com.mygdx.game.rule;

import java.util.ArrayList;
import java.util.HashSet;

import com.mygdx.game.objects.text.Text;

public class RuleStackList extends ArrayList<RuleStack> {

	private RuleSet rules;

	public RuleStackList(RuleSet rules) {
		super();
		this.rules = rules;
	}

	public void buildNext(ArrayList<Text> textList, boolean thereIsAnOnOrNearOrFacingOrAnd, boolean thereIsANot) {

		ArrayList<RuleStack> toBeRemoved = new ArrayList<RuleStack>();
		ArrayList<RuleStack> newRuleStacks = new ArrayList<RuleStack>();

		System.out.println("thereIsAnOnOr... = "+thereIsAnOnOrNearOrFacingOrAnd);
		System.out.println("thereIsANot = "+thereIsANot);

		// And handling in case of a final State
		if (thereIsAnd(textList))
			for (RuleStack ruleStack : this) {	
				if (ruleStack.isFinal()) {
					System.out.println("And + final");
					rules.add(new Rule(ruleStack, rules));
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
					rules.add(new Rule(ruleStack, rules));
					toBeRemoved.add(ruleStack);
				}
			}
		}
		this.removeAll(toBeRemoved);

		System.out.println("            11111111111111111");
		// Create a new Stack for each RuleStack that has an automaton in the AND state
		for (RuleStack ruleStack : this) {
			if (ruleStack.isAnd()) {
				for (Text text : textList) {
					RuleStack newRuleStack = new RuleStack();
					// Cas où on est juste après le AND et qu'on doit faire une nouvelle stackRule
					if (!newRuleStack.isNextHopAWell(text) && thereIsAnOnOrNearOrFacingOrAnd && !thereIsANot) {
						newRuleStack.add(text); 
						newRuleStacks.add(newRuleStack);
						System.out.println("and -> new stack");
					}
				}				
			}
		}	

		System.out.println("            22222222222222222");
		toBeRemoved = new ArrayList<RuleStack>();
		// next state if not a well
		for (RuleStack ruleStack : this) {
			toBeRemoved.add(ruleStack);
			// si dans l'état AND et qu'on a fini les NOT (ou bien que y en a pas)
			// Permet que la stack ne se duplique pas d'autant que d'itemRefs présents sur la case de lecture
			if (ruleStack.isAnd() && atLeastOneItemRef(textList)) {
				RuleStack divRuleStack = ruleStack.clone();
				divRuleStack.isNextHopAWell(null);
				newRuleStacks.add(divRuleStack);
				System.out.println("save stacks in AND state");
				divRuleStack.show();
			}


			// Classic next State
			else {
				System.out.println("            333333333333333333");
				for (Text text : textList) {	
					RuleStack divRuleStack = ruleStack.clone();
					// Si l'état correspond a une regexp (formant une vraie Rule !)
					if (!divRuleStack.isNextHopAWell(text)) {
						// Si on est pas dans l'état AND et qu'on ne lit pas AND
						if (!text.isAnd() && !ruleStack.isAnd()) {
							divRuleStack.add(text);
							System.out.println("// add text");
						}
						newRuleStacks.add(divRuleStack); // TODO : filter so that BABA are not duplicated !!!!
						System.out.println("next State + add");
						divRuleStack.show();
					}
				}	
			}
		}
		this.removeAll(toBeRemoved);

		System.out.println("            44444444444444444444");
		// init new Stacks with an Item ref that is not following  ON / NEAR / FACING / AND
		if ((!thereIsAnOnOrNearOrFacingOrAnd && !thereIsANot) || (this.isEmpty() && newRuleStacks.isEmpty()))
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

	private boolean thereIsNot(ArrayList<Text> textList) {
		for (Text text : textList) {
			if (text.isNot())
				return true;
		}
		return false;
	}

	private boolean atLeastOneItemRef(ArrayList<Text> textList) {
		for (Text text : textList)
			if (text.isItemRef())
				return true;
		return false;
	}

	public void show() {
		for (RuleStack stack : this)
			stack.show();
	}

	public void currentStates() {

		for (RuleStack stack : this)
			stack.printCurrentState();
	}

}
