package edu.xupt.cs.core;

import java.util.LinkedList;
import java.util.List;

public class TempClientPool {
    private List<ServerConversation> clients;
    {
        clients = new LinkedList<>();
    }

    public void addClient(ServerConversation client) {
        if (!clients.contains(client)) {
            clients.add(client);
        }
    }

    public boolean removeClient(ServerConversation client) {
        return clients.remove(client);
    }

    public ServerConversation popClient() {
        int index = getClientCount() - 1;
        return clients.remove(index);
    }

    public int getClientCount() {
        return clients.size();
    }

    public boolean isEmpty() {
        return clients.isEmpty();
    }
}
