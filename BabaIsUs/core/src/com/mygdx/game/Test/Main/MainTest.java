package com.mygdx.game.Test.Main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Test.view.*;

public class MainTest extends Game {

    private Menu menu;
    private EditorView editorView;
    private LevelView levelView;
    private ServerView server;
    private ClientView client;
    private Settings settings;
    private MultiplayerView multiplayerView;
   // private EndScreen endScreen;

    public final static int MENU = 0;
    public final static int EDITOR = 1;
    public final static int LEVEL = 2;
    public final static int SETTINGS = 3;
    public final static int MULTIPLAYER =4;
    public final static int SERVER = 5;
    public final static int CLIENT = 6;
    public static String ip_addr;
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
                if(editorView == null) editorView = new EditorView(this);
                this.setScreen(editorView);
                Stage stage2 = editorView.getStage();
                Gdx.input.setInputProcessor(stage2);
                break;
            case LEVEL:
                if(levelView == null) levelView = new LevelView(this);
                this.setScreen(levelView);
                Stage stage3 = levelView.getStage();
                Gdx.input.setInputProcessor(stage3);
                break;
            case MULTIPLAYER:
                if(multiplayerView == null) multiplayerView = new MultiplayerView(this);
                this.setScreen(multiplayerView);
                Stage stage4 = multiplayerView.getStage();
                Gdx.input.setInputProcessor(stage4);
                break;
            case SETTINGS:
                if(settings == null) settings = new Settings(this);
                this.setScreen(settings);
                Stage stage5 = settings.getStage();
                Gdx.input.setInputProcessor(stage5);
                break;
            case SERVER:
                if(server == null) server = new ServerView(this);
                this.setScreen(server);
                Stage stage6 = server.getStage();
                Gdx.input.setInputProcessor(stage6);
                break;
            case CLIENT:
                if(client == null) client = new ClientView(this,ip_addr);
                this.setScreen(client);
                Stage stage7 = client.getStage();
                Gdx.input.setInputProcessor(stage7);
                break;
        }
    }
}