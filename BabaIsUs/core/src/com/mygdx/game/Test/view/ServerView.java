package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Level;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.client_serveur.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
//import com.mygdx.game.states.MainMenu;

public class ServerView implements Screen,ServerCallBack {

	private MainTest parent; // a field to store our orchestrator
	private com.mygdx.game.ServerLevel slvl;
	private Stage stage;
	private Client client;
	private Server server;
	private ServerThread thread;
	private boolean enabled;
	private BlockingQueue<Integer> data;
	private Texture background;
	private int movePoto;

	public ServerView(MainTest mainTest) {

		parent = mainTest;     // setting the argument to our field.
		stage = new Stage(new ScreenViewport());

		data = new ArrayBlockingQueue<Integer>(1);

		this.movePoto = -1;
		this.enabled = true;
		this.background = new Texture("Menu_background.jpg");
		this.slvl = new com.mygdx.game.ServerLevel("level.txt");
		this.thread = new ServerThread(data,this,slvl);
		Gdx.input.setInputProcessor(stage);
	}

	public Stage getStage(){
		return stage;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		//System.out.println("ON EST DANS LA SERVERVIEW POTOOOOOOOOOOOOOOOOOOOOOO");
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			parent.screenChoice(MainTest.MENU);
		}
		if(enabled) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
				slvl.endturn();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
				slvl.rollback();
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
				slvl.moveYou1(2);
				slvl.endturn();

				try {
					data.put(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				slvl.moveYou1(1);
				slvl.endturn();

				try {
					data.put(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}


			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
				slvl.moveYou1(0);
				slvl.endturn();

				try {
					data.put(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				slvl.moveYou1(3);
				slvl.endturn();

				try {
					data.put(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			if(movePoto != -1) {
				slvl.moveYou2(movePoto);
				movePoto = -1;
				slvl.endturn();
			}
		}
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		parent.screenChoice(MainTest.SERVER);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.getBatch().begin();
		/* if(server.isConnected()) {
            lvl.render(stage.getBatch());
            enabled =true;
        }
        else{
            stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        }*/
		slvl.render(stage.getBatch());
		stage.getBatch().end();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		this.background.dispose();
	}

	@Override
	public void dataReceived(int data) {
		movePoto = data;
	}
}

class ServerThread extends Thread{
	private BlockingQueue<Integer> bq;
	ServerCallBack callBack;
	Level level;

	public ServerThread(BlockingQueue<Integer> bq, ServerCallBack callBack, Level level){
		super();
		this.bq = bq;
		this.callBack = callBack;
		this.level = level;
		this.start();
	}

	public void run() {
		new Server(this.bq,this.callBack,this.level) ;
	}
}