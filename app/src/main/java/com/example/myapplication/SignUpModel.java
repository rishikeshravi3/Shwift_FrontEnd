package com.example.myapplication;

public class SignUpModel{
    public String accType;
    public String emailId;
    public String firstName;
    public String lastName;
    public String pSWD;
    public String phoneNum;
    public String orgName;
    public String orgNum;
    public String orgDescription;
    public String profileDp;
    public String availability;



    public SignUpModel(String name, String lastName,String emailId,String pSWD,String accType,String phoneNum,String profileDp,String orgName,String orgNum,String orgDesc) {
        this.firstName = name;
        this.lastName = lastName;
        this.emailId = emailId;
        this.pSWD = pSWD;
        this.accType= accType;
        this.phoneNum = phoneNum;
        this.profileDp = profileDp;
        this.orgName =orgName;
        this.orgNum = orgNum;
        this.orgDescription = orgDesc;
    }
}
