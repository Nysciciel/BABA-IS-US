package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Test.Main.MainTest;

public class Editor implements Screen {

    private MainTest parent; // a field to store our orchestrator
    private Stage stage;
    private com.mygdx.game.Editor edt;

    // our constructor with a Box2DTutorial argument
    public Editor(MainTest mainTest) {

        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        this.edt = new com.mygdx.game.Editor("level.txt");
        Gdx.input.setInputProcessor(stage);
    }

    public Stage getStage(){
        return stage;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parent.screenChoice(MainTest.MENU);
        }
        edt.update();
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        parent.screenChoice(MainTest.EDITOR);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.getBatch().begin();
        edt.draw(stage.getBatch());
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