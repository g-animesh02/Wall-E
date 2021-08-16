package com.g.todo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.g.todo.DailyRoutine.DailyWork;
import com.g.todo.DailyRoutine.DailyWorkDatabase;
import com.g.todo.DailyRoutine.DailyWorkModelDatabase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

public class AddTask extends AppCompatActivity {
    TimePicker picker;
TextInputLayout title_parent, desc_parent;
TextInputEditText title, desc;
RadioButton radioButton;
Button addtask;
RadioGroup radioGroup;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Objects.requireNonNull(getSupportActionBar()).hide();

        picker = findViewById(R.id.task_time_picker);
        title_parent = findViewById(R.id.TaskTitle_parent);
        title = findViewById(R.id.taskTitle);
        desc = findViewById(R.id.TaskDesc);
        desc_parent = findViewById(R.id.TaskDesc_parent);
        addtask = findViewById(R.id.add_task);
        radioGroup = findViewById(R.id.radioGroup);

        picker.setIs24HourView(true);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        picker.setHour(hour);
        picker.setMinute(min);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable task = title.getText();
                Editable task_desc = desc.getText();
                int c = 0;
                assert task != null;
                if (task.toString().isEmpty()){
                    title_parent.setError("Enter a valid title");
                    c += 1;
                }
                assert task_desc != null;
                if(task_desc.toString().isEmpty() || task_desc.toString().length() > 40){
                    desc_parent.setError("Enter a valid description");
                    c += 1;
                }
                int hour = picker.getCurrentHour();
                int min = picker.getCurrentMinute();
                String h,m;
                if (hour == 0){
                    h = "00";
                }
                else{
                    h = "" + hour;
                }
                if (min == 0){
                    m = "00";
                }
                else{
                    m = "" + min;
                }
                if (c == 0){
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(selectedId);
                    final String[] radio = {""+selectedId};

                    class SaveTask extends AsyncTask<Void, Void, Void> {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            if (radio[0].equals(""+R.id.radio_button_daily)){
                                radio[0] = "1";
                            }
                            else{
                                radio[0] = "0";
                            }
                            DailyWorkDatabase ads = new DailyWorkDatabase();
                            ads.setTask(task.toString());
                            ads.setTask_desc(task_desc.toString());
                            ads.setTask_time(""+h+":"+m);
                            ads.setFinished(0);
                            ads.setRepeat(radio[0]);
                            ads.setDate(""+day+"/"+month+"/"+year);

                            DailyWorkModelDatabase.getInstance(AddTask.this).dailyWorkDatabaseDao().insert(ads);

                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            finish();
                            Intent i = new Intent(AddTask.this, DailyWork.class);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(), "Task Added", Toast.LENGTH_SHORT).show();
                        }
                    }

                    SaveTask st = new SaveTask();
                    st.execute();


                }
            }
        });


    }
}