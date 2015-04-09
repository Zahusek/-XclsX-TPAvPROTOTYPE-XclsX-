package com.gmail.zahusek.packets;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class PacketPlayOutEntityMetadata {
	
	private int id;
	private Object datawatcher;
	private boolean update;
	
	private Class<?> classpacket = Reflection.getClass("PacketPlayOutEntityMetadata", Type.NET);
	
	public PacketPlayOutEntityMetadata(int id, Object datawatcher, boolean update) {
		this.id = id;
		this.datawatcher = datawatcher;
		this.update = update;
	}
	
	public Object getPacket() {
		Object packet = null;
		try {
			packet = classpacket.getConstructor(int.class, Reflection.getClass("DataWatcher", Type.NET), boolean.class).newInstance(this.id, this.datawatcher, this.update);
		} catch (Exception e) { e.printStackTrace(); }
		return packet;
	}
}
