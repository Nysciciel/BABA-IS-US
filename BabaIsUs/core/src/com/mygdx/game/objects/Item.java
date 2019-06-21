package com.mygdx.game.objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.*;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;


public abstract class Item {
	protected float animationChrono=0;
	protected TextureAtlas textureAtlas;
	protected Animation animation;
	protected float elapsedTime = 0;

	public boolean isPush() {
		return false;
	}
	public boolean isYou1() {
		return false;
	}
	public boolean isYou2() {
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
	public boolean isWeak() {
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
		loadTextureAtlas();
	}

	public Vector2 getPosition() {
		return new Vector2(x,y);
	}

	public int getOrientation() {
		return orientation;
	}

	public void dispose() {
		textureAtlas.dispose();
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

	public void loadTextureAtlas(){
		try{
			textureAtlas = new TextureAtlas(Gdx.files.internal(this.getClass().getSimpleName()+ "Sheet.txt"));
		}catch(Exception e){
			textureAtlas = new TextureAtlas(Gdx.files.internal("ErrorSheet.txt"));
		}
	}


	public void render(Batch sb){
		String[] spriteUsed = getSpriteUsed();
		int length = spriteUsed.length;
		TextureRegion[] orientedWall = new TextureRegion[length];
		for(int i=0;i<length;i++) {
			orientedWall[i] = textureAtlas.findRegion(spriteUsed[i]);
		}
		animation = new Animation(1/3f, orientedWall);
		elapsedTime += Gdx.graphics.getDeltaTime();
		animationChrono +=Gdx.graphics.getDeltaTime();
		TextureRegion test = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
		int h_ratio = Constants.WINDOW_HEIGHT/(loc.getLevelHeigh());
		int w_ratio = Constants.WINDOW_WIDTH/(loc.getLevelWidth());
		int size = Math.min(h_ratio,w_ratio);
		sb.draw(test,getAffichePos()[0]*size,getAffichePos()[1]*size,size,size);
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[1];
		spriteUsed[0]="Error0";
		return(spriteUsed);
	}

	public float[] getAffichePos(){
		float a,b;
		if(animationChrono<0.2){
			switch(orientation){
				case(0):
					a=x+1-animationChrono*5;
					b=y;
					break;
				case(1):
					a=x;
					b=y-1+animationChrono*5;
					break;
				case(2):
					a=x-1+animationChrono*5;
					b=y;
					break;
				default:
					a=x;
					b=y+1-animationChrono*5;
					break;
			}
		}else {
			a=x;
			b=y;
		}
		float[] tab={a,b};
		return(tab);
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

	public boolean isOn(Class item) {
		return loc.isOn(item);
	}

	public boolean isNear(Class item) {
		return loc.isNear(item);
	}

	public boolean isFacing(Class item) {
		if (loc.next(orientation) != null) {
			return loc.next(orientation).isOn(item);
		}
		return false;
	}
	
	
	
}
