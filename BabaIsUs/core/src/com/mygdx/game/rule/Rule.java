package com.mygdx.game.rule;

import java.util.ArrayList;
import java.util.HashSet;

import com.mygdx.game.objects.text.Text;

public class Rule {
	
	private HashSet<Rule> rules;

	public Rule(RuleStack ruleStack, HashSet<Rule> rules) {
		
		this.rules = rules;
		
		System.out.println("");
		System.out.println("+++++++++++++++++++++++++Here is the Rule++++++++++++++++++++++++++");
		
		// show elements
		ruleStack.showPhrase();
		System.out.println("===================================================================");
		System.out.println("");
		
		// Compressing the NOTs
		ArrayList<Text> simplified = new ArrayList<Text>();
		
		Text previous = null;
		for (Text text : ruleStack.getStack()) {
			if (previous != null) {
				if (!text.getName().equals("Not") || !previous.getName().contentEquals("Not")) {
					simplified.add(text);
				}
			}
			previous = text;
		}
		
		
	}
	
	

}
