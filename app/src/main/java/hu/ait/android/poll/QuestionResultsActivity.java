package hu.ait.android.poll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class QuestionResultsActivity extends AppCompatActivity {

    private BarChart chart;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tvQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_results);

        tv1 = findViewById(R.id.tv1);
        tv1.setText(getIntent().getStringExtra(PollActivity.ANSWER1));
        tv2 = findViewById(R.id.tv2);
        tv2.setText(getIntent().getStringExtra(PollActivity.ANSWER2));
        tv3 = findViewById(R.id.tv3);
        tv3.setText(getIntent().getStringExtra(PollActivity.ANSWER3));
        tv4 = findViewById(R.id.tv4);
        tv4.setText(getIntent().getStringExtra(PollActivity.ANSWER4));
        tvQuestion = findViewById(R.id.tv_question);
        tvQuestion.setText(getIntent().getStringExtra(PollActivity.QUESTON));

        List<BarEntry> entries = new ArrayList<BarEntry>();
        entries.add(new BarEntry(0, getIntent().getIntExtra(PollActivity.ANSWER1_COUNT, -1)));
        entries.add(new BarEntry(1, getIntent().getIntExtra(PollActivity.ANSWER2_COUNT, -1)));
        entries.add(new BarEntry(2, getIntent().getIntExtra(PollActivity.ANSWER3_COUNT, -1)));
        entries.add(new BarEntry(3, getIntent().getIntExtra(PollActivity.ANSWER4_COUNT, -1)));

        BarDataSet dataset = new BarDataSet(entries, "");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart = findViewById(R.id.chart);

        BarData data = new BarData(dataset);
        chart.setData(data);

        YAxis left = chart.getAxisLeft();
        left.setStartAtZero(true);
        YAxis right = chart.getAxisRight();
        right.setStartAtZero(true);

        chart.invalidate(); // refresh

    }
}
