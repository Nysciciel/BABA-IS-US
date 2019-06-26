package com.mygdx.game.objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.*;
import com.mygdx.game.objects.text.Text;
import com.mygdx.game.rule.Logic;
import com.mygdx.game.rule.LogicHashtable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Stack;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.utils.Constants;
import com.badlogic.gdx.graphics.g2d.Batch;


/**
 *
 * @author Maxwell
 *
 * Item is the abstract class defining all the items possible.
 *
 * It is mainly characterized by its location that contains it and its orientation.
 *
 */
public abstract class Item {

	protected float animationChrono=1;
	protected TextureAtlas textureAtlas;
	protected Animation animation;
	protected float elapsedTime = 0;
	protected Texture texture;
	protected int orientation;
	protected Location loc;
	protected int prio;

	protected boolean hasMoved = false;
	protected boolean hasShifted = false;
	protected boolean isTextureOriented = false;

	public Item(Location loc, int orientation) {
		this.loc = loc;
		this.orientation= orientation;

		loadTextureAtlas();
	}

	/**
	 * Ask the truth table whether the property with the label key characterize this object
	 * @param key : property key of the Hashtable
	 * @return boolean
	 */
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

	public int getPrio(){
		return(prio);
	}

	public void setPrio(int p){
		prio=p;
	}

	public int getIntFloat(){
		if(this.isFloat()) {
			return (1);
		}
		return(0);
	}

	public boolean isPush() {
		return propertyAssert("Push");
	}
	public boolean isYou1() {
		return propertyAssert("You");
	}
	public boolean isYou2() {
		return propertyAssert("You2");
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
	public boolean isWeak() {
		return propertyAssert("Weak");
	}

	/**
	 *
	 * @return the ArrayList<Class> of the transformations due to setting logics
	 */
	public ArrayList<Class> hasToTransformTo(){

		ArrayList<Class> transform = new ArrayList<Class>();
		try {
			if (((Logic)getRuleTable().get("Is").get(this.getCategory()).get(this.getCategory())).getTruth(this))
				return transform;
		}
		catch(Exception e) {

		}
		
		for (Class c : getRuleTable().getProps()) {
			// TODO :  getSimpleName wrong for Text objects ?
			// TODO : verify order !!!!!!!!
			Logic affirm;
			Logic restrict;
			boolean affirmBoolean;
			boolean restrictBoolean;

			try {
				affirm = (Logic)(getRuleTable().get("Is").get(c.getSimpleName()).get(this.getCategory()));
				if (affirm == null)
					affirmBoolean = false;
				else
					affirmBoolean = affirm.getTruth(this);
			}
			catch(Exception e) {
				affirmBoolean = false;
			}
			try {
				restrict = (Logic)(getRuleTable().get("Is").get("Not").get(this.getCategory()).get(this.getCategory()));
				if (restrict == null)
					restrictBoolean = false;
				else
					restrictBoolean = restrict.getTruth(this);
			}
			catch(Exception e) {
				restrictBoolean = false;
			}

			if (affirmBoolean && !restrictBoolean)
				transform.add(c);
		}
		return transform;
	}



	public int getOrientation() {
		return orientation;
	}

	public void dispose() {
		textureAtlas.dispose();
	}

	/**
	 * move of the item toward the orientation case using an animation
	 */
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

		loc = loc.next(orientation);
		loc.add(this);
	}

	/**
	 * same as go forward except it doesn't pull the one behind
	 * useful for when a push&pull chain is getting pushed
	 */
	public void advance() {

		animationChrono = 0;
		loc.del(this);
		loc = loc.next(orientation);
		loc.add(this);
	}

	public boolean isEmpty() {
		return false;
	}

	public boolean isText() {
		return false;
	}

	/**
	 * Loading the Atlas texture to set the parameter textureAtlas
	 * Use the name of the current class to choose it and set a default texture if not found
	 */
	public void loadTextureAtlas() {
		try{
			textureAtlas = new TextureAtlas(Gdx.files.internal(this.getName()+ "Sheet.txt"));
		}catch(Exception e){
			textureAtlas = new TextureAtlas(Gdx.files.internal("ErrorSheet.txt"));
		}
	}

	/**
	 *  Draw the animated texture
	 * @param sb Sprite bash that has to be drawn
	 */
	public void render(Batch sb, float cellSize){

		Object[] spriteChosen = spriteChosen();
		// C'est en r�alit� une TextureRegion[]
		int length = spriteChosen.length;

		animation = new Animation(2f/(3f*(float)length), spriteChosen);
		elapsedTime += Gdx.graphics.getDeltaTime();
		animationChrono +=Gdx.graphics.getDeltaTime();
		TextureRegion test = (TextureRegion) animation.getKeyFrame(elapsedTime, true);
		sb.draw(test,getAffichePos()[0]*cellSize,getAffichePos()[1]*cellSize,cellSize,cellSize);
	}

	public Object[] spriteChosen() {
		
		Stack<TextureRegion> spriteChoosing = new Stack<TextureRegion>();
		
		for(int i=0;i<3;i++) {
			spriteChoosing.push(textureAtlas.findRegion(getSpriteID(i)));
			
			if (spriteChoosing.peek() == null && this.isText()) {
				spriteChoosing.pop();
				spriteChoosing.push(textureAtlas.findRegion(((Text)this).getSpriteIDNoH(i)));
			}
			if (spriteChoosing.peek() == null) {
				spriteChoosing.pop();
				//System.out.println(this.getSpriteID(i));
				if (i == 0) {
					spriteChoosing.push((new TextureAtlas(Gdx.files.internal("ErrorSheet.txt"))).findRegion("Error0"));
				}
				break;
			}
		}
		return spriteChoosing.toArray();
	}
	
	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[1];
		spriteUsed[0]="Error0";
		return(spriteUsed);
	}
	
	public String getSpriteID(int i) {
		if (isTextureOriented) {
			return this.getName()+orientation+"-"+i;
		}
		return this.getName()+i;
	}

	/**
	 * animation of a move
	 * @return
	 */
	public float[] getAffichePos(){
		float a,b;
		if(animationChrono<0.2){
			switch(orientation){
				case(0):
					a=getX()+1-animationChrono*5;
					b=getY()+getIntFloat()*0.1f*((float)Math.cos(5*elapsedTime));
					break;
				case(1):
					a=getX();
					b=getY()-1+animationChrono*5+getIntFloat()*0.1f*((float)Math.cos(5*elapsedTime));
					break;
				case(2):
					a=getX()-1+animationChrono*5;
					b=getY()+getIntFloat()*0.1f*((float)Math.cos(5*elapsedTime));
					break;
				default:
					a=getX();
					b=getY()+1-animationChrono*5+getIntFloat()*0.1f*((float)Math.cos(5*elapsedTime));
					break;
			}
		}else {
			a=getX();
			b=getY()+getIntFloat()*0.1f*((float)Math.cos(5*elapsedTime));
		}
		float[] tab={a,b};
		return(tab);
	}

	protected int getY() {
		return loc.getY();
	}

	protected int getX() {
		return loc.getX();
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
			return (Item)getClass().getConstructors()[0].newInstance(null, orientation);
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

	/**
	 *
	 * @return the name of the class except for the ItemRef ones whom return is the reference class
	 * Example :
	 * for the Hot class it returns "Hot"
	 * for the WallText class it returns "Wall"
	 */
	public String getRefName() {
		return getName();
	}

	/**
	 * Ask for the rule table
	 * @return the rule table of the level
	 */
	public LogicHashtable getRuleTable() {
		return loc.getLevel().getRuleTable();
	}
}
