package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
    private Texture background;
    private Skin skin;
    // our constructor with a Box2DTutorial argument
    public MultiplayerView(MainTest mainTest) {

        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        this.background = new Texture("Menu_background.jpg");
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
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
        final TextField address = new TextField("IP address", skin);
        final TextButton okBut = new TextButton("OK", skin);
        address.setVisible(false);
        okBut.setVisible(false);
        table.add(host).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(connect).fillX().uniformX();
        table.row();
        table.add(address);
        table.row().pad(10, 0, 10, 0);
        table.add(okBut);

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parent.screenChoice(MainTest.MENU,null);
        }

        host.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.screenChoice(MainTest.LOADING,null);
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
        stage.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
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
    }
}
