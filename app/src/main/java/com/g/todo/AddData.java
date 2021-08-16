package com.g.todo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.g.todo.assignments.AssignmentsDatabase;
import com.g.todo.assignments.AssignmentsModelDatabase;
import com.g.todo.topics.TopicsDatabase;
import com.g.todo.topics.TopicsModelDatabase;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddData extends AppCompatActivity {

    TextInputLayout subjectNames, deadline_assignments_parent, topic_parent, topic_desc_parent;
    AutoCompleteTextView subautofill;
    TextInputEditText deadline_assignments, topic, topic_desc;
    DatePickerDialog picker;
    Button submitData;
    RelativeLayout topics_form, assignments_form;
    boolean TOPIC_FORM = true;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);
        Objects.requireNonNull(getSupportActionBar()).hide();

        subjectNames = (TextInputLayout) findViewById(R.id.SubjectName);
        subautofill = findViewById(R.id.subjectAutoFill);
        deadline_assignments = (TextInputEditText) findViewById(R.id.deadline_data_assignments);
        deadline_assignments_parent = findViewById(R.id.deadline_data_assignments_parent);
        topics_form = (RelativeLayout) findViewById(R.id.topics_form);
        assignments_form = (RelativeLayout) findViewById(R.id.assignments_form);
        topic_parent = (TextInputLayout) findViewById(R.id.DataTitle_parent);
        topic_desc_parent = (TextInputLayout) findViewById(R.id.DataDesc_parent);
        topic = (TextInputEditText) findViewById(R.id.DataTitle);
        topic_desc = (TextInputEditText) findViewById(R.id.DataDesc);
        submitData = (Button) findViewById(R.id.submitData);

        topics_form.setBackgroundResource(R.drawable.selected_data_pic);
        deadline_assignments_parent.setVisibility(View.GONE);
        topic_desc.setText("");
        topic.setText("");


        String[] items = {"Competitive Programming", "Data Science", "AI (CS3101)", "Design & Analysis of Algos (CS3102)", "Compiler Design (CS3103)", "Computer Networks (CS3104)", "Soft Computing (P.E.)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddData.this, R.layout.subject_list_form, items);
        subautofill.setAdapter(adapter);

        topics_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topics_form.setBackgroundResource(R.drawable.selected_data_pic);
                assignments_form.setBackgroundResource(R.drawable.data_pics_forms);
                deadline_assignments_parent.setVisibility(View.GONE);
                TOPIC_FORM = true;
                topic_parent.setHint("Topic's Title");
                topic_desc_parent.setHint("Topic's Description");
                submitData.setText(R.string.add_topic);
            }
        });

        assignments_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignments_form.setBackgroundResource(R.drawable.selected_data_pic);
                topics_form.setBackgroundResource(R.drawable.data_pics_forms);
                deadline_assignments_parent.setVisibility(View.VISIBLE);
                TOPIC_FORM = false;
                topic_parent.setHint("Assignment's Title");
                topic_desc_parent.setHint("Assignment's Description");
                submitData.setText(R.string.add_assignment);
            }
        });


        deadline_assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddData.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String val = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                deadline_assignments.setText(val);
                            }
                        }, year, month, day);
                picker.show();
            }

        });

        submitData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable subjectName = subautofill.getText();
                Editable title = topic.getText();
                Editable title_desc = topic_desc.getText();
                int c = 0;

                if (title.equals("") || title.toString().isEmpty()) {
                    topic_parent.setError("Enter a valid Title");
                } else {
                    topic_parent.setError(null);
                    c += 1;
                }

                if (subjectName.equals("") || (!Arrays.asList(items).contains(subjectName.toString()))) {
                    subjectNames.setError("Enter a valid Subject");
                } else {
                    subjectNames.setError(null);
                    c += 1;
                }

                if (title_desc.equals("") || title_desc.toString().isEmpty() || title_desc.toString().length() > 40) {
                    topic_desc_parent.setError("Enter a valid Description");
                } else {
                    topic_desc_parent.setError(null);
                    c += 1;
                }

                if (TOPIC_FORM) {
                    if(c > 2) {

                        class SaveTask extends AsyncTask<Void, Void, Void> {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                TopicsDatabase td = new TopicsDatabase();
                                td.setSub(subjectName.toString());
                                td.setTopic(title.toString());
                                td.setTopic_desc(title_desc.toString());
                                td.setFinished(0);

                                TopicsModelDatabase.getInstance(AddData.this).topicsDatabaseDao().insert(td);


                                return null;
                            }
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                finish();
                                Intent i = new Intent(AddData.this, SubjectData.class);
                                i.putExtra("SUBJECT_NAME", subjectName.toString());
                                startActivity(i);
                                Toast.makeText(getApplicationContext(), "Topic Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                        SaveTask st = new SaveTask();
                        st.execute();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Unable to add the topic", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Editable deadline = deadline_assignments.getText();

                    String regex = "[0-9]+";

                    Pattern p = Pattern.compile(regex);
                    Matcher m =p.matcher(String.join("",deadline.toString().split("/")));

                    if (!deadline.equals("") && !deadline.toString().isEmpty() && m.matches() ) {
                        if (c > 2) {

                            class SaveTask extends AsyncTask<Void, Void, Void> {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    AssignmentsDatabase ad = new AssignmentsDatabase();
                                    ad.setSub(subjectName.toString());
                                    ad.setAssignment(title.toString());
                                    ad.setAssignment_desc(title_desc.toString());
                                    ad.setDeadline(deadline.toString());

                                    AssignmentsModelDatabase.getInstance(AddData.this).assignmentsDatabaseDao().insert(ad);


                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    super.onPostExecute(aVoid);
                                    finish();
                                    Intent i = new Intent(AddData.this, SubjectData.class);
                                    i.putExtra("SUBJECT_NAME", subjectName.toString());
                                    startActivity(i);
                                    Toast.makeText(getApplicationContext(), "Assignment Added", Toast.LENGTH_SHORT).show();
                                }
                            }

                            SaveTask st = new SaveTask();
                            st.execute();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Unable to add the assignment", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        deadline_assignments_parent.setError("Enter a Valid Deadline");
                    }


                }
            }
        });

    }
}