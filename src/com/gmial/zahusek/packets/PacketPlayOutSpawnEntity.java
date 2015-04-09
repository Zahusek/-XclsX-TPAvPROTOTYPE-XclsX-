package com.gmail.zahusek.packets;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class PacketPlayOutSpawnEntity {
	
	private Object entity;
	private int id;
	
	private Class<?> classpacket = Reflection.getClass("PacketPlayOutSpawnEntity", Type.NET);
	
	public PacketPlayOutSpawnEntity(Object entity, int id) {
		this.entity = entity;
		this.id = id;
	}
	
	public Object getPacket() {
		Object packet = null;
		try {
			packet = classpacket.getConstructor(Reflection.getClass("Entity", Type.NET), int.class).newInstance(this.entity, this.id);
		} catch (Exception e) { e.printStackTrace(); }
		return packet;
	}
}
