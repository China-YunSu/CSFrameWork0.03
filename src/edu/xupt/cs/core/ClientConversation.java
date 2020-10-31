package edu.xupt.cs.core;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

 class ClientConversation extends Communicate{
    private Client client;
    private String serverIp;
    private static final Gson gson = new Gson();

     ClientConversation(Socket socket,Client client) {
        super(socket);
        this.client = client;
        serverIp = socket.getInetAddress().getHostAddress();
    }

    @Override
     public void dealMessage(NetMessage netMessage) {
        try {
            NetCommandProcesser.dealNetMessage(this,netMessage);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    void dealToOthers(NetMessage netMessage) {
        Interactive interactive = gson.fromJson(netMessage.getMessage(), Interactive.class);
        client.getClientAction().dealTalkToOthers(interactive.getSourceId(),interactive.getMessage());
    }

    void dealOnlinePass(NetMessage netMessage) {
        client.setId(netMessage.getMessage());
        client.getClientAction().afterConnectionToServer();
    }

    void dealResponse(NetMessage netMessage) {
        try {
            client.getActionProcess().dealResponse(netMessage.getAction(),netMessage.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void dealOffline(NetMessage netMessage) {
        closeChannel();
        client.getClientAction().afterOffLine();
    }

    void dealToOne(NetMessage netMessage) {
        Interactive interactive = gson.fromJson(netMessage.getMessage(), Interactive.class);
        client.getClientAction().dealTalkToOne(interactive.getSourceId(),interactive.getMessage());
    }

    void dealOutOfRoom(NetMessage netMessage) {
        closeChannel();
        client.getClientAction().serverOutOfRoom();
    }

    void expressOne(String targetId,String message) {
        Interactive interactive = new Interactive();
        interactive.setSourceId(client.getId()).setTargetId(targetId).setMessage(message);
        sendMessage(new NetMessage().setCammand(ECammand.TO_ONE)
                    .setMessage(gson.toJson(interactive)));
    }

    void expressAll(String message) {
        Interactive interactive = new Interactive();
        interactive.setSourceId(client.getId()).setMessage(message);
        sendMessage(new NetMessage().setCammand(ECammand.TO_OTHERS)
                .setMessage(gson.toJson(interactive)));
    }

    void dealForceDown(NetMessage netMessage) {
        closeChannel();
        client.getClientAction().serverForceDrop();
    }

    void dealWhoAreYou(NetMessage netMessage) {
        String message = socket.getLocalAddress().getHostAddress() + "!" + System.currentTimeMillis();
        message += "#" + message.hashCode();
        sendMessage(new NetMessage().setCammand(ECammand.I_AM)
                        .setMessage(message));
    }

    void offLine() {
        client.getClientAction().offLine();
        sendMessage(new NetMessage().setCammand(ECammand.OFFLINE));
    }

    @Override
     public void dealPeerDown() {
        client.getClientAction().serverPeerDrop();
    }

     void request(String requset, String response, String parameter) {
        sendMessage(new NetMessage().setCammand(ECammand.REQUEST)
                .setAction(requset + "#" + response).setMessage(parameter));
    }
}