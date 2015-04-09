
package com.gmail.zahusek.packets;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class PacketPlayOutWorldParticles {
	
	private String name;
	private float lx, ly, lz, ox, oy, oz, speed;
	private int amount;
	
	private Class<?> classpacket = Reflection.getClass("PacketPlayOutWorldParticles", Type.NET);
	
	public PacketPlayOutWorldParticles(String name, float lx, float ly, float lz, float ox, float oy, float oz, float speed, int amount) {
		this.name = name;
		this.lx = lx;
		this.ly = ly;
		this.lz = lz;
		this.ox = ox;
		this.oy = oy;
		this.oz = oz;
		this.speed = speed;
		this.amount = amount;
	}
	
	public Object getPacket() {
		Object packet = null;
		try {
			packet = classpacket.getConstructor(String.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class).newInstance(this.name, this.lx, this.ly, this.lz, this.ox, this.oy, this.oz, this.speed, this.amount);
		} catch (Exception e) { e.printStackTrace(); }
		return packet;
	}
}
