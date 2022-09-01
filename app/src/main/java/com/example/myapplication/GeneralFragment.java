package com.example.myapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentNotificationsBinding;

import java.lang.ref.WeakReference;
import java.net.InetAddress;

public class GeneralFragment extends Fragment {

    private FragmentNotificationsBinding binding;
    private static final String TAG = "NetworkSniffTask";
    private WeakReference<Context> mContextRef;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GeneralViewModel notificationsViewModel =
                new ViewModelProvider(this).get(GeneralViewModel.class);
        //        Context context = mContextRef.get();
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Log.e(TAG, "Let's sniff the network");

        try {
            // Context context = mContextRef.get();

            ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            WifiManager wm = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo connectionInfo = wm.getConnectionInfo();
            int ipAddress = connectionInfo.getIpAddress();
            String ipString = Formatter.formatIpAddress(ipAddress);
            Log.e(TAG, "activeNetwork: " + String.valueOf(activeNetwork));
            Log.e(TAG, "ipString: " + String.valueOf(ipString));
            String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);
            Log.e(TAG, "prefix: " + prefix);
            for (int i = 0; i < 255; i++) {
                String testIp = prefix + String.valueOf(i);
                InetAddress name = InetAddress.getByName(testIp);
                String hostName = name.getCanonicalHostName();
                System.out.println("hi");
                if (name.isReachable(10))
                    Log.e(TAG, "Host:" + hostName);
            }

        } catch (Throwable t) {
            Log.e(TAG, "Well that's not good.", t);
        }

        //WifiInfo connectionInfo = wm.getConnectionInfo();
        //int ipAddress = connectionInfo.getIpAddress();
        //String ipString = Formatter.formatIpAddress(ipAddress);
        //Log.e(TAG, "activeNetwork: " + String.valueOf(activeNetwork));
        //Log.e(TAG, "ipString: " + String.valueOf(ipString));
        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}