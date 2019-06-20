package com.mygdx.game.objects;
import com.mygdx.game.*;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.utils.Constants;


public abstract class Item {
	protected float animationChrono=0;
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
	public boolean isFloat() {
		return false;
	}
	public boolean isShift() {
		return false;
	}
	public boolean isShut() {
		return false;
	}
	public boolean isOpen() {
		return false;
	}

	protected int x;
	protected int y;
	protected int orientation;
	protected Location loc;

	protected boolean hasMoved = false;
	protected boolean hasShifted = false;

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
