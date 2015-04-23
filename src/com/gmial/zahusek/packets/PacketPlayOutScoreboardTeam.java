package com.gmail.zahusek.packets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.gmail.zahusek.utils.Reflection;
import com.gmail.zahusek.utils.Reflection.Type;

public class PacketPlayOutScoreboardTeam {
		
    private String name, prefix, suffix;
    private ArrayList<String> players;
    
    
	private Class<?> classpacket = Reflection.getClass("PacketPlayOutScoreboardTeam", Type.NET);
	
	public PacketPlayOutScoreboardTeam(String name) {
		this.name = name;
		this.prefix = null;
		this.suffix = null;
		this.players = null;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	public void setPlayers(String... players) {
		this.players = new ArrayList<String>(Arrays.asList(players));
	}
	
	public enum ScoreboardTeamType { CREATE, DELETE, UPDATE, ADD, REMOVE };
	
	public Object getPacket(ScoreboardTeamType type) {
		Object packet = null;
		switch (type) {
		case CREATE:
			try {
				Object sb = Reflection.getClassInstance("Scoreboard", Type.NET);
				Object st = sb.getClass().getMethod("createTeam", String.class).invoke(sb, this.name);
				packet =  classpacket.getConstructor(st.getClass(), int.class).newInstance(st, 0);
			} catch (Exception e) { e.printStackTrace(); }
			break;
		case DELETE:
			try {
				Object sb = Reflection.getClassInstance("Scoreboard", Type.NET);
				Object st = sb.getClass().getMethod("createTeam", String.class).invoke(sb, this.name);
				packet =  classpacket.getConstructor(st.getClass(), int.class).newInstance(st, 1);
			} catch (Exception e) { e.printStackTrace(); }
			break;
		case UPDATE:
			try {
				Object sb = Reflection.getClassInstance("Scoreboard", Type.NET);
				Object st = sb.getClass().getMethod("createTeam", String.class).invoke(sb, this.name);
				st.getClass().getMethod("setPrefix", String.class).invoke(st, this.prefix);
				st.getClass().getMethod("setSuffix", String.class).invoke(st, this.suffix);
				packet =  classpacket.getConstructor(st.getClass(), int.class).newInstance(st, 2);
			} catch (Exception e) { e.printStackTrace(); }
			break;
		case ADD:
			try {
				Object sb = Reflection.getClassInstance("Scoreboard", Type.NET);
				Object st = sb.getClass().getMethod("createTeam", String.class).invoke(sb, this.name);
				packet =  classpacket.getConstructor(st.getClass(), Collection.class, int.class).newInstance(st, this.players, 3);
			} catch (Exception e) { e.printStackTrace(); }
			break;
		case REMOVE:
			try {
				Object sb = Reflection.getClassInstance("Scoreboard", Type.NET);
				Object st = sb.getClass().getMethod("createTeam", String.class).invoke(sb, this.name);
				packet =  classpacket.getConstructor(st.getClass(), Collection.class, int.class).newInstance(st, this.players, 4);
			} catch (Exception e) { e.printStackTrace(); }
			break;
		default:
			break;
		}
		return packet;
	}
}
