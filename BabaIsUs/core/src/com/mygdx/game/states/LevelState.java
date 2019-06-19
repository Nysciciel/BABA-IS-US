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
			lvl.endturn();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			lvl.moveYou(1);
			lvl.endturn();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			lvl.moveYou(0);
			lvl.endturn();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			lvl.moveYou(3);
			lvl.endturn();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER )) {
			lvl.endturn();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.Z )) {
			lvl.rollback();
		}
	}

	@Override
	public void update(float dt) {
		this.handleInput();
		lvl.update();
	}

	@Override
	public void render(SpriteBatch sb) {
	
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sb.begin();
		lvl.draw(sb);
		
		
		sb.end();
	}

	@Override
	public void dispose() {
		
		
		lvl.dispose();
	}

}