package com.gmail.zahusek.api.barapi;

import java.util.ArrayList;
import java.util.List;

public class BarObject {
	
	public static ArrayList<BarObject> list = new ArrayList<BarObject>();
	
	private String name;
	private Object datawatcher;
	private int id;
	
	private String m;
	private float h;

	public BarObject(String name, int id, Object datawatcher) {
		this.name = name;
		this.id = id;
		this.datawatcher = datawatcher;
		this.m = "";
		this.h = 0F;
		list.add(this);
	}
	
	public String getName() {
		return this.name;
	}
	public Object getDataWatcher() {
		return this.datawatcher;
	}
	public Object setDataWatcher(Object datawatcher) {
		return this.datawatcher = datawatcher;
	}
	public int getID() {
		return this.id;
	}
	public String getMessage() {
		return this.m;
	}
	public void setMessage(String m) {
		this.m = m;
	}
	public float getHealth() {
		return this.h;
	}
	public void setHealth(float h) {
		this.h = h;
	}
	public static List<BarObject> getList () {
		return list;
	}
	public static BarObject getPlayer(String name) {
		for(BarObject po : list) if(po.getName().equals(name)) return po;
		return null;
	}
}
