package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.objects.text.Text;

public class Menu implements Screen {

    private MainTest parent; // a field to store our orchestrator
    private Stage stage;
    private Texture background;
    // our constructor with a Box2DTutorial argument
    public Menu(MainTest mainTest) {
        parent = mainTest;

        this.background = new Texture("Menu_background.jpg");

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);// setting the argument to our field.

    }

    public Stage getStage(){
        return stage;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        TextButton newLevel = new TextButton("new Level", skin);
        TextButton editor = new TextButton("play Editor", skin);
        TextButton exit = new TextButton("Exit", skin);
        TextButton settings = new TextButton("Settings", skin);
        TextButton multiplayer = new TextButton("Multiplayer", skin);

        table.add(newLevel).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(editor).fillX().uniformX();
        table.row();
        table.add(multiplayer).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(settings).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        newLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.screenChoice(MainTest.LEVEL);
            }
        });
        editor.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.screenChoice(MainTest.EDITOR);
            }
        });
        multiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.screenChoice(MainTest.MULTIPLAYER);
            }
        });
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.screenChoice(MainTest.SETTINGS);
            }
        });
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        parent.screenChoice(MainTest.MENU);
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
        this.background.dispose();
    }
}