package com.gmail.zahusek.packets;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class PacketPlayOutEntityTeleport {
	
	private int id, x, y, z;
	private byte ya, pi;
	
	private Class<?> classpacket = Reflection.getClass("PacketPlayOutEntityTeleport", Type.NET);
	
	public PacketPlayOutEntityTeleport(int id, int x, int y, int z, byte ya, byte pi) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.ya = ya;
		this.pi = pi;
	}
	
	public Object getPacket() {
		Object packet = null;
		try {
			packet = classpacket.getConstructor(int.class, int.class, int.class, int.class, byte.class, byte.class).newInstance(this.id, this.x, this.y, this.z, this.ya, this.pi);
		} catch (Exception e) { e.printStackTrace(); }
		return packet;
	}
}
