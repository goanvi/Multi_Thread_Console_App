package server;

import request.Request;
import utils.Serializator;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;

public class RequestHandler implements Callable<Request> {
    private SelectionKey key;

    public RequestHandler(SelectionKey key){
        this.key = key;
    }


    @Override
    public Request call() throws Exception {
        SocketChannel client =(SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(5000);
        client.read(buffer);
        return Serializator.deserializeObject(buffer.array());
    }
}
