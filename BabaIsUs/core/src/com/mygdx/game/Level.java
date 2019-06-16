package com.mygdx.game;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.objects.*;

import java.io.File;

public class Level {
	private ArrayList<Item>[][] items;
	private int height;
	private int length;

	public Level(String filename) {



		try {
			Scanner scanner = new Scanner(new File(filename));
			scanner.useDelimiter("\\A");
			String all = scanner.next();
			scanner.close();
			
			String[] lines = all.split(System.getProperty("line.separator"));
			
			height = lines.length;
			length = lines[0].length();
			

			items = new ArrayList[height][length];
			
			for (int index1 = 0; index1<height; index1++) {
				for (int index2 = 0; index2<length; index2++) {
					switch(lines[index1].charAt(index2)) {
					case 'e':
						items[index1][index2] = new ArrayList<Item>();
						items[index1][index2].add(new Empty(this, index2, index1,0));
						break;
					case 'b':
						items[index1][index2] = new ArrayList<Item>();
						items[index1][index2].add(new Baba(this, index2, index1,0));
						break;
					case 'w':
						items[index1][index2] = new ArrayList<Item>();
						items[index1][index2].add(new Wall(this, index2, index1,0));
						break;
					case 'r':
						items[index1][index2] = new ArrayList<Item>();
						items[index1][index2].add(new Rock(this, index2, index1,0));
						break;
					}
				}
			}

			
		}
		catch(Exception e) {
			System.out.println("Error while loading level");
			e.printStackTrace();
		}


	}

	public void del(Item item, int x, int y) {
		items[y][x].remove(item);
		this.checkempty();
	}

	public void add(Item item, int x, int y) {
		items[y][x].add(item);
		this.checkempty();
	}

	
	public void checkempty() {
		for (int index1 = 0; index1<length;index1++) {
			for (int index2 = 0; index2<height;index2++) {
				this.checkempty(index1,index2);
			}
		}
	}
	
	
	public void checkempty(int x, int y) {
		
		ArrayList<Item> toBeRemoved = new ArrayList<Item>();
		for(int index=0; index< items[y][x].size(); index++) {
			if (items[y][x].get(index).isempty()) {
				toBeRemoved.add(items[y][x].get(index));
			}
		}
		for(int index=0; index< toBeRemoved.size(); index++) {
			items[y][x].remove(toBeRemoved.get(index));
		}
		
		
		if (items[y][x].size()==0) {
			items[y][x].add(new Empty(this, x, y, 0));
		}

	}
	public ArrayList<Item> getlocation(int x, int y){
		return items[y][x];
	}
	
	public void moveYou(int direction) {
		
		
		for (int index1 = 0; index1<length;index1++) {
			for (int index2 = 0; index2<height;index2++) {
				for (int index3 = 0; index3<items[index2][index1].size();index3++) {
					if (items[index2][index1].get(index3).isyou()) {
						items[index2][index1].get(index3).moveAsyou(direction);
					}
				}
			}
		}
	}
	
	public void update() {
		for (int index1 = 0; index1<length;index1++) {
			for (int index2 = 0; index2<height;index2++) {
				for (int index3 = 0; index3<items[index2][index1].size();index3++) {
					items[index2][index1].get(index3).update();
				}
			}
		}
	}
	
	public void dispose() {
		for (int index1 = 0; index1<length;index1++) {
			for (int index2 = 0; index2<height;index2++) {
				for (int index3 = 0; index3<items[index2][index1].size();index3++) {
					items[index2][index1].get(index3).dispose();
				}
			}
		}
	}
	
	public void draw(SpriteBatch sb) {
		for (int index1 = 0; index1<length;index1++) {
			for (int index2 = 0; index2<height;index2++) {
				for (int index3 = 0; index3<items[index2][index1].size();index3++) {
					Item item = items[index2][index1].get(index3);
					sb.draw(item.getTexture(), item.getPosition().x*32, item.getPosition().y*32);
				}
			}
		}
	}
	
	public void reset() {
		for (int index1 = 0; index1<length;index1++) {
			for (int index2 = 0; index2<height;index2++) {
				for (int index3 = 0; index3<items[index2][index1].size();index3++) {
					items[index2][index1].get(index3).reset();
				}
			}
		}
	}

}