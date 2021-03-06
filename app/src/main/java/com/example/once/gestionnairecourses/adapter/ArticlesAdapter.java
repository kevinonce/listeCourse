package com.example.once.gestionnairecourses.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.once.gestionnairecourses.R;
import com.example.once.gestionnairecourses.pojo.Article;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

/**
 * Created by once on 23/09/15.
 */
public class ArticlesAdapter extends ArrayAdapter<Article> {
    private RuntimeExceptionDao<Article, Long> daoArticle;

    public ArticlesAdapter(Context context, List<Article> listeArticles, RuntimeExceptionDao<Article, Long> daoArticle) {
        super(context, 0, listeArticles);
        this.daoArticle = daoArticle;
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

        viewHolder.quantiteArticle.setText("Quantité: " + removeZeroIfExist(article.getQuantity()));
        viewHolder.selectionArticle.setChecked(article.isSelected());
        viewHolder.selectionArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Article article = (Article) cb.getTag();
                article.setIsSelected(((CheckBox) v).isChecked());
                ArticlesAdapter.this.notifyDataSetChanged();
                daoArticle.update(article);

            }
        });

        if(viewHolder.selectionArticle.isChecked()){
            convertView.setBackgroundColor(Color.parseColor("#F9E778"));
            viewHolder.nomArticle.setPaintFlags(viewHolder.nomArticle.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }else{
            convertView.setBackgroundColor(Color.parseColor("#F7F5BB"));
            viewHolder.nomArticle.setPaintFlags(viewHolder.nomArticle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        ((ArticleViewHolder) convertView.getTag()).selectionArticle.setTag(article);

        return convertView;
    }

    private String removeZeroIfExist(Float quantite){

        String quantiteS = quantite.toString();
        if(quantiteS.endsWith(".0")){
            return quantiteS.substring(0,quantiteS.indexOf("."));
        }else{
            return quantiteS;
        }


    }
    private class ArticleViewHolder{
        public TextView nomArticle;
        public TextView quantiteArticle;
        public CheckBox selectionArticle;
    }
}
