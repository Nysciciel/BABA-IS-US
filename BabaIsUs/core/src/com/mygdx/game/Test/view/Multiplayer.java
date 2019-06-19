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
import javafx.stage.Popup;

public class Multiplayer implements Screen {


    private MainTest parent; // a field to store our orchestrator
    private Stage stage;
    private Texture background;

    // our constructor with a Box2DTutorial argument
    public Multiplayer(MainTest mainTest) {

        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        this.background = new Texture("Menu_background.jpg");
        Gdx.input.setInputProcessor(stage);
    }

    public Stage getStage(){
        return stage;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));



        TextButton okBut = new TextButton("OK", skin);
        TextButton cancelBut= new TextButton("Cancel", skin);


        Table table2 = new Table();
        table2.setFillParent(true);
        table2.setVisible(false);
        table2.right().top();
        stage.addActor(table2);

        TextField hosting = new TextField("You are hosting a game", skin);
        hosting.isPasswordMode();
        table2.add(hosting).fillX().uniformX();
        table2.row().pad(10, 0, 10, 0);
        table2.add(okBut).fillX().uniformX();
        table2.add(cancelBut).fillX().uniformX();

        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        table.setVisible(true);
        table.right().bottom();
        stage.addActor(table);




        TextButton host = new TextButton("Host a game", skin);
        TextButton connect = new TextButton("Connect to an already existing game", skin);


        table.add(host).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(connect).fillX().uniformX();

        DialogTest dial = new DialogTest("Confirm Exit",skin);
        dial.hide();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parent.screenChoice(MainTest.MENU);
        }

        host.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
              //  stage.addActor(dial);
                table.setVisible(false);
                dial.show(stage);
            }
        });

        connect.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                table2.setVisible(true);
               // Popup test = new Popup(IP);
            }
        });

        okBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                String IP = hosting.getText();
            }
        });
        cancelBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {

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
            System.out.println("Chosen: " + object);
        }
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        parent.screenChoice(MainTest.MULTIPLAYER);
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
