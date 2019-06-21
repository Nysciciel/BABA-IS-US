package com.mygdx.game.Test.view;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Location;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.objects.Baba;
import com.mygdx.game.objects.Item;
import com.mygdx.game.utils.ObjectList;

public class EditorView implements Screen {

    private MainTest parent; // a field to store our orchestrator
    private Stage stage;
    private DrawEditor editor;
    private ObjectList selectedItem;
    private Table mainList;
    private Table charaList;
    private Table objectList;
    private Table textList;
    
    private TextButton charaSelect;
    private TextButton textSelect;
    private TextButton objectSelect;
    
    private TextButton babaSelect;
    private TextButton kekeSelect;
    
    private TextButton rockSelect;
    private TextButton waterSelect;
    
    private TextButton babaTextSelect;
    private TextButton rockTextSelect;
    
    Table table;

    // our constructor with a Box2DTutorial argument
    public EditorView(MainTest mainTest) {
    		

        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        this.editor = new DrawEditor(10,10);
        selectedItem = ObjectList.BABA;
        
        Gdx.input.setInputProcessor(stage);
        
        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        
        mainList = new Table();
        charaList = new Table();
        objectList = new Table();
        textList = new Table();
        
        
        charaSelect = new TextButton("Chara",skin);
        objectSelect = new TextButton("Obj",skin);
        textSelect = new TextButton("Text",skin);
        
        babaSelect = new TextButton("baba",skin);
        kekeSelect = new TextButton("keke",skin);
        
        rockSelect = new TextButton("rock",skin);
        waterSelect = new TextButton("water",skin);
        
        babaTextSelect = new TextButton("babatext",skin);
        rockTextSelect = new TextButton("rocktext",skin);
        
        mainList.add(charaSelect).fill();
        mainList.add(objectSelect).fill();
        mainList.add(textSelect).fill();
        
        charaList.add(babaSelect);
        charaList.add(kekeSelect);
        
        textList.add(babaTextSelect);
        textList.add(rockTextSelect);
        
        objectList.add(rockSelect);
        objectList.add(waterSelect);
        
        table.add(mainList);
        table.row().pad(0, 0, 10, 0);
        table.add(charaList);
        table.row().pad(10, 10, 10, 0);
        table.add(editor).expand().colspan(3).fill();
        
        
        stage.addActor(table);
        
        editor.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		System.out.println(selectedItem);
        		editor.setItem(selectedItem, (int) (x/32), (int)(y/32), 0 );
        		return true;
        	}
        	
        });
        
        mainList.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.substring(nom.indexOf(" ")+1);
        		switch(nom) {
        		case "Chara":
        			table.clear();
        			table.add(mainList);
        		    table.row().pad(0, 0, 10, 0);
        		    table.add(charaList);
        		    table.row().pad(10, 10, 10, 0);
        		    table.add(editor).expand().colspan(3).fill();
        			break;
        		case "Obj":
        			table.clear();
        			table.add(mainList);
        		    table.row().pad(0, 0, 10, 0);
        		    table.add(objectList);
        		    table.row().pad(10, 10, 10, 0);
        		    table.add(editor).expand().colspan(3).fill();
        			break;
        		case "Text":
        			table.clear();
        			table.add(mainList);
        		    table.row().pad(0, 0, 10, 0);
        		    table.add(textList);
        		    table.row().pad(10, 10, 10, 0);
        		    table.add(editor).expand().colspan(3).fill();
        			break;
        		}
        		return true;
        	}
        	
        });
        
        charaList.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.substring(nom.indexOf(" ")+1);
        		switch(nom) {
        		case "baba":
        			selectedItem = ObjectList.BABA;
        			break;
        		case "keke":
        			//selectedItem = ObjectList.KEKE;
        			break;
        		}
        		return true;
        	}
        	
        });
        
        objectList.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.substring(nom.indexOf(" ")+1);
        		switch(nom) {
        		case "rock":
        			selectedItem = ObjectList.ROCK;
        			break;
        		case "water":
        			//selectedItem = ObjectList.KEKE;
        			break;
        		}
        		return true;
        	}
        	
        });
        
        textList.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.substring(nom.indexOf(" ")+1);
        		switch(nom) {
        		case "babatext":
        			
        			break;
        		case "rocktext":
        			//selectedItem = ObjectList.KEKE;
        			break;
        		}
        		return true;
        	}
        	
        });
        
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parent.screenChoice(MainTest.MENU);
        }   
        
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
        parent.screenChoice(MainTest.EDITOR);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
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
    
    public Stage getStage() {
    	return stage;
    }

}