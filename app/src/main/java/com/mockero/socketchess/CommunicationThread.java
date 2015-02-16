package com.mockero.socketchess;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Created by ddwiyaksha on 2/17/15.
 */
public class CommunicationThread extends Thread {

    private final String TAG = getClass().getSimpleName();
    private Socket socket;
    private InputStream istream;
    private static boolean running = true;

    private final static String server = "xinuc.org";
    private final static int port = 7387;
    private BufferedReader reader;
    private String message;


    public CommunicationThread() {

    }

    @Override
    public void run() {

        while(running) {
            try {
                if(socket == null || !socket.isConnected()) {
                    InetAddress address = InetAddress.getByName(server);
                    socket = new Socket(address, port);
                    socket.setKeepAlive(true);
                    istream = socket.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(istream));
                }

                if(reader != null) {
                    message = reader.readLine();
                }
//                reader.close();
                Log.e(TAG, "input : " + message);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "socket runnable error : " + e.getMessage());
            }
        }
    }

    public void cancel() {
        try {
            running = false;
            reader.close();
            istream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
