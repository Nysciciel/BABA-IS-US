package com.mygdx.game.Test.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.utils.Constants;

public class WinView implements Screen {


    private MainTest parent; // a field to store our orchestrator
    private com.mygdx.game.Level lvl;
    private Stage stage;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private TextureAtlas textureAtlas2;
    private Animation animation2;

    private BitmapFont gameTitleText, touchText, toText, startText;
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private GlyphLayout gameTitleGlyph, touchGlyph, toGlyph, startGlyph;

    // our constructor with a Box2DTutorial argument
    public WinView(MainTest mainTest) {

        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        textureAtlas = new TextureAtlas(Gdx.files.internal("BackgroundMenu.txt"));
        animation = new Animation(2/3f, textureAtlas.getRegions());
        textureAtlas2 = new TextureAtlas(Gdx.files.internal("NightSheet.txt"));
        animation2 = new Animation(2/3f, textureAtlas2.getRegions());

        this.generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        this.parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        this.parameter.size = 256;
        this.gameTitleText = this.generator.generateFont(this.parameter);
        this.gameTitleGlyph = new GlyphLayout();
        this.gameTitleGlyph.setText(this.gameTitleText, Constants.WIN_SCREEN);
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
        parent.screenChoice(MainTest.WIN,null);
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
        this.gameTitleText.draw(stage.getBatch(), this.gameTitleGlyph, Gdx.graphics.getWidth()/2 - this.gameTitleGlyph.width/2, Gdx.graphics.getHeight()/2);
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
}

