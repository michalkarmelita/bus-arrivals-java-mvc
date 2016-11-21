package com.example.trackermvc.stops.ui;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trackermvc.R;
import com.example.trackermvc.app.injection.qualifiers.ForActivity;
import com.example.trackermvc.app.injection.qualifiers.ForIoThread;
import com.example.trackermvc.app.injection.qualifiers.ForMainThread;
import com.example.trackermvc.app.ui.BaseActionBarView;
import com.example.trackermvc.stops.controller.StopsController;
import com.example.trackermvc.stops.model.StopData;
import com.example.trackermvc.stops.repository.MarkersTransformer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

    @BindView(R.id.root_view)
    View mRootView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.favorites_stops)
    View mFavStops;
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
    @BindView(R.id.fav_stop)
    ImageView mFavButton;

    private final Context mContext;
    private final Resources mResources;
    private final StopsController mController;
    private final MarkersTransformer mTransformer;
    private final Scheduler mUiScheduler;
    private final Scheduler mIoScheduler;
    private GoogleMap mMap;
    private boolean first = true;
    private BottomSheetBehavior<View> mBottomSheetBehavior;
    private int bottomSheetState;
    private Marker mCurrentMarker;


    @Inject
    public StopsViewImpl(@ForActivity Context context, Resources resources, StopsController controller, MarkersTransformer transformer,
                         @ForMainThread Scheduler uiScheduler, @ForIoThread Scheduler ioScheduler) {
        super(controller);
        mContext = context;
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
    public void zoomToLocation(Location location) {
        if (first && mMap != null) {
            CameraPosition position = CameraPosition.builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(MAP_ZOOM)
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
    }

    @Override
    public void init(View view) {
        super.init(view);
        mToolbar.setTitle(R.string.stops_activity_title);
        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setHideable(true);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_SETTLING:
                        if (bottomSheetState == BottomSheetBehavior.STATE_DRAGGING ||
                                bottomSheetState == BottomSheetBehavior.STATE_EXPANDED) {
                            mDisplayArrivals.animate().scaleX(0).scaleY(0).setDuration(100);
                            setSmallMarker(mCurrentMarker);
                            break;
                        }
                        if (bottomSheetState == BottomSheetBehavior.STATE_HIDDEN ||
                                bottomSheetState == BottomSheetBehavior.STATE_COLLAPSED) {
                            mDisplayArrivals.animate().scaleX(1).scaleY(1).setDuration(100);
                            break;
                        }
                        mDisplayArrivals.setVisibility(View.VISIBLE);

                }
                bottomSheetState = newState;
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        mFavStops.setOnClickListener(v -> {
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            setSmallMarker(mCurrentMarker);
            mController.showFavStops();
        });
    }

    @Override
    public void initMap() {
        mMapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setIndoorEnabled(false);
        mMap.setMapStyle(new MapStyleOptions(mResources
                .getString(R.string.style_map_json)));
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);
        mMap.setOnMarkerClickListener(marker -> {
            setSmallMarker(mCurrentMarker);
            mCurrentMarker = marker;
            setLargeMarker(marker);
            StopData stopData = (StopData) marker.getTag();
            bindStopData(stopData);
            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            return false;
        });
    }

    @NonNull
    private Bitmap createBitmap(int size) {
        Bitmap mDotMarkerBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mDotMarkerBitmap);
        Paint paint = new Paint();
        paint.setColor(mResources.getColor(android.R.color.holo_red_dark));
        canvas.drawText("70", 0, 0, paint);
        Drawable shape = mResources.getDrawable(R.drawable.bus_stop_marker);
        shape.setBounds(0, 0, mDotMarkerBitmap.getWidth(), mDotMarkerBitmap.getHeight());
        shape.draw(canvas);
        return mDotMarkerBitmap;
    }

    private void setSmallMarker(Marker marker) {
        if (marker != null) {
            Bitmap mDotMarkerBitmap = createBitmap(24);
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(mDotMarkerBitmap));
        }
    }

    private void setLargeMarker(Marker marker) {
        if (marker != null) {
            Bitmap mDotMarkerBitmap = createBitmap(36);
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(mDotMarkerBitmap));
        }
    }

    private void bindStopData(StopData stopData) {
        mStopName.setText(stopData.getCommonName());
        mStopDetails.setText(stopData.getIndicator());
        mDisplayArrivals.setOnClickListener(v -> mController.onStopSelected(stopData.getId(), stopData.getCommonName()));
        setFavouriteIcon(mController.isStopFavorite(stopData));
        mFavButton.setOnClickListener(v -> mController.favoriteStopClicked(stopData));
    }

    @Override
    public void setFavouriteIcon(boolean favorite) {
        int resId = favorite ? R.drawable.ic_star_selected : R.drawable.ic_star_unselected;
        Theme theme = mContext.getTheme();
        Drawable drawable = VectorDrawableCompat.create(mResources, resId, theme);
        mFavButton.setImageDrawable(drawable);
    }

    @Override
    public void showFavoritedSnackbar(boolean favorited) {
        int messageId = favorited ? R.string.stop_saved_message : R.string.stop_removed_message;
        Snackbar.make(mRootView, messageId, Snackbar.LENGTH_LONG).show();
    }
}
