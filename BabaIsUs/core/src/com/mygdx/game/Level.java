package com.mygdx.game;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.objects.*;

import java.io.File;

public class Level {
	private Location[][] locationMatrix;
	private int height;
	private int length;
	private HashSet<Rule> rules;

	public Level(String filename) {



		try {
			Scanner scanner = new Scanner(new File(filename));
			ArrayList <String> lines = new ArrayList <String>();
			while(scanner.hasNext()) {
				lines.add(scanner.nextLine());
			}

			scanner.close();



			height = lines.size();
			length = lines.get(0).length();


			locationMatrix = new Location[height][length];
			for (int y = 0; y<height; y++) {
				for (int x = 0; x<length; x++) {
					ArrayList<Item> items = new ArrayList<Item>();
					Location loc = new Location(items, locationMatrix, x, height - 1 -y);
					switch(lines.get(y).charAt(x)) {
					case 'e':
						loc.add(new Empty(loc, x, height - 1 -y,0));
						break;
					case 'b':
						loc.add(new Baba(loc, x, height - 1 -y,0));
						break;
					case 'w':
						loc.add(new Wall(loc, x, height - 1 -y,0));
						break;
					case 'r':
						loc.add(new Rock(loc, x, height - 1 -y,0));
						break;
					}
					locationMatrix[height - 1 -y][x] = loc;
				}
			}


		}
		catch(Exception e) {
			System.out.println("Error while loading level");
			e.printStackTrace();
		}


	}

	public void readRules() {
		// lecture par ligne
		for (int y = 0; y<height; y++) {
			for (int x = 0; x<length; x++) {

			}
		}
		//lecture par colonne
		for (int index1 = 0; index1<length; index1++) {
			for (int index2 = 0; index2<height; index2++) {

			}
		}
	}

	public void moveYou(int direction) {
		ArrayList<Item> toMove = new ArrayList<Item>();
		ArrayList<Item> found = new ArrayList<Item>();
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				found = locationMatrix[y][x].moveYou(direction);
				if(found != null) {
					for(Item i:found) {
						toMove.add(i);
					}
				}
			}
		}
		for(Item i:toMove) {
			i.goforward();
		}
	}

	public void update() {
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].update();
			}
		}
	}

	public void dispose() {
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].dispose();
			}
		}
	}

	public void draw(SpriteBatch sb) {
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].draw(sb);
			}
		}
	}

	public void reset() {
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].reset();
			}
		}
	}

}
