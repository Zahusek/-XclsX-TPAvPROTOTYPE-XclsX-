package com.gmail.zahusek.entity;

import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class EntityHorse {
	
	private Object datawatcher, horse;
	private int id;
	public EntityHorse(World w) {
		int i = -1;
		Object dw = null, h = null;
		
		try {
			Object world = Reflection.getHandle(w);
			h = Reflection.getClass("EntityHorse", Type.NET).getConstructor(Reflection.getClass("World", Type.NET)).newInstance(world);
			i = (int) h.getClass().getMethod("getId").invoke(h, new Object[0]);
			dw = h.getClass().getMethod("getDataWatcher").invoke(h, new Object[0]);
			h.getClass().getMethod("setAge", int.class).invoke(h, -1700000);
		} catch(Exception e) { e.printStackTrace(); }
		
		this.horse = h;
		this.id = i;
		this.datawatcher = dw;
	}
	public Object getEntity() {
		return this.horse;
	}
	public Object getDataWatcher() {
		return this.datawatcher;
	}
	public int getId() {
		return this.id;
	}
	public void setLocation(Location l) {
		try {
			this.horse.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class).invoke(this.horse, l.getX(), l.getY()+56, l.getZ(), 0, 0);
		} catch(Exception e) { e.printStackTrace(); }
	}
}
