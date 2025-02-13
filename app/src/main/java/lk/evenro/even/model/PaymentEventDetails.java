package lk.evenro.even.model;

import java.io.Serializable;

public class PaymentEventDetails implements Serializable {

    private String payment_ID;
    private String qty;
    private String event_name;
    private String buyer_name;
    private String buyer_email;
    private String ticket_price;
    private String payment_date;

    public PaymentEventDetails(String payment_ID, String qty, String event_name, String buyer_name, String buyer_email, String ticket_price, String payment_date) {
        this.payment_ID = payment_ID;
        this.qty = qty;
        this.event_name = event_name;
        this.buyer_name = buyer_name;
        this.buyer_email = buyer_email;
        this.ticket_price = ticket_price;
        this.payment_date = payment_date;
    }

    public PaymentEventDetails(){

    }

    public String getPayment_ID() {
        return payment_ID;
    }

    public void setPayment_ID(String payment_ID) {
        this.payment_ID = payment_ID;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public String getTicket_price() {
        return ticket_price;
    }

    public void setTicket_price(String ticket_price) {
        this.ticket_price = ticket_price;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }
}
