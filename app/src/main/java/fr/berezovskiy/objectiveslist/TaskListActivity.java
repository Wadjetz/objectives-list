package fr.berezovskiy.objectiveslist;

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

import fr.berezovskiy.objectiveslist.models.Task;
import fr.berezovskiy.objectiveslist.models.TaskAdapter;
import fr.berezovskiy.objectiveslist.models.TaskDAO;


public class TaskListActivity extends ActionBarActivity {

    private static final String TAG = "TaskListActivity";
    public static final String TASK_SELECTED = "TASK_SELECTED";

    private ListView taskListView = null;

    private TaskDAO tasksDao = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        tasksDao = new TaskDAO(this);
        tasksDao.open();

        taskListView = (ListView) findViewById(R.id.task_listView);
        taskListView.setAdapter(new TaskAdapter(this, getTasks()));

        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) parent.getItemAtPosition(position);
                Intent intent = new Intent(TaskListActivity.this, TaskActivity.class);
                intent.putExtra(TASK_SELECTED, task);
                Log.d(TAG, task.toString());
                startActivity(intent);
            }
        });

    }

    /**
     * Gets Tasks from database
     * @return
     */
    private ArrayList<Task> getTasks() {
        return (ArrayList<Task>) tasksDao.getAllTasks();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
