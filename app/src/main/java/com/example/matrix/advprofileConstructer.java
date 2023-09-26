package com.example.matrix;

import android.widget.EditText;

public class advprofileConstructer {
    private String userId; // Unique identifier (Primary Key)
    private String Name;
    private String mobile;
    private String email;
    private String gender;
    private String aadhar;
    private String location;
    private String lawSchool;
    private String yearOfGraduation;
    private String areaOfPractice;
    private String website;
    private String officeAddress;
    private String introduction;
    private String yearOfExperience;
    private String awardsRecognition;
    private String profileImageUrl;

    private String language;
    // Default constructor (empty)
    public advprofileConstructer() {
    }

    // Parameterized constructor
    public advprofileConstructer(String userId, String Name ,String mobile, String email, String gender, String aadhar,String language,
                                 String location, String lawSchool, String yearOfGraduation,
                                 String areaOfPractice, String website, String officeAddress, String introduction,
                                 String yearOfExperience, String awardsRecognition) {

        this.userId = userId;
        this.Name=Name;
        this.mobile = mobile;
        this.email = email;
        this.gender = gender;
        this.aadhar = aadhar;
        this.language=language;
        this.location = location;
        this.lawSchool = lawSchool;
        this.yearOfGraduation = yearOfGraduation;
        this.areaOfPractice = areaOfPractice;
        this.website = website;
        this.officeAddress = officeAddress;
        this.introduction = introduction;
        this.yearOfExperience = yearOfExperience;
        this.awardsRecognition = awardsRecognition;
    }

    // Getters and setters for all fields
    public String getName() {
        return Name;
    }
    public String getLanguage(){
        return language;
    }
    public void setName(String name) {
        this.Name=name;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLawSchool() {
        return lawSchool;
    }

    public void setLawSchool(String lawSchool) {
        this.lawSchool = lawSchool;
    }

    public String getYearOfGraduation() {
        return yearOfGraduation;
    }

    public void setYearOfGraduation(String yearOfGraduation) {
        this.yearOfGraduation = yearOfGraduation;
    }

    public String getAreaOfPractice() {
        return areaOfPractice;
    }

    public void setAreaOfPractice(String areaOfPractice) {
        this.areaOfPractice = areaOfPractice;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(String yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

    public String getAwardsRecognition() {
        return awardsRecognition;
    }

    public void setAwardsRecognition(String awardsRecognition) {
        this.awardsRecognition = awardsRecognition;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }


    public void setLanguage(String language) {
        this.language=language;
    }
}
