package edu.xupt.cs.core;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

 class ServerConversation extends Communicate{
    private Server server;
    private String clientIp;
    private String id;
    private static final Gson gson = new Gson();

     ServerConversation(Socket socket,Server server) {
        super(socket);
        this.server = server;
    }

     String getClientIp() {
        return clientIp;
    }

     String getId() {
        return id;
    }

     void forceDown() {
        sendMessage(new NetMessage().setCammand(ECammand.FORCE_DOWN));
        closeChannel();
    }


    @Override
     public void dealPeerDown() {
        server.publishMessage("客户端[" + id + "] 宕机");
        server.getTempClientPool().removeClient(this);
        server.getClientPool().removeClient(id);
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

    void dealRequest(NetMessage netMessage) {
        try {
            String action = netMessage.getAction();
            int index = action.indexOf("#");
            String request = action.substring(0,index);
            String response = action.substring(index + 1);
            Object result = server.getActionProcess().dealRequst(request, netMessage.getMessage());
            sendMessage(new NetMessage().setCammand(ECammand.RESPONSE).setAction(response)
                    .setMessage(gson.toJson(result)));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void dealToOthers(NetMessage netMessage) {
        String message = netMessage.getMessage();
        Interactive interactive = gson.fromJson(message, Interactive.class);
        server.talkToOthers(interactive.getSourceId(),message);
    }

    void dealToOne(NetMessage netMessage) {
        String message = netMessage.getMessage();
        Interactive interactive = gson.fromJson(message, Interactive.class);
        server.talkToOne(interactive.getTargetId(),message);
    }

     void toOne(String message) {
        sendMessage(new NetMessage().setCammand(ECammand.TO_ONE)
            .setMessage(message));
    }

     void talkToOthers(String message) {
        sendMessage(new NetMessage().setCammand(ECammand.TO_OTHERS)
                .setMessage(message));
    }
    
    void dealOffline(NetMessage netMessage) {
        sendMessage(new NetMessage().setCammand(ECammand.OFFLINE));
        server.getClientPool().removeClient(id);
        closeChannel();
        server.publishMessage("客户端[" + id + "] 下线");
    }

    void dealIAm(NetMessage netMessage) {
        String message = netMessage.getMessage();
        if (cheakClient(message)) {
            clientIp = message.substring(0,message.indexOf("!"));
            id = clientIp + "_" + System.currentTimeMillis();
            server.getTempClientPool().removeClient(this);
            server.getClientPool().addClient(this);
            sendMessage(new NetMessage().setCammand(ECammand.ONLINE_PASS)
                        .setMessage(id));
            server.publishMessage("客户端[" + id + "] 上线");
            return;
        }
        closeChannel();
    }


    boolean cheakClient(String message) {
        int index = message.indexOf("#");
        String hashCode = message.substring(index + 1);
        String para = message.substring(0,index);
        if (para.hashCode() == Integer.valueOf(hashCode)) {
            return true;
        }
        return false;
    }

     void whoAreYou() {
        sendMessage(new NetMessage().setCammand(ECammand.WHO_ARE_YOU));
    }

     void outOfRoom() {
        sendMessage(new NetMessage().setCammand(ECammand.OUT_OF_ROOM));
        closeChannel();
    }

}
