package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Level;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.client_serveur.*;

import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
//import com.mygdx.game.states.MainMenu;

public class LevelView implements Screen,ServerCallBack, InputProcessor {

	private MainTest parent; // a field to store our orchestrator
	private Level lvl;
	private Stage stage;
	private Client client;
	private Server server;
	private BlockingQueue<Integer> data;
	private Table table;
	private int keyPressed = -1;
	private Texture texture;
	private long timeRef;
	private int moveTime = 150;
	private boolean hasMoved = false;
	private Stack<Integer> actionStack;

	public LevelView(MainTest mainTest, String fileName) {

		parent = mainTest;     // setting the argument to our field.
		stage = new Stage(new ScreenViewport());
		table = new Table();
		table.setFillParent(true);
		timeRef = System.currentTimeMillis();
		actionStack = new Stack<Integer>();
		
		this.lvl = new Level(fileName, parent);

		lvl.setPlayed();

		texture = new Texture(Gdx.files.internal("backgroundLevel.png"));
		stage.addActor(table);
		table.add(lvl).expand().fill().center().right();

		timeRef = System.currentTimeMillis();
		Gdx.input.setInputProcessor(this);
	}

	public Stage getStage(){
		return stage;
	}

	@Override
	public void show() {
		if(lvl == null){
			lvl = new com.mygdx.game.Level("Level/level.txt", parent);
		}
	}

	public Level getLvl(){
		return lvl;
	}

	public void setLvl(Level nlvl){
		this.lvl = nlvl;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.getViewport().update(width, height, true);
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
		texture.dispose();
	}

	@Override
	public void dataReceived(int data) {
	}


	@Override
	public boolean keyDown(int keycode) {

		
		switch(keycode) {
		case Keys.ENTER:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.SPACE:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.E:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.R:
			lvl.reset();
			break;
		case Keys.RIGHT:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.UP:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.LEFT:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.DOWN:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.D:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.Z:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.Q:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.S:
			keyPressed = keycode;
			actionStack.push(keycode);
			break;
		case Keys.ESCAPE:
			parent.screenChoice(MainTest.MENU, null);
			break;
		default:
			return false;
		}


		
		return true;
	}

	private boolean keyAction(int keycode) {

		switch(keycode) {
		case Keys.ENTER:
			lvl.fakeTurn();
			break;
		case Keys.SPACE:
			lvl.fakeTurn();
			break;
		case Keys.E:
			lvl.rollback();
			break;
		case Keys.R:
			lvl.reset();
			break;
		case Keys.RIGHT:
			lvl.moveYou1(2);
			lvl.endturn();
			break;
		case Keys.UP:
			lvl.moveYou1(1);
			lvl.endturn();
			break;
		case Keys.LEFT:
			lvl.moveYou1(0);
			lvl.endturn();
			break;
		case Keys.DOWN:
			lvl.moveYou1(3);
			lvl.endturn();
			break;
		case Keys.D:
			lvl.moveYou2(2);
			lvl.endturn();
			break;
		case Keys.Z:
			lvl.moveYou2(1);
			lvl.endturn();
			break;
		case Keys.Q:
			lvl.moveYou2(0);
			lvl.endturn();
			break;
		case Keys.S:
			lvl.moveYou2(3);
			lvl.endturn();
			break;
		case Keys.ESCAPE:
			parent.screenChoice(MainTest.MENU, null);
			break;
		default:
			return false;

		}
		return true;    	
	}

	@Override
	public boolean keyUp(int keycode) {

		if (keycode == keyPressed) {
			keyPressed = -1;
		}
		return false;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub	
		
		if(parent.hasWon()){
			parent.screenChoice(MainTest.WIN,null);
			parent.resetwin();
		}
		
		
		
		if (System.currentTimeMillis()-timeRef > moveTime) {
			timeRef = System.currentTimeMillis();
			if (keyPressed != -1 && Gdx.input.isKeyPressed(keyPressed)) {
				actionStack.push(keyPressed);
			}
			if (!actionStack.empty()) {
				keyAction(actionStack.pop());
				if (!actionStack.empty()) {
					int last = actionStack.pop();
					actionStack = new Stack<Integer>();
					if (last != keyPressed)
						actionStack.push(last);
				}
				else
					actionStack = new Stack<Integer>();
			}
			
		}

		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.getBatch().begin();
		stage.getBatch().draw(texture,48*(16-(lvl.getIntLength()/lvl.getIntHeight())*9),0,lvl.getMatrixLength()*Math.min(lvl.getWidth()/lvl.getIntLength(), lvl.getHeight()/lvl.getIntHeight()),lvl.getMatrixHeight()*Math.min(lvl.getWidth()/lvl.getIntLength(), lvl.getHeight()/lvl.getIntHeight()));
		stage.getBatch().end();
		stage.draw();
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
