package com.mygdx.game.rule;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.mygdx.game.Location;
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
	}

	public boolean getTruth(Location loc) {
		
		// TODO
		return value;
	}
	
	public boolean onTruth(Location loc) {
			
		// TODO
		return;
	}
	
	public boolean nearTruth(Location loc) {
		
		// TODO
		return true;
	}
	
	public boolean facingTruth(Location loc) {
		
		// TODO
		return true;
	}

}
