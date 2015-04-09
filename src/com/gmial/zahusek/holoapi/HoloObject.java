package com.gmail.zahusek.api.holoapi;

import java.util.ArrayList;

import org.bukkit.Location;

public class HoloObject {
	
	public static ArrayList<HoloObject> list = new ArrayList<HoloObject>();
	
	private String nam;
	private ArrayList<ArrayList<Data>> hol;
	
	public HoloObject(String nam) {
		this.nam = nam;
		this.hol = new ArrayList<ArrayList<Data>>();
		list.add(this);
	}
	public String getName() {
		return this.nam;
	}
	public ArrayList<ArrayList<Data>> getHolos() {
		return this.hol;
	}
	public static HoloObject getUser(String name) {
		for(HoloObject hol : list) if(hol.getName().equals(name)) return hol;
		return null;
	}
	public static HoloObject get(String name) {
		return getUser(name) == null ? new HoloObject(name):getUser(name);
		
	}
	public static class Data {
		
		private int[] ids;
		private Object datawatcher;
		private Location location;
		
		public Data(int[] ids, Object datawatcher, Location location) {
			this.ids = ids;
			this.datawatcher = datawatcher;
			this.location = location;
		}
		
		public int[] getIDs() {
			return this.ids;
		}
		public Object getDataWatcher() {
			return this.datawatcher;
		}
		public Location getLocation() {
			return this.location;
		}
	}
}
