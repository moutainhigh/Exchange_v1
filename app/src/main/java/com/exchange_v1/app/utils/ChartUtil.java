package com.exchange_v1.app.utils;

import android.content.Context;
import android.graphics.Color;

import com.brightoilonline.c2b_phone.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by zhangzk on 2017/5/15.
 */

public class ChartUtil {

    //默认背景颜色静态方法
    public static LineData getLineData(Context context, String name, ArrayList<String> xVals, ArrayList<Entry> yVals) {
        return getLineData(context,name,xVals,yVals,context.getApplicationContext().getResources().getColor(R.color.lineBg));
    }

    //重载背景颜色静态方法
    public static LineData getLineData(Context context, String name, ArrayList<String> xVals, ArrayList<Entry> yVals, int bgColor) {

        LineDataSet set1;//set1 表示LineDataSet折线图在Y轴上的名字和值
        set1 = new LineDataSet(yVals, name);//每一个DataSet对象代表一组属于一个表的数据记录，它用来分离不同组的逻辑值，每种类型的图表对应不同DataSet的子类，对应不同样式。
        //设置选中坐标线不显示-----------start-------------
//        是否启用垂直方向上的高亮《是》
        set1.setDrawVerticalHighlightIndicator(true);
        //是否起用水平方向 上的高亮《否》
        set1.setDrawHorizontalHighlightIndicator(false);
        //设置选中坐标线不显示-----------end-------------
        //给set1 添加数据
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        //设置颜色
        set1.setColor(context.getApplicationContext().getResources().getColor(R.color.lineColor));
        //设置折线的线宽
        set1.setLineWidth(1f);
//        set1.setFillAlpha(255);
        //是否填充 true 表示填充
        set1.setDrawFilled(true);

//        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//        set1.setFillDrawable(drawable);
        //折线图下方填充颜色设置
        set1.setFillColor(bgColor);
        //图表上的数据点是否用小圆圈表示
        set1.setDrawCircles(true);
        // 设置节点是否为同心圆
        set1.setDrawCircleHole(true);
        // 设置圆形节点大小
        set1.setCircleSize(4f);
        // 圆形的颜色
        set1.setCircleColor(context.getApplicationContext().getResources().getColor(R.color.linePointBorder));
        // 设置节点同心圆内部的颜色
        set1.setCircleColorHole(context.getApplicationContext().getResources().getColor(R.color.linePointIn));
        //隐藏折线图每个数据点的值
        set1.setDrawValues(!set1.isDrawValuesEnabled());


        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);
        LineData data = new LineData(xVals, dataSets);


        return data;
    }

    //控件 mChart 数据data
    public static void showChartView(Context context, LineChart mChart, LineData data, float[] yyList) {
        showChartView(context,mChart,data,true,yyList);
    }

    //控件 mChart 数据data
    public static void showChartView(Context context, LineChart mChart, LineData data, boolean isVisible, float[] yyList) {

        //排序取出最大最下值
        Arrays.sort(yyList);
        float min = yyList[0];
        float max = yyList[yyList.length - 1];

        // 数据描述
        mChart.setDescription("");
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        mChart.setNoDataTextDescription("You need to provide data for the chart.");
        // 设置是否可以触摸
        // enable touch gestures
        mChart.setTouchEnabled(true);
        //持续滚动时的速度快慢，[0,1) 0代表立即停止。
        mChart.setDragDecelerationFrictionCoef(0.9f);
        // 是否可以拖拽
        // enable scaling and dragging
        mChart.setDragEnabled(true);
        // 是否可以缩放
        mChart.setScaleEnabled(false);
        // 是否显示表格颜色
        mChart.setDrawGridBackground(false);
        // 能否拖拽高亮线(数据点与坐标的提示线)
        mChart.setHighlightPerDragEnabled(true);
        // 是否按X/Y按比例缩放，如果为false,X/Y可以分别缩放
        mChart.setPinchZoom(false);
        //设置背景色
        mChart.setBackgroundColor(Color.WHITE);
        // 隐藏右边 的坐标轴
        mChart.getAxisRight().setEnabled(false);
//        setData(xList, priceList);
        // 执行的动画的时间 ,x轴
        mChart.animateX(500);
        // 设置比例图标示，就是那个一组y的value的
        Legend l = mChart.getLegend();
        //设置是否显示Legend
        l.setEnabled(isVisible);
        // 样式
        l.setForm(Legend.LegendForm.SQUARE);
        //字体大小
        l.setTextSize(11f);
        //字体颜色
        l.setTextColor(context.getApplicationContext().getResources().getColor(R.color.all_red_4c));
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);//设置折线图文字所显示的位置

        XAxis xAxis = mChart.getXAxis();
//        xAxis.setTypeface(tf);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(context.getApplicationContext().getResources().getColor(R.color.dimgray));
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setSpaceBetweenLabels(1);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);//把坐标轴放在上下
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setStartAtZero(false);
        leftAxis.setTextColor(context.getApplicationContext().getResources().getColor(R.color.dimgray));

        //设置y轴的小数位
        leftAxis.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat df = new DecimalFormat("0.00");  //生成一个df对象，确保放大的value也是小数点后一位
                String formatValue = df.format(value);
                String sub1 = StringFormat.subFrontString(formatValue, 1);
                if ("-".equals(sub1)) {
                    return "";
                }
                return formatValue;  //确保返回的数值时0.0
            }
        });

        leftAxis.setAxisMaxValue(max + (max - min) / 4);
        leftAxis.setAxisMinValue(min - (max - min) / 4);
        leftAxis.setLabelCount(7, true);

        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);  //参数是INSIDE_CHART(Y轴坐标在内部) 或 OUTSIDE_CHART(在外部（默认是这个）)
    }

    /**
     * 显示两条曲线
     *
     * @param context
     * @param name
     * @param xVals
     * @param yVals
     * @return
     */
    public static LineData getTwoLineData(Context context, String name, ArrayList<String> xVals, ArrayList<ArrayList<Entry>> yVals) {
        LineDataSet set1;//set1 表示LineDataSet折线图在Y轴上的名字和值

        set1 = new LineDataSet(yVals.get(1), name);//每一个DataSet对象代表一组属于一个表的数据记录，它用来分离不同组的逻辑值，每种类型的图表对应不同DataSet的子类，对应不同样式。
        //设置选中坐标线不显示-----------start-------------
//        是否启用垂直方向上的高亮《是》
        set1.setDrawVerticalHighlightIndicator(true);
        //是否起用水平方向 上的高亮《否》
        set1.setDrawHorizontalHighlightIndicator(false);
        //设置选中坐标线不显示-----------end-------------
        //给set1 添加数据
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        //设置颜色
        set1.setColor(context.getApplicationContext().getResources().getColor(R.color.lineColor));
        //设置折线的线宽
        set1.setLineWidth(1f);
//        set1.setFillAlpha(255);
        //是否填充 true 表示填充
        set1.setDrawFilled(true);

//        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//        set1.setFillDrawable(drawable);
        //折线图下方填充颜色设置
        set1.setFillColor(context.getApplicationContext().getResources().getColor(R.color.lineBg));
        //图表上的数据点是否用小圆圈表示
        set1.setDrawCircles(true);
        // 设置节点是否为同心圆
        set1.setDrawCircleHole(true);
        // 设置圆形节点大小
        set1.setCircleSize(4f);
        // 圆形的颜色
        set1.setCircleColor(context.getApplicationContext().getResources().getColor(R.color.linePointBorder));
        // 设置节点同心圆内部的颜色
        set1.setCircleColorHole(context.getApplicationContext().getResources().getColor(R.color.linePointIn));
        //隐藏折线图每个数据点的值
        set1.setDrawValues(!set1.isDrawValuesEnabled());

        LineDataSet set2;//第二条 成本线
        set2 = settingLine2(context, new LineDataSet(yVals.get(0), "成本线"));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        LineData data = new LineData(xVals, dataSets);

        return data;
    }

    public static LineDataSet settingLine2(Context context, LineDataSet set2) {
        set2.setDrawVerticalHighlightIndicator(true);
        //是否起用水平方向 上的高亮《否》
        set2.setDrawHorizontalHighlightIndicator(false);
        //设置选中坐标线不显示-----------end-------------
        //给set1 添加数据
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        //设置颜色
        set2.setColor(context.getApplicationContext().getResources().getColor(R.color.costlineColor));
        //设置折线的线宽
        set2.setLineWidth(1f);
//        set1.setFillAlpha(255);
        //是否填充 true 表示填充
        set2.setDrawFilled(false);

//        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//        set1.setFillDrawable(drawable);
        //折线图下方填充颜色设置
//        set2.setFillColor(context.getApplicationContext().getResources().getColor(R.color.lineBg));
        //图表上的数据点是否用小圆圈表示  set2为false
        set2.setDrawCircles(false);
        // 设置节点是否为同心圆
        set2.setDrawCircleHole(true);
        // 设置圆形节点大小
        set2.setCircleSize(4f);
        // 圆形的颜色
        set2.setCircleColor(context.getApplicationContext().getResources().getColor(R.color.linePointBorder));
        // 设置节点同心圆内部的颜色
        set2.setCircleColorHole(context.getApplicationContext().getResources().getColor(R.color.costlineColor));
        //隐藏折线图每个数据点的值
        set2.setDrawValues(!set2.isDrawValuesEnabled());

        return set2;
    }

    /**
     * 简化X刻度让其只能显示指定的个数
     */
    public static void setXAxisByNum(ArrayList<String> mCharXVals, LineChart chart_view, int maxNum) {
        if (null != mCharXVals) {
            int xSize = mCharXVals.size();
            //要显示的个数大于已经有的
            if (maxNum >= xSize) {
                //不进行设置
                //chart_view.getXAxis().setLabelsToSkip(1);
            } else {
                int max = maxNum - 2;
                if (max > 0) {
                    int avgNum = (xSize - 1) / max;
                    chart_view.getXAxis().setLabelsToSkip(avgNum - 1); //设置坐标相隔多少，参数是int类型
                }
//                int avgNum = xSize/maxNum;
//                chart_view.getXAxis().setLabelsToSkip(avgNum);

            }
        }
    }

    /**
     * 设置高限制线
     * 油价的成本线
     *
     * @param high
     * @param name
     */
    public static void setHightLimitLine(LineChart lineChart, float high, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        LimitLine hightLimit = new LimitLine(high, name);
        hightLimit.setLineWidth(1f);
        hightLimit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        hightLimit.setTextSize(10f);
        hightLimit.setLineColor(color);
        hightLimit.setTextColor(color);
        lineChart.getAxisLeft().addLimitLine(hightLimit);
//        lineChart.invalidate();
    }

}
