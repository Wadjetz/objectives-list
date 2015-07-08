package fr.objectiveslist;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fr.objectiveslist.fragments.ListFragment;
import fr.objectiveslist.fragments.SortFragment;
import fr.objectiveslist.models.Task;
import fr.objectiveslist.models.TaskAdapter;
import fr.objectiveslist.models.TaskDAO;

public class TaskListActivity extends ActionBarActivity {

    private static final String TAG = "TaskListActivity";
    public static final String TASK_SELECTED = "TASK_SELECTED";


    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction;

    private SortFragment sortFragment = null;
    private ListFragment listFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);


        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        listFragment = new ListFragment();
        fragmentTransaction.add(R.id.main_activity, listFragment,"listfragment");
        fragmentTransaction.commit();
    }



    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.)


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




    public  void trieList(View v){
/*        Intent trie = new Intent(this, Trie.class);
        startActivityForResult(trie, 1);*/
        this.sortFragment = new SortFragment();
        fragmentTransaction = fragmentManager.beginTransaction();
       fragmentTransaction.replace(R.id.main_activity, sortFragment, "sort_fragment");
       fragmentTransaction.commit();
    }
}
