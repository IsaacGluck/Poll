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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_results);

        TextView tv1 = findViewById(R.id.tv1);
        tv1.setText(getIntent().getStringExtra(PollActivity.ANSWER1));
        TextView tv2 = findViewById(R.id.tv2);
        tv2.setText(getIntent().getStringExtra(PollActivity.ANSWER2));
        TextView tv3 = findViewById(R.id.tv3);
        tv3.setText(getIntent().getStringExtra(PollActivity.ANSWER3));
        TextView tv4 = findViewById(R.id.tv4);
        tv4.setText(getIntent().getStringExtra(PollActivity.ANSWER4));
        TextView tvQuestion = findViewById(R.id.tv_question);
        tvQuestion.setText(getIntent().getStringExtra(PollActivity.QUESTION));

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, getIntent().getIntExtra(PollActivity.ANSWER1_COUNT, -1)));
        entries.add(new BarEntry(1, getIntent().getIntExtra(PollActivity.ANSWER2_COUNT, -1)));
        entries.add(new BarEntry(2, getIntent().getIntExtra(PollActivity.ANSWER3_COUNT, -1)));
        entries.add(new BarEntry(3, getIntent().getIntExtra(PollActivity.ANSWER4_COUNT, -1)));

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarChart chart = findViewById(R.id.chart);

        BarData data = new BarData(dataSet);
        chart.setData(data);

        YAxis left = chart.getAxisLeft();
        left.setStartAtZero(true);
        YAxis right = chart.getAxisRight();
        right.setStartAtZero(true);

        chart.invalidate();

    }
}
