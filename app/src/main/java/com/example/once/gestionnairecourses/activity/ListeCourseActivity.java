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
                ((EditText)dialogVue.findViewById(R.id.quantiteArticleDialog)).setText(articleCourant.getQuantity().toString());


                builder.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditText nomListe = (EditText)dialogVue.findViewById(R.id.nomArticleDialog);
                        String nom = nomListe.getText().toString();
                        EditText quantiteArticleEdit = (EditText)dialogVue.findViewById(R.id.quantiteArticleDialog);
                        CheckBox necessaire = (CheckBox)dialogVue.findViewById(R.id.necessaireArticleDialog);
                        if(verifierDonnees(nom,quantiteArticleEdit.getText().toString())) {
                            articleCourant.setName(nom);
                            articleCourant.setQuantity(Float.valueOf(quantiteArticleEdit.getText().toString()));
                            articleCourant.setIsSelected(necessaire.isChecked());
                            daoArticle.update(articleCourant);
                            adapter.notifyDataSetChanged();
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

        adapter = new ArticlesAdapter(ListeCourseActivity.this, daoArticle.queryForEq("listeCourse_id",currentListe ),daoArticle);

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
                if(verifierDonnees(nom,quantiteArticleEdit.getText().toString())) {
                    Article article = new Article(currentListe, nom, Float.valueOf(quantiteArticleEdit.getText().toString()), necessaire.isChecked());
                    daoArticle.create(article);
                    adapter.add(article);
                    adapter.notifyDataSetChanged();
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

    private boolean verifierDonnees(String nom, String quantite){
        StringBuffer message = new StringBuffer();
        if(nom.trim().length() <= 0 || !(nom.trim().length() > 0 && nom.trim().length() < 25)){
            message.append("Nom incorrect, la taille doit être comprise entre 0 et 25 caractères !");
        }

        try{
            Float quantiteConvert = Float.valueOf(quantite);
            if(quantiteConvert <= 0){
                throw new NumberFormatException();
            }
        }catch (NumberFormatException ex){
            message.append("\nLa quantité doit être un nombre décimal supérieur à 0");
        }

        if(message.length() > 0){
            Toast.makeText(ListeCourseActivity.this, message.toString(), Toast.LENGTH_LONG).show();
            return false;
        }else{
            return true;
        }

    }
}
