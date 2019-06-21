package com.mygdx.game.objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.graphics.g2d.Batch;


public abstract class Item {

	private boolean propertyAssert(String key) {
		
		Logic affirm;
		Logic restrict;
		
		try {
		affirm = (Logic) getRuleTable().get(key).get(getCategory());
		if (affirm == null)
			return false;
		}
		catch(Exception e) {
			return false;
		}
		
		try{
			restrict = (Logic) getRuleTable().get(key).get("Not").get(getCategory());
			if (restrict == null)
				return affirm.getTruth(this);
		}
		catch(Exception e) {
			return affirm.getTruth(this);
		}
		
		return affirm.getTruth(this) && !restrict.getTruth(this);
	}

	public boolean isPush() {
		return propertyAssert("Push");
	}
	
	public boolean isYou() {
		return propertyAssert("You");
	}

	public boolean isYou1() {
		return propertyAssert("You1");
	}
	public boolean isYou2() {
		return propertyAssert("You2");
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
	
	public boolean isWeak() {
		return propertyAssert("Weak");
	}
	
	private ArrayList<Class> hasToTranformTo(){
		
		ArrayList<Class> transform = new ArrayList<Class>();
		
		if (getRuleTable().get("Is").get(this.getCategory()).get(this.getCategory()) == null)
			return transform;
		
		for (Class c : getRuleTable().getProps()) {
			// TODO :  getSimpleName wrong for Text objects ?
			// TODO : verify order !!!!!!!!
			if (((Logic)(getRuleTable().get("Is").get(c.getSimpleName()).get(this.getCategory()))).getTruth(this)
					&& !((Logic)(getRuleTable().get("Is").get("Not").get(this.getCategory()).get(this.getCategory()))).getTruth(this))
				transform.add(c);
		}
		return null;		
	}
	

	protected int x;
	protected int y;
	protected int orientation;
	protected Location loc;
	protected float animationChrono = 1;
	protected TextureAtlas textureAtlas;
	protected Animation animation;
	protected float elapsedTime = 0;
	protected boolean hasMoved = false;
	protected boolean hasShifted = false;

	public Item(Location loc, LogicHashtable ruleTable,
			int x, int y, int orientation) {
		this.loc = loc;
		this.x = x;
		this.y = y;
		this.orientation= orientation;
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

	public String getRefName() {
		return getName();
	}

	public LogicHashtable getRuleTable() {
		return loc.getLevel().getRuleTable();
	}
}
