package f.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author ltf
 * @since 2018/1/10, 下午3:55
 */
public class Server {

    private ServerSocket mServerSocket;


    public Server() {
        try {
            mServerSocket = new ServerSocket(51888);

            Socket socket = mServerSocket.accept();
            //socket.


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
