package lk.evenro.even.model;

import java.io.Serializable;

public class Watchlist implements Serializable {

    private String eventID;
    private String eventName;
    private String eventDescription;
    private String eventCategory;
    private String eventLoaction;
    private String eventPrice;
    private String eventDate;
    private String eventTime;
    private String eventOrganizerName;
    private String eventMobileNumber;
    private String eventImageUri;

    public Watchlist() {
    }

    public Watchlist(String eventID, String eventName, String eventDescription, String eventCategory, String eventLoaction, String eventPrice, String eventDate, String eventTime, String eventOrganizerName, String eventMobileNumber, String eventImageUri) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventCategory = eventCategory;
        this.eventLoaction = eventLoaction;
        this.eventPrice = eventPrice;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.eventOrganizerName = eventOrganizerName;
        this.eventMobileNumber = eventMobileNumber;
        this.eventImageUri = eventImageUri;
    }


    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getEventLoaction() {
        return eventLoaction;
    }

    public void setEventLoaction(String eventLoaction) {
        this.eventLoaction = eventLoaction;
    }

    public String getEventPrice() {
        return eventPrice;
    }

    public void setEventPrice(String eventPrice) {
        this.eventPrice = eventPrice;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventOrganizerName() {
        return eventOrganizerName;
    }

    public void setEventOrganizerName(String eventOrganizerName) {
        this.eventOrganizerName = eventOrganizerName;
    }

    public String getEventMobileNumber() {
        return eventMobileNumber;
    }

    public void setEventMobileNumber(String eventMobileNumber) {
        this.eventMobileNumber = eventMobileNumber;
    }

    public String getEventImageUri() {
        return eventImageUri;
    }

    public void setEventImageUri(String eventImageUri) {
        this.eventImageUri = eventImageUri;
    }
}
