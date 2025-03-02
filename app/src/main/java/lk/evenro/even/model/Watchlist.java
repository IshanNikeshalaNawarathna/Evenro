package lk.evenro.even.model;

import java.io.Serializable;

public class Watchlist implements Serializable {

    private String eventID;
    private String eventName;

    private String eventLoaction;
    private String eventPrice;
    private String eventDate;

    private String eventImageUri;

    public Watchlist() {
    }

    public Watchlist(String eventID, String eventName, String eventLoaction, String eventPrice, String eventDate, String eventImageUri) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventLoaction = eventLoaction;
        this.eventPrice = eventPrice;
        this.eventDate = eventDate;
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

    public String getEventImageUri() {
        return eventImageUri;
    }

    public void setEventImageUri(String eventImageUri) {
        this.eventImageUri = eventImageUri;
    }
}
