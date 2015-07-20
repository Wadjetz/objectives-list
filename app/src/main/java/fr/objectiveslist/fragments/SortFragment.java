package fr.objectiveslist.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.objectiveslist.R;
import fr.objectiveslist.TaskListActivity;
import fr.objectiveslist.helpers.DatePickerFragment;
import fr.objectiveslist.helpers.Dates;
import fr.objectiveslist.models.Task;
import fr.objectiveslist.models.TaskDAO;

public class SortFragment extends Fragment {


    private Button dateTrie = null;

    private Spinner spinner = null;
    private Spinner categories = null;
    private EditText text = null;
    private Calendar calendar = Calendar.getInstance();
    TaskDAO dao = null;

    private Button sort = null;
    private Button cancel = null;

    private Context context = null;

    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        context = view.getContext();
        spinner = (Spinner) view.findViewById(R.id.etat_trie);
        text = (EditText) view.findViewById(R.id.nom);

        dateTrie = (Button) view.findViewById(R.id.dateTrie);

        dateTrie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        sort = (Button) view.findViewById(R.id.sort);

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trieTask(v);
            }
        });

        cancel = (Button) view.findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAction(v);
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.etat_trie, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        categories = (Spinner) view.findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(context, R.array.categorie, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categories.setAdapter(adapter2);


        dao = new TaskDAO(context);
        dao.open();

        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    @Override
    public void onPause() {
        super.onPause();
        dao.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        dao.open();
    }

    public void cancelAction(View v) {
        dao.close();
        ListFragment listFragment = new ListFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, listFragment, "fragmentlist");
        fragmentTransaction.commit();

        //finish();
    }

    public  void trieTask(View v){
        String etatString = spinner.getSelectedItem().toString();
        Log.i("TrieFrag", "fdksldf");
        String nom = text.getText().toString();
        String categireTask = categories.getSelectedItem().toString();
        Date date = calendar.getTime();
        ArrayAdapter<CharSequence> spinner = ArrayAdapter.createFromResource(context, R.array.etat_trie, android.R.layout.simple_spinner_item);
        Log.i("infor", spinner.getItem(0).toString());
        List<Task> listTrie = null;
        //si une date est renseigner
        if(!nom.equals("") && !dateTrie.getText().toString().equals(getString(R.string.set_date))) {
            //si il y a une date + un nom
            if(etatString.equals(spinner.getItem(0).toString()) && !nom.equals("")){
                listTrie = dao.getTrieTask(date, nom, 1, categireTask);
            }
            //si il y a une date + un etat
            else if(!etatString.equals(spinner.getItem(0).toString()) && nom.equals("")){
                listTrie = dao.getTrieTask(date, etatString, 0, categireTask);
            }
            //tout les champs
            else {
                listTrie = dao.getTrieTask(etatString, date, nom, categireTask);
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
                listTrie = dao.getTrieTask(etatString, nom, categireTask);
            }
        }
        //uniquement la date
        else if(!dateTrie.getText().toString().equals(getString(R.string.set_date))){
            listTrie = (ArrayList<Task>) dao.getTrieTask(date, categireTask);
        }
        else{
            //tout les taches ne sont pas  rensigner
            if(etatString.equals(spinner.getItem(0).toString())){
                listTrie = (ArrayList<Task>) dao.getAllTasks();
            }
            //que l'etat est rechercher
            else {
                listTrie = dao.getTrieTask(etatString, categireTask);
            }
        }
        dao.close();
        ListFragment listFragment = new ListFragment();
        listFragment.setList(listTrie);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity, listFragment, "fragmentlist");
        fragmentTransaction.commit();

    }

    public void showDatePickerDialog(View v) {
        DatePickerFragment date = new DatePickerFragment() {
            @Override
            public void onDateSet(DatePicker view, int y, int m, int d) {
                calendar.set(y, m, d);
                dateTrie.setText(Dates.dateFormat.format(calendar.getTime()));
            }
        };
        date.show(getFragmentManager().beginTransaction(), "date");
    }




}
