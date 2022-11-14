package org.example.Utils;

public interface MarketAlert {
    public static int WEBSITE_UNAVAILABLE = 0;
    public static int WEBSITE_AVAILABLE = 1;
    public int getAlertWebsiteStatus();
}
