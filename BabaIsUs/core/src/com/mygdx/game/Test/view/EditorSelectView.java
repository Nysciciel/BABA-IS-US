package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.utils.ObjectList;

public class EditorSelectView implements Screen{
	
	 private MainTest parent; // a field to store our orchestrator
	 private Stage stage;
	 private VerticalGroup table;
	 private ScrollPane pane;
	 private TextField textField;
	 
	 private TextureAtlas textureAtlas;
	    private Animation animation;
	    private TextureAtlas textureAtlas2;
	    private Animation animation2;
	 
	 private TextButton nouveau;
	 
	 public EditorSelectView(MainTest mainTest) {
		 this.parent = mainTest;
		 stage = new Stage(new ScreenViewport());
		 Gdx.input.setInputProcessor(stage);
		 
		 textureAtlas = new TextureAtlas(Gdx.files.internal("BackgroundMenu.txt"));
	     animation = new Animation(2/3f, textureAtlas.getRegions());
	     textureAtlas2 = new TextureAtlas(Gdx.files.internal("NightSheet.txt"));
	     animation2 = new Animation(2/3f, textureAtlas2.getRegions());
		 
		 table = new VerticalGroup();
	     table.setFillParent(true);
	     
	     Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
	     
	     FileHandle handle = new FileHandle("Level/");
	     
	     FileHandle[] files = handle.list();
	     
	     table.center().fill().expand().space(20);
	     textField = new TextField("",skin);
	     nouveau = new TextButton("New",skin);
	     for(FileHandle file : files) {
	    	 table.addActor(new TextButton(file.name(),skin));
	     }
	     
	     
	     pane = new ScrollPane(table);
	     
	     Table table1 = new Table();
	     table1.setFillParent(true);
	     table1.center().pad(100, 100, 100, 100);
	     table1.add(textField).expandX().fill().width(400);
	     table1.row();
	     table1.add(nouveau).expandX().fill().width(400);
	     table1.row();
	     table1.add(pane).fill().expand().width(400).pad(50, 0, 50, 0);
	     stage.addActor(table1);
	     
	     
	     nouveau.addListener(new InputListener() {
	        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	        		String nom = event.getTarget().toString();
	        		nom = nom.split(" ")[nom.split(" ").length-1];
	        		parent.screenChoice(MainTest.EDITOR,textField.getText());

	        		return true;
	        	}
	        	
	        });
	     
	     table.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.split(" ")[nom.split(" ").length-1];

        		parent.screenChoice(MainTest.EDITOR,nom);

        		return true;
        	}
        	
        });
	     
	 }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parent.screenChoice(MainTest.MENU,null);
        } 
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			parent.screenChoice(MainTest.MENU,null);
		}
		
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.getBatch().begin();
        float elapsedTime = parent.getElapsedTime();
        elapsedTime += Gdx.graphics.getDeltaTime();
        parent.setElapsedTime(elapsedTime);
        if(40*elapsedTime>Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth())){
            parent.setElapsedTime(0);
        }
        stage.getBatch().draw((TextureRegion) animation2.getKeyFrame(elapsedTime, true),0,0,Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth()),Math.max(Gdx.graphics.getHeight(),(170/420)*Gdx.graphics.getWidth()));
        stage.getBatch().draw((TextureRegion) animation.getKeyFrame(elapsedTime, true),40*elapsedTime,0,Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth()),Math.max(Gdx.graphics.getHeight(),(170/420)*Gdx.graphics.getWidth()));
        stage.getBatch().draw((TextureRegion) animation.getKeyFrame(elapsedTime, true),40*elapsedTime-Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth()),0,Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth()),Math.max(Gdx.graphics.getHeight(),(170/420)*Gdx.graphics.getWidth()));
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
        this.textureAtlas.dispose();
        this.textureAtlas2.dispose();
	}
	
	public Stage getStage() {
    	return stage;
    }

}
