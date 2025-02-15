package lk.evenro.even.model;

import java.io.Serializable;

public class EventDetails implements Serializable {


    private String eventName;
    private String eventLocations;
    private String eventDescriptions;
    private String prices;
    private String eventCategory;
    private String qty;
    private String eventDate;
    private String eventTime;
    private String organizerName;
    private String eventID;
    private String mobileNumber;
    private String imageUri;


    public EventDetails() {
    }

    public EventDetails(String eventName, String eventLocations, String eventDescriptions, String prices, String eventCategory, String qty, String eventDate, String eventTime, String organizerName, String eventID, String mobileNumber, String imageUri) {
        this.eventName = eventName;
        this.eventLocations = eventLocations;
        this.eventDescriptions = eventDescriptions;
        this.prices = prices;
        this.eventCategory = eventCategory;
        this.qty = qty;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.organizerName = organizerName;
        this.eventID = eventID;
        this.mobileNumber = mobileNumber;
        this.imageUri = imageUri;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventLocations() {
        return eventLocations;
    }

    public void setEventLocations(String eventLocations) {
        this.eventLocations = eventLocations;
    }

    public String getEventDescriptions() {
        return eventDescriptions;
    }

    public void setEventDescriptions(String eventDescriptions) {
        this.eventDescriptions = eventDescriptions;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
        this.eventCategory = eventCategory;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
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

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
