package edu.xupt.cs.core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class Communicate implements Runnable {
    public Socket socket;
    protected DataInputStream dis;
    protected DataOutputStream dos;
    private volatile boolean goon;
    private final Thread initThread;

    public Communicate(Socket socket) {
        this.socket = socket;
        initThread = new Thread(new ThreadTask());
        initThread.start();
    }

    @Override
    public void run() {
        while(goon) {
            try {
                String message = dis.readUTF();
                dealMessage(new NetMessage(message));
            } catch (IOException e) {
                if (goon) {
                    dealPeerDown();
                }
                closeChannel();
            }
        }
        closeChannel();
    }

    public void sendMessage(NetMessage netMessage) {
        try {
            if (initThread.isAlive()) {
                initThread.join();
            }
            dos.writeUTF(netMessage.toString());
        } catch (IOException e) {
            closeChannel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closeChannel() {
        goon = false;
        if (dos != null) {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                dos = null;
            }
        }

        if (dis != null) {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                dis = null;
            }
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                socket = null;
            }
        }
    }

    private class ThreadTask implements Runnable {
        @Override
        public void run() {
            try {
                dos = new DataOutputStream(socket.getOutputStream());
                dis = new DataInputStream(socket.getInputStream());
                goon = true;
                new Thread(Communicate.this).start();
            } catch (IOException e) {
                goon =false;
            }
        }
    }

    public abstract void dealPeerDown();
    public abstract void dealMessage(NetMessage netMessage);

}
