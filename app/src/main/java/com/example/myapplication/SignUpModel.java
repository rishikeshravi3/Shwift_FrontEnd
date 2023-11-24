package com.example.myapplication;

public class SignUpModel{
    public String firstName;
    public String lastName;
    public String emailId;
    public String pSWD;
    public String accType;
    public String phoneNum;


    public SignUpModel(String name, String lastName,String emailId,String pSWD,String accType,String phoneNum) {
        this.firstName = name;
        this.lastName = lastName;
        this.emailId = emailId;
        this.pSWD = pSWD;
        this.accType= accType;
        this.phoneNum = phoneNum;
    }
}
