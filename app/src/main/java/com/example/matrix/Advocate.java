package com.example.matrix;

public class Advocate {
    private  String practiceAreas;
    private  String language;
    private  String experience;
    private  String location;
    private String name;
    private String amount;
    private String imageUrl;

    public Advocate() {
        // Default constructor required for Firebase
    }

    public Advocate(String name, String location, String amount,String experience,String language,String practiceAreas,String imageUrl) {
        this.name = name;
        this.location = location;
        this.amount = amount;
        this.experience=experience;
        this.language=language;
        this.practiceAreas=practiceAreas;
        this.imageUrl = imageUrl;
    }
    public String getLanguage() {
        return language;
    }
    public String getPracticeAreas() {
        return practiceAreas;
    }
    public String getExperience() {
        return experience;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }
    public String getAmount() {
        return amount;
    }


}