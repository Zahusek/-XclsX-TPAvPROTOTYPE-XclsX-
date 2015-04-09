package com.gmail.zahusek.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import com.gmail.zahusek.utils.Reflection.Type;

public class PacketSend {
	
	private static Method craftplayer, sendpacket;
	private static Field connection;
	private Object getConnection;
	
	static {
		try {
			
			craftplayer = Reflection.getClass("entity.CraftPlayer", Type.ORG).getMethod("getHandle");
			connection = Reflection.getClass("EntityPlayer", Type.NET).getField("playerConnection");
			sendpacket = Reflection.getClass("PlayerConnection", Type.NET).getMethod("sendPacket", new Class[] { Reflection.getClass("Packet", Type.NET)});
		
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public PacketSend(Player p) {
		try {
			
			Object player = craftplayer.invoke(p);
			getConnection = connection.get(player);
		
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	public void sendPacket(Object packet) {
		try {
			sendpacket.invoke(getConnection, packet);
		} catch (Exception e) { e.printStackTrace(); }
	}
}
