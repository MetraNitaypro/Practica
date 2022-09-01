package com.example.myapplication.ui;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Suck extends AsyncTask<Void, Void, String> {
    private String str1;

    @Override
    protected String doInBackground(Void... voids) {
        try {
            Socket socket = new Socket("192.168.56.1", 12000);
            ObjectInputStream in = new ObjectInputStream(socket .getInputStream());
            Byte a  =  in.readByte();
            str1 = a.toString();
            System.out.println(str1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return str1;
    }

    @Override
    protected void onPostExecute(String res) {
        System.out.println("Geme over");
        super.onPostExecute(res);
    }
}
