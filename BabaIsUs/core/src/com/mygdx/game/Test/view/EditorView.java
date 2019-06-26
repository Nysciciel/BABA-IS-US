package com.mygdx.game.Test.view;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Location;
import com.mygdx.game.Test.Main.MainTest;
import com.mygdx.game.objects.Baba;
import com.mygdx.game.objects.Item;
import com.mygdx.game.utils.FileManager;
import com.mygdx.game.utils.ItemTypeList;
import com.mygdx.game.utils.ObjectList;

public class EditorView implements Screen {

    private MainTest parent; // a field to store our orchestrator
    private Stage stage;
    private DrawEditor editor;
    private ObjectList selectedItem;
    private int selectedOrientation;
    
    private Table mainList;
    private Table propertyList;
    private Table relationList;
    private Table operatorList;
    private Table objectList;
    private Table item_textList;
    
 
    private TextButton clearSelect;
    private TextButton resetSelect;
    private TextButton saveSelect;
    
    private Slider sliderW;
    private Slider sliderH;
 
    private String file;
    
    private Table table;

    // our constructor with a Box2DTutorial argument
    public EditorView(MainTest mainTest, String fileName) {
    		
    	this.file = fileName;
        parent = mainTest;     // setting the argument to our field.
        stage = new Stage(new ScreenViewport());
        this.editor = new DrawEditor(10,10,file);
        selectedItem = ObjectList.BABA;
        selectedOrientation = 0;
        
        Gdx.input.setInputProcessor(stage);
        
        table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);
        
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        
        mainList = new Table();
        propertyList = new Table();
        item_textList = new Table();
        objectList = new Table();
        operatorList = new Table();
        relationList = new Table();
        
        
        clearSelect = new TextButton("clear",skin);
        resetSelect = new TextButton("reset",skin);
        saveSelect = new TextButton("save",skin);
        
        sliderW = new Slider(5,20,1,true,skin);
        sliderW.setValue(10);
        sliderH = new Slider(5,20,1,true,skin);
        sliderH.setValue(10);
        
        mainList.left();
        for(ItemTypeList object : ItemTypeList.values()) {
        	mainList.add(new TextButton(object.toString(),skin)).fill().width(100).uniformX();
        }
        mainList.add(clearSelect).expandX().right();
        mainList.add(resetSelect).fill().uniformX();
        mainList.add(saveSelect).fill().uniformX();
        
        
        
        for(ObjectList object : ObjectList.values()) {
        	switch(object.getItemType()) {
        	case ITEM_TEXT:
        		item_textList.add(new TextButton(object.toString(),skin)).fill().uniformX();
        		break;
        	case OPERATOR:
        		operatorList.add(new TextButton(object.toString(),skin)).fill().uniformX();
        		break;
        	case OBJECT:
        		objectList.add(new TextButton(object.toString(),skin)).fill().uniformX();
        		break;
        	case PROPERTY:
        		propertyList.add(new TextButton(object.toString(),skin)).fill().uniformX();
        		break;
        	case RELATION:
        		relationList.add(new TextButton(object.toString(),skin)).fill().uniformX();
        		break;
        	}
        }
        
        table.add(mainList).left().expandX().fill().colspan(2);
        table.row().pad(0, 0, 10, 0);
        table.add(objectList).left().expandX();
        table.row().pad(0, 0, 0, 0).right();
        table.add(editor).expand().fill();

        table.add(sliderW).fill().width(50);
        table.add(sliderH).fill().width(50);
        
        stage.addActor(table);
        
        editor.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		editor.setItem(selectedItem, (int) (x), (int)(y), selectedOrientation );
        		return true;
        	}
        	
        });
        
        stage.addListener(new InputListener() {
        	public boolean scrolled(InputEvent event,
                    float x,
                    float y,
                    int amount) {
         
            selectedOrientation = (selectedOrientation + amount)%4;
            if(selectedOrientation<0) selectedOrientation += 4;
            return true;
        }
        	
        });
        
        mainList.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.split(" ")[nom.split(" ").length-1];
        		if(nom.equals("clear")) {
        			selectedItem = ObjectList.EMPTY;
        		} else if(nom.equals("reset")) {
        			editor.clear();
        		}else if(nom.equals("save")) {
        			FileManager.SaveLevel(editor.getLevel(),"Level/"+file);
        			parent.screenChoice(MainTest.MENU, null);
        		}else {
        			
        			switch(ItemTypeList.valueOf(nom)) {
            		case PROPERTY:
            			table.clear();
            			table.add(mainList).left().expandX().fill().colspan(2);
            		    table.row().pad(0, 0, 10, 0);
            		    table.add(propertyList).left().expandX();
            		    table.row().pad(0, 0, 0, 0);
            		    table.add(editor).expand().colspan(3).fill();
            		    table.add(sliderW).fill().width(50);
            	        table.add(sliderH).fill().width(50);
            			break;
            		case RELATION:
            			table.clear();
            			table.add(mainList).left().expandX().fill().colspan(2);
            		    table.row().pad(0, 0, 10, 0);
            		    table.add(relationList).left().expandX();
            		    table.row().pad(0, 0, 0, 0);
            		    table.add(editor).expand().colspan(3).fill();
            		    table.add(sliderW).fill().width(50);
            	        table.add(sliderH).fill().width(50);
            			break;
            		case ITEM_TEXT:
            			table.clear();
            			table.add(mainList).left().expandX().fill().colspan(2);
            		    table.row().pad(0, 0, 10, 0);
            		    table.add(item_textList).left().expandX();
            		    table.row().pad(0, 0, 0, 0);
            		    table.add(editor).expand().colspan(3).fill();
            		    table.add(sliderW).fill().width(50);
            	        table.add(sliderH).fill().width(50);
            			break;
            		case OPERATOR:
            			table.clear();
            			table.add(mainList).left().expandX().fill().colspan(2);
            		    table.row().pad(0, 0, 10, 0);
            		    table.add(operatorList).left().expandX();
            		    table.row().pad(0, 0, 0, 0);
            		    table.add(editor).expand().colspan(3).fill();
            		    table.add(sliderW).fill().width(50);
            	        table.add(sliderH).fill().width(50);
            			break;
            		case OBJECT:
            			table.clear();
            			table.add(mainList).left().expandX().fill().colspan(2);
            		    table.row().pad(0, 0, 10, 0);
            		    table.add(objectList).left().expandX();
            		    table.row().pad(0, 0, 0, 0);
            		    table.add(editor).expand().colspan(3).fill();
            		    table.add(sliderW).fill().width(50);
            	        table.add(sliderH).fill().width(50);
            			break;
        		}
     
        		}
        		return true;
        	}
        	
        });
        
        objectList.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.split(" ")[nom.split(" ").length-1];
        		System.out.println(nom);
        		selectedItem = ObjectList.valueOf(nom);
        		return true;
        	}
        	
        });
        
        propertyList.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.split(" ")[nom.split(" ").length-1];
        		System.out.println(nom);
        		selectedItem = ObjectList.valueOf(nom);
        		return true;
        	}
        	
        });
        
        operatorList.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.split(" ")[nom.split(" ").length-1];
        		System.out.println(nom);
        		selectedItem = ObjectList.valueOf(nom);
        		return true;
        	}
        	
        });
        
        item_textList.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.split(" ")[nom.split(" ").length-1];
        		System.out.println(nom);
        		selectedItem = ObjectList.valueOf(nom);
        		return true;
        	}
        	
        });
        
        relationList.addListener(new InputListener() {
        	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        		String nom = event.getTarget().toString();
        		nom = nom.split(" ")[nom.split(" ").length-1];
        		System.out.println(nom);
        		selectedItem = ObjectList.valueOf(nom);
        		return true;
        	}
        	
        });
        
        sliderW.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				editor.setLarg((int) sliderW.getValue());;
			}      	
        });
        
        sliderH.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				editor.setHaut((int) sliderH.getValue());;		
			}      	
        });
        
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
         
        
        
    }

    @Override
    public void render(float delta) {
        // TODO Auto-generated method stub
    	if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            parent.screenChoice(MainTest.MENU, null);
        }  
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 3f));
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