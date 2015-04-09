package com.gmail.zahusek.packets;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class PacketPlayOutEntityDestroy {
	
	private int[] id;
	
	private Class<?> classpacket = Reflection.getClass("PacketPlayOutEntityDestroy", Type.NET);
	
	public PacketPlayOutEntityDestroy(int... id) {
		this.id = id;
	}
	
	public Object getPacket() {
		Object packet = null;
		try {
			packet = classpacket.getConstructor(int[].class).newInstance(this.id);
		} catch (Exception e) { e.printStackTrace(); }
		return packet;
	}
}
