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
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BarChart chart;
    private HashMap<String, List<BarEntry>> chartData;

    private int iterations = 1000000;


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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

        chartData = new HashMap<String, List<BarEntry>>();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
            chartData.put("expr", entries);
        }
    }

    private void updateChart()
    {

        BarDataSet dataSet = new BarDataSet(entries, expr); // add entries to dataset
        dataSet.setColor(Color.BLUE);
        //dataSet.setValueTextColor(Color.BLUE); // styling, ...
        BarDataSet dataSet2 = new BarDataSet(entries2, expr2); // add entries to dataset
        dataSet.setColor(Color.GREEN);

        BarData barData = new BarData(dataSet, dataSet2);

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset

        barData.setBarWidth(barWidth); // set the width of each bar
        chart.setData(barData);
        chart.groupBars(0f, groupSpace, barSpace); // perform the "explicit" grouping

        XAxis xAxis = chart.getXAxis();
        xAxis.setCenterAxisLabels(true);

        chart.invalidate(); // refresh
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
