package com.example.myapplication.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentHomeBinding;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class HomeFragment extends Fragment {
    public BarGraphSeries<DataPoint> series;
    public BarGraphSeries<DataPoint> series1;
    public BarGraphSeries<DataPoint> series2;
    public BarGraphSeries<DataPoint> series3;
    private Integer w = 0;
    private GraphView graph;
    private FragmentHomeBinding binding;
    private BroadcastReceiver br;
    private TextView me;
    public final static String BROADCAST_ACTION = "ru.startandroid.develop.p0961servicebackbroadcast";
    private int[] arr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //omeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        me = binding.textView3;
        graph = (GraphView) binding.graph;
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);
        series = new BarGraphSeries<DataPoint>(new DataPoint[]{
                new DataPoint(0, 0),
                new DataPoint(0, 0),
                new DataPoint(0, 0),
                new DataPoint(0, 0),
                new DataPoint(0, 0)
        });
        graph.addSeries(series);
        graph.getViewport().setMaxX(100);
        graph.getViewport().setMaxY(100);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMinY(0);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        arr = new int[4];

        return root;
    }

    public static String extractNumber(final String str) {

        if (str == null || str.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
                found = true;
            } else if (found) {
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }

        return sb.toString();
    }

    @Override

    public void onStart() {
        super.onStart();
        br = new BroadcastReceiver() {
            // действия при получении сообщений
            public void onReceive(Context context, Intent intent) {
                //System.out.println("поймал дома");
                w++;
                String dd = intent.getStringExtra("res");

                int gg = dd.indexOf("+- CPU Total");
                if (gg >= 0) {
                    String roar = dd.substring(gg);
                    String h = extractNumber(roar);
                    arr[0] = Integer.valueOf(h);
                }
                gg = dd.indexOf("+- GPU Core");
                if (gg >= 0) {
                    String roar = dd.substring(gg);
                    String h = extractNumber(roar);
                    arr[1] = Integer.valueOf(h);
                }
                gg = dd.indexOf("+- Memory");
                if (gg >= 0) {
                    String roar = dd.substring(gg);
                    String h = extractNumber(roar);
                    arr[2] = Integer.valueOf(h);
                }
                gg = dd.indexOf("+- GPU Memory");
                if (gg >= 0) {
                    String roar = dd.substring(gg);
                    String h = extractNumber(roar);
                    arr[3] = Integer.valueOf(h);
                }


                series = new BarGraphSeries<DataPoint>(new DataPoint[]{
                        new DataPoint(10, arr[0]),


                });
                series1 = new BarGraphSeries<DataPoint>(new DataPoint[]{

                        new DataPoint(30, arr[1]),


                });
                series2 = new BarGraphSeries<DataPoint>(new DataPoint[]{

                        new DataPoint(50, arr[2]),


                });
                series3 = new BarGraphSeries<DataPoint>(new DataPoint[]{

                        new DataPoint(70, arr[3]),
                });
                series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return Color.rgb((int) Math.abs(data.getY()*255/100), 20, 100);
                    }
                });
                series1.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return Color.rgb((int) Math.abs(data.getY()*255/100), 20, 100);
                    }
                });
                series2.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return Color.rgb((int) Math.abs(data.getY()*255/100), 20, 100);
                    }
                });
                series3.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                    @Override
                    public int get(DataPoint data) {
                        return Color.rgb((int) Math.abs(data.getY()*255/100), 20, 100);
                    }
                });

                series.setSpacing(20);
                series1.setSpacing(20);
                series2.setSpacing(20);
                series3.setSpacing(20);
                series.setTitle("CPU");
                series1.setTitle("RAM");
                series2.setTitle("Temperature GPU");
                series3.setTitle("Memory GPU");

                //series2.setTitle("bar");
                graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
// draw values on top
                series.setDrawValuesOnTop(true);
                series.setValuesOnTopColor(Color.RED);
                series1.setDrawValuesOnTop(true);
                series1.setValuesOnTopColor(Color.RED);
                series2.setDrawValuesOnTop(true);
                series2.setValuesOnTopColor(Color.RED);
                series3.setDrawValuesOnTop(true);
                series3.setValuesOnTopColor(Color.RED);
                DataPoint dataPoint = new DataPoint(w, w);
                if (HomeFragment.this.isVisible()) {
                    //System.out.println(dd);
                    //System.out.println(w1);
                    graph.removeAllSeries();
                    graph.addSeries(series);
                    graph.addSeries(series1);
                    graph.addSeries(series2);
                    graph.addSeries(series3);
                    me.append(w.toString());
                    //series.appendData(dataPoint,false,10);
                }


                //
                //series.appendData(dataPoint,false,0);
                // graph.addSeries(series);
                // graph.addSeries(series);

            }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        // регистрируем (включаем) BroadcastReceiver
        getActivity().registerReceiver(br, intFilt);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}