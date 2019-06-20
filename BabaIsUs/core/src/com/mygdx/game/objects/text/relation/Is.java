package com.mygdx.game.objects.text.relation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.text.Relation;
import com.mygdx.game.utils.Constants;

public class Is extends Relation {

	public Is(Location loc, int x, int y, int orientation) {
		super(loc, x, y, orientation);
		textureAtlas = new TextureAtlas(Gdx.files.internal("RockSheet.txt"));
	}

	public String[] getSpriteUsed(){
		String[] spriteUsed = new String[2];
		spriteUsed[0]="Rock0";
		spriteUsed[1]="Rock1";
		return(spriteUsed);
	}

	public float[] getAffichePos(){
		float[] tab = {x,y};
		return(tab);
	}
	@Override
	public boolean isIs() {
		return true;
	}

}
