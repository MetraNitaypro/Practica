package com.example.myapplication.ui.dashboard;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.MyService;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;
import com.example.myapplication.ui.Suck;

public class DashboardFragment extends Fragment {
    private boolean flag = true;
    private Button but1;
    private Button but2;
    private Suck suck;
    private FragmentDashboardBinding binding;
    public static TextView tx1;
    public TextView tx2;
    public TextView tx3;
    public TextView tx4;
    public final static String brodcast = "Start";
    public final static String param_1 = "Param_1";
    public final static String SendServ = "SendServ";
    ServiceConnection serviceConnection;
    private Messenger mMessenger;
    Integer add = 0;
    private boolean isBound;
    private final Messenger mActivityMessenger = new Messenger(new ResponseHandler(getActivity()));
    Intent intent_1;
    IntentFilter filter;
    BroadcastReceiver smsBroadcastReceiver;


    private static class ResponseHandler extends Handler {
        private Context mContext;

        ResponseHandler(Context context) {
            mContext = context;
        }


        @Override
        public void handleMessage(Message msg) {
            String messageText;
            messageText = msg.getData().getString("message_res");
            System.out.println(messageText);
            tx1.append(messageText);


        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        tx1 = binding.textView;
        tx3 = binding.textView3;
        // Typeface type = Typeface.createFromAsset(g,"fonts/font1.ttf");
        // myTextView.setTypeface(type);
        tx1.setBackgroundResource(R.color.tvBackground);
        tx1.setMovementMethod(new ScrollingMovementMethod());
        tx2 = binding.textView2;
        tx4 = binding.textView4;
       // tx2.setBackgroundResource(R.color.tvBackground);
        //tx3.setBackgroundResource(R.color.tvBackground);
       // tx4.setBackgroundResource(R.color.tvBackground);
        but1 = binding.button3;
        but2 = binding.button;
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(but2.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                if(flag) {
                    serviceConnection = new ServiceConnection() {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) {
                            Log.d("ServiceConnection", "connected");
                            mMessenger = new Messenger(service);
                            isBound = true;
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) {
                            Log.d("ServiceConnection", "disconnected");
                            isBound = false;
                            mMessenger = null;
                        }
                    };
                    Intent intent = new Intent(getActivity(), MyService.class);
                    System.out.println(tx3.getText());

                    intent.putExtra("ip", tx3.getText().toString());
                    intent.putExtra("port", tx4.getText().toString());
                    System.out.println(intent.getStringExtra("ip"));
                    getActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                    flag = false;
                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("message", "dir");
                    message.setData(bundle);
                    message.replyTo = mActivityMessenger;
                    try {
                        if (mMessenger != null) mMessenger.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    getActivity().unbindService(serviceConnection);
                    flag = true;
                }

            }
            ScrollView mScrollView = binding.SCROLLERID;

            private void scrollToBottom()
            {
                mScrollView.post(new Runnable()
                {
                    public void run()
                    {
                        mScrollView.smoothScrollTo(0, tx1.getBottom());
                    }
                });
            }
        });
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent("MEE");
                getActivity().sendBroadcast(inte);
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(but1.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                add++;
                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("message", tx2.getText().toString());
                message.setData(bundle);
                message.replyTo = mActivityMessenger;
                try {
                    if (mMessenger != null) mMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart");
        //but1.callOnClick();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume");
        //but1.callOnClick();
    }

    @Override
    public void onDestroy() {
        //getActivity().unregisterReceiver(smsBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        // System.out.println("Detech");
        // getActivity().unregisterReceiver(smsBroadcastReceiver);
        super.onDetach();
    }

    @Override
    public void onPause() {
        //getActivity().unregisterReceiver(smsBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onStop() {
        // getActivity().unregisterReceiver(smsBroadcastReceiver);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        //getActivity().unregisterReceiver(smsBroadcastReceiver);
        super.onDestroyView();
        binding = null;
    }

}