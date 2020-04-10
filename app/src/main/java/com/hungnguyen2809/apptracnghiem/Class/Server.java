package com.hungnguyen2809.apptracnghiem.Class;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class Server {
    public static Socket mySocket;

    static {
        try {
            mySocket = IO.socket(StringURL.urlServer);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }




}
