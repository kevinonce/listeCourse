package com.example.once.gestionnairecourses.pojo;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by once on 14/09/15.
 */
@DatabaseTable(tableName = "listeCourse")
public class ListeCourse {


    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;

    @ForeignCollectionField
    private ForeignCollection<Article> Articles;

    public ListeCourse(){

    }

    public ListeCourse(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ForeignCollection<Article> getArticles() {
        return Articles;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || other.getClass() != getClass()) {
            return false;
        }
        return name.equals(((ListeCourse) other).name);
    }

}
