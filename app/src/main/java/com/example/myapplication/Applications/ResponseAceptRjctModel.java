package com.example.myapplication.Applications;

public class ResponseAceptRjctModel {

    public String recruiter_emailId;
    public String employee_emailId;
    public  String jobId;
    public  int appStatus;


    public ResponseAceptRjctModel(String recruiter_emailId,String employee_emailId,String jobId,int appStatus ){

        this.recruiter_emailId = recruiter_emailId;
        this.employee_emailId =employee_emailId;
        this.jobId = jobId;
        this.appStatus = appStatus;
    }
}
