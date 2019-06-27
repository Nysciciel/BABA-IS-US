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

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
//import com.mygdx.game.states.MainMenu;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerView implements Screen,ServerCallBack {

	private MainTest parent; // a field to store our orchestrator
	private com.mygdx.game.ServerLevel slvl;
	private Stage stage;
	private Client client;
	private Server server;
	private ServerThread thread;
	private boolean enabled;
	private ConcurrentLinkedQueue data;
	private Texture background;
	private int movePoto;
	private Table table;
	private ConcurrentLinkedQueue<Integer> actions = new ConcurrentLinkedQueue();
	private int keyPressed = -1;
	private long timeRef;
	private int moveTime = 150;
	private boolean hasMoved = false;

	private Texture texture;

	public ServerView(MainTest mainTest, ServerThread thread, ConcurrentLinkedQueue data, String filename) {

		parent = mainTest;     // setting the argument to our field.
		stage = new Stage(new ScreenViewport());
		table = new Table();
		table.setFillParent(true);

		//data = new ArrayBlockingQueue<Integer>(1);
		this.data = data;
		this.movePoto = -1;
		this.enabled = true;
		this.background = new Texture("Menu_background.jpg");
		//this.slvl = new com.mygdx.game.ServerLevel("level.txt");
		this.slvl = new com.mygdx.game.ServerLevel(filename, parent);
		this.thread = thread;
		this.server = this.thread.getServer();
		this.thread.getServer().setServerCallBack(this);
		Gdx.input.setInputProcessor(stage);

		table.add(slvl).expand().fill();

		stage.addActor(table);

		texture = new Texture(Gdx.files.internal("backgroundLevel.png"));
	}

	public Stage getStage(){
		return stage;
	}

	public Thread getThread() {
		return thread;
	}

	@Override
	public void show() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            System.out.println("connection fermee avant setconnected");
			this.thread.getStatus();
			this.server.setConnected(false);
            System.out.println("connection fermee apres setconnected");
            this.thread.getStatus();
			this.thread.shutCO();
            parent.screenChoice(MainTest.MENU,null);
			//this.thread.interrupt();
		}
		if(enabled) {

			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				slvl.fakeTurn();
				try {
					data.add("b");
					data.add("15");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
				slvl.reset();
				try {
					data.add("b");
					data.add("16");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
				slvl.rollback();
				try {
					data.add("b");
					data.add("14");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
				slvl.moveYou1(2);
				slvl.endturn();

				try {
					data.add("b");
					data.add("12");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				slvl.moveYou1(1);
				slvl.endturn();

				try {
					data.add("b");
					data.add("11");
				} catch (Exception e) {
					e.printStackTrace();
				}


			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
				slvl.moveYou1(0);
				slvl.endturn();

				try {
					data.add("b");
					data.add("10");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				slvl.moveYou1(3);
				slvl.endturn();

				try {
					data.add("b");
					data.add("13");
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			while(actions.peek()!=null) {

				movePoto = (int)actions.poll();
				System.out.println("value taken from actions:"+movePoto);
				switch(movePoto) {
				case(4):
					slvl.rollback();
				break;
				case(5):
					slvl.fakeTurn();
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
				case (99):
					parent.screenChoice(MainTest.MENU,null);
					try {
						server.getSocket().close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				break;
				default:
				}
				try {
					if(0<=movePoto && movePoto<=6) {
						data.add("b");
						data.add(Integer.toString(movePoto+20));
						System.out.println("tosend:" + data);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}


	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		parent.screenChoice(MainTest.SERVER,null);
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		stage.getBatch().begin();
		stage.getBatch().draw(texture,0,0,slvl.getMatrixLength()*Math.min(slvl.getWidth()/slvl.getIntLength(), slvl.getHeight()/slvl.getIntHeight()),slvl.getMatrixHeight()*Math.min(slvl.getWidth()/slvl.getIntLength(), slvl.getHeight()/slvl.getIntHeight()));
		stage.getBatch().end();
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
		texture.dispose();
	}
	@Override
	public void dataReceived(int data) {
		System.out.println("avant actions:"+actions);
		try {
			actions.add(data);
			System.out.println("received:"+data);
			System.out.println("apr�s actions:"+actions);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
