package com.mockero.socketchess;

import android.content.Context;
import android.content.Intent;
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
    private Context context;
    private Intent intent;


    public CommunicationThread(Context context) {
        this.context = context;
        intent = new Intent("com.mockero.socketchess.piece");
    }

    @Override
    public void run() {

        intent.putExtra("piece", "Starting..");
        context.sendBroadcast(intent);
        Log.e(TAG, "Running : " + running);
        while(running) {
            try {
                if(socket == null || !socket.isConnected()) {
                    intent.putExtra("piece", "Reconnecting..");
                    context.sendBroadcast(intent);

                    Log.e(TAG, "Reconnecting..");
                    InetAddress address = InetAddress.getByName(server);
                    socket = new Socket(address, port);
                    socket.setKeepAlive(true);
                    istream = socket.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(istream));
                }

                if(reader != null) {
                    message = reader.readLine();
                    intent.putExtra("piece", message);
                    context.sendBroadcast(intent);
                }
//                reader.close();
                Log.e(TAG, "input : " + message);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "socket runnable error : " + e.getMessage());
            }
        }
    }

    public void restart() {
        running = true;
    }

    public void cancel() {
        try {
            running = false;
            if(reader != null) reader.close();
            if(istream != null) istream.close();
            if(socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
