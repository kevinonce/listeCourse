package com.example.once.gestionnairecourses.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.once.gestionnairecourses.R;
import com.example.once.gestionnairecourses.pojo.Article;

import java.util.List;

/**
 * Created by once on 23/09/15.
 */
public class ArticlesAdapter extends ArrayAdapter<Article> {

    public ArticlesAdapter(Context context, List<Article> listeArticles) {
        super(context, 0, listeArticles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article,parent, false);
        }

        ArticleViewHolder viewHolder = (ArticleViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ArticleViewHolder();
            viewHolder.nomArticle = (TextView) convertView.findViewById(R.id.nomArticle);
            viewHolder.quantiteArticle = (TextView) convertView.findViewById(R.id.quantiteArticle);
            viewHolder.selectionArticle = (CheckBox) convertView.findViewById(R.id.articleSelection);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Article>
        Article article = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.nomArticle.setText(article.getName());
        viewHolder.quantiteArticle.setText("Quantité: article.getQuantity()");
        viewHolder.selectionArticle.setSelected(article.isSelected());
        return convertView;
    }

    private class ArticleViewHolder{
        public TextView nomArticle;
        public TextView quantiteArticle;
        public CheckBox selectionArticle;
    }
}
