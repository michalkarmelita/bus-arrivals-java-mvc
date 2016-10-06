package com.example.trackermvc.stops.manager;

import android.location.Location;

/**
 * Manager dealing with location services.
 */
public interface LocationManager {
    /**
     * Starts listening for location updates.
     */
    void requestUpdates(LocationManagerListener listener);

    /**
     * Stops listening for location updates.
     */
    void removeUpdates();

    /**
     * Check location services are enabled.
     */
    boolean isLocationEnabled();

    /**
     * Get last known location.
     */
    Location getLastKnownLocation();
}
