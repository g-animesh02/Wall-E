package com.g.todo.DailyRoutine;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.g.todo.R;

import java.util.List;

public class DailyRoutineAdapter extends RecyclerView.Adapter<DailyRoutineAdapter.MyViewHolder> {

    private final List<DailyWorkDatabase> tasks;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, title_desc, time;
        RelativeLayout relativeLayout;
        ImageView task_completion;

        public MyViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.task_title);
            title_desc = (TextView) itemView.findViewById(R.id.task_desc);
            time = itemView.findViewById(R.id.task_time);
            relativeLayout = itemView.findViewById(R.id.task_relative);
            task_completion = itemView.findViewById(R.id.task_done);

        }
    }

    Context context;

    DailyRoutineAdapter(List<DailyWorkDatabase> tasks, Context context) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public DailyRoutineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_routine_rows, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyRoutineAdapter.MyViewHolder holder, int position) {
        DailyWorkDatabase task = tasks.get(position);
        int bg;
        String TIME;
        if (task.getFinished() == 1) {
            bg = R.mipmap.wave_completed;
            TIME = "Completed Before: " + task.getTask_time();
        } else {
            bg = R.mipmap.wave_uncompleted;
            TIME = "Complete Before: " + task.getTask_time();
        }

        holder.task_completion.setBackgroundResource(bg);
        holder.title.setText(task.getTask());

        holder.time.setText(TIME);

        holder.title_desc.setText(task.getTask_desc());



        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    if (task.getFinished() == 0) {
        new AlertDialog.Builder(context)
                .setTitle("Confirmation")
                .setMessage("Is your task completed?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {


                        class GetTasks extends AsyncTask<Void, Void, List<DailyWorkDatabase>> {
                            List<DailyWorkDatabase> t;

                            @Override
                            protected List<DailyWorkDatabase> doInBackground(Void... voids) {
                                DailyWorkModelDatabase.getInstance(context).dailyWorkDatabaseDao().updateTask(task.getTask(), task.getTask_desc(), 1);

                                return null;
                            }

                            @Override
                            protected void onPostExecute(List<DailyWorkDatabase> dailyWorkDatabases) {
                                super.onPostExecute(dailyWorkDatabases);
                                holder.task_completion.setBackgroundResource(R.mipmap.wave_completed);
                                holder.time.setText("Completed before: " + task.getTask_time());
                            }
                        }
                        GetTasks gt = new GetTasks();
                        gt.execute();
                        task.setFinished(1);

                    }
                })
                .setNegativeButton("just kidding", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }
    else{
        new AlertDialog.Builder(context)
                .setTitle("Confirmation")
                .setMessage("Change back to incomplete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {

                        class GetTasks extends AsyncTask<Void, Void, List<DailyWorkDatabase>> {
                            List<DailyWorkDatabase> t;

                            @Override
                            protected List<DailyWorkDatabase> doInBackground(Void... voids) {
                                DailyWorkModelDatabase.getInstance(context).dailyWorkDatabaseDao().updateTask(task.getTask(), task.getTask_desc(), 0);

                                return null;
                            }

                            @Override
                            protected void onPostExecute(List<DailyWorkDatabase> dailyWorkDatabases) {
                                super.onPostExecute(dailyWorkDatabases);
                                holder.task_completion.setBackgroundResource(R.mipmap.wave_uncompleted);
                                holder.time.setText("Complete before: " + task.getTask_time());
                            }
                        }
                        GetTasks gt = new GetTasks();
                        gt.execute();
                        task.setFinished(0);

                    }
                })
                .setNegativeButton("No way!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
    }
            }
        });

        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                new AlertDialog.Builder(context).setTitle("Confirmation").setMessage("Are you sure to delete this task?")
                 .setIcon(android.R.drawable.ic_dialog_alert)
                 .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         class GetTasks extends AsyncTask<Void, Void, List<DailyWorkDatabase>> {
                             List<DailyWorkDatabase> t;

                             @Override
                             protected List<DailyWorkDatabase> doInBackground(Void... voids) {
                                 DailyWorkModelDatabase.getInstance(context).dailyWorkDatabaseDao().delete(task);
                                 return null;
                             }

                             @Override
                             protected void onPostExecute(List<DailyWorkDatabase> dailyWorkDatabases) {
                                 super.onPostExecute(dailyWorkDatabases);
                                 tasks.remove(position);
                                 notifyItemRemoved(position);

                             }
                         }
                         GetTasks gt = new GetTasks();
                         gt.execute();


                     }
                 })
                 .setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                     }
                 }).show() ;
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
