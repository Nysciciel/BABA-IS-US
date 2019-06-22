package com.mygdx.game;

import java.util.ArrayList;
import java.util.Scanner;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.objects.*;

import com.mygdx.game.objects.text.Text;
import com.mygdx.game.objects.text.item_ref.*;
import com.mygdx.game.objects.text.property.*;
import com.mygdx.game.objects.text.operator.*;
import com.mygdx.game.objects.text.relation.*;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.Rule;
import com.mygdx.game.rule.RuleSet;
import com.mygdx.game.rule.RuleStackList;


import java.io.File;

public class Level {

	private Location[][] locationMatrix;
	private int height;
	private int length;
	private RuleSet rules;
	private LogicHashtable ruleTable;
	private ArrayList<Class> props;

	private ArrayList<Location[][]> history;
	
	public Level(int length,int height) {
		this.height = height;
		this.length = length;
		this.rules = new RuleSet();
		
		locationMatrix = new Location[height][length];
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < length ; j++) {
				ArrayList<Item> items = new ArrayList<Item>();
				locationMatrix[i][j] = new Location(items,this,i,j);
				items.add(new Empty(locationMatrix[i][j],0));
			}
		}
	}

	public Level(String filename) {
		
		props = new ArrayList<Class>();
		
		props.add(Empty.class);
		props.add(Baba.class);props.add(Keke.class);props.add(Rock.class);props.add(Wall.class);props.add(Water.class);props.add(Water.class);props.add(Skull.class);
		this.ruleTable = new LogicHashtable();
		
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
			this.rules = new RuleSet();
			//System.out.println(length);
			//System.out.println(height);

			
			locationMatrix = new Location[height][length];
			for (int y = 0; y<height; y++) {
				for (int x = 0; x<length; x++) {
					ArrayList<Item> items = new ArrayList<Item>();
					Location loc = new Location(items, this, x, height - 1 -y);
					switch(lines.get(y).charAt(x)) {
					case 'e':
						loc.add(new Empty(loc, 0));
						break;
					case 'b':
						loc.add(new Baba(loc, 0));
						break;
					case 'w':
						loc.add(new Wall(loc, 0));
						break;
					case 'r':
						loc.add(new Rock(loc, 0));
						break;
					case 'a':
						loc.add(new Water(loc, 0));
						break;
					case 'k':
						loc.add(new Keke(loc, 0));
						break;
					case 's':
						loc.add(new Skull(loc, 0));
						break;
					}
					locationMatrix[height - 1 -y][x] = loc;
				}
			}
			
			locationMatrix[6][12].add(new Keke(locationMatrix[6][12], 0));
			locationMatrix[6][12].add(new Wall(locationMatrix[6][12], 0));
			
			// BABA IS YOU
			locationMatrix[0][0].add(new You(locationMatrix[0][0], 0));
			locationMatrix[1][0].add(new Is(locationMatrix[1][0], 0));
			locationMatrix[2][0].add(new BabaText(locationMatrix[2][0], 0));
			//locationMatrix[3][0].add(new Not(locationMatrix[3][0], 0));
			

			// WALL IS STOP
			locationMatrix[0][10].add(new Push(locationMatrix[0][10], 0));
			locationMatrix[1][10].add(new Is(locationMatrix[1][10], 0));
			locationMatrix[2][10].add(new WallText(locationMatrix[2][10], 0));

			// WATER IS SINK
			locationMatrix[0][12].add(new Sink(locationMatrix[0][12], 0));
			locationMatrix[1][12].add(new Is(locationMatrix[1][12], 0));
			locationMatrix[2][12].add(new WaterText(locationMatrix[2][12], 0));

			// SKULL ON BABA iS WALL
			locationMatrix[0][14].add(new Weak(locationMatrix[0][14], 0));
			locationMatrix[1][14].add(new Is(locationMatrix[1][14], 0));
			locationMatrix[2][14].add(new Wall(locationMatrix[2][14], 0));
			locationMatrix[3][14].add(new Not(locationMatrix[3][14], 0));
			locationMatrix[4][14].add(new On(locationMatrix[4][14], 0));
			locationMatrix[5][14].add(new KekeText(locationMatrix[5][14], 0));

			// SKULL IS HOT AND PULL
			locationMatrix[0][16].add(new Pull(locationMatrix[0][16], 0));
			locationMatrix[1][16].add(new And(locationMatrix[1][16], 0));
			//locationMatrix[2][16].add(new Hot(locationMatrix[2][16], 0));
			locationMatrix[3][16].add(new Is(locationMatrix[3][16], 0));
			locationMatrix[4][16].add(new SkullText(locationMatrix[4][16], 0));



			history.add(this.matrixCopy());

			updateRules();


		}
		catch(Exception e) {
			System.out.println("Error while loading level");
			e.printStackTrace();
		}



	}
	
	public Location getLocation(int x, int y) {
		return locationMatrix[y][x];
	}
	/*
	public LevelView(int hauteur, int largeur) {
		this.height = hauteur;
		this.length = largeur;

		items = new ArrayList[height][length];

		for (int index1 = 0; index1 < height; index1++) {
			for (int index2 = 0; index2 < length; index2++) {
				items[height - 1 - index1][index2] = new ArrayList<Item>();
				items[height - 1 - index1][index2].add(new Empty(this, index2, height - 1 - index1, 0));
			}
		}
	}*/

	public void readRules() {
		
		rules = new RuleSet();
				
		RuleStackList currentRules; 
		boolean thereIsAnOnOrNearOrFacingOrAnd;
		boolean thereIsANot;

		// lecture par ligne
		for (int y = height-1; y>=0; y--) {
			
			thereIsAnOnOrNearOrFacingOrAnd = false;
			thereIsANot = false;
			currentRules = new RuleStackList(rules);
			for (int x = 0; x<length; x++) {
				ArrayList<Text> textList = locationMatrix[y][x].giveTextItems();
				
				
				currentRules.buildNext(textList, thereIsAnOnOrNearOrFacingOrAnd, thereIsANot);
				thereIsAnOnOrNearOrFacingOrAnd = locationMatrix[y][x].thereIsAOn() || locationMatrix[y][x].thereIsAAnd();
				thereIsANot = locationMatrix[y][x].thereIsANot();				
			}
			currentRules.buildNext(new ArrayList<Text>(), thereIsAnOnOrNearOrFacingOrAnd, thereIsANot);
		}
		//lecture par colonne
		for (int x = 0; x<length; x++) {
			thereIsAnOnOrNearOrFacingOrAnd = false;
			thereIsANot = false;
			currentRules = new RuleStackList(rules);
			for (int y = height-1; y>=0; y--) {
				ArrayList<Text> textList = locationMatrix[y][x].giveTextItems();
				
				currentRules.buildNext(textList, thereIsAnOnOrNearOrFacingOrAnd, thereIsANot);
				thereIsAnOnOrNearOrFacingOrAnd = locationMatrix[y][x].thereIsAOn() || locationMatrix[y][x].thereIsAAnd();
				thereIsANot = locationMatrix[y][x].thereIsANot();
			}
			currentRules.buildNext(new ArrayList<Text>(), thereIsAnOnOrNearOrFacingOrAnd, thereIsANot);
		}
	}
	

	public void interpretRules() {

		//System.out.println("################################## __Construction__    ################################################");
		ruleTable = new LogicHashtable(rules, props);
		//System.out.println("##################################  __RuleTable__    ################################################");
		System.out.println(ruleTable);
		//System.out.println(locationMatrix[2][0].getItems().get(0).getRuleTable());
		
	}
	
	public void updateRules() {
		
		highLight(false);
		readRules();
		highLight(true);
		interpretRules();		
	}

	private void highLight(boolean b) {
		for (Rule rule : rules) {
			for (Text text : rule.getTextList()) {
				text.highLight(b);
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
				if(!(i.next(direction).hasYou1() || i.next(direction).hasYou2()) || (list.indexOf(i.next(direction))==-1)) {
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

	public void moveYou1(int direction) {
		ArrayList<Location> found = new ArrayList<Location>();
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				if (locationMatrix[y][x].hasYou1()) {
					found.add(locationMatrix[y][x]);
				}
			}
		}

		found = prioritySort(found, direction);

		if (found != null) {
			for(Location i:found) {
				ArrayList<Item> res = i.move1(direction);
				if (res!=null) {
					for(Item j:res) {
						j.goforward();
					}
				}
			}
		}
	}
	
	public void moveYou2(int direction) {
		ArrayList<Location> found = new ArrayList<Location>();
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				if (locationMatrix[y][x].hasYou2()) {
					found.add(locationMatrix[y][x]);
				}
			}
		}

		found = prioritySort(found, direction);

		if (found != null) {
			for(Location i:found) {
				ArrayList<Item> res = i.move2(direction);
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


	public void render(Batch sb) {

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
		
		updateRules();
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].transform();
				locationMatrix[y][x].checkDeaths();
			}
		}
		updateRules();
		history.add(this.matrixCopy());
	}

	public void rollback() {
		if (history.size()>1) {
			locationMatrix = history.get(history.size()-2);
			locationMatrix = this.matrixCopy();
			history.remove(history.size()-1);
		}
		updateRules();
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
		updateRules();
	}
	
	public int getLength() {
		return this.length;
	}
	
	public int getHeight() {
		return this.height;
	}

	public LogicHashtable getRuleTable() {
		return ruleTable;
	}
}
