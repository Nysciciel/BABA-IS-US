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
		
		ArrayList<RuleStack> currentRules; 
		
		// lecture par ligne
		for (int y = 0; y<height; y++) {
			currentRules = new ArrayList<RuleStack>();
			for (int x = 0; x<length; x++) {

			}
		}
		//lecture par colonne
		for (int index1 = 0; index1<length; index1++) {
			for (int index2 = 0; index2<height; index2++) {

			}
		}
	}





	public ArrayList<Location> prioritySort(ArrayList<Location> list, int direction){


		if (list.size()==1) {
			return list;
		}
		Location first = null;
		for(Location i:list) {
			if (i.next(direction)!=null) {
				if(!(i.next(direction).hasYou()) || (list.indexOf(i.next(direction))==-1)) {
					first = i;

					break;
				}
			}
		}

		if (first==null) {
			first = list.get(0);
		}

		list.remove(first);
		ArrayList<Location> beginning = new ArrayList<Location>();
		beginning.add(first);
		beginning.addAll(prioritySort(list, direction));
		return beginning;
	}



	public void moveYou(int direction) {
		ArrayList<Item> toMove = new ArrayList<Item>();
		ArrayList<Location> found = new ArrayList<Location>();
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				if (locationMatrix[y][x].hasYou()) {
					found.add(locationMatrix[y][x]);
				}
			}
		}





		System.out.println("");

		found = prioritySort(found, direction);


		for(Location i:found) {
			ArrayList<Item> res = i.moveYou(direction);
			if (res!=null) {
				for(Item j:res) {
					j.goforward();
				}
			}
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

	public void render(SpriteBatch sb) {
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].render(sb);
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
