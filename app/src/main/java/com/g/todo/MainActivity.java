package com.g.todo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.g.todo.DailyRoutine.DailyWork;
import com.g.todo.DailyRoutine.DailyWorkDatabase;
import com.g.todo.DailyRoutine.DailyWorkModelDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;

    private List<Subject_Names> subjectNames = new ArrayList<>();
    private RecyclerView recyclerView;
    private Main_subject_adapter mAdapter;
    ImageView daily, refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        String date = "" + day + "/" + month + "/" + year;


        class GetTasks extends AsyncTask<Void, Void, List<DailyWorkDatabase>> {
            List<DailyWorkDatabase> t;

            @Override
            protected List<DailyWorkDatabase> doInBackground(Void... voids) {
                t = DailyWorkModelDatabase.getInstance(MainActivity.this).dailyWorkDatabaseDao().getDate(date);
                if (t.size() == 0) {
                    DailyWorkModelDatabase.getInstance(MainActivity.this).dailyWorkDatabaseDao().repeatedTask();
                    DailyWorkModelDatabase.getInstance(MainActivity.this).dailyWorkDatabaseDao().resetTask(0);
                    DailyWorkModelDatabase.getInstance(MainActivity.this).dailyWorkDatabaseDao().resetDate(date);
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<DailyWorkDatabase> dailyWorkDatabases) {
                super.onPostExecute(dailyWorkDatabases);

            }
        }
        GetTasks gt = new GetTasks();
        gt.execute();


        rv = findViewById(R.id.subjectRecycleView);
        daily = findViewById(R.id.dailyTodo_main);


        daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DailyWork.class);
                startActivity(i);
            }
        });

        mAdapter = new Main_subject_adapter(subjectNames);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(mAdapter);

        prepareSubjects();
    }


    private void prepareSubjects() {

        Subject_Names sub0 = new Subject_Names("Competitive Programming", R.mipmap.cp, "A mind sport usually held over the Internet.", R.drawable.subject_rows);
        subjectNames.add(sub0);

        Subject_Names sub1 = new Subject_Names("Data Science", R.mipmap.datascience, "Road map to Data Science, plz ye krlena poora", R.drawable.subject_rows);
        subjectNames.add(sub1);

        Subject_Names sub2 = new Subject_Names("AI (CS3101)", R.mipmap.ai, "A perfect art of developing intelligent machines", R.drawable.subject_rows);
        subjectNames.add(sub2);

        Subject_Names sub3 = new Subject_Names("Design & Analysis of Algos (CS3102)", R.mipmap.designalgo, "Solve different types of algo problems", R.drawable.subject_rows);
        subjectNames.add(sub3);

        Subject_Names sub4 = new Subject_Names("Compiler Design (CS3103)", R.mipmap.compilerdesign, "Please translate the written code in Machine Language", R.drawable.subject_rows);
        subjectNames.add(sub4);

        Subject_Names sub5 = new Subject_Names("Computer Networks (CS3104)", R.mipmap.cn, "C'mon! Lets talk... ;)", R.drawable.subject_rows);
        subjectNames.add(sub5);

        Subject_Names sub6 = new Subject_Names("Soft Computing (P.E.)", R.mipmap.softcomput, "Lets exploit tolerance for uncertainty and partial truth", R.drawable.subject_rows);
        subjectNames.add(sub6);

    }
}