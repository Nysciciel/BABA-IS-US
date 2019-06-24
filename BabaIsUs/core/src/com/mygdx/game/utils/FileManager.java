package com.mygdx.game.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.Level;
import com.mygdx.game.Location;
import com.mygdx.game.objects.Baba;
import com.mygdx.game.objects.Empty;
import com.mygdx.game.objects.Item;
import com.mygdx.game.objects.Keke;
import com.mygdx.game.objects.Rock;
import com.mygdx.game.objects.Skull;
import com.mygdx.game.objects.Wall;
import com.mygdx.game.objects.Water;
import com.mygdx.game.rule.RuleSet;

public class FileManager {
	
	public static boolean SaveLevel(Level level) {
		
		FileHandle file  = Gdx.files.local("level.txt");
		
		Location loc = null;
		String chaine = "";
		
		int length = level.getMatrixLength();
		int height = level.getMatrixHeight();
		
		chaine = chaine + level.getMatrixLength() + " " + level.getMatrixHeight() + "\n";
		
		for(int i=0 ; i<height ; i++) {
			for(int j=0 ; j<length ; j++) {
				loc = level.getLocationMatrix()[i][j];
				for(Item item : loc.getItems()) {
					chaine = chaine + item.getClass().getName() + item.getOrientation() + " ";
				}
				chaine = chaine + ",";
			}
			chaine = chaine + "\n";
		}
		
		file.writeString(chaine, false);
		return false;		
	}
	
	public static Level loadLevel(String fileName){
		return null;
	}
		
		
}
