package com.example.myapplication;

public class ApplicationStageListData {
    private String role;
    private String company;
    private String status;
    private int logoId;
    public ApplicationStageListData(String role, String company, String status, int logoId){
        this.role=role;
        this.company=company;
        this.status=status;
        this.logoId=logoId;
    }
    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role=role;
    }
    public String getCompany(){
        return company;
    }
    public void setCompany(String company){
        this.company=company;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public void setLogoId(int logoId){
        this.logoId=logoId;
    }
    public int getLogoId(){
        return logoId;
    }
}
