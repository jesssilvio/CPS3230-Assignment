package org.example.Utils;

public interface WebDriverStatus {
    public static int DRIVER_INITIALISED = 0;
    public static int DRIVER_NOT_INITIALISED = 1;
    public int getDriverStatus();
}
