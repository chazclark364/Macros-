package com.example.macros.ui.home;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import androidx.lifecycle.ViewModelProviders;

import com.example.macros.R;

import java.util.HashMap;

import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment{

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

    Button status;
    Button advise;
    Button reminder;
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

        //Feature Buttons
        status = root.findViewById(R.id.statusButton);
        advise = root.findViewById(R.id.advise);
        reminder = root.findViewById(R.id.reminderButton);

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

        advise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRecommendation();
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statusUpdate();
            }
        });

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                String prev = (String) fatsTextView.getText();
                prev = prev.replace(" grams", "");
                Integer newVal = Integer.valueOf(prev) + Integer.valueOf(fat);
                fatsTextView.setText(newVal + " grams");
                macros.put(FAT,newVal);
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });

        dialog.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                Integer newVal = 0;
                fatsTextView.setText(newVal + " grams");
                macros.put(FAT, newVal);
                saveMacros();
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
                String prev = (String) carbsTextView.getText();
                prev = prev.replace(" grams", "");
                Integer newVal = Integer.valueOf(prev) + Integer.valueOf(carb);
                carbsTextView.setText(newVal + " grams");
                macros.put(CARB, newVal);
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });

        dialog.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                Integer newVal = 0;
                carbsTextView.setText(newVal + " grams");
                macros.put(CARB, newVal);
                saveMacros();
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
                String prev = (String) proteinTextView.getText();
                prev = prev.replace(" grams", "");
                Integer newVal = Integer.valueOf(prev) + Integer.valueOf(protein);
                proteinTextView.setText(newVal + " grams");
                macros.put(PROTEIN, newVal);
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });

        dialog.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                Integer newVal = 0;
                proteinTextView.setText(newVal + " grams");
                macros.put(PROTEIN, newVal);
                saveMacros();
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
                String prev = (String) fatsGoalTextView.getText();
                prev = prev.replace(" grams", "");
                Integer newVal = Integer.valueOf(prev) + Integer.valueOf(fat);
                fatsGoalTextView.setText(newVal + " grams");
                macros.put(FAT_GOAL,newVal);
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });

        dialog.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                Integer newVal = 0;
                fatsGoalTextView.setText(newVal + " grams");
                macros.put(FAT_GOAL, newVal);
                saveMacros();
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
                String prev = (String) carbsGoalTextView.getText();
                prev = prev.replace(" grams", "");
                Integer newVal = Integer.valueOf(prev) + Integer.valueOf(carb);
                carbsGoalTextView.setText(newVal + " grams");
                macros.put(CARB_GOAL, newVal);
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });

        dialog.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                Integer newVal = 0;
                carbsGoalTextView.setText(newVal + " grams");
                macros.put(CARB_GOAL, newVal);
                saveMacros();
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
                String prev = (String) proteinGoalTextView.getText();
                prev = prev.replace(" grams", "");
                Integer newVal = Integer.valueOf(prev) + Integer.valueOf(protein);
                proteinGoalTextView.setText(newVal + " grams");
                macros.put(PROTEIN_GOAL, newVal);
                saveMacros();
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });

        dialog.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                Integer newVal = 0;
                proteinGoalTextView.setText(newVal + " grams");
                macros.put(PROTEIN_GOAL, newVal);
                saveMacros();
                message.cancel();
            }
        });
        dialog.show();
    }

    /**
     * This function will produce recommendation of food based off of your biggest goal deficit
     * source of reccomendations: https://healthyeater.com/carb-protein-fat-rich-foods
     *
     */
    public void  getRecommendation() {
        String goodProteins = "Eggs, Salmon, Tofu, Greek yogurt, Pumpkin Seeds, Turkey breast";
        String goodCarbs = "Apples, Bananas, Quinoa, Sweet potatoes, Brown rice";
        String goodFats = "Almonds, Walnuts, Peanuts, Avocado, Dark Chocolate, Pecans";
        String biggestDeficit = "";

        String message = "";
        Boolean goalsReached = true;

        Integer low = 0;
        Integer temp = 0;

        if(macros.get(FAT) < macros.get(FAT_GOAL)) {
            biggestDeficit = FAT;
            low = macros.get(FAT_GOAL) - macros.get(FAT);
            goalsReached = false;
        }
        if(macros.get(CARB) < macros.get(CARB_GOAL)) {
            temp = (macros.get(CARB_GOAL) - macros.get(CARB));
            if (temp > low) {
                biggestDeficit = CARB;
                low = macros.get(CARB_GOAL) - macros.get(CARB);
                goalsReached = false;
            }
        }
        if(macros.get(PROTEIN) < macros.get(PROTEIN_GOAL)) {
            temp = (macros.get(PROTEIN_GOAL) - macros.get(PROTEIN));
            if (temp > low) {
                biggestDeficit = PROTEIN;
                low = macros.get(PROTEIN_GOAL) - macros.get(PROTEIN);
                goalsReached = false;
            }
        }

        if(goalsReached == false) {
            if(biggestDeficit == FAT) {
                message = "Your biggest goal deficit is Fat by " + String.valueOf(low) + " grams. Try eating the following to get more healthy fat: " + goodFats;
            } else if (biggestDeficit == CARB) {
                message = "Your biggest goal deficit is Carbs by " + String.valueOf(low) + " grams. Try eating the following to get more healthy carbs: " + goodCarbs;
            } else if (biggestDeficit == PROTEIN) {
                message = "Your biggest goal deficit is Protein by " + String.valueOf(low) + " grams. Try eating the following to get more healthy protein: " + goodProteins;
            }
        } else {
            message = "You reached all of your goals! CONGRATS!";
        }
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.root.getContext());
        dialog.setTitle("Advise to Reach Goal!");
        dialog.setMessage(message);
        dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });
        dialog.show();
    }

    public void statusUpdate() {
        ClipboardManager clipboard = (ClipboardManager)
                getSystemService(this.root.getContext(), ClipboardManager.class);
        String message = generateUpdate();

        ClipData clip = ClipData.newPlainText("Status Update", message);
        clipboard.setPrimaryClip(clip);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this.root.getContext());
        dialog.setTitle("Update Copied to Clipboard");
        dialog.setMessage(message);
        dialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface message, int w) {
                message.cancel();
            }
        });
        dialog.show();
    }

    private String generateUpdate() {
        String message = "";
        message = "Hi, I am tracking my nutrients with Macros+, I have had " + macros.get(FAT) + " grams of Fat, " + macros.get(CARB) +  " grams of Carbs," + " and " + macros.get(PROTEIN) + " grams of Protein!";
        return message;
    }

}
