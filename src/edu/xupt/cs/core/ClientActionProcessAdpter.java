package edu.xupt.cs.core;

public class ClientActionProcessAdpter implements IClientProcess {
    @Override
    public void offLine() {
    }

    @Override
    public void afterOffLine() {
    }

    @Override
    public void dealTalkToOne(String sourceId, String message) {
    }

    @Override
    public void dealTalkToOthers(String sourceId, String message) {
    }

    @Override
    public void afterConnectionToServer() {
    }

    @Override
    public void serverOutOfRoom() {
    }

    @Override
    public void serverPeerDrop() {
    }

    @Override
    public boolean confirmOffline() {
        return false;
    }

    @Override
    public void serverForceDrop() {

    }
}
