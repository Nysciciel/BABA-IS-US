package com.mygdx.game.objects;
import com.mygdx.game.*;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.utils.Constants;


public abstract class Item {

	protected Texture texture;

	public boolean isPush() {
		return false;
	}
	public boolean isYou() {
		return false;
	}
	public boolean isWin() {
		return false;
	}
	public boolean isStop() {
		return false;
	}
	public boolean isPull() {
		return false;
	}
	public boolean isSink() {
		return false;
	}
	public boolean isDefeat() {
		return false;
	}
	public boolean isMove() {
		return false;
	}
	public boolean isHot() {
		return false;
	}
	public boolean isMelt() {
		return false;
	}


	protected int x;
	protected int y;
	protected int orientation;
	protected Location loc;

	protected boolean hasmoved = false;



	public Item(Location loc,
			int x, int y, int orientation) {
		this.loc = loc;
		this.x = x;
		this.y = y;
		this.orientation= orientation;

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
		this.texture = new Texture(getClass().getSimpleName() + Integer.toString(orientation)+".png");
	}

	public void goforward() {


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
	public abstract void render(SpriteBatch sb);
	
	public void orient(int direction) {
		orientation = direction;
	}

	public boolean hasmoved() {
		return hasmoved;
	}

	public void reset() {
		hasmoved = false;
	}

	public void moved() {
		hasmoved = true;
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

}
