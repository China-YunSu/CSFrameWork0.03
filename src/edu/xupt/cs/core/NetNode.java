package edu.xupt.cs.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetNode {
    private String ip;
    private int port;

    public NetNode(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
