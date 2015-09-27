package com.example.once.gestionnairecourses.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.once.gestionnairecourses.R;
import com.example.once.gestionnairecourses.adapter.ArticlesAdapter;
import com.example.once.gestionnairecourses.bdd.CourseDbHelper;
import com.example.once.gestionnairecourses.pojo.Article;
import com.example.once.gestionnairecourses.pojo.ListeCourse;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class ListeCourseActivity extends OrmLiteBaseActivity<CourseDbHelper> {


    private RuntimeExceptionDao<ListeCourse, Long> daoListeCourse;
    private RuntimeExceptionDao<Article, Long> daoArticle;
    private ArticlesAdapter adapter;
    private ListeCourse currentListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_course);

        final ListView listeViewArticle = (ListView) findViewById(R.id.listViewArticle);

        CheckBox checkSelection = (CheckBox) listeViewArticle.findViewById(R.id.articleSelection);

        listeViewArticle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListeCourseActivity.this);
                LayoutInflater inflater = ListeCourseActivity.this.getLayoutInflater();
                final View dialogVue = inflater.inflate(R.layout.dialog_box_ajout_article, null);
                builder.setView(dialogVue);
                builder.setTitle("Modifier un article");
                final Article articleCourant = (Article)listeViewArticle.getItemAtPosition(position);

                ((EditText)dialogVue.findViewById(R.id.nomArticleDialog)).setText(articleCourant.getName());
                ((CheckBox)dialogVue.findViewById(R.id.necessaireArticleDialog)).setChecked(articleCourant.isSelected());

                builder.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText nomListe = (EditText)dialogVue.findViewById(R.id.nomArticleDialog);
                        String nom = nomListe.getText().toString();
                        EditText quantiteArticleEdit = (EditText)dialogVue.findViewById(R.id.quantiteArticleDialog);
                        CheckBox necessaire = (CheckBox)dialogVue.findViewById(R.id.necessaireArticleDialog);
                        if(nom.trim().length() > 0 && nom.trim().length() < 25) {
                            articleCourant.setName(nom);
                            articleCourant.setQuantity(Integer.valueOf(quantiteArticleEdit.getText().toString()));
                            articleCourant.setIsSelected(necessaire.isChecked());
                            daoArticle.update(articleCourant);
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(ListeCourseActivity.this, "Nom incorrect, la taille doit être comprise entre 0 et 25 caractères !", Toast.LENGTH_LONG).show();
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
        });

        listeViewArticle.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ListeCourseActivity.this);
                builder.setMessage("Voulez-vous vraiment supprimer cet article ?");
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Article article = (Article) listeViewArticle.getItemAtPosition(position);
                        daoArticle.delete(article);
                        adapter.remove(article);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });


        Long idListe = (Long)getIntent().getExtras().get("idListe");

        daoArticle  = getHelper().getRuntimeExceptionDao(Article.class);
        daoListeCourse  = getHelper().getRuntimeExceptionDao(ListeCourse.class);
        currentListe = daoListeCourse.queryForId(idListe);

        adapter = new ArticlesAdapter(ListeCourseActivity.this, daoArticle.queryForEq("listeCourse_id",currentListe ));

        final TextView title = (TextView) findViewById(R.id.nomListeCourseTitle);
        title.setText(currentListe.getName());

        listeViewArticle.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_liste_course, menu);
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

    public void addArticleClick(View vue){

        AlertDialog.Builder builder = new AlertDialog.Builder(ListeCourseActivity.this);
        LayoutInflater inflater = ListeCourseActivity.this.getLayoutInflater();
        final View dialogVue = inflater.inflate(R.layout.dialog_box_ajout_article, null);
        builder.setView(dialogVue);
        builder.setTitle("Ajouter un article");

        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText nomListe = (EditText)dialogVue.findViewById(R.id.nomArticleDialog);
                String nom = nomListe.getText().toString();
                EditText quantiteArticleEdit = (EditText)dialogVue.findViewById(R.id.quantiteArticleDialog);
                CheckBox necessaire = (CheckBox)dialogVue.findViewById(R.id.necessaireArticleDialog);
                if(nom.trim().length() > 0 && nom.trim().length() < 25) {
                    Article article = new Article(currentListe, nom, Integer.valueOf(quantiteArticleEdit.getText().toString()), necessaire.isChecked());
                    daoArticle.create(article);
                    adapter.add(article);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(ListeCourseActivity.this, "Nom incorrect, la taille doit être comprise entre 0 et 25 caractères !", Toast.LENGTH_LONG).show();
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
