package org.example;

public class Alert {
    // This will be used for the POST Json Object
    protected int alertType;
    protected String heading;
    protected String description;
    protected String url;
    protected String imageUrl;
    protected String postedBy = "4414c849-bd0e-4f98-9bd4-405c4d6df075\n";
    protected int priceInCents;

    public Alert(int alertType, String heading, String description, String url, String imageUrl, int priceInCents) {
        this.alertType = alertType;
        this.heading = heading;
        this.description = description;
        this.url = url;
        this.imageUrl = imageUrl;
        this.priceInCents = priceInCents;
    }

    public void setAlertType(int alertType){
        this.alertType = alertType;
    }
}
