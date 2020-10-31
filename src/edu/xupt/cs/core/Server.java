package edu.xupt.cs.core;

import edu.xupt.cs.action.abstract_.IActionProcess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server implements Runnable ,ISpeaker{
    private ClientPool clientPool;
    private TempClientPool tempClientPool;
    private ServerSocket serverSocket;
    private volatile boolean goon;
    private final int MAX_COUNT = 10;
    private IActionProcess actionProcess;
    private List<IListener> listeners = new ArrayList<>();

    public Server() {
        tempClientPool = new TempClientPool();
        clientPool = new ClientPool();
    }

    public boolean isStartUp() {
        return goon;
    }

    public IActionProcess getActionProcess() {
        return actionProcess;
    }

    public void setActionProcess(IActionProcess actionProcess) {
        this.actionProcess = actionProcess;
    }

    TempClientPool getTempClientPool() {
        return tempClientPool;
    }

    ClientPool getClientPool() {
        return clientPool;
    }

    public String getServerIp() {
        return serverSocket.getInetAddress().getHostAddress();
    }

    public void openServer() {
        try {
            serverSocket = new ServerSocket(INetConfigure.port);
            publishMessage("服务器启动成功！");
            publishMessage("等待客户端连接......！");
            goon = true;
            new Thread(this).start();
        } catch (IOException e) {
            goon = false;
        }
    }

    @Override
    public void run() {
        while(goon) {
            try {
                Socket client_socket = serverSocket.accept();
                ServerConversation serverConversation = new ServerConversation(client_socket,this);
                if (tempClientPool.getClientCount() + clientPool.getClientCount() >= MAX_COUNT) {
                    serverConversation.outOfRoom();
                    continue;
                }
                tempClientPool.addClient(serverConversation);
                serverConversation.whoAreYou();
            } catch (IOException e) {
                close();
            }
        }
        close();
    }

    private void close() {
        goon = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
            } finally {
                serverSocket = null;
            }
        }
    }

    public void forceDown() {
        if (!tempClientPool.isEmpty()) {
            ServerConversation serverConversation = tempClientPool.popClient();
            serverConversation.forceDown();
        }

        for (ServerConversation serverConversation : clientPool.clientList()) {
            serverConversation.forceDown();
            clientPool.removeClient(serverConversation.getId());
        }
    }

    public void shutDown() {
        if (tempClientPool.getClientCount() + clientPool.getClientCount() > 0) {
            publishMessage("客户端尚未全部关闭！");
            return;
        }

        close();
    }

    @Override
    public void addListener(IListener listener) {
        if (listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);
    }

    @Override
    public void removeListener(IListener listener) {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    @Override
    public void publishMessage(String message) {
        for (IListener listener : listeners) {
            listener.processMessage(message);
        }
    }

    public void talkToOne(String targetId,String message) {
        clientPool.getClient(targetId).toOne(message);
    }

    public void talkToOthers(String sourceId,String message) {
        List<ServerConversation> clients = clientPool.clientsExceptOne(sourceId);
        for (ServerConversation client : clients) {
            client.talkToOthers(message);
        }
    }
}
