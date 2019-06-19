package com.mygdx.game.Test.Main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Test.view.*;

public class MainTest extends Game {

    private Menu menu;
    private Editor editor;
    private Level level;
    private Settings settings;
    private Multiplayer multiplayer;
   // private EndScreen endScreen;

    public final static int MENU = 0;
    public final static int EDITOR = 1;
    public final static int LEVEL = 2;
    public final static int SETTINGS = 3;
    public final static int MULTIPLAYER =4;
 //   public final static int ENDGAME = 3;
    @Override
    public void create() {
        menu = new Menu(this);
        setScreen(menu);
    }

    public void screenChoice(int screen){
        switch(screen){
            case MENU:
                if(menu == null) menu = new Menu(this);
                this.setScreen(menu);
                Stage stage = menu.getStage();
                Gdx.input.setInputProcessor(stage);
                break;
            case EDITOR:
                if(editor == null) editor = new Editor(this);
                this.setScreen(editor);
                Stage stage2 = editor.getStage();
                Gdx.input.setInputProcessor(stage2);
                break;
            case LEVEL:
                if(level == null) level = new Level(this);
                this.setScreen(level);
                Stage stage3 = level.getStage();
                Gdx.input.setInputProcessor(stage3);
                break;
            case MULTIPLAYER:
                if(multiplayer == null) multiplayer = new Multiplayer(this);
                this.setScreen(multiplayer);
                Stage stage4 = multiplayer.getStage();
                Gdx.input.setInputProcessor(stage4);
                break;
            case SETTINGS:
                if(settings == null) settings = new Settings(this);
                this.setScreen(settings);
                Stage stage5 = settings.getStage();
                Gdx.input.setInputProcessor(stage5);
                break;
        }
    }
}