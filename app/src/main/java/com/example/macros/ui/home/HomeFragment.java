package com.example.macros.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import android.os.PersistableBundle;

import com.example.macros.R;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView fatsTextView;
    TextView carbsTextView;
    TextView proteinTextView;
    Button editFats;
    Button editCarbs;
    Button editProtein;
    TextView fatsGoalTextView;
    TextView carbsGoalTextView;
    TextView proteinGoalTextView;
    Button editFatsGoal;
    Button editCarbsGoal;
    Button editProteinGoal;
    public static HashMap<String, Integer> macros = new HashMap<>();
    public static final String FAT = "fatID";
    public static final String CARB = "carbID";
    public static final String PROTEIN = "proteinID";
    public static final String FAT_GOAL = "fatGoalID";
    public static final String CARB_GOAL = "carbGoalID";
    public static final String PROTEIN_GOAL = "proteinGoalID";
    public static final String SAVE = "SAVE";
    //Date number to determine if we should create a new map
    public static final String EXPERIATION = "date";
    View root;
    Bundle saved;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    private int fat;
    private int carb;
    private int protein;
    private int fatGoal;
    private int carbGoal;
    private int proteinGoal;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPref = getActivity().getPreferences(this.getContext().MODE_PRIVATE);
        editor = sharedPref.edit();
        //Today View Items
        fatsTextView = root.findViewById(R.id.fatView);
        carbsTextView = root.findViewById(R.id.carbsView);
        proteinTextView = root.findViewById(R.id.proteinView);
        editFats = root.findViewById(R.id.editFat);
        editCarbs = root.findViewById(R.id.editCarbs);
        editProtein = root.findViewById(R.id.editProtein);

        //Goal items
        fatsGoalTextView = root.findViewById(R.id.goalFatView);
        carbsGoalTextView = root.findViewById(R.id.goalCarbView);
        proteinGoalTextView = root.findViewById(R.id.goalProteinView);
        editFatsGoal = root.findViewById(R.id.editFatGoal);
        editCarbsGoal = root.findViewById(R.id.editCarbGoal);
        editProteinGoal = root.findViewById(R.id.editProteinGoal);

        //TODO: Check if we already have a macro map saved on disk else create new one

        if(macros.isEmpty()) {
            getMacros();
        }

        fatsTextView.setText(String.valueOf(macros.get(FAT) + " grams"));
        carbsTextView.setText(String.valueOf(macros.get(CARB) + " grams"));
        proteinTextView.setText(String.valueOf(macros.get(PROTEIN) + " grams"));

        fatsGoalTextView.setText(String.valueOf(macros.get(FAT_GOAL) + " grams"));
        carbsGoalTextView.setText(String.valueOf(macros.get(CARB_GOAL) + " grams"));
        proteinGoalTextView.setText(String.valueOf(macros.get(PROTEIN_GOAL) + " grams"));

        editFats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFat();
            }
        });

        editCarbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCarbs();
            }
        });

        editProtein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProtein();
            }
        });

        editFatsGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFatGoal();
            }
        });

        editCarbsGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCarbsGoal();
            }
        });

        editProteinGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProteinGoal();
            }
        });

        return root;
    }

    public void initMap() {
        HomeFragment.macros.put(FAT, 0);
        HomeFragment.macros.put(CARB, 0);
        HomeFragment.macros.put(PROTEIN, 0);

        HomeFragment.macros.put(FAT_GOAL, 0);
        HomeFragment.macros.put(CARB_GOAL, 0);
        HomeFragment.macros.put(PROTEIN_GOAL, 0);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    private void saveMacros() {
        editor.putInt(FAT, macros.get(FAT));
        editor.putInt(CARB, macros.get(CARB));
        editor.putInt(PROTEIN, macros.get(PROTEIN));

        editor.putInt(FAT_GOAL, macros.get(FAT_GOAL));
        editor.putInt(CARB_GOAL, macros.get(CARB_GOAL));
        editor.putInt(PROTEIN_GOAL, macros.get(PROTEIN_GOAL));
        editor.apply();
    }

    private void getMacros() {
        fat = sharedPref.getInt(FAT, 0);
        carb = sharedPref.getInt(CARB, 0);
        protein = sharedPref.getInt(PROTEIN, 0);

        fatGoal = sharedPref.getInt(FAT_GOAL, 0);
        carbGoal = sharedPref.getInt(CARB_GOAL, 0);
        proteinGoal = sharedPref.getInt(PROTEIN_GOAL, 0);

        HomeFragment.macros.put(FAT, fat);
        HomeFragment.macros.put(CARB, carb);
        HomeFragment.macros.put(PROTEIN, protein);

        HomeFragment.macros.put(FAT_GOAL, fatGoal);
        HomeFragment.macros.put(CARB_GOAL, carbGoal);
        HomeFragment.macros.put(PROTEIN_GOAL, proteinGoal);
    }
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void editFat() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.root.getContext());
        dialog.setTitle("Update Fat Intake");

        final EditText input = new EditText(this.root.getContext());
        dialog.setView(input);

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                String fat = input.getText().toString();
                fatsTextView.setText(fat + " grams");
                macros.put(FAT,Integer.valueOf(fat));
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });

        dialog.show();
    }

    public void editCarbs() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.root.getContext());
        dialog.setTitle("Update Carb Intake");

        final EditText input = new EditText(this.root.getContext());
        dialog.setView(input);

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                String carb = input.getText().toString();
                carbsTextView.setText(carb + " grams");
                macros.put(CARB, Integer.valueOf(carb));
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });

        dialog.show();
    }

    public void editProtein() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.root.getContext());
        dialog.setTitle("Update Protein Intake");

        final EditText input = new EditText(this.root.getContext());
        dialog.setView(input);

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                String protein = input.getText().toString();
                proteinTextView.setText(protein + " grams");
                macros.put(PROTEIN, Integer.valueOf(protein));
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });
        dialog.show();
    }

    public void editFatGoal() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.root.getContext());
        dialog.setTitle("Update Fat Goal");

        final EditText input = new EditText(this.root.getContext());
        dialog.setView(input);

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                String fat = input.getText().toString();
                fatsGoalTextView.setText(fat + " grams");
                macros.put(FAT_GOAL,Integer.valueOf(fat));
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });
        dialog.show();
    }

    public void editCarbsGoal() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.root.getContext());
        dialog.setTitle("Update Carb Goal");

        final EditText input = new EditText(this.root.getContext());
        dialog.setView(input);

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                String carb = input.getText().toString();
                carbsGoalTextView.setText(carb + " grams");
                macros.put(CARB_GOAL, Integer.valueOf(carb));
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });

        dialog.show();
    }

    public void editProteinGoal() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.root.getContext());
        dialog.setTitle("Update Protein Goal");

        final EditText input = new EditText(this.root.getContext());
        dialog.setView(input);

        dialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                String protein = input.getText().toString();
                proteinGoalTextView.setText(protein + " grams");
                macros.put(PROTEIN_GOAL, Integer.valueOf(protein));
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });
        dialog.show();
    }

}
