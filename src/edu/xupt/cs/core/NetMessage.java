package edu.xupt.cs.core;

public class NetMessage {
    private ECammand cammand;
    private String action;
    private String message;

    public NetMessage() {
    }
    public NetMessage(String info) {
        int index = info.indexOf(":");
        String cammand = info.substring(0, index);
        String subInfo = info.substring(index + 1);
        index = subInfo.indexOf(":");
        action = subInfo.substring(0,index);
        message = subInfo.substring(index + 1);
        this.cammand = ECammand.valueOf(cammand);
    }

    public ECammand getCammand() {
        return cammand;
    }

    public NetMessage setCammand(ECammand cammand) {
        this.cammand = cammand;
        return this;
    }

    public String getAction() {
        return action;
    }

    public NetMessage setAction(String action) {
        this.action = action;
        return this;
    }

    @Override
    public String toString() {
        return cammand + ":" +  action + ":"  + message;
    }

    public String getMessage() {
        return message;
    }

    public NetMessage setMessage(String message) {
        this.message = message;
        return this;
    }
}
