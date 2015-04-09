package com.gmail.zahusek.utils;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Reflection {
	
	public enum Type { ORG, NET }
	
	private static String version ;
	
	static {
		version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3] + ".";
	}
	
	public static Class<?> getClass (String classname, Type type) {
		
		Class<?> c = null;
		switch (type) {
		
		case ORG:
			try {
				c = Class.forName("org.bukkit.craftbukkit." + version + classname);
			} catch (ClassNotFoundException e) { e.printStackTrace(); }
			break;
		case NET:
			try {
				c = Class.forName("net.minecraft.server." + version + classname);
			} catch (ClassNotFoundException e) {
				e.printStackTrace(); 
			}
			break;
		default:
			c = null;
			break;
		}
		return c;
	}
	public static Object getClassInstance (String classname, Type type) {
		Object classinstance = null;
		try {
			classinstance = Reflection.getClass(classname, type).newInstance();
		} catch(Exception e) { e.printStackTrace(); }
		return classinstance;
	}
	
	public static void setValue (Object instance, String fieldName, Object value) {
		Field field = null;
		try {
			field = instance.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(instance, value);
		} catch(Exception e) { e.printStackTrace(); }
	}
	
	public static Object getValue (Object instance, String fieldName) {
		Field field = null ;
		try {
			field = instance.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(instance);
		} catch(Exception e) { e.printStackTrace(); }
		return field;
	}
	
	public static Object getHandle (World w) {
		Object handle = null;
		try {
			handle = w.getClass().getMethod("getHandle").invoke(w, new Object[0]);
		} catch (Exception e) { e.printStackTrace(); }
		return handle;
	}
}
