package com.mygdx.game;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.history.Born;
import com.mygdx.game.history.Change;
import com.mygdx.game.history.Death;
import com.mygdx.game.history.HistoryStack;
import com.mygdx.game.history.TurnStack;
import com.mygdx.game.objects.*;
import com.mygdx.game.objects.text.Text;

/**
 * Object in a grid of the level containing a list of Items
 * It is mostly a box
 * @author Maxwell
 *
 */
public class Location {
	protected Level lvl;
	protected ArrayList<Item> items;
	protected int x;
	protected int y;

	public Location(ArrayList<Item> items, Level lvl, int x, int y) {
		this.items = items;
		this.lvl = lvl;
		this.x = x;
		this.y = y;
	}

	public ArrayList<Item> getItems(){
		return(items);
	}

	public int getLevelHeigh(){
		return(lvl.getLocationMatrix().length);
	}

	public int getLevelWidth(){
		return(lvl.getLocationMatrix()[0].length);
	}

	public Location next(int direction) {
		try {
			switch(direction) {

			case(0):
				return lvl.getLocationMatrix()[y][x-1];
			case(1):
				return lvl.getLocationMatrix()[y+1][x];
			case(2):
				return lvl.getLocationMatrix()[y][x+1];
			case(3):
				return lvl.getLocationMatrix()[y-1][x];
			default:
				return null;
			}
		}
		catch(Exception e) {
			return null;
		}
	}

	public boolean hasYou1() {
		for(Item i:items) {
			if (i.isYou1()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasYou2() {
		for(Item i:items) {
			if (i.isYou2()) {
				return true;
			}
		}
		return false;
	}

	public void orientYou(int direction) {
		for(Item i:items) {
			if (i.isYou1() || i.isYou2()) {
				i.orient(direction);
			}
		}
	}

	public ArrayList<Text> giveTextItems(){

		ArrayList<Text> textList = new ArrayList<Text>();

		for (Item item : items) {
			if (item instanceof Text)
				textList.add((Text) item);
		}
		return textList;
	}

	public boolean thereIsAOn() {

		for (Item item : items) {
			if (item instanceof Text)
				if (((Text)item).isOn()) 
					return true;
		}
		return false;
	}

	/**
	 * Ask the next location in the 
	 * @param direction
	 * whether an object of this can move to the next location and move the interactive objects of the next location if necessary
	 * @return the permission
	 */
	public boolean pleaseCanIGo(int direction) {

		if (this.allAreShut() && this.next((2+direction)%4).allAreOpen()) {
			return true;
		}

		ArrayList<Item> pushable = new ArrayList<Item>();
		for(Item i:items) {
			if (((i.isStop()&&!i.isPush()) || (i.isPull() && !(i.isPush()))) && next((2+direction)%4).allAreWeak()) {
				return true;
			}
			if ((i.isStop()&&!i.isPush()) || (i.isPull() && !(i.isPush()))) {
				return false;
			}
			if (i.isPush()) {
				pushable.add(i);
			}
		}
		if (pushable.size()>0) {
			if (next(direction)!=null) {
				if (next(direction).pleaseCanIGo(direction)) {
					for(Item i:pushable) {
						i.orient(direction);
						// TODO : kill
						i.advance();
						// TODO : born
						
					}
					return true;
				}
			}
			return false;
		}
		return true;
	}

	public void del(Item item) {
		items.remove(item);
		if (items.isEmpty()) {
			this.add(new Empty(this, 0));
		}
	}

	public void add(Item item) {
		ArrayList<Item> toRemove = new ArrayList<Item>();
		for(Item i:items) {
			if (i.isEmpty()) {
				toRemove.add(i);
			}
		}
		items.removeAll(toRemove);
		if(!item.isEmpty() || items.isEmpty()) {
			items.add(item);
		}
	}
	
	public ArrayList<Item> move1(int direction) {
		ArrayList<Item> yous = new ArrayList<Item>();
		for(Item i:items) {
			if (i.isYou1()) {
				yous.add(i);
				i.orient(direction);
			}
		}
		if (yous.size()>0) {
			if (next(direction)!=null) {
				if (next(direction).pleaseCanIGo(direction)) {
					return yous;
				}
			}
		}
		return null;
	}

	public ArrayList<Item> move2(int direction) {
		ArrayList<Item> yous = new ArrayList<Item>();
		for(Item i:items) {
			if (i.isYou2()) {
				yous.add(i);
				i.orient(direction);
			}
		}
		if (yous.size()>0) {
			if (next(direction)!=null) {
				if (next(direction).pleaseCanIGo(direction)) {
					return yous;
				}
			}
		}
		return null;
	}

	public ArrayList<Item> getPulled(int direction) {
		ArrayList<Item> pulls = new ArrayList<Item>();
		for(Item i:items) {
			if (i.isPull()) {
				pulls.add(i);
				i.orient(direction);
			}
		}
		if (pulls.size()>0) {
			if (next(direction)!=null) {
				if (next(direction).pleaseCanIGo(direction)) {
					return pulls;
				}
			}
		}
		return null;
	}

	public void dispose() {
		for(Item i:items) {
			i.dispose();
		}
	}


	public void render(Batch sb, float cellSize,int prio) {
		for(Item i:items) {
			if((i.getPrio()+4*(i.getIntFloat()))==prio) {
				i.render(sb, cellSize);
			}
		}
	}

	public void checkDeaths() {

		this.checkLocks();

		ArrayList<Item> toKill = new ArrayList<Item>();

		for(Item i:items) {

			if(i.isDefeat()) {
				boolean isFloat = i.isFloat();
				for(Item j:items) {
					if((j.isYou1() || j.isYou2()) && (isFloat == j.isFloat())) {
						if(toKill.indexOf(j)==-1) {
							toKill.add(j);
						}
					}
				}
			}


			if(i.isSink() && items.size()>1 ) {
				boolean isFloat = i.isFloat();
				for(Item j:items) {
					if((isFloat == j.isFloat())) {
						if(toKill.indexOf(j)==-1) {
							toKill.add(j);
						}
					}
				}
			}

			if(i.isHot()) {
				boolean isFloat = i.isFloat();
				for(Item j:items) {
					if(j.isMelt() && (isFloat == j.isFloat())) {
						if(toKill.indexOf(j)==-1) {
							toKill.add(j);
						}
					}
				}
			}
			if(i.isWeak() && this.items.size()>1) {
				toKill.add(i);
			}
		}
		for(Item i:toKill) {
			// TODO : kill
			addChange(new Death(i, this, i.getOrientation()));
			this.del(i);
		}
	}

	public void checkLocks() {
		ArrayList<Item> toKill = new ArrayList<Item>();
		for(Item i:items) {
			if(i.isShut()) {
				for(Item j:items) {
					if(j.isOpen()) {
						if(toKill.indexOf(j)==-1) {
							toKill.add(j);
						}
						if(toKill.indexOf(i)==-1) {
							toKill.add(i);
						}
					}
				}
			}
		}
		for(Item i:toKill) {
			this.del(i);
		}
	}

	public void checkMove() {

		ArrayList<Item> toMove = new ArrayList<Item>();
		ArrayList<Item> toShift = new ArrayList<Item>();

		for(Item i:items) {

			if(i.isShift()) {
				boolean isFloat = i.isFloat();
				for(Item j:items) {
					if((isFloat == j.isFloat()) && !i.hasShifted() && i!=j) {
						toMove.add(j);
						j.orient(i.getOrientation());
					}
				}
			}

		}
		for(Item i:items) {

			if(i.isMove() && !i.hasMoved()) {
				if(next(i.getOrientation())==null || !(next(i.getOrientation()).pleaseCanIGo(i.getOrientation()))){
					i.orient((i.getOrientation()+2)%4);
				}
				toMove.add(i);
			}

		}
		for(Item i:toShift) {
			if(next(i.getOrientation()).pleaseCanIGo(i.getOrientation())) {
				i.goforward();
				i.shifted();
			}
		}
		for(Item i:toMove) {
			if(next(i.getOrientation())!=null) {
				if(next(i.getOrientation()).pleaseCanIGo(i.getOrientation())) {
					i.goforward();
					i.moved();
				}
			}
		}

	}

	public void checkWin() {

		for(Item i:items) {

			if(i.isWin()) {
				boolean isFloat = i.isFloat();
				for(Item j:items) {
					if((j.isYou1() || j.isYou2()) && (isFloat == j.isFloat())) {
						
						lvl.win();
					}
				}
			}
		}

	}

	public void reset() {
		for(Item j:items) {
			j.reset();
		}
	}

	public Location copy() {

		ArrayList<Item> items = new ArrayList<Item>();
		for(int x=0; x < this.items.size(); x++) {
			items.add(this.items.get(x).copy());
		}
		try {
			Location loc = (Location)getClass().getConstructors()[0].newInstance(items,lvl,x,y);
			loc.setLocation();
			return loc;
		}
		catch(Exception e) {
			return null;
		}
	}

	public void setLocation() {
		for(Item i:items) {
			i.setLocation(this);
		}
	}

	public boolean allAreOpen() {
		for(Item i:items) {
			if(!(i.isOpen())) {
				return false;
			}
		}
		return true;
	}

	public boolean allAreWeak() {
		for(Item i:items) {
			if(!(i.isWeak())) {
				return false;
			}
		}
		return true;
	}


	public boolean thereIsAAnd() {
		for (Item item : items) {
			if (item instanceof Text)
				if (((Text)item).isAnd()) 
					return true;
		}
		return false;
	}

	public boolean thereIsANot() {
		for (Item item : items) {
			if (item instanceof Text)
				if (((Text)item).isNot()) 
					return true;
		}
		return false;
	}

	/**
	 * Transform all the items that have a setting like "BABA IS KEKE" in the new item(s)
	 */
	public void transform() {
		
		ArrayList<Item> newItems = new ArrayList<Item>();
		ArrayList<Class> afterTransform;
		
		for (Item item : items) {
			afterTransform = item.hasToTransformTo();
			if (afterTransform.isEmpty())
				newItems.add(item);
			else {
				// TODO : kill
				addChange(new Death(item, this, item.getOrientation()));
				
				for (Class c : afterTransform) {
					try {
						Item newItem = (Item)c.getConstructors()[0].newInstance(this,item.getOrientation());
						newItems.add(newItem);
						// TODO : born
						addChange(new Born(newItem, this));
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				 
			}
		}
		items = newItems;
	}


	public boolean allAreShut() {
		for(Item i:items) {
			if(!(i.isShut())) {
				return false;
			}
		}
		return true;
	}

	public boolean isOnLocation(Class<Item> item) {
		for(Item i:items) {
			if(item.isInstance(i)) {
				return true;
			}
		}
		return false;
	}

	public boolean isNearLocation(Class<Item> item) {

		for(int i=0; i<4; i++) {
			if (this.next(i) != null && this.next(i).isOnLocation(item)) {
				return true;
			}
		}
		return false;
	}	

	public Level getLevel() {
		return lvl;
	}
	
	public TurnStack getTurnStack() {
		return lvl.getTurnStack();
	}
	
	private void addChange(Change change) {
		getTurnStack().push(change);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}



