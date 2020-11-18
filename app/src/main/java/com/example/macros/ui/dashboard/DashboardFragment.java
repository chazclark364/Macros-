package com.example.macros.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.macros.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.example.macros.ui.home.HomeFragment.CARB;
import static com.example.macros.ui.home.HomeFragment.CARB_GOAL;
import static com.example.macros.ui.home.HomeFragment.FAT;
import static com.example.macros.ui.home.HomeFragment.FAT_GOAL;
import static com.example.macros.ui.home.HomeFragment.PROTEIN;
import static com.example.macros.ui.home.HomeFragment.PROTEIN_GOAL;
import static com.example.macros.ui.home.HomeFragment.macros;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    PieChart pieChart;
    PieChart pieChartGoal;
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
        pieChart.getDescription().setEnabled(false);
        pieEntryList.add(new PieEntry((macros.get(FAT)), "Fat"));
        pieEntryList.add(new PieEntry((macros.get(CARB)),"Carb"));
        pieEntryList.add(new PieEntry((macros.get(PROTEIN)),"Protein"));
        PieDataSet pieDataSet = new PieDataSet(pieEntryList,"");
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawHoleEnabled(false);
        pieChart.invalidate();

        pieChartGoal= root.findViewById(R.id.pieChart2);
        pieChartGoal.setUsePercentValues(true);
        pieChartGoal.getDescription().setEnabled(false);
        pieEntryList2.add(new PieEntry(macros.get(FAT_GOAL),"FatGoal"));
        pieEntryList2.add(new PieEntry(macros.get(CARB_GOAL),"CarbGoal"));
        pieEntryList2.add(new PieEntry(macros.get(PROTEIN_GOAL),"ProteinGoal"));
        PieDataSet pieDataSet2 = new PieDataSet(pieEntryList2,"");
        pieDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        pieData2 = new PieData(pieDataSet2);
        pieChartGoal.setData(pieData2);
        pieChartGoal.setDrawHoleEnabled(false);
        pieChartGoal.invalidate();

        return root;
    }

}