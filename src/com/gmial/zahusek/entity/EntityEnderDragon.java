package com.gmail.zahusek.entity;

import org.bukkit.World;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class EntityEnderDragon {
	
	private Object datawatcher, dragon;
	private int id;
	
	public EntityEnderDragon(World w) {
		int i = -1;
		Object dw = null, d = null;
		
		try {
			Object world = w.getClass().getMethod("getHandle").invoke(w, new Object[0]);
			d = Reflection.getClass("EntityEnderDragon", Type.NET).getConstructor(Reflection.getClass("World", Type.NET)).newInstance(world);
			i = (int) d.getClass().getMethod("getId").invoke(d, new Object[0]);
			dw = d.getClass().getMethod("getDataWatcher").invoke(d, new Object[0]);
		} catch(Exception e) { e.printStackTrace(); }
		
		this.dragon = d;
		this.id = i;
		this.datawatcher = dw;
	}
	public Object getEntity() {
		return this.dragon;
	}
	public Object getDataWatcher() {
		return this.datawatcher;
	}
	public int getId() {
		return this.id;
	}
}
