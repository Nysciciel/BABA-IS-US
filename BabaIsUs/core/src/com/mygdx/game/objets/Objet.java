package com.mygdx.game.objets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.utils.Constants;

public abstract class Objet {
	protected Texture texture;
	protected Vector2 position;
	
	public Objet(int x, int y) {
		this.position = new Vector2(x,y);
	}
	
	public abstract void update(float dt);
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public void dispose() {
		texture.dispose();
	}
	
	public void goDown() {
		position.y -= Constants.SPEED; 
	}
	
	public void goUp() {
		position.y += Constants.SPEED; 
	}
	
	public void goLeft() {
		position.x -= Constants.SPEED; 
	}
	
	public void goRight() {
		position.x += Constants.SPEED; 
	}
	
}
