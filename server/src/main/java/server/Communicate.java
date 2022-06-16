package server;

import request.Request;
import response.Response;

import java.io.*;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

public class Communicate {
    SocketChannel socket;
    ObjectInput ois;
    ObjectOutputStream oos;

    public Communicate(SocketChannel socket) {
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.socket().getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ois = new ObjectInputStream(socket.socket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Request getRequest() {
        Request request = null;
        try {
            request = (Request) ois.readObject();
        } catch (SocketException e) {

        } catch (EOFException ex) {

        } catch (IOException e) {
            System.out.println("ioe");
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return request;
    }

    public void sendResponse(Response response) {
        try {
            oos.writeObject(response);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SocketChannel getSocket() {
        return socket;
    }
}
