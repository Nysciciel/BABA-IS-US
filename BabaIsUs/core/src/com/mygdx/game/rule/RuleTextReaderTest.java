package com.mygdx.game.rule;

import java.util.ArrayList;
import java.util.HashSet;


import com.mygdx.game.objects.text.item_ref.*;
import com.mygdx.game.objects.text.operator.*;
import com.mygdx.game.objects.text.relation.*;
import com.mygdx.game.objects.text.property.*;
import com.mygdx.game.objects.text.*;

public class RuleTextReaderTest {
	
	private HashSet<Rule> rules;
	private ArrayList<ArrayList<Text>> textLigne;

	
	public RuleTextReaderTest() {
		
		rules = new HashSet<Rule>();
		
		textLigne = new ArrayList<ArrayList<Text>>();
		
		
		ArrayList<Text> dgfglkedsgl = new ArrayList<Text>();
		dgfglkedsgl.add(new On(null, 0, 0, 0));
		textLigne.add(dgfglkedsgl);
		
		
		
		ArrayList<Text> lqsvh = new ArrayList<Text>();
		lqsvh.add(new Not(null, 0, 0, 0));
		textLigne.add(lqsvh);
		
		ArrayList<Text> dgfgl5 = new ArrayList<Text>();
		dgfgl5.add(new BabaText(null, 0, 0, 0));
		textLigne.add(dgfgl5);
		
		ArrayList<Text> KSUFPU = new ArrayList<Text>();
		KSUFPU.add(new And(null, 0, 0, 0));
		KSUFPU.add(new On(null, 0, 0, 0));
		textLigne.add(KSUFPU);
		
		ArrayList<Text> dgfgl = new ArrayList<Text>();
		dgfgl.add(new KekeText(null, 0, 0, 0));
		textLigne.add(dgfgl);
		
		ArrayList<Text> dgfgl2 = new ArrayList<Text>();
		dgfgl2.add(new Is(null, 0, 0, 0));
		textLigne.add(dgfgl2);
		
		
		
		ArrayList<Text> dgfgl7 = new ArrayList<Text>();
		dgfgl7.add(new Not(null, 0, 0, 0));
		textLigne.add(dgfgl7);
		
		
		
		ArrayList<Text> dgfgl8 = new ArrayList<Text>();
		dgfgl8.add(new You(null, 0, 0, 0));
		textLigne.add(dgfgl8);
		
		ArrayList<Text> dgfgl13 = new ArrayList<Text>();
		dgfgl13.add(new And(null, 0, 0, 0));
		textLigne.add(dgfgl13);
		
		ArrayList<Text> dgfgl56 = new ArrayList<Text>();
		dgfgl56.add(new On(null, 0, 0, 0));
		textLigne.add(dgfgl56);
		
		
		ArrayList<Text> dgfgl3 = new ArrayList<Text>();
		dgfgl3.add(new KekeText(null, 0, 0, 0));
		textLigne.add(dgfgl3);
		
		
		
		
		ArrayList<Text> dgfgl4 = new ArrayList<Text>();
		dgfgl4.add(new And(null, 0, 0, 0));
		textLigne.add(dgfgl4);
		
		
		
		
		
		
		
		ArrayList<Text> dgfgl57 = new ArrayList<Text>();
		dgfgl57.add(new You(null, 0, 0, 0));
		textLigne.add(dgfgl57);
		
		
		textLigne.add(new ArrayList<Text>());
		
		readRules();

	}
	
	public void readRules() {
		
			
			System.out.println("readRules");
			
		    RuleStackList currentRules; 
			boolean thereIsAnOnOrNearOrFacingOrAnd = false;
			boolean thereIsANot_ = false;
			
			currentRules = new RuleStackList(rules);
			for (ArrayList<Text> textList : textLigne) {
				
				System.out.println();
				System.out.println();
				System.out.print("            READING : ");
				for (Text text : textList) {
					text.show();
					System.out.print("    ");
				}
				System.out.println();
				
				currentRules.buildNext(textList, thereIsAnOnOrNearOrFacingOrAnd, thereIsANot_);
				if (!thereIsANot(textList)) {
					thereIsAnOnOrNearOrFacingOrAnd = thereIsAOn(textList) ||thereIsAAnd(textList);
					thereIsANot_ = false;
				}
				else {
					thereIsANot_ = true;
				}
				
				System.out.println("           show stacks :");
				currentRules.show();
				System.out.println("-------------------------------");
				System.out.println(currentRules.toString());
				//System.out.print("Current States : ");
				//currentRules.currentStates();
				System.out.println();
				
				
			}
	}
	
	private boolean thereIsANot(ArrayList<Text> textList) {
		
		for (Text text : textList)
			if (text.isNot())
				return true;		
		return false;
	}

	public boolean thereIsAOn(ArrayList<Text> textList) {
		
		for (Text text : textList)
			if (text.isOn())
				return true;
		return false;		
	}
	
	public boolean thereIsAAnd(ArrayList<Text> textList) {
		
		for (Text text : textList)
			if (text.isAnd())
				return true;
		return false;		
	}
}
