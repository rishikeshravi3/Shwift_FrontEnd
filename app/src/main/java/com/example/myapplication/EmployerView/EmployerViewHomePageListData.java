package com.example.myapplication.EmployerView;

public class EmployerViewHomePageListData {
    private String role;
    private String name;
    private String location;
    private String availability;
    private String positionType;
    private String workType;
    private int photoId;
    public EmployerViewHomePageListData(String role, String name, String location,String availability,String positionType, String workType, int photoId){
        this.role=role;
        this.name=name;
        this.location=location;
        this.availability=availability;
        this.photoId=photoId;
        this.positionType=positionType;
        this.workType=workType;
    }
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role=role;
    }
    public String get_Name(){
        return name;
    }
    public void set_Name(String name){
        this.name=name;
    }
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location=location;
    }
    public void setPhotoId(int photoId){
        this.photoId=photoId;
    }
    public int getPhotoId(){
        return photoId;
    }
    public String getAvailability(){
        return availability;
    }
    public void setAvailability(String availability){
        this.availability=availability;
    }
    public String getPositionType(){
        return positionType;
    }
    public void setPositionType(String positionType){
        this.positionType=positionType;
    }
    public String getWorkType(){
        return workType;
    }
    public void setWorkType(String workType){
        this.workType=workType;
    }
}