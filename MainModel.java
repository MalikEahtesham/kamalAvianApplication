package com.example.kamalavianapp;

public class MainModel {
    String name,symptoms,price,imageURl;

    MainModel()
    {

    }

    public MainModel(String name, String symptoms, String price, String imageURl) {
        this.name = name;
        this.symptoms = symptoms;
        this.price = price;
        this.imageURl = imageURl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }
}
