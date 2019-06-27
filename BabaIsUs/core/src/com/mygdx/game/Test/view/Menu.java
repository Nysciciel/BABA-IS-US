package com.mygdx.game.Test.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private TextureAtlas textureAtlas;
    private Animation animation;
    private TextureAtlas textureAtlas2;
    private Animation animation2;
    private Texture texture;
    private Skin skin;

    // our constructor with a Box2DTutorial argument
    public Menu(MainTest mainTest) {
        parent = mainTest;

        textureAtlas = new TextureAtlas(Gdx.files.internal("BackgroundMenu.txt"));
        animation = new Animation(2/3f, textureAtlas.getRegions());
        textureAtlas2 = new TextureAtlas(Gdx.files.internal("NightSheet.txt"));
        animation2 = new Animation(2/3f, textureAtlas2.getRegions());
        texture = new Texture(Gdx.files.internal("BabaIsUsTitle.png"));

        skin = new Skin(Gdx.files.internal("Skins/freezing-ui.json"));

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
        table.pad(200,0,0,0);
        stage.addActor(table);



        TextButton newLevel = new TextButton("Play", skin);
        TextButton editor = new TextButton("Map Editor", skin);
        TextButton exit = new TextButton("Exit", skin);
        TextButton settings = new TextButton("Settings", skin);
        TextButton multiplayer = new TextButton("Multiplayer", skin);

        newLevel.setTransform(true);
        newLevel.setScale(1.3f);
        editor.setTransform(true);
        editor.setScale(1.3f);
        exit.setTransform(true);
        exit.setScale(1.3f);
        settings.setTransform(true);
        settings.setScale(1.3f);
        multiplayer.setTransform(true);
        multiplayer.setScale(1.3f);

        table.add(newLevel).uniformX().fillX();
        table.row().pad(20 , 0, 20, 0);
        table.add(multiplayer).uniformX().fillX();
        table.row();
        table.add(editor).fillX().uniformX().fillX();
        table.row().pad(20, 0, 20, 0);
        table.add(settings).fillX().uniformX().fillX();
        table.row();
        table.add(exit).fillX().uniformX().fillX();

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        newLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.screenChoice(MainTest.LEVELSELECT,null);
            }
        });
        editor.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.screenChoice(MainTest.EDITORSELECT,null);
            }
        });
        multiplayer.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.screenChoice(MainTest.MULTIPLAYER,null);
            }
        });
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                parent.screenChoice(MainTest.SETTINGS,null);
            }
        });
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        parent.screenChoice(MainTest.MENU,null);
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
        stage.getBatch().draw(texture,(Gdx.graphics.getWidth()-740)/2,Gdx.graphics.getHeight()-800);
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
        texture.dispose();
    }
}