package client;

import request.Request;
import response.Response;
import utils.Serializator;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class Communicate {
    Request request;
    Socket socket;

    public Communicate(Socket socket) {
        this.socket = socket;
    }

    public void send(Request req) {
        try {
            request=req;
            byte[] output = Serializator.serializeObject(req);
            socket.getOutputStream().write(output);
        } catch (SocketException e) {
            Client.waitingConnection();
            send(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response get() {
        Response response = null;
        try {
            byte[] input = new byte[10000];
            int size = socket.getInputStream().read(input);
            byte[] formatInput = Arrays.copyOf(input, size);
            response = Serializator.deserializeObject(formatInput);
            return response;
        } catch (SocketException exception) {
            Client.waitingConnection();
            send(request);
            return get();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}

