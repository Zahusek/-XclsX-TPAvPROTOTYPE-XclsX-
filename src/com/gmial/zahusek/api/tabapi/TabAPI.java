package com.gmail.zahusek.api.tabapi;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.gmail.zahusek.packets.PacketPlayOutPlayerInfo;
import com.gmail.zahusek.packets.PacketPlayOutScoreboardTeam;
import com.gmail.zahusek.packets.PacketPlayOutPlayerInfo.PlayerInfoType;
import com.gmail.zahusek.packets.PacketPlayOutScoreboardTeam.ScoreboardTeamType;
import com.gmail.zahusek.utils.PacketSend;

public class TabAPI {
	
	private static Plugin plg;
	
	public TabAPI (Plugin p) {
		plg = p;
	}
	
	public void register () {
		Bukkit.getServer().getPluginManager().registerEvents(new TabEvT(), plg);
	}
	
	public void unregister () {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			clear(p);
			classic(p);
		}
	}
	
	public static void update(Player p, String... s) {
		PacketSend ps = new PacketSend(p);
		TabObject po = TabObject.get(p.getName());
		for(int i = 0; i < s.length; i++) {
			PacketPlayOutScoreboardTeam ppost;
			if(i >= po.getFakeplayers().size()) {
				po.addFakePlayer();
				ppost = new PacketPlayOutScoreboardTeam(po.getId(i));
				ps.sendPacket(new PacketPlayOutPlayerInfo(po.getId(i), true, 9999).getPacket(PlayerInfoType.OTHER));
				ps.sendPacket(ppost.getPacket(ScoreboardTeamType.CREATE));
				ppost.setPlayers(po.getId(i));
				ps.sendPacket(ppost.getPacket(ScoreboardTeamType.ADD));
				
			}
			ppost =  new PacketPlayOutScoreboardTeam(po.getId(i));
			String msg = s[i] ;
			String one = msg.length() <= 16 ? msg : msg.substring(0, 16);
			ppost.setPrefix(one);
			String two = "";
			if(msg.length() > 16) {
				two = msg.substring(16, msg.length()).length()+2 > 16 ? "§r" + msg.substring(16, 30) : "§r" + msg.substring(16, msg.length());
				int idx = one.lastIndexOf("§");
				if(idx != -1) two = one.substring(idx, idx+2) + two.substring(2, two.length());
			}
			ppost.setSuffix(two);
			ps.sendPacket(ppost.getPacket(ScoreboardTeamType.UPDATE));
		}
	}
	
	public static void classic(Player p) {
		clear(p);
		PacketSend ps = new PacketSend(p);
		for(Player online : Bukkit.getServer().getOnlinePlayers()) {
			ps.sendPacket(new PacketPlayOutPlayerInfo(online.getPlayerListName()).getPacket(PlayerInfoType.CREATE));
		}
	}
	
	public static void clear(Player p) {
		PacketSend ps = new PacketSend(p);
		for(Player online : Bukkit.getServer().getOnlinePlayers()) {
			ps.sendPacket(new PacketPlayOutPlayerInfo(online.getPlayerListName()).getPacket(PlayerInfoType.DELETE));
		}
		TabObject po = TabObject.getUser(p.getName());
		if(po == null) return;
		for(int i = 0; i < po.getFakeplayers().size(); i++) {
			ps.sendPacket(new PacketPlayOutPlayerInfo(po.getId(i)).getPacket(PlayerInfoType.DELETE));
			ps.sendPacket(new PacketPlayOutScoreboardTeam(po.getId(i)).getPacket(ScoreboardTeamType.DELETE));
		}
		TabObject.list.remove(po);
	}
	
	private class TabEvT implements Listener {
		
		@EventHandler(priority = EventPriority.MONITOR)
		public void playerquitevt(PlayerQuitEvent e) {
			clear(e.getPlayer());
		}
		@EventHandler(priority = EventPriority.MONITOR)
		public void playerjointevt(PlayerJoinEvent e) {
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(p==e.getPlayer())continue; 
				if(TabObject.getUser(p.getName())== null) continue;
				clear(p);
			}
		}
	}
}
