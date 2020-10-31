package edu.xupt.cs.core;

import com.mec.util.PropertiesParse;
import edu.xupt.cs.action.abstract_.IActionProcess;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private ClientConversation clientConversation;
    private IClientProcess clientAction;
    private IActionProcess actionProcess;
    private static NetNode serverNode;
    private String id;


    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

     public static void loadNetConfigure(String filePath) {
        PropertiesParse pp = new PropertiesParse();
        pp.loadProperties(filePath);
        serverNode = new NetNode(pp.value("serverIp"), Integer.valueOf(pp.value("port")));
    }

    IClientProcess getClientAction() {
        return clientAction;
    }

    public void setActionProcess(IClientProcess actionProcess) {
        this.clientAction = actionProcess;
    }

    public boolean connectToServer() throws ClientActionNotSetException {
        if (clientAction == null) {
            throw new ClientActionNotSetException();
        }
        try {
            if (serverNode == null) {
                serverNode = new NetNode(INetConfigure.ip, INetConfigure.port);
            }
            Socket socket = new Socket(serverNode.getIp(), serverNode.getPort());
            clientConversation = new ClientConversation(socket, this);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void setClientAction(IClientProcess clientAction) {
        this.clientAction = clientAction;
    }

    IActionProcess getActionProcess() {
        return actionProcess;
    }

    public void setActionProcess(IActionProcess actionProcess) {
        this.actionProcess = actionProcess;
    }

    public void talkToOne(String id, String message) {
        clientConversation.expressOne(id, message);
    }

    public void toOthers(String message) {
        clientConversation.expressAll(message);
    }

    public void offLine() {
        if (clientAction.confirmOffline()) {
            clientConversation.offLine();
        }
    }

    public void request(String requset, String response, String parameter) {
        clientConversation.request(requset, response, parameter);
    }
}
