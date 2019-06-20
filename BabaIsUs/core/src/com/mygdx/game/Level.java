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

	private ArrayList<Location[][]> history;

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
			history = new ArrayList<Location[][]>();


			locationMatrix = new Location[height][length];
			for (int y = 0; y<height; y++) {
				for (int x = 0; x<length; x++) {
					ArrayList<Item> items = new ArrayList<Item>();
					Location loc = new Location(items, this, x, height - 1 -y);
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
					case 'a':
						loc.add(new Water(loc, x, height - 1 -y,0));
						break;
					case 'k':
						loc.add(new Keke(loc, x, height - 1 -y,0));
						break;
					case 's':
						loc.add(new Skull(loc, x, height - 1 -y,0));
						break;
					}
					locationMatrix[height - 1 -y][x] = loc;
				}
			}

			history.add(this.matrixCopy());


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

		if (list.size()==0) {
			return null;
		}

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
		ArrayList<Location> found = new ArrayList<Location>();
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				if (locationMatrix[y][x].hasYou()) {
					found.add(locationMatrix[y][x]);
				}
			}
		}

		found = prioritySort(found, direction);

		if (found != null) {
			for(Location i:found) {
				ArrayList<Item> res = i.move(direction);
				if (res!=null) {
					for(Item j:res) {
						j.goforward();
					}
				}
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


	public void endturn() {
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].checkDeaths();
			}
		}
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].checkMove();
			}
		}
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].checkDeaths();
			}
		}
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].checkWin();
			}
		}
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].reset();
			}
		}
		history.add(this.matrixCopy());
	}

	public void rollback() {
		if (history.size()>1) {
			locationMatrix = history.get(history.size()-2);
			locationMatrix = this.matrixCopy();
			history.remove(history.size()-1);
		}
	}

	public Location[][] getLocationMatrix(){
		return locationMatrix;
	}

	public Location[][] matrixCopy(){
		Location[][] matrix = new Location[height][length];
		for(int y=0; y < height; y++) {
			for(int x=0; x < length; x++) {
				matrix[y][x] = locationMatrix[y][x].copy();
			}
		}
		return matrix;
	}

	public void reset() {
		locationMatrix = history.get(0);
		history = new ArrayList<Location[][]>();
		history.add(this.matrixCopy());
	}

}
