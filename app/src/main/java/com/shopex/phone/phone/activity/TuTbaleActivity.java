package com.shopex.phone.phone.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.shopex.phone.phone.R;
import com.shopex.phone.phone.common.BaseActivity;
import com.shopex.phone.phone.library.toolbox.PreferencesUtils;
import com.shopex.phone.phone.library.toolbox.TimeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liufanglin on 2019/5/22.
 */

public class TuTbaleActivity extends BaseActivity implements
        OnChartValueSelectedListener {

    private BarChart chart;
    private PieChart chart2;

    private YAxis leftAxis;
    private YAxis rightAxis;
    private XAxis xAxis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chary);
        setCenterTitle("chart");
        chart = (BarChart) findViewById(R.id.chart1);
        chart2 = (PieChart) findViewById(R.id.chart2);

        chart.setOnChartValueSelectedListener(this);
        chart.setDrawValueAboveBar(true);//将Y数据显示在点的上方

        // mChart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(true);//挤压缩放

        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        chart.setScaleYEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);//双击缩放
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//x轴位置
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        //自定义   MarkerView

        // define an offset to change the original position of the marker
        // (optional)
        // mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());

        // set the marker to the chart
        Legend l = chart.getLegend();//图例
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        l.setTextSize(10f);
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setWordWrapEnabled(true);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);



        leftAxis = chart.getAxisLeft();
        rightAxis = chart.getAxisRight();
        xAxis = chart.getXAxis();
        chart.getAxisRight().setEnabled(false);
        showBarChartAlong();
        bing();
        setData();
    }



    public void bing(){
        chart2.setEntryLabelColor(Color.WHITE);
        chart2.setEntryLabelTextSize(12f);

    }

    private void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        JSONArray jsonArray=getArray(TuTbaleActivity.this, TimeUtils.getCurrentDay());
        if (jsonArray==null||jsonArray.length()==0){
            return;
        }

        List<BarEntry> yVals = new ArrayList<>();


//        for (int i=0;i<jsonArray.length();i++){
//            JSONObject jsonObject=jsonArray.optJSONObject(i);
//            yVals.add(new BarEntry(i, jsonObject.optLong("num")));
//        }
        String[] xValues = new String[jsonArray.length()];
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=jsonArray.optJSONObject(i);
            xValues[i]=jsonObject.optString("time");
        }

        for (int i = 0; i < jsonArray.length() ; i++) {
            JSONObject jsonObject=jsonArray.optJSONObject(i);

            entries.add(new PieEntry(jsonObject.optLong("num"),
                    xValues[i]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
//        data.setValueFormatter(new PercentFormatter(chart2));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        chart2.setData(data);

        // undo all highlights
        chart2.highlightValues(null);

        chart2.invalidate();
    }

    private void showBarChartAlong() {
        try {


        // 每一个BarDataSet代表一类柱状图
        JSONArray jsonArray=getArray(TuTbaleActivity.this, TimeUtils.getCurrentDay());
        if (jsonArray==null||jsonArray.length()==0){
            return;
        }
        List<BarEntry> yVals = new ArrayList<>();


        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=jsonArray.optJSONObject(i);
            yVals.add(new BarEntry(i, jsonObject.optLong("num")));
        }

        BarDataSet barDataSet = new BarDataSet(yVals, "");
        //是否显示顶部的值
        barDataSet.setDrawValues(true);
//        文字的大小
        barDataSet.setValueTextSize(9f);

        barDataSet.setFormLineWidth(1f);
        barDataSet.setFormSize(15.0f);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        BarData data = new BarData(dataSets);
//      设置宽度
        data.setBarWidth(0.3f);
        //设置X轴的刻度数


//        String[] xValues = {"东城", "西城", "朝阳", "丰台", "石景山", "海淀区", "海淀区"};
        String[] xValues = new String[jsonArray.length()];
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=jsonArray.optJSONObject(i);
            xValues[i]=jsonObject.optString("time");
        }

        xAxis.setLabelCount(yVals.size() + 1, true);
        xAxis.setDrawLabels(true);
        IAxisValueFormatter xAxisFormatter = new XAxisValueFormatter(xValues);
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setTextColor(Color.parseColor("#d5d5d5"));
        xAxis.setAxisLineColor(Color.parseColor("#d5d5d5"));
//        IAxisValueFormatter custom = new MyYAxisValueFormatter(yValues);
//        leftAxis.setValueFormatter(custom);
//        leftAxis.setLabelCount(yValues.length + 1, false);
        leftAxis.setAxisLineColor(Color.parseColor("#d5d5d5"));
//        设置Y轴的最小值和最大值
        leftAxis.setAxisMaximum(1000f);
        leftAxis.setAxisMinimum(0f);
        chart.setData(data);
        }catch (Exception e){

        }
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {
    }

    public class XAxisValueFormatter implements IAxisValueFormatter {

        private String[] xValues;

        public XAxisValueFormatter(String[] xValues) {
            this.xValues = xValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return xValues[(int) value];
        }

    }

    public class MyYAxisValueFormatter implements IAxisValueFormatter {
        DecimalFormat mFormat = new DecimalFormat("#,###.##");

        private String[] xValues;

        public MyYAxisValueFormatter(String[] yValues) {
            xValues = yValues;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
//            Log.e("TAG", "xValues[(int) value]====="+xValues[(int) value]);
            return mFormat.format(value) + "%";
        }
    }

    public JSONArray getArray(Context context, String day){
        String array= PreferencesUtils.getString(context,day);
        try {
            if (!TextUtils.isEmpty(array)){
                JSONArray jsonArray=new JSONArray(array);
                if (jsonArray.length()>0){

                    return jsonArray;
                }
                return null;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



}
