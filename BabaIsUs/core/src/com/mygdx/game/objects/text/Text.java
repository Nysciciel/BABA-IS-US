package com.mygdx.game.objects.text;

import com.mygdx.game.*;
import com.mygdx.game.objects.*;
import com.mygdx.game.rule.Logic;

public abstract class Text extends Item {

	public Text(Location loc, int orientation) {
		super(loc, orientation);
		highlight = "0";
		prio=4;
	}

	protected String label;
	private String highlight;
		
	public String getSpriteID(int i) {
		
		return this.getName()+highlight+"-"+i;
	}
	
	public String getSpriteIDNoH(int i) {
				
		return this.getName()+i;
	}
	
	public boolean isOperator() {
		return false;
	}
	
	public boolean isProperty() {
		return false;
	}
	
	@Override
	public boolean isText() {
		return true;
	}


	public boolean isNot() {
		return false;
	}

	public boolean isOn() {
		return false;
	}

	public boolean isIs() {
		return false;
	}

	@Override
	public boolean isPush() {
		
		try {
			Logic restrict = ((Logic)getRuleTable().get("Push").get("Not").get(getCategory()));	
			if (restrict == null)
				return true;
			return restrict.getTruth(this);
			
		}
		catch(Exception e) {
			return true;
		}
		
	}

	
	public boolean isItemRef() {
		return false;
	}
	
	public boolean isRelation() {
		return false;
	}

	public boolean isAnd() {
		return false;
	}
	
	@Override
	public String toString() {
		
		return this.getName();
	}
	
	@Override
	public String getCategory() {
		return "Text";
	}
	
	public void show() {
		System.out.print(toString());
	}

	public Class getRefClass() {
		return null;
	}

	public void highLight(boolean b) {
		if (b)
			highlight = "1";
		else
			highlight = "0";
	};
}
