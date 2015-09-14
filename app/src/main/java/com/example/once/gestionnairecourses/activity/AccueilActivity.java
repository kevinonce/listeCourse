package com.example.once.gestionnairecourses.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.once.gestionnairecourses.R;
import com.example.once.gestionnairecourses.adapter.ListeCourseAdapter;
import com.example.once.gestionnairecourses.pojo.ListeCourse;

import java.util.ArrayList;
import java.util.List;

public class AccueilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        ListView listeCourses = (ListView)findViewById(R.id.listCourses);

        final ListeCourseAdapter adapter = new ListeCourseAdapter(AccueilActivity.this,genererListeCourses());

        listeCourses.setAdapter(adapter);


    }

    private List<ListeCourse> genererListeCourses(){
        List<ListeCourse> listeCourses = new ArrayList<ListeCourse>();
        listeCourses.add(new ListeCourse("Auchan"));
        listeCourses.add(new ListeCourse("Lidl"));
        listeCourses.add(new ListeCourse("Carrefour"));

        return listeCourses;
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
}
