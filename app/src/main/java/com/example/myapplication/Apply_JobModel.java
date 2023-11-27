package com.example.myapplication;

public class Apply_JobModel{
    public String applicantEmailId;
    public int applicationStatus;
    public String jobId;
    public String resumeUrl;
    public String employerEmailId;
    public Apply_JobModel(String applicantEmailId, int applicationStatus, String jobId, String resumeUrl, String employerEmailId) {
        this.applicantEmailId = applicantEmailId;
        this.applicationStatus = applicationStatus;
        this.jobId = jobId;
        this.resumeUrl = resumeUrl;
        this.employerEmailId=employerEmailId;

    }
}