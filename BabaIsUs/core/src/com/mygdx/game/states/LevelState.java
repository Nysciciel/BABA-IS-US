package com.mygdx.game.states;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.*;

import com.mygdx.game.client_serveur.*;

public class LevelState extends GameState implements ServerCallBack{
	
	private Level lvl;
	
	
	
	//private Client client;
	
	//private BlockingQueue<Integer> data;
	
	

	protected LevelState(GameStateManager gms) {
		super(gms);
		//data = new ArrayBlockingQueue<Integer>(1);
		//client = new Client(data,this,"192.168.43.114");
		
		
		this.lvl = new Level("level.txt");
		
		
		
	}

	@Override
	protected void handleInput() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			lvl.moveYou(2);
			lvl.endturn();
			/*
			try {
				data.put(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			lvl.moveYou(1);
			lvl.endturn();
			/*
			try {
				data.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			lvl.moveYou(0);
			lvl.endturn();
			/*
			try {
				data.put(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			lvl.moveYou(3);
			lvl.endturn();
			/*
			try {
				data.put(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
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

	@Override
	public void dataReceived(int data) {
		// TODO Auto-generated method stub
		System.out.println(data);
	}

}