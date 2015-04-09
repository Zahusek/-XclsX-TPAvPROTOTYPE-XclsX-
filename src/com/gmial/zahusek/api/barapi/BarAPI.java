package com.gmail.zahusek.api.barapi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.zahusek.entity.DataWatcher;
import com.gmail.zahusek.entity.EntityEnderDragon;
import com.gmail.zahusek.packets.PacketPlayOutEntityDestroy;
import com.gmail.zahusek.packets.PacketPlayOutEntityMetadata;
import com.gmail.zahusek.packets.PacketPlayOutEntityTeleport;
import com.gmail.zahusek.packets.PacketPlayOutSpawnEntityLiving;
import com.gmail.zahusek.utils.PacketSend;

public class BarAPI {
	
	private int task;
	private static Plugin plg;
	
	public BarAPI (Plugin p) {
		plg = p;
	}
	public void register () {
		Bukkit.getServer().getPluginManager().registerEvents(new BarEvT(), plg);
		task = new BukkitRunnable() {
			
			@Override
			public void run() {
				for(BarObject id : BarObject.list) {
					Player p = Bukkit.getServer().getPlayer(id.getName());
					Location loc = p.getLocation().clone().subtract(0, 300, 0);
					PacketSend ps = new PacketSend(p);
					PacketPlayOutEntityTeleport ppoet = new PacketPlayOutEntityTeleport(id.getID(), loc.getBlockX() * 32, loc.getBlockY() * 32, loc.getBlockZ() * 32, (byte) ((int) loc.getYaw() * 256 / 360), (byte) ((int) loc.getPitch() * 256 / 360));
					ps.sendPacket(ppoet.getPacket());
				}
			}
		}.runTaskTimer(plg, 0L, 5L).getTaskId();
	}
	public void unregister () {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			remove(p);
		}
		Bukkit.getServer().getScheduler().cancelTask(this.task);
	}
	public void update(Player p, String m, float h) {
		PacketSend ps = new PacketSend(p);
		BarObject po;
		if(BarObject.getPlayer(p.getName()) == null) {
			EntityEnderDragon d = new EntityEnderDragon(p.getWorld());
			po = new BarObject(p.getName(), d.getId(), d.getDataWatcher());
			PacketPlayOutSpawnEntityLiving pposel = new PacketPlayOutSpawnEntityLiving(d.getEntity());
			ps.sendPacket(pposel.getPacket());
			setWatcher(m, h, po.getID(), po.getDataWatcher(), ps);
		}
		else {
			po = BarObject.getPlayer(p.getName());
			setWatcher(m, h, po.getID(), po.getDataWatcher(), ps);
		}
		po.setMessage(m);
		po.setHealth(h);
	}
	public void update(Player p, String m) {
		update(p, m, 200.0F);
	}
	public void remove(Player p) {
		BarObject po = BarObject.getPlayer(p.getName());
		if(po == null) return;
		PacketSend ps = new PacketSend(p);
		PacketPlayOutEntityDestroy ppoed = new PacketPlayOutEntityDestroy(po.getID());
		ps.sendPacket(ppoed.getPacket());
		BarObject.list.remove(po);
	}
	private void setWatcher(String m, float h, int id, Object datawatcher, PacketSend ps) {
		DataWatcher dw = new DataWatcher(datawatcher);
		dw.setName(m, true);
		dw.setHealth(h);
		PacketPlayOutEntityMetadata ppoem = new PacketPlayOutEntityMetadata(id, dw.getDataWatcher(), true);
		ps.sendPacket(ppoem.getPacket());
	}
	private void refresh(final Player p) {
		BarObject po = BarObject.getPlayer(p.getName());
		if(po == null) return;
		final String m = po.getMessage();
		final float h = po.getHealth();
		new BukkitRunnable() {
			@Override
			public void run() {
				remove(p);
				update(p, m, h);
				p.sendMessage(m);
			}
		}.runTaskLater(plg, 3L);
	}
	private class BarEvT implements Listener {
		
		@EventHandler(priority = EventPriority.MONITOR)
		public void playerquitevt(PlayerQuitEvent e) {
			remove(e.getPlayer());
		}
		@EventHandler(priority = EventPriority.MONITOR)
		public void playerteleportevt(PlayerChangedWorldEvent e) {
			Player p = e.getPlayer();
			refresh(p);
		}
		@EventHandler(priority = EventPriority.MONITOR)
		public void playerrespawnevt(PlayerRespawnEvent e) {
			Player p = e.getPlayer();
			refresh(p);
		}
	}
}
