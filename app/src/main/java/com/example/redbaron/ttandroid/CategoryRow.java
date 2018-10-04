package com.example.redbaron.ttandroid;

import java.util.ArrayList;
import java.util.Arrays;

public class CategoryRow {

    private Category left;
    private Category center;
    private Category right;
    private ArrayList<Category> allCats;

    public CategoryRow(Category left, Category center, Category right){
        this.left = left;
        this.center = center;
        this.right = right;
        this.allCats = new ArrayList<>();
        this.allCats.add(this.left);
        if(this.center != null){
            this.allCats.add(this.center);
        }
        if(this.right != null){
            this.allCats.add(this.right);
        }
    }

    public Category getLeft() {
        return left;
    }

    public Category getCenter() {
        return center;
    }

    public Category getRight() {
        return right;
    }

    public ArrayList<Category> getRowCats(){
        return allCats;
    }
}
