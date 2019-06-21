package com.mygdx.game.utils;

import com.badlogic.gdx.Input;
import com.mygdx.game.Level;
//import com.mygdx.game.managers.GameStateManager;
//import com.mygdx.game.states.EditorState;

import java.io.PrintWriter;

public class MyTextInputListener implements Input.TextInputListener {
  //  private GameStateManager gsm;
    private int hauteur;
    private int largeur;
    private String[] result;
    public MyTextInputListener(){
        super();
      //  this.gsm =gsm;
    }
    @Override
    public void input (String text) {
        result = text.split(",");
        this.hauteur = Integer.parseInt(result[0]);
        this.largeur = Integer.parseInt(result[1]);
    }

    @Override
    public void canceled () {

    }

    public String[] getText(){
        return result;
    }

    public int getHauteur(){
        return hauteur;
    }

    public int getLargeur(){
        return largeur;
    }
}

