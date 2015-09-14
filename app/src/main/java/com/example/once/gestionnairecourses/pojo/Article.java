package com.example.once.gestionnairecourses.pojo;

/**
 * Created by once on 14/09/15.
 */
public class Article {

    private String name;
    private int quantity;
    private boolean isSelected;

    public Article(){
        super();
    }

    public Article(String nameTp, int quantityTp, boolean isSelectedTp){
        super();
        this.name = nameTp;
        this.quantity = quantityTp;
        this.isSelected = isSelectedTp;
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

}
