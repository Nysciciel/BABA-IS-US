package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Test.Main.MainTest;

public class Settings implements Screen {


    private MainTest parent; // a field to store our orchestrator
    private com.mygdx.game.Level lvl;
    private Stage stage;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private TextureAtlas textureAtlas2;
    private Animation animation2;
    private float elapsedTime = 0;

    // our constructor with a Box2DTutorial argument
    public Settings(MainTest mainTest) {

        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        textureAtlas = new TextureAtlas(Gdx.files.internal("BackgroundMenu.txt"));
        animation = new Animation(2/3f, textureAtlas.getRegions());
        textureAtlas2 = new TextureAtlas(Gdx.files.internal("NightSheet.txt"));
        animation2 = new Animation(2/3f, textureAtlas2.getRegions());
        Gdx.input.setInputProcessor(stage);

    }

    public Stage getStage(){
        return stage;
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
        parent.screenChoice(MainTest.SETTINGS,null);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.getBatch().begin();
        elapsedTime += Gdx.graphics.getDeltaTime();
        if(40*elapsedTime>Math.max((420/170)*Gdx.graphics.getHeight(),Gdx.graphics.getWidth())){
            elapsedTime=0;
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
}
