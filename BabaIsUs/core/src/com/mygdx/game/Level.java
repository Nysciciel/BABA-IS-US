package com.mygdx.game;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.history.HistoryStack;
import com.mygdx.game.history.TurnStack;
import com.mygdx.game.objects.*;
import com.mygdx.game.objects.text.ItemRef;
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
	private int hash;
	private HistoryStack historyStack;

	public Level(int length,int height) {
		super();
		this.height = height;
		this.length = length;
		this.rules = new RuleSet();
		this.hash = 0;
		this.historyStack = new HistoryStack();

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

		this.ruleTable = new LogicHashtable();
		this.historyStack = new HistoryStack();

		try {
			FileHandle file = Gdx.files.local("Level/"+filename);
			Scanner scanner = new Scanner(file.read());
			ArrayList <String> lines = new ArrayList <String>();
			String[] taille = scanner.nextLine().split(" ");
			length = Integer.parseInt(taille[0]);
			height = Integer.parseInt(taille[1]);
			while(scanner.hasNext()) {
				lines.add(scanner.nextLine());
			}

			scanner.close();
			
			this.rules = new RuleSet();


			locationMatrix = new Location[height][length];

			for(int i=0;i<lines.size();i++) {
				String[] cell = lines.get(i).split(",");
				for(int j=0 ; j<cell.length ;j++) {
					String[] split = cell[j].split(" ");
					ArrayList<Item> items = new ArrayList<Item>();
					for(int k=0 ;k<split.length;k++) {

						locationMatrix[i][j] = new Location(items, this, j, i);
						String classname = split[k].substring(0, split[k].length()-1);
						Class clazz = Class.forName(classname);
						locationMatrix[i][j].add((Item) clazz.getConstructor(Location.class , int.class).newInstance(locationMatrix[i][j] , Integer.parseInt(split[k].substring(split[k].length()-1))));


						if(classname.contains("text")) {
							if (classname.contains("item_ref")) {
								String desired_name = "com.mygdx.game.objects"+classname.substring(classname.lastIndexOf('.'),classname.length()-4);
								Class desired_object = Class.forName(desired_name);
								if (!props.contains(desired_object)) {
									props.add(desired_object);
								}
							}
						}
						else
						{
							if (!props.contains(clazz) && (clazz != Class.forName("com.mygdx.game.objects.Empty"))) {
								props.add(clazz);
							}
						}
					}
				}
			}

			updateRules();


		}
		catch(Exception e) {
			System.out.println("Error while loading level");
			e.printStackTrace();
		}
	}

	public void addTurnStack() {
		
		if (historyStack.empty())
			historyStack.push(new TurnStack());
		else if (!historyStack.peek().isEmpty()) {
			historyStack.push(new TurnStack());
		}
	}
	
	public TurnStack getTurnStack() {
		return historyStack.peek();		
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

		ruleTable = new LogicHashtable(rules, props);

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
		this.addTurnStack();
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
		this.addTurnStack();
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
		for(int prio=1;prio<=8;prio++) {
			for (int x = 0; x < length; x++) {
				for (int y = 0; y < height; y++) {
					locationMatrix[y][x].render(sb, cellSize,prio);
				}
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
	}

	public void rollback() {
		if (!historyStack.empty())
			historyStack.unStack();
		updateRules();
	}

	public Location[][] getLocationMatrix(){
		return locationMatrix;
	}

	public void reset() {
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

	@Override
	public int hashCode(){
		hash =0;
		for (int x = 0; x<length;x++) {
			for (int y = 0; y<height;y++) {
				for (Item i: locationMatrix[y][x].getItems()){
					hash = hash +((int) i.toString().charAt(0)*(x+1)*2*(i.getOrientation()+6) + (int) i.toString().charAt(0)*(y+1)*3*(i.getOrientation()+6) );
				}
			}
		}
		return hash;
	}

	public void fakeTurn() {
		this.addTurnStack();
		this.endturn();
	}
}
