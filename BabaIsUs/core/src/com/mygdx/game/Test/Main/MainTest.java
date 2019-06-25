package com.mygdx.game.Test.Main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Level;
import com.mygdx.game.ServerLevel;
import com.mygdx.game.Test.view.*;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class MainTest extends Game {

    private Menu menu;
    private EditorView editorView;
    private LevelView levelView;
    private ServerView server;
    private ClientView client;
    private Settings settings;
    private MultiplayerView multiplayerView;
    private LoadingView loading;
    private boolean reloaded;
   // private EndScreen endScreen;
    private ServerThread thread;
    private BlockingQueue<Integer> data;
    private ServerLevel level;

    public final static int MENU = 0;
    public final static int EDITOR = 1;
    public final static int LEVEL = 2;
    public final static int SETTINGS = 3;
    public final static int MULTIPLAYER =4;
    public final static int SERVER = 5;
    public final static int CLIENT = 6;
    public final static int LOADING = 7;
    public static String ip_addr = null;
  //  public static ServerThread thread;
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
                if(levelView != null) levelView.setLvl(new Level("Level.txt"));
                if(multiplayerView != null) multiplayerView.setStage(new Stage(new ScreenViewport()));
                if(!reloaded) {
                    loading = new LoadingView(this) ;
                    reloaded = true;
                }
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
                this.thread = loading.getThread();
                this.data = loading.getData();
                this.level = loading.getSlvl();
                if(server == null) server = new ServerView(this,thread,data);
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
            case LOADING:
                System.out.println("je suis en loading");
                reloaded = false;
                if(loading == null) loading = new LoadingView(this);
                this.setScreen(loading);
                Stage stage8 = loading.getStage();
                Gdx.input.setInputProcessor(stage8);
                break;
        }
    }
}