# TabAPI
- src
  - com/gmail/zahusek
    - api/tabapi:
      » TabAPI.java
      » TabObject.java
    - packets:
      » PacketPlayOutPlayerInfo.java
      » PacketPlayOutScoreboardTeam.java
    - utils:
      » PacketSend.java
      » Reflcetion.java

Usage:

TabAPI tabapi = new TabAPI(this);
 
public TabAPI getTabAPI() {
  return this.tabapi;
}

onEnable:
getTabAPI().register();

onDisable:
getTabAPI().unregister();

getTabAPI().update(PLAYER, STRING[]); //- create new TabList for the player

getTabAPI().classic(PLAYER); //- classical TabList for the player

getTabAPI().clear(PLAYER); //- clear all slots from TabList for the player

# BarAPI
- src
  - com/gmail/zahusek
    - api/barapi:
      » BarAPI.java
      » BarObject.java
    - entity:
      » DataWatcher.java
      » EntityEnderDragon.java
    - packets:
      » PacketPlayOutSpawnEntityLiving.java
      » PacketPlayOutEntityDestroy.java
      » PacketPlayOutEntityMetaData.java
      » PacketPlayOutEntityTeleport.java
    - utils:
      » PacketSend.java
      » Reflcetion.java

Usage:

BarAPI barapi = new BarAPI(this);
 
public BarAPI getBarAPI() {
  return this.barapi;
}

onEnable:
getBarAPI().register();

onDisable:
getBarAPI().unregister();

getBarAPI().update(PLAYER, STRING); //- create new BossBar with CUSTOM NAME AND 200 HEALTH for the player

getBarAPI().update(PLAYER, STRING, FLOAT); //- create new BossBar with CUSTOM NAME AND CUSTOM HEALTH for the player

getBarAPI().remove(PLAYER); //- remove BossBar for the player

# ParticleAPI
- src
  - com/gmail/zahusek
    - api/particleapi:
      » ParticleAPI.java
    - packets:
      » PacketPlayOutWorldParticles.java
    - utils:
      » PacketSend.java
      » Reflcetion.java

Usage:

ParticleAPI.send(PLAYER, EFFECT, LOCATION, INT, FLOAT, FLOAT, FLOAT, FLOAT); //send effect for the player

ParticleAPI.send(PLAYER, MATERIAL, EFFECT, INT, INT, LOCATION, INT, FLOAT, FLOAT, FLOAT, FLOAT); //send material effect for the player

# HoloAPI
- src
  - com/gmail/zahusek
    - api/holoapi:
      » HoloAPI.java
      » HoloObject.java
    - entity:
      » DataWatcher.java
      » EntityHorse.java
      » EntityWitherSkull.java
    - packets:
      » PacketPlayOutAttachEntity.java
      » PacketPlayOutEntityDestroy.java
      » PacketPlayOutEntityMetadata.java
      » PacketPlayOutSpawnEntity.java
      » PacketPlayOutSpawnEntityLiving.java
    - utils:
      » PacketSend.java
      » Reflcetion.java

Usage:

HoloAPI holoapi = new HoloAPI(this);
 
public HoloAPI getHoloAPI() {
  return this.holoapi;
}

onEnable:
getHoloAPI().register();

onDisable:
getHoloAPI().unregister();

getHoloAPI().create(PLAYER, LOCATION, STRING[]); //- create new Hologram for the player

getHoloAPI().create(PLAYER, LOCATION, INT, STRING[]); //- create new Hologram on X second/s for the player

getHoloAPI().remove(PLAYER, INT); //- remove Hologram for the player

getHoloAPI().removeALL(PLAYER); //- remove all Holograms for the player

//method create also return id !

int id = getHoloAPI().create(PLAYER, LOCATION, STRING[]);

Player player = ...

getHoloAPI().remove(player, id);

