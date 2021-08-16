package com.g.todo.DailyRoutine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.g.todo.AddTask;
import com.g.todo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DailyWork extends AppCompatActivity {

    private final List<DailyWorkDatabase> tasks = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_work);
        Objects.requireNonNull(getSupportActionBar()).hide();

       recyclerView = findViewById(R.id.task_recycle);
        FloatingActionButton addtask = findViewById(R.id.task_add);

        addtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i = new Intent(DailyWork.this, AddTask.class);
                startActivity(i);
            }
        });



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DailyWork.this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());


        prepareTopics();
    }

    private  void prepareTopics(){
        class getTask extends AsyncTask<Void, Void, List<DailyWorkDatabase>>{
            List<DailyWorkDatabase> t;

            @Override
            protected List<DailyWorkDatabase> doInBackground(Void... voids) {
                List<DailyWorkDatabase> tasks = DailyWorkModelDatabase.getInstance(DailyWork.this).dailyWorkDatabaseDao().getAll();
                t = tasks;

                return tasks;
            }

            @Override
            protected void onPostExecute(List<DailyWorkDatabase> dailyWorkDatabases) {
                DailyRoutineAdapter mAdapter = new DailyRoutineAdapter(t, DailyWork.this);
                 recyclerView.setAdapter(mAdapter);
            }
        }
        getTask gt = new getTask();
        gt.execute();

    }
}