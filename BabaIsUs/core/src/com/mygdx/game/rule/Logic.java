package com.mygdx.game.rule;

import java.util.ArrayList;
import com.mygdx.game.objects.Item;

public class Logic extends LogicHashtable {
	
	private boolean value;
	private ArrayList<Class> on;
	private ArrayList<Class> near;
	private ArrayList<Class> facing;
	private ArrayList<Class> onNot;
	private ArrayList<Class> nearNot;
	private ArrayList<Class> facingNot;
	private ArrayList<Class> props;
	
	public Logic(ArrayList<Class> on2, ArrayList<Class> near2,
			ArrayList<Class> facing2, ArrayList<Class> onNot2, ArrayList<Class> nearNot2, ArrayList<Class> facingNot2) {
		value = true;
		on = on2;
		near = near2;
		facing = facing2;
		onNot = onNot2;
		nearNot = nearNot2;
		facing = facing2;		
		System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
	}

	public boolean getTruth(Item item) {
		System.out.println("value = "+value);
		return value && onTruth(item) && nearTruth(item) && facingTruth(item);
	}
	
	public boolean onTruth(Item item) {
			
		boolean b = true;
		
		for (Class c : on) {
			b &= item.isOnLocation(c);
			if (!b)
				return false;
		}
		for (Class c : onNot) {
			b &= !item.isOnLocation(c);
			if (!b)
				return false;
		}
		
		return true;
	}
	
	public boolean nearTruth(Item item) {
		
		boolean b = true;
		
		for (Class c : near) {
			b &= item.isOnLocation(c);
			if (!b)
				return false;
		}
		for (Class c : nearNot) {
			b &= !item.isOnLocation(c);
			if (!b)
				return false;
		}
		
		return true;
	}
	
	public boolean facingTruth(Item item) {
		

		boolean b = true;
		
		for (Class c : near) {
			b &= item.isFacingLocation(c);
			if (!b)
				return false;
		}
		for (Class c : nearNot) {
			b &= !item.isFacingLocation(c);
			if (!b)
				return false;
		}
		
		return true;
	}

	public String toString() {
		return ""+value;
	}
}
