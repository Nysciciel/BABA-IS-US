package com.mygdx.game.objects;
import com.mygdx.game.*;
import com.mygdx.game.rule.Logic;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.RuleSet;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.utils.Constants;


public abstract class Item {
	protected float animationChrono=0;
	protected Texture texture;
	protected LogicHashtable ruleTable;

	private boolean propertyAssert(String key) {
		try {
			Logic affirm = (Logic) ruleTable.get("Push").get(getCategory());
			Logic restrict = (Logic) ruleTable.get("Push").get("Not").get(getCategory());
			return affirm.getTruth(this) && !restrict.getTruth(this);
		}
		catch(Exception e) {
			return false;
		}
	}

	public boolean isPush() {
		return propertyAssert("Push");
	}
	public boolean isYou() {
		return propertyAssert("You");
	}
	public boolean isWin() {
		return propertyAssert("Win");
	}
	public boolean isStop() {
		return propertyAssert("Stop");
	}
	public boolean isPull() {
		return propertyAssert("Pull");
	}
	public boolean isSink() {
		return propertyAssert("Sink");
	}
	public boolean isDefeat() {
		return propertyAssert("Defeat");
	}
	public boolean isMove() {
		return propertyAssert("Move");
	}
	public boolean isHot() {
		return propertyAssert("Hot");
	}
	public boolean isMelt() {
		return propertyAssert("Melt");
	}
	public boolean isFloat() {
		return propertyAssert("Float");
	}
	public boolean isShift() {
		return propertyAssert("Shift");
	}
	public boolean isShut() {
		return propertyAssert("Shut");
	}
	public boolean isOpen() {
		return propertyAssert("Open");
	}
	
	private ArrayList<Class> hasToTranformTo(){
		
		
		
		return null;		
	}

	protected int x;
	protected int y;
	protected int orientation;
	protected Location loc;

	protected boolean hasMoved = false;
	protected boolean hasShifted = false;

	public Item(Location loc, LogicHashtable ruleTable,
			int x, int y, int orientation) {
		this.loc = loc;
		this.x = x;
		this.y = y;
		this.orientation= orientation;
		this.ruleTable = ruleTable;

		update();
	}

	public Texture getTexture() {
		return this.texture;
	}

	public Vector2 getPosition() {
		return new Vector2(x,y);
	}

	public int getOrientation() {
		return orientation;
	}

	public void dispose() {
		texture.dispose();
	}

	public void update() {
		try {
			this.texture = new Texture(getClass().getSimpleName() + Integer.toString(orientation)+".png");
		}
		catch(Exception e) {
			this.texture = new Texture("Baba" + Integer.toString(orientation)+".png");
		}
	}

	public void goforward() {

		animationChrono = 0;
		loc.del(this);

		if (loc.next((orientation+2)%4)!=null) {

			ArrayList<Item> res = loc.next((orientation+2)%4).getPulled(orientation);
			if (res!=null) {
				for(Item j:res) {
					j.goforward();
				}
			}
		}

		switch(orientation) {
		case(0):
			x-=1;
		break;
		case(1):
			y+=1;
		break;
		case(2):
			x+=1;
		break;
		case(3):
			y-=1;
		break;
		default:
		}

		loc = loc.next(orientation);
		loc.add(this);
	}

	public void advance() {

		//same as go forward except it doesn't pull the one behind
		//useful for when a push&pull chain is getting pushed
		animationChrono = 0;
		loc.del(this);

		switch(orientation) {
		case(0):
			x-=1;
		break;
		case(1):
			y+=1;
		break;
		case(2):
			x+=1;
		break;
		case(3):
			y-=1;
		break;
		default:
		}

		loc = loc.next(orientation);
		loc.add(this);
	}

	public boolean isempty() {
		return false;
	}

	public boolean isText() {
		return false;
	}

	public void render(SpriteBatch sb){
		int h_ratio = Constants.WINDOW_HEIGHT/(loc.getLevelHeigh());
		int w_ratio = Constants.WINDOW_WIDTH/(loc.getLevelWidth());
		int size = Math.min(h_ratio,w_ratio);
		sb.draw(texture,x*size,y*size,size,size);
	}

	public void orient(int direction) {
		orientation = direction;
	}

	public boolean hasMoved() {
		return hasMoved;
	}

	public void reset() {
		hasMoved = false;
		hasShifted = false;
	}

	public void moved() {
		hasMoved = true;
	}

	public boolean hasShifted() {
		return hasShifted;
	}

	public void shifted() {
		hasShifted = true;
	}

	public Item copy() {
		try {
			return (Item)getClass().getConstructors()[0].newInstance(null,x,y,orientation);
		}
		catch(Exception e) {
			return null;
		}
	}

	public void setLocation(Location loc) {
		this.loc = loc;
	}

	boolean isNeighbourEqual(int orientation){
		Location NeigLoc = loc.next(orientation);
		if(NeigLoc == null){
			return(true);
		}else{
			for (Item i : NeigLoc.getItems()) {
				if (i.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {
					return (true);
				}
			}
		}
		return(false);
	}

	public String getName() {
		return this.getClass().getSimpleName();
	}

	public String getCategory() {
		return getName();
	}

	public boolean isOnLocation(Class item) {
		return loc.isOnLocation(item);
	}

	public boolean isNearLocation(Class item) {
		return loc.isNearLocation(item);
	}

	public boolean isFacingLocation(Class item) {
		if (loc.next(orientation) != null) {
			return loc.next(orientation).isOnLocation(item);
		}
		return false;
	}
}
