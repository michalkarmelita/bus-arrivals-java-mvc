package com.example.trackermvc.stops.ui;

import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;

import com.example.trackermvc.R;
import com.example.trackermvc.app.injection.qualifiers.ForIoThread;
import com.example.trackermvc.app.injection.qualifiers.ForMainThread;
import com.example.trackermvc.app.ui.BaseActionBarView;
import com.example.trackermvc.arrivals.ui.adapters.ArrivalsListAdapter;
import com.example.trackermvc.stops.controller.StopsController;
import com.example.trackermvc.stops.model.StopData;
import com.example.trackermvc.stops.repository.MarkersTransformer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Scheduler;
import timber.log.Timber;

public class StopsViewImpl extends BaseActionBarView implements StopsView, OnMapReadyCallback {
    private static final int MAP_ZOOM = 16;
    private static final int MAP_TILT = 30;

    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.bottom_sheet)
    View mBottomSheet;
    @BindView(R.id.stop_name)
    TextView mStopName;
    @BindView(R.id.stop_details)
    TextView mStopDetails;
    @BindView(R.id.arrivals_button)
    FloatingActionButton mDisplayArrivals;

    private final Resources mResources;
    private final StopsController mController;
    private final MarkersTransformer mTransformer;
    private final Scheduler mUiScheduler;
    private final Scheduler mIoScheduler;
    private GoogleMap mMap;
    private boolean first = true;
    private BottomSheetBehavior<View> mBottomSheetBehavior;

    @Inject
    public StopsViewImpl(Resources resources, StopsController controller, MarkersTransformer transformer,
                         @ForMainThread Scheduler uiScheduler, @ForIoThread Scheduler ioScheduler) {
        super(controller);
        mResources = resources;
        mController = controller;
        mTransformer = transformer;
        mUiScheduler = uiScheduler;
        mIoScheduler = ioScheduler;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_stops;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    public void displayLocation(Location location) {
        if (first && mMap != null) {
            CameraPosition position = CameraPosition.builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(MAP_ZOOM)
                    .tilt(MAP_TILT)
                    .build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));
            first = false;
        }
    }

    @Override
    public void displayStops(List<StopData> stops) {
        mTransformer.transform(stops)
                .subscribeOn(mIoScheduler)
                .observeOn(mUiScheduler)
                .subscribe(markerDetails -> {
                    Marker marker = mMap.addMarker(markerDetails.getMarkerOptions());
                    marker.setTag(markerDetails.getStopData());
                }, Timber::e);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        mMapView.onLowMemory();
    }

    @Override
    public boolean stopDetailsExpanded() {
        int state = mBottomSheetBehavior.getState();
        return state == BottomSheetBehavior.STATE_EXPANDED ||
                state == BottomSheetBehavior.STATE_DRAGGING ||
                state == BottomSheetBehavior.STATE_SETTLING;
    }

    @Override
    public void collapseStopDetails() {
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mDisplayArrivals.setVisibility(View.GONE);
    }

    @Override
    public void init(View view) {
        super.init(view);
        mMapView.getMapAsync(this);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(true);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setMapStyle(new MapStyleOptions(mResources
                .getString(R.string.style_map_json)));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setOnMarkerClickListener(marker -> {
            StopData stopData = (StopData) marker.getTag();
            bindStopData(stopData);
            mDisplayArrivals.setVisibility(View.VISIBLE);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            return false;
        });
    }

    private void bindStopData(StopData stopData) {
        mStopName.setText(stopData.getCommonName());
        mStopDetails.setText(stopData.getIndicator());
        mDisplayArrivals.setOnClickListener(v -> mController.onStopSelected(stopData.getId()));
    }
}
