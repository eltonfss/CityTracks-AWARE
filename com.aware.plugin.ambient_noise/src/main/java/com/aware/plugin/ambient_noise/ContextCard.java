package com.aware.plugin.ambient_noise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aware.plugin.ambient_noise.Provider.AmbientNoise_Data;
import com.aware.utils.IContextCard;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class ContextCard implements IContextCard {

    LineChart ambient_chart;
    LineData ambient_plot = new LineData();

    TextView decibels;
    TextView ambient_noise;

    Context context;

    /**
     * Constructor for Stream reflection
     */
    public ContextCard() {
    }

    public View getContextCard(Context context) {

        this.context = context;

        LayoutInflater sInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View card = sInflater.inflate(R.layout.ambient_layout, null);

        ambient_chart = (LineChart) card.findViewById(R.id.ambient_chart);
        ambient_chart.setData(ambient_plot); //set an empty line plot initially

        ViewGroup.LayoutParams params = ambient_chart.getLayoutParams();
        params.height = 300;
        ambient_chart.setLayoutParams(params);
        ambient_chart.setBackgroundColor(Color.WHITE);
        ambient_chart.setDrawGridBackground(false);
        ambient_chart.setDrawBorders(false);
        ambient_chart.getAxisRight().setEnabled(false);
        ambient_chart.getXAxis().setEnabled(false);
        ambient_chart.setDragEnabled(false);
        ambient_chart.setScaleEnabled(false);
        ambient_chart.setPinchZoom(false);
        ambient_chart.getLegend().setForm(Legend.LegendForm.LINE);
        ambient_chart.setDescription("");
        ambient_chart.setContentDescription("");

        YAxis left = ambient_chart.getAxisLeft();
        left.setDrawLabels(true);
        left.setDrawGridLines(true);
        left.setDrawAxisLine(true);
        left.setGranularity(10);

        decibels = (TextView) card.findViewById(R.id.decibels);
        ambient_noise = (TextView) card.findViewById(R.id.ambient_noise);

        updateGraph(context);

        IntentFilter filter = new IntentFilter();
        filter.addAction(Plugin.ACTION_AWARE_PLUGIN_AMBIENT_NOISE);
        context.registerReceiver(audioListener, filter);

        return card;
    }

    private void updateGraph(Context context) {
        LineData currentPlot = ambient_chart.getLineData();
        ILineDataSet set = currentPlot.getDataSetByIndex(0); //we are only plotting one
        if (set == null) {
            LineDataSet audioDataSet = new LineDataSet(null, "Audio sample");
            audioDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            audioDataSet.setColor(Color.parseColor("#33B5E5"));
            audioDataSet.setHighLightColor(Color.parseColor("#B0C4DE"));
            audioDataSet.setDrawValues(false);
            audioDataSet.setDrawCircles(false);

            set = audioDataSet;
            currentPlot.addDataSet(set);
        }

        Cursor latest = context.getContentResolver().query(AmbientNoise_Data.CONTENT_URI, null, null, null, AmbientNoise_Data.TIMESTAMP + " DESC LIMIT 1");
        if (latest != null && latest.moveToFirst()) {
            decibels.setText(String.format("%.1f", latest.getDouble(latest.getColumnIndex(AmbientNoise_Data.DECIBELS))) + " dB");
            ambient_noise.setText(latest.getInt(latest.getColumnIndex(AmbientNoise_Data.IS_SILENT)) == 0?"Noisy":"Silent");

            byte[] raw_audio = latest.getBlob(latest.getColumnIndex(AmbientNoise_Data.RAW));
            for(int i = 0; i < raw_audio.length; i++) {
                currentPlot.addXValue("" + set.getEntryCount()+1);
                currentPlot.addEntry(new Entry(raw_audio[i], set.getEntryCount()), 0);
            }
        }
        if (latest != null && !latest.isClosed()) latest.close();

        ambient_chart.notifyDataSetChanged();
        ambient_chart.setVisibleXRangeMaximum(1000);
        ambient_chart.moveViewToX(currentPlot.getXValCount()-1);
    }

    private AudioPlotListener audioListener = new AudioPlotListener();
    public class AudioPlotListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGraph(context);
        }
    }
}
