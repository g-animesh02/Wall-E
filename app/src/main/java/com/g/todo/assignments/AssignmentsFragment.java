package com.g.todo.assignments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.g.todo.R;

import java.util.ArrayList;
import java.util.List;

public class AssignmentsFragment extends Fragment {

    public RecyclerView recyclerView;
    private final List<AssignmentsData> assignments = new ArrayList<>();
    private AssignmentsAdapter mAdapter;
    String subject;


    public AssignmentsFragment(String subject) {
        // Required empty public constructor
        this.subject = subject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_assignments, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.assignments_recyclerView);



        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        prepareAssignments(view);



        return view;
    }

    private void prepareAssignments(View view) {

        class GetTasks extends AsyncTask<Void, Void, List<AssignmentsDatabase>> {

            List<AssignmentsDatabase>t;
            @Override
            protected List<AssignmentsDatabase> doInBackground(Void... voids) {
                t = AssignmentsModelDatabase
                        .getInstance(view.getContext())
                        .assignmentsDatabaseDao()
                        .findWithSubject(subject);

                return null;
            }

            @Override
            protected void onPostExecute(List<AssignmentsDatabase> assignmentsDatabases) {
                super.onPostExecute(assignmentsDatabases);
                   AssignmentsAdapter mAdapter = new AssignmentsAdapter(t, view.getContext(), subject);
                    recyclerView.setAdapter(mAdapter);
                }


            }



        GetTasks gt = new GetTasks();
        gt.execute();

    }
}
