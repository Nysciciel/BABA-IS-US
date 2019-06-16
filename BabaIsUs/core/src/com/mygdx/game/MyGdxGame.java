package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.states.MainMenu;

public class MyGdxGame extends ApplicationAdapter {
	private GameStateManager gsm;
	private SpriteBatch sb;
	
	@Override
	public void create () {
		this.gsm = new GameStateManager();
		this.sb = new SpriteBatch();
		
		this.gsm.push(new MainMenu(gsm));
	}

	@Override
	public void render () {
		this.gsm.update(Gdx.graphics.getDeltaTime());
		this.gsm.render(this.sb);
	}
	
	@Override 
	public void dispose () {
		this.sb.dispose();
	}
}
