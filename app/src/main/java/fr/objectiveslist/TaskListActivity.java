package fr.objectiveslist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fr.objectiveslist.models.Task;
import fr.objectiveslist.models.TaskAdapter;
import fr.objectiveslist.models.TaskDAO;

public class TaskListActivity extends Activity {

    private static final String TAG = "TaskListActivity";
    public static final String TASK_SELECTED = "TASK_SELECTED";

    private ListView taskListView = null;

    private ArrayList<Task> tasks = new ArrayList<>();

    private TaskDAO tasksDao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        tasksDao = new TaskDAO(this);
        tasksDao.open();

        tasks = (ArrayList<Task>) tasksDao.getAllTasks();

        Log.d(TAG, tasks.toString());

        taskListView = (ListView) findViewById(R.id.task_listView);
        taskListView.setAdapter(new TaskAdapter(this, tasks));

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getItemAtPosition(position);
                Log.d(TAG, task.toString());
                Intent intent = new Intent(TaskListActivity.this, TaskActivity.class);
                intent.putExtra(TASK_SELECTED, task);
                Log.d(TAG, task.toString());
                startActivity(intent);
            }
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        tasksDao.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tasksDao.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return true;
    }

    public void newTaskAction(View v) {
        startActivity(new Intent(this, AddTaskActivity.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_new:
                startActivity(new Intent(this, AddTaskActivity.class));
                return true;
            default:
                return true;
        }
    }

}
