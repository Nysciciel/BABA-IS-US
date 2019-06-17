package com.mygdx.game.objects;
import com.mygdx.game.*;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


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
	

	protected int x;
	protected int y;
	protected int orientation;
	protected Location loc;



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

	public void dispose() {
		texture.dispose();
	}

	public void update() {
	}
	
	
	public void goforward() {


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

	public void reset() {
	}
	public void draw(SpriteBatch sb) {
		sb.draw(texture, x*32, y*32);
	}
	
	public void orient(int direction) {
		orientation = direction;
	}
	
	
}
