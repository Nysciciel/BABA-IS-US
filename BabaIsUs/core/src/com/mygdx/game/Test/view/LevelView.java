package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Level;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.client_serveur.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
//import com.mygdx.game.states.MainMenu;

public class LevelView implements Screen,ServerCallBack {

    private MainTest parent; // a field to store our orchestrator
    private Level lvl;
    private Stage stage;
    private Client client;
    private Server server;
    private BlockingQueue<Integer> data;
    private Table table;


    public LevelView(MainTest mainTest) {

        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        table = new Table();
        table.setFillParent(true);


        this.lvl = new Level("level.txt");
        Gdx.input.setInputProcessor(stage);
        
        stage.addActor(table);
        table.add(lvl).expand().fill();
    }


    public Stage getStage(){
        return stage;
    }

    @Override
    public void show() {
        if(lvl == null){
            lvl = new com.mygdx.game.Level("level.txt");
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            lvl.endturn();
            System.out.println(lvl.hashCode());
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            lvl.rollback();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            lvl.reset();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            lvl.moveYou1(2);
            lvl.endturn();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            lvl.moveYou1(1);
            lvl.endturn();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            lvl.moveYou1(0);
            lvl.endturn();

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            lvl.moveYou1(3);
            lvl.endturn();

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parent.screenChoice(MainTest.MENU);
        }
    }

    public Level getLvl(){
        return lvl;
    }

    public void setLvl(Level nlvl){
       this.lvl = nlvl;
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        parent.screenChoice(MainTest.LEVEL);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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

    @Override
    public void dataReceived(int data) {
        System.out.println(data);
    }
}