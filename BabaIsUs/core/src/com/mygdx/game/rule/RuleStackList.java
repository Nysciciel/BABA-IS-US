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

	public void buildNext(ArrayList<Text> textList, boolean thereIsAnOnOrNearOrFacing) {
		
		if (thereIsAnd(textList))
			for (RuleStack ruleStack : this) {
				if (ruleStack.isItemState()) {
					if (ruleStack.isFinal()) {
						rules.add(new Rule(ruleStack));
						ruleStack.pop(); //pop and go back to the previous state
					}
					else {
						RuleStack newRuleStack = ruleStack.clone();
						newRuleStack.pop(); //pop and go back to the previous state
						this.add(newRuleStack);
					}
				}
			}
		
		for (Text text : textList) {
			for (RuleStack ruleStack : this) {
				RuleStack divRuleStack = ruleStack.clone();
				if (!divRuleStack.isNextHopAWell(text));
					this.add(divRuleStack);
			}
		}
		
		if (!thereIsAnOnOrNearOrFacing)
			for (Text text : textList) {
				RuleStack ruleStack = new RuleStack();
				if (!ruleStack.isNextHopAWell(text));
					this.add(ruleStack);
			}
	}
	
	private boolean thereIsAnd(ArrayList<Text> textList) {
		for (Text text : textList) {
			if (text.isAnd())
				return true;
		}
		return false;
	}

}
