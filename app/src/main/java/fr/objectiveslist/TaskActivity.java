package fr.objectiveslist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fr.objectiveslist.helpers.SQLiteHelper;
import fr.objectiveslist.models.Task;
import fr.objectiveslist.models.TaskDAO;


public class TaskActivity extends Activity {

    private static final String TAG = "TaskActivity";
    public static final String TASK_EDITED = "TASK_EDITED";

    private TextView taskTitle = null;
    private TextView taskDescription = null;
    private TextView taskDateLimit = null;
    private TextView taskStatus = null;

    private TaskDAO tasksDAO = null;

    private Task task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        task = intent.getExtras().getParcelable(TaskListActivity.TASK_SELECTED);

        if (task == null) {
            task = intent.getExtras().getParcelable(TASK_EDITED);
        }

        tasksDAO = new TaskDAO(this);
        tasksDAO.open();

        taskTitle = (TextView) findViewById(R.id.task_title);
        taskDescription = (TextView) findViewById(R.id.task_description);
        taskDateLimit = (TextView) findViewById(R.id.task_datetime_limit);
        taskStatus = (TextView) findViewById(R.id.task_status);


        taskTitle.setText(task.getTitle());
        taskDescription.setText(task.getDescription());
        taskDateLimit.setText(SQLiteHelper.dateTimeFormat.format(task.getDateLimit()));
        taskStatus.setText(task.getState());

    }

    public void doneTask(View v) {
        tasksDAO.taskDone(task);
        taskStatus.setText(Task.DONE);
        startActivity(new Intent(this, TaskListActivity.class));
        finish();
    }

    public void editTaskAction(View v) {
        Intent intent = new Intent(this, EditTaskActivity.class);
        intent.putExtra(TASK_EDITED, task);
        startActivity(intent);
        finish();
    }

    public void deleteTaskAction(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Task ?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(TaskActivity.this, "Task Delete", Toast.LENGTH_LONG).show();
                tasksDAO.delete(task);
                startActivity(new Intent(TaskActivity.this, TaskListActivity.class));
                finish();
            }

        });

        builder.setNegativeButton("Cancel", null);
        builder.create().show();
    }

    public void backAction(View v) {
        startActivity(new Intent(this, TaskListActivity.class));
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        tasksDAO.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tasksDAO.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_edit) {
            Toast.makeText(this, "Task Edit = " + task.toString(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, EditTaskActivity.class);
            intent.putExtra(TASK_EDITED, task);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Task ?");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(TaskActivity.this, "Task Delete", Toast.LENGTH_LONG).show();
                    tasksDAO.delete(task);
                    startActivity(new Intent(TaskActivity.this, TaskListActivity.class));
                    finish();
                }

            });

            builder.setNegativeButton("Cancel", null);
            builder.create().show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
