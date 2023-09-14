package com.example.collegeapp.clubs;

public class ClubData {
    private String name,facCor,stuCor,contact,image,key;

    public ClubData() {
    }

    public ClubData(String name, String facCor, String stuCor, String contact, String image, String key) {
        this.name = name;
        this.facCor = facCor;
        this.stuCor = stuCor;
        this.contact = contact;
        this.image = image;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacCor() {
        return facCor;
    }

    public void setFacCor(String facCor) {
        this.facCor = facCor;
    }

    public String getStuCor() {
        return stuCor;
    }

    public void setStuCor(String stuCor) {
        this.stuCor = stuCor;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
