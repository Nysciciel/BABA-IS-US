package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.ServerLevel;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.client_serveur.ServerCallBack;
import com.mygdx.game.utils.Constants;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LoadingView implements Screen, ServerCallBack {


    private MainTest parent; // a field to store our orchestrator
    private com.mygdx.game.Level lvl;
    private Stage stage;

    private ConcurrentLinkedQueue data;
    private com.mygdx.game.ServerLevel slvl;
    private ServerThread thread;

    private BitmapFont gameTitleText, touchText, toText, startText;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private GlyphLayout gameTitleGlyph, touchGlyph, toGlyph, startGlyph;
    private String IP;
    private Skin skin;

    private TextureAtlas textureAtlas;
    private Animation animation;
    private TextureAtlas textureAtlas2;
    private Animation animation2;

    // our constructor with a Box2DTutorial argument
    public LoadingView(MainTest mainTest) {

        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.data = new ConcurrentLinkedQueue();
        this.thread = new ServerThread(data,this);
        this.IP = " ";


        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = 64;
        this.gameTitleText = this.generator.generateFont(this.parameter);
        this.gameTitleGlyph = new GlyphLayout();
        this.gameTitleGlyph.setText(this.gameTitleText, Constants.LOADING_SCREEN);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        textureAtlas = new TextureAtlas(Gdx.files.internal("BackgroundMenu.txt"));
        animation = new Animation(2/3f, textureAtlas.getRegions());
        textureAtlas2 = new TextureAtlas(Gdx.files.internal("NightSheet.txt"));
        animation2 = new Animation(2/3f, textureAtlas2.getRegions());

    }

    public Stage getStage(){
        return stage;
    }

    public ServerLevel getSlvl() {
        return this.slvl;
    }

    public ConcurrentLinkedQueue getData(){
        return this.data;
    }

    public ServerThread getThread(){
        return thread;
    }


    @Override
    public void show() {
        // TODO Auto-generated method stub
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        table.setVisible(false);
        stage.addActor(table);
        this.IP = MainTest.ip_addr;
        TextField address = new TextField(this.IP, skin);

        table.add(address);


        if(this.IP != null){
            table.setVisible(true);
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parent.screenChoice(MainTest.MENU,null);
            this.thread.interrupt();
        }

        if(this.thread.checkClient()){
            parent.screenChoice(MainTest.SERVER,null);
        }


    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        parent.screenChoice(MainTest.LOADING,null);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.getBatch().begin();
        //stage.getBatch().draw(this.gameTitleGlyph,Gdx.graphics.getWidth()/2 - this.gameTitleGlyph.width/2,Gdx.graphics.getHeight()/2);
        float elapsedTime = parent.getElapsedTime();
        elapsedTime += Gdx.graphics.getDeltaTime();
        parent.setElapsedTime(elapsedTime);
        if(40*elapsedTime>Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth())){
            parent.setElapsedTime(0);
        }
        stage.getBatch().draw((TextureRegion) animation2.getKeyFrame(elapsedTime, true),0,0,Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth()),Math.max(Gdx.graphics.getHeight(),(170/420)*Gdx.graphics.getWidth()));
        stage.getBatch().draw((TextureRegion) animation.getKeyFrame(elapsedTime, true),40*elapsedTime,0,Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth()),Math.max(Gdx.graphics.getHeight(),(170/420)*Gdx.graphics.getWidth()));
        stage.getBatch().draw((TextureRegion) animation.getKeyFrame(elapsedTime, true),40*elapsedTime-Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth()),0,Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth()),Math.max(Gdx.graphics.getHeight(),(170/420)*Gdx.graphics.getWidth()));
        this.gameTitleText.draw(stage.getBatch(), this.gameTitleGlyph, Gdx.graphics.getWidth()/2 - this.gameTitleGlyph.width/2, Gdx.graphics.getHeight()/3);
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
        this.gameTitleText.dispose();
    }

    @Override
    public void dataReceived(int data) {

    }
}
