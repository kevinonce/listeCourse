package com.example.once.gestionnairecourses.pojo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by once on 14/09/15.
 */

@DatabaseTable(tableName = "article")

public class Article {

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String name;

    @DatabaseField
    private int quantity;

    @DatabaseField
    private boolean isSelected;

    @DatabaseField(foreign = true)
    ListeCourse listeCourse;

    public Article(){

    }

    public Article(ListeCourse liste, String nameTp, int quantityTp, boolean isSelectedTp){
        super();
        this.listeCourse = liste;
        this.name = nameTp;
        this.quantity = quantityTp;
        this.isSelected = isSelectedTp;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public ListeCourse getListeCourse() {
        return listeCourse;
    }

    public void setListeCourse(ListeCourse listeCourse) {
        this.listeCourse = listeCourse;
    }
}
