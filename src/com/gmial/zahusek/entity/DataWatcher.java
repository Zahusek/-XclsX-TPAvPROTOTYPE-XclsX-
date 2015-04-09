package com.gmail.zahusek.entity;

import java.lang.reflect.Method;

public class DataWatcher {
	
	private Object datawatcher;
	private Method dw;
	
	public DataWatcher(Object datawatcher) {
		this.datawatcher = datawatcher;
		Method d = null;
		try {
			d = this.datawatcher.getClass().getMethod("watch", int.class, Object.class);
		} catch (Exception e) { e.printStackTrace(); }
		this.dw = d;
		other(7, (int) 0);
		other(8, (byte) 0x20);
	}
	public void setName(String s, boolean v) {
		byte vn = v ? (byte) 1 : (byte) 0;
		try {
			dw.invoke(datawatcher, 10, (String) s);
			dw.invoke(datawatcher, 11, vn);
		} catch (Exception e) { e.printStackTrace(); }
	}
	public void setHealth(float f) {
		try {
			dw.invoke(datawatcher, 6, (float) f);
		} catch (Exception e) { e.printStackTrace();}
	}
	public void other(int i, Object o) {
		try {
			dw.invoke(datawatcher, i, o);
		} catch (Exception e) { e.printStackTrace();}
	}
	public Object getDataWatcher() {
		return this.datawatcher;
	}
}
