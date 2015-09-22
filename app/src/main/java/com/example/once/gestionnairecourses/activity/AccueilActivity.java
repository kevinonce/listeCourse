package com.example.once.gestionnairecourses.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.once.gestionnairecourses.R;
import com.example.once.gestionnairecourses.adapter.ListeCourseAdapter;
import com.example.once.gestionnairecourses.bdd.CourseDbHelper;
import com.example.once.gestionnairecourses.pojo.ListeCourse;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class AccueilActivity extends OrmLiteBaseActivity<CourseDbHelper> {

    private RuntimeExceptionDao<ListeCourse, Long> daoListeCourse;
    private ListeCourseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        final ListView listeCourses = (ListView) findViewById(R.id.listCourses);
        listeCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListeCourse listeCourseDelete = (ListeCourse) listeCourses.getItemAtPosition(position);
                Intent intent = new Intent(AccueilActivity.this,ListeCourseActivity.class);
                startActivity(intent);
            }
        });

        listeCourses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ListeCourse listeCourseDelete = (ListeCourse) listeCourses.getItemAtPosition(position);
                daoListeCourse.delete(listeCourseDelete);
                adapter.remove(listeCourseDelete);
                adapter.notifyDataSetChanged();

                return true;
            }
        });

        daoListeCourse = getHelper().getRuntimeExceptionDao(ListeCourse.class);
        /*ListeCourse listeAuchan = new ListeCourse("Auchan");
        ListeCourse listeLidl = new ListeCourse("Lidl");

        daoListeCourse.create(listeAuchan);
        daoListeCourse.create(listeLidl);

        RuntimeExceptionDao<Article, Long> daoArticle = getHelper().getRuntimeExceptionDao(Article.class);

        Article oeufs = new Article(listeAuchan, "oeufs", 12, false);
        Article filetPoulet = new Article(listeAuchan, "1.5kg Filet de poulet", 2, false);
        Article saumon = new Article(listeLidl, "280 gr de saumon", 1, false);

        daoArticle.create(oeufs);
        daoArticle.create(filetPoulet);
        daoArticle.create(saumon);*/


        adapter = new ListeCourseAdapter(AccueilActivity.this, daoListeCourse.queryForAll());

        listeCourses.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accueil, menu);
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

    public void addListClick(View vue){

        AlertDialog.Builder builder = new AlertDialog.Builder(AccueilActivity.this);
        LayoutInflater inflater = AccueilActivity.this.getLayoutInflater();
        final View dialogVue = inflater.inflate(R.layout.dialog_box_ajout_liste, null);
        builder.setView(dialogVue);

        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText nomListe = (EditText)dialogVue.findViewById(R.id.nomListeDialog);
                String nom = nomListe.getText().toString();
                if(nom.trim().length() > 0 && nom.trim().length() < 25) {
                    ListeCourse liste = new ListeCourse(nom);
                    daoListeCourse.create(liste);
                    adapter.add(liste);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(AccueilActivity.this,"Nom incorrect, la taille doit être comprise entre 0 et 25 caractères !",Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
