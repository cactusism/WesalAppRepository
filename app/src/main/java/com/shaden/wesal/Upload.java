package com.shaden.wesal;

public class Upload {
    private String title;
    private String imageURL;

    public Upload(){

    }

    public Upload(String title, String url){
            if(title.trim().equals("")){
                title = "بدون عنوان" ;
            }
            this.title = title;
            this.imageURL= url;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setImageURL(String url){
        this.imageURL = url;
    }
}
