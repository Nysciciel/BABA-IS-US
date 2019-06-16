package com.mygdx.game.objects;

import java.util.ArrayList;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Level;





public abstract class Item {

	protected Texture texture;

	protected boolean isPush;
	protected boolean isYou;
	protected boolean isWin;
	protected boolean isStop;

	protected boolean defaultisPush = false;
	protected boolean defaultisYou = false;
	protected boolean defaultisWin = false;
	protected boolean defaultisStop = false;

	protected int x;
	protected int y;
	protected int orientation;
	protected Level lvl;

	protected boolean hasmoved = false;


	public Item(Level lvl,
			int x, int y, int orientation) {
		this.lvl = lvl;
		this.x = x;
		this.y = y;
		this.orientation= orientation;

		isPush = defaultisPush;
		isYou = defaultisYou;
		isWin = defaultisWin;
		isStop = defaultisStop;

		update();
	}

	public Texture getTexture() {
		return this.texture;
	}

	public Vector2 getPosition() {
		return new Vector2(x,y);
	}

	public void dispose() {
		texture.dispose();
	}

	public void update() {
	}


	public boolean move() {
		if(isPush) {
			if (this.facinglocation()!=null) {
				ArrayList<Item> target = this.facinglocation();
				boolean possible = true;
				for (int index = 0; index < target.size(); index++) {
					if (!target.get(index).move(orientation)) {
						possible = false;
					}
				}
				if (possible){
					this.goforward();
					return true;
				}
				else {
					return false;
				}
			}
			else {
				return false;
			}
		}
		if (isStop) {
			return false;
		}
		return true;
	}


	public void moveAsyou(int orientation) {
		this.orientation = orientation;
		this.moveAsyou();
	}

	public void moveAsyou() {
		if (!hasmoved) {
			if (this.facinglocation()!=null) {
				ArrayList<Item> target = this.facinglocation();
				boolean possible = true;
				for (int index = 0; index < target.size(); index++) {
					if (!target.get(index).move(orientation)) {
						possible = false;
					}
				}
				if (possible){
					this.goforward();
					hasmoved = true;
				}
			}
		}
	}


	public boolean move(int orientation) {
		int previous_orientation = this.orientation;
		this.orientation = orientation;
		boolean result = this.move();
		if (!result) {
			this.orientation = previous_orientation;
		}

		return result;
	}

	public void goforward() {


		lvl.del(this,x,y);

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

		lvl.add(this,x,y);
		lvl.checkempty(x, y);
	}

	public ArrayList<Item> facinglocation() {
		try {
			switch(orientation) {
			case(0):
				return lvl.getlocation(x-1,y);
			case(1):
				return lvl.getlocation(x,y+1);
			case(2):
				return lvl.getlocation(x+1,y);
			case(3):
				return lvl.getlocation(x,y-1);
			default:
				return null;
			}
		}
		catch(Exception e) {
			return null;
		}
	}
	public boolean isempty() {
		return false;
	}

	public boolean isyou() {
		return isYou;
	}

	public void reset() {
		hasmoved = false;
	}
}
