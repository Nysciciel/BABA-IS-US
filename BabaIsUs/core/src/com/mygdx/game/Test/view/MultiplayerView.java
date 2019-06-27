package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.client_serveur.*;

public class MultiplayerView implements Screen {


    private MainTest parent; // a field to store our orchestrator
    private Stage stage;
    private Skin skin;
    private Skin skin2;
    private TextureAtlas textureAtlas;
    private Animation animation;
    private TextureAtlas textureAtlas2;
    private Animation animation2;
    // our constructor with a Box2DTutorial argument
    public MultiplayerView(MainTest mainTest) {

        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        textureAtlas = new TextureAtlas(Gdx.files.internal("BackgroundMenu.txt"));
        animation = new Animation(2/3f, textureAtlas.getRegions());
        textureAtlas2 = new TextureAtlas(Gdx.files.internal("NightSheet.txt"));
        animation2 = new Animation(2/3f, textureAtlas2.getRegions());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("Skins/freezing-ui.json"));
        skin2 = new Skin(Gdx.files.internal("uiskin.json"));
    }

    public Stage getStage(){
        return stage;
    }
    public void setStage(Stage nstage){ this.stage = nstage;}

    @Override
    public void show() {
        // TODO Auto-generated method stub
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();



        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        table.setVisible(true);
        stage.addActor(table);


        TextButton host = new TextButton("Host a game", skin);
        TextButton connect = new TextButton("Connect to an already existing game", skin);
        final TextField address = new TextField("", skin2);
        final TextButton okBut = new TextButton("OK", skin);
        address.setVisible(false);
        okBut.setVisible(false);
        table.add(host).fillX().uniformX();
        table.row().pad(20, 0, 20, 0);
        table.add(connect).fillX().uniformX();
        table.row();
        table.row().pad(0,80,0,0);
        table.add(address);
        table.row().pad(20, 70, 20, 0);
        table.add(okBut);
        table.row().pad(20, 70, 20, 0);

        host.setTransform(true);
        host.setScale(1.3f);
        connect.setTransform(true);
        connect.setScale(1.3f);
        okBut.setTransform(true);
        okBut.setScale(1.3f);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parent.screenChoice(MainTest.MENU,null);
        }

        host.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.screenChoice(MainTest.MULTILEVELSELECT,null);
            }
        });

        connect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                address.setVisible(true);
                okBut.setVisible(true);
               // Popup test = new Popup(IP);
            }
        });

      /*  if(Gdx.input.isKeyJustPressed((Input.Keys.ENTER))){
            okBut.act(float delta);
        }*/

        okBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                String IP = address.getText();
                MainTest.ip_addr = IP;
                parent.screenChoice(MainTest.CLIENT,null);

                // Popup test = new Popup(IP);
            }
        });


    }

    public static class DialogTest extends Dialog{
        public DialogTest(String title, Skin skin){
            super(title, skin);
        }

        {
            text("Enter a valid IP");
            button("Ok");
            button("Cancel");

        }

        protected void result(Object object){
        }
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        parent.screenChoice(MainTest.MULTIPLAYER,null);
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
}
