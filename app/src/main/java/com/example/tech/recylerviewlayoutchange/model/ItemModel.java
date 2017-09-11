package com.example.tech.recylerviewlayoutchange.model;



public class ItemModel {
    private String name;
    private boolean selected = false;


    public ItemModel(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void toggleData(){
        selected = !selected;
    }
}
