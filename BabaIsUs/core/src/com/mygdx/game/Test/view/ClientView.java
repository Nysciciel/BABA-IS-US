package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.client_serveur.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
//import com.mygdx.game.states.MainMenu;

public class ClientView implements Screen,ServerCallBack {

	private MainTest parent; // a field to store our orchestrator
	private com.mygdx.game.Level lvl;
	private Stage stage;
	private Client client;
	private Server server;
	private BlockingQueue<Integer> data;
	private int movePoto;
	private Table table;


	public ClientView(MainTest mainTest, String ip_addr) {

		this.movePoto = -1;
		parent = mainTest;     // setting the argument to our field.
		stage = new Stage(new ScreenViewport());
		table = new Table();
		table.setFillParent(true);

		data = new ArrayBlockingQueue<Integer>(1);
		client = new Client(data,this,ip_addr);

		this.lvl = new com.mygdx.game.Level("levelc.txt");
		Gdx.input.setInputProcessor(stage);
		
		table.add(lvl).expand().fill();
		
		stage.addActor(table);
	}

	public Stage getStage(){
		return stage;
	}

	@Override
	public void show() {
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			parent.screenChoice(MainTest.MENU);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			lvl.endturn();
			try {
				data.put(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
			lvl.reset();
			try {
				data.put(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
			lvl.rollback();
			try {
				data.put(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			lvl.moveYou2(2);
			lvl.endturn();

			try {
				data.put(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			lvl.moveYou2(1);
			lvl.endturn();

			try {
				data.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}


		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			lvl.moveYou2(0);
			lvl.endturn();

			try {
				data.put(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			lvl.moveYou2(3);
			lvl.endturn();

			try {
				data.put(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		if(movePoto != -1) {
			switch(movePoto) {
			case(4):
				lvl.rollback();
				break;
			case(5):
				lvl.endturn();
				break;
			case(6):
				lvl.reset();
				break;
			case(0):
			case(1):
			case(2):
			case(3):
				lvl.moveYou1(movePoto);
				lvl.endturn();
				break;
			default:
			}
			movePoto = -1;
		}
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		parent.screenChoice(MainTest.CLIENT);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
	}

	@Override
	public void dataReceived(int data) {
		movePoto = data;
	}
}