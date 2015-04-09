package com.gmail.zahusek.packets;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class PacketPlayOutPlayerInfo {
	
	private String name;
	private boolean online;
	private int ping;
	
	private Class<?> classpacket = Reflection.getClass("PacketPlayOutPlayerInfo", Type.NET);
	
	public PacketPlayOutPlayerInfo(String name) {
		this.name = name;
	}
	public PacketPlayOutPlayerInfo(String name, boolean online, int ping) {
		this.name = name;
		this.online = online;
		this.ping = ping;
	}
	
	public enum PlayerInfoType { CREATE, DELETE, OTHER }
	
	public Object getPacket(PlayerInfoType type) {
		Object packet = null;
		switch (type) {
		case CREATE:
			try {
				packet = classpacket.getConstructor(String.class, boolean.class, int.class).newInstance(this.name, true, this.ping);
			} catch (Exception e) { e.printStackTrace(); }
			break;
		case DELETE:
			try {
				packet = classpacket.getConstructor(String.class, boolean.class, int.class).newInstance(this.name, false, this.ping);
			} catch (Exception e) { e.printStackTrace(); }
			break;
		case OTHER:
			try {
				packet = classpacket.getConstructor(String.class, boolean.class, int.class).newInstance(this.name, this.online, this.ping);
			} catch (Exception e) { e.printStackTrace(); }
			break;
		default:
			break;
		}
		return packet;
	}
}
