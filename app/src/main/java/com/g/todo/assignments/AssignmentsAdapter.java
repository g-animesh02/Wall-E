package com.g.todo.assignments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.g.todo.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.MyViewHolder> {
    private final List<AssignmentsDatabase> assignments;
    Context context;
    String subject;


    public AssignmentsAdapter(List<AssignmentsDatabase> assignment, Context context, String subject) {
        this.assignments = assignment;
        this.context = context;
        this.subject = subject;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView2 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assignments_rows, parent, false);

        return new AssignmentsAdapter.MyViewHolder(itemView2);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AssignmentsDatabase assignment = assignments.get(position);

        holder.assignment_name.setText(assignment.getAssignment());
        holder.assignment_desc.setText(assignment.getAssignment_desc());
        try {
            holder.deadline.setText("Deadline: " + assignment.getDeadline());
        }
        catch (Exception e){
            Toast.makeText(context, "Unable to retrive deadline", Toast.LENGTH_SHORT).show();
            holder.deadline.setText("Deadline: ??");
        }
        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        if (dateRemaining(date, assignment.getDeadline())) {
            holder.alert.setVisibility(View.VISIBLE);
        }


        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Confirmation")
                        .setMessage("Miracle!! You completed your Assignment on time...")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yess", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                int pos = assignments.indexOf(assignment);


                                class GetTasks extends AsyncTask<Void, Void, List<AssignmentsDatabase>> {
                                    List<AssignmentsDatabase> t;

                                    @Override
                                    protected List<AssignmentsDatabase> doInBackground(Void... voids) {
                                        AssignmentsModelDatabase
                                                .getInstance(context)
                                                .assignmentsDatabaseDao()
                                                .deleteTopic(assignment.getAssignment(), assignment.getAssignment_desc());

                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(List<AssignmentsDatabase> topicsDatabases) {
                                        super.onPostExecute(topicsDatabases);
                                        assignments.remove(pos);
                                        notifyItemRemoved(pos);
                                    }
                                }
                                GetTasks gt = new GetTasks();
                                gt.execute();


                            }
                        })
                        .setNegativeButton("Nahh, still pending", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                return true;
            }
        });
    }


    private boolean dateRemaining(String date, String deadline) {
        String[] today = date.split("/");
        String[] deadlne = deadline.split("/");
        return (Integer.parseInt(deadlne[0]) - Integer.parseInt(today[0]) <= 2) && (Integer.parseInt(deadlne[1]) - Integer.parseInt(today[1]) == 0) && (Integer.parseInt(deadlne[2]) - Integer.parseInt(today[2]) == 0);

    }


    @Override
    public int getItemCount() {
        return assignments.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView assignment_name, assignment_desc, deadline;
        public ImageView alert;
        public RelativeLayout relativeLayout;

        public MyViewHolder(View view) {
            super(view);
            assignment_name = (TextView) view.findViewById(R.id.data_title_assignment);
            alert = (ImageView) view.findViewById(R.id.data_done_assignments);
            assignment_desc = (TextView) view.findViewById(R.id.data_desc_assignment);
            deadline = (TextView) view.findViewById(R.id.deadline_assignments);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.relative_assignments);

        }
    }
}
