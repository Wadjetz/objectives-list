package fr.objectiveslist;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.objectiveslist.helpers.DatePickerFragment;
import fr.objectiveslist.helpers.Dates;
import fr.objectiveslist.models.Task;
import fr.objectiveslist.models.TaskDAO;


public class Trie extends ActionBarActivity  {

    private Button dateTrie = null;

    private Spinner spinner = null;
    private EditText text = null;
    private Calendar calendar = Calendar.getInstance();
    TaskDAO dao = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trie);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        spinner = (Spinner) findViewById(R.id.etat_trie);
        text = (EditText) findViewById(R.id.nom);

        dateTrie = (Button) findViewById(R.id.dateTrie);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.etat_trie, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        dao = new TaskDAO(this);
        dao.open();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trie, menu);
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

    public void cancelAction(View v) {
        startActivity(new Intent(this, TaskListActivity.class));
        finish();
    }

    public  void trieTask(View v){
        String etatString = spinner.getSelectedItem().toString();

        String nom = text.getText().toString();
        Date date = calendar.getTime();
        ArrayAdapter<CharSequence> spinner = ArrayAdapter.createFromResource(this, R.array.etat_trie, android.R.layout.simple_spinner_item);
        Log.i("infor", spinner.getItem(0).toString());
        List<Task> listTrie = null;
        //si une date est renseigner
        if(!nom.equals("") && !dateTrie.getText().toString().equals(getString(R.string.set_date))) {
            //si il y a une date + un nom
            if(etatString.equals(spinner.getItem(0).toString()) && !nom.equals("")){
                listTrie = dao.getTrieTask(date, nom, 1, "");
            }
            //si il y a une date + un etat
            else if(!etatString.equals(spinner.getItem(0).toString()) && nom.equals("")){
                listTrie = dao.getTrieTask(date, etatString, 0, "");
            }
            //tout les champs
            else {
                listTrie = dao.getTrieTask(etatString, date, nom, "");
            }
        }
        //si il n'y a pas de date
        else if(dateTrie.getText().toString().equals(getString(R.string.set_date))){
            //si aucun champs n'est renseigner on affiche toutes les taches
            if(etatString.equals(spinner.getItem(0).toString()) && nom.equals("")){

                listTrie = (ArrayList<Task>) dao.getAllTasks();
            }
            else {
                //si aucun etat est voulue on passe etatString a ""
                if(etatString.equals(spinner.getItem(0).toString())){
                    etatString = "";
                }
                listTrie = dao.getTrieTask(etatString, nom);
            }
        }
        //uniquement la date
        else if(!dateTrie.getText().toString().equals(getString(R.string.set_date))){
            listTrie = (ArrayList<Task>) dao.getTrieTask(date, "");
        }
        else{
            //tout les taches ne sont pas  rensigner
            if(etatString.equals(spinner.getItem(0).toString())){
                listTrie = (ArrayList<Task>) dao.getAllTasks();
            }
            //que l'etat est rechercher
            else {
                listTrie = dao.getTrieTask(etatString, "");
            }
        }
        Intent data = new Intent();
        ArrayList<Parcelable> retour = new ArrayList<>();
        for(int i = 0; i < listTrie.size(); i++){
            retour.add(listTrie.get(i));
        }
        data.putExtra("ResultTrie", retour);
        setResult(RESULT_OK, data);
        finish();
    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment date = new DatePickerFragment() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                calendar.set(y, m, d);
                dateTrie.setText(Dates.dateFormat.format(calendar.getTime()));
            }
        };
        date.show(getFragmentManager().beginTransaction(), "datePicker");
    }

}
