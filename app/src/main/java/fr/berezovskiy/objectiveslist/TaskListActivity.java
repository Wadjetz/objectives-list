package fr.berezovskiy.objectiveslist;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import fr.berezovskiy.objectiveslist.models.Task;
import fr.berezovskiy.objectiveslist.models.TaskAdapter;


public class TaskListActivity extends Activity {

    private static final String TAG = "TaskListActivity";
    public static final String TASK_SELECTED = "TASK_SELECTED";

    private ListView taskListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

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
        // TODO create a really database
        ArrayList<Task> taskList = new ArrayList<>();
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };
        for (int i = 0; i < values.length; ++i) {
            taskList.add(new Task(values[i]));
        }
        return taskList;
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
