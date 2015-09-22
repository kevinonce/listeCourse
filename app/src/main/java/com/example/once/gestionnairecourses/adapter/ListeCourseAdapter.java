package com.example.once.gestionnairecourses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.once.gestionnairecourses.R;
import com.example.once.gestionnairecourses.pojo.ListeCourse;

import java.util.List;

/**
 * Created by once on 14/09/15.
 */
public class ListeCourseAdapter extends ArrayAdapter<ListeCourse>{

    public ListeCourseAdapter(Context context, List<ListeCourse> listeCourses) {
        super(context, 0, listeCourses);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.liste_course,parent, false);
        }

        ListeCourseViewHolder viewHolder = (ListeCourseViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ListeCourseViewHolder();
            viewHolder.nomListe = (TextView) convertView.findViewById(R.id.nomListe);
            viewHolder.nbrArticle = (TextView) convertView.findViewById(R.id.nombreArticle);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<ListeCourse>
        ListeCourse listeCourse = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nomListe.setText(listeCourse.getName());
        viewHolder.nbrArticle.setText(listeCourse.getArticles() != null ? listeCourse.getArticles().size()+" article(s)" : " 0 article(s)");

        return convertView;
    }

    private class ListeCourseViewHolder{
        public TextView nomListe;
        public TextView nbrArticle;
    }
}
