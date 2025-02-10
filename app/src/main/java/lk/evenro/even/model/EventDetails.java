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


    public EventDetails(String eventName, String eventLocations, String eventDescriptions, String prices, String eventCategory, String qty, String eventDate, String eventTime) {
        this.eventName = eventName;
        this.eventLocations = eventLocations;
        this.eventDescriptions = eventDescriptions;
        this.prices = prices;
        this.eventCategory = eventCategory;
        this.qty = qty;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
    }

    public EventDetails() {
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
}
