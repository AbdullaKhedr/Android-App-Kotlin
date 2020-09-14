package qa.edu.qu.cmps312.qatar2022;



import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Stadium extends ArrayList<Parcelable> implements Parcelable {

    private String name;
    private String hostCity;
    private String status;
    private String stadiumImage;

    public Stadium() {
    }

    public Stadium(String name, String hostCity, String status, String stadiumImage) {
        this.name = name;
        this.hostCity = hostCity;
        this.status = status;
        this.stadiumImage = stadiumImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostCity() {
        return hostCity;
    }

    public void setHostCity(String hostCity) {
        this.hostCity = hostCity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStadiumImage() {
        return stadiumImage;
    }

    public void setStadiumImage(String stadiumImage) {
        this.stadiumImage = stadiumImage;
    }

    public static ArrayList<Stadium> populateStadiumsData(){

        //location of the stadiums
        //http://www.qatar.to/stadiums/World-Cup-2022-stadiums.php

        ArrayList<Stadium> stadiums = new ArrayList<>();

        stadiums.add(new Stadium("Al-Gharafa Stadium" , "Al-Rayyan" , "Major renovation" , "al_gharafa"));
        stadiums.add(new Stadium("Al-Khor Stadium" , "Al-Khor " , "To be built" , "al_khoor"));
        stadiums.add(new Stadium("Al-Rayyan Stadium" , "Al-Rayyan" , "Major renovation" , "al_rayan"));
        stadiums.add(new Stadium("Al-Shamal Stadium" , "Al-Shamal" , "To be built" , "al_shamal"));
        stadiums.add(new Stadium("Al-Wakrah Stadium" , "Al-Wakrah" , "To be built" , "al_wakra"));
        stadiums.add(new Stadium("Education city stadium" , " Al-Rayyan" , "To be built" , "education_city"));
        stadiums.add(new Stadium("Umsallal Stadium" , "Umsallal" , "To be built" , "umsallal"));
        stadiums.add(new Stadium("Doha Port Stadium" , "Al-Daayen" , "To be built" , "sports_city"));
        stadiums.add(new Stadium("Lusail Iconic Stadium" , " Lusail City " , "To be built" , "lusail_city"));
        stadiums.add(new Stadium("Qatar University Stadium" , "Doha" , "To be built" , "qatar_university"));

        return stadiums;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.hostCity);
        dest.writeString(this.status);
        dest.writeString(this.stadiumImage);
    }

    protected Stadium(Parcel in) {
        this.name = in.readString();
        this.hostCity = in.readString();
        this.status = in.readString();
        this.stadiumImage = in.readString();
    }

    public static final Parcelable.Creator<Stadium> CREATOR = new Parcelable.Creator<Stadium>() {
        @Override
        public Stadium createFromParcel(Parcel source) {
            return new Stadium(source);
        }

        @Override
        public Stadium[] newArray(int size) {
            return new Stadium[size];
        }
    };
}
