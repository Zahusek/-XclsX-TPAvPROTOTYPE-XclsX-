package com.gmail.zahusek.api.holoapi;

import java.util.ArrayList;

import net.minecraft.server.v1_7_R1.MathHelper;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.zahusek.api.holoapi.HoloObject.Data;
import com.gmail.zahusek.entity.DataWatcher;
import com.gmail.zahusek.entity.EntityHorse;
import com.gmail.zahusek.entity.EntityWitherSkull;
import com.gmail.zahusek.packets.PacketPlayOutAttachEntity;
import com.gmail.zahusek.packets.PacketPlayOutEntityDestroy;
import com.gmail.zahusek.packets.PacketPlayOutEntityMetadata;
import com.gmail.zahusek.packets.PacketPlayOutEntityTeleport;
import com.gmail.zahusek.packets.PacketPlayOutSpawnEntity;
import com.gmail.zahusek.packets.PacketPlayOutSpawnEntityLiving;
import com.gmail.zahusek.utils.PacketSend;

public class HoloAPI {
	
	private static Plugin plg;
	
	public HoloAPI (Plugin p) {
		plg = p;
	}
	public void unregister () {
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			removeAll(p);
		}
	}
	public void register () {
		Bukkit.getServer().getPluginManager().registerEvents(new HoloEvT(), plg);
	}
	
	private static final double distance = 0.25;
	
	public int create (Player p, Location l, String... o) {
		PacketSend ps =  new PacketSend(p);
		HoloObject ho = HoloObject.get(p.getName());
		World w = l.getWorld();	
		int lm = o.length;
		l.setY(l.getY()+(distance*(lm/2)));
		ArrayList<Data> list = new ArrayList<Data>();
		for (int i = 0; i < lm; i++) {
			EntityWitherSkull skull = new EntityWitherSkull(w);
			skull.setLocation(l);
			
			EntityHorse horse = new EntityHorse(w);
			horse.setLocation(l);
			
			PacketPlayOutSpawnEntity pposp = new PacketPlayOutSpawnEntity(skull.getEntity(), 64);
			ps.sendPacket(pposp.getPacket());
			
			PacketPlayOutSpawnEntityLiving pposel = new PacketPlayOutSpawnEntityLiving(horse.getEntity());
			setWatcher(o[i], horse.getId(), horse.getDataWatcher(), ps);
			ps.sendPacket(pposel.getPacket());
			
			PacketPlayOutAttachEntity ppoae = new PacketPlayOutAttachEntity(0, horse.getEntity(), skull.getEntity()); 
			ps.sendPacket(ppoae.getPacket());
			
			int[] ids = new int[] { horse.getId(), skull.getId() };
			Data data = new Data(ids, horse.getDataWatcher(), l);
			list.add(data);
			l.subtract(0, distance, 0);
		}
		ho.getHolos().add(list);
		return ho.getHolos().size()-1;
	}
	public void create (Player p, Location l, int sec, String... o) {
		final int id = create(p, l, o);
		final Player pl = p;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				remove(pl, id);
			}
		}.runTaskLater(plg, sec*20);
	}
	public void remove (Player p, int id) {
		HoloObject ho = HoloObject.getUser(p.getName());
		if(ho == null) return;
		PacketSend ps =  new PacketSend(p);
		for(Data dt : ho.getHolos().get(id)) {
			int[] ids = dt.getIDs();
			PacketPlayOutEntityDestroy ppoed = new PacketPlayOutEntityDestroy(ids);
			ps.sendPacket(ppoed.getPacket());
		}
	}
	public void removeAll (Player p) {
		HoloObject ho = HoloObject.getUser(p.getName());
		if(ho == null) return;
		PacketSend ps =  new PacketSend(p);
		for(ArrayList<Data> list : ho.getHolos())
		for(Data dt : list) {
			int[] ids = dt.getIDs();
			PacketPlayOutEntityDestroy ppoed = new PacketPlayOutEntityDestroy(ids);
			ps.sendPacket(ppoed.getPacket());
		}
		ho.getHolos().clear();
	}
	private void setWatcher (String m, int id, Object datawatcher, PacketSend ps) {
		DataWatcher dw = new DataWatcher(datawatcher);
		dw.setName(m, true);
		PacketPlayOutEntityMetadata ppoem = new PacketPlayOutEntityMetadata(id, dw.getDataWatcher(), true);
		ps.sendPacket(ppoem.getPacket());
	}
	private class HoloEvT implements Listener {
		
		@EventHandler(priority = EventPriority.MONITOR)
		public void join (PlayerQuitEvent evt) {
			Player p = (Player) evt.getPlayer();
			if(HoloObject.getUser(p.getName()) == null) return;
			removeAll(p);
		}
	}
}
