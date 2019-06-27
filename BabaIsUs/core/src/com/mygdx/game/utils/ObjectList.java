package com.mygdx.game.utils;

import com.mygdx.game.objects.*;
import com.mygdx.game.objects.text.item_ref.*;
import com.mygdx.game.objects.text.operator.*;
import com.mygdx.game.objects.text.property.*;
import com.mygdx.game.objects.text.property.Float;
import com.mygdx.game.objects.text.relation.*;

public enum ObjectList {
	BABA(ItemTypeList.OBJECT,Baba.class),
	FLAG(ItemTypeList.OBJECT,Flag.class),
	KEKE(ItemTypeList.OBJECT,Keke.class),
	WALL(ItemTypeList.OBJECT,Wall.class),
	WATER(ItemTypeList.OBJECT,Water.class),
	EMPTY(ItemTypeList.OBJECT,Empty.class),
	ROCK(ItemTypeList.OBJECT,Rock.class),
	SKULL(ItemTypeList.OBJECT,Skull.class),
	WALLTEXT(ItemTypeList.ITEM_TEXT,WallText.class),
	FLAGTEXT(ItemTypeList.ITEM_TEXT,FlagText.class),
	ROCKTEXT(ItemTypeList.ITEM_TEXT,RockText.class),
	BABATEXT(ItemTypeList.ITEM_TEXT,BabaText.class),
	KEKETEXT(ItemTypeList.ITEM_TEXT,KekeText.class),
	EMPTYTEXT(ItemTypeList.ITEM_TEXT,EmptyText.class),
	METEXT(ItemTypeList.ITEM_TEXT,MeText.class),
	SKULLTEXT(ItemTypeList.ITEM_TEXT,SkullText.class),
	WATERTEXT(ItemTypeList.ITEM_TEXT,WaterText.class),
	AND(ItemTypeList.OPERATOR,And.class),
	FACING(ItemTypeList.OPERATOR,Facing.class),
	NEAR(ItemTypeList.OPERATOR,Near.class),
	Not(ItemTypeList.OPERATOR,Not.class),
	ON(ItemTypeList.OPERATOR,On.class),
	DEFEAT(ItemTypeList.PROPERTY,Defeat.class),
	FLOAT(ItemTypeList.PROPERTY,Float.class),
	HOT(ItemTypeList.PROPERTY,Hot.class),
	MELT(ItemTypeList.PROPERTY,Melt.class),
	MOVE(ItemTypeList.PROPERTY,Move.class),
	OPEN(ItemTypeList.PROPERTY,Open.class),
	PULL(ItemTypeList.PROPERTY,Pull.class),
	PUSH(ItemTypeList.PROPERTY,Push.class),
	SHIFT(ItemTypeList.PROPERTY,Shift.class),
	SHUT(ItemTypeList.PROPERTY,Shut.class),
	SINK(ItemTypeList.PROPERTY,Sink.class),
	STOP(ItemTypeList.PROPERTY,Stop.class),
	WEAK(ItemTypeList.PROPERTY,Weak.class),
	WIN(ItemTypeList.PROPERTY,Win.class),
	YOU(ItemTypeList.PROPERTY,You.class),
	YOU1(ItemTypeList.PROPERTY,You1.class),
	YOU2(ItemTypeList.PROPERTY,You2.class),
	HAS(ItemTypeList.RELATION,Has.class),
	IS(ItemTypeList.RELATION,Is.class);
	
	private ItemTypeList itemType;
	private Class clazz;
	
	private ObjectList(ItemTypeList itemType , Class clazz) {
		this.itemType = itemType;
		this.clazz = clazz;
	}
	
	public ItemTypeList getItemType() {
		return itemType;
	}
	
	public Class getClazz() {
		return clazz;
	}
}
