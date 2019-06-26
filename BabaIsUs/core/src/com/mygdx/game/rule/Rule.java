package com.mygdx.game.rule;

import java.util.LinkedList;
import com.mygdx.game.objects.text.Relation;
import com.mygdx.game.objects.text.Text;

public class Rule {
	
	private RuleSet rules;
	private LinkedList<Text> textList;

	public Rule(RuleStack ruleStack, RuleSet rules) {
		
		this.rules = rules;
		
		
		// Compressing the NOTs
		textList = new LinkedList<Text>();
		
		Text previous = null;
		for (Text text : ruleStack.getStack()) {
			if (previous != null) {
				if (!text.getName().equals("Not") || !previous.getName().equals("Not")) {
					textList.add(text);
				}
			}
			else {
				textList.add(text);
			}
			
			previous = text;
		}
	}
	
	public String toString() {
		
		String s = new String();
		for (Text text : textList) {
			s += text.toString() + " ";
		}
		
		return s;
	}
	
	public void show() {
	}
	
	public LinkedList<Text> getTextList() {
		return this.textList;
	}
	
	public RuleSet getRuleSet() {
		return this.rules;
	}

	public boolean is() {
		for (Text text : textList) {
			if (text.isIs()) {
				return true;
			}
		}
		return false;
	}
	
	public Relation getRelation() {
		for (Text text : textList) {
			if (text.isRelation()) {
				return (Relation)text;
			}
		}
		return null;
	}

}
