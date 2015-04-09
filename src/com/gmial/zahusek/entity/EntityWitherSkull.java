package com.gmail.zahusek.entity;

import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class EntityWitherSkull {
	
	private Object skull;
	private int id;
	
	public EntityWitherSkull(World w) {
		int i = -1;
		Object s = null;
		
		try {
			Object world = Reflection.getHandle(w);
			s = Reflection.getClass("EntityWitherSkull", Type.NET).getConstructor(Reflection.getClass("World", Type.NET)).newInstance(world);
			i = (int) s.getClass().getMethod("getId").invoke(s, new Object[0]);
		} catch(Exception e) { e.printStackTrace(); }
		
		this.skull = s;
		this.id = i;
	}
	public Object getEntity() {
		return this.skull;
	}
	public int getId() {
		return this.id;
	}
	public void setLocation(Location l) {
		try {
			this.skull.getClass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class).invoke(this.skull, l.getX(), l.getY()+56, l.getZ(), 0, 0);
		} catch(Exception e) { e.printStackTrace(); }
	}
}
