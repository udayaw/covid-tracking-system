package org.chathu.covid.api.model.documents;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.chathu.covid.api.model.documents.Document;
import org.chathu.covid.api.model.types.PointLocation;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends Document {

    //private String userName;
    private String firstName;
    private String lastName;
    private String passHash;
    private String phoneNumber;
    private String NIC;
    private String DOB;
    private UserType userType;
    private PointLocation lastKnownLocation;
    private String covidId;
    private boolean isCovidCase;
    private int encounters;

    public int getEncounters() {
        return encounters;
    }

    public void setEncounters(int encounters) {
        this.encounters = encounters;
    }

    public String getCovidId() {
        return covidId;
    }

    public void setCovidId(String covidId) {
        this.covidId = covidId;
    }

    public boolean isCovidCase() {
        return isCovidCase;
    }

    public void setCovidCase(boolean covidCase) {
        isCovidCase = covidCase;
    }


    //private List<PointLocation> locationHistory;

    public enum UserType{
        @JsonProperty("standard")
        STANDARD,
        @JsonProperty("privilege")
        PRIVILEGE
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
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

    public String getPassHash() {
        return passHash;
    }

    public void setPassHash(String passHash) {
        this.passHash = passHash;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public PointLocation getLastKnownLocation() {
        return lastKnownLocation;
    }

    public void setLastKnownLocation(PointLocation lastKnownLocation) {
        this.lastKnownLocation = lastKnownLocation;
    }
//
//    public List<PointLocation> getLocationHistory() {
//        return locationHistory;
//    }
//
//    public void setLocationHistory(List<PointLocation> locationHistory) {
//        this.locationHistory = locationHistory;
//    }
}
