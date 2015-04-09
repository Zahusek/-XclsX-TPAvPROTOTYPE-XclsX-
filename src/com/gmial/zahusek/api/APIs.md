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

tabapi.update(PLAYER, STRING[]); // - create new TabList for the player


