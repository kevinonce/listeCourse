package com.example.once.gestionnairecourses.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by once on 14/09/15.
 */
public class ListeCourse {

    private String name;
    private List<Article> listeArticle;

    public ListeCourse(){
        super();
        listeArticle = new ArrayList<>();

    }
    public ListeCourse(String name) {
        super();
        this.name = name;
        listeArticle = new ArrayList<>();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Article> getListeArticle() {
        return listeArticle;
    }

    public void setListeArticle(List<Article> listeArticle) {
        this.listeArticle = listeArticle;
    }

    public void addArticle(Article article){
        listeArticle.add(article);
    }

    public void removeArticle(Article article){
        listeArticle.remove(article);
    }


}
