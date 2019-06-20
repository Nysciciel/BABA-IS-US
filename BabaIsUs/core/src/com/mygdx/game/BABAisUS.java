package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.rule.RuleTextReaderTest;
import com.mygdx.game.states.MainMenu;

public class BABAisUS extends ApplicationAdapter {
	private GameStateManager gsm;
	private SpriteBatch sb;
	
	@Override
	public void create () {
		
		this.gsm = new GameStateManager();
		this.sb = new SpriteBatch();
		
		this.gsm.push(new MainMenu(gsm));
		
		//RuleTextReaderTest test = new RuleTextReaderTest();
	}

	@Override
	public void render () {
		this.gsm.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sb.begin();
		gsm.render(sb);
		sb.end();
	}
	
	@Override 
	public void dispose () {
		this.sb.dispose();
		gsm.dispose();
	}
}