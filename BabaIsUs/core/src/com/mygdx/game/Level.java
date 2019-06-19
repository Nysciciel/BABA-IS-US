package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
<<<<<<< HEAD

import com.badlogic.gdx.graphics.g2d.Batch;
=======
>>>>>>> 36aa44934f51c5751e865c8507c038cdc03026f0
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.objects.*;
import com.badlogic.gdx.scenes.scene2d.*;

import java.io.File;

public class Level {
<<<<<<< HEAD

	private ArrayList<Item>[][] items;
=======
	private Location[][] locationMatrix;
>>>>>>> 36aa44934f51c5751e865c8507c038cdc03026f0
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

<<<<<<< HEAD
	public Level(int hauteur, int largeur) {
		this.height = hauteur;
		this.length = largeur;

		items = new ArrayList[height][length];

		for (int index1 = 0; index1 < height; index1++) {
			for (int index2 = 0; index2 < length; index2++) {
				items[height - 1 - index1][index2] = new ArrayList<Item>();
				items[height - 1 - index1][index2].add(new Empty(this, index2, height - 1 - index1, 0));
			}
		}
	}
	
=======
>>>>>>> 36aa44934f51c5751e865c8507c038cdc03026f0
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
<<<<<<< HEAD
	
	public void draw(Batch sb) {
		for (int index1 = 0; index1<length;index1++) {
			for (int index2 = 0; index2<height;index2++) {
				for (int index3 = 0; index3<items[index2][index1].size();index3++) {
					Item item = items[index2][index1].get(index3);
					sb.draw(item.getTexture(), item.getPosition().x*32, item.getPosition().y*32);
				}
=======

	public void draw(SpriteBatch sb) {
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].draw(sb);
>>>>>>> 36aa44934f51c5751e865c8507c038cdc03026f0
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
