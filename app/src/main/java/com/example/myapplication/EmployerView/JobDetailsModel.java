package com.example.myapplication.EmployerView;

public class JobDetailsModel{
    public String jobTitle;
    public String jobDescription;
    public String jobRequirement;
    public String jobPriority;
    public String payScale;
    public String numHours;
    public String jobLocation;
    public int positionType;
    public String startDate;
    public String appDeadline;
    public String recruiterName;
    public int positionOnsite;
    public  String recruiterEmail;


    public JobDetailsModel(String jobTitle, String jobDescription, String jobRequirement, String jobPriority,
                           String payScale, String numHours, String jobLocation, int positionType,
                           String startDate, String appDeadline, String recruiterName, int positionOnsite,String recruiterEmail) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.jobRequirement = jobRequirement;
        this.jobPriority = jobPriority;
        this.payScale = payScale;
        this.numHours = numHours;
        this.jobLocation = jobLocation;
        this.positionType = positionType;
        this.startDate = startDate;
        this.appDeadline = appDeadline;
        this.recruiterName = recruiterName;
        this.positionOnsite = positionOnsite;
        this.recruiterEmail= recruiterEmail;
    }
}