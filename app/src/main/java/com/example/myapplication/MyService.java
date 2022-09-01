package com.example.myapplication;

import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.ui.dashboard.DashboardFragment;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyService extends Service {
    private boolean tagOut = true;
    private ExecutorService es;
    public Socket sock;
    public ObjectInputStream in;
    public ObjectOutputStream out;
    public Intent intent_1;
    public IntentFilter filter;
    public Intent intent_2;
    Messenger messenger = new Messenger(new IncomingHandler());
    Messenger activityMessenger;
    public Socket mySocket;
    public Socket mySocket1;
    public Socket mySocket2;
    public Socket mySocket3;
    public Socket mySocket4;
    public Socket mySocket5;
    private boolean tag34 = true;
    private boolean fuck = true;
    public final static String BROADCAST_ACTION = "ru.startandroid.develop.p0961servicebackbroadcast";
    public String ip;
    public String port1;
    public int port;

    // C:\Users\artem\Desktop\Hardwaremonitor\OpenHardwareMonitorConsole\OpenHardwareMonitorReport.exe | findstr intelcpu/0/load/0
    class receiver implements Runnable {
        private ObjectInputStream in;
        private Socket sock;
        private byte[] buffer = new byte[1024];
        private byte[] buffer1;

        receiver(Socket sock) {
            this.sock = sock;
        }

        @Override
        public void run() {
            try {
                in = new ObjectInputStream(this.sock.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (tagOut) {
                Integer val = 0;
                Integer val1 = 2;

                try {
                    val1 = in.read(buffer);
                    buffer1 = Arrays.copyOf(buffer, val1);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                String res = new String();
                try {
                    res = new String(buffer1, "CP866");
                } catch (UnsupportedEncodingException e) {

                }
                Message message;
                Bundle bundle = new Bundle();
                message = Message.obtain();
                bundle.putString("message_res", res);
                message.setData(bundle);
                try {
                    if (activityMessenger != null)
                        activityMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class receiver1 implements Runnable {
        private ObjectInputStream in;
        private Socket sock;

        private byte[] buffer = new byte[1024];
        private byte[] buffer1;
        private int port;

        receiver1(Socket sock, int port) {
            this.sock = sock;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                this.sock = new Socket(ip, port);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in = new ObjectInputStream(this.sock.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (tagOut) {
                Integer val = 0;
                Integer val1 = 2;
                try {
                    val1 = in.read(buffer);
                    buffer1 = Arrays.copyOf(buffer, val1);
                } catch (IOException e) {

                    e.printStackTrace();
                    return;
                }
                String res = new String();
                try {
                    res = new String(buffer1, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                }
                Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
                intent.putExtra("res", res);
                sendBroadcast(intent);
            }
        }
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int val = 1;
            if (fuck)
                activityMessenger = msg.replyTo;
            fuck = false;
            try {
                if (out == null) return;
                System.out.println("Hadler");
                byte[] buffer = new byte[1024];
                out.writeBytes(msg.getData().getString("message"));
                //out.write("dir");

                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.handleMessage(msg);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        try {
            ip = intent.getStringExtra("ip");
            port = Integer.valueOf(intent.getStringExtra("port"));
            if (tag34) {
                tagOut = true;
                try {
                    mySocket = new Socket(ip, port);
                    out = new ObjectOutputStream(mySocket.getOutputStream());

                    tag34 = false;
                    Thread myThread = new Thread(new receiver(mySocket));
                    myThread.start();
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Thread myThread1 = new Thread(new receiver1(mySocket1, port + 1));
                    myThread1.start();
                    Thread myThread2 = new Thread(new receiver1(mySocket2, port + 2));
                    myThread2.start();
                    Thread myThread3 = new Thread(new receiver1(mySocket3, port + 3));
                    myThread3.start();
                    Thread myThread4 = new Thread(new receiver1(mySocket4, port + 4));
                    myThread4.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    this.stopSelf();
                }
            }
        } catch (NumberFormatException e) {

        }
        return messenger.getBinder();
    }

    public void onCreate() {
        super.onCreate();
        Log.d("Hi", "MyService onCreate");
    }

    public void onDestroy() {
        super.onDestroy();
        tagOut = false;
        Log.d("HI", "MyService onDestroy");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Hi", "MyService onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }
}


