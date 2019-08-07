package com.b3log.example;

import com.b3log.example.client.TcpClient;
import com.b3log.example.server.TcpServer;

import java.util.Scanner;

public class Application {

    public static void main(String...args)throws Exception{
        int type = Integer.parseInt(args[0]);
        int port = Integer.parseInt(args[1]);
        String host = args[2];
        if (type == 0){
            TcpServer server = new TcpServer(port, host);
            server.startServer();
        } else if (type == 1){
            TcpClient client = new TcpClient(host, port);
            client.startClient();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String msg = scanner.next();
                client.sendMsg(msg);
            }
        }
    }
}
