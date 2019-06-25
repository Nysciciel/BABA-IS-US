package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Level;
import com.mygdx.game.ServerLevel;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.client_serveur.*;
import java.util.concurrent.ArrayBlockingQueue;

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
	private Table table;
	private int shash;
	private int chash;
	private BlockingQueue<Integer> actions = new ArrayBlockingQueue(10);

	public ServerView(MainTest mainTest, ServerThread thread, BlockingQueue<Integer> data) {

		parent = mainTest;     // setting the argument to our field.
		stage = new Stage(new ScreenViewport());
		table = new Table();
		table.setFillParent(true);

		//data = new ArrayBlockingQueue<Integer>(1);
		this.data = data;
		this.shash = 0;
		this.chash = 0;
		this.movePoto = -1;
		this.enabled = true;
		this.background = new Texture("Menu_background.jpg");
		//this.slvl = new com.mygdx.game.ServerLevel("level.txt");
		this.slvl = new com.mygdx.game.ServerLevel("level.txt");
		this.thread = thread;
		this.thread.getServer().setServerCallBack(this);
		Gdx.input.setInputProcessor(stage);

		table.add(slvl).expand().fill();

		stage.addActor(table);
	}

	public Stage getStage(){
		return stage;
	}

	@Override
	public void show() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			parent.screenChoice(MainTest.MENU);
			this.thread.setConnection(false);
			this.thread.shutCO();
			//this.thread.interrupt();
		}
		if(enabled) {

			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				slvl.endturn();
				try {
					data.put(15);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
				slvl.reset();
				try {
					data.put(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
				slvl.rollback();
				try {
					data.put(14);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
				slvl.moveYou1(2);
				slvl.endturn();

				try {
					data.put(12);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				slvl.moveYou1(1);
				slvl.endturn();

				try {
					data.put(11);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}


			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
				slvl.moveYou1(0);
				slvl.endturn();

				try {
					data.put(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				slvl.moveYou1(3);
				slvl.endturn();

				try {
					data.put(13);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
			while(actions.peek()!=null) {

				movePoto = actions.poll();
				switch(movePoto) {
				case(4):
					slvl.rollback();
				break;
				case(5):
					slvl.endturn();
				break;
				case(6):
					slvl.reset();
				break;
				case(0):
				case(1):
				case(2):
				case(3):
					slvl.moveYou2(movePoto);
				slvl.endturn();
				break;
				default:
				}
				try {
					if(0<=movePoto && movePoto<=6) {
						data.put(movePoto+20);
						int kjsghj = movePoto+20;
						System.out.println("tosend:" + data);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		parent.screenChoice(MainTest.SERVER);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		/* if(server.isConnected()) {
            lvl.render(stage.getBatch());
            enabled =true;
        }
        else{
            stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
        }*/
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
		try {
			int number = movePoto;
			actions.put(number);
			System.out.println("received:"+number);
			System.out.println("actions:"+actions);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

