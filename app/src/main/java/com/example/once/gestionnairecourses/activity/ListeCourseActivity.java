package com.example.once.gestionnairecourses.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_course);

        final ListView listeViewArticle = (ListView) findViewById(R.id.listViewArticle);
        Long idListe = (Long)getIntent().getExtras().get("idListe");

        daoArticle  = getHelper().getRuntimeExceptionDao(Article.class);
        daoListeCourse  = getHelper().getRuntimeExceptionDao(ListeCourse.class);
        ListeCourse liste = daoListeCourse.queryForId(idListe);

        adapter = new ArticlesAdapter(ListeCourseActivity.this, daoArticle.queryForEq("listeCourse_id",liste ));

        listeViewArticle.setAdapter(adapter);

        final TextView title = (TextView) findViewById(R.id.nomListeCourseTitle);
        title.setText(liste.getName());
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
}
