package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.*;

public class LevelState extends GameState{
	
	private Level lvl;

	protected LevelState(GameStateManager gms) {
		super(gms);
		
		this.lvl = new Level("level.txt");
	}

	@Override
	protected void handleInput() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			lvl.moveYou(2);
			lvl.reset();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			lvl.moveYou(1);
			lvl.reset();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			lvl.moveYou(0);
			lvl.reset();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			lvl.moveYou(3);
			lvl.reset();
		}
	}

	@Override
	public void update(float dt) {
		this.handleInput();
		lvl.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		lvl.render(sb);
	}

	@Override
	public void dispose() {
		lvl.dispose();
	}

}