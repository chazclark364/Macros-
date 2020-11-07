package com.example.macros.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.macros.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    PieChart pieChart;
    PieChart pieChart2;
    PieData pieData;
    PieData pieData2;

    List<PieEntry> pieEntryList = new ArrayList<>();
    List<PieEntry> pieEntryList2 = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        pieChart = root.findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieEntryList.add(new PieEntry(10,"Protein"));
        pieEntryList.add(new PieEntry(5,"carbohydrates"));
        pieEntryList.add(new PieEntry(7,"FAT"));
        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"T");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawHoleEnabled(false);
        pieChart.invalidate();

        pieChart2= root.findViewById(R.id.pieChart2);
        pieChart2.setUsePercentValues(true);
        pieEntryList2.add(new PieEntry(10,"Protein"));
        pieEntryList2.add(new PieEntry(5,"Carbohydrates"));
        PieDataSet pieDataSet2 = new PieDataSet(pieEntryList2,"P");
        pieDataSet2.setColors(ColorTemplate.JOYFUL_COLORS);
        pieData2 = new PieData(pieDataSet2);
        pieChart2.setData(pieData2);
        pieChart2.setDrawHoleEnabled(false);
        pieChart2.invalidate();
        return root;
    }

}