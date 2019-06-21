/*package com.mygdx.game.states;

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
	
	private LevelView lvl;
	
	
	
	//private Client client;
	
	//private BlockingQueue<Integer> data;
	
	

	protected LevelState(GameStateManager gms) {
		super(gms);

		data = new ArrayBlockingQueue<Integer>(1);
		client = new Client(data,this,"137.194.90.186");

		//data = new ArrayBlockingQueue<Integer>(1);
		//client = new Client(data,this,"192.168.43.114");


		this.lvl = new LevelView("level.txt");
		
		
		
	}

	//@Override
	protected void handleInput() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			lvl.moveYou(2);
			lvl.endturn();
			lvl.updateRules();
			/*
			try {
				data.put(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
/*		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			lvl.moveYou(1);
			lvl.endturn();
			lvl.updateRules();
			/*
			try {
				data.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
/*		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			lvl.moveYou(0);
			lvl.endturn();
			lvl.updateRules();
			/*
			try {
				data.put(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
/*		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			lvl.moveYou(3);
			lvl.endturn();
			lvl.updateRules();
			/*
			try {
				data.put(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			*/
/*		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER ) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE )) {
			lvl.endturn();
			lvl.updateRules();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.Z )) {
			lvl.rollback();
			lvl.updateRules();
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			this.gsm.set(new MainMenu(gsm));

		if(Gdx.input.isKeyJustPressed(Input.Keys.R )) {
			lvl.reset();
<<<<<<< HEAD
<<<<<<< HEAD

=======
			lvl.updateRules();
>>>>>>> Grammaire
=======
			lvl.updateRules();
=======

>>>>>>> 4abd532973c760d05cdbc950df249d2db39ff1ad
>>>>>>> dev
		}
	}

	//@Override
	public void update(float dt) {
		this.handleInput();
	}

	//@Override
	public void render(SpriteBatch sb) {
		lvl.render(sb);
	}

	//@Override
	public void dispose() {
		lvl.dispose();
	}


}

	@Override
	public void dataReceived(int data) {
		// TODO Auto-generated method stub
		System.out.println(data);
	}

}*/

