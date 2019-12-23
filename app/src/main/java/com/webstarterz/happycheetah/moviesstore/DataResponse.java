package com.webstarterz.happycheetah.moviesstore;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResponse {


    @SerializedName("status")
    private String Status;

    @SerializedName("i")
    private int I;

    @SerializedName("images")
    List<Images> Images;

    @SerializedName("serviceitem")
    List<ServiceList> serviceitem;


    @SerializedName("items")
    List<ItemList> items;

    @SerializedName("delete")
    String delete;

    public String getStatus() {
        return Status;
    }

    public int getI() {
        return I;
    }

    public List<com.webstarterz.happycheetah.moviesstore.Images> getImages() {
        return Images;
    }

    public List<com.webstarterz.happycheetah.moviesstore.ItemList> getItemList() {
        return items;
    }

    public List<com.webstarterz.happycheetah.moviesstore.ServiceList> getServiceList() {
        return serviceitem;
    }

    public String getDeleteItem() {
        return delete;
    }

}

class Images{
    @SerializedName("name")
    private String Imagename;
    @SerializedName("year")
    private String ImageYear;
    @SerializedName("image_path")
    private String ImagePath;
    @SerializedName("codeno")
    private String CodeNo;
    @SerializedName("type")
    private String Type;
    @SerializedName("genre")
    private String Genre;
    @SerializedName("rating")
    private String Rating;
    @SerializedName("about")
    private String About;

    public String getCodeNo() {
        return CodeNo;
    }

    public String getType() {
        return Type;
    }

    public String getGenre() {
        return Genre;
    }

    public String getRating() {
        return Rating;
    }

    public String getAbout() {
        return About;
    }

    public String getImagename() {
        return Imagename;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public String getImageYear() {
        return ImageYear;
    }
}

class ItemList{
    @SerializedName("id")
    private int id;

    @SerializedName("brand")
    private String brand;

    @SerializedName("model")
    private String model;

    @SerializedName("price")
    private String price;

    @SerializedName("url")
    private String url;

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }
}

class ServiceList{

    @SerializedName("id")
    private int id;

    @SerializedName("service")
    private String service;

    public int getId() {
        return id;
    }

    public String getService() {
        return service;
    }

}
