package rpg.dicetool;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BarChart chart;
    private HashMap<String, BarDataSet> chartData;

    private int iterations = 1000000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText input = (EditText) findViewById(R.id.editText);

        final Button button = (Button) findViewById(R.id.addbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String expr = input.getText().toString();

                executeSimulation(expr);

                updateChart();
            }
        });

        chartData = new HashMap<String, BarDataSet>();

    }

    private void executeSimulation(String expr)
    {
        if(DiceParser.isValid(expr))
        {
            //execute simulation and store data
            HashMap<String, Object> map = DiceParser.parseString(expr);

            int[] dices = (int[]) map.get("dicetypes");
            int drop = 0;
            if (map.containsKey("drop"))
                drop = (int) map.get("drop");

            int[] results;
            results = DiceSimulator.simulate(dices, drop, iterations);

            List<BarEntry> entries = new ArrayList<BarEntry>();

            for (int i = 0; i < results.length; i++) {
                float perc = (float) results[i] / iterations * 100;
                entries.add(new BarEntry(i, perc));
            }

            //store data in bar entry
            BarDataSet dataSet = new BarDataSet(entries, expr);
            dataSet.setColor(Color.BLUE);
            dataSet.setValueTextColor(Color.BLUE); // styling, ...

            chartData.put(expr, dataSet);
        }
    }

    private void updateChart()
    {
        BarData bardata = new BarData();

        for( String key : chartData.keySet())
        {
            BarDataSet set =  chartData.get(key);

            bardata.addDataSet(set);
        }

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset

        bardata.setBarWidth(barWidth); // set the width of each bar
        chart.setData(bardata);
        chart.groupBars(0f, groupSpace, barSpace); // perform the "explicit" grouping

        XAxis xAxis = chart.getXAxis();
        xAxis.setCenterAxisLabels(true);

        chart.invalidate(); // refresh
    }
}
