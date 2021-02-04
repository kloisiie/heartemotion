package com.hyuga.xinlv.rnModule.shouhuan;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.hyuga.xinlv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HeartRateChart extends LinearLayout {
    private static final String TAG = "HeartRateChart";
    private LineChart chart;
    public int type; // 1 正常心率图表   2. 心率时间段选择
    public ReadableMap data1;
    public ReadableArray data;
    public ReadableArray timeData;

    public HeartRateChart(Context context) {
        super(context);
        config();
    }

    public HeartRateChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        config();
    }

    public HeartRateChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        config();
    }

    public HeartRateChart(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        config();
    }

    private void config() {
        LayoutInflater.from(getContext()).inflate(R.layout.heart_rate_chart, this, true);
        chart = findViewById(R.id.chart_view);
        // background color
        chart.setBackgroundColor(Color.WHITE);

        // disable description text
        chart.getDescription().setEnabled(true);

        // enable touch gestures
        chart.setTouchEnabled(true);

        chart.setDrawGridBackground(false);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.setDescription(null);
        //chart.setScaleXEnabled(true);
        // chart.setScaleYEnabled(true);

        // force pinch zoom along both axis
        chart.setPinchZoom(false);
        chart.setMarker(null);
        chart.setMaxVisibleValueCount(150);
        //chart.zoom(1,1f,0,0);
        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(9);
            xAxis.setTextColor(Color.parseColor("#666666"));
            xAxis.setYOffset(8);
            xAxis.setAvoidFirstLastClipping(true);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    int IValue = (int) value;
                    int index = IValue / 5;
                    if (data.size() == 1) {
                        return "";
                    }
                    if (index > timeData.size() - 1) {
                        return "";
                    }
                    if (index > 0 && index < 15) {
                        return "";
                    }
                    String str = timeData.getString(index);
                    ;
                    String[] arr = str.split("[ ]");
                    return arr[1];
                }
            });
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
            yAxis.setAxisMaximum(140f);
            yAxis.setAxisMinimum(60f);
            yAxis.setXOffset(8);
            yAxis.setTextColor(Color.parseColor("#666666"));
        }


        {   // // Create Limit Lines // //
            LimitLine llXAxis = new LimitLine(9f, "Index 10");
            llXAxis.setLineWidth(4f);
            llXAxis.enableDashedLine(10f, 10f, 0f);
            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            llXAxis.setTextSize(10f);


            LimitLine ll1 = new LimitLine(150f, "Upper Limit");
            ll1.setLineWidth(4f);
            ll1.enableDashedLine(10f, 10f, 0f);
            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
            ll1.setTextSize(10f);


            LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
            ll2.setLineWidth(4f);
            ll2.enableDashedLine(10f, 10f, 0f);
            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
            ll2.setTextSize(10f);


            yAxis.addLimitLine(ll1);
            yAxis.addLimitLine(ll2);

        }

        Legend l = chart.getLegend();
        l.setEnabled(false);
    }

    public void setData1(ReadableMap data1) {
        this.data1 = data1;
        this.data = data1.getArray("arr");
        this.timeData = data1.getArray("timerArr");
        if (data == null || data.size() == 0) {
            chart.clear();
        } else {
            ArrayList<List<Entry>> arrayList = new ArrayList<>();
            int size = data.size();
            int index = -1;
            Entry lastEntry = null;
            for (int i = 0; i < size; i++) {
                List<Entry> dataSet = new ArrayList<>();
                ReadableMap map = data.getMap(i);
                int status = map.getInt("status");
                int tagType = map.getInt("tagType");
                ReadableArray list = map.getArray("list");
                int size1 = list.size();
                int middle = size1 / 2;
                for (int j = 0; j < size1; j++) {
                    index++;
                    int value = list.getInt(j);
                    if (i > 0 && j == 0) {
                        dataSet.add(lastEntry);
                    }
                    if (j == middle && this.type == 1) {
                        dataSet.add(new Entry(index * 5, value, getStatusDrawable(status)));
                    } else {
                        lastEntry = new Entry(index * 5, value);
                        dataSet.add(lastEntry);
                    }

                }
                arrayList.add(dataSet);
            }
            configData(arrayList, data);
            XAxis xAxis = chart.getXAxis();
            int count = Math.round((index / 100.0f) * 6);
            Log.d(TAG, "setData1: index " + index);
            Log.d(TAG, "setData1: count " + count);
            if (count < 1 || index < 15) {
                count = 1;
            }
            if (count > 6) {
                count = 6;
            }

            xAxis.setLabelCount(count, false);
//            float scalex =  (index+1)/72.0f;
//            chart.zoom(scalex,1f,0,0);

        }


    }

    private void configData(ArrayList<List<Entry>> chartData, ReadableArray data) {
        int size = chartData.size();
        List<ILineDataSet> sets = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Entry> values = chartData.get(i);
            ReadableMap map = data.getMap(i);
            int status = map.getInt("status");
            int tagType = map.getInt("tagType");
            String labelType = map.getString("labelType");
            LineDataSet set1;
            set1 = new LineDataSet(values, "DataSet " + i);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setHighlightEnabled(true);
            Drawable drawable = null;
            if (this.type == 1) {
                drawable = getStatusFillDrawable(status);
            } else {
                if (tagType == 1) {
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_blue);
                } else {
                    drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_green);
                }
            }

            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.TRANSPARENT);
            if (this.type == 1) {
                set1.setColor(Color.parseColor(getStatusLineColor(status)));
            } else {
                if (tagType == 1) {
                    set1.setColor(Color.parseColor("#5A60E9"));
                } else {
                    set1.setColor(Color.parseColor("#32BB82"));
                }
            }
            if (this.type == 1) {
                if (labelType.equals("ARITHMETIC")) {
                    set1.setFillDrawable(drawable);
                }else{
                    set1.setFillColor(Color.WHITE);
                }
            } else {
                set1.setFillDrawable(drawable);
            }

            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setIconsOffset(new MPPointF(0, -30));
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });
            sets.add(set1);
        }

        LineData data1 = new LineData(sets);
        data1.setValueTextSize(9f);
        data1.setDrawValues(false);
        // set data
        chart.setData(data1);
        chart.setVisibleXRangeMaximum(500);//设置可以显示多少个数据，超出的滑动才能见
        chart.setVisibleXRange(500, 500);
        chart.invalidate();

    }

    private Drawable getStatusDrawable(int status) {
        // 1高兴  2平稳  3烦躁  4 无情绪
        Drawable drawable = null;
        if (status == 1) {
            drawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_linelable1_20);
        } else if (status == 2) {
            drawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_linelable2_20);
        } else if (status == 3) {
            drawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_linelable3_20);
        } else {
            drawable = ContextCompat.getDrawable(getContext(), R.mipmap.icon_linelable4_20);
        }
        Log.d(TAG, "getStatusDrawable: ");
        return drawable;
    }

    private String getStatusLineColor(int status) {
        // 1高兴  2平稳  3烦躁  4 无情绪
        String color = null;
        if (status == 1) {
            color = "#FFA300";
        } else if (status == 2) {
            color = "#32BB82";
        } else if (status == 3) {
            color = "#FF5159";
        } else {
            color = "#707C93";
        }
        Log.d(TAG, "getStatusLineColor: " + color);
        return color;
    }

    private Drawable getStatusFillDrawable(int status) {
        // 1高兴  2平稳  3烦躁  4 无情绪
        Drawable drawable = null;
        if (status == 1) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_yellow);
        } else if (status == 2) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_green);
        } else if (status == 3) {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
        } else {
            drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_gray);
        }
        Log.d(TAG, "getStatusFillDrawable: " + drawable.toString());
        return drawable;
    }

}
