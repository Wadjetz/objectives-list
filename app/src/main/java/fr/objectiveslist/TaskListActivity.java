package fr.objectiveslist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fr.objectiveslist.models.Task;
import fr.objectiveslist.models.TaskAdapter;
import fr.objectiveslist.models.TaskDAO;

public class TaskListActivity extends ActionBarActivity {

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

        timeAlert();

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

    public void timeAlert()
    {
        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
        String formattedDate = df.format(c.getTime());

        Cursor cursor = tasksDao.db.query(tasksDao.TABLE_NAME, tasksDao.allColumns, "date_limit < '"+formattedDate+"' and ", null, null, null, null);
        cursor.moveToFirst();
        while (! cursor.isAfterLast()) {
            Log.d(TAG, cursor.getString(4));
            cursor.moveToNext();
        }

        if(cursor.getCount()>0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tasks are passed !!!");
            builder.setPositiveButton("Ok", null);
            builder.create().show();
        }

        cursor.close();

    }

}
