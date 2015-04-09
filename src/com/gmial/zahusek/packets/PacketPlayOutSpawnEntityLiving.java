package com.gmail.zahusek.packets;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class PacketPlayOutSpawnEntityLiving {
	
	private Object entity;
	
	private Class<?> classpacket = Reflection.getClass("PacketPlayOutSpawnEntityLiving", Type.NET);
	
	public PacketPlayOutSpawnEntityLiving(Object entity) {
		this.entity = entity;
	}
	
	public Object getPacket() {
		Object packet = null;
		try {
			packet = classpacket.getConstructor(Reflection.getClass("EntityLiving", Type.NET)).newInstance(this.entity);
		} catch (Exception e) { e.printStackTrace(); }
		return packet;
	}
}
