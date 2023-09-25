package com.example.matrix;

public class userprofileconstructor {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String aadhar;
    private String location;
    private String dob;
    private String profileImageUrl;

    public userprofileconstructor() {
        // Default constructor required for Firebase
    }

    public userprofileconstructor(String userId, String firstName, String lastName, String email, String gender,
                       String aadhar, String location, String dob, String profileImageUrl) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.aadhar = aadhar;
        this.location = location;
        this.dob = dob;
        this.profileImageUrl = profileImageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}
