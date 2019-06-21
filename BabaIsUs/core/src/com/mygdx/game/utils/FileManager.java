package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.Item;

public class FileManager {
	
	public static boolean SaveLevel(Level level) {
		
		FileHandle file  = Gdx.files.local("test.txt");
		
		Location loc = null;
		String chaine = "";
		
		int length = level.getLength();
		int height = level.getHeight();
		
		for(int i=0 ; i<height ; i++) {
			for(int j=0 ; j<length ; j++) {
				loc = level.getLocation(i, j);
				for(Item item : loc.getItems()) {
					chaine = chaine + item.toString();
				}
				chaine = chaine + " ";
			}
			chaine = chaine + "\n";
		}
		
		file.writeString(chaine, false);
		
		return false;		
	}
}
