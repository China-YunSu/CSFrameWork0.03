package edu.xupt.cs.core;

public interface IClientProcess {
    void offLine();
    void afterOffLine();
    void dealTalkToOne(String sourceId, String message);
    void dealTalkToOthers(String sourceId,String message);
    void afterConnectionToServer();
    void serverOutOfRoom();
    void serverPeerDrop();
    boolean confirmOffline();
    void serverForceDrop();
}
