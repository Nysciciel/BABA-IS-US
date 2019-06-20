package com.mygdx.game.rule;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import com.mygdx.game.objects.text.Text;

public class LogicHashtable extends Hashtable<String, LogicHashtable> {

	public LogicHashtable(RuleSet rules, ArrayList<Class> props) {
		
		for (Rule rule : rules) {
									
			int length = rule.getTextList().size();
			ArrayList<ArrayList<String>> keys = new ArrayList<ArrayList<String>>();
			// case Property
			if (rule.getTextList().getLast().isProperty()) {
				// add last
				ArrayList<String> keyList = new ArrayList<String>();
				keyList.add(rule.getTextList().getLast().getName());
				keys.add(keyList);
				// case Property restriction
				if (rule.getTextList().get(length-2).isNot()) { // -1 ?
					// add NOT
					ArrayList<String> keyList1 = new ArrayList<String>();
					keyList1.add("Not");
					keys.add(keyList1);
					
				}
				if (rule.getTextList().getFirst().isNot()){
					// add complementary of 2nd element
					ArrayList<String> complementaryKeyList = new ArrayList<String>();
					for (Class c : props) {
						if (!c.getSimpleName().equals(rule.getTextList().get(1).getName()))
							complementaryKeyList.add(c.getSimpleName());
					}
					keys.add(complementaryKeyList);
				}
				else {
					// add first 
					ArrayList<String> keyList1 = new ArrayList<String>();
					keyList1.add(rule.getTextList().getFirst().getName());
					keys.add(keyList1);
				}
			}
			// case ItemRef at the end
			else {
				// case Setting (change of nature)
				if (rule.is()) {
					// add relation
					ArrayList<String> keyList = new ArrayList<String>();
					keyList.add(rule.getRelation().getName());
					keys.add(keyList);
					// add last item
					ArrayList<String> keyList2 = new ArrayList<String>();
					keyList2.add(rule.getTextList().getLast().getName());
					keys.add(keyList);
					// case restriction by opposition
					if (rule.getTextList().get(length-2).isNot()) { // -1 ?
						// add NOT
						ArrayList<String> keyList1 = new ArrayList<String>();
						keyList1.add("Not");
						keys.add(keyList1);
					}
					if (rule.getTextList().getFirst().isNot()){
						/// add complementary of 2nd element
						ArrayList<String> complementaryKeyList = new ArrayList<String>();
						for (Class c : props) {
							if (!c.getSimpleName().equals(rule.getTextList().get(1).getName()))
								complementaryKeyList.add(c.getSimpleName());
						}
						keys.add(complementaryKeyList);
					}
					else {
						// add first
						ArrayList<String> keyList1 = new ArrayList<String>();
						keyList1.add(rule.getTextList().getFirst().getName());
						keys.add(keyList1);
					}
				}
				// case Has/Make
				else {
					// add relation
					ArrayList<String> keyList = new ArrayList<String>();
					keyList.add(rule.getRelation().getName());
					keys.add(keyList);
					if (rule.getTextList().get(length-2).isNot()) {
						// add complementary of the final - 1
						ArrayList<String> complementaryKeyList = new ArrayList<String>();
						for (Class c : props) {
							if (!c.getSimpleName().equals(rule.getTextList().get(length-2).getName()))
								complementaryKeyList.add(c.getSimpleName());
						}
						keys.add(complementaryKeyList);
					}
					else {
						// add last
						ArrayList<String> keyList2 = new ArrayList<String>();
						keyList2.add(rule.getTextList().getLast().getName());
						keys.add(keyList);
					}
					if (rule.getTextList().getFirst().isNot()){
						// add complementary of 2nd element
						ArrayList<String> complementaryKeyList = new ArrayList<String>();
						for (Class c : props) {
							if (!c.getSimpleName().equals(rule.getTextList().get(1).getName()))
								complementaryKeyList.add(c.getSimpleName());
						}
						keys.add(complementaryKeyList);
					}
					else {
						// add first
						ArrayList<String> keyList1 = new ArrayList<String>();
						keyList1.add(rule.getTextList().getFirst().getName());
						keys.add(keyList1);
					}
				}
			}
			
			// TODO : ON/NEAR/FACING
			ArrayList<Class> on = new ArrayList<Class>();
			ArrayList<Class> near = new ArrayList<Class>();
			ArrayList<Class> facing = new ArrayList<Class>();
			ArrayList<Class> onNot = new ArrayList<Class>();
			ArrayList<Class> nearNot = new ArrayList<Class>();
			ArrayList<Class> facingNot = new ArrayList<Class>();
			
			LinkedList<Text> textList = rule.getTextList();
			for (int i = 2; i < length-2; i++) {
				
				if (textList.get(i).getName().equals("On")) {
					if (textList.get(i).isNot()) {
						onNot.add(textList.get(i+1).getRefClass());
					}
					on.add(textList.get(i).getRefClass());
				}
				
				if (textList.get(i).getName().equals("Near")) {
					if (textList.get(i).isNot()) {
						nearNot.add(textList.get(i+1).getRefClass());
					}
					near.add(textList.get(i).getRefClass());
				}
				
				if (textList.get(i).getName().equals("Facing")) {
					if (textList.get(i).isNot()) {
						facingNot.add(textList.get(i+1).getRefClass());
					}
					facing.add(textList.get(i).getRefClass());
				}
			}
			

			for (String key : keys.get(0)) {
				this.put(key, new LogicHashtable(keys.subList(1, keys.size()), on, near, facing, onNot, nearNot, facingNot));
			}
		
		
		}
	}
	
	public LogicHashtable() {
		super();
	}

	public LogicHashtable(List<ArrayList<String>> keys, ArrayList<Class> on, ArrayList<Class> near,
			ArrayList<Class> facing, ArrayList<Class> onNot, ArrayList<Class> nearNot, ArrayList<Class> facingNot) {

		if (keys.size()>1)
			for (String key : keys.get(0)) {
				this.put(key, new LogicHashtable(keys.subList(1, keys.size()), on, near, facing, onNot, nearNot, facingNot));
			}
		else {
			for (String key : keys.get(0)) {
				this.put(key, new Logic(on, near, facing, onNot, nearNot, facingNot));
			}
		}

	}

	

}