/*package com.mygdx.game.states;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.managers.GameStateManager;
import com.mygdx.game.*;
import com.mygdx.game.utils.MyTextInputListener;


public class EditorState extends GameState{

    private EditorView edt;
    private LevelView lvl;


    protected EditorState(GameStateManager gms) {
        super(gms);
        //MyTextInputListener listener = new MyTextInputListener();
        //Gdx.input.getTextInput(listener, "Dimension du niveau à éditer", "10,10", "");
        Gdx.input.getTextInput(new Input.TextInputListener() {
            private int hauteur;
            private int largeur;
            private String[] result;
            @Override
            public void input(String text) {
                result = text.split(",");
                this.hauteur = Integer.parseInt(result[0]);
                this.largeur = Integer.parseInt(result[1]);
                edt = new EditorView(hauteur,largeur);
            }

            @Override
            public void canceled() {

            }
        }, "Dimension du niveau à éditer", "10,10", "");
        this.edt = new EditorView("level.txt");
    }


    @Override
    protected void handleInput() {
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            this.gsm.set(new MainMenu(gsm));
        }
    }

    @Override
    public void update(float dt) {
        this.handleInput();
        edt.update();
    }

    @Override
    public void render(SpriteBatch sb) {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        sb.begin();
        edt.draw(sb);


        sb.end();
    }

    @Override
    public void dispose() {

        edt.dispose();
    }

}*/

