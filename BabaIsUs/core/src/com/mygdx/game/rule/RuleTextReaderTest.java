package com.mygdx.game.rule;

import java.util.ArrayList;
import java.util.HashSet;

import com.mygdx.game.objects.text.Text;
import com.mygdx.game.objects.text.item_ref.BabaText;
import com.mygdx.game.objects.text.item_ref.KekeText;
import com.mygdx.game.objects.text.item_ref.WallText;
import com.mygdx.game.objects.text.operator.And;
import com.mygdx.game.objects.text.property.You;
import com.mygdx.game.objects.text.relation.Is;

public class RuleTextReaderTest {
	
	private HashSet<Rule> rules;
	private ArrayList<ArrayList<Text>> textLigne;

	
	public RuleTextReaderTest() {
		
		rules = new HashSet<Rule>();
		
		textLigne = new ArrayList<ArrayList<Text>>();

		ArrayList<Text> dgfgl = new ArrayList<Text>();
		dgfgl.add(new BabaText(null, 0, 0, 0));
		textLigne.add(dgfgl);
		
		ArrayList<Text> dgfgl2 = new ArrayList<Text>();
		dgfgl2.add(new Is(null, 0, 0, 0));
		textLigne.add(dgfgl2);
		
		ArrayList<Text> dgfgl3 = new ArrayList<Text>();
		dgfgl3.add(new WallText(null, 0, 0, 0));
		dgfgl3.add(new KekeText(null, 0, 0, 0));
		textLigne.add(dgfgl3);
		
		ArrayList<Text> dgfgl4 = new ArrayList<Text>();
		dgfgl4.add(new Is(null, 0, 0, 0));
		textLigne.add(dgfgl4);
		
		ArrayList<Text> dgfgl5 = new ArrayList<Text>();
		dgfgl5.add(new BabaText(null, 0, 0, 0));
		textLigne.add(dgfgl5);
		
		ArrayList<Text> dgfgl56 = new ArrayList<Text>();
		dgfgl56.add(new And(null, 0, 0, 0));
		textLigne.add(dgfgl56);
		
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
			
			currentRules = new RuleStackList(rules);
			for (ArrayList<Text> textList : textLigne) {
				
				System.out.print("            READING : ");
				for (Text text : textList) {
					text.show();
					System.out.print("    ");
				}
				System.out.println();
				
				currentRules.buildNext(textList, thereIsAnOnOrNearOrFacingOrAnd);
				if (!thereIsANot(textList))
					thereIsAnOnOrNearOrFacingOrAnd = thereIsAOn(textList) ||thereIsAAnd(textList);
				System.out.println("           show stacks :");
				currentRules.show();
				System.out.println("-------------------------------");
				System.out.println(currentRules.toString());
				System.out.print("Current States : ");
				currentRules.currentStates();
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
