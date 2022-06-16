package server;

import controller.DbConnectionManager;
import controller.UserManager;
import response.Response;
import utils.Serializator;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.sql.Connection;

public class ResponseSandler implements Runnable{
    private SelectionKey key;
    private DbConnectionManager manager;


    public ResponseSandler (SelectionKey key , DbConnectionManager connectionManager){
        this.key = key;
        this.manager = connectionManager;
    }


    @Override
    public void run() {
        SocketChannel client = (SocketChannel) key.channel();
        Response response = UserManager.getResponse(client);
        if (response.getText().equals("exit")) {
            key.cancel();
            Connection connection = UserManager.getConnection(client);
            manager.addCollection(connection);
            UserManager.removeConnection(client);
            UserManager.removeDao(client);
            UserManager.removeResponse(client);
        }
        byte[] output = Serializator.serializeObject(response);
        ByteBuffer buffer = ByteBuffer.wrap(output);
        try {
            while (client.write(buffer) > 0) {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
