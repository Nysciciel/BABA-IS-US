package com.mygdx.game;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Scanner;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.objects.*;
import com.mygdx.game.objects.text.Text;
import com.mygdx.game.objects.text.item_ref.BabaText;
import com.mygdx.game.objects.text.property.You;
import com.mygdx.game.objects.text.relation.Is;
import com.mygdx.game.rule.Logic;
import com.mygdx.game.rule.LogicHashtable;
import com.mygdx.game.rule.Rule;
import com.mygdx.game.rule.RuleSet;
import com.mygdx.game.rule.RuleStack;
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

	public Level(String filename) {
		
		props = new ArrayList<Class>();
		
		props.add(Baba.class);props.add(Empty.class);props.add(Keke.class);props.add(Rock.class);props.add(Wall.class);props.add(Water.class);
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
			System.out.println(length);
			System.out.println(height);

			
			locationMatrix = new Location[height][length];
			for (int y = 0; y<height; y++) {
				for (int x = 0; x<length; x++) {
					ArrayList<Item> items = new ArrayList<Item>();
					Location loc = new Location(items, this, x, height - 1 -y);
					switch(lines.get(y).charAt(x)) {
					case 'e':
						loc.add(new Empty(loc, getRuleTable(), x, height - 1 -y,0));
						break;
					case 'b':
						loc.add(new Baba(loc, getRuleTable(), x, height - 1 -y,0));
						break;
					case 'w':
						loc.add(new Wall(loc, getRuleTable(), x, height - 1 -y,0));
						break;
					case 'r':
						loc.add(new Rock(loc, getRuleTable(), x, height - 1 -y,0));
						break;
					case 'a':
						loc.add(new Water(loc, getRuleTable(), x, height - 1 -y,0));
						break;
					case 'k':
						loc.add(new Keke(loc, getRuleTable(), x, height - 1 -y,0));
						break;
					case 's':
						loc.add(new Skull(loc, getRuleTable(), x, height - 1 -y,0));
						break;
					}
					locationMatrix[height - 1 -y][x] = loc;
				}
			}
			
			// Test de text
			locationMatrix[0][0].add(new You(locationMatrix[0][0], getRuleTable(), 0, height-1,0));
			locationMatrix[1][0].add(new Is(locationMatrix[1][0], getRuleTable(), 0, height-2,0));
			locationMatrix[2][0].add(new BabaText(locationMatrix[2][0], getRuleTable(), 0, height-3,0));

			history.add(this.matrixCopy());
			
			updateRules();


		}
		catch(Exception e) {
			System.out.println("Error while loading level");
			e.printStackTrace();
		}
		
		

	}

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
				
							
				for (Text text : textList) {
					text.show();
					System.out.println("  "+x+"  "+y+"  ");
				}
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
				
				for (Text text : textList) {
					text.show();
					System.out.println("  "+x+"  "+y+"  ");
				}
				currentRules.buildNext(textList, thereIsAnOnOrNearOrFacingOrAnd, thereIsANot);
				thereIsAnOnOrNearOrFacingOrAnd = locationMatrix[y][x].thereIsAOn() || locationMatrix[y][x].thereIsAAnd();
				thereIsANot = locationMatrix[y][x].thereIsANot();
			}
			currentRules.buildNext(new ArrayList<Text>(), thereIsAnOnOrNearOrFacingOrAnd, thereIsANot);
		}
	}
	

	public void interpretRules() {

		System.out.println("################################## __Construction__    ################################################");
		ruleTable = new LogicHashtable(rules, props);
		System.out.println("##################################  __RuleTable__    ################################################");
		System.out.println(ruleTable);
		System.out.println(locationMatrix[2][0].getItems().get(0).getRuleTable());
		
	}
	
	public void updateRules() {
		System.out.println("Joy and Hapiness \n " + getRuleTable());
		;
		readRules();
		interpretRules();
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

	public LogicHashtable getRuleTable() {
		return ruleTable;
	}

	

}
