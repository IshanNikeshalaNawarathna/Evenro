package lk.evenro.even.model;

import java.io.Serializable;

public class EventDetails implements Serializable {

    private String eventId;
    private String eventName;
    private String eventLocations;
    private String eventDescriptions;
    private String prices;
    private String eventCategory;
    private int qty;

    public EventDetails(String eventId, String eventName, String eventLocations, String eventDescriptions, String prices, String eventCategory, int qty) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventLocations = eventLocations;
        this.eventDescriptions = eventDescriptions;
        this.prices = prices;
        this.eventCategory = eventCategory;
        this.qty = qty;
    }

    public EventDetails() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
