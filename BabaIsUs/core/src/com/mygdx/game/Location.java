package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.objects.*;

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


	public boolean hasYou() {
		for(Item i:items) {
			if (i.isYou()) {
				return true;
			}
		}
		return false;
	}


	public void orientYou(int direction) {
		for(Item i:items) {
			if (i.isYou()) {
				i.orient(direction);
			}
		}
	}


	public boolean pleaseCanIGo(int direction) {

		ArrayList<Item> pushable = new ArrayList<Item>();
		for(Item i:items) {
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
						i.advance();
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}


	public void del(Item item) {
		items.remove(item);
		if (items.size()==0) {
			this.add(new Empty(this, x, y, 0));
		}
	}


	public void add(Item item) {
		for(Item i:items) {
			if (i.isempty()) {
				this.del(i);
			}
		}
		items.add(item);
	}


	public ArrayList<Item> move(int direction) {
		ArrayList<Item> yous = new ArrayList<Item>();
		for(Item i:items) {
			if (i.isYou()) {
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


	public void update() {
		for(Item i:items) {
			i.update();
		}
	}


	public void dispose() {
		for(Item i:items) {
			i.dispose();
		}
	}

	public void render(SpriteBatch sb) {
		for(Item i:items) {
			i.render(sb);
		}
	}


	public void checkDeaths() {

		ArrayList<Item> toKill = new ArrayList<Item>();

		for(Item i:items) {

			if(i.isDefeat()) {
				for(Item j:items) {
					if(j.isYou()) {
						toKill.add(j);
					}
				}
			}


			if(i.isSink() && items.size()>1 ) {
				ArrayList<Item> items = new ArrayList<Item>();
				items.add(new Empty(this,x,y,0));
				this.items = items;
			}

			if(i.isHot() ) {
				for(Item j:items) {
					if(j.isMelt()) {
						toKill.add(j);
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

		for(Item i:items) {

			if(i.isMove() && !i.hasmoved()) {
				if(next(i.getOrientation())==null || !(next(i.getOrientation()).pleaseCanIGo(i.getOrientation()))){
					i.orient((i.getOrientation()+2)%4);
				}
				toMove.add(i);
			}

		}
		for(Item i:toMove) {
			if(next(i.getOrientation()).pleaseCanIGo(i.getOrientation())) {
				i.goforward();
				i.moved();
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

}


