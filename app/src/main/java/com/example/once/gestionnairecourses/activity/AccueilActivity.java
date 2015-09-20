package com.example.once.gestionnairecourses.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.once.gestionnairecourses.R;
import com.example.once.gestionnairecourses.adapter.ListeCourseAdapter;
import com.example.once.gestionnairecourses.bdd.CourseDbHelper;
import com.example.once.gestionnairecourses.pojo.ListeCourse;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class AccueilActivity extends OrmLiteBaseActivity<CourseDbHelper> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        ListView listeCourses = (ListView) findViewById(R.id.listCourses);

        RuntimeExceptionDao<ListeCourse, Long> daoListeCourse = getHelper().getRuntimeExceptionDao(ListeCourse.class);

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


        final ListeCourseAdapter adapter = new ListeCourseAdapter(AccueilActivity.this, daoListeCourse.queryForAll());

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

        //custom dialog box

    }
}
