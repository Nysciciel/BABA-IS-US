package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.objets.Baba;

public class LevelState extends GameState{
	
	private Baba baba;

	protected LevelState(GameStateManager gms) {
		super(gms);
		
		this.baba = new Baba(100,100);
	}

	@Override
	protected void handleInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			baba.goRight();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			baba.goUp();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			baba.goLeft();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			baba.goDown();
		}
	}

	@Override
	public void update(float dt) {
		this.handleInput();
		this.baba.update(dt);
	}

	@Override
	public void render(SpriteBatch sb) {
	
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sb.begin();
		sb.draw(baba.getTexture(), baba.getPosition().x, baba.getPosition().y);
		sb.end();
	}

	@Override
	public void dispose() {
		baba.dispose();
	}

}
