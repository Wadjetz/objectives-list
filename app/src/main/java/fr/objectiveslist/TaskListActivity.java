package fr.objectiveslist;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import fr.objectiveslist.fragments.AddTacheFragment;
import fr.objectiveslist.fragments.ListFragment;
import fr.objectiveslist.fragments.SortFragment;

public class TaskListActivity extends ActionBarActivity {

    private static final String TAG = "TaskListActivity";
    public static final String TASK_SELECTED = "TASK_SELECTED";

    private FragmentManager fragmentManager = null;
    private FragmentTransaction fragmentTransaction;

    private SortFragment sortFragment = null;
    private ListFragment listFragment = null;
    private AddTacheFragment addTacheFragment = null;

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
        getMenuInflater().inflate(R.menu.menu_task_list, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_new:
                addTacheFragment = new AddTacheFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_activity, addTacheFragment, "sort_fragment");
                fragmentTransaction.commit();
                return true;
            case R.id.list_trie:
                this.sortFragment = new SortFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_activity, sortFragment, "sort_fragment");
                fragmentTransaction.commit();
                return true;
            default:
                return true;
        }
    }
}
