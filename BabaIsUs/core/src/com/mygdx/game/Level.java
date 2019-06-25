package com.mygdx.game;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.objects.*;

import com.mygdx.game.objects.text.Text;
import com.mygdx.game.objects.text.item_ref.BabaText;
import com.mygdx.game.objects.text.item_ref.WallText;
import com.mygdx.game.objects.text.item_ref.WaterText;
import com.mygdx.game.objects.text.operator.Not;
import com.mygdx.game.objects.text.property.Sink;
import com.mygdx.game.objects.text.property.Stop;
import com.mygdx.game.objects.text.property.You;
import com.mygdx.game.objects.text.property.You2;
import com.mygdx.game.objects.text.relation.Is;
import com.mygdx.game.objects.text.item_ref.*;
import com.mygdx.game.objects.text.property.*;
import com.mygdx.game.objects.text.operator.*;
import com.mygdx.game.objects.text.relation.*;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.Rule;
import com.mygdx.game.rule.RuleSet;
import com.mygdx.game.rule.RuleStackList;


import java.io.File;

public class Level extends Actor{

	private Location[][] locationMatrix;
	private int height;
	private int length;
	private RuleSet rules;
	private LogicHashtable ruleTable;
	private ArrayList<Class> props;

	private ArrayList<Location[][]> history;

	public Level(int length,int height) {
		super();
		this.height = height;
		this.length = length;
		this.rules = new RuleSet();

		locationMatrix = new Location[height][length];
		for(int i = 0 ; i < height ; i++) {
			for(int j = 0 ; j < length ; j++) {
				ArrayList<Item> items = new ArrayList<Item>();
				locationMatrix[i][j] = new Location(items,this,j,i);
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
			FileHandle file = Gdx.files.local(filename);
			Scanner scanner = new Scanner(file.read());
			ArrayList <String> lines = new ArrayList <String>();
			String[] taille = scanner.nextLine().split(" ");
			length = Integer.parseInt(taille[0]);
			height = Integer.parseInt(taille[1]);
			while(scanner.hasNext()) {
				lines.add(scanner.nextLine());
			}

			scanner.close();

			history = new ArrayList<Location[][]>();
			this.rules = new RuleSet();
			//System.out.println(length);
			//System.out.println(height);


			locationMatrix = new Location[height][length];

			for(int i=0;i<lines.size();i++) {
				String[] cell = lines.get(i).split(",");
				for(int j=0 ; j<cell.length ;j++) {
					String[] split = cell[j].split(" ");
					ArrayList<Item> items = new ArrayList<Item>();
					for(int k=0 ;k<split.length;k++) {
						locationMatrix[i][j] = new Location(items, this, j, i);
						locationMatrix[i][j].add((Item) Class.forName(split[k].substring(0, split[k].length()-1)).getConstructor(Location.class , int.class).newInstance(locationMatrix[i][j] , Integer.parseInt(split[k].substring(split[k].length()-1))));

					}
				}
			}



			history.add(this.matrixCopy());

			updateRules();


		}
		catch(Exception e) {
			System.out.println("Error while loading level");
			e.printStackTrace();
		}



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
		Location first = list.get(0);

		for(Location i:list) {
			if(direction == 0 && i.getX()<first.getX()) {
				first = i;
			}
			if(direction == 1 && i.getY()>first.getY()) {
				first = i;
			}
			if(direction == 2 && i.getX()>first.getX()) {
				first = i;
			}
			if(direction == 3 && i.getY()<first.getY()) {
				first = i;
			}
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


	public void render(Batch sb, float cellSize) {
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				locationMatrix[y][x].render(sb,cellSize);
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
				System.out.println(x);
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

	public int getMatrixLength() {
		return this.length;
	}

	public int getMatrixHeight() {
		return this.height;
	}

	public LogicHashtable getRuleTable() {
		return ruleTable;
	}

	@Override
    public void draw(Batch batch, float parentAlpha) {
    	super.draw(batch,parentAlpha);
    	this.render(batch,Math.min(this.getWidth()/length, this.getHeight()/height));

    }
}
