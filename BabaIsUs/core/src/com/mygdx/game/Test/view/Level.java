package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Test.Main.MainTest;
//import com.mygdx.game.states.MainMenu;

public class Level implements Screen {

    private MainTest parent; // a field to store our orchestrator
    private com.mygdx.game.Level lvl;
    private Stage stage;

    // our constructor with a Box2DTutorial argument
    public Level(MainTest mainTest) {

        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        this.lvl = new com.mygdx.game.Level("level.txt");
        Gdx.input.setInputProcessor(stage);
    }

    public Stage getStage(){
        return stage;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
            lvl.endturn();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.Z)){
            lvl.rollback();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            lvl.moveYou(2);
            lvl.endturn();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            lvl.moveYou(1);
            lvl.endturn();

        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            lvl.moveYou(0);
            lvl.endturn();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            lvl.moveYou(3);
            lvl.endturn();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parent.screenChoice(MainTest.MENU);
        }
        lvl.update();
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        parent.screenChoice(MainTest.LEVEL);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.getBatch().begin();
        lvl.draw(stage.getBatch());
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