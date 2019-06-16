package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameStateManager;

public abstract class GameState {
	protected OrthographicCamera camera;
	protected GameStateManager gsm;
	
	protected GameState(GameStateManager gsm) {
		this.camera = new OrthographicCamera();
		this.gsm = gsm;
	}
	
	protected abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb); 
	public abstract void dispose();
}
