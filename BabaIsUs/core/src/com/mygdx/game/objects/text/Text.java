package com.mygdx.game.objects.text;

import com.mygdx.game.*;
import com.mygdx.game.objects.*;
import com.mygdx.game.rule.Logic;

public abstract class Text extends Item {

	public Text(Location loc, int orientation) {
		super(loc, orientation);
		// TODO Auto-generated constructor stub
	}

	protected String label;
	protected Class refClass;

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
		// TODO Highlight the text by changing the texture by a brighter one if b is true		
	};
}
