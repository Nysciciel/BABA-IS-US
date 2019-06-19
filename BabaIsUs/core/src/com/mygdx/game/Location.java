package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.objects.*;

public class Location {
	protected Location[][] locationMatrix;
	protected ArrayList<Item> items;
	protected int x;
	protected int y;
	
	
	

	public Location(ArrayList<Item> items, Location[][] locationMatrix, int x, int y) {
		this.items = items;
		this.locationMatrix = locationMatrix;
		this.x = x;
		this.y = y;
	}

	public int getLevelHeigh(){
		return(locationMatrix.length);
	}

	public int getLevelWidth(){
		return(locationMatrix[0].length);
	}

	public Location next(int direction) {
		try {
			switch(direction) {

			case(0):
				return locationMatrix[y][x-1];
			case(1):
				return locationMatrix[y+1][x];
			case(2):
				return locationMatrix[y][x+1];
			case(3):
				return locationMatrix[y-1][x];
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
			if (i.isStop() || i.isPull()) {
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
						i.goforward();
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
		item.dispose();
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

	public ArrayList<Item> moveYou(int direction) {
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

	public void reset() {
		for(Item i:items) {
			i.reset();
		}
	}
}


