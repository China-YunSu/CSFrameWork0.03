package edu.xupt.cs.core;

import java.util.*;

public class ClientPool {
    private Map<String,ServerConversation> clientPool;

    {
        clientPool = new HashMap<String,ServerConversation>();
    }

    public void addClient(ServerConversation client) {
        String id = client.getId();
        if (clientPool.containsKey(id)) {
            return;
        }
        clientPool.put(id,client);
    }

    public void removeClient(String id) {
        if (clientPool.containsKey(id)) {
            clientPool.remove(id);
        }
    }

    public List<ServerConversation> clientList() {
        List<ServerConversation> clients = new LinkedList<>();
        for (String id : clientPool.keySet()) {
            clients.add(clientPool.get(id));
        }

        return clients;
    }

    public List<ServerConversation> clientsExceptOne(String fireClientId) {
        List<ServerConversation> clients = new LinkedList<>();
        for (Map.Entry<String, ServerConversation> entry : clientPool.entrySet()) {
            if (entry.getKey().equalsIgnoreCase(fireClientId)) {
                continue;
            }
            clients.add(entry.getValue());
        }
        return  clients;
    }

    public ServerConversation getClient(String id) {
        return  clientPool.get(id);
    }

    public int getClientCount() {
        return clientPool.size();
    }


}
