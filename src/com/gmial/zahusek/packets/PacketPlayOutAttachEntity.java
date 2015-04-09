package com.gmail.zahusek.packets;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class PacketPlayOutAttachEntity {
	
	private int type;
	private Object passenger, entity;
	
	private Class<?> classpacket = Reflection.getClass("PacketPlayOutAttachEntity", Type.NET);
	
	public PacketPlayOutAttachEntity(int i , Object passenger, Object entity) {
		this.type = i;
		this.passenger = passenger;
		this.entity = entity;
	}
	
	public Object getPacket() {
		Object packet = null;
		try {
			packet = classpacket.getConstructor(int.class, Reflection.getClass("Entity", Type.NET), Reflection.getClass("Entity", Type.NET)).newInstance(this.type, this.passenger, this.entity);
		} catch (Exception e) { e.printStackTrace(); }
		return packet;
	}
}
