package com.example.macros.ui.Reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.macros.R;


public class ReminderFragment extends Fragment {

    Button setter;
    Button cancel;
    EditText editText;
    TimePicker timePicker;

    private int notificationId = 1;
    Intent intent ;

    public ReminderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_reminder, container, false);

       setter = root.findViewById(R.id.setBtn);
       cancel = root.findViewById(R.id.cancelBtn);
       timePicker = root.findViewById(R.id.timePicker);

     intent  = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("notificationId", notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getActivity(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT
        );


        AlarmManager alarmManager =( AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);


        setter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                // Create time.
                Calendar startTime = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    startTime = Calendar.getInstance();
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    startTime.set(Calendar.HOUR_OF_DAY, hour);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    startTime.set(Calendar.MINUTE, minute);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    startTime.set(Calendar.SECOND, 0);
                }
                long alarmStartTime = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    alarmStartTime = startTime.getTimeInMillis();
                }

                // Set Alarm
                alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
                Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
                Toast.makeText(getActivity(), "Canceled.", Toast.LENGTH_SHORT).show();
            }
        });

        return root;

    }


}