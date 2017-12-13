package hu.ait.android.poll.graph;

import android.util.Log;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.List;

/**
 * Created by andersbando-hess on 12/13/17.
 */

public class axisFormatter implements IAxisValueFormatter {

    public List<String> labels;

    public axisFormatter(List<String> labels) {
        // maybe do something here or provide parameters in constructor
        this.labels = labels;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Log.d("VALUUEE", String.valueOf(value));
        Log.d("RANGE", String.valueOf(axis.mAxisRange));

//        float percent = value / axis.mAxisRange;
//        if (value > axis.mAxisRange) percent = 1;

        return labels.get((int) value);
    }
}
